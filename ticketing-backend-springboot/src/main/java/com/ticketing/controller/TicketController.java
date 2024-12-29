package com.ticketing.controller;

import com.ticketing.dto.request.TicketSaveRequestDTO;
import com.ticketing.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping(path = "/save-ticket")
    public String saveTicket(@RequestBody TicketSaveRequestDTO ticketSaveRequestDTO) {
        String message = ticketService.saveTicket(ticketSaveRequestDTO);
        return message;
    }
}
