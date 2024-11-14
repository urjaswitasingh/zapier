package com.example.demo.kafka;

import com.example.demo.kafkamessagetemplate.CreditCheckMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private static final String TOPIC = "credit-check";
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    public void sendMessage(CreditCheckMessage message){
        this.kafkaTemplate.send(TOPIC, message);
    }
}
