package net.engineeringdigest.journalApp.controller;

import org.springframework.http.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import net.engineeringdigest.journalApp.service.*;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.dto.UserDTO;
import net.engineeringdigest.journalApp.api.response.*;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("user")
@Tag(name = "User APIs", description = "Read, Update and Delete User")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private QuoteService quoteService;
    @Autowired
    private WeatherService weatherService;

    @GetMapping
    @Operation(summary = "Get greetings for a user")
    public ResponseEntity<?> greetings() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        QuoteResponse quoteResponse = quoteService.getQuote();
        StringBuilder greeting = new StringBuilder("Hi " + username + "!.");
        WeatherResponse weatherResponse = weatherService.getWeather("Kolkata");
        if (weatherResponse != null) {
            greeting.append("\nWeather feels like " + weatherResponse.getCurrent().getFeelslike());
        }
        if (quoteResponse != null) {
            greeting.append("\n" + quoteResponse.getQuote());
        }
        return new ResponseEntity<>(greeting.toString(), HttpStatus.OK);
    }

    @PutMapping
    @Operation(summary = "Update a user entry by username")
    public ResponseEntity<?> updateUserByName(@RequestBody UserDTO userDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User newEntry = userService.findByUsername(username);
        String email = userDTO.getEmail();
        String userName = userDTO.getUsername();
        String password = userDTO.getPassword();
        newEntry.setEmail(email != null && !email.equals("") ? email : newEntry.getEmail());
        newEntry.setUsername(userName != null && !userName.equals("") ? userName : newEntry.getUsername());
        newEntry.setPassword(password != null && !password.equals("") ? password : newEntry.getPassword());
        userService.updateEntry(newEntry);
        return new ResponseEntity<>(newEntry, HttpStatus.OK);
    }

    @DeleteMapping
    @Operation(summary = "Delete a user entry by username")
    public ResponseEntity<?> deleteUserByName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        userService.deleteByUsername(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
