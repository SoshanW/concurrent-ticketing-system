package com.ticketing.service;

public interface SimulationManagerService {
    void startSimulation();

    int stopSimulation();

    void setNumberOfVendors(int numOfVendors);

    void setNumberOfCustomers(int numOfCustomers);
}
