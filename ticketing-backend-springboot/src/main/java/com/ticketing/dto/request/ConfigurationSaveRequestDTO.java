package com.ticketing.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ConfigurationSaveRequestDTO {
    private int totalTicketNumber;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;
}
