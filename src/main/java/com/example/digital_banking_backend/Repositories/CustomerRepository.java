package com.example.digital_banking_backend.Repositories;
import com.example.digital_banking_backend.Entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    //List<Customer> findByNameContainsOrEmailContainsOrId(String nameKeyword,String emailKeyword,String idKeyword);
    @Query("SELECT c FROM Customer c WHERE c.name LIKE %:kw% OR c.email LIKE %:kw% OR c.id = :kw")
    List<Customer> search(@Param("kw") String keyword);

}
