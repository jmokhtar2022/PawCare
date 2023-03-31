package com.example.pawcare.services.user;

import com.example.pawcare.entities.Role;
import com.example.pawcare.entities.User;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface IUserService {
     abstract List<User> retrieveAllUsers();

     User addUser(User user);

     void deleteUser(Long id);

     User updateUser(User user);

     User retrieveUser(Long id);

     void register(User user, String siteURL) throws UnsupportedEncodingException;

     List<User> findUsersByRole(Role role);
}
