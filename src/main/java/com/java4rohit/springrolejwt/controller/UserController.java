package com.java4rohit.springrolejwt.controller;

import com.java4rohit.springrolejwt.config.TokenProvider;
import com.java4rohit.springrolejwt.model.AuthToken;
import com.java4rohit.springrolejwt.model.LoginUser;
import com.java4rohit.springrolejwt.model.User;
import com.java4rohit.springrolejwt.model.UserDto;
import com.java4rohit.springrolejwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtTokenUtil;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> generateToken(@RequestBody LoginUser loginUser) throws AuthenticationException {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        return ResponseEntity.ok(new AuthToken(token));
    }

    @RequestMapping(value="/register", method = RequestMethod.POST)
    public User saveUser(@RequestBody UserDto user){
        return userService.save(user);
    }

    @PreAuthorize("hasRole('ABM')")
    @RequestMapping(value="/shipment-details", method = RequestMethod.GET)
    public String adminPing(){
        return "Client ABM Can Read This";
    }

    @PreAuthorize("hasRole('WAVE')")
    @RequestMapping(value="/cartonStatus", method = RequestMethod.GET)
    public String userPing(){
        return "Client WAVE Can Read This";
    }

}
