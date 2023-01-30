package icu.chenz.comments.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author : Chenz
 * @date : 2023-01-08 20:40
 */

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserEntity extends Entity {
    String nickname;
    @NotNull(message = "用户名不能为空")
    @NotBlank(message = "用户名不能为空")
    @Size(max = 20, min = 4, message = "用户名长度应在 4 ~ 20 之间")
    String username;
    @NotNull(message = "密码不能为空")
    String password;

    public UserEntity() {
    }

    public UserEntity(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }
}
