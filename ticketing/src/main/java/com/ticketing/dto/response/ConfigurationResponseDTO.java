package com.ticketing.dto.response;

import com.ticketing.config.Configuration;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfigurationResponseDTO {
    private boolean configExists;
    private Configuration existingConfig;
    private Configuration defaultConfig;
}