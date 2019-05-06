package com.mistra.utilservice.service;

import com.mistra.utilservice.dto.MailDTO;

/**
 * @Author: WangRui
 * @Date: 2018/10/14
 * Time: 下午10:34
 * Description:
 */
public interface MailService {

    /**
     * 邮件发送
     *
     * @param mailDTO
     */
    void sendMail(MailDTO mailDTO);
}
