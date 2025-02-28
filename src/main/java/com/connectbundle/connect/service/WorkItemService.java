package com.connectbundle.connect.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.connectbundle.connect.dto.WorkItemsDTO.WorkItemDto;
import com.connectbundle.connect.model.User;
import com.connectbundle.connect.model.WorkItem;
import com.connectbundle.connect.model.enums.WorkItemLevel;
import com.connectbundle.connect.model.enums.WorkItemStatus;
import com.connectbundle.connect.model.enums.WorkItemType;
import com.connectbundle.connect.model.enums.WorkItemVerification;
import com.connectbundle.connect.repository.WorkItemRepository;
import lombok.Getter;

@Service
public class WorkItemService {

    @Autowired
    private WorkItemRepository workItemRepository;

    @Autowired
    private UserService userService;

    public WorkItemServiceResponse<WorkItem> createWorkItem(WorkItemDto workItemDto) {
        try {
            UserService.UserServiceResponse<User> userResponse = userService.getUserByID(workItemDto.getUserId());
            if (!userResponse.isSuccess()) {
                return new WorkItemServiceResponse<>(false, "User not found", null);
            }

            WorkItem workItem = new WorkItem();
            workItem.setName(workItemDto.getName());
            workItem.setDescription(workItemDto.getDescription());
            workItem.setUser(userResponse.getData());
            
            // Set default values for required fields
            workItem.setType(WorkItemType.INTERNSHIP); // Default type
            workItem.setStatus(WorkItemStatus.ONGOING); // Default status
            workItem.setLevel(WorkItemLevel.EASY); // Default level
            workItem.setVerified(WorkItemVerification.UNVERIFIED); // Default verification status

            WorkItem savedWorkItem = workItemRepository.save(workItem);
            return new WorkItemServiceResponse<>(true, "Work item created successfully", savedWorkItem);
        } catch (Exception e) {
            return new WorkItemServiceResponse<>(false, e.getMessage(), null);
        }
    }

    public WorkItemServiceResponse<WorkItem> updateWorkItem(Long id, WorkItemDto workItemDto) {
        try {
            Optional<WorkItem> optionalWorkItem = workItemRepository.findById(id);
            if (!optionalWorkItem.isPresent()) {
                return new WorkItemServiceResponse<>(false, "Work item not found", null);
            }

            WorkItem workItem = optionalWorkItem.get();
            
            // Only update user if it's changing
            if (workItem.getUser().getId() != workItemDto.getUserId()) {
                UserService.UserServiceResponse<User> userResponse = userService.getUserByID(workItemDto.getUserId());
                if (!userResponse.isSuccess()) {
                    return new WorkItemServiceResponse<>(false, "User not found", null);
                }
                workItem.setUser(userResponse.getData());
            }
            
            workItem.setName(workItemDto.getName());
            workItem.setDescription(workItemDto.getDescription());

            WorkItem updatedWorkItem = workItemRepository.save(workItem);
            return new WorkItemServiceResponse<>(true, "Work item updated successfully", updatedWorkItem);
        } catch (Exception e) {
            return new WorkItemServiceResponse<>(false, e.getMessage(), null);
        }
    }

    public WorkItemServiceResponse<WorkItem> getWorkItemById(Long id) {
        try {
            Optional<WorkItem> optionalWorkItem = workItemRepository.findById(id);
            if (!optionalWorkItem.isPresent()) {
                return new WorkItemServiceResponse<>(false, "Work item not found", null);
            }
            return new WorkItemServiceResponse<>(true, "Work item retrieved successfully", optionalWorkItem.get());
        } catch (Exception e) {
            return new WorkItemServiceResponse<>(false, e.getMessage(), null);
        }
    }

    public WorkItemServiceResponse<List<WorkItem>> getAllWorkItems() {
        try {
            List<WorkItem> workItems = workItemRepository.findAll();
            return new WorkItemServiceResponse<>(true, "Work items retrieved successfully", workItems);
        } catch (Exception e) {
            return new WorkItemServiceResponse<>(false, e.getMessage(), null);
        }
    }

    public WorkItemServiceResponse<List<WorkItem>> getWorkItemsByUser(Long userId) {
        try {
            UserService.UserServiceResponse<User> userResponse = userService.getUserByID(userId);
            if (!userResponse.isSuccess()) {
                return new WorkItemServiceResponse<>(false, "User not found", null);
            }
            
            List<WorkItem> workItems = workItemRepository.findByUser(userResponse.getData());
            return new WorkItemServiceResponse<>(true, "Work items retrieved successfully", workItems);
        } catch (Exception e) {
            return new WorkItemServiceResponse<>(false, e.getMessage(), null);
        }
    }

    public WorkItemServiceResponse<List<WorkItem>> getWorkItemsByType(WorkItemType type) {
        try {
            List<WorkItem> workItems = workItemRepository.findByType(type);
            return new WorkItemServiceResponse<>(true, "Work items retrieved successfully", workItems);
        } catch (Exception e) {
            return new WorkItemServiceResponse<>(false, e.getMessage(), null);
        }
    }

