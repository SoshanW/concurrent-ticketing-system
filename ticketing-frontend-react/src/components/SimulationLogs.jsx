import React, { useState, useEffect } from 'react';
import { Client } from '@stomp/stompjs';
import { Card, ListGroup } from 'react-bootstrap';

const SimulationLogs = ({ isRunning }) => {
  const [logs, setLogs] = useState([]);
  const [client, setClient] = useState(null);

  useEffect(() => {
    if (isRunning) {
      const stompClient = new Client({
        brokerURL: 'http://localhost:8081/ws',
        onConnect: () => {
          stompClient.subscribe('/topic/simulation-logs', (message) => {
            const logData = JSON.parse(message.body);
            setLogs(prevLogs => [...prevLogs, logData]);
          });
        },
      });

      stompClient.activate();
      setClient(stompClient);

      return () => {
        if (stompClient) {
          stompClient.deactivate();
        }
      };
    }
  }, [isRunning]);

  useEffect(() => {
    if (!isRunning) {
      if (client) {
        client.deactivate();
        setClient(null);
      }
      setLogs([]);
    }
  }, [isRunning]);

  return (
    <Card className="mt-3">
      <Card.Header>Simulation Logs</Card.Header>
      <Card.Body style={{ maxHeight: '300px', overflowY: 'auto' }}>
        <ListGroup variant="flush">
          {logs.map((log, index) => (
            <ListGroup.Item key={index}>
              [{log.timestamp}] {log.threadName}: {log.message}
            </ListGroup.Item>
          ))}
        </ListGroup>
      </Card.Body>
    </Card>
  );
};

export default SimulationLogs;
