package com.mistra.uaaservice.service;

import com.mistra.base.result.Result;
import com.mistra.uaaservice.config.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: WangRui
 * @Date: 2018-09-19
 * Time: 下午5:21
 * Description:
 */
public interface FeignService {

    /**
     * 测试
     * @return
     */
    Result test();

    /**
     * 发送邮件
     * @return
     */
    Result sendMail();
}
