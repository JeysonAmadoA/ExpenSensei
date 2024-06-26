package Triju.ExpenSensei.Helpers;

import Triju.ExpenSensei.Dto.Auth.RegisterUserDto;
import Triju.ExpenSensei.Dto.User.UserDto;
import Triju.ExpenSensei.Exceptions.Users.RegisterUserException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthHelper {

    public static boolean verifyRegisterPasswords(RegisterUserDto registerUserDto) throws RegisterUserException {
        if (registerUserDto.getPassword().equals(registerUserDto.getConfirmPassword())) {
            return true;
        } else {
            throw new RegisterUserException("Las contraseñas no coinciden");
        }
    }

    public static Long getUserWhoActingId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null){
            UserDto userData = (UserDto) authentication.getCredentials();
            return userData.getId();
        }
        else return null;
    }

    public static UserDto getUserAuthenticated(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null){
            return (UserDto) authentication.getCredentials();
        }
        else return null;
    }

}
