using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;


namespace srv_getallnumbers
{
    public class Contact
    {
        [BsonId]
        public ObjectId InternalId { get; init;}
        [BsonElement("name")]
        public string? Name { get; set;}
        [BsonElement("number")]
        public string? Number { get; init;}
    }
}