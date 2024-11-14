package com.example.demo.controller;

import com.example.demo.dto.CreditCardApplicationDTO;
import com.example.demo.response.ReturnResponse;
import com.example.demo.service.CreditCardApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/creditcard")
public class CreditCardApplicationController {

    @Autowired
    private  CreditCardApplicationService applicationService;


    @PostMapping("/apply")
    public ResponseEntity<ReturnResponse> applyForCreditCard(@RequestBody CreditCardApplicationDTO applicationDTO) {
        ReturnResponse response = new ReturnResponse();
        try {
            CreditCardApplicationDTO returnedApplicationDTO=applicationService.submitApplication(applicationDTO);
            response.setStatus("success");
            response.setCode(HttpStatus.CREATED.value());
            response.setMessage("Credit Card application submitted successfully.");
            response.setData(returnedApplicationDTO);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error applying for Credit Card: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

