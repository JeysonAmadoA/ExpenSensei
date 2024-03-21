package Triju.ExpenSensei.Dto.User;

import Triju.ExpenSensei.Utilities.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class UserDto {

    private Long id;

    private String name;

    private String lastName;

    private LocalDate birthDay;

    private String email;

    private Role role;
}
