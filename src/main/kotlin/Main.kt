import com.mongodb.reactivestreams.client.MongoClients
import com.mongodb.reactivestreams.client.MongoCollection
import org.bson.Document
import java.io.File



fun main() {
    // Connection to the MongoDB
    val uri = "mongodb+srv://adriasilgado:123456@clusterpractica.muro8id.mongodb.net/?retryWrites=true&w=majority&appName=ClusterPractica"
    val mongoClient = MongoClients.create(uri)

    //Connection to the database
    val dbItb = mongoClient.getDatabase("itb")

    //Connection to the collections
    val collectionRestaurants: MongoCollection<Document> = dbItb.getCollection("restaurants")
    val collectionProducts : MongoCollection<Document> = dbItb.getCollection("products")

    //Inserting the data from the restaurants JSON
    val jsonFileRestaurants = File("JSON/restaurants.json")
    val documents = mutableListOf<Document>()
    jsonFileRestaurants.forEachLine {restuarant ->
        val document = Document.parse(restuarant)
        documents.add(document)
    }
    collectionRestaurants.insertMany(documents)

    //Inserting the data from the products JSON
    val jsonFileProducts = File("JSON/products.json")
    documents.clear()
    jsonFileProducts.forEachLine { product ->
        val document = Document.parse(product)
        documents.add(document)
    }
    collectionProducts.insertMany(documents)
}