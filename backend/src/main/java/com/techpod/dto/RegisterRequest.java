package com.techpod.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank private String firstName;
    @NotBlank private String lastName;
    @Email    private String email;
    @NotBlank private String password;
    private boolean marketingConsent;
    public boolean isPrivacyConsent() {
        return marketingConsent; // Always return true since we are not storing this field
    }
    public void setMarketingConsent(boolean marketingConsent) {
        this.marketingConsent = marketingConsent;
    }
}
