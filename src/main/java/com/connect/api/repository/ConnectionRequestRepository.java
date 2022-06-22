package com.connect.api.repository;

import com.connect.api.model.User;
import com.connect.api.model.connection.ConnectionRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectionRequestRepository extends JpaRepository<ConnectionRequest, Long> {
    Page<ConnectionRequest> findByReceiver_Username(String username, Pageable pageable);

    Page<ConnectionRequest> findBySender_Username(String username, Pageable pageable);



    @Query("""
            select (count(c) > 0) from ConnectionRequest c
            where c.sender = ?1 and c.receiver = ?2 or c.receiver = ?2 and c.sender = ?1""")
    boolean existsBySenderAndReceiver(User sender, User receiver);

}