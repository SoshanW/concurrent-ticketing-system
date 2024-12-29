import React from 'react';
import { useForm } from 'react-hook-form';
import { Container, Form, Button, Card } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { ticketService } from '../services/apiService';

const TicketDetailsForm = () => {
  const { register, handleSubmit, formState: { errors } } = useForm();
  const navigate = useNavigate();

  const onSubmit = async (data) => {
    try {
      await ticketService.saveTicketDetails(data);
      navigate('/vendor-customer-input');
    } catch (error) {
      console.error('Ticket save error', error);
    }
  };

  return (
    <Container className="mt-5">
      <Card>
        <Card.Header>Ticket Details</Card.Header>
        <Card.Body>
          <Form onSubmit={handleSubmit(onSubmit)}>
            <Form.Group className="mb-3">
              <Form.Label>Event Name</Form.Label>
              <Form.Control 
                type="text" 
                {...register('event', { 
                  required: 'Event name is required',
                  minLength: {
                    value: 3,
                    message: 'Event name must be at least 3 characters'
                  }
                })} 
              />
              {errors.event && <span className="text-danger">{errors.event.message}</span>}
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Label>Ticket Price</Form.Label>
              <Form.Control 
                type="number" 
                step="0.01"
                {...register('price', { 
                  required: 'Price is required',
                  min: {
                    value: 0.01,
                    message: 'Price must be greater than 0'
                  }
                })} 
              />
              {errors.price && <span className="text-danger">{errors.price.message}</span>}
            </Form.Group>

            <Button variant="primary" type="submit">
              Save Ticket Details
            </Button>
          </Form>
        </Card.Body>
      </Card>
    </Container>
  );
};

export default TicketDetailsForm;