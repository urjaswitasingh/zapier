package com.example.demo.dto;

import com.example.demo.model.CreditCardApplication;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.xml.crypto.Data;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardDTO {

    private String id;
    @NotEmpty
    private String cardNumber;// taking string because of the leading zeros, example: 0000123456789, long will remove the leading zeros
    @NotEmpty
    private String cardType;  // e.g., VISA, MasterCard
    @NotEmpty
    private String cardHolderName;
    @NotNull
    private Data expirationDate;
    @NotNull
    private double creditLimit;
    @NotNull
    private double availableBalance;
    @NotEmpty
    private String status;  // e.g., ACTIVE, INACTIVE, BLOCKED
    private int pin;  // 4-digit PIN
    private CreditCardApplication creditCardApplication; // Reference to Customer entity


}

