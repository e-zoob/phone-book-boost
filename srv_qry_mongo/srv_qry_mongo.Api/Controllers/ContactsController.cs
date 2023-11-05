using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using System;
using System.Collections.Generic;
using Microsoft.Extensions.Options;


namespace srv_qry_mongo.Api.Controllers
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
}