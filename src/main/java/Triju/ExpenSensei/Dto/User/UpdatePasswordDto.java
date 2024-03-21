package Triju.ExpenSensei.Dto.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePasswordDto {

    private String password;

    private String newPassword;

    private String confirmPassword;
}
