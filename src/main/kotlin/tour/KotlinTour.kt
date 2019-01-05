/*
 * Copyright 2015 MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tour

import com.mongodb.reactivestreams.client.MongoClients
import com.mongodb.reactivestreams.client.Success
import com.sparetimedevs.consonance.config.MongodbConfiguration
import org.bson.Document
import tour.SubscriberHelpers.ObservableSubscriber

/**
 * The QuickTour code example see: https://mongodb.github.io/mongo-java-driver-reactivestreams/1.0/getting-started
 */
fun main() {

    val mongoClient = MongoClients.create(MongodbConfiguration.load())

    // get handle to "mydb" database
    val database = mongoClient.getDatabase("mydb")

    // get a handle to the "test" collection
    val collection = database.getCollection("test")

    // drop all the com.sparetimedevs.consonance.data in it
    val subscriber: ObservableSubscriber<Success> = ObservableSubscriber()
    collection.drop().subscribe(subscriber)
    subscriber.await()

    // make a document and insert it
    val doc = Document("name", "MongoDB2")
        .append("type", "database2")
        .append("count", 12)
        .append("info", Document("x", 2032).append("y", 1022))

    collection.insertOne(doc).subscribe(SubscriberHelpers.OperationSubscriber<Success>())

    // get it (since it's the only one in there since we dropped the rest earlier on)
    collection.find().first().subscribe(SubscriberHelpers.PrintDocumentSubscriber())
}
