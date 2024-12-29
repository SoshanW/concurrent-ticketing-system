package com.ticketing.model;

import com.ticketing.config.Configuration;
import com.ticketing.dto.response.SimulationLogDTO;
import com.ticketing.service.PollLogService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Vendor implements Runnable {
    private int vendorId;
    private int ticketPerRelease;
    private int releaseInterval;
    private TicketPool ticketPoolService;
    private Configuration configuration;
    private PollLogService logService;

    @Override
    public void run() {
        try {
            logService.sendSimulationLog(
                    Thread.currentThread().getName(),
                    "Vendor started ticket production",
                    SimulationLogDTO.LogType.SIMULATION_EVENT
            );

            for (int i = 0; i < configuration.getMaxTicketCapacity() &&
                    !Thread.currentThread().isInterrupted() &&
                    ticketPoolService.getTotalTicketCount() < configuration.getTotalTickets(); i++) {

                for (int j = 0; j < ticketPerRelease; j++) {
                    // Create a new unique ticket for each addition
                    Ticket ticket = new Ticket();
                    ticket.setTicketId(ticketPoolService.getTotalTicketCount() + 1); // Unique ID
                    ticket.setEvent("Event-" + ticket.getTicketId()); // Example event name
                    ticket.setPrice(BigDecimal.valueOf(100)); // Example price

                    ticketPoolService.addTicket(ticket);
                }

                // Log the size of the pool after adding tickets
                int currentPoolSize = ticketPoolService.getQueueSize(); // Get the updated size
                logService.sendSimulationLog(
                        Thread.currentThread().getName(),
                        String.format("Added %d Tickets to the pool. Pool size: %d",
                                ticketPerRelease,
                                currentPoolSize),
                        SimulationLogDTO.LogType.VENDOR_PRODUCTION
                );

                System.out.println(Thread.currentThread().getName() + ": has added " +
                        ticketPerRelease + " Tickets to the pool. Size of the TicketPool: " +
                        currentPoolSize);

                try {
                    Thread.sleep(releaseInterval * 1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Restore interrupted status
                    break; // Exit the loop if interrupted
                }
            }
        } finally {
            System.out.println(Thread.currentThread().getName() + ": Stopped Producing Tickets");
            logService.sendSimulationLog(
                    Thread.currentThread().getName(),
                    "Stopped Producing Tickets",
                    SimulationLogDTO.LogType.SIMULATION_EVENT
            );
        }
    }
}