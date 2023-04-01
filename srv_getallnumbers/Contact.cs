using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;


namespace srv_getallnumbers
{
    public class Contact
    {
        [BsonId]
        public ObjectId InternalId { get; init;}
        public string? name { get; init;}
        public string? number { get; init;}
    }
}