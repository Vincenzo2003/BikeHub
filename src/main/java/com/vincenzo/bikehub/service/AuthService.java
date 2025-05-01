package com.vincenzo.bikehub.service;

import com.vincenzo.bikehub.exceptions.AccountAlreadyExistsException;
import com.vincenzo.bikehub.exceptions.AccountCreationException;
import com.vincenzo.bikehub.exceptions.AccountNotFoundException;
import com.vincenzo.bikehub.mapper.AccountMapper;
import com.vincenzo.bikehub.models.Account;
import com.vincenzo.bikehub.repository.AccountRepository;
import com.vincenzo.bikehub.security.JwtManager;
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
    public Account signUp(Account signUp){
        if (accountRepository.existsByUsernameOrEmail(signUp.getUsername(), signUp.getEmail())) {
            throw new AccountAlreadyExistsException();
        }
        com.vincenzo.bikehub.entity.Account accountEntity = new com.vincenzo.bikehub.entity.Account();
        accountEntity.setEmail(signUp.getEmail());
        accountEntity.setPassword(signUp.getPassword());
        //        accountEntity.setPassword(passwordEncoder.encode(signUpRequest.getPassword())); TODO: codificare la password
        accountEntity.setUsername(signUp.getUsername());
        accountEntity.setPhoneNumber(signUp.getPhoneNumber());
        accountEntity.setRole(signUp.getRole());
        try {
            accountEntity = accountRepository.saveAndFlush(accountEntity);
        } catch (DataIntegrityViolationException exc){
            throw new AccountCreationException();
        }
        return accountMapper.entityToModel(accountEntity);
    }

    @Transactional
    public String authenticate(Account account){
        com.vincenzo.bikehub.entity.Account accountEntity = accountRepository.findByUsername(
                account.getUsername()).orElseThrow(AccountNotFoundException::new);
        Account accountModel = accountMapper.entityToModel(accountEntity);
        return jwtManager.generateToken(accountModel.getId().toString(), accountModel.getRole().toString());
    }

}
