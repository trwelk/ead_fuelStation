using FuelStationBackend.Models;
using Microsoft.Extensions.Options;
using MongoDB.Driver;
using MongoDB.Bson;

namespace FuelStationBackend.Services;

public class UserService
{

    private readonly IMongoCollection<User> _userCollection;

    public UserService(IOptions<MongoDBSettings> mongoDBSettings)
    {
        MongoClient client = new MongoClient(mongoDBSettings.Value.ConnectionURI);
        IMongoDatabase database = client.GetDatabase(mongoDBSettings.Value.DatabaseName);
        _userCollection = database.GetCollection<User>(mongoDBSettings.Value.UserCollection);
    }

    public async Task<List<User>> GetAsync()
    {
        return await _userCollection.Find(new BsonDocument()).ToListAsync();
    }
    public async Task CreateAsync(User user)
    {
        await _userCollection.InsertOneAsync(user);
        return;
    }

        public async Task<List<User>> Login(User user)
    {
        FilterDefinition<User> filter = Builders<User>.Filter.Where(x => x.email == user.email && x.password == user.password);
        return await _userCollection.Find(filter).ToListAsync();
    }
    public async Task DeleteAsync(string id)
    {
        FilterDefinition<User> filter = Builders<User>.Filter.Eq("Id", id);
        await _userCollection.DeleteOneAsync(filter);
        return;
    }

         public async Task<List<User>> Update(User user)
    {
        FilterDefinition<User> filter = Builders<User>.Filter.Where(x => x.email == user.email);
        var updateResult = await _userCollection.ReplaceOneAsync(filter, user);
        var updatedId = updateResult.UpsertedId;
        FilterDefinition<User> filterUpdated = Builders<User>.Filter.Eq("Id", updatedId);
        return await _userCollection.Find(filterUpdated).ToListAsync();
    }
}