package com.example.pawcare.services.user;


import com.example.pawcare.entities.User;
import com.example.pawcare.repositories.auth.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserServiceImp implements UserDetailsService {
    @Autowired
    IUserRepository iUserRepository;



    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = iUserRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User Not Found with Username :"+ username));

        return UserDetailsImpl.build(user);
    }
}
