using MongoDB.Driver;
using srv_getallnumbers;

var connectionString = "mongodb://127.0.0.1:27017/?authSource=meteor&compressors=disabled&gssapiServiceName=mongodb&ssl=false";
if (connectionString == null)
{
    Console.WriteLine("You must set your 'MONGODB_URI' environmental variable. See\n\t https://www.mongodb.com/docs/drivers/go/current/usage-examples/#environment-variable");
    Environment.Exit(0);
}

var client = new MongoClient(connectionString);

var collection = client.GetDatabase("meteor").GetCollection<Contact>("phonecontacts");

var filter = Builders<Contact>.Filter.Empty;;

var document = collection.Find(filter);

Console.WriteLine(document);
