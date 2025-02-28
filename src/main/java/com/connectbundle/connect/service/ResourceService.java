package com.connectbundle.connect.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.connectbundle.connect.dto.ResourcesDTO.ResourceDto;
import com.connectbundle.connect.model.Resource;
import com.connectbundle.connect.repository.ResourceRepository;
import lombok.Getter;

@Service
public class ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    public ResourceServiceResponse<Resource> createResource(ResourceDto resourceDto) {
        try {
            Resource resource = new Resource();
            resource.setName(resourceDto.getName());
            resource.setType(resourceDto.getType());
            resource.setUrl(resourceDto.getUrl());

            Resource savedResource = resourceRepository.save(resource);
            return new ResourceServiceResponse<>(true, "Resource created successfully", savedResource);
        } catch (Exception e) {
            return new ResourceServiceResponse<>(false, e.getMessage(), null);
        }
    }

    public ResourceServiceResponse<Resource> updateResource(Long id, ResourceDto resourceDto) {
        try {
            Optional<Resource> optionalResource = resourceRepository.findById(id);
            if (!optionalResource.isPresent()) {
                return new ResourceServiceResponse<>(false, "Resource not found", null);
            }

            Resource resource = optionalResource.get();
            resource.setName(resourceDto.getName());
            resource.setType(resourceDto.getType());
            resource.setUrl(resourceDto.getUrl());

            Resource updatedResource = resourceRepository.save(resource);
            return new ResourceServiceResponse<>(true, "Resource updated successfully", updatedResource);
        } catch (Exception e) {
            return new ResourceServiceResponse<>(false, e.getMessage(), null);
        }
    }

    public ResourceServiceResponse<Resource> getResourceById(Long id) {
        try {
            Optional<Resource> optionalResource = resourceRepository.findById(id);
            if (!optionalResource.isPresent()) {
                return new ResourceServiceResponse<>(false, "Resource not found", null);
            }
            return new ResourceServiceResponse<>(true, "Resource retrieved successfully", optionalResource.get());
        } catch (Exception e) {
            return new ResourceServiceResponse<>(false, e.getMessage(), null);
        }
    }

    public ResourceServiceResponse<List<Resource>> getAllResources() {
        try {
            List<Resource> resources = resourceRepository.findAll();
            return new ResourceServiceResponse<>(true, "Resources retrieved successfully", resources);
        } catch (Exception e) {
            return new ResourceServiceResponse<>(false, e.getMessage(), null);
        }
    }

    public ResourceServiceResponse<List<Resource>> getResourcesByType(String type) {
        try {
            List<Resource> resources = resourceRepository.findByType(type);
            return new ResourceServiceResponse<>(true, "Resources retrieved successfully", resources);
        } catch (Exception e) {
            return new ResourceServiceResponse<>(false, e.getMessage(), null);
        }
    }

    public ResourceServiceResponse<List<Resource>> searchResourcesByName(String name) {
        try {
            List<Resource> resources = resourceRepository.findByNameContainingIgnoreCase(name);
            return new ResourceServiceResponse<>(true, "Resources retrieved successfully", resources);
        } catch (Exception e) {
            return new ResourceServiceResponse<>(false, e.getMessage(), null);
        }
    }

    public ResourceServiceResponse<Resource> getResourceByUrl(String url) {
        try {
            Resource resource = resourceRepository.findByUrl(url);
            if (resource == null) {
                return new ResourceServiceResponse<>(false, "Resource not found", null);
            }
            return new ResourceServiceResponse<>(true, "Resource retrieved successfully", resource);
        } catch (Exception e) {
            return new ResourceServiceResponse<>(false, e.getMessage(), null);
        }
    }

    public ResourceServiceResponse<Void> deleteResource(Long id) {
        try {
            if (!resourceRepository.existsById(id)) {
                return new ResourceServiceResponse<>(false, "Resource not found", null);
            }
            
            resourceRepository.deleteById(id);
            return new ResourceServiceResponse<>(true, "Resource deleted successfully", null);
        } catch (Exception e) {
            return new ResourceServiceResponse<>(false, e.getMessage(), null);
        }
    }

    // Response class
    @Getter
    public static class ResourceServiceResponse<T> {
        private final boolean success;
        private final String message;
        private final T data;

        public ResourceServiceResponse(boolean success, String message, T data) {
            this.success = success;
            this.message = message;
            this.data = data;
        }
    }
}
