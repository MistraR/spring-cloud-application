package com.mistra.userservice.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mistra.userservice.entity.SystemPermission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: WangRui
 * @Date: 2018/12/29
 * Time: 00:05
 * Description:
 */
@Repository
public interface SystemPermissionMapper extends BaseMapper<SystemPermission> {

    /**
     * 根据角色id查询该角色所有的权限
     * @param systemRoleId
     * @return
     */
    List<SystemPermission> findBySystemRoleId(@Param("systemRoleId") int systemRoleId);
}
