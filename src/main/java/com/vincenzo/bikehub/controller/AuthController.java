package com.vincenzo.bikehub.controller;
import com.vincenzo.bikehub.mapper.AccountMapper;
import com.vincenzo.bikehub.models.Account;
import com.vincenzo.bikehub.server.gen.controller.AuthApi;
import com.vincenzo.bikehub.server.gen.model.AuthLogin;
import com.vincenzo.bikehub.server.gen.model.Login;
import com.vincenzo.bikehub.server.gen.model.SignUp;
import com.vincenzo.bikehub.server.gen.model.SignUp201Response;
import com.vincenzo.bikehub.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController implements AuthApi {

    private final AuthService authService;
    private final AccountMapper accountMapper;

    @Autowired
    public AuthController(AuthService authService, AccountMapper accountMapper) {
        this.authService = authService;
        this.accountMapper = accountMapper;
    }

    @Override
    public ResponseEntity<AuthLogin> login(Login loginRequest) {
        Account accountModel = accountMapper.loginToModel(loginRequest);
        String accessToken = authService.authenticate(accountModel);
        AuthLogin response = new AuthLogin();
        response.setAccessToken(accessToken);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<SignUp201Response> signUp(SignUp signUpRequest) {
        Account accountToBeCreated = accountMapper.signUpRequestToModel(signUpRequest);
        Account createdAccount = authService.signUp(accountToBeCreated);
        SignUp201Response response = new SignUp201Response();
        response.setId(createdAccount.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
