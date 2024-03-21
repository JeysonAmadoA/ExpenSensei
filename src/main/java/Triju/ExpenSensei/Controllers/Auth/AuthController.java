package Triju.ExpenSensei.Controllers.Auth;


import Triju.ExpenSensei.Dto.Auth.JwtAuthenticationDto;
import Triju.ExpenSensei.Dto.Auth.LoginDto;
import Triju.ExpenSensei.Dto.Auth.RegisterUserDto;
import Triju.ExpenSensei.Dto.User.UserDto;
import Triju.ExpenSensei.Services.Interfaces.AuthServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static Triju.ExpenSensei.Helpers.AuthHelper.verifyRegisterPasswords;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthServiceInterface authService;

    @Autowired
    public AuthController(AuthServiceInterface authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterUserDto registerUserDto) {
        try {
            verifyRegisterPasswords(registerUserDto);
            UserDto userCreated = authService.registerUser(registerUserDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        try {
            UserDto userLogged = authService.loginUser(loginDto);
            JwtAuthenticationDto jwtAuthentication = authService.getJwtAuthenticationDto();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", jwtAuthentication.getToken());
            return ResponseEntity.status(HttpStatus.OK)
                    .headers(headers)
                    .body(userLogged);

        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
