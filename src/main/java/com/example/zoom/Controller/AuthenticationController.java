package com.example.zoom.Controller;

import com.example.zoom.Dto.AuthRequestDTO;
import com.example.zoom.Dto.AuthResponseDTO;
import com.example.zoom.Service.AuthenticationService;
import com.example.zoom.utils.AppResponse;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static com.example.zoom.utils.ValidationProcessor.processBindingResult;

@RestController
@RequestMapping(value = "/api")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationServiceParam) {
        this.authenticationService = authenticationServiceParam;
    }

    @PostMapping(value = "/authenticate")
    public AppResponse<AuthResponseDTO> authenticate(
            @RequestBody @Valid final AuthRequestDTO authRequest,
            final BindingResult bindingResult) {
        List<String> errors = processBindingResult(bindingResult);
        if (errors.size() > 0) {
            return AppResponse.<AuthResponseDTO>builder()
                    .message(errors.toString())
                    .success(false)
                    .build();
        }
        AuthResponseDTO response = authenticationService.loginCheck(authRequest);
        if (response != null) {
            return AppResponse.<AuthResponseDTO>builder()
                    .data(response)
                    .message("Successfully authenticated user.")
                    .success(true)
                    .build();
        }
        return AppResponse.<AuthResponseDTO>builder()
                .success(false)
                .message("Authentication error, please check provided email or password!")
                .build();
    }
}
