package com.mistra.utilservice.entity;

import lombok.Data;

/**
 * @Author: WangRui
 * @Date: 2018/10/14
 * Time: 下午10:41
 * Description: 邮件模板实体类
 */
@Data
public class Template {

    private Integer id;

    private String key;

    private String content;
}
