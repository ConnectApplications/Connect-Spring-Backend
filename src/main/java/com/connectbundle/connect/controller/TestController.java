package com.connectbundle.connect.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.connectbundle.connect.dto.BaseResponse;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping()
    public ResponseEntity<BaseResponse<String>> testServer() {
        return BaseResponse.success(null,"Server is OK",HttpStatus.OK,0);
    }
}
