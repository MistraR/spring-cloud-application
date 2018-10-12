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

    User findByEmailAndPassword(@Param("email") String email, @Param("password") String password);

    List<User> getSelectList2(UserDTO userDTO);

    List<User> getSelectList3(UserDTO userDTO);

}
