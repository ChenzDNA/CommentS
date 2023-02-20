package icu.chenz.comments.dao;

import icu.chenz.comments.entity.ContextEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author : Chenz
 * @date : 2023-02-20 16:00
 */

@Mapper
public interface ContextDao {
    ContextEntity getContextByName(String name);

    int createContext(String context);

    int updateTop(Long id);
}
