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
    private Integer count;

    public BaseResponse(String message) {
        this.message = message;
    }

    public static <T> ResponseEntity<BaseResponse<T>> success(T data, String message, Integer count) {
        BaseResponse<T> response = new BaseResponse<>(data, message, count);
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<BaseResponse<Void>> successMessage(String message) {
        BaseResponse<Void> response = new BaseResponse<>(message);
        return ResponseEntity.ok(response);
    }

    public static <T> ResponseEntity<BaseResponse<T>> error(String message, HttpStatus status) {
        BaseResponse<T> response = new BaseResponse<>(message);
        return ResponseEntity.status(status).body(response);
    }
}
