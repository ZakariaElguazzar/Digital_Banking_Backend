package com.example.digital_banking_backend.DTOs;

import lombok.Data;

import java.util.List;
@Data
public class AccountHistoryDTO {
    private String accountId;
    private int currentPage;
    private int totalPages;
    private int pageSize;
    private List<OperationDTO> operationsDTO;
}
