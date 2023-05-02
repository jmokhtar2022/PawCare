package com.example.pawcare.controllers.auth;


import com.example.pawcare.entities.User;
import com.example.pawcare.repositories.auth.IRoleRepository;
import com.example.pawcare.repositories.auth.IUserRepository;
import com.example.pawcare.services.user.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/userslist")
    public List<Object[]> GetAllUsers(){
        return iUserRepository.GetAllUsers();
    }

    @GetMapping("/getUsersByRoleNative")
    public ResponseEntity<List<User>> getUsersByRole(@RequestParam("role") String roleName) {
        List<User> users =  userServiceImp.getUsersByRoleName(roleName);
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long userId, @RequestBody User userDetails) {
        User updatedUser = userServiceImp.updateUser(userId, userDetails);
        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedUser);
    }
    @DeleteMapping("/deleteuser")
    public void deleteUser(Long id){
        iUserRepository.deleteById(id);
    }

    @PutMapping("/update/{id}/roles")
    public ResponseEntity<User> updateUserRoles(@PathVariable(value = "id") Long userId, @RequestBody List<String> roleNames) {
        User updatedUser = userServiceImp.updateUserRoles(userId, roleNames);
        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedUser);
    }


}
