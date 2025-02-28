package com.connectbundle.connect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.connectbundle.connect.model.Task;
import com.connectbundle.connect.model.User;
import com.connectbundle.connect.model.enums.TaskStatusEnum;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    
    List<Task> findByUser(User user);
    
    List<Task> findByStatus(TaskStatusEnum status);
    
    List<Task> findByUserAndStatus(User user, TaskStatusEnum status);
}
