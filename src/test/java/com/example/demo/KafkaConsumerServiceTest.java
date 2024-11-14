package com.example.demo;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import com.example.demo.kafka.KafkaConsumerService;
import com.example.demo.kafkamessagetemplate.CreditCheckMessage;
import com.example.demo.service.CreditCheckService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.test.context.EmbeddedKafka;


//@EnableKafka
//@EmbeddedKafka(topics = { "check-credit" })
//@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class KafkaConsumerServiceTest {

    private String testMessage;

    @Autowired
    private KafkaConsumerService kafkaConsumerService;

    @Autowired
    private CreditCheckService creditCheckService;

    @BeforeEach
    public void setUp() throws JsonProcessingException {
        // hardcoding the message for testing
//        CreditCheckMessage testMessage = new CreditCheckMessage();
//        testMessage.setPhoneNumber(1234567890L);
//        testMessage.setSalary(75000);
//        testMessage.setTotalCard(3);
//
//        // Convert the object to a JSON string
//        ObjectMapper objectMapper = new ObjectMapper();
//        String HardCodedMessage = objectMapper.writeValueAsString(testMessage);
        testMessage = "{\"phoneNumber\":1234567890, \"salary\":50000, \"totalCard\":3}";

    }

    @Test
    public void testListener() throws JsonProcessingException {

        kafkaConsumerService.listner(testMessage);

        // Verify that creditCheckService.creditCheck() is called once with the correct argument
        verify(creditCheckService, times(1)).creditCheck(testMessage);
    }
}
