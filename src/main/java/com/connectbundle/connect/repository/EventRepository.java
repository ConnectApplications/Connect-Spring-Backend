package com.connectbundle.connect.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.connectbundle.connect.model.Event;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByClubId(Long clubId);
}
