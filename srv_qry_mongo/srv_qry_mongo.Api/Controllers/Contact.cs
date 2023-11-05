using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;

namespace srv_qry_mongo.Api.Controllers
{
    public class Contact
    {
        [BsonId]
        [BsonRepresentation(BsonType.ObjectId)]
        public string? Id { get; set; }

        [BsonElement("0")]
        public ContactInfo? Info { get; set; }
    }

    public class ContactInfo
    {
        [BsonElement("name")]
        public string? Name { get; set; }

        [BsonElement("phone")]
        public string? Phone { get; set; }
    }
}