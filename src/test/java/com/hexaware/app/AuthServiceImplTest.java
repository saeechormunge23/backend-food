package com.hexaware.app;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.hexaware.app.Dao.UserRepository;
import com.hexaware.app.Dto.SignupRequest;
import com.hexaware.app.Dto.User;
import com.hexaware.app.Enums.UserRole;
import com.hexaware.app.Service.AuthServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser_Success() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setName("John Doe");
        signupRequest.setEmail("john@example.com");
        signupRequest.setPassword("password123");
        signupRequest.setUserRole(null);  // Default to USER

        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setName("John Doe");
        mockUser.setEmail("john@example.com");
        mockUser.setUserRole(UserRole.USER);

        // Mocking password encoding
        when(passwordEncoder.encode(signupRequest.getPassword())).thenReturn("encodedPassword123");
        
        // Mocking the repository save method
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        User createdUser = authService.createUser(signupRequest);

        // Assertions to verify the user creation logic
        assertNotNull(createdUser);
        assertEquals("John Doe", createdUser.getName());
        assertEquals("john@example.com", createdUser.getEmail());
        assertEquals(UserRole.USER, createdUser.getUserRole());
        assertNull(createdUser.getPassword());  // Ensure password is not returned

        // Verify repository save was called
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testCreateUser_WithRole() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setName("Admin User");
        signupRequest.setEmail("admin@example.com");
        signupRequest.setPassword("adminPassword");
        signupRequest.setUserRole(UserRole.ADMIN);

        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setName("Admin User");
        mockUser.setEmail("admin@example.com");
        mockUser.setUserRole(UserRole.ADMIN);

        when(passwordEncoder.encode(signupRequest.getPassword())).thenReturn("encodedAdminPassword");
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        User createdUser = authService.createUser(signupRequest);

        assertNotNull(createdUser);
        assertEquals("Admin User", createdUser.getName());
        assertEquals("admin@example.com", createdUser.getEmail());
        assertEquals(UserRole.ADMIN, createdUser.getUserRole());
    }

    @Test
    void testHasCustomerWithEmail_Found() {
        String email = "existing@example.com";

        User mockUser = new User();
        mockUser.setEmail(email);

        when(userRepository.findFirstByEmail(email)).thenReturn(Optional.of(mockUser));

        boolean exists = authService.hasCustomerWithEmail(email);

        assertTrue(exists);

        // Verify repository was called
        verify(userRepository, times(1)).findFirstByEmail(email);
    }

    @Test
    void testHasCustomerWithEmail_NotFound() {
        String email = "nonexistent@example.com";

        when(userRepository.findFirstByEmail(email)).thenReturn(Optional.empty());

        boolean exists = authService.hasCustomerWithEmail(email);

        assertFalse(exists);

        // Verify repository was called
        verify(userRepository, times(1)).findFirstByEmail(email);
    }
}