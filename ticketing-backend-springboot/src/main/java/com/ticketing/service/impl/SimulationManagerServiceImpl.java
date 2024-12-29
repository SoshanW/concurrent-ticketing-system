package com.ticketing.service.impl;

import com.ticketing.config.Configuration;
import com.ticketing.config.ConfigurationBean;
import com.ticketing.dto.response.SimulationLogDTO;
import com.ticketing.service.SimulationManagerService;
import com.ticketing.service.PollLogService;
import com.ticketing.model.Customer;
import com.ticketing.model.TicketPool;
import com.ticketing.model.Vendor;
import org.springframework.stereotype.Service;


@Service
public class SimulationManagerServiceImpl implements SimulationManagerService {
    private final ConfigurationBean configurationBean;
    private final PollLogService pollLogService;
    private TicketPool ticketPoolImpl;

    private volatile boolean isRunning = false;
    private Thread[] vendorThreads;
    private Thread[] customerThreads;
    private Thread monitorThread;
    private int numberOfVendors = 3;
    private int numberOfCustomers = 6 ;

    public SimulationManagerServiceImpl(ConfigurationBean configurationBean, PollLogService pollLogService, TicketPool ticketPoolImpl) {
        this.configurationBean = configurationBean;
        this.pollLogService = pollLogService;
        this.ticketPoolImpl = ticketPoolImpl;
    }

    @Override
    public void startSimulation() {

        Configuration configuration = configurationBean.getCurrentConfiguration();
        // Ensure we're not already running

        if (isRunning){
            pollLogService.sendSimulationLog(
                    "SimulationManager",
                    "Simulation is already running",
                    SimulationLogDTO.LogType.SIMULATION_EVENT
            );
        }

        if (isRunning) {
            System.out.println("Simulation is already running");
            return;
        }

        // Reset the ticket pool
        ticketPoolImpl = new TicketPool(
                configuration.getMaxTicketCapacity(),
                configuration.getTotalTickets()
        );

        // Set running flag
        isRunning = true;

        //Log Simulation starting point
        pollLogService.sendSimulationLog(
                "SimulationManager",
                "Simulation Started",
                SimulationLogDTO.LogType.SIMULATION_EVENT
        );

        // Create and start vendor threads
        vendorThreads = new Thread[numberOfVendors];
        for (int i = 0; i < numberOfVendors; i++) {
            Vendor vendor = new Vendor(i, configuration.getTicketReleaseRate(), 2, ticketPoolImpl, configuration, pollLogService);
            vendorThreads[i] = new Thread(vendor, "Vendor-" + i);
            vendorThreads[i].start();
        }

        // Create and start customer threads
        customerThreads = new Thread[numberOfCustomers];
        for (int i = 0; i < numberOfCustomers; i++) {
            Customer customer = new Customer(ticketPoolImpl, i, configuration.getCustomerRetrievalRate(), 4, pollLogService);
            customerThreads[i] = new Thread(customer, "Customer-" + i);
            customerThreads[i].start();
        }

        // Create monitor thread
        monitorThread = new Thread(() -> {
            while (isRunning) {
                // Check if total tickets have been reached and all tickets consumed
                if (ticketPoolImpl.isVendorProductionStopped() &&
                        ticketPoolImpl.getQueueSize() == 0) {
                    stopSimulation();
                    break;
                }

                try {
                    Thread.sleep(1000); // Check every second
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }, "MonitorThread");
        monitorThread.start();
    }

    @Override
    public int stopSimulation() {
        if (!isRunning) {
            return ticketPoolImpl.getQueueSize();
        }

        // Set running flag to false
        isRunning = false;

        // Shutdown ticket pool
        ticketPoolImpl.shutdown();

        // Interrupt vendor and customer threads
        if (vendorThreads != null) {
            for (Thread vendorThread : vendorThreads) {
                if (vendorThread != null) {
                    vendorThread.interrupt();
                }
            }
        }

        if (customerThreads != null) {
            for (Thread customerThread : customerThreads) {
                if (customerThread != null) {
                    customerThread.interrupt();
                }
            }
        }

        // Wait for threads to finish
        try {
            if (vendorThreads != null) {
                for (Thread vendorThread : vendorThreads) {
                    if (vendorThread != null) {
                        vendorThread.join();
                    }
                }
            }

            if (customerThreads != null) {
                for (Thread customerThread : customerThreads) {
                    if (customerThread != null) {
                        customerThread.join();
                    }
                }
            }

                if (monitorThread != null) {
                    monitorThread.join();
                }
            } catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }
        System.out.println("Simulation ended.");
        pollLogService.sendSimulationLog(
                "SimulationManager",
                "Simulation Stopped",
                SimulationLogDTO.LogType.SIMULATION_EVENT
        );

        return ticketPoolImpl.getQueueSize(); //Returning remaining ticket number to the frontend
    }

    @Override
    public void setNumberOfVendors(int numOfVendors) {
        this.numberOfVendors = numOfVendors;
    }

    @Override
    public void setNumberOfCustomers(int numOfCustomers) {
        this.numberOfCustomers = numOfCustomers;
    }


}