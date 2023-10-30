using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using MongoDB.Driver;
using System;
using System.Collections.Generic;
using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;
using Microsoft.Extensions.Options;

namespace ContactsController.Controllers
{
    [Route("api/contacts")]
    [ApiController]
    public class ContactsController : ControllerBase
    {
        private readonly MongoDBService _mongoDBService;
        public ContactsController(MongoDBService mongoDBService)
        {
            _mongoDBService = mongoDBService;
        }

        [HttpGet]
        public IActionResult GetMongoContacts()
        {
            var contacts_data = _mongoDBService.GetAllContacts();
            return Ok(contacts_data);
        }
    }

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

    public class Contact
    {
        [BsonId]
        [BsonRepresentation(BsonType.ObjectId)]
        public string? Id { get; set; }

        [BsonElement("name")]
        public string? Name { get; set; }

        [BsonElement("phone")]
        public string? Phone { get; set; }

    }

}