package com.ticketing.config;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Component
public class ConfigurationBean {
    private Configuration currentConfiguration;

    @Bean
    public Configuration configuration() {
        // First, try to load from file
        Configuration fileConfig = loadConfigurationFromFile();

        // If no file config exists, use default
        if (fileConfig == null) {
            fileConfig = createDefaultConfiguration();
        }

        // Store the current configuration
        this.currentConfiguration = fileConfig;

        return this.currentConfiguration;
    }

    // Add a method to update configuration
    public void updateConfiguration(Configuration newConfig) {
        this.currentConfiguration = newConfig;
    }

    // Add a method to get current configuration
    public Configuration getCurrentConfiguration() {
        return this.currentConfiguration != null
                ? this.currentConfiguration
                : createDefaultConfiguration();
    }
    public Configuration loadConfigurationFromFile() {
        File configFile = new File("config.json");
        if (!configFile.exists()) {
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
            Gson gson = new Gson();
            Configuration config = gson.fromJson(reader, Configuration.class);

            return isValidConfiguration(config) ? config : null;
        } catch (IOException e) {
            System.err.println("Error reading configuration file: " + e.getMessage());
            return null;
        }
    }

    public com.ticketing.config.Configuration createDefaultConfiguration() {
        // Provide sensible default values
        return new com.ticketing.config.Configuration(
                100,    // totalTickets
                5,      // ticketReleaseRate
                3,      // customerRetrievalRate
                50      // maxTicketCapacity
        );
    }

    private boolean isValidConfiguration(com.ticketing.config.Configuration config) {
        // Add validation logic to ensure configuration is valid
        return config != null &&
                config.getTotalTickets() > 0 &&
                config.getTicketReleaseRate() > 0 &&
                config.getCustomerRetrievalRate() > 0 &&
                config.getMaxTicketCapacity() > 0;
    }
}