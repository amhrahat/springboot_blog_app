package com.rahat.blog.repositories;

import com.rahat.blog.domain.entities.VerificationToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VerificationTokenRepository
        extends JpaRepository<VerificationToken, UUID> {

    @Query("SELECT v FROM VerificationToken v WHERE v.email = :email ORDER BY v.expiryDate DESC")
    Optional<VerificationToken> findLatestByEmail(@Param("email") String email);

    @Query("""
        SELECT v.email
        FROM VerificationToken v
        WHERE v.expiryDate < :now
    """)
    List<String> findExpiredEmails(@Param("now") LocalDateTime now);

    @Modifying
    @Transactional
    @Query("""
        DELETE FROM VerificationToken v
        WHERE v.expiryDate < :now
    """)
    void deleteAllExpired(@Param("now") LocalDateTime now);
}


