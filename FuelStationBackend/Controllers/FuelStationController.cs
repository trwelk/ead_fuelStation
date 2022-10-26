using System;
using Microsoft.AspNetCore.Mvc;
using FuelStationBackend.Services;
using FuelStationBackend.Models;

namespace FuelStationBackend.Controllers;

[Controller]
[Route("api/[controller]")]
public class FuelStationController : Controller
{

    private readonly FuelStationService _fuelStationService;

    public FuelStationController(FuelStationService fuelStationService)
    {
        _fuelStationService = fuelStationService;
    }

    [HttpGet]
    public async Task<List<FuelStation>> Get()
    {
        return await _fuelStationService.GetAsync();
    }

   [HttpGet("GetByUser/{id}")]
    public async Task<List<FuelStation>> GetByUserId(string id)
    {
        return await _fuelStationService.GetByUserIdAsync(id);
    }

    [HttpPost]
    public async Task<IActionResult> Post([FromBody] FuelStation fuelStation)
    {
        await _fuelStationService.CreateAsync(fuelStation);
        return CreatedAtAction(nameof(Get), new { id = fuelStation.Id }, fuelStation);
    }


    [HttpDelete("{id}")]
    public async Task<IActionResult> Delete(string id)
    {
        await _fuelStationService.DeleteAsync(id);
        return NoContent();
    }

    [HttpPut("{id}/Stock")]
    public async Task<IActionResult> AddToStock(string id, [FromBody] Fuel fuel)
    {

        await _fuelStationService.AddToStock(id, fuel);
        return NoContent();
    }

    [HttpPost("{id}/FuelType/{fId}/VehiclesInQueue")]
    public async Task<IActionResult> AddUserToQueue(string id, string fId, [FromBody] UserQueue userQueue)
    {
        Console.WriteLine("userid " + userQueue.userId);
        Console.WriteLine("arrived" + userQueue.timeArrived);

     await _fuelStationService.AddUserToQueue(id, fId, userQueue);
     return  NoContent();
         
    }

    

    [HttpPost("{id}/FuelType")]
    public async Task<IActionResult> AddFuelType(string id, [FromBody] Fuel fuel)
    {
        Console.WriteLine("type " + fuel.fuelType);
        Console.WriteLine("arrived" + fuel.available);

     await _fuelStationService.AddFuelType(id, fuel);
    return CreatedAtAction(nameof(Get), new { id = fuel.Id }, fuel);
;
         
    }

    [HttpPatch("{id}/FuelType/{fId}/VehiclesInQueue")]
    public async Task<IActionResult> LeaveQueue(string id, string fId, [FromBody] UserQueue userQueue)
    {

        await _fuelStationService.LeaveQueue(id, fId, userQueue);
        return NoContent();
    }

}