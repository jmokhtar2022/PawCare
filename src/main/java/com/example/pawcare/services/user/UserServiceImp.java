package com.example.pawcare.services.user;


import com.example.pawcare.entities.Role;
import com.example.pawcare.entities.User;
import com.example.pawcare.repositories.auth.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class UserServiceImp implements UserDetailsService , IUserService{
    @Autowired
    IUserRepository iUserRepository;



    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = iUserRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User Not Found with Username :"+ username));

        return UserDetailsImpl.build(user);
    }

    @Override
    public List<User> retrieveAllUsers() {
        return iUserRepository.findAll();
    }

    @Override
    public User addUser(User user) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public User retrieveUser(Long id) {
        return null;
    }

    @Override
    public List<User> findUsersByRole(Role role) {
        return null;
    }
}
