package com.ticketing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Ticket {
    private int ticketId;
    private String event;
    private BigDecimal price;
}
