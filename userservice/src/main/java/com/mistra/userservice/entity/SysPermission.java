package com.mistra.userservice.entity;

import lombok.Data;

/**
 * @Author: WangRui
 * @Date: 2018/12/27
 * Time: 18:40
 * Description:
 */
@Data
public class SysPermission {

    private Integer id;
    private String name;
    /**
     * 资源类型，[menu|button]
     */
    private String resourceType;
    /**
     * 资源路径
     */
    private String url;
    /**
     * 权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
     */
    private String permission;
    /**
     * 父编号
     */
    private Long parentId;
    /**
     * 父编号列表
     */
    private String parentIds;
    /**
     * 是否可用
     */
    private Boolean available = Boolean.FALSE;

}
