package pl.jhonylemon.memewebsite.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.jhonylemon.memewebsite.entity.Account;
import pl.jhonylemon.memewebsite.exception.account.AccountNotFoundException;
import pl.jhonylemon.memewebsite.repository.AccountRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findByEmail(username)
                .orElseThrow(()->{throw new UsernameNotFoundException("Username "+username+" not found");});
    }


    public Account currentUser(){
        return accountRepository.findByEmail(
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName()
                )
                .orElseThrow(()->{throw new AccountNotFoundException();});
    }


}
