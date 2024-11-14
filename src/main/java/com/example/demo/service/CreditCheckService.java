package com.example.demo.service;

import com.example.demo.kafkamessagetemplate.CreditCheckMessage;
import com.example.demo.kafkamessagetemplate.CreditScoreReceiveMessage;
import com.example.demo.model.CreditScore;
import com.example.demo.model.KafkaMessage;
import com.example.demo.repository.CreditScoreRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreditCheckService {
    @Autowired
    CreditScoreRepo creditScoreRepo;

    public void creditCheck(String message) throws JsonProcessingException {
            ObjectMapper objectMapper = new ObjectMapper();
            CreditCheckMessage creditCheckMessage = objectMapper.readValue(message, CreditCheckMessage.class);
            CreditScore creditScore= creditScoreRepo.findByPhoneNumber(creditCheckMessage.getPhoneNumber());

    }

}
