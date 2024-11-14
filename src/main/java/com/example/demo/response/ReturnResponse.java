package com.example.demo.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReturnResponse<T> {

    private String status;          // "success" or "error"
    private int code;               // HTTP Status code (e.g., 200, 400, 500)
    private String message;         // success or error message
    private T data;                 // Actual response data (can be any type)


}
