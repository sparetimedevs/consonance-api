package integrationtest.mongodb.driver

import org.bson.Document

interface MongodbDriver {

    fun insert(docsToInsert: List<Document>)

    fun find(expectedAmount: Int) : List<Document>
}
