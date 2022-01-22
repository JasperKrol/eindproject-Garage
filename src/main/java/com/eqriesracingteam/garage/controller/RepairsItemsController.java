package com.eqriesracingteam.garage.controller;

import com.eqriesracingteam.garage.dto.RepairItemsDto;
import com.eqriesracingteam.garage.model.RepairItems;
import com.eqriesracingteam.garage.model.RepairsItemsKey;
import com.eqriesracingteam.garage.service.RepairsItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/garage")
public class RepairsItemsController {

    @Autowired
    private RepairsItemsService repairsItemsService;

    @GetMapping(value = "/repairs_items")
    public ResponseEntity<Object> getAllRepairsWithItems() {
        List<RepairItems> repairItems = repairsItemsService.getAllRepairsWithItems();
        return ResponseEntity.ok(repairItems);
    }

    @PostMapping(value = "/repairs_items/{repair_id}/{inventory_id}")
    public ResponseEntity<Object> createRepairWithItems(@PathVariable("repair_id") Long repairId, @PathVariable("inventory_id") Long inventoryId, @RequestBody RepairItems repairItems) {

        int quantity = repairItems.getAmount();

        RepairsItemsKey ID = repairsItemsService.addRepairsItems(repairId, inventoryId, quantity);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();

        return ResponseEntity.created(location).body(ID);
    }

    @DeleteMapping(value = "/repairs_items/{repair_id}/{inventoryId}")
    public ResponseEntity<Object> deleteById(@PathVariable("repair_id") long repair_id, @PathVariable("inventoryId") long inventoryId) {
        repairsItemsService.deleteById(repair_id, inventoryId);
        return new ResponseEntity<>("Repair with items deleted", HttpStatus.OK);
    }

    //    @GetMapping(value = "/repairs_items/{inventoryId}/repairs")
    //    public ResponseEntity<Object> getJobPartsByPartId(@PathVariable("inventoryId") Long inventoryId){
    //        Collection<RepairItems> repairItemsCollection =  repairsItemsService.getJobPartsByPartId(inventoryId);
    //        List<RepairItemsDto> dtos = new ArrayList<>();
    //        for(RepairItems repairItem : repairItemsCollection){
    //            dtos.add(RepairItemsDto.fromRepairItems(repairItem));
    //        }
    //        return ResponseEntity.ok().body(dtos);
    //    }
}
