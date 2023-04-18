package com.example.pawcare.services.user;

import com.example.pawcare.entities.Role;
import com.example.pawcare.entities.User;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface IUserService {
     List<User> retrieveAllUsers();

     User addUser(User user);

     void deleteUser(Long id);

     User updateUser(User user);

     User retrieveUser(Long id);



     List<User> findUsersByRole(Role role);
}
