package com.example.pawcare.payload.Response;

import com.example.pawcare.services.user.UserDetailsImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class JwtResponse {
    private String jwt;
    private UserDetailsImpl userDetails;

    public JwtResponse(String jwt, UserDetailsImpl userDetails) {
        this.jwt=jwt;
        this.userDetails= userDetails;
    }
}
