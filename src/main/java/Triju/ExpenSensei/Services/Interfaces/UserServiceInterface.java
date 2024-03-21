package Triju.ExpenSensei.Services.Interfaces;

import Triju.ExpenSensei.Dto.User.UpdatePasswordDto;
import Triju.ExpenSensei.Dto.User.UserDto;
import Triju.ExpenSensei.Entities.User.User;
import Triju.ExpenSensei.Exceptions.Users.DeleteUserException;
import Triju.ExpenSensei.Exceptions.Users.UpdateUserException;

import java.util.List;

public interface UserServiceInterface {

    UserDto getUserById(Long userId);

    List<UserDto> getAllUsers();

    List<UserDto> filterUsersByEmail(String email);

    List<UserDto> filterUsersByNameOrLastname(String entrySearch);

    UserDto updateUser(UserDto userDto, Long userId) throws UpdateUserException;

    UserDto updatePassword(UpdatePasswordDto updatePasswordDto, Long userId) throws UpdateUserException;

    boolean deleteUser(Long userId) throws DeleteUserException;

    User getUserByEmail(String userEmail);
}
