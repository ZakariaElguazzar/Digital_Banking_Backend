package com.example.digital_banking_backend.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
// This class represents a customer entity in the banking system
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    @Column(unique = true)
    private String email;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<BankAccount> bankAccounts;
}
