package Triju.ExpenSensei.Services.Interfaces;

import Triju.ExpenSensei.Dto.Auth.JwtAuthenticationDto;
import Triju.ExpenSensei.Dto.Auth.LoginDto;
import Triju.ExpenSensei.Dto.Auth.RegisterUserDto;
import Triju.ExpenSensei.Dto.User.UserDto;
import Triju.ExpenSensei.Exceptions.Users.RegisterUserException;

public interface AuthServiceInterface {

    UserDto registerUser(RegisterUserDto registerUserDto) throws RegisterUserException;

    UserDto loginUser(LoginDto loginDto);

    JwtAuthenticationDto getJwtAuthenticationDto();
}
