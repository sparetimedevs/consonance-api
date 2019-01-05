package integrationtest.mongodb

import integrationtest.mongodb.driver.MongodbDriver
import integrationtest.mongodb.driver.ReactiveStreamsMongodbDriver
import integrationtest.mongodb.driver.SyncMongodbDriver
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.bson.Document
import org.junit.jupiter.api.Test

class MongodbDriverComparisonIT {

    private val reactiveStreamsMongodbDriver = ReactiveStreamsMongodbDriver()
    private val syncMongodbDriver = SyncMongodbDriver()

    @Test
    fun `Given five inserts at one moment when testing mongodb reactive streams driver and mongodb sync driver then sync driver is faster`() = runBlocking<Unit> {
        val executionTimeUsingReactiveStreamsDriver = executionTime(FIVE_DOCS, reactiveStreamsMongodbDriver::insert)
        val executionTimeUsingSyncDriver = executionTime(FIVE_DOCS, syncMongodbDriver::insert)

        println("execution time using reactive streams driver = $executionTimeUsingReactiveStreamsDriver")
        println("execution time using sync driver = $executionTimeUsingSyncDriver")

        assertThatListInsertedIsAsExpected(reactiveStreamsMongodbDriver, FIVE_DOCS)
        assertThatListInsertedIsAsExpected(syncMongodbDriver, FIVE_DOCS)
        assertThat(executionTimeUsingSyncDriver).`as`("Execution time of sync driver is less then execution time of reactive streams driver.").isLessThan(executionTimeUsingReactiveStreamsDriver)
    }

    @Test
    fun `Given one thousand inserts at one moment when testing mongodb reactive streams driver and mongodb sync driver then reactive streams driver is faster`() = runBlocking<Unit> {
        val executionTimeUsingReactiveStreamsDriver = executionTime(ONE_THOUSAND_DOCS, reactiveStreamsMongodbDriver::insert)
        val executionTimeUsingSyncDriver = executionTime(ONE_THOUSAND_DOCS, syncMongodbDriver::insert)

        println("execution time using reactive streams driver = $executionTimeUsingReactiveStreamsDriver")
        println("execution time using sync driver = $executionTimeUsingSyncDriver")

        assertThatListInsertedIsAsExpected(reactiveStreamsMongodbDriver, ONE_THOUSAND_DOCS)
        assertThatListInsertedIsAsExpected(syncMongodbDriver, ONE_THOUSAND_DOCS)
        assertThat(executionTimeUsingReactiveStreamsDriver).`as`("Execution time of reactive streams driver is less then execution time of sync driver.").isLessThan(executionTimeUsingSyncDriver)
    }

    private fun executionTime(docsToInsert: List<Document>, insertFunction: (docsToInsert: List<Document>) -> Unit): Long {
        val startTime = System.currentTimeMillis()
        insertFunction(docsToInsert)
        val endTime = System.currentTimeMillis()
        return endTime - startTime
    }

    private fun assertThatListInsertedIsAsExpected(mongodbDriver: MongodbDriver, expectedList: List<Document>) {
        val docs = mongodbDriver.find(expectedList.size)

        assertThat(docs.size).`as`("retrieved docs should be same amount as inserted docs").isEqualTo(expectedList.size)
        assertThat(docs).`as`("retrieved docs should be the same docs as the inserted docs").containsAll(expectedList)
    }
}
