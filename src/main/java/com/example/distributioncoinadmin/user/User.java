package com.example.distributioncoinadmin.user;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(
        name = "users",
        uniqueConstraints = @UniqueConstraint(name = "uk_users_username", columnNames = "username"),
        indexes = @Index(name = "idx_users_enabled", columnList = "enabled")
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="username", nullable=false, length=100)
    private String username;

    @Column(name="password_hash", nullable=false, length=255)
    private String passwordHash;

    @Column(name="name", nullable=false, length=100)
    private String name;

    @Column(name="enabled", nullable=false)
    private boolean enabled = true;

    @CreationTimestamp
    @Column(name="created_at", nullable=false, columnDefinition = "datetime(6)")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name="updated_at", nullable=false, columnDefinition = "datetime(6)")
    private LocalDateTime updatedAt;
}
