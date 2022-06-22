package com.connect.api.model.connection;

import com.connect.api.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Connection", uniqueConstraints = {
        @UniqueConstraint(name = "uc_connection_user_connection", columnNames = {"user", "connection"})
})
public class Connection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user", nullable = false)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "connection", nullable = false)
    private User connection;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public User getConnection() {
        return connection;
    }

    public void setConnection(User rightUser) {
        this.connection = rightUser;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User leftUser) {
        this.user = leftUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
