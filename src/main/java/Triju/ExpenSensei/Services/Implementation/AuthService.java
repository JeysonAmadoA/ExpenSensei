package Triju.ExpenSensei.Services.Implementation;


import Triju.ExpenSensei.Dto.Auth.JwtAuthenticationDto;
import Triju.ExpenSensei.Dto.Auth.LoginDto;
import Triju.ExpenSensei.Dto.Auth.RegisterUserDto;
import Triju.ExpenSensei.Dto.User.UserDto;
import Triju.ExpenSensei.Entities.User.User;
import Triju.ExpenSensei.Exceptions.Users.RegisterUserException;
import Triju.ExpenSensei.Mapper.User.RegisterUserMapper;
import Triju.ExpenSensei.Mapper.User.UserMapper;
import Triju.ExpenSensei.Repositories.User.UserRepository;
import Triju.ExpenSensei.Services.Interfaces.AuthServiceInterface;
import Triju.ExpenSensei.Utilities.Role;
import lombok.Getter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthService implements AuthServiceInterface {

    @Getter
    private JwtAuthenticationDto jwtAuthenticationDto;

    private static final UserMapper userMapper = new UserMapper();

    private final AuthenticationManager authenticationManager;

    private final JWTService jwtService;

    private final UserRepository userRepo;

    public AuthService(AuthenticationManager authenticationManager, JWTService jwtService, UserRepository userRepo) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepo = userRepo;
    }

    @Override
    public UserDto registerUser(RegisterUserDto registerUserDto) throws RegisterUserException {
        User userRegistered;

        try {
            User newUser = (new RegisterUserMapper()).toEntity(registerUserDto);
            newUser.setRole(Role.CUSTOMER);
            newUser.commitCreate();
            userRegistered = this.userRepo.save(newUser);
        } catch (Exception e) {
            throw new RegisterUserException(e.getMessage());
        }
        return userMapper.toDto(userRegistered);
    }

    public UserDto loginUser(LoginDto loginDto){
        authenticateUser(loginDto);
        User user = this.userRepo.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        setJwtAuthenticationDto(user);
        return userMapper.toDto(user);
    }

    private void authenticateUser(LoginDto loginDto){
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(), loginDto.getPassword());

        authenticationManager.authenticate(authToken);
    }

    private void setJwtAuthenticationDto(User user) {
        String jwt = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        this.jwtAuthenticationDto = JwtAuthenticationDto
                .builder()
                .token(jwt).refreshToken(refreshToken)
                .build();
    }
}
