package com.mistra.base.lombok;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mistra.base.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import java.beans.Transient;
import java.io.Serializable;

/**
 * Author: RoronoaZoro丶WangRui
 * Time: 2018/7/15/015
 * Describe:
 */

//json序列化时将忽略属性
@JsonIgnoreProperties({"notExistParam"})
//提供getting,setting,equals,canEqual,hashCode,toString 方法。
@Data
//提供无参构造
@NoArgsConstructor
//提供全参构造
@AllArgsConstructor
public class LombokEntity extends BaseEntity implements Cloneable, Serializable {

    private Long id;

    private String email;

    private String userName;

    private String password;

    //同上,属性并非数据库表的字段映射，ORM框架将忽略该属性
    @JsonIgnore
    private String notExistParam;

    //有get方法时要卸载get方法上
    @Transient
    public String getNotExistParam() {
        return notExistParam;
    }

    public void setNotExistParam(String notExistParam) {
        this.notExistParam = notExistParam;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        LombokEntity lombokEntity = null;
        try {
            lombokEntity = (LombokEntity) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return lombokEntity;
    }

    @Override
    public String toString() {
        return "LombokEntity{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", notExistParam='" + notExistParam + '\'' +
                '}';
    }
}
