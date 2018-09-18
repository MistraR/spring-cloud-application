package com.mistra.userservice.entity;

import com.mistra.base.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * Author: RoronoaZoro丶WangRui
 * Time: 2018/7/15/015
 * Describe:
 */

@Data
public class User extends BaseEntity implements Cloneable, Serializable {

    private Long id;

    private String email;

    private String userName;

    private String password;

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
