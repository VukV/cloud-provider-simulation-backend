package com.raf.cloudproviderbackend.repository;

import com.raf.cloudproviderbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    @Query("select distinct u from User u left join fetch u.roles")
    List<User> findAllUsers();
    Optional<User> findByUserId(Long userId);
    void deleteByUserId(Long userId);
}
