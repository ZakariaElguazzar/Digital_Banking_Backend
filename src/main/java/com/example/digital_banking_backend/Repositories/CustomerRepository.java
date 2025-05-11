package com.example.digital_banking_backend.Repositories;

import com.example.digital_banking_backend.Entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
}
