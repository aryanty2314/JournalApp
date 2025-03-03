package net.company.unique.Controller;

import net.company.unique.Entity.User;
import net.company.unique.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController
{

    @Autowired
    private UserService userService;

    @GetMapping("/all-users")
    public ResponseEntity<Object> getAllUsers()
    {
        List<User> all = userService.getAllUsers();
        if (all != null && !all.isEmpty())
        {
            return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/create-new-admin")
    public ResponseEntity<User> NewAdmin(@RequestBody User user)
    {
        userService.saveAdmin(user);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }


}
