package net.company.unique.Controller;

import net.company.unique.Entity.User;
import net.company.unique.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class HealthCheck
{
    private UserService userService;

    public HealthCheck(UserService userService)
    {
        this.userService = userService;
    }

    @GetMapping("health-check")
    public String healthCheck()
{
    return "ok";
}

    @PostMapping
    public ResponseEntity<Object> newUser(@RequestBody User user) {
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

}
