package com.connect.api.repository;

import com.connect.api.model.User;
import com.connect.api.model.connection.Connection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long> {

    Optional<Connection> findByUser_IdAndConnection_Id(Long userId, Long connectionUserId);

    List<User> findConnectionsByUser(@NonNull User user);

    List<User> findConnectionsByUser(@NonNull User user, Pageable pageable);

}