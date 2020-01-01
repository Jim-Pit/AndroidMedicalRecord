package com.iatrikhplhroforia.emf.authJwt;

import com.iatrikhplhroforia.emf.user.FirebaseUserRepo;
import com.iatrikhplhroforia.emf.user.RegisteredUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUDetailsService implements UserDetailsService {
    @Autowired
    FirebaseUserRepo repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        RegisteredUser user = repository.findByEmail(email);    // Method with no implementations, Spring knows what 's its purpose
        JwtUDetails userDetails;
        if (user != null) {
            userDetails = new JwtUDetails();
            userDetails.setUser(user);
        } else {
            throw new UsernameNotFoundException("User with email : " + email + " does not exist");
        }
        return userDetails;
    }
}
