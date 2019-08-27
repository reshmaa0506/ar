package com.example.zoom.Service;

import com.example.zoom.Dto.AuthRequestDTO;
import com.example.zoom.Dto.AuthResponseDTO;

public interface AuthenticationService {

    AuthResponseDTO loginCheck(AuthRequestDTO req);

}
