package com.vincenzo.bikehub.controller;
import com.vincenzo.bikehub.server.gen.controller.AuthApi;
import com.vincenzo.bikehub.server.gen.model.LoginRequest;
import com.vincenzo.bikehub.server.gen.model.LoginResponse;
import com.vincenzo.bikehub.server.gen.model.SignUpRequest;
import com.vincenzo.bikehub.server.gen.model.SignUpResponse;
import com.vincenzo.bikehub.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController implements AuthApi {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.authenticate(loginRequest));
    }

    @Override
    public ResponseEntity<SignUpResponse> signUp(SignUpRequest signUpRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signUp(signUpRequest));
    }

}
