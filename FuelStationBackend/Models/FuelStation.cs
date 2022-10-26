using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;
using System.Text.Json.Serialization;

namespace FuelStationBackend.Models;

public class FuelStation {

    [BsonId]
    [BsonRepresentation(BsonType.ObjectId)]
    public string? Id { get; set; }

    public string? userId { get; set; }
    
    public string name { get; set; } = null!;
    public string email { get; set; } = null!;
    public string password { get; set; } = null!;
    public string address { get; set; } = null!;
    public string? latitude { get; set; } = null;
    public string? longitude { get; set; } = null;
    public string contactNumber { get; set; } = null!;
    
    public List<Fuel> fuelTypes { get; set;} = new List<Fuel>();


}