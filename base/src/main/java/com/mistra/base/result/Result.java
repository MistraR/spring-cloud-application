package com.mistra.base.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: WangRui
 * @Date: 2018-09-14
 * Time: 上午10:07
 * Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    /**
     * 处理结果
     */
    private boolean success;

    /**
     * 信息
     */
    private String message;


}
