# Tiny Bank

## Task description

Implement a set of apis to simulate a Tiny Bank.

From a functional perspective, the following features should be implemented:
* Ability to deposit/withdraw money
* View balance
* View transaction history

Bank should have only 1 account (hence... Tiny Bank). There is no need to segregate
transactions by account nor to include it in the model.

Solution should be **simple**. This means that it is not expected to deliver any of the below:
* authentication/authorisation
* error handling
* logging / monitoring
* transactions/atomic operations
* ...

## Initial state
The Tiny Bank has **one** Account with **zero** balance.

## How to run service

### Requirements for local environment
* Java 21
* Maven

### Run service
From project root directory run the following command
```bash
./mvnw spring-boot:run
```

### Use swagger to call API
Open http://localhost:8080/swagger-ui/index.html in browser

### Use Intellij HTTP Client
Intellij IDEA users can utilize [Tiny Bank Client](./tiny-bank-api.http) to run API calls.
