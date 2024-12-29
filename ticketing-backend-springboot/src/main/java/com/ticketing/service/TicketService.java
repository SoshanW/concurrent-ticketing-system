package com.ticketing.service;

import com.ticketing.dto.request.TicketSaveRequestDTO;

public interface TicketService {
    String saveTicket(TicketSaveRequestDTO ticketSaveRequestDTO);
}
