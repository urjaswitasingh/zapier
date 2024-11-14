package com.example.demo.service;

import com.example.demo.dto.CreditCardApplicationDTO;
import com.example.demo.kafka.KafkaProducerService;
import com.example.demo.model.CreditCardApplication;
import com.example.demo.repository.CreditCardApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CreditCardApplicationService {

    @Autowired
    private CreditCardApplicationRepository creditCardApplicationRepository;
    @Autowired
    private KafkaProducerService kafkaProducerService;

    public CreditCardApplicationDTO submitApplication(CreditCardApplicationDTO applicationDTO) {
        CreditCardApplication creditCardApplication = new CreditCardApplication();
        //converting DTO to Entity to save into Database
        creditCardApplication.setFirstName(applicationDTO.getFirstName());
        creditCardApplication.setLastName(applicationDTO.getLastName());
        creditCardApplication.setDateOfBirth(applicationDTO.getDateOfBirth());
        creditCardApplication.setEmail(applicationDTO.getEmail());
        creditCardApplication.setPhoneNumber(applicationDTO.getPhoneNumber());
        creditCardApplication.setEmploymentStatus(applicationDTO.getEmploymentStatus());
        creditCardApplication.setAnnualSalary(applicationDTO.getAnnualSalary());
        creditCardApplication.setIdProofName(applicationDTO.getIdProofName());
        creditCardApplication.setIdProofNumber(applicationDTO.getIdProofNumber());
        creditCardApplication.setApplicationStatus("PENDING");
        creditCardApplication.setApplicationDate(new Date());

        //saving into Database
        try {
            CreditCardApplication savedApplication = creditCardApplicationRepository.save(creditCardApplication);
            if(savedApplication==null){
                throw new RuntimeException("Error saving application");
            }
            // sending to kafka to check credit
            kafkaProducerService.sendMessage(savedApplication);

            //converting Entity to DTO to return as response, setting applicationId, applicationStatus and applicationDate only other filed already in DTO
            applicationDTO.setApplicationId(savedApplication.getApplicationId());
            applicationDTO.setApplicationStatus(savedApplication.getApplicationStatus());
            applicationDTO.setApplicationDate(savedApplication.getApplicationDate());
            return applicationDTO;

        } catch (Exception e) {
            // Log the error
            throw new RuntimeException("Error saving application: " + e.getMessage());
        }

    }
}

