package com.connectbundle.connect.repository;

import com.connectbundle.connect.model.ProjectApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectApplicationRepository extends JpaRepository<ProjectApplication, Long> {
}
