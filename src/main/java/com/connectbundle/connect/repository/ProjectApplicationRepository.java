package com.connectbundle.connect.repository;

import com.connectbundle.connect.model.Project;
import com.connectbundle.connect.model.ProjectApplication;
import com.connectbundle.connect.model.User.User;
import com.connectbundle.connect.model.enums.ProjectApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectApplicationRepository extends JpaRepository<ProjectApplication, Long> {
    boolean existsByProjectAndApplicantAndStatusIn(Project project, User applicant, List<ProjectApplicationStatus> statuses);

}
