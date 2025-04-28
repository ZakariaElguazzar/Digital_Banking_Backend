package com.example.digital_banking_backend.Entities;

import com.example.digital_banking_backend.Enums.accStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
//@MappedSuperclass
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "account_type",length = 4, discriminatorType = DiscriminatorType.STRING)
public abstract class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String Id;
    private Date createdAt;
    private double balance;
    @Enumerated(EnumType.STRING)
    private accStatus status;
    private String currency;
    @ManyToOne
    private Customer customer;
    @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL ,fetch=FetchType.LAZY)
    private List<Operation> operations;

}
