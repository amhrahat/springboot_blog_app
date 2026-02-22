package com.rahat.blog.domain.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "verification_tokens")
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String otpHash;

    @Column(nullable = false)
    private LocalDateTime expiryDate;


    public VerificationToken() {
    }

    public VerificationToken(UUID id, String email, String otpHash, LocalDateTime expiryDate) {
        this.id = id;
        this.email = email;
        this.otpHash = otpHash;
        this.expiryDate = expiryDate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtpHash() {
        return otpHash;
    }

    public void setOtpHash(String otpHash) {
        this.otpHash = otpHash;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        VerificationToken that = (VerificationToken) o;
        return Objects.equals(id, that.id) && Objects.equals(email, that.email) && Objects.equals(otpHash, that.otpHash) && Objects.equals(expiryDate, that.expiryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, otpHash, expiryDate);
    }

    @Override
    public String toString() {
        return "VerificationToken{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", otpHash='" + otpHash + '\'' +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
