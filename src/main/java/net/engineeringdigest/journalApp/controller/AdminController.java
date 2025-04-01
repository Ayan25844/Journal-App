package net.engineeringdigest.journalApp.controller;

import java.util.*;
import org.springframework.http.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.dto.UserDTO;
import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
@RequestMapping("admin")
@Tag(name = "Admin APIs", description = "Create Admin, Read User Details and Check Health")
public class AdminController {

    @Autowired
    private AppCache appCache;
    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(summary = "Get all user entries")
    public ResponseEntity<?> getAllUser() {
        List<User> all = userService.getAll();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("create-admin-user")
    @Operation(summary = "Create an admin entry")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        try {
            User newUser = new User();
            newUser.setEmail(userDTO.getEmail());
            newUser.setUsername(userDTO.getUsername());
            newUser.setPassword(userDTO.getPassword());
            newUser.setSentimentAnalysis(userDTO.isSentimentAnalysis());
            userService.saveAdmin(newUser);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("clear-app-cache")
    @Operation(summary = "Clear app cache")
    public ResponseEntity<?> clearAppCache() {
        appCache.loadConfig();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
