package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;


import javax.xml.crypto.Data;
import java.util.List;

@Document(collection = "credit_cards")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreditCard {

    @Id
    private String id;
    private String cardNumber;// taking string because of the leading zeros, example: 0000123456789, long will remove the leading zeros
    private String cardType;  // e.g., VISA, MasterCard
    private String cardHolderName;
    private Data expirationDate;
    private double creditLimit;
    private double availableBalance;
    private String status;  // e.g., ACTIVE, INACTIVE, BLOCKED
    private int pin;  // 4-digit PIN

    @DBRef
    private CreditCardApplication creditCardApplication; // Reference to Customer entity


}

