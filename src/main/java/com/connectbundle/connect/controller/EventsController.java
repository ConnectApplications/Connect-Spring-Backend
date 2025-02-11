package com.connectbundle.connect.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.connectbundle.connect.dto.BaseResponse;
import com.connectbundle.connect.dto.EventsDTO.CreateEventDTO;
import com.connectbundle.connect.model.Event;
import com.connectbundle.connect.service.EventService;
import com.connectbundle.connect.service.EventService.EventServiceResponse;

import jakarta.validation.Valid;

@RestController()
@RequestMapping("/api/events")
public class EventsController {

    @Autowired
    EventService eventService;

    // Get all Events
    @GetMapping()
    public ResponseEntity<BaseResponse<List<Event>>> getAllEvents() {
        try {
            EventServiceResponse<List<Event>> allEvents = eventService.getAllEvents();
            if (allEvents.isSuccess()) {
                return BaseResponse.success(allEvents.getData(), allEvents.getMessage(), HttpStatus.OK,
                        allEvents.getData().size());
            } else {
                return BaseResponse.error(allEvents.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Create An Event
    @PostMapping()
    public ResponseEntity<BaseResponse<Event>> createEvent(@Valid @RequestBody CreateEventDTO createEvent) {
        try {
            EventServiceResponse<Event> eventServiceResponse = eventService.createEvent(createEvent);
            if (eventServiceResponse.isSuccess()) {
                return BaseResponse.success(eventServiceResponse.getData(), eventServiceResponse.getMessage(),
                        HttpStatus.CREATED, 1);
            } else {
                return BaseResponse.error(eventServiceResponse.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
