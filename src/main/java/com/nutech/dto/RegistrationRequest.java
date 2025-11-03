package com.nutech.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistrationRequest {
    @NotBlank(message = "Email tidak boleh kosong")
    @Email(message = "Format email tidak valid")
    private String email;

    @NotBlank(message = "First name tidak boleh kosong")
    @Size(max = 50, message = "First name maksimal 50 karakter")
    private String firstName;

    @NotBlank(message = "Last name tidak boleh kosong")
    @Size(max = 50, message = "Last name maksimal 50 karakter")
    private String lastName;

    @NotBlank(message = "Password tidak boleh kosong")
    @Size(min = 8, message = "Password minimal 8 karakter")
    private String password;
}