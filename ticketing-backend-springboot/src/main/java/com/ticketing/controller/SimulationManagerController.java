package com.ticketing.controller;

import com.ticketing.dto.request.SimulationNumberSettingDTO;
import com.ticketing.service.SimulationManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("api/simulation")
public class SimulationManagerController {

    @Autowired
    private SimulationManagerService simulationManagerService;

    @PostMapping("/set-simulation-number-setting")
    public ResponseEntity<String> setSimulationSettings (@RequestBody SimulationNumberSettingDTO simulationNumberSettingDTO){
        simulationManagerService.setNumberOfVendors(simulationNumberSettingDTO.getNumOfVendors());
        simulationManagerService.setNumberOfCustomers(simulationNumberSettingDTO.getNumOfCustomers());
        return ResponseEntity.ok("Simulation settings set");
    }

    @PostMapping("/start-simulation")
    public String startSimulation(){
        simulationManagerService.startSimulation();
        return "Simulation started";
    }

    @PostMapping("/stop-simulation")
    public ResponseEntity<Map<String, Integer>> stopSimulation() {
        int remainingTickets = simulationManagerService.stopSimulation();
        Map<String, Integer> response = new HashMap<>();
        response.put("totalTicketsRemaining", remainingTickets);
        return ResponseEntity.ok(response);
    }

}
