package com.connectbundle.connect.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.connectbundle.connect.dto.EventsDTO.CreateEventDTO;
import com.connectbundle.connect.model.Event;
import com.connectbundle.connect.model.User;
import com.connectbundle.connect.repository.EventRepository;
import com.connectbundle.connect.service.UserService.UserServiceResponse;

import lombok.Getter;

@Service
public class EventService {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    UserService userService;

    public EventServiceResponse<List<Event>> getAllEvents() {
        try {
            List<Event> allEvents = eventRepository.findAll();
            return new EventServiceResponse<>(true, "Events fetched successfully", allEvents);
        } catch (Exception e) {
            return new EventServiceResponse<>(false, e.getMessage(), null);
        }

    }

    public EventServiceResponse<Event> createEvent(CreateEventDTO createEventDTO) {
        try {
            UserServiceResponse<User> eventOwner = userService.getUserByUsername(createEventDTO.getUsername());
            if (eventOwner.isSuccess()) {
                Event newEvent = new Event();
                newEvent.setUser(eventOwner.getData());
                newEvent.setEvent_name(createEventDTO.getEventName());
                newEvent.setLocation(createEventDTO.getLocation());
                newEvent.setDescription(createEventDTO.getDescription());
                newEvent.setDate(java.time.LocalDate.parse(createEventDTO.getDate()));
                eventRepository.save(newEvent);
                return new EventServiceResponse<>(true, "Event saved successfully", newEvent);
            } else {
                return new EventServiceResponse<>(false, "User not found", null);
            }
        } catch (Exception e) {
            return new EventServiceResponse<>(false, e.getMessage(), null);
        }
    }

    // RESPONSE CLASS
    @Getter
    public static class EventServiceResponse<T> {
        private final boolean success;
        private final String message;
        private final T data;

        public EventServiceResponse(boolean success, String message, T data) {
            this.success = success;
            this.message = message;
            this.data = data;
        }
    }
}
