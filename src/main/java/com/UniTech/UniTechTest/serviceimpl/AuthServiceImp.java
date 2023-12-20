package com.UniTech.UniTechTest.serviceimpl;


import com.UniTech.UniTechTest.dto.AuthenticationResponse;
import com.UniTech.UniTechTest.dto.LoginRequest;
import com.UniTech.UniTechTest.dto.RegisterRequest;
import com.UniTech.UniTechTest.exception.BadUserCredentialException;
import com.UniTech.UniTechTest.exception.UserIsExistWithThisPinException;
import com.UniTech.UniTechTest.model.User;
import com.UniTech.UniTechTest.repository.UserRepository;
import com.UniTech.UniTechTest.service.AuthService;
import com.UniTech.UniTechTest.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImp implements AuthService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthServiceImp(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          JwtService jwtService,
                          AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthenticationResponse Register(RegisterRequest request) throws UserIsExistWithThisPinException {

        var pinIsUsed = userRepository.existsByPin(request.getPin());
        if (pinIsUsed)
            throw new UserIsExistWithThisPinException(request.getPin());
        var user = User.builder()
                .pin(request.getPin())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    @Override
    public AuthenticationResponse Login(LoginRequest request) throws BadUserCredentialException {
        if(request.getPin() ==null ||request.getPassword()==null)
            throw new BadUserCredentialException();

        var user = userRepository.findByPin(request.getPin())
                .orElseThrow(BadUserCredentialException::new);

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getPin(),
                        request.getPassword()
                ));

        if (!authenticate.isAuthenticated()) {
            throw new BadUserCredentialException();
        }



        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }


}
