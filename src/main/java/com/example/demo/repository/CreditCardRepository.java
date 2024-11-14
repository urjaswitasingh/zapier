package com.example.demo.repository;

import com.example.demo.model.CreditCard;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRepository extends MongoRepository<CreditCard, String> {
    CreditCard findByCardNumber(String cardNumber);
}

