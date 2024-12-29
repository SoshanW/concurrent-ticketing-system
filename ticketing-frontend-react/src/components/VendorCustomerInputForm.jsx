import React from 'react';
import { useForm } from 'react-hook-form';
import { Container, Form, Button, Card } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { simulationService } from '../services/apiService';

const VendorCustomerInputForm = () => {
  const { register, handleSubmit, formState: { errors } } = useForm();
  const navigate = useNavigate();

  const onSubmit = async (data) => {
    try {
      await simulationService.setSimulationSettings(data);
      navigate('/simulation-control');
    } catch (error) {
      console.error('Error saving simulation settings', error);
    }
  };

  return (
    <Container className="mt-5">
      <Card>
        <Card.Header>Vendor and Customer Settings</Card.Header>
        <Card.Body>
          <Form onSubmit={handleSubmit(onSubmit)}>
            <Form.Group className="mb-3">
              <Form.Label>Number of Vendors (1-10)</Form.Label>
              <Form.Control 
                type="number" 
                {...register('numOfVendors', { 
                  required: 'Number of vendors is required', 
                  min: { value: 1, message: 'Minimum is 1' }, 
                  max: { value: 10, message: 'Maximum is 10' } 
                })} 
              />
              {errors.numOfVendors && <span className="text-danger">{errors.numOfVendors.message}</span>}
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Label>Number of Customers (1-10)</Form.Label>
              <Form.Control 
                type="number" 
                {...register('numOfCustomers', { 
                  required: 'Number of customers is required', 
                  min: { value: 1, message: 'Minimum is 1' }, 
                  max: { value: 10, message: 'Maximum is 10' } 
                })} 
              />
              {errors.numOfCustomers && <span className="text-danger">{errors.numOfCustomers.message}</span>}
            </Form.Group>

            <Button variant="primary" type="submit">
              Save Settings
            </Button>
          </Form>
        </Card.Body>
      </Card>
    </Container>
  );
};

export default VendorCustomerInputForm;