package com.connectbundle.connect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.connectbundle.connect.model.User;
import com.connectbundle.connect.model.WorkItem;
import com.connectbundle.connect.model.enums.WorkItemLevel;
import com.connectbundle.connect.model.enums.WorkItemStatus;
import com.connectbundle.connect.model.enums.WorkItemType;
import com.connectbundle.connect.model.enums.WorkItemVerification;

@Repository
public interface WorkItemRepository extends JpaRepository<WorkItem, Long> {
    
    List<WorkItem> findByUser(User user);
    
    List<WorkItem> findByType(WorkItemType type);
    
    List<WorkItem> findByStatus(WorkItemStatus status);
    
    List<WorkItem> findByLevel(WorkItemLevel level);
    
    List<WorkItem> findByVerified(WorkItemVerification verified);
    
    List<WorkItem> findByUserAndType(User user, WorkItemType type);
    
    List<WorkItem> findByUserAndStatus(User user, WorkItemStatus status);
    
    List<WorkItem> findByCompanyContainingIgnoreCase(String company);
    
    List<WorkItem> findByTechStackContainingIgnoreCase(String techStack);
    
    List<WorkItem> findByCategoryContainingIgnoreCase(String category);
}
