package icu.chenz.comments.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author : Chenz
 * @date : 2023-02-20 15:53
 */

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ContextEntity extends BaseEntity {
    String name;
    Long top;
}
