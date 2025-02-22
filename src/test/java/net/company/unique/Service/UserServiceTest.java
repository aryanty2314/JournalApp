package net.company.unique.Service;

import net.company.unique.Entity.User;
import net.company.unique.Repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest
{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Disabled
    @ParameterizedTest
    @ArgumentsSource(UserArgumentsSource.class)
public void testFindByUsername(User user)
{
    assertTrue(userService.save(user));
}




@Disabled
@ParameterizedTest
@CsvSource({
        "1,1,2",
        "1,7,8",
        "4,4,5,"
})
public void Testing(int a, int b , int expected)
{
assertEquals(expected,a+b);
}
}
