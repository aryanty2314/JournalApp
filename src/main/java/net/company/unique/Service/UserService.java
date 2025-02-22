package net.company.unique.Service;
import lombok.extern.slf4j.Slf4j;
import net.company.unique.Entity.User;
import net.company.unique.Repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class UserService {

    private UserRepository userRepo;

    public UserService(UserRepository userRepository)
    {
        this.userRepo = userRepository;
    }

//    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private static final PasswordEncoder passwordencoder = new BCryptPasswordEncoder();

    //Save or Update a User
    public boolean save(User user)
    {
        try{
            user.setPassword(passwordencoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepo.save(user);
            return true;
        }
        catch (Exception e)
        {
            log.error("HEHEHEHE");
            log.info("HEHEHEHEHEHEHEHEHE");
            log.warn("HEHEHEHEHEHEHE");
            log.trace("HEHEHEHEHEHEHE");
            log.debug("HEHEHEHEHEHEHEHEHEHEHEHE");
            return false;
        }
    }

    public void saveNew(User user)
    {
        userRepo.save(user);
    }

    // Fetch all users
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    // Get user by username
    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public void saveAdmin(User user)
    {
        user.setPassword(passwordencoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        userRepo.save(user);
    }
}
