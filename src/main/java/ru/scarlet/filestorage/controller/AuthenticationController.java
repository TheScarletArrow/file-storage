package ru.scarlet.filestorage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.scarlet.filestorage.dto.AuthResponse;
import ru.scarlet.filestorage.dto.AuthenticationRequest;
import ru.scarlet.filestorage.dto.UserDto;
//import ru.scarlet.filestorage.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

//    private final AuthService authService;
//
//    @PostMapping("/signup")
//    public ResponseEntity<AuthResponse> signup(@RequestBody UserDto signUpRequest){
//        return ResponseEntity.ok(authService.signup(signUpRequest));
//    }
//
//    @PostMapping("/authenticate")
//    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest){
//
//        return ResponseEntity.ok(authService.authenticate(authenticationRequest));
//    }

//    @GetMapping("/currentUser")
//    public ResponseEntity<Object> getCurrent(){
//        System.out.println("1"+SecurityContextHolder.getContext().getAuthentication());
//        return ResponseEntity.ok(SecurityContextHolder.getContext().getAuthentication());
//    }
}
