package com.ticketing.config;

import com.ticketing.model.TicketPool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TicketPoolBean {
    @Bean
    public TicketPool ticketPool(com.ticketing.config.Configuration configuration) {
        return new TicketPool(
                configuration.getMaxTicketCapacity(),
                configuration.getTotalTickets()
        );
    }
}