package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name is required")
    public String name;
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid Email Format")
    public String email;
    @NotBlank(message = "Strong Password of length upto 6 char required")
    @Size(min = 6, message = "Password length should be upto 6")
//    @JsonIgnore
    public String password;
    @NotBlank(message = "Phone number cannot be empty")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be exactly 10 digits")
    public String number;
    public String role="0";
}
