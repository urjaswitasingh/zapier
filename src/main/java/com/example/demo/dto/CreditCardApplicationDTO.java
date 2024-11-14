package com.example.demo.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;


import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardApplicationDTO {

    private String applicationId;  // Unique application identifier

    @NotEmpty //checks if the value is not null and not empty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotNull
    private Date dateOfBirth;

    @NotBlank(message = "Email is required.")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Please provide a valid email address.")// regex for email validation
    private String email;

    @NotNull
    @Min(10)
    @Min(10)
    private long phoneNumber;

    @NotEmpty
    private String employmentStatus;  // Employed, Self-employed, Unemployed

    @NotNull
    private double annualSalary;

    @NotEmpty
    private String idProofName;

    @NotEmpty
    private String idProofNumber;

    private String ApplicationStatus;  // e.g., PENDING, APPROVED, REJECTED

    private Date applicationDate;  // Date the application was submitted

}

