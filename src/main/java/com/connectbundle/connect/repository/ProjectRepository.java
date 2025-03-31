package com.connectbundle.connect.repository;

import com.connectbundle.connect.model.Project;
import com.connectbundle.connect.model.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> , JpaSpecificationExecutor<Project> {
    List<Project> findByOwnerId(User owner);
    List<Project> findByOwnerUsername(String username);
    List<Project> findByOwnerUsernameAndTagsIn(String username, List<String> tags);
    List<Project> findByTagsIn(List<String> tags);


}
