package com.mistra.userservice.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.mistra.base.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * @ Author: RoronoaZoro丶WangRui
 * Time: 2018/7/15/015
 * Describe:
 */

@Data
public class User extends BaseEntity implements Cloneable, Serializable {

    private Long id;
    /**
     * 帐号
     */
    private String userName;
    private String password;
    private String email;
    /**
     * 加密密码的盐
     */
    private String salt;
    private String name;
    /**
     * 用户状态,0:创建未认证（比如没有激活，没有输入验证码等等）--等待验证的用户 , 1:正常状态,2：用户被锁定
     */
    private Integer status;

    /**
     * 角色id
     */
    private int systemRoleId;

    /**
     * app登录和Token Version版本号
     */
    private Integer appTokenVersion;

    /**
     * H5登录和Token Version版本号
     */
    private Integer webTokenVersion;

    /**
     * app token最后刷新时间
     */
    private Timestamp appTokenRefreshTime;

    /**
     * web token最后刷新时间
     */
    private Timestamp webTokenRefreshTime;
    /**
     * 假如一个用户具有多个角色
     *
     */
    @TableField(exist = false)
    private List<SystemRole> roleList;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        User user = null;
        try {
            user = (User) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return user;
    }
}
