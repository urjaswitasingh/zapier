package com.example.demo.service;

import com.example.demo.model.OTP;
import com.example.demo.repository.OTPRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OTPService {
    @Autowired
    OTPRepo otpRepo;

    public int generateOTP(String UserId) {
        Random random = new Random();
        int generatedOTP = 100000 + random.nextInt(900000); // Generates a random number between 100000 and 999999
        OTP otp = new OTP();
        otp.setOtp(generatedOTP);
        otp.setUserId(UserId);
        otpRepo.save(otp);
        return generatedOTP;
    }

    public boolean validateOTP(String UserId, int OTP) {
        OTP otp = otpRepo.findByUserId(UserId);
        if(otp == null) {
            return false;
        }
        if(otp.getOtp() == OTP) {
            otpRepo.delete(otp);
            return true;
        }
        return false;
    }
}
