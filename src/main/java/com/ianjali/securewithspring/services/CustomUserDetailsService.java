package com.ianjali.securewithspring.services;

import com.ianjali.securewithspring.CustomUserDetails;
import com.ianjali.securewithspring.entities.UserDetail;
import com.ianjali.securewithspring.repository.UserDetailRepo;
import com.ianjali.securewithspring.utility.UtilityHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserDetailRepo userDetailRepo;

    @Override
    public UserDetails loadUserByUsername(String userData) throws UsernameNotFoundException {
        if(userData == null || userData.isEmpty()) {
            throw new IllegalArgumentException("Email or username cannot be null or empty");
        }
        UserDetail user = null;
        if (UtilityHelper.isValidEmail(userData)) {
            user = userDetailRepo.findByEmailIgnoreCase(userData).orElse(null);
        } else {
            user = userDetailRepo.findByUserNameIgnoreCase(userData).orElse(null);
        }

        if(user == null) {
            throw new UsernameNotFoundException("User not found with email: " + userData);
        }
        return new CustomUserDetails(user);
    }
}
