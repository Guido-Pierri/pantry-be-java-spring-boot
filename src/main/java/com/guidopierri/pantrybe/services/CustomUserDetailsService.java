package com.guidopierri.pantrybe.services;

import com.guidopierri.pantrybe.models.User;
import com.guidopierri.pantrybe.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    // Inject your user repository here
     private final UserRepository userRepository;

     public CustomUserDetailsService(UserRepository userRepository) {
         this.userRepository = userRepository;
     }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Here you can use the UserRepository to fetch the user from the database
         User user = userRepository.findByUsername(username);
         if (user == null) {
             throw new UsernameNotFoundException("User not found");
         }
         return new CustomUserDetails(user);
    }
}
