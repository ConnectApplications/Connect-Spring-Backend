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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.connectbundle.connect.dto.BaseResponse;
import com.connectbundle.connect.dto.ResourcesDTO.ResourceDto;
import com.connectbundle.connect.model.Resource;
import com.connectbundle.connect.service.ResourceService;
import com.connectbundle.connect.service.ResourceService.ResourceServiceResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/resources")
@Tag(name = "Resources", description = "Resource Management Endpoints")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @GetMapping
    @Operation(summary = "Get All Resources", description = "Retrieve a list of all resources")
    public ResponseEntity<BaseResponse<List<Resource>>> getAllResources() {
        try {
            ResourceServiceResponse<List<Resource>> response = resourceService.getAllResources();
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
    @Operation(summary = "Get Resource by ID", description = "Retrieve a resource by its ID")
    public ResponseEntity<BaseResponse<Resource>> getResourceById(@PathVariable Long id) {
        try {
            ResourceServiceResponse<Resource> response = resourceService.getResourceById(id);
            if (response.isSuccess()) {
                return BaseResponse.success(response.getData(), response.getMessage(), HttpStatus.OK, 1);
            } else {
                return BaseResponse.error(response.getMessage(), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/type/{type}")
    @Operation(summary = "Get Resources by Type", description = "Retrieve all resources of a specific type")
    public ResponseEntity<BaseResponse<List<Resource>>> getResourcesByType(@PathVariable String type) {
        try {
            ResourceServiceResponse<List<Resource>> response = resourceService.getResourcesByType(type);
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

    @GetMapping("/search")
    @Operation(summary = "Search Resources by Name", description = "Search for resources by name (case-insensitive)")
    public ResponseEntity<BaseResponse<List<Resource>>> searchResourcesByName(@RequestParam String name) {
        try {
            ResourceServiceResponse<List<Resource>> response = resourceService.searchResourcesByName(name);
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

    @GetMapping("/url")
    @Operation(summary = "Get Resource by URL", description = "Retrieve a resource by its URL")
    public ResponseEntity<BaseResponse<Resource>> getResourceByUrl(@RequestParam String url) {
        try {
            ResourceServiceResponse<Resource> response = resourceService.getResourceByUrl(url);
            if (response.isSuccess()) {
                return BaseResponse.success(response.getData(), response.getMessage(), HttpStatus.OK, 1);
            } else {
                return BaseResponse.error(response.getMessage(), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    @Operation(summary = "Create Resource", description = "Create a new resource")
    public ResponseEntity<BaseResponse<Resource>> createResource(@Valid @RequestBody ResourceDto resourceDto) {
        try {
            ResourceServiceResponse<Resource> response = resourceService.createResource(resourceDto);
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
    @Operation(summary = "Update Resource", description = "Update an existing resource")
    public ResponseEntity<BaseResponse<Resource>> updateResource(
            @PathVariable Long id,
            @Valid @RequestBody ResourceDto resourceDto) {
        try {
            ResourceServiceResponse<Resource> response = resourceService.updateResource(id, resourceDto);
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
    @Operation(summary = "Delete Resource", description = "Delete a resource by its ID")
    public ResponseEntity<BaseResponse<Void>> deleteResource(@PathVariable Long id) {
        try {
            ResourceServiceResponse<Void> response = resourceService.deleteResource(id);
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
