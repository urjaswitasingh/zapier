package com.example.demo.service;

import com.example.demo.kafka.KafkaProducerService;
import com.example.demo.kafkamessagetemplate.CreditCheckMessage;
import com.example.demo.kafkamessagetemplate.CreditScoreReceiveMessage;
import com.example.demo.model.CreditScore;
import com.example.demo.repository.CreditScoreRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditCheckService {
    @Autowired
    CreditScoreRepo creditScoreRepo;
    @Autowired
    KafkaProducerService kafkaProducerService;

    public void creditCheck(String message) throws JsonProcessingException {
        // convert kafka message to CreditCheckMessage
        ObjectMapper objectMapper = new ObjectMapper();
        CreditCheckMessage creditCheckMessage = objectMapper.readValue(message, CreditCheckMessage.class);

        // check if credit score already exists
        CreditScore creditScore = creditScoreRepo.findByPhoneNumber(creditCheckMessage.getPhoneNumber());

        // if credit score does not exist, generate credit score and save it
        if (creditScore == null) {
            int generatedScore = generateCreditScore(creditCheckMessage.getSalary(), creditCheckMessage.getTotalCard());
            creditScore.setCreditScore(generatedScore);
            creditScore.setPhoneNumber(creditCheckMessage.getPhoneNumber());
            creditScoreRepo.save(creditScore);

            // send credit score to kafka
            CreditScoreReceiveMessage creditScoreReceiveMessage = new CreditScoreReceiveMessage();
            creditScoreReceiveMessage.setCreditScore(generatedScore);
            creditScoreReceiveMessage.setPhoneNumber(creditCheckMessage.getPhoneNumber());
            kafkaProducerService.sendMessage(creditScoreReceiveMessage);
        }
        else{
            // send credit score to kafka
            CreditScoreReceiveMessage creditScoreReceiveMessage = new CreditScoreReceiveMessage();
            creditScoreReceiveMessage.setCreditScore(creditScore.getCreditScore());
            creditScoreReceiveMessage.setPhoneNumber(creditCheckMessage.getPhoneNumber());
            kafkaProducerService.sendMessage(creditScoreReceiveMessage);
            System.out.println( "Credit score already exists for phone number: " + creditCheckMessage.getPhoneNumber());
        }
    }
    private int generateCreditScore(double annualSalary, int numCreditCards) {
        int creditScore = 0;

        // Rule 1: Number of credit cards
        if (numCreditCards >= 2) {
            creditScore += 300;
        }
        // Rule 2: Annual salary
        if (annualSalary > 200000) {
            creditScore += 500;
        } else if (annualSalary > 50000) {
            creditScore += 150;
        } else {
            creditScore += 50;
        }

        return creditScore;
    }

}
