package icu.chenz.comments.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author : Chenz
 * @date : 2023-01-08 20:34
 */


@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BaseEntity extends IDEntity {
    Long ctime;
    Long mtime;
}
