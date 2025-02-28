package com.connectbundle.connect.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.connectbundle.connect.dto.BaseResponse;
import com.connectbundle.connect.dto.WorkItemsDTO.WorkItemDto;
import com.connectbundle.connect.model.WorkItem;
import com.connectbundle.connect.model.enums.WorkItemLevel;
import com.connectbundle.connect.model.enums.WorkItemStatus;
import com.connectbundle.connect.model.enums.WorkItemVerification;
import com.connectbundle.connect.service.WorkItemService;
import com.connectbundle.connect.service.WorkItemService.WorkItemServiceResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/workitems")
@Tag(name = "Work Items", description = "Work Item Management Endpoints")
public class WorkItemController {

    @Autowired
    private WorkItemService workItemService;

    @GetMapping
    @Operation(summary = "Get All Work Items", description = "Retrieve a list of all work items")
    public ResponseEntity<BaseResponse<List<WorkItem>>> getAllWorkItems() {
        try {
            WorkItemServiceResponse<List<WorkItem>> response = workItemService.getAllWorkItems();
            if (response.isSuccess()) {
                return BaseResponse.success(response.getData(), response.getMessage(),
                        HttpStatus.OK, response.getData().size());
            } else {
                return BaseResponse.error(response.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Work Item by ID", description = "Retrieve a work item by its ID")
    public ResponseEntity<BaseResponse<WorkItem>> getWorkItemById(@PathVariable Long id) {
        try {
            WorkItemServiceResponse<WorkItem> response = workItemService.getWorkItemById(id);
            if (response.isSuccess()) {
                return BaseResponse.success(response.getData(), response.getMessage(), HttpStatus.OK, 1);
            } else {
                return BaseResponse.error(response.getMessage(), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get Work Items by User", description = "Retrieve all work items assigned to a specific user")
    public ResponseEntity<BaseResponse<List<WorkItem>>> getWorkItemsByUser(@PathVariable Long userId) {
        try {
            WorkItemServiceResponse<List<WorkItem>> response = workItemService.getWorkItemsByUser(userId);
            if (response.isSuccess()) {
                return BaseResponse.success(response.getData(), response.getMessage(),
                        HttpStatus.OK, response.getData().size());
            } else {
                return BaseResponse.error(response.getMessage(), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get Work Items by Status", description = "Retrieve all work items with a specific status")
    public ResponseEntity<BaseResponse<List<WorkItem>>> getWorkItemsByStatus(@PathVariable WorkItemStatus status) {
        try {
            WorkItemServiceResponse<List<WorkItem>> response = workItemService.getWorkItemsByStatus(status);
            if (response.isSuccess()) {
                return BaseResponse.success(response.getData(), response.getMessage(),
                        HttpStatus.OK, response.getData().size());
            } else {
                return BaseResponse.error(response.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/verification/{verification}")
    @Operation(summary = "Get Work Items by Verification Status", description = "Retrieve all work items with a specific verification status")
    public ResponseEntity<BaseResponse<List<WorkItem>>> getWorkItemsByVerification(@PathVariable WorkItemVerification verification) {
        try {
            WorkItemServiceResponse<List<WorkItem>> response = workItemService.getWorkItemsByVerificationStatus(verification);
            if (response.isSuccess()) {
                return BaseResponse.success(response.getData(), response.getMessage(),
                        HttpStatus.OK, response.getData().size());
            } else {
                return BaseResponse.error(response.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/level/{level}")
    @Operation(summary = "Get Work Items by Level", description = "Retrieve all work items with a specific difficulty level")
    public ResponseEntity<BaseResponse<List<WorkItem>>> getWorkItemsByLevel(@PathVariable WorkItemLevel level) {
        try {
            WorkItemServiceResponse<List<WorkItem>> response = workItemService.getWorkItemsByLevel(level);
            if (response.isSuccess()) {
                return BaseResponse.success(response.getData(), response.getMessage(),
                        HttpStatus.OK, response.getData().size());
            } else {
                return BaseResponse.error(response.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/category/{category}")
    @Operation(summary = "Get Work Items by Category", description = "Retrieve all work items in a specific category")
    public ResponseEntity<BaseResponse<List<WorkItem>>> getWorkItemsByCategory(@PathVariable String category) {
        try {
            WorkItemServiceResponse<List<WorkItem>> response = workItemService.getWorkItemsByCategory(category);
            if (response.isSuccess()) {
                return BaseResponse.success(response.getData(), response.getMessage(),
                        HttpStatus.OK, response.getData().size());
            } else {
                return BaseResponse.error(response.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    @Operation(summary = "Create Work Item", description = "Create a new work item")
    public ResponseEntity<BaseResponse<WorkItem>> createWorkItem(@Valid @RequestBody WorkItemDto workItemDto) {
        try {
            WorkItemServiceResponse<WorkItem> response = workItemService.createWorkItem(workItemDto);
            if (response.isSuccess()) {
                return BaseResponse.success(response.getData(), response.getMessage(), HttpStatus.CREATED, 1);
            } else {
                return BaseResponse.error(response.getMessage(), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Work Item", description = "Update an existing work item")
    public ResponseEntity<BaseResponse<WorkItem>> updateWorkItem(
            @PathVariable Long id,
            @Valid @RequestBody WorkItemDto workItemDto) {
        try {
            WorkItemServiceResponse<WorkItem> response = workItemService.updateWorkItem(id, workItemDto);
            if (response.isSuccess()) {
                return BaseResponse.success(response.getData(), response.getMessage(), HttpStatus.OK, 1);
            } else {
                return BaseResponse.error(response.getMessage(), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}/status/{status}")
    @Operation(summary = "Update Work Item Status", description = "Update the status of an existing work item")
    public ResponseEntity<BaseResponse<WorkItem>> updateWorkItemStatus(
            @PathVariable Long id,
            @PathVariable WorkItemStatus status) {
        try {
            WorkItemServiceResponse<WorkItem> response = workItemService.updateWorkItemStatus(id, status);
            if (response.isSuccess()) {
                return BaseResponse.success(response.getData(), response.getMessage(), HttpStatus.OK, 1);
            } else {
                return BaseResponse.error(response.getMessage(), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}/verification/{verification}")
    @Operation(summary = "Update Work Item Verification", description = "Update the verification status of an existing work item")
    public ResponseEntity<BaseResponse<WorkItem>> updateWorkItemVerification(
            @PathVariable Long id,
            @PathVariable WorkItemVerification verification) {
        try {
            WorkItemServiceResponse<WorkItem> response = workItemService.updateWorkItemVerification(id, verification);
            if (response.isSuccess()) {
                return BaseResponse.success(response.getData(), response.getMessage(), HttpStatus.OK, 1);
            } else {
                return BaseResponse.error(response.getMessage(), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Work Item", description = "Delete a work item by its ID")
    public ResponseEntity<BaseResponse<Void>> deleteWorkItem(@PathVariable Long id) {
        try {
            WorkItemServiceResponse<Void> response = workItemService.deleteWorkItem(id);
            if (response.isSuccess()) {
                return BaseResponse.success(null, response.getMessage(), HttpStatus.OK, 0);
            } else {
                return BaseResponse.error(response.getMessage(), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
