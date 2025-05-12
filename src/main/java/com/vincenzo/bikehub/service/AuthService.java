package com.vincenzo.bikehub.service;

import com.vincenzo.bikehub.exceptions.AccountAlreadyExistsException;
import com.vincenzo.bikehub.exceptions.AccountCreationException;
import com.vincenzo.bikehub.exceptions.AccountNotFoundException;
import com.vincenzo.bikehub.exceptions.InvalidCredentialsException;
import com.vincenzo.bikehub.mapper.AccountMapper;
import com.vincenzo.bikehub.models.Account;
import com.vincenzo.bikehub.repository.AccountRepository;
import com.vincenzo.bikehub.security.JwtManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AuthService {

    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;
    private final JwtManager jwtManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(
            AccountMapper accountMapper,
            AccountRepository accountRepository,
            JwtManager jwtManager,
            PasswordEncoder passwordEncoder
    ) {
        this.accountMapper = accountMapper;
        this.accountRepository = accountRepository;
        this.jwtManager = jwtManager;
        this.passwordEncoder = passwordEncoder;
    }

    public com.vincenzo.bikehub.entity.Account getAccountEntity(String username) {
        return accountRepository.findByUsername(username)
                .orElseThrow(AccountNotFoundException::new);
    }

    public Account getAccount(String username) {
        return accountMapper.entityToModel(getAccountEntity(username));
    }

    @Transactional
    public Account signUp(Account signUp){
        if (accountRepository.existsByUsernameOrEmail(signUp.getUsername(), signUp.getEmail())) {
            throw new AccountAlreadyExistsException();
        }
        com.vincenzo.bikehub.entity.Account accountEntity = new com.vincenzo.bikehub.entity.Account();
        accountEntity.setEmail(signUp.getEmail());
        accountEntity.setPassword(passwordEncoder.encode(signUp.getPassword()));
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
    public String authenticate(String loginUsername, String loginPassword) {
        Account account = getAccount(loginUsername);
        if (!passwordEncoder.matches(loginPassword, account.getPassword())) {
            throw new InvalidCredentialsException();
        }
            return jwtManager.generateToken(account.getUsername(), account.getRole().toString());
    }
}
