# Digital Banking Backend

## Description
This is a Spring Boot application that provides a RESTful API for a digital banking system. It allows for managing customers, bank accounts (current and savings), and banking operations (credit, debit, transfer).

## Technologies Used
- Java 17
- Spring Boot 3.4.5
- Spring Data JPA
- Spring Security with JWT Authentication
- MySQL Database
- Lombok
- SpringDoc OpenAPI for API Documentation
- Spring Boot Actuator

## Features
- Customer Management (CRUD operations)
- Bank Account Management (Current and Savings accounts)
- Banking Operations (Credit, Debit, Transfer)
- Account History and Transaction Tracking
- JWT-based Authentication and Authorization
- Role-based Access Control (USER and ADMIN roles)

## Prerequisites
- Java 17 or higher
- MySQL Server
- Maven

## Setup and Installation
1. Clone the repository
2. Configure the database connection in `src/main/resources/application.properties`
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/digital_banking?createDatabaseIfNotExist=true
   spring.datasource.username=root
   spring.datasource.password=your_password
   ```
3. Build the project using Maven:
   ```bash
   mvn clean install
   ```
4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

## API Documentation
The API documentation is available via SpringDoc OpenAPI. After starting the application, you can access it at:
- http://localhost:8080/swagger-ui.html

## Security
The application uses JWT (JSON Web Token) for authentication and authorization:
- Default users:
  - Username: admin, Password: 12345, Roles: ADMIN, USER
  - Username: user, Password: 12345, Role: USER
- Authentication endpoint: POST /auth/login
- Protected endpoints require a valid JWT token
- Role-based access control:
  - USER role: Can view customers, accounts, and operations
  - ADMIN role: Can create, update, and delete customers

## API Endpoints

### Authentication
- POST /auth/login - Authenticate and get JWT token
- GET /auth/profile - Get authenticated user profile

### Customers
- GET /customers/list_customer - List all customers (USER role)
- GET /customers/customer/{id} - Get customer by ID (USER role)
- POST /customers/save - Create a new customer (ADMIN role)
- PUT /customers/update/{id} - Update a customer (ADMIN role)
- DELETE /customers/delete/{id} - Delete a customer (ADMIN role)
- GET /customers/search_customer?keyword=... - Search for customers (USER role)

### Bank Accounts
- GET /bank_accounts/bank_account/{id} - Get bank account by ID
- GET /bank_accounts/list_bank_accounts - List all bank accounts
- GET /bank_accounts/{id}/operations - Get all operations for an account
- GET /bank_accounts/{id}/page_operations - Get paginated operations for an account

## Project Architecture

### Entities
- **Customer**: Represents a bank customer with ID, name, email, and associated bank accounts
  - Uses JPA annotations for ORM mapping
  - Has a one-to-many relationship with BankAccount entities
  - Fields: id (UUID), name, email, bankAccounts (List)

- **BankAccount**: Abstract class representing a bank account
  - Uses single table inheritance strategy with discriminator column "account_type"
  - Fields: id (UUID), createdAt, balance, status (enum), currency, customer (ManyToOne), operations (OneToMany)

- **CurrentAccount**: Extends BankAccount with overdraft facility
  - Discriminator value: "CA"
  - Additional field: overDraft (double)

- **SavingAccount**: Extends BankAccount with interest rate
  - Discriminator value: "SA"
  - Additional field: interestRate (double)

- **Operation**: Represents a banking operation
  - Fields: id (Long), date, amount, type (CREDIT/DEBIT enum), bankAccount (ManyToOne)

### DTOs (Data Transfer Objects)
- **CustomerDTO**: Simplified representation of Customer entity
  - Fields: id, name, email

- **BankAccountDTO**: Abstract base class for bank account DTOs
  - Field: type

- **CurrentAccountDTO**: DTO for CurrentAccount
  - Fields: id, createdAt, balance, status, customerDTO, overDraft

- **SavingAccountDTO**: DTO for SavingAccount
  - Fields: id, createdAt, balance, status, customerDTO, interestRate

- **OperationDTO**: DTO for Operation
  - Fields: id, date, amount, type

- **AccountHistoryDTO**: DTO for paginated account operations
  - Fields: accountId, currentPage, totalPages, pageSize, operationsDTO (List)

### Mappers
- **BankAccountMapper**: Interface for converting between entities and DTOs
  - Methods for converting Customer, SavingAccount, CurrentAccount, and Operation entities to their respective DTOs and vice versa

### Services
- **BankAccountService**: Interface defining the service layer methods
  - Customer management: save, get, delete, update, list, search
  - Bank account management: save current account, save saving account, list, get
  - Banking operations: debit, credit, transfer, account history

### Repositories
- **CustomerRepository**: JPA repository for Customer entity
  - Extends JpaRepository<Customer, String>
  - Custom method: search(String keyword) - searches customers by name, email, or ID

- **BankAccountRepository**: JPA repository for BankAccount entity
  - Extends JpaRepository<BankAccount, String>

- **OperationRepository**: JPA repository for Operation entity
  - Extends JpaRepository<Operation, Long>
  - Custom methods: findByBankAccountId(String) and findByBankAccountId(String, Pageable)

### Controllers
- **CustomerRestController**: REST controller for customer-related endpoints
  - Secured with role-based access control
  - Endpoints for listing, getting, saving, updating, deleting, and searching customers

- **BankAccountRestController**: REST controller for bank account-related endpoints
  - Endpoints for getting a bank account, listing bank accounts, and retrieving account operations

- **SecurityController**: REST controller for authentication
  - Endpoints for login (generates JWT token) and retrieving user profile
