package com.example.demo.kafka;

import com.example.demo.service.CreditCheckService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

public class KafkaConsumerService {

    @Autowired
    CreditCheckService creditCheckService;

    @KafkaListener(topics= "credit-check" ,groupId = "my-group" )
    public void listner(String message) throws JsonProcessingException {

        creditCheckService.creditCheck(message);

    }

}
