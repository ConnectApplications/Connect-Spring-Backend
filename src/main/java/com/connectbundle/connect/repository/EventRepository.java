package com.connectbundle.connect.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.connectbundle.connect.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

}
