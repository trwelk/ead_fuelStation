using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;
using System.Text.Json.Serialization;

namespace FuelStationBackend.Models;

public class Fuel {

    public string? Id { get; set; }  = null!;

    public string fuelType { get; set; } = null!;
    public bool available { get; set; } = false!;
    public DateTime? arrivalTime { get; set; } = null;
    public DateTime? finishTime { get; set; } = null;

    public List<UserQueue> usersInQueue { get; set;} = new List<UserQueue>();

}