package com.ticketing.controller;

import com.ticketing.config.Configuration;
import com.ticketing.config.ConfigurationBean;
import com.ticketing.dto.request.ConfigurationSaveRequestDTO;
import com.ticketing.dto.request.ConfigurationSelectRequestDTO;
import com.ticketing.dto.response.ConfigurationResponseDTO;
import com.ticketing.service.FileConfigurationLoader;
import io.swagger.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/config")
public class ConfigurationController {

    @Autowired
    private ConfigurationBean configurationBean;

    @Autowired
    private FileConfigurationLoader fileConfigurationLoader;

    @PostMapping(path = "/save-config")
    public String saveConfig(@RequestBody ConfigurationSaveRequestDTO configurationSaveRequestDTO) {
        // Save to file
        String message = fileConfigurationLoader.saveConfig(configurationSaveRequestDTO);

        // Create Configuration object and update in bean
        Configuration newConfig = new Configuration(
                configurationSaveRequestDTO.getTotalTicketNumber(),
                configurationSaveRequestDTO.getTicketReleaseRate(),
                configurationSaveRequestDTO.getCustomerRetrievalRate(),
                configurationSaveRequestDTO.getMaxTicketCapacity()
        );

        // Update the configuration in the bean
        configurationBean.updateConfiguration(newConfig);

        return message;
    }
    @GetMapping("/get-config-status")
    public ConfigurationResponseDTO getConfigStatus() {
        Configuration existingConfig = configurationBean.loadConfigurationFromFile();
        Configuration defaultConfig = configurationBean.createDefaultConfiguration();
        Configuration currentConfig = configurationBean.getCurrentConfiguration();

        return new ConfigurationResponseDTO(
                existingConfig != null,
                currentConfig,  // Use current configuration
                defaultConfig
        );
    }

    @PostMapping("/select-configuration")
    public ResponseEntity<String> selectConfiguration(@RequestBody ConfigurationSelectRequestDTO configurationSelectRequestDTO) {
        switch (configurationSelectRequestDTO.getConfigurationType()){
            case USE_EXISTING:
                Configuration existingConfig = configurationBean.loadConfigurationFromFile();
                if (existingConfig != null){
                    configurationBean.updateConfiguration(existingConfig);
                    return ResponseEntity.ok("Using existing configuration");
                }
                return ResponseEntity.badRequest().body("No existing configuration found");

            case USE_DEFAULT:
                Configuration defaultConfig = configurationBean.createDefaultConfiguration();
                configurationBean.updateConfiguration(defaultConfig);
                return ResponseEntity.ok("Using default configuration");

            case USE_NEW:
                Configuration currentConfig = configurationBean.getCurrentConfiguration();
                return ResponseEntity.ok("Using new configuration");

            default:
                return ResponseEntity.badRequest().body("Invalid configuration type");
        }
    }

}
