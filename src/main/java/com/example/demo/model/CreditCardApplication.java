package com.example.demo.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "credit_card_applications")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardApplication {

    @Id
    private String applicationId;  // Unique application identifier
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotNull
    private Date dateOfBirth;
    @NotEmpty
    private String email;
    @NotEmpty
    @Min(10)
    @Max(10)
    private long phoneNumber;
    @NotEmpty
    private String employmentStatus;  // Employed, Self-employed, Unemployed
    @NotNull
    private double annualSalary;
    @NotEmpty
    private String idProofName;
    @NotEmpty
    private String idProofNumber;
    @NotEmpty
    private String ApplicationStatus;  // e.g., PENDING, APPROVED, REJECTED
    private Date applicationDate;  // Date the application was submitted

}

