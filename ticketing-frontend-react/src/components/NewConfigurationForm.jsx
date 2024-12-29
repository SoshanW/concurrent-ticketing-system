import React from 'react';
import { useForm } from 'react-hook-form';
import { Container, Form, Button, Card } from 'react-bootstrap';
import { configService } from '../services/apiService';
import { useNavigate } from 'react-router-dom';

const NewConfigurationForm = () => {
  const { register, handleSubmit, formState: { errors } } = useForm();
  const navigate = useNavigate();

  const onSubmit = async (data) => {
    try {
      await configService.saveNewConfiguration(data);
      navigate('/ticket-details');
    } catch (error) {
      console.error('Configuration save error', error);
    }
  };

  return (
    <Container className="mt-5">
      <Card>
        <Card.Header>New Configuration</Card.Header>
        <Card.Body>
          <Form onSubmit={handleSubmit(onSubmit)}>
            <Form.Group className="mb-3">
              <Form.Label>Total Ticket Number</Form.Label>
              <Form.Control 
                type="number" 
                {...register('totalTicketNumber', { 
                  required: true, 
                  min: 10, 
                  max: 10000 
                })} 
              />
              {errors.totalTicketNumber && <span className="text-danger">Invalid ticket number</span>}
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Label>Ticket Release Rate (1-10)</Form.Label>
              <Form.Control 
                type="number" 
                {...register('ticketReleaseRate', { 
                  required: true, 
                  min: 1, 
                  max: 10 
                })} 
              />
              {errors.ticketReleaseRate && <span className="text-danger">Invalid release rate</span>}
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Label>Customer Retrieval Rate (1-10)</Form.Label>
              <Form.Control 
                type="number" 
                {...register('customerRetrievalRate', { 
                  required: true, 
                  min: 1, 
                  max: 10 
                })} 
              />
              {errors.customerRetrievalRate && <span className="text-danger">Invalid retrieval rate</span>}
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Label>Max Ticket Capacity</Form.Label>
              <Form.Control 
                type="number" 
                {...register('maxTicketCapacity', { 
                  required: true, 
                  min: 10, 
                  max: 10000 
                })} 
              />
              {errors.maxTicketCapacity && <span className="text-danger">Invalid ticket capacity</span>}
            </Form.Group>

            <Button variant="primary" type="submit">
              Save Configuration
            </Button>
          </Form>
        </Card.Body>
      </Card>
    </Container>
  );
};

export default NewConfigurationForm;