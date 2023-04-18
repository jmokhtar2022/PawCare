package com.example.pawcare.controllers.auth;

import com.example.pawcare.entities.User;
import com.example.pawcare.repositories.auth.IRoleRepository;
import com.example.pawcare.repositories.auth.IUserRepository;
import com.example.pawcare.services.user.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserServiceImp userServiceImp;

    @Autowired
    IUserRepository iUserRepository;

    @Autowired
    IRoleRepository iRoleRepository;

    @GetMapping("/list")
    public List<User> retrieveAllUsers(){
        return userServiceImp.retrieveAllUsers();
    }
}
