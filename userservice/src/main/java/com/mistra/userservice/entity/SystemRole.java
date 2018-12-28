package com.mistra.userservice.entity;

import lombok.Data;

import java.util.List;

/**
 * @Author: WangRui
 * @Date: 2018/12/27
 * Time: 18:37
 * Description:
 */
@Data
public class SystemRole {

    private Integer id;

    private String roleName;

    private String description;

    /**
     * 是否可用,如果不可用将不会添加给用户
     */
    private Boolean available = Boolean.FALSE;

    private List<SysPermission> sysPermissionList;

}
