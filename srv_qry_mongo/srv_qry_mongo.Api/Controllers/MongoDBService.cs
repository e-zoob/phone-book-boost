using MongoDB.Driver;
using Microsoft.Extensions.Options;


namespace srv_qry_mongo.Api.Controllers
{
     public class MongoDBService
    {
        private readonly IMongoCollection<Contact> _collection;

        public MongoDBService(IOptions<MongoDBConnect> configuration)
        {
            string connectionString = configuration.Value.MongoDBConnection;

            var client = new MongoClient(connectionString);

            var database = client.GetDatabase("meteor");
            _collection = database.GetCollection<Contact>("contacts");
        }

        public IEnumerable<Contact> GetAllContacts()
        {
            List<Contact> data = _collection.Find(_ => true).ToList();
            return data;
        }

    }
}