package com.example.pawcare.services.user;


import com.example.pawcare.entities.Role;
import com.example.pawcare.entities.Roles;
import com.example.pawcare.entities.User;
import com.example.pawcare.repositories.auth.IRoleRepository;
import com.example.pawcare.repositories.auth.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.*;


@Service
@Slf4j
public class UserServiceImp implements UserDetailsService , IUserService{
    @Autowired
    IUserRepository iUserRepository;

    @Autowired
    private IRoleRepository roleRepository;



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
    public void deleteUser(Long id) {
        try {
            iUserRepository.deleteById(id);
            log.info("user deleted with id: "+id);
        }
        catch(Exception e) {
            log.info("erreur user delete : "+id);
        }

    }

    @Override
    public User updateUser(Long id, User updatedUser) {

        Optional<User> optionalUser = iUserRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setUsername(updatedUser.getUsername());
            user.setEmail(updatedUser.getEmail());

            return iUserRepository.save(user);
        }
        throw new RuntimeException("User not found with id " + id);
    }

    @Override
    public User retrieveUser(Long id) {
        return iUserRepository.findById(id).get();
    }

    @Override
    public List<User> getUsersByRoleName(String roleName) {
        List<User> users = iUserRepository.findAll();
        List<User> result = new ArrayList<>();
        for (User user : users) {
            Set<Role> roles = user.getRoles();
            for (Role role : roles) {
                if (role.getName().equals(Roles.valueOf(roleName))) {
                    result.add(user);
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public User updateUserRoles(Long userId, List<String> roleNames) {
        Optional<User> optionalUser = iUserRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Set<Role> roles = new HashSet<>();
            for (String roleName : roleNames) {
                Optional<Role> optionalRole = roleRepository.findByName(Roles.valueOf(roleName));
                if (optionalRole.isPresent()) {
                    roles.add(optionalRole.get());
                } else {
                    throw new RuntimeException("Role not found: " + roleName);
                }
            }
            user.setRoles(roles);

            iUserRepository.save(user);
            return user;
        }
        return null;
    }
    @Override
    public List<User> findAvailableDoctors(LocalDateTime startDate, LocalDateTime endDate) {
        return iUserRepository.findAvailableDoctors(startDate,endDate);
    }
    public  User GetDoctorByAptId(Long id){
        return iUserRepository.GetDoctorByAptId(id);
    }
    public  List<User>GetAllDoctors(){
        return iUserRepository.GetAllDoctors();
    }

    }



