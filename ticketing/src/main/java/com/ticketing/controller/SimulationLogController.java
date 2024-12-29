package com.ticketing.controller;

import com.ticketing.dto.response.SimulationLogDTO;
import com.ticketing.service.impl.SimulationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/simulation-logs")
public class SimulationLogController {

    @Autowired
    private SimulationLogService simulationLogService;

    @GetMapping("/recent")
    public ResponseEntity<List<SimulationLogDTO>> getRecentLogs() {
        return ResponseEntity.ok(simulationLogService.getRecentLogs());
    }

    @GetMapping("/clear")
    public ResponseEntity<String> clearLogs() {
        simulationLogService.clearLogs();
        return ResponseEntity.ok("Logs cleared");
    }
}
