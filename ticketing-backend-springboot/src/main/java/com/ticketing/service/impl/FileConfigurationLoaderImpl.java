package com.ticketing.service.impl;

import com.google.gson.Gson;
import com.ticketing.config.Configuration;
import com.ticketing.dto.request.ConfigurationSaveRequestDTO;
import com.ticketing.service.FileConfigurationLoader;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class FileConfigurationLoaderImpl implements FileConfigurationLoader {

    @Override
    public String saveConfig(ConfigurationSaveRequestDTO configurationSaveRequestDTO) {
        Configuration configuration = new Configuration(
                configurationSaveRequestDTO.getTotalTicketNumber(),
                configurationSaveRequestDTO.getTicketReleaseRate(),
                configurationSaveRequestDTO.getCustomerRetrievalRate(),
                configurationSaveRequestDTO.getMaxTicketCapacity()
        );

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("config.json"))) {
            Gson gson = new Gson();
            gson.toJson(configuration, writer);
            return "Configuration Saved Successfully";
        } catch (IOException e) {
            throw new RuntimeException("Failed to save configuration", e);
        }
    }
}
