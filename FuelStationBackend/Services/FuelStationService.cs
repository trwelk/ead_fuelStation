using FuelStationBackend.Models;
using Microsoft.Extensions.Options;
using MongoDB.Driver;
using MongoDB.Bson;

namespace FuelStationBackend.Services;

public class FuelStationService
{

    private readonly IMongoCollection<FuelStation> _fuelStationCollection;

    public FuelStationService(IOptions<MongoDBSettings> mongoDBSettings)
    {
        MongoClient client = new MongoClient(mongoDBSettings.Value.ConnectionURI);
        IMongoDatabase database = client.GetDatabase(mongoDBSettings.Value.DatabaseName);
        _fuelStationCollection = database.GetCollection<FuelStation>(mongoDBSettings.Value.FuelStationCollection);
    }

    public async Task<List<FuelStation>> GetAsync()
    {
        return await _fuelStationCollection.Find(new BsonDocument()).ToListAsync();
    }

    
    public async Task<List<FuelStation>> GetByUserIdAsync(string id)
    {
        Console.WriteLine("User Id " + id);

        FilterDefinition<FuelStation> filter = Builders<FuelStation>.Filter.Eq("userId", id);

        return await _fuelStationCollection.Find(filter).ToListAsync();
    }

    public async Task CreateAsync(FuelStation fuelStation)
    {
        await _fuelStationCollection.InsertOneAsync(fuelStation);
        return;
    }
    public async Task DeleteAsync(string id)
    {
        FilterDefinition<FuelStation> filter = Builders<FuelStation>.Filter.Eq("Id", id);
        await _fuelStationCollection.DeleteOneAsync(filter);
        return;
    }

    public async Task AddToStock(string id, Fuel fuelStock)
    {
        Console.WriteLine("Current Date and time is : " + fuelStock.fuelType);
        FilterDefinition<FuelStation> filter = Builders<FuelStation>.Filter.Eq("Id", id);
        UpdateDefinition<FuelStation> update = Builders<FuelStation>.Update.AddToSet<Fuel>("fuelStock", fuelStock);
        await _fuelStationCollection.UpdateOneAsync(filter, update);
        return;
    }


    public async Task AddUserToQueue(string id, String fuelId, UserQueue userQueue)
    {
        // FilterDefinition<FuelStation> filter = Builders<FuelStation>.Filter.Where(x => x.Id == id && x.fuelTypes.Any(i => i.Id == fuelId));
        // UpdateDefinition<FuelStation> update = Builders<FuelStation>.Update.AddToSet<UserQueue>("usersInQueue", userQueue);
        // await _fuelStationCollection.UpdateOneAsync(filter, update);

        var filter = Builders<FuelStation>.Filter.And(
         Builders<FuelStation>.Filter.Where(x => x.Id == id),
         Builders<FuelStation>.Filter.Eq("fuelTypes.Id", fuelId));

        var update = Builders<FuelStation>.Update.Push("fuelTypes.$.usersInQueue", userQueue);
        await _fuelStationCollection.FindOneAndUpdateAsync(filter, update);
        return;
    }

    public async Task AddFuelType(string id, Fuel fuel)
    {
        FilterDefinition<FuelStation> filter = Builders<FuelStation>.Filter.Where(x => x.Id == id);
        UpdateDefinition<FuelStation> update = Builders<FuelStation>.Update.AddToSet<Fuel>("fuelTypes", fuel);
        await _fuelStationCollection.UpdateOneAsync(filter, update);
        return;
    }

    public async Task LeaveQueue(string id, string fuelId, UserQueue userQueue)
    {
        // var filterBuilder = Builders<FuelStation>.Filter;
        // var filter = Builders<FuelStation>.Filter.Where(x => x.Id == id && x.fuelTypes.Any(fuel => fuel.Id == fuelId && fuel.usersInQueue.Any(userInQueue => userInQueue.Id == userQueue.Id)));
        // var update = Builders<FuelStation>.Update.Set(x => x.fuelTypes[-1].usersInQueue[-1].timeLeft, userQueue.timeLeft);
        await _fuelStationCollection.UpdateOneAsync(x => x.Id == id,
            Builders<FuelStation>.Update.Set("fuelTypes.$[g].usersInQueue.$[c].timeLeft", userQueue.timeLeft),
            new UpdateOptions
            {
                ArrayFilters = new List<ArrayFilterDefinition>
                {
            new BsonDocumentArrayFilterDefinition<BsonDocument>(new BsonDocument("g._id", fuelId)),
            new BsonDocumentArrayFilterDefinition<BsonDocument>(new BsonDocument("c._id", userQueue.Id))
                }
            });
        return;
    }

    public async Task<List<FuelStation>> GetUsersInQueueAsync()
    {
        var filter = Builders<BsonDocument>.Filter.Eq("Parameters.Value", 11111);

        return await _fuelStationCollection.Find(new BsonDocument()).ToListAsync();
    }

}