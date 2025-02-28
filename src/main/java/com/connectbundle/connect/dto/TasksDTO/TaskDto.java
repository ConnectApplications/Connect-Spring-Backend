package com.connectbundle.connect.dto.TasksDTO;

import com.connectbundle.connect.model.enums.TaskStatusEnum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Deadline is required")
    private String deadline;

    @NotNull(message = "Status is required")
    private TaskStatusEnum status;

    @NotNull(message = "User ID is required")
    private Long userId;
}
