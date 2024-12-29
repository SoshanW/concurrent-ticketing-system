package com.ticketing.dto.request;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConfigurationSelectRequestDTO {

    private ConfigurationType configurationType;

    public enum ConfigurationType {
        USE_EXISTING,
        USE_DEFAULT,
        USE_NEW
    }
}
