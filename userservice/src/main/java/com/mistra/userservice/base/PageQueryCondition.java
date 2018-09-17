package com.mistra.userservice.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: WangRui
 * @Date: 2018-09-14
 * Time: 下午5:22
 * Description:
 */
@Data
public class PageQueryCondition {

    @ApiModelProperty(value = "页数")
    private int pageNumber = 0;

    @ApiModelProperty(value = "每页数量")
    private int pageSize = 10;

    @ApiModelProperty(value = "排序字段")
    private String order;

    @ApiModelProperty(value = "排序规则 默认降序，升序=ASC")
    private String orderBy = "DESC";

}
