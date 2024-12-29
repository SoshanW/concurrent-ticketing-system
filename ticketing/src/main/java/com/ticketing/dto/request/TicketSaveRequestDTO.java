package com.ticketing.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TicketSaveRequestDTO {
    private int ticketId;
    private String event;
    private BigDecimal price;
}
