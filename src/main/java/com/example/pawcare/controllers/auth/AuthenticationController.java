package com.example.pawcare.controllers.auth;


import com.example.pawcare.entities.Role;
import com.example.pawcare.entities.Roles;
import com.example.pawcare.entities.User;
import com.example.pawcare.payload.Response.JwtResponse;
import com.example.pawcare.repositories.auth.IRoleRepository;
import com.example.pawcare.repositories.auth.IUserRepository;
import com.example.pawcare.payload.Request.SigninRequest;
import com.example.pawcare.payload.Request.SignupRequest;

import com.example.pawcare.payload.Response.MessageResponse;
import com.example.pawcare.security.jwt.JwtService;
import com.example.pawcare.services.user.EmailSenderService;
import com.example.pawcare.services.user.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    /*@Autowired
    AuthenticationManager authenticationManager;*/


    @Autowired
    IUserRepository iUserRepository;

    @Autowired
    IRoleRepository iRoleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    EmailSenderService emailSenderService;



    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody SigninRequest signinRequest)
    {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signinRequest.getUsername(), signinRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.generateJwtToken(authentication);

        UserDetailsImpl userDetails =(UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest){

        //check if username exit
        if (iUserRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        //check if username exit
        if (iUserRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        //Register
        User user = new User( signUpRequest.getUsername(),
                            signUpRequest.getEmail(),
                            passwordEncoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = iRoleRepository.findByName(Roles.ROLE_CLIENT)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        }else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = iRoleRepository.findByName(Roles.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "vet":
                        Role veterinaryRole = iRoleRepository.findByName(Roles.ROLE_VETERINARY)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(veterinaryRole);


                        break;
                    default:
                        Role clientRole = iRoleRepository.findByName(Roles.ROLE_CLIENT)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(clientRole);

                }
            });
        }
        user.setRoles(roles);
        iUserRepository.save(user);
        emailSenderService.sendEmail(user.getEmail(),"Welcome to Pawcare ", "Dear " +
                user.getUsername()+" \n Welcome to PawCare! Thank you for signing up. \n Best regards, \n PawCare Team");

        return ResponseEntity.ok(new MessageResponse("Marhbé bik!"));
    }

    @GetMapping("/findAvailableDoctors/{startDate}/{endDate}")
    public List<User> findAvailableDoctors(@PathVariable("startDate")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate
            , @PathVariable("endDate")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime EndDate) {
        return iUserRepository.findAvailableDoctors(startDate, EndDate);
    }

    @GetMapping("/GetDoctorByAptId/{id}")
    public User GetDoctorByAptId(@PathVariable("id") Long id)
    {
        return iUserRepository.GetDoctorByAptId(id);
    }

    @GetMapping("/GetAllDoctors")
    public List<User> GetAllDoctor()
    {
        return iUserRepository.GetAllDoctors();
    }

}
