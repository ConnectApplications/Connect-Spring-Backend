package com.connectbundle.connect.service;

import com.connectbundle.connect.dto.BaseResponse;
import com.connectbundle.connect.dto.EventsDTO.CreateEventDTO;
import com.connectbundle.connect.dto.EventsDTO.EventResponseDTO;
import com.connectbundle.connect.exception.ResourceNotFoundException;
import com.connectbundle.connect.model.Club;
import com.connectbundle.connect.model.Event;
import com.connectbundle.connect.repository.ClubsRespository;
import com.connectbundle.connect.repository.EventRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private ClubsRespository clubRepository;
    @Autowired
    private  ModelMapper modelMapper;

    public ResponseEntity<BaseResponse<List<EventResponseDTO>>> getAllEvents() {
        List<Event> events = eventRepository.findAll();

        List<EventResponseDTO> response = events.stream()
                .map(event -> modelMapper.map(event, EventResponseDTO.class))
                .toList();

        return BaseResponse.success(response, "Events fetched successfully", response.size());
    }

    public ResponseEntity<BaseResponse<EventResponseDTO>> getEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event", "id", id));

        EventResponseDTO response = modelMapper.map(event, EventResponseDTO.class);
        return BaseResponse.success(response, "Event fetched successfully", 1);
    }


    public ResponseEntity<BaseResponse<EventResponseDTO>> createEvent(CreateEventDTO dto) {
        Event event = modelMapper.map(dto, Event.class);

        Event savedEvent = eventRepository.save(event);
        if (dto.getClubId() != null) {
            Club club = clubRepository.findById(dto.getClubId())
                    .orElseThrow(() -> new ResourceNotFoundException("Club", "id", dto.getClubId()));
            event.setClub(club);
        }

        Event saved = eventRepository.save(event);
        EventResponseDTO response = modelMapper.map(saved, EventResponseDTO.class);
        return BaseResponse.success(response, "Event created", 1);
    }

    public ResponseEntity<BaseResponse<EventResponseDTO>> updateEvent(Long id, CreateEventDTO dto) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event", "id", id));

        modelMapper.map(dto, event);

        if (dto.getClubId() != null) {
            Club club = clubRepository.findById(dto.getClubId())
                    .orElseThrow(() -> new ResourceNotFoundException("Club", "id", dto.getClubId()));
            event.setClub(club);
        } else {
            event.setClub(null);
        }

        Event updated = eventRepository.save(event);
        EventResponseDTO response = modelMapper.map(updated, EventResponseDTO.class);

        return BaseResponse.success(response, "Event updated", 1);
    }

    public ResponseEntity<BaseResponse<Void>> deleteEvent(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event", "id", id));

        eventRepository.delete(event);
        return BaseResponse.successMessage("Event deleted");
    }

    public ResponseEntity<BaseResponse<List<EventResponseDTO>>> getEventsByClub(Long clubId) {
        List<Event> events = eventRepository.findByClubId(clubId);
        List<EventResponseDTO> response = events.stream()
                .map(event -> modelMapper.map(event, EventResponseDTO.class))
                .toList();
        return BaseResponse.success(response, "Events under club", response.size());
    }
}
