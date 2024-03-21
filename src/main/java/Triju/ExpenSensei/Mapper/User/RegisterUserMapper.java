package Triju.ExpenSensei.Mapper.User;

import Triju.ExpenSensei.Dto.Auth.RegisterUserDto;
import Triju.ExpenSensei.Entities.User.User;
import Triju.ExpenSensei.Mapper.BaseMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class RegisterUserMapper extends BaseMapper<RegisterUserDto, User> {

    @Override
    public RegisterUserDto toDto(User entity) {
        return null;
    }

    @Override
    public User toEntity(RegisterUserDto dto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encryptedPassword = encoder.encode(dto.getPassword());

        return User.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .lastName(dto.getLastName())
                .birthDay(dto.getBirthDay())
                .password(encryptedPassword)
                .build();
    }
}
