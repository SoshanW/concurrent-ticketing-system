package com.ticketing.model;

import com.ticketing.dto.response.SimulationLogDTO;
import com.ticketing.service.PollLogService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Random;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Customer implements Runnable {
    private TicketPool ticketPoolService;
    private int customerId;
    private int retrievalInterval;
    private int quantity;
    private PollLogService logService;

    private final Random random = new Random();

    @Override
    public void run() {

        logService.sendSimulationLog(
                Thread.currentThread().getName(),
                "Customer started ticket purchasing",
                SimulationLogDTO.LogType.SIMULATION_EVENT
        );

        while (!Thread.currentThread().isInterrupted()) {

            //Check if vendors have stopped and no tickets are left
            if (ticketPoolService.isVendorProductionStopped()&& ticketPoolService.getQueueSize()==0){
                System.out.println(Thread.currentThread().getName()+" No more tickets available. Stopping !!");
                break;
            }

            int numbOfTicketsToBuy = random.nextInt(quantity)+1;
            int ticketsBought = 0;

            //Use synchronization block to ensure atomic operation
            synchronized (ticketPoolService) {
                //Try to buy ticket
                for (int i = 0; i < numbOfTicketsToBuy; i++) {
                    Ticket ticket = ticketPoolService.removeTicket();
                    if (ticket == null){
                        break;
                    }
                    ticketsBought++;
                }

                int currentPoolSize = ticketPoolService.getQueueSize();
                System.out.println(Thread.currentThread().getName() + ": Bought " +ticketsBought+" tickets. Pool size: " + currentPoolSize);

                //Send WebSocket log
                logService.sendSimulationLog(
                        Thread.currentThread().getName(),
                        String.format("Bought %d tickets. Pool size: %d",
                                ticketsBought,
                                currentPoolSize),
                        SimulationLogDTO.LogType.CUSTOMER_PURCHASE
                );
            }

            // If no tickets could be bought and vendors have stopped exit
            if (ticketsBought==0 && ticketPoolService.isVendorProductionStopped()){
                break;
            }

            try {
                Thread.sleep(retrievalInterval * 1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore interrupted status
                break; // Exit the loop if interrupted
            }
        }

        logService.sendSimulationLog(
                Thread.currentThread().getName(),
                "Stopped purchasing tickets",
                SimulationLogDTO.LogType.SIMULATION_EVENT
        );
    }
}
