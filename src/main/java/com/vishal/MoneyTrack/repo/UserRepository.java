package com.vishal.MoneyTrack.repo;

import com.vishal.MoneyTrack.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    
    Optional<User> findByEmailId(String emailId);
    boolean existsByEmailId(String emailId);
    boolean existsByPhoneNumber(String phoneNumber);
}

