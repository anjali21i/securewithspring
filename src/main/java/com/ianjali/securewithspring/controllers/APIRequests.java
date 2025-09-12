package com.ianjali.securewithspring.controllers;

import com.ianjali.securewithspring.dto.RegisteredUserResponse;
import com.ianjali.securewithspring.dto.UserRegistrationDTO;
import com.ianjali.securewithspring.entities.UserDetail;
import com.ianjali.securewithspring.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class APIRequests {

    private final UserService userService;

    @Autowired
    public APIRequests(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetail> getUserDetailById(@PathVariable Long id) {
        if (id <= 0) {
            return ResponseEntity.badRequest().build();
        }
        UserDetail userDetail= userService.getUserDetailById(id);
        if(userDetail==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userDetail);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserRegistrationDTO userDetail) {
        try {
            RegisteredUserResponse savedUser = userService.registerUser(userDetail);
            return ResponseEntity.ok(savedUser);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong! \n"+e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody @Valid UserRegistrationDTO userDetail) {
        if (userDetail.getEmail() == null || userDetail.getPassword() == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Email and password must not be null"));
        }

        String result = userService.login(userDetail);

        return ResponseEntity.ok(Map.of("message", result));
    }
}
