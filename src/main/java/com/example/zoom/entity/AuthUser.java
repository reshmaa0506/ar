package com.example.zoom.entity;
import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;


@Data
@Entity
public class AuthUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String role;

    private Boolean isDeleted;

    private Boolean isDisabled;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    private LocalDateTime lastLoggedInDate;

}
