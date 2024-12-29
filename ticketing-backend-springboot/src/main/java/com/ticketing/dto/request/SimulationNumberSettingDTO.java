package com.ticketing.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SimulationNumberSettingDTO {
    private int numOfVendors;
    private int numOfCustomers;
}
