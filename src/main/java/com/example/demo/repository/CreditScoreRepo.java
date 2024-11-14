package com.example.demo.repository;

import com.example.demo.model.CreditScore;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditScoreRepo extends MongoRepository<CreditScore, String> {
    CreditScore findByPhoneNumber(long phoneNumber);
}
