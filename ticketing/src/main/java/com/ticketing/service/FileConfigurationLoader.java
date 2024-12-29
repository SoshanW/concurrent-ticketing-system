package com.ticketing.service;

import com.ticketing.dto.request.ConfigurationSaveRequestDTO;

public interface FileConfigurationLoader {
    String saveConfig(ConfigurationSaveRequestDTO configurationSaveRequestDTO);
}
