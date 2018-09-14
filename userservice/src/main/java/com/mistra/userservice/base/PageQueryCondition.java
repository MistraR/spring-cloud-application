package com.mistra.userservice.base;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: WangRui
 * @Date: 2018-09-14
 * Time: 下午5:22
 * Description:
 */
public class PageQueryCondition {

    @ApiModelProperty(value = "页数")
    private int pageNumber = 0;

    @ApiModelProperty(value = "每页数量")
    private int pageSize = 10;

    @ApiModelProperty(value = "排序字段")
    private String order;

    @ApiModelProperty(value = "排序规则 默认降序，升序=ASC")
    private String orderBy = "DESC";

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}
