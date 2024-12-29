package com.ticketing.service.impl;

import com.ticketing.dto.response.SimulationLogDTO;
import org.apache.catalina.LifecycleState;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class SimulationLogService {
    private final List<SimulationLogDTO> logs = new CopyOnWriteArrayList<>();
    private static final int MAX_LOGS = 100;

    public void addLog(String threadName, String message, SimulationLogDTO.LogType logType) {
        SimulationLogDTO logDTO = new SimulationLogDTO(
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                threadName,
                message,
                logType
        );

        // Remove oldest logs if exceeding max
        if (logs.size() >= MAX_LOGS) {
            logs.remove(0);
        }
        logs.add(logDTO);
    }

    public List<SimulationLogDTO> getRecentLogs() {
        return new ArrayList<>(logs);
    }

    public void clearLogs() {
        logs.clear();
    }
}
