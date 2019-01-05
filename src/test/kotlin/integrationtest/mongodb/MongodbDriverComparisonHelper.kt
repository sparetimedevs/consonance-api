package integrationtest.mongodb

import org.bson.Document

private const val FIVE = 5
internal val FIVE_DOCS = createListOfDocs(FIVE)

private const val ONE_THOUSAND = 1000
internal val ONE_THOUSAND_DOCS = createListOfDocs(ONE_THOUSAND)

private fun createListOfDocs(amount: Int) : List<Document> {
    val docsToInsert = mutableListOf<Document>()
    for (i in 1..amount) {
        // make a document and insert it
        val doc = Document("name", "MongoDB$i")
            .append("type", "database$i")
            .append("count", i)
            .append("info", Document("x", 2030 + i).append("y", 1020 + i))

        docsToInsert.add(doc)
    }
    return docsToInsert
}
