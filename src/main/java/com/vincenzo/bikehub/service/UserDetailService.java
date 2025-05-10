package com.vincenzo.bikehub.service;

import com.vincenzo.bikehub.exceptions.AccountNotFoundException;
import com.vincenzo.bikehub.mapper.AccountMapper;
import com.vincenzo.bikehub.models.Account;
import com.vincenzo.bikehub.repository.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public UserDetailService(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws AccountNotFoundException {
        com.vincenzo.bikehub.entity.Account accountEntity = accountRepository.findByUsername(
                username).orElseThrow(AccountNotFoundException::new);
        Account account = accountMapper.entityToModel(accountEntity);
        return org.springframework.security.core.userdetails.User
                .withUsername(account.getUsername())
                .password(account.getPassword())
                .roles(account.getRole().toString())
                .build();
    }
}