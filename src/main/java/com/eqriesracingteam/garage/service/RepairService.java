package com.eqriesracingteam.garage.service;

import com.eqriesracingteam.garage.exceptions.AppointmentException;
import com.eqriesracingteam.garage.exceptions.RecordNotFoundException;
import com.eqriesracingteam.garage.model.*;
import com.eqriesracingteam.garage.repository.CarRepository;
import com.eqriesracingteam.garage.repository.InventoryRepository;
import com.eqriesracingteam.garage.repository.RepairRepository;
import com.eqriesracingteam.garage.repository.RepairsItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RepairService {

    private RepairRepository repairRepository;
    private CarRepository carRepository;
    private InventoryRepository inventoryRepository;
    private RepairsItemsRepository repairsItemsRepository;


    // Constructor

    @Autowired
    public RepairService(RepairRepository repairRepository, CarRepository carRepository, InventoryRepository inventoryRepository, RepairsItemsRepository repairsItemsRepository) {
        this.repairRepository = repairRepository;
        this.carRepository = carRepository;
        this.inventoryRepository = inventoryRepository;
        this.repairsItemsRepository = repairsItemsRepository;
    }

    // Methods

    public Repair createRepairAppointment(Repair repair, long carId) {
        //        var date = repair.getRepairDateWorkshop();
        var optionalCar = carRepository.findById(carId);

        repair.setAppointmentStatus(AppointmentStatus.REPARATIE_GEPLAND);
        if (optionalCar.isPresent()) {
            var car = optionalCar.get();
            repair.setScheduledCar(car);

            return repairRepository.save(repair);


            //            Repair newRepair = repairRepository.save(repair);
            //
            //            return newRepair;
        }
        throw new AppointmentException("Appointment date with timeslot taken");
    }


    public Repair getOneAppointment(long id) {
        Optional<Repair> optionalRepair = repairRepository.findById(id);

        if (optionalRepair.isPresent()) {
            return optionalRepair.get();
        } else {
            throw new AppointmentException("Could not find repair with that id");
        }
    }

    // TODO: 6-1-2022 request param for date search option or car
    public List<Repair> getAllRepairAppointments() {
        return repairRepository.findAll();
    }

    public Repair adjustRepairAppointment(long id, Repair repair) {
        Optional<Repair> optionalAppointment = repairRepository.findById(id);

        if (optionalAppointment.isPresent()) {
            //            var repair = repairRepository.getById(id);
            Repair existingRepair = optionalAppointment.get();

            existingRepair.setId(existingRepair.getId());
            existingRepair.setAppointmentStatus(repair.getAppointmentStatus());
            existingRepair.setRepairDateWorkshop(repair.getRepairDateWorkshop());
            // TODO: 25-12-2021 add customer and car
            repairRepository.save(existingRepair);
            return existingRepair;

        } else {
            throw new AppointmentException("Appointment with id not found");
        }
    }

    public void deleteRepairAppointment(long id) {
        var existingAppointment = repairRepository.findById(id);

        if (existingAppointment.isPresent()) {
            repairRepository.deleteById(id);
        } else {
            throw new AppointmentException("Appointment not found");
        }
    }


    public void addARepairItem(long id, long repairItemId) {
        Repair repair = getOneAppointment(id);
        Optional<Inventory> optionalInventory = inventoryRepository.findById(repairItemId);

        if (optionalInventory.isPresent()) {
            Inventory inventoryItem = optionalInventory.get();
            if (inventoryItem.getStock() != 0) {
                RepairItems repairItems = new RepairItems();

                repairItems.setRepair(repair);
                repairItems.setInventoryItem(inventoryItem);
                repairsItemsRepository.save(repairItems);

                repair.getRepairItems().add(repairItems);
                repairRepository.save(repair);
                inventoryItem.setStock(-1);
                inventoryRepository.save(inventoryItem);
            }
        } else {
            throw new RecordNotFoundException("No repairItemId with id" + repairItemId );
        }
    }
}
