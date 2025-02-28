package com.connectbundle.connect.dto.WorkItemsDTO;

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
public class WorkItemDto {
    private Long id;

    @NotBlank(message = "Work item name is required")
    private String name;

    @NotBlank(message = "Work item description is required")
    private String description;

    @NotNull(message = "User ID is required")
    private Long userId;
}
