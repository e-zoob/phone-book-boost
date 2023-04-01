using MongoDB.Driver;
using srv_getallnumbers;

var connectionString = "mongodb://127.0.0.1:27017/?directConnection=true&serverSelectionTimeoutMS=2000&appName=mongosh+1.8.0";

var client = new MongoClient(connectionString);

var collection = client.GetDatabase("meteor").GetCollection<Contact>("phonecontacts");

var filter = Builders<Contact>.Filter.Empty;;

var contacts = collection.Find<Contact>(filter).ToList();

foreach(var contact in contacts)
{
    Console.WriteLine($"{contact.Name}: {contact.Number}");
}

