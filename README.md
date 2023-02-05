# Banking-system
## Description
The following console application is a backend simulation of a banking system. It has:
- factory classes used for creating objects for customer, bank institution, bank account and transaction
- model classes modelling:
  - customer with id, first name, last name and birthdate;
  - bank institution with id, name, address, fees and exchange rates;
  - bank account with id, bank institution name, customer, IBAN, account currency,  available amount, type and transactions made by this account;
  - transaction with id, type, source IBAN, bank name, currency, transferred amount and timestamp;
  - transfer transaction that extends transactions with target IBAN, bank name, currency, additional fees and exchange rate;
- repository classes modelling tables in a database for customers, bank institutions, bank accounts and transactions
- service classes allowing the user to perform operations 
- application class which is the starting point for the program
- validation class containing different methods for validation purposes
- test classes testing the validation and the different operations
## Features
This simulation of a banking system allows user to :
- create customers, bank institutions, bank accounts and transactions
- add, remove and update fees and exchange rates to a bank institution
- make deposit transactions for all types of accounts
- make withdrawals transactions for all types of accounts
- make transfer transactions between two current accounts
- print information about a specific bank 
- print exchange rate information for a specific bank
- print fees information for a specific bank
- print bank accounts information for a specific bank
- print the number of customers for a specific bank
- print information about a specific account
- print information about a specific customer
- print transactions information for a specific bank
- print transactions information for a specific account
- print transactions information for a specific account and time range
## Technologies used
- Java 17
- Maven
- Junit 5