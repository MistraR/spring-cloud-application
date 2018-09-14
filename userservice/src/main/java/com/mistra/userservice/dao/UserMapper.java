package com.mistra.userservice.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mistra.userservice.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: WangRui
 * @Date: 2018-09-14
 * Time: 下午1:54
 * Description:
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    User findByEmailAndPassword(String email,String password);


}
