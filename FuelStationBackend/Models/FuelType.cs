using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;
using System.Text.Json.Serialization;

namespace FuelStationBackend.Models;

public class FuelType {

   
    public string? id { get; set; } = null!;

    public string type { get; set; } = null!;
    public DateTime? arrivalTime { get; set; } = null;
    public DateTime? finishTime { get; set; } = null;


}