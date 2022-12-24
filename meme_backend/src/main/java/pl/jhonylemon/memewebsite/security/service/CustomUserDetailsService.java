package pl.jhonylemon.memewebsite.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.jhonylemon.memewebsite.entity.Account;
import pl.jhonylemon.memewebsite.entity.AccountPermission;
import pl.jhonylemon.memewebsite.repository.AccountRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        try {
            return accountRepository.findByEmail(
                            SecurityContextHolder.getContext()
                                    .getAuthentication()
                                    .getName()
                    )
                    .orElse(null);
        }catch (Exception e){
            return null;
        }
    }

    public List<String> currentUserPermissions(){
        Account account = currentUser();
        if(account==null){
            return new ArrayList<>();
        }else{
            return account.getAccountRole().getPermissions().stream()
                    .map(AccountPermission::getPermission)
                    .collect(Collectors.toList());
        }
    }

}
