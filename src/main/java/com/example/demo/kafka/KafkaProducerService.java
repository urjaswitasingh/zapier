package com.example.demo.kafka;

import com.example.demo.dto.CreditCardApplicationDTO;
import com.example.demo.model.CreditCardApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private static final String TOPIC = "credit-check";
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    public void sendMessage(CreditCardApplication message){
        this.kafkaTemplate.send(TOPIC, message);
    }
}
