# Coding Task Summary

Design and implement a RESTful API (including data model and the backing implementation) for
money transfers between accounts.

# How to build solution

How to build application using Maven
```
mvn clean install
```

# How to run start Jetty Server

How to start Jetty Server
```
java -jar transfer-0.0.1-SNAPSHOT.jar EmbeddedServer
```

# Test Result Verified
- To Get All Account Data
- To Save Account Data
- To Delete Account Data
- To Transfer amount between two accounts
- To Deposit amount in given account
- To Withdraw amount from given account

Example Rest APIs
```
To Get All Account Data - GET http://localhost:9001/account/getAllAccounts
To Save Account Data - PUT http://localhost:9001/account/saveAccount
To Delete Account Data - DELETE http://localhost:9001/account/deleteAccount/1448830
To Transfer amount between two accounts - POST http://localhost:9001/account/transferAmount/1448830/1448831/200
To Deposit amount in given account - POST http://localhost:9001/account/depositAmount/1448830/200
To Withdraw amount from given account - POST http://localhost:9001/account/withdrawAmount/1448830/200
```
