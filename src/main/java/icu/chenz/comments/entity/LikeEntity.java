package icu.chenz.comments.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author : Chenz
 * @date : 2023-02-24 16:56
 */

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LikeEntity extends BaseEntity {
    Long uid;
    Long cid;
    Byte type;

    public LikeEntity(Long uid, Long cid, Byte type) {
        this.uid = uid;
        this.cid = cid;
        this.type = type;
    }
}
