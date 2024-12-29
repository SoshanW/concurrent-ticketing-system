import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';

import ConfigurationSelection from './components/ConfigurationSelection';
import NewConfigurationForm from './components/NewConfigurationForm';
import TicketDetailsForm from './components/TicketDetailsForm';
import SimulationControl from './components/SimulationControl';
import VendorCustomerInputForm from './components/VendorCustomerInputForm';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<ConfigurationSelection />} />
        <Route path="/new-config" element={<NewConfigurationForm />} />
        <Route path="/ticket-details" element={<TicketDetailsForm />} />
        <Route path="/vendor-customer-input" element={<VendorCustomerInputForm />} />
        <Route path="/simulation-control" element={<SimulationControl />} />
      </Routes>
    </Router>
  );
}

export default App;