package com.mistra.userservice.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.github.pagehelper.Page;
import com.mistra.userservice.entity.User;
import com.mistra.userservice.vo.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import sun.jvm.hotspot.memory.LinearAllocBlock;

import java.util.List;

/**
 * @Author: WangRui
 * @Date: 2018-09-14
 * Time: 下午1:54
 * Description:
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    User findByEmailAndPassword(String email, String password);

    List<User> getSelectList2(UserDTO userDTO);

    List<User> methodParameter(@Param("userDTO") UserDTO userDTO, @Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    List<User> getSelectList3(UserDTO userDTO);

}
