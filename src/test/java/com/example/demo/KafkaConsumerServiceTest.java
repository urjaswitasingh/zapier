package com.example.demo;

import com.example.demo.kafka.KafkaConsumerService;

import com.example.demo.service.CreditCheckService;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class KafkaConsumerServiceTest {

    private String testMessage;

    @Autowired
    private KafkaConsumerService kafkaConsumerService;

    @Autowired
    private CreditCheckService creditCheckService;

    @BeforeEach
    public void setUp() throws JsonProcessingException {

        testMessage = "{\"phoneNumber\":1234567890, \"salary\":50000, \"totalCard\":3}";

    }

    @Test
    public void testListener() throws JsonProcessingException {

        kafkaConsumerService.listner(testMessage);

    }
}
