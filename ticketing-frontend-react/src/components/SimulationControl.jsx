import React, { useState, useEffect } from 'react';
import { Container, Card, Button, ListGroup } from 'react-bootstrap';
import { simulationService } from '../services/apiService';

const SimulationControl = () => {
  const [isSimulationRunning, setIsSimulationRunning] = useState(false);
  const [simulationLogs, setSimulationLogs] = useState([]);
  const [totalTicketsRemaining, setTotalTicketsRemaining] = useState(0); // New state for total tickets remaining

  useEffect(() => {
    let intervalId;

    const fetchLogs = async () => {
      try {
        const response = await simulationService.getRecentLogs();
        setSimulationLogs(response);
      } catch (error) {
        console.error('Error fetching logs', error);
      }
    };

    if (isSimulationRunning) {
      fetchLogs();
      intervalId = setInterval(fetchLogs, 5000);
    }

    return () => clearInterval(intervalId);
  }, [isSimulationRunning]);

  const handleStartSimulation = async () => {
    try {
      await simulationService.startSimulation();
      setIsSimulationRunning(true);
    } catch (error) {
      console.error('Start simulation error', error);
    }
  };

  const handleStopSimulation = async () => {
    try {
      const response = await simulationService.stopSimulation();
      setIsSimulationRunning(false);
      // Assuming the response contains the total tickets remaining
      setTotalTicketsRemaining(response.totalTicketsRemaining); // Update the total tickets remaining
    } catch (error) {
      console.error('Stop simulation error', error);
    }
  };

  const clearLogs = () => {
    setSimulationLogs([]);
  };

  return (
    <Container className="mt-5">
      <Card>
        <Card.Header>Simulation Control</Card.Header>
        <Card.Body>
          <div className="d-flex justify-content-between mb-3">
            <div>
              <Button 
                variant="success" 
                onClick={handleStartSimulation}
                disabled={isSimulationRunning}
              >
                Start Simulation
              </Button>{' '}
              <Button 
                variant="danger" 
                onClick={handleStopSimulation}
                disabled={!isSimulationRunning}
              >
                Stop Simulation
              </Button>
            </div>
            <Button 
              variant="secondary" 
              onClick={clearLogs}
            >
              Clear Logs
            </Button>
          </div>

          <Card>
            <Card.Header>Simulation Logs</Card.Header>
            <ListGroup 
              variant="flush" 
              style={{ 
                maxHeight: '300px', 
                overflowY: 'auto' 
              }}
            >
              {simulationLogs.map((log, index) => (
                <ListGroup.Item key={index}>
                  {log.timestamp ? new Date(log.timestamp).toLocaleString() : ''} - {log.message}
                </ListGroup.Item>
              ))}
            </ListGroup>
          </Card>

          {/* Display total tickets remaining */}
          {!isSimulationRunning && (
            <h1>Total Tickets Remaining: {totalTicketsRemaining}</h1>
          )}
        </Card.Body>
      </Card>
    </Container>
  );
};

export default SimulationControl;