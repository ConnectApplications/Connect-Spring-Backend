package com.connectbundle.connect.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.connectbundle.connect.dto.TasksDTO.TaskDto;
import com.connectbundle.connect.model.Task;
import com.connectbundle.connect.model.User;
import com.connectbundle.connect.model.enums.TaskStatusEnum;
import com.connectbundle.connect.repository.TaskRepository;
import lombok.Getter;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    public TaskServiceResponse<Task> createTask(TaskDto taskDto) {
        try {
            UserService.UserServiceResponse<User> userResponse = userService.getUserByID(taskDto.getUserId());
            if (!userResponse.isSuccess()) {
                return new TaskServiceResponse<>(false, "User not found", null);
            }

            Task task = new Task();
            task.setTitle(taskDto.getTitle());
            task.setDeadline(taskDto.getDeadline());
            task.setStatus(taskDto.getStatus());
            task.setUser(userResponse.getData());

            Task savedTask = taskRepository.save(task);
            return new TaskServiceResponse<>(true, "Task created successfully", savedTask);
        } catch (Exception e) {
            return new TaskServiceResponse<>(false, e.getMessage(), null);
        }
    }

    public TaskServiceResponse<Task> updateTask(Long id, TaskDto taskDto) {
        try {
            Optional<Task> optionalTask = taskRepository.findById(id);
            if (!optionalTask.isPresent()) {
                return new TaskServiceResponse<>(false, "Task not found", null);
            }

            Task task = optionalTask.get();
            
            // Only update user if it's changing
            if (task.getUser().getId() != taskDto.getUserId()) {
                UserService.UserServiceResponse<User> userResponse = userService.getUserByID(taskDto.getUserId());
                if (!userResponse.isSuccess()) {
                    return new TaskServiceResponse<>(false, "User not found", null);
                }
                task.setUser(userResponse.getData());
            }
            
            task.setTitle(taskDto.getTitle());
            task.setDeadline(taskDto.getDeadline());
            task.setStatus(taskDto.getStatus());

            Task updatedTask = taskRepository.save(task);
            return new TaskServiceResponse<>(true, "Task updated successfully", updatedTask);
        } catch (Exception e) {
            return new TaskServiceResponse<>(false, e.getMessage(), null);
        }
    }

    public TaskServiceResponse<Task> getTaskById(Long id) {
        try {
            Optional<Task> optionalTask = taskRepository.findById(id);
            if (!optionalTask.isPresent()) {
                return new TaskServiceResponse<>(false, "Task not found", null);
            }
            return new TaskServiceResponse<>(true, "Task retrieved successfully", optionalTask.get());
        } catch (Exception e) {
            return new TaskServiceResponse<>(false, e.getMessage(), null);
        }
    }

    public TaskServiceResponse<List<Task>> getAllTasks() {
        try {
            List<Task> tasks = taskRepository.findAll();
            return new TaskServiceResponse<>(true, "Tasks retrieved successfully", tasks);
        } catch (Exception e) {
            return new TaskServiceResponse<>(false, e.getMessage(), null);
        }
    }

    public TaskServiceResponse<List<Task>> getTasksByUser(Long userId) {
        try {
            UserService.UserServiceResponse<User> userResponse = userService.getUserByID(userId);
            if (!userResponse.isSuccess()) {
                return new TaskServiceResponse<>(false, "User not found", null);
            }
            
            List<Task> tasks = taskRepository.findByUser(userResponse.getData());
            return new TaskServiceResponse<>(true, "Tasks retrieved successfully", tasks);
        } catch (Exception e) {
            return new TaskServiceResponse<>(false, e.getMessage(), null);
        }
    }

    public TaskServiceResponse<List<Task>> getTasksByStatus(TaskStatusEnum status) {
        try {
            List<Task> tasks = taskRepository.findByStatus(status);
            return new TaskServiceResponse<>(true, "Tasks retrieved successfully", tasks);
        } catch (Exception e) {
            return new TaskServiceResponse<>(false, e.getMessage(), null);
        }
    }

    public TaskServiceResponse<Void> deleteTask(Long id) {
        try {
            if (!taskRepository.existsById(id)) {
                return new TaskServiceResponse<>(false, "Task not found", null);
            }
            
            taskRepository.deleteById(id);
            return new TaskServiceResponse<>(true, "Task deleted successfully", null);
        } catch (Exception e) {
            return new TaskServiceResponse<>(false, e.getMessage(), null);
        }
    }

    // Response class
    @Getter
    public static class TaskServiceResponse<T> {
        private final boolean success;
        private final String message;
        private final T data;

        public TaskServiceResponse(boolean success, String message, T data) {
            this.success = success;
            this.message = message;
            this.data = data;
        }
    }
}
