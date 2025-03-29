package com.connectbundle.connect.controller;

import com.connectbundle.connect.dto.BaseResponse;
import com.connectbundle.connect.dto.EventsDTO.CreateEventDTO;
import com.connectbundle.connect.dto.EventsDTO.EventResponseDTO;
import com.connectbundle.connect.service.EventService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@Tag(name = "Events", description = "Events Endpoints")
public class EventController {
    @Autowired
    private  EventService eventService;

    @GetMapping
    public ResponseEntity<BaseResponse<List<EventResponseDTO>>> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<EventResponseDTO>> getEventById(@PathVariable Long id) {
        return eventService.getEventById(id);
    }


    @PostMapping
    public ResponseEntity<BaseResponse<EventResponseDTO>> createEvent(@RequestBody @Valid CreateEventDTO dto) {
        return eventService.createEvent(dto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BaseResponse<EventResponseDTO>> updateEvent(@PathVariable Long id, @RequestBody CreateEventDTO dto) {
        return eventService.updateEvent(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deleteEvent(@PathVariable Long id) {
        return eventService.deleteEvent(id);
    }

    @GetMapping("/club/{clubId}")
    public ResponseEntity<BaseResponse<List<EventResponseDTO>>> getEventsByClub(@PathVariable Long clubId) {
        return eventService.getEventsByClub(clubId);
    }
}
