package com.eqriesracingteam.garage.repository;

import com.eqriesracingteam.garage.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
