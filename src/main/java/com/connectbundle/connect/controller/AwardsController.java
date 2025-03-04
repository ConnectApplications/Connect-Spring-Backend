package com.connectbundle.connect.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.connectbundle.connect.dto.AwardsDTO.CreateAwardDTO;
import com.connectbundle.connect.dto.BaseResponse;
import com.connectbundle.connect.model.Awards;
import com.connectbundle.connect.service.AwardsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController()
@RequestMapping("/api/awards")
@Tag(name = "Awards", description = "Awards Endpoints")
public class AwardsController {

    @Autowired
    AwardsService awardsService;

    @GetMapping()
    @Operation(summary = "Get All Awards", description = "Retrieve a list of all awards")
    public ResponseEntity<BaseResponse<List<Awards>>> getAllAwards() {
        try {
            AwardsService.AwardServiceResponse<List<Awards>> awardServiceResponse = awardsService.getAllAwards();
            if (awardServiceResponse.isSuccess()) {
                return BaseResponse.success(awardServiceResponse.getData(), awardServiceResponse.getMessage(), awardServiceResponse.getData().size());
            } else {
                return BaseResponse.error(awardServiceResponse.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/createAward")
    @Operation(summary = "Create Award", description = "Create a new award")
    public ResponseEntity<BaseResponse<Awards>> createAward(@Valid @RequestBody CreateAwardDTO award) {
        try {
            AwardsService.AwardServiceResponse<Awards> awardServiceResponse = awardsService.createAward(award);
            if (awardServiceResponse.isSuccess()) {
                return BaseResponse.success(awardServiceResponse.getData(), awardServiceResponse.getMessage(), 1);
            } else {
                return BaseResponse.error(awardServiceResponse.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
