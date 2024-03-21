package Triju.ExpenSensei.Mapper.User;

import Triju.ExpenSensei.Dto.User.UserDto;
import Triju.ExpenSensei.Entities.User.User;
import Triju.ExpenSensei.Mapper.BaseMapper;

import static Triju.ExpenSensei.Helpers.MapperHelper.updateFieldIfNotNull;

public class UserMapper extends BaseMapper<UserDto, User> {

    @Override
    public UserDto toDto(User entity) {
        return UserDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .birthDay(entity.getBirthDay())
                .role(entity.getRole())
                .build();
    }

    @Override
    public User toEntity(UserDto dto) {
        return User.builder()
                .name(dto.getName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .birthDay(dto.getBirthDay())
                .role(dto.getRole())
                .build();

    }

    public User update(User user, UserDto userDto){
        updateFieldIfNotNull(userDto.getName(), user::setName);
        updateFieldIfNotNull(userDto.getLastName(), user::setLastName);
        updateFieldIfNotNull(userDto.getEmail(), user::setEmail);
        updateFieldIfNotNull(userDto.getBirthDay(), user::setBirthDay);
        return user;
    }
}
