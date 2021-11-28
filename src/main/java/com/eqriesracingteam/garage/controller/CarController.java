package com.eqriesracingteam.garage.controller;

import com.eqriesracingteam.garage.model.Car;
import com.eqriesracingteam.garage.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class CarController {

    //Attributes
    @Autowired
    private CarService carService;

    //Constructor

    //CRUD Requests
    //Post request
    @PostMapping(value = "/api/garage/cars")
    public ResponseEntity<Object> addCar(@RequestBody Car car){
        long newId = carService.addCar(car);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/id").buildAndExpand(newId).toUri();
        return ResponseEntity.created(location).build();
    }

    //Get requests
    //Get all cars
    @GetMapping(value = "/api/garage/cars")
    public ResponseEntity<Object> getAllCars(){
        return ResponseEntity.ok(carService.getAllCustomers());
    }
}
