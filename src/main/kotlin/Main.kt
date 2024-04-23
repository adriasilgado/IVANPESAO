import com.mongodb.reactivestreams.client.MongoClients
import com.mongodb.reactivestreams.client.MongoCollection
import org.bson.Document
import org.json.simple.JSONArray
import org.json.simple.parser.JSONParser
import java.io.FileReader


fun main() {
    val uri = "mongodb+srv://<username>:<password>@clusterpractica.muro8id.mongodb.net/?retryWrites=true&w=majority&appName=ClusterPractica"
    val mongoClient = MongoClients.create(uri)
    val database = mongoClient.getDatabase("itb")

    // Leer y subir el primer archivo JSON
    val collection1 = database.getCollection("collection1")
    uploadJsonToMongoDB("path/to/your/first.json", collection1)

    // Leer y subir el segundo archivo JSON
    val collection2 = database.getCollection("collection2")
    uploadJsonToMongoDB("path/to/your/second.json", collection2)
}

fun uploadJsonToMongoDB(filePath: String, collection: MongoCollection<Document>) {
    val parser = JSONParser()
    val jsonArray = parser.parse(FileReader(filePath)) as JSONArray

    // Convertir cada objeto JSON en un documento MongoDB y subirlo a la base de datos
    for (jsonObj in jsonArray) {
        val document = Document.parse(jsonObj.toString())
        collection.insertOne(document)
    }
}