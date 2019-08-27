package com.example.zoom.Dto;

import lombok.Data;

@Data
public class AuthResponseDTO {

    private Long userId;

    private Long parentUserId;

    private String fullName;

    private String userName;

    private String companyName;

    private String email;

    private String role;

    private String imageUrl;

    private String token;

    private Boolean isCallTaker;
}
