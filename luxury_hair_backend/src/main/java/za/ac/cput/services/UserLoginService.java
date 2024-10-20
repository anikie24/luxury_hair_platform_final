package za.ac.cput.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.UserLogin;
import za.ac.cput.repository.UserLoginRepository;
import za.ac.cput.util.JwtUtil;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserLoginService implements UserDetailsService {

    private final UserLoginRepository repo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserLoginService(UserLoginRepository repository, @Lazy PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.repo = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public List<UserLogin> findAll() {
        return repo.findAll();
    }

    public UserLogin create(UserLogin userLogin) {
        userLogin.setPassword(passwordEncoder.encode(userLogin.getPassword()));
        return repo.save(userLogin);
    }

    public UserLogin findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public UserLogin update(UserLogin userLogin) {
        return repo.save(userLogin);
    }

    public UserLogin findByEmail(String email) {
        return repo.findByEmail(email);
    }

    public boolean checkEmailExists(String email) {
        UserLogin user = repo.findByEmail(email);
        return user != null;
    }

    public boolean validatePassword(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }

    public String authenticateUser(String email, String rawPassword) {
        UserLogin user = repo.findByEmail(email);
        if (user != null && validatePassword(rawPassword, user.getPassword())) {
            // Generate JWT token using user's email and userType (role)
            return jwtUtil.generateToken(user.getEmail(), user.getUserType());
        }
        return null;
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserLogin user = repo.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found " + username);
        }

        // Add user role as a SimpleGrantedAuthority
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getUserType())); // "Admin" or "Customer"

        // Return Spring Security User object with email, password, and role
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    public String generateTokenForUser(UserLogin user) {
        // Generate a JWT token for a given user based on their email and userType
        return jwtUtil.generateToken(user.getEmail(), user.getUserType());
    }
}

