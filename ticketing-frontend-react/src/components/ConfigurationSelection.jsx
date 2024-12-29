import React, { useState } from 'react';
import { Container, Card, Button, Row, Col } from 'react-bootstrap';
import { configService } from '../services/apiService';
import { useNavigate } from 'react-router-dom';

const ConfigurationSelection = () => {
  const navigate = useNavigate();
  const [error, setError] = useState(null);

  const handleConfigSelection = async (type) => {
    try {
      await configService.selectConfiguration(type);
      navigate('/ticket-details');
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <Container className="mt-5">
      <Card>
        <Card.Header>Select Configuration</Card.Header>
        <Card.Body>
          <Row>
            <Col>
              <Button 
                variant="primary" 
                onClick={() => navigate('/new-config')}
                className="w-100 mb-2"
              >
                Create New Configuration
              </Button>
            </Col>
            <Col>
              <Button 
                variant="success" 
                onClick={() => handleConfigSelection('USE_EXISTING')}
                className="w-100 mb-2"
              >
                Load Previous Configuration
              </Button>
            </Col>
            <Col>
              <Button 
                variant="info" 
                onClick={() => handleConfigSelection('USE_DEFAULT')}
                className="w-100 mb-2"
              >
                Use Default Configuration
              </Button>
            </Col>
          </Row>
          {error && <div className="text-danger mt-2">{error}</div>}
        </Card.Body>
      </Card>
    </Container>
  );
};

export default ConfigurationSelection;


