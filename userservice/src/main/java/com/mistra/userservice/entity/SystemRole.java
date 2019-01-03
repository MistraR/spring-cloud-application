package com.mistra.userservice.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: WangRui
 * @Date: 2018/12/27
 * Time: 18:37
 * Description:
 */
@Data
public class SystemRole implements Serializable {

    private Integer id;

    private String roleName;

    private String description;

    /**
     * 是否可用,如果不可用将不会添加给用户,0:可用 1:不可用
     */
    private int available;

    @TableField(exist = false)
    private List<SystemPermission> sysPermissionList;

}
