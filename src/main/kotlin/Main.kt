import com.mongodb.reactivestreams.client.MongoClients
import com.mongodb.reactivestreams.client.MongoCollection
import org.bson.Document
import java.io.File
import org.json.simple.JSONArray
import org.json.simple.parser.JSONParser


fun main() {
    val uri = "mongodb+srv://<username>:<password>@clusterpractica.muro8id.mongodb.net/?retryWrites=true&w=majority&appName=ClusterPractica"
    val mongoClient = MongoClients.create(uri)
    val database = mongoClient.getDatabase("itb")

    // Cargar y guardar el archivo people.json
    val peopleCollection: MongoCollection<Document> = database.getCollection("people")
    loadJsonIntoCollection("people.json", peopleCollection)

    // Cargar y guardar el archivo products.json
    val productsCollection: MongoCollection<Document> = database.getCollection("products")
    loadJsonIntoCollection("products.json", productsCollection)
}

fun loadJsonIntoCollection(fileName: String, collection: MongoCollection<Document>) {
    val parser = JSONParser()
    val jsonArray: JSONArray = parser.parse(File(fileName).reader()) as JSONArray

    for (item in jsonArray) {
        val document = Document.parse(item.toString())
        collection.insertOne(document)
    }
}