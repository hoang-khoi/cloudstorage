package com.udacity.jwdnd.course1.cloudstorage.domain.repository.user;

import com.udacity.jwdnd.course1.cloudstorage.domain.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserRepository {
    @Insert("INSERT INTO USERS (username, password, firstname, lastname) VALUES " +
            "(#{username}, #{password}, #{firstName}, #{lastName});")
    void save(User user);

    /**
     * @return null if no user can be found.
     */
    @Select("SELECT userid AS id, username, password, firstname, lastname FROM USERS WHERE username = #{username}")
    User findByUsername(String username);
}
