package com.example.pawcare.services.user;

import com.example.pawcare.entities.Role;
import com.example.pawcare.entities.User;
import org.springframework.data.repository.query.Param;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface IUserService {
     List<User> retrieveAllUsers();



     void deleteUser(Long id);

     User updateUser(Long id, User updatedUser);

     User retrieveUser(Long id);
     List<User> getUsersByRoleName(String roleName);
      User updateUserRoles(Long userId, List<String> roleNames);
      ;

     List<User> findAvailableDoctors(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
     User GetDoctorByAptId(Long id);
     List<User>GetAllDoctors();




}
