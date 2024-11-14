package com.example.demo;


import com.example.demo.model.OTP;
import com.example.demo.repository.OTPRepo;
import com.example.demo.service.OTPService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OTPServiceTest {

    @Mock
    private OTPRepo otpRepo; // Mock the OTPRepo dependenc

    @InjectMocks
    private OTPService otpService; // Inject mocks into OTPService

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }



    @Test
    public void testValidateOTP_Valid() {
        // Arrange
        String userId = "user123";
        int otpValue = 223456;
        OTP otp = new OTP();
        otp.setUserId(userId);
        otp.setOtp(otpValue);
        when(otpRepo.findByUserId(userId)).thenReturn(otp); // Mock repo to return OTP

        // Act
        boolean isValid = otpService.validateOTP(userId, otpValue);

        // Assert
        assertTrue(isValid); // Assert OTP is valid
        verify(otpRepo, times(1)).delete(otp); // Verify delete method called once
    }

}
