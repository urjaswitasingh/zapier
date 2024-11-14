package com.example.demo.repository;

import com.example.demo.model.CreditCardApplication;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardApplicationRepository extends MongoRepository<CreditCardApplication, String> {

}
