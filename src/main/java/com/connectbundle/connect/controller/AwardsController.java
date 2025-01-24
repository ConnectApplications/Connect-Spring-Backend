package com.connectbundle.connect.controller;

import com.connectbundle.connect.dto.AwardsDTO.CreateAwardDTO;
import com.connectbundle.connect.dto.BaseResponse;
import com.connectbundle.connect.model.Awards;
import com.connectbundle.connect.service.AwardsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/api/awards")
public class AwardsController {

    @Autowired
    AwardsService awardsService;

    @GetMapping()
    // Get all Awards
    public ResponseEntity<BaseResponse<List<Awards>>> getAllAwards() {
        try {
            AwardsService.AwardServiceResponse<List<Awards>> awardServiceResponse = awardsService.getAllAwards();
            if (awardServiceResponse.isSuccess()) {
                return BaseResponse.success(awardServiceResponse.getData(), awardServiceResponse.getMessage(), HttpStatus.OK, awardServiceResponse.getData().size());
            } else {
                return BaseResponse.error(awardServiceResponse.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/createAward")
    // Create / Add a new Award
    public ResponseEntity<BaseResponse<Awards>> createAward(@Valid @RequestBody CreateAwardDTO award) {
        try {
            AwardsService.AwardServiceResponse<Awards> awardServiceResponse = awardsService.createAward(award);
            if (awardServiceResponse.isSuccess()) {
                return BaseResponse.success(awardServiceResponse.getData(), awardServiceResponse.getMessage(), HttpStatus.OK, 0);
            } else {
                return BaseResponse.error(awardServiceResponse.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
