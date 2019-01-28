package integrationtest.mongodb.driver

import com.mongodb.reactivestreams.client.MongoClients
import com.mongodb.reactivestreams.client.MongoCollection
import com.mongodb.reactivestreams.client.Success
import com.sparetimedevs.consonance.config.MongodbConfiguration
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.bson.Document

class ReactiveStreamsMongodbDriver : MongodbDriver {

    private val collection: MongoCollection<Document>

    init {
        val mongoClient = MongoClients.create(MongodbConfiguration.getDbConnectionUrl())

        // get handle to "mydb" database
        val database = mongoClient.getDatabase("mydb")

        // get a handle to the "test" collection
        collection = database.getCollection("test-async")

        // drop all the com.sparetimedevs.consonance.data in it
        val subscriber: SubscriberHelpers.ObservableSubscriber<Success> = SubscriberHelpers.ObservableSubscriber()
        collection.drop().subscribe(subscriber)
        subscriber.await()
    }

    override fun insert(docsToInsert: List<Document>) = runBlocking<Unit> {
        val jobs = ArrayList<Job>()
        for (doc in docsToInsert) {
            val job = launch {
                collection.insertOne(doc).subscribe(SubscriberHelpers.OperationSubscriber<Success>())
            }
            jobs.add(job)
        }
        jobs.forEach { it.join() }
    }

    override fun find(expectedAmount: Int): List<Document> = runBlocking {
        var docs = listOf<Document>()

        var count = 0
        while (docs.size != expectedAmount) {
            when {
                (count == (expectedAmount * 5)) -> {
                    throw Exception("Not the amount the test counts on...")
                }
            }
            delay(10L)
            val subscriber1 = SubscriberHelpers.ObservableSubscriber<Document>()
            collection.find().subscribe(subscriber1)
            subscriber1.await()
            docs = subscriber1.getReceived()
            count++
        }
        return@runBlocking docs
    }
}
