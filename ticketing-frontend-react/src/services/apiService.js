import axios from 'axios';

const BASE_URL = 'http://localhost:8081/api';

export const configService = {
  selectConfiguration: async (type) => {
    try {
      const response = await axios.post(`${BASE_URL}/config/select-configuration`, { 
        configurationType: type 
      });
      return response.data;
    } catch (error) {
      console.error('Configuration selection error', error);
      throw error;
    }
  },

  saveNewConfiguration: async (config) => {
    try {
      const response = await axios.post(`${BASE_URL}/config/save-config`, config);
      return response.data;
    } catch (error) {
      console.error('Configuration save error', error);
      throw error;
    }
  },

  getConfigStatus: async () => {
    try {
      const response = await axios.get(`${BASE_URL}/config/get-config-status`);
      return response.data;
    } catch (error) {
      console.error('Config status error', error);
      throw error;
    }
  }
};

export const simulationService = {
  setSimulationSettings: async (settings) => {
    try {
      const response = await axios.post(`${BASE_URL}/simulation/set-simulation-number-setting`, settings);
      return response.data;
    } catch (error) {
      console.error('Simulation settings error', error);
      throw error;
    }
  },

  startSimulation: async () => {
    try {
      const response = await axios.post(`${BASE_URL}/simulation/start-simulation`);
      return response.data;
    } catch (error) {
      console.error('Start simulation error', error);
      throw error;
    }
  },

  stopSimulation: async () => {
    try {
      const response = await axios.post(`${BASE_URL}/simulation/stop-simulation`);
      return response.data;
    } catch (error) {
      console.error('Stop simulation error', error);
      throw error;
    }
  },

  getRecentLogs: async () => {
    try {
      const response = await axios.get(`${BASE_URL}/simulation-logs/recent`); 
      return response.data;
    } catch (error) {
      console.error('Error fetching recent logs', error);
      throw error;
    }
  }
};

// Added Ticket Details Service
export const ticketService = {
  saveTicketDetails: async (ticketDetails) => {
    try {
      const response = await axios.post(`${BASE_URL}/ticket/save-ticket`, {
        ...ticketDetails,
        price: parseFloat(ticketDetails.price)
      });
      return response.data;
    } catch (error) {
      console.error('Ticket save error', error);
      throw error;
    }
  }
};

export const vendorCustomerService = {
  saveVendorDetails: async (vendorDetails) => {
    try {
      const response = await axios.post(`${BASE_URL}/vendor/save-vendor`, vendorDetails);
      return response.data;
    } catch (error) {
      console.error('Vendor save error', error);
      throw error;
    }
  },

  saveCustomerDetails: async (customerDetails) => {
    try {
      const response = await axios.post(`${BASE_URL}/customer/save-customer`, customerDetails);
      return response.data;
    } catch (error) {
      console.error('Customer save error', error);
      throw error;
    }
  }
};

