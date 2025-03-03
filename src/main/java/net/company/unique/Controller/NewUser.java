package net.company.unique.Controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.company.unique.Entity.User;
import net.company.unique.Service.UserDetailsServiceImpl;
import net.company.unique.Service.UserService;
import net.company.unique.Utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/public")
public class NewUser
{
    private UserService userService;
    private AuthenticationManager authenticationManager;
    private UserDetailsServiceImpl userDetailsServiceImpl;
    private JwtUtil jwt;



    @GetMapping("health-check")
    public String healthCheck()
{
    return "ok";
}

    @PostMapping("/sign-up")
    public ResponseEntity<Object> signup(@RequestBody User user) {
        try {
            if (user.getUsername() == null || user.getPassword() == null) {
                return new ResponseEntity<>("Username or Password cannot be null", HttpStatus.BAD_REQUEST);
            }
            userService.save(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating user: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        try {


            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );

            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(user.getUsername());
            String token = jwt.generateToken(userDetails.getUsername());

            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Authentication failed: " + e.getMessage());
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }
    }
}
