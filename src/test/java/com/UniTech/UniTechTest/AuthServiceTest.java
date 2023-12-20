package com.UniTech.UniTechTest;

import com.UniTech.UniTechTest.dto.AuthenticationResponse;
import com.UniTech.UniTechTest.dto.LoginRequest;
import com.UniTech.UniTechTest.dto.RegisterRequest;
import com.UniTech.UniTechTest.enums.Role;
import com.UniTech.UniTechTest.exception.BadUserCredentialException;
import com.UniTech.UniTechTest.exception.UserIsExistWithThisPinException;
import com.UniTech.UniTechTest.model.User;
import com.UniTech.UniTechTest.repository.UserRepository;
import com.UniTech.UniTechTest.service.AuthService;
import com.UniTech.UniTechTest.service.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;
@SpringBootTest
public class AuthServiceTest {
    @Autowired
    private AuthService authService;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private JwtService jwtService;

    @Test
    void shouldRegisterUserSuccessfully() {
        // Arrange
        RegisterRequest request = new RegisterRequest("Az1234","12345P", Role.User);
        User user = User.builder()
                .pin(request.getPin())
                .password("hashedPassword")
                .role(request.getRole())
                .build();

        when(userRepository.existsByPin(request.getPin())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("hashedPassword"); // Mock password encoding
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn("mockedToken");

        // Act
        AuthenticationResponse response = null;
        try {
            response = authService.Register(request);
        } catch (UserIsExistWithThisPinException e) {
            throw new RuntimeException(e);
        }

        // Assert
        assertNotNull(response);
        assertEquals("mockedToken", response.getAccessToken());
        // You can add more assertions based on your requirements
    }

    @Test
    void shouldThrowUserIsExistWithThisPinException() {
        // Arrange
        RegisterRequest request = new RegisterRequest("Az1234","12345P", Role.User);

        // Mock the scenario where the pin already exists
        when(userRepository.existsByPin(request.getPin())).thenReturn(true);

        // Act and Assert
        assertThrows(UserIsExistWithThisPinException.class, () -> {
            authService.Register(request);
        });
    }

    @Test
    void shouldThrowBadUserCredentialExceptionOnAuthenticationFailure() {
        // Arrange
        LoginRequest request = new LoginRequest("Az1234","123455");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Authentication failed"));

        // Act and Assert
        assertThrows(BadUserCredentialException.class, () -> {
            authService.Login(request);
        });
    }

    @Test
    void shouldThrowBadUserCredentialExceptionOnUserNotFound() {
        // Arrange
        LoginRequest request = new LoginRequest("Az123","12345P");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken("Az123", "12345P"));

        when(userRepository.findByPin(request.getPin())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(BadUserCredentialException.class, () -> {
            authService.Login(request);
        });
    }

    @Test
    void shouldThrowBadUserCredentialExceptionOnPinOrPasswordNull() {
        // Arrange
        LoginRequest request = new LoginRequest("Az123","12345P");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken(null,null ));

        when(userRepository.findByPin(request.getPin())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(BadUserCredentialException.class, () -> {
            authService.Login(request);
        });
    }


}
