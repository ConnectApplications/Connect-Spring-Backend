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
import com.connectbundle.connect.dto.TasksDTO.TaskDto;
import com.connectbundle.connect.model.Task;
import com.connectbundle.connect.model.enums.TaskStatusEnum;
import com.connectbundle.connect.service.TaskService;
import com.connectbundle.connect.service.TaskService.TaskServiceResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tasks")
@Tag(name = "Tasks", description = "Task Management Endpoints")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    @Operation(summary = "Get All Tasks", description = "Retrieve a list of all tasks")
    public ResponseEntity<BaseResponse<List<Task>>> getAllTasks() {
        try {
            TaskServiceResponse<List<Task>> response = taskService.getAllTasks();
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
    @Operation(summary = "Get Task by ID", description = "Retrieve a task by its ID")
    public ResponseEntity<BaseResponse<Task>> getTaskById(@PathVariable Long id) {
        try {
            TaskServiceResponse<Task> response = taskService.getTaskById(id);
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
    @Operation(summary = "Get Tasks by User", description = "Retrieve all tasks assigned to a specific user")
    public ResponseEntity<BaseResponse<List<Task>>> getTasksByUser(@PathVariable Long userId) {
        try {
            TaskServiceResponse<List<Task>> response = taskService.getTasksByUser(userId);
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
    @Operation(summary = "Get Tasks by Status", description = "Retrieve all tasks with a specific status")
    public ResponseEntity<BaseResponse<List<Task>>> getTasksByStatus(@PathVariable TaskStatusEnum status) {
        try {
            TaskServiceResponse<List<Task>> response = taskService.getTasksByStatus(status);
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
    @Operation(summary = "Create Task", description = "Create a new task")
    public ResponseEntity<BaseResponse<Task>> createTask(@Valid @RequestBody TaskDto taskDto) {
        try {
            TaskServiceResponse<Task> response = taskService.createTask(taskDto);
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
    @Operation(summary = "Update Task", description = "Update an existing task")
    public ResponseEntity<BaseResponse<Task>> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskDto taskDto) {
        try {
            TaskServiceResponse<Task> response = taskService.updateTask(id, taskDto);
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
    @Operation(summary = "Delete Task", description = "Delete a task by its ID")
    public ResponseEntity<BaseResponse<Void>> deleteTask(@PathVariable Long id) {
        try {
            TaskServiceResponse<Void> response = taskService.deleteTask(id);
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
