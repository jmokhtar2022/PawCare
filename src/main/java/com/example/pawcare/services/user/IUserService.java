package com.example.pawcare.services.user;

import com.example.pawcare.entities.Role;
import com.example.pawcare.entities.User;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface IUserService {
     List<User> retrieveAllUsers();



     void deleteUser(Long id);

     User updateUser(Long id, User updatedUser);

     User retrieveUser(Long id);
     List<User> getUsersByRoleName(String roleName);




}
