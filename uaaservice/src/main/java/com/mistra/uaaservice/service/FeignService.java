package com.mistra.uaaservice.service;


/**
 * @Author: WangRui
 * @Date: 2018-09-19
 * Time: 下午5:21
 * Description:
 */
public interface FeignService {

    /**
     * 测试
     *
     * @return
     */
    void test();

    /**
     * 发送邮件
     *
     * @return
     */
    void sendMail();

    /**
     * 分布式事物测试
     */
    void distributedTransaction();
}
