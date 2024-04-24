import com.mongodb.MongoException
import com.mongodb.reactivestreams.client.MongoClients
import com.mongodb.reactivestreams.client.MongoCollection
import org.bson.Document
import java.io.File



fun main() {
    try {
        // Connection to the MongoDB
        val uri = "mongodb+srv://adriasilgado:adriasilgado@clusterpractica.muro8id.mongodb.net/?retryWrites=true&w=majority&appName=ClusterPractica"
        val mongoClient = MongoClients.create(uri)

        //Connection to the database
        val dbItb = mongoClient.getDatabase("itb")

        //Connection to the collections
        val collectionRestaurants: MongoCollection<Document> = dbItb.getCollection("restaurants")
        val collectionProducts : MongoCollection<Document> = dbItb.getCollection("products")
        uploadJSON(collectionRestaurants, "JSON/restaurants.json")
        uploadJSON(collectionProducts, "JSON/products.json")
    } catch (e: MongoException) {
        println("An error occurred: ${e.message}")
    }
}

// Function to upload the JSON file to the collection
fun uploadJSON(collection: MongoCollection<Document>, jsonFilePath: String) {
    val jsonFile = File(jsonFilePath)
    val documents = mutableListOf<Document>()
    jsonFile.forEachLine { line ->
        val document = Document.parse(line)
        documents.add(document)
    }
    collection.insertMany(documents)
}