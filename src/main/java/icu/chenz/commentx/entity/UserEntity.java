package icu.chenz.commentx.entity;

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
    @NotNull
    @Size(max = 20, min = 4)
    String username;
    @NotNull
    String password;
}
