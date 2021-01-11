package pl.kopka.summary.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserForm {
    private String username;
    private String email;
    private String password;
    private String newPassword;
    private String firstName;
}
