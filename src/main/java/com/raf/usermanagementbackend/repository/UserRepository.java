package com.raf.usermanagementbackend.repository;

import com.raf.usermanagementbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    Optional<User> findByUserId(Long userId);
    void deleteByUserId(Long userId);
}
