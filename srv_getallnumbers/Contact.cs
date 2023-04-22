using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;


namespace srv_getallnumbers
{
    public class Contact
    {
        [BsonId]
        public ObjectId InternalId { get; init;}
        [BsonElement("Name")]
        public string? Name { get; set;}
        [BsonElement("Number")]
        public string? Number { get; init;}
    }
}