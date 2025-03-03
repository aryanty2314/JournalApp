package net.company.unique.Controller;

import net.company.unique.ApiResponse.WeatherResponse;
import net.company.unique.Entity.User;
import net.company.unique.Repository.UserRepository;
import net.company.unique.Service.UserService;
import net.company.unique.Service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.util.List;



@RestController
@RequestMapping("/user")
public class UserEntryController
{
    private UserService userService;
    private UserRepository userRepo;
    private WeatherService weatherService;

public UserEntryController(UserService userService, UserRepository userRepository, WeatherService weatherService)
{
    this.weatherService = weatherService;
    this.userService = userService;
    this.userRepo = userRepository;
}

    @GetMapping()
    public ResponseEntity<String> Greetings() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse = weatherService.getWeather("Mumbai");

        String greeting = "Weather Feels alike: N/A"; // Default message
        if (weatherResponse != null && weatherResponse.getCurrent() != null) {
            greeting = "Weather Feels alike: " + weatherResponse.getCurrent().getFeelslike();
        }

        String responseBody = "Hii " + authentication.getName() + ". " + greeting;

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteByUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        String username = authentication.getName();
        userRepo.deleteByUsername(username);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }
    @PutMapping
    public ResponseEntity<User> update(@RequestBody User user)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String Username = authentication.getName();
        User user1 = userService.findByUsername(Username);
        if ( user1 != null)
        {
          user1.setUsername(user.getUsername());
          user1.setPassword(user.getPassword());
          userService.save(user1);
          return new ResponseEntity<>(user1,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
