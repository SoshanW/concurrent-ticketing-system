package com.ticketing.service;

import com.ticketing.dto.response.SimulationLogDTO;
import com.ticketing.service.impl.SimulationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PollLogService {

    @Autowired
    private SimulationLogService simulationLogService;

    public void sendSimulationLog(String threadName, String message, SimulationLogDTO.LogType logType) {
        simulationLogService.addLog(threadName, message, logType);
    }

}
