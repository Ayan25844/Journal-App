package net.engineeringdigest.journalApp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import net.engineeringdigest.journalApp.service.*;
import net.engineeringdigest.journalApp.dto.UserDTO;
import net.engineeringdigest.journalApp.entity.User;
import org.springframework.security.authentication.*;
import net.engineeringdigest.journalApp.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

@Slf4j
@RestController
@RequestMapping("/public")
@Tag(name = "Public APIs", description = "Create User and Check Health")
public class PublicController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("signup")
    @Operation(summary = "Signup for a user")
    public ResponseEntity<?> signup(@RequestBody UserDTO userDTO) {
        try {
            User newUser = new User();
            newUser.setEmail(userDTO.getEmail());
            newUser.setUsername(userDTO.getUsername());
            newUser.setPassword(userDTO.getPassword());
            newUser.setSentimentAnalysis(userDTO.isSentimentAnalysis());
            userService.saveEntry(newUser);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("login")
    @Operation(summary = "Login for a user")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
        try {
            authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(userDTO.getUsername());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception occured while createAuthenticationToken: ", e);
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("health-check")
    @Operation(summary = "Check system health")
    public String healthCheck() {
        return "Ok";
    }
}
