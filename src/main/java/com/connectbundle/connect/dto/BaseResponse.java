package com.connectbundle.connect.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {
    private T data;
    private String message;
    private int status;
    private String success;
    private int count;

    public static <T> ResponseEntity<BaseResponse<T>> success(T data, String message, HttpStatus status,int count) {
        return ResponseEntity.status(status)
                .body(new BaseResponse<>(data, message, status.value(), "success", count));
    }

    // do we need T here ?
    public static <T> ResponseEntity<BaseResponse<T>> error(String message, HttpStatus status) {
        return ResponseEntity.status(status)
                .body(new BaseResponse<>(null, message, status.value(), "Error", 0));
    }


}
