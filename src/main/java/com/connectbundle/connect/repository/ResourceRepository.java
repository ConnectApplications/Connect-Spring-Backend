package com.connectbundle.connect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.connectbundle.connect.model.Resource;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
    
    List<Resource> findByType(String type);
    
    List<Resource> findByNameContainingIgnoreCase(String name);
    
    Resource findByUrl(String url);
}