    public WorkItemServiceResponse<List<WorkItem>> getWorkItemsByStatus(WorkItemStatus status) {
        try {
            List<WorkItem> workItems = workItemRepository.findByStatus(status);
            return new WorkItemServiceResponse<>(true, "Work items retrieved successfully", workItems);
        } catch (Exception e) {
            return new WorkItemServiceResponse<>(false, e.getMessage(), null);
        }
    }

    public WorkItemServiceResponse<List<WorkItem>> getWorkItemsByLevel(WorkItemLevel level) {
        try {
            List<WorkItem> workItems = workItemRepository.findByLevel(level);
            return new WorkItemServiceResponse<>(true, "Work items retrieved successfully", workItems);
        } catch (Exception e) {
            return new WorkItemServiceResponse<>(false, e.getMessage(), null);
        }
    }

    public WorkItemServiceResponse<List<WorkItem>> getWorkItemsByVerificationStatus(WorkItemVerification verified) {
        try {
            List<WorkItem> workItems = workItemRepository.findByVerified(verified);
            return new WorkItemServiceResponse<>(true, "Work items retrieved successfully", workItems);
        } catch (Exception e) {
            return new WorkItemServiceResponse<>(false, e.getMessage(), null);
        }
    }

    public WorkItemServiceResponse<List<WorkItem>> searchWorkItemsByCompany(String company) {
        try {
            List<WorkItem> workItems = workItemRepository.findByCompanyContainingIgnoreCase(company);
            return new WorkItemServiceResponse<>(true, "Work items retrieved successfully", workItems);
        } catch (Exception e) {
            return new WorkItemServiceResponse<>(false, e.getMessage(), null);
        }
    }

    public WorkItemServiceResponse<List<WorkItem>> searchWorkItemsByTechStack(String techStack) {
        try {
            List<WorkItem> workItems = workItemRepository.findByTechStackContainingIgnoreCase(techStack);
            return new WorkItemServiceResponse<>(true, "Work items retrieved successfully", workItems);
        } catch (Exception e) {
            return new WorkItemServiceResponse<>(false, e.getMessage(), null);
        }
    }

    public WorkItemServiceResponse<WorkItem> updateWorkItemStatus(Long id, WorkItemStatus status) {
        try {
            Optional<WorkItem> optionalWorkItem = workItemRepository.findById(id);
            if (!optionalWorkItem.isPresent()) {
                return new WorkItemServiceResponse<>(false, "Work item not found", null);
            }
            
            WorkItem workItem = optionalWorkItem.get();
            workItem.setStatus(status);
            
            WorkItem updatedWorkItem = workItemRepository.save(workItem);
            return new WorkItemServiceResponse<>(true, "Work item status updated successfully", updatedWorkItem);
        } catch (Exception e) {
            return new WorkItemServiceResponse<>(false, e.getMessage(), null);
        }
    }

    public WorkItemServiceResponse<WorkItem> updateWorkItemVerification(Long id, WorkItemVerification verification) {
        try {
            Optional<WorkItem> optionalWorkItem = workItemRepository.findById(id);
            if (!optionalWorkItem.isPresent()) {
                return new WorkItemServiceResponse<>(false, "Work item not found", null);
            }
            
            WorkItem workItem = optionalWorkItem.get();
            workItem.setVerified(verification);
            
            WorkItem updatedWorkItem = workItemRepository.save(workItem);
            return new WorkItemServiceResponse<>(true, "Work item verification status updated successfully", updatedWorkItem);
        } catch (Exception e) {
            return new WorkItemServiceResponse<>(false, e.getMessage(), null);
        }
    }

    public WorkItemServiceResponse<List<WorkItem>> getWorkItemsByCategory(String category) {
        try {
            List<WorkItem> workItems = workItemRepository.findByCategoryContainingIgnoreCase(category);
            return new WorkItemServiceResponse<>(true, "Work items retrieved successfully", workItems);
        } catch (Exception e) {
            return new WorkItemServiceResponse<>(false, e.getMessage(), null);
        }
    }

    public WorkItemServiceResponse<Void> deleteWorkItem(Long id) {
        try {
            if (!workItemRepository.existsById(id)) {
                return new WorkItemServiceResponse<>(false, "Work item not found", null);
            }
            
            workItemRepository.deleteById(id);
            return new WorkItemServiceResponse<>(true, "Work item deleted successfully", null);
        } catch (Exception e) {
            return new WorkItemServiceResponse<>(false, e.getMessage(), null);
        }
    }

    // Response class
    @Getter
    public static class WorkItemServiceResponse<T> {
        private final boolean success;
        private final String message;
        private final T data;

        public WorkItemServiceResponse(boolean success, String message, T data) {
            this.success = success;
            this.message = message;
            this.data = data;
        }
    }
}
