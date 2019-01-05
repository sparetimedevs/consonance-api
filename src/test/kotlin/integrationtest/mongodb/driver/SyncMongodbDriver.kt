package integrationtest.mongodb.driver

import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import com.sparetimedevs.consonance.config.MongodbConfiguration
import org.bson.Document

class SyncMongodbDriver : MongodbDriver {

    private val collection: MongoCollection<Document>

    init {
        val mongoClient = MongoClients.create(MongodbConfiguration.load())

        // get handle to "mydb" database
        val database = mongoClient.getDatabase("mydb")

        // get a handle to the "test" collection
        collection = database.getCollection("test-sync")

        // drop all the com.sparetimedevs.consonance.data in it
        collection.drop()
    }

    override fun insert(docsToInsert: List<Document>) = runBlocking<Unit> {
        val jobs = ArrayList<Job>()
        for (doc in docsToInsert) {
            val job = launch {
                collection.insertOne(doc)
            }
            jobs.add(job)
        }
        jobs.forEach { it.join() }
    }

    override fun find(expectedAmount: Int) : List<Document> = runBlocking {
        var docs = mutableListOf<Document>()

        var count = 0
        while (docs.size != expectedAmount) {
            when {
                (count == 300) -> {
                    throw Exception("Not the amount the test counts on...")
                }
            }
            delay(10L)
            val cursor = collection.find().iterator()
            docs = mutableListOf()
            while (cursor.hasNext()) {
                docs.add(cursor.next())
            }
            count++
        }
        return@runBlocking docs
    }
}
