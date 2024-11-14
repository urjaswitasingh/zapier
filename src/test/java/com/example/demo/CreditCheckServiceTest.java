package com.example.demo;

import com.example.demo.kafka.KafkaProducerService;
import com.example.demo.kafkamessagetemplate.CreditCheckMessage;
import com.example.demo.kafkamessagetemplate.CreditScoreReceiveMessage;
import com.example.demo.model.CreditScore;
import com.example.demo.repository.CreditScoreRepo;
import com.example.demo.service.CreditCheckService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CreditCheckServiceTest {

    @Mock
    private CreditScoreRepo creditScoreRepo; // Mock the CreditScoreRepo dependency, Mock means it is a fake object for testing

    @Mock
    private KafkaProducerService kafkaProducerService; // Mock the KafkaProducerService dependency

    @InjectMocks
    private CreditCheckService creditCheckService; // Inject mocks into CreditCheckService, InjectMocks means it is the real object, and we are injecting above mocked classes into it

    private ObjectMapper objectMapper; // ObjectMapper for JSON processing, it is used to convert JSON to Java object and vice versa

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
        objectMapper = new ObjectMapper(); // Initialize ObjectMapper
    }

    @Test
    public void testCreditCheck_NewCreditScore() throws JsonProcessingException {
        // creating a CreditCheckMessage object for testing
        CreditCheckMessage creditCheckMessage = new CreditCheckMessage(1234567890L, 75000, 3);
        String message = objectMapper.writeValueAsString(creditCheckMessage);
        when(creditScoreRepo.findByPhoneNumber(creditCheckMessage.getPhoneNumber())).thenReturn(null); // when findByPhoneNumber is found it will take that value else it will return null

        // Act
        creditCheckService.creditCheck(message); // Call the method under test

        // ArgumentCaptor is used to capture the arguments passed to a method or not.
        ArgumentCaptor<CreditScore> creditScoreCaptor = ArgumentCaptor.forClass(CreditScore.class);
        verify(creditScoreRepo, times(1)).save(creditScoreCaptor.capture()); // Verify save method called once
        CreditScore savedCreditScore = creditScoreCaptor.getValue();
        assertEquals(1234567890L, savedCreditScore.getPhoneNumber()); // Assert phone number
        assertEquals(450, savedCreditScore.getCreditScore()); // Assert credit score

        ArgumentCaptor<CreditScoreReceiveMessage> messageCaptor = ArgumentCaptor.forClass(CreditScoreReceiveMessage.class);
        verify(kafkaProducerService, times(1)).sendMessage(messageCaptor.capture()); // Verify sendMessage called once
        CreditScoreReceiveMessage sentMessage = messageCaptor.getValue();
        assertEquals(1234567890L, sentMessage.getPhoneNumber()); // Assert phone number
        assertEquals(450, sentMessage.getCreditScore()); // Assert credit score
    }

    @Test
    public void testCreditCheck_ExistingCreditScore() throws JsonProcessingException {
        // Arrange
        CreditCheckMessage creditCheckMessage = new CreditCheckMessage(1234567890L, 75000, 3);
        String message = objectMapper.writeValueAsString(creditCheckMessage);
        CreditScore existingCreditScore = new CreditScore();
        existingCreditScore.setPhoneNumber(1234567890L);
        existingCreditScore.setCreditScore(300);
        when(creditScoreRepo.findByPhoneNumber(creditCheckMessage.getPhoneNumber())).thenReturn(existingCreditScore); // Mock repo to return existing credit score

        // Act
        creditCheckService.creditCheck(message); // Call the method under test

        // Assert
        verify(creditScoreRepo, never()).save(any(CreditScore.class)); // Verify save method never called

        ArgumentCaptor<CreditScoreReceiveMessage> messageCaptor = ArgumentCaptor.forClass(CreditScoreReceiveMessage.class);
        verify(kafkaProducerService, times(1)).sendMessage(messageCaptor.capture()); // Verify sendMessage called once
        CreditScoreReceiveMessage sentMessage = messageCaptor.getValue();
        assertEquals(1234567890L, sentMessage.getPhoneNumber()); // Assert phone number
        assertEquals(300, sentMessage.getCreditScore()); // Assert credit score
    }

    @Test
    public void testGenerateCreditScore() {
        // Test case 1: Salary > 200000, numCreditCards >= 2
        int score = creditCheckService.generateCreditScore(250000, 3);
        assertEquals(800, score); // Assert credit score

        // Test case 2: Salary > 50000, numCreditCards >= 2
        score = creditCheckService.generateCreditScore(100000, 2);
        assertEquals(450, score); // Assert credit score

        // Test case 3: Salary <= 50000, numCreditCards >= 2
        score = creditCheckService.generateCreditScore(40000, 2);
        assertEquals(350, score); // Assert credit score

        // Test case 4: Salary > 200000, numCreditCards < 2
        score = creditCheckService.generateCreditScore(250000, 1);
        assertEquals(500, score); // Assert credit score

        // Test case 5: Salary > 50000, numCreditCards < 2
        score = creditCheckService.generateCreditScore(100000, 1);
        assertEquals(150, score); // Assert credit score

        // Test case 6: Salary <= 50000, numCreditCards < 2
        score = creditCheckService.generateCreditScore(40000, 1);
        assertEquals(50, score); // Assert credit score
    }
}
