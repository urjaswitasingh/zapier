package com.example.demo;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import com.example.demo.kafka.KafkaConsumerService;
import com.example.demo.service.CreditCheckService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;

@ExtendWith(MockitoExtension.class)
@EnableKafka
@EmbeddedKafka(topics = { "send-credit" })
public class KafkaConsumerServiceTest {

    @InjectMocks
    private KafkaConsumerService kafkaConsumerService;

    @Mock
    private CreditCheckService creditCheckService;

    private String testMessage;

    @BeforeEach
    public void setUp() {
        testMessage = "{\"creditScore\": 700, \"userId\": 12345}";  // Example message
    }

    @Test
    public void testListener() throws JsonProcessingException {
        // Simulate receiving a message
        kafkaConsumerService.listner(testMessage);

        // Verify that creditCheckService.creditCheck() is called once with the correct argument
        verify(creditCheckService, times(1)).creditCheck(testMessage);
    }
}
