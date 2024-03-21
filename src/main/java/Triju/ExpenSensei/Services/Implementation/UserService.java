package Triju.ExpenSensei.Services.Implementation;

import Triju.ExpenSensei.Dto.User.UpdatePasswordDto;
import Triju.ExpenSensei.Dto.User.UserDto;
import Triju.ExpenSensei.Entities.User.User;
import Triju.ExpenSensei.Exceptions.Users.DeleteUserException;
import Triju.ExpenSensei.Exceptions.Users.UpdateUserException;
import Triju.ExpenSensei.Mapper.User.UserMapper;
import Triju.ExpenSensei.Repositories.User.UserRepository;
import Triju.ExpenSensei.Services.Interfaces.UserServiceInterface;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static Triju.ExpenSensei.Helpers.AuthHelper.getUserWhoActingId;
import static Triju.ExpenSensei.Helpers.UserHelper.verifyNewPassword;
import static Triju.ExpenSensei.Helpers.UserHelper.verifyOldPassword;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserServiceInterface {


    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDto getUserById(Long userId) {
        User userFound = userRepository.findById(userId).orElse(null);
        return userFound != null ? (new UserMapper()).toDto(userFound) : null;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> allUsers = userRepository.findAll();
        UserMapper userMapper = new UserMapper();
        return allUsers.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> filterUsersByEmail(String email) {
        List<User> usersByEmail = userRepository.findByEmailLike(email);
        UserMapper userMapper = new UserMapper();
        return usersByEmail.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> filterUsersByNameOrLastname(String entrySearch) {
        List<User> usersByName = userRepository.findByNameLikeOrLastNameLike(entrySearch);
        UserMapper userMapper = new UserMapper();
        return usersByName.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long userId) throws UpdateUserException {
        User userUpdated;
        UserMapper userMapper = new UserMapper();
        try {
            User userFound = userRepository.findById(userId).orElse(null);
            if (userFound != null){
                userUpdated =  userMapper.update(userFound, userDto);
                userUpdated.commitUpdate(getUserWhoActingId());
                userRepository.save(userUpdated);
            }
            else return null;
        } catch (Exception e){
            throw new UpdateUserException(e.getMessage());
        }
        return userMapper.toDto(userUpdated);
    }

    @Override
    public UserDto updatePassword(UpdatePasswordDto updatePasswordDto, Long userId) throws UpdateUserException {
        User userUpdated;
        UserMapper userMapper = new UserMapper();
        try {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            User userFound = userRepository.findById(userId).orElse(null);
            if (userFound != null &&
                verifyOldPassword(updatePasswordDto.getPassword(), userFound.getPassword(), encoder)){
                verifyNewPassword(updatePasswordDto);
                String newPasswordEncoded = encoder.encode(updatePasswordDto.getNewPassword());
                userFound.setPassword(newPasswordEncoded);
                userFound.commitUpdate(getUserWhoActingId());
                userUpdated = userRepository.save(userFound);
            }
            else return null;
        } catch (Exception e){
            throw new UpdateUserException(e.getMessage());
        }
        return userMapper.toDto(userUpdated);
    }

    @Override
    public boolean deleteUser(Long userId) throws DeleteUserException {
        try {
            User userFound = userRepository.findById(userId).orElse(null);
            if (userFound != null){
                userFound.commitDelete(getUserWhoActingId());
                userRepository.save(userFound);
                return true;
            }
            else return false;

        } catch (Exception e){
            throw new DeleteUserException(e.getMessage());
        }
    }

    @Override
    public User getUserByEmail(String userEmail) {
        return userRepository.findByEmail(userEmail).orElse(null);
    }
}
