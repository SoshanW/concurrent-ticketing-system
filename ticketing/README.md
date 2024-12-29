# Ticketing System Simulation

A full stack application that simulates a ticket selling system with multiple vendors and customers. The system 
demonstrates concurrent ticket transcriptions in a controlled environment

## Features

* Multi-threaded ticket vending simulation
* Real-time monitoring of ticket sales
* Configuration number of vendors and customers
* Adjustable ticket release and purchase rates
* Web-based control interface
* Real-time simulation logs
* Persistent configuration management

## Tech-Stack

### Backend
* Java 11
* Spring Boot 2.7.14
* Maven
* Lombok
* Swagger UI

### Frontend
* React 18
* Vite
* React Bootstrap
* React Router Dom
* Axios
* React Hook Form
* SockJS Client

## Prerequisites

Before you begin, ensure the following are installed:
* Java 11
* Node.js 14 or higher
* npm 6 or higher
* Maven 3.6 or higher

## Installation

### Backend Setup
*The backend server will start on http://localhost:8081

### Frontend Setup
1. Navigate to the frontend directory

    ``` cd frontend```

2. Install Dependencies

   ```npm install```
3. Start the development server

    ```npm run dev```

Access the application at http://localhost:5173


### Backend Configuration
The application uses a config.json file for storing simulation parameters.
Key configuration parameters include
* Total number of tickets
* Ticket release rate
* Customer retrieval rate
* Maximum ticket capacity

# API Documentation

## Configuration Endpoints

###  Configuration Management

```
Base URL: http://localhost:8081/api/config

POST /select-configuration
- Purpose: Select configuration type (new/existing/default)
- Body: { "configurationType": "USE_EXISTING" | "USE_DEFAULT" }
- Returns: Configuration status

POST /save-config
- Purpose: Save new configuration
- Body: {
    "totalTicketNumber": number,
    "ticketReleaseRate": number,
    "customerRetrievalRate": number,
    "maxTicketCapacity": number
  }
- Returns: Saved configuration

GET /get-config-status
- Purpose: Get current configuration status
- Returns: Configuration status object```
```

## Simulation Endpoints
### Simulation Control
```
Base URL: http://localhost:8081/api/simulation

POST /start-simulation
- Purpose: Start the ticket simulation
- Returns: Simulation start status

POST /stop-simulation
- Purpose: Stop the running simulation
- Returns: Simulation stop status

POST /set-simulation-number-setting
- Purpose: Configure vendor and customer counts
- Body: {
    "numOfVendors": number,
    "numOfCustomers": number
  }
- Returns: Updated simulation settings
```
### Simulation Monitoring
```
Base URL: http://localhost:8081/api/simulation-logs

GET /recent
- Purpose: Fetch recent simulation logs
- Returns: Array of log entries with timestamps
```
### Ticket Management
```
Base URL: http://localhost:8081/api/ticket

POST /save-ticket
- Purpose: Save ticket details for simulation
- Body: {
    "event": string,
    "price": number
  }
- Returns: Saved ticket details
```
### Vendor Management
```
Base URL: http://localhost:8081/api/vendor

POST /save-vendor
- Purpose: Save vendor configuration
- Body: Vendor details object
- Returns: Saved vendor configuration
```

### Customer Management
```
Base URL: http://localhost:8081/api/customer

POST /save-customer
- Purpose: Save customer configuration
- Body: Customer details object
- Returns: Saved customer configuration
```


## Acknowledgements 
* Spring Framework documentation
* React Documentation
* Java Concurrency in Practice book
* University of Westminster Lecture Slides