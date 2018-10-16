package com.mistra.uaaservice.entity;

import lombok.Data;

/**
 * @Author: WangRui
 * @Date: 2018-10-16
 * Time: 下午5:33
 * Description:
 */
@Data
public class EmailTemplate  {

    private Long id;

    private String key;

    private String content;
}
