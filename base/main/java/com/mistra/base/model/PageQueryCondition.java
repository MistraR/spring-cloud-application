package com.mistra.base.model;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Author: WangRui
 * @Date: 2018-09-14
 * Time: 下午5:22
 * Description:
 */
@Data
public class PageQueryCondition {

    @NotNull
    @Min(1)
    private int pageNumber = 1;

    @NotNull
    @Min(1)
    private int pageSize = 10;

    private String order;

    private String orderBy = "DESC";

}
