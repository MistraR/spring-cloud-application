package com.mistra.uaaservice.dto;

import lombok.Data;

/**
 * @Author: WangRui
 * @Date: 2018-10-16
 * Time: 下午5:47
 * Description:
 */
@Data
public class EmailContentDTO {

    private String sheetName;

    private String cell;

    private String reason;
}
