package com.ticketing.service.impl;

import com.ticketing.dto.request.TicketSaveRequestDTO;
import com.ticketing.service.TicketService;
import com.ticketing.model.Ticket;
import org.springframework.stereotype.Service;

@Service
public class TicketServiceImpl implements TicketService {
    @Override
    public String saveTicket(TicketSaveRequestDTO ticketSaveRequestDTO) {

        Ticket ticket = new Ticket(
                ticketSaveRequestDTO.getTicketId(),
                ticketSaveRequestDTO.getEvent(),
                ticketSaveRequestDTO.getPrice()
        );

        return "Ticket Details Saved Successfully";
    }
}
