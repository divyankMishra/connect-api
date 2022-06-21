package com.connect.api.repository;

import com.connect.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    boolean existsByUsernameEquals(String username);

    boolean existsByEmailEqualsIgnoreCase(String email);

    User findByEmailIgnoreCase(String email);

    Set<User> findUsersByUsernameIn(List<String> username);

}