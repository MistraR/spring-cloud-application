package com.mistra.userservice.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mistra.userservice.entity.User;
import com.mistra.userservice.dto.UserDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: WangRui
 * @Date: 2018-09-14
 * Time: 下午1:54
 * Description:
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * @param userName
     * @param password
     * @return
     */
    User findByUserNameAndPassword(@Param("userName") String userName, @Param("password") String password);

    /**
     * @param userDTO
     * @return
     */
    List<User> getSelectList2(UserDTO userDTO);

    /**
     * @param userDTO
     * @return
     */
    List<User> getSelectList3(UserDTO userDTO);

    /**
     * 根据用户名查询用户
     *
     * @param userName
     * @return
     */
    User findByUserName(@Param("userName") String userName);

}
