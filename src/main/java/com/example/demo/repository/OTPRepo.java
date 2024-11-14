package com.example.demo.repository;

import com.example.demo.model.OTP;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface OTPRepo extends MongoRepository<OTP, String> {
   OTP findByUserId(String userId);
    OTP deleteByUserId(String otp);
}
