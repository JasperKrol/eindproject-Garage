package com.eqriesracingteam.garage.repository;

import com.eqriesracingteam.garage.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Iterable<Customer> findAllByLastName(String lastname);

    Iterable<Customer> findAllByPostalCode(String postalCode);

    Optional<Customer> findCustomerByLastNameContainingIgnoreCase(String lastName);

    Customer findByLastName(String lastname);

    List<Customer> findAllByLastNameContainingIgnoreCase(String lastName);
}
