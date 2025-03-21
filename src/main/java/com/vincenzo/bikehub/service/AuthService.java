package com.vincenzo.bikehub.service;

import com.vincenzo.bikehub.exceptions.AccountAlreadyExistsException;
import com.vincenzo.bikehub.exceptions.AccountCreationException;
import com.vincenzo.bikehub.exceptions.AccountNotFoundException;
import com.vincenzo.bikehub.mapper.AccountMapper;
import com.vincenzo.bikehub.models.Account;
import com.vincenzo.bikehub.repository.AccountRepository;
import com.vincenzo.bikehub.security.JwtManager;
import com.vincenzo.bikehub.server.gen.model.LoginRequest;
import com.vincenzo.bikehub.server.gen.model.LoginResponse;
import com.vincenzo.bikehub.server.gen.model.SignUpRequest;
import com.vincenzo.bikehub.server.gen.model.SignUpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AuthService {

    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;
    private final JwtManager jwtManager;

    @Autowired
    public AuthService(
            AccountMapper accountMapper,
            AccountRepository accountRepository,
            JwtManager jwtManager
    ) {
        this.accountMapper = accountMapper;
        this.accountRepository = accountRepository;
        this.jwtManager = jwtManager;
    }

    @Transactional
    public SignUpResponse signUp(SignUpRequest signUpRequest){
        if (accountRepository.existsByUsernameOrEmail(signUpRequest.getUsername(), signUpRequest.getEmail())) {
            throw new AccountAlreadyExistsException();
        }
        com.vincenzo.bikehub.entity.Account accountEntity = accountMapper.signUpRequestToEntity(signUpRequest);
//        accountEntity.setPassword(passwordEncoder.encode(signUpRequest.getPassword())); TODO: codificare la password

        try {
            accountEntity = accountRepository.saveAndFlush(accountEntity);
        } catch (DataIntegrityViolationException exc){
            throw new AccountCreationException();
        }
        Account createdAccountModel = accountMapper.entityToModel(accountEntity);
        return accountMapper.modelToApi(createdAccountModel);
    }

    @Transactional
    public LoginResponse authenticate(LoginRequest loginRequest){
        Account accountModel = accountMapper.loginRequestToModel(loginRequest);
        com.vincenzo.bikehub.entity.Account accountEntity = accountRepository.findByUsername(
                accountModel.getUsername()).orElseThrow(AccountNotFoundException::new);
        accountModel = accountMapper.entityToModel(accountEntity);
        String generatedToken = jwtManager.generateToken(accountModel.getId().toString(), accountModel.getRole().toString());
        LoginResponse response = new LoginResponse();
        response.setAccessToken(generatedToken);
        return response;
    }

}
