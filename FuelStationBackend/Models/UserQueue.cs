using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;
using System.Text.Json.Serialization;

namespace FuelStationBackend.Models;

public class UserQueue {

   
    public string? Id { get; set; } = null!;

    public string userId { get; set; } = null!;
    public DateTime? timeArrived { get; set; } = null;
    public DateTime? timeLeft { get; set; } = null;


}