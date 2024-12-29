package com.ticketing.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SimulationLogDTO {

    private String timestamp;
    private String threadName;
    private String message;
    private LogType logType;

    public enum LogType {
        VENDOR_PRODUCTION,
        CUSTOMER_PURCHASE,
        TICKET_POOL_STATUS,
        SIMULATION_EVENT
    }
}
