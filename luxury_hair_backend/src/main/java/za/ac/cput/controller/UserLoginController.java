package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.UserLogin;
import za.ac.cput.services.UserLoginService;

import za.ac.cput.util.JwtUtil;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/userlogin")
@CrossOrigin(origins = "http://localhost:5173")
public class UserLoginController {

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private AuthenticationManager authenticationManager; // For authentication

    @Autowired
    private JwtUtil jwtUtil; // For JWT token generation

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserLogin request) {
        if (userLoginService.checkEmailExists(request.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists.");
        }

        UserLogin newUser = userLoginService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PostMapping("/read")
    public ResponseEntity<?> loginUser(@RequestBody UserLogin loginDetails) {
        try {
            // Authenticate the user using Spring Security
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDetails.getEmail(), loginDetails.getPassword())
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal(); // Get user details from authentication
            String token = jwtUtil.generateToken(userDetails); // Generate token

            // Retrieve the UserLogin details (including the userId) based on email
            UserLogin user = userLoginService.findByEmail(loginDetails.getEmail());
            if (user != null) {
                // Return the userId along with the success message and token
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Login successful!");
                response.put("userId", user.getUserId());
                response.put("token", token);
                response.put("userType", user.getUserType()); // Include user type in the response
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password.");
        }
    }

    @GetMapping("/email-exists")
    public ResponseEntity<Map<String, Boolean>> checkEmailExists(@RequestParam String email) {
        boolean exists = userLoginService.checkEmailExists(email);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody UserLogin userLogin){
        UserLogin updatedUser = userLoginService.update(userLogin);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }
}
