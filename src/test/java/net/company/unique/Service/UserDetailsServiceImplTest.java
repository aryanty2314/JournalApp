package net.company.unique.Service;

import net.company.unique.Entity.User;
import net.company.unique.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserDetailsServiceImplTest {


    @InjectMocks
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Mock
    private UserRepository userRepository;

    @Disabled
    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Disabled
    @Test
    public void UserDetailsServiceImplTestCase() {
        // Arrange
        User mockUser = User.builder()
                .username("Sharad")
                .password("TyagiSharad")
                .roles(new ArrayList<>())
                .build();

        when(userRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn(Optional.ofNullable(mockUser));

        // Act
        UserDetails user = userDetailsServiceImpl.loadUserByUsername("Sharad");

        // Assert
        assertNotNull(user);
        assertEquals("Sharad", user.getUsername());
    }
}
