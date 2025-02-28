package com.connectbundle.connect.dto.ResourcesDTO;

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
public class ResourceDto {
    private Long id;

    @NotBlank(message = "Resource name is required")
    private String name;

    @NotBlank(message = "Resource type is required")
    private String type;

    @NotBlank(message = "Resource URL is required")
    private String url;

    @NotNull(message = "User ID is required")
    private Long userId;
}
