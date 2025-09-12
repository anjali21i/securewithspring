package com.ianjali.securewithspring.services;

import com.ianjali.securewithspring.dto.RegisteredUserResponse;
import com.ianjali.securewithspring.dto.UserRegistrationDTO;
import com.ianjali.securewithspring.entities.Roles;
import com.ianjali.securewithspring.entities.UserDetail;
import com.ianjali.securewithspring.repository.RoleRepository;
import com.ianjali.securewithspring.repository.UserDetailRepo;
import com.ianjali.securewithspring.utility.Constants;
import com.ianjali.securewithspring.utility.UtilityHelper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserDetailRepo userDetailRepo;

    @Autowired
    RoleRepository roleRepo;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;

    public UserDetail getUserDetailById(Long id) {
        return userDetailRepo.findById(id).orElse(null);
    }

    private void validateUserRegistration(UserRegistrationDTO dto) {
        if (UtilityHelper.isNullOrEmpty(dto.getPassword())) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        if (UtilityHelper.isNullOrEmpty(dto.getEmail())) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (UtilityHelper.isNullOrEmpty(dto.getUserName())) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (!UtilityHelper.isValidEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email format is invalid");
        }
    }

    @Transactional
    public RegisteredUserResponse registerUser(UserRegistrationDTO userRegisterBean) throws Exception {
        validateUserRegistration(userRegisterBean);

        if (userDetailRepo.findAll().stream().anyMatch(u -> u.getEmail().equals(userRegisterBean.getEmail()))) {
            throw new IllegalArgumentException("Email already exists!");
        }

        String encodedPassword = passwordEncoder.encode(userRegisterBean.getPassword());

        String roleName = userRegisterBean.getRoleName() != null ?
                userRegisterBean.getRoleName().toUpperCase() :
                Constants.RoleName.USER.toString().toUpperCase();

        Constants.RoleName enumRole = Constants.RoleName.valueOf(roleName.toUpperCase());

        Roles appliedRole = roleRepo.findByRoleName(enumRole)
                .orElseThrow(() -> new RuntimeException("Role not found"+ roleName));


        UserDetail userDetail = new UserDetail();
        userDetail.setUserName(userRegisterBean.getUserName());
        userDetail.setEmail(userRegisterBean.getEmail());
        userDetail.setEncPassword(encodedPassword);
        userDetail.setEnabled(Boolean.TRUE);
        userDetail.setAccountNonExpired(Boolean.TRUE);
        userDetail.setAccountNonLocked(Boolean.TRUE);
        userDetail.setCredentialsNonExpired(Boolean.TRUE);
        userDetail.setCreatedAt(LocalDateTime.now());
        userDetail.setUpdatedAt(LocalDateTime.now());

        Set<Roles> roles = new HashSet<>();
        roles.add(appliedRole);
        userDetail.setRoles(roles);
        UserDetail savedInfo =  userDetailRepo.save(userDetail);
        RegisteredUserResponse responseDTO = new RegisteredUserResponse();

        responseDTO.setUserId(savedInfo.getUserId());
        responseDTO.setUserName(savedInfo.getUserName());
        responseDTO.setEmail(savedInfo.getEmail());
        responseDTO.setRoles(
                savedInfo.getRoles().stream()
                        .map(role -> role.getRoleName().name())
                        .collect(Collectors.toSet())
        );

        responseDTO.setMessage("User registered successfully!");
        return responseDTO;
    }

    public String authenticateUser(UserRegistrationDTO user) {
        try {
            Authentication authenticatedUser = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );
            if(authenticatedUser.isAuthenticated()) {
                return jwtService.generateToken(user);
            } else {
                return "Authentication failed";
            }
        } catch (Exception e) {
            return "Authentication failed: " + e.getMessage();
        }
    }

    public String login(UserRegistrationDTO user) {
        return authenticateUser(user);
    }

}