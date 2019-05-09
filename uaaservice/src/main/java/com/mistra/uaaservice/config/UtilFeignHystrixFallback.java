package com.mistra.uaaservice.config;

import com.mistra.utilservice.dto.MailDTO;
import org.springframework.stereotype.Component;

/**
 * @Author: WangRui
 * @Date: 2018-10-16
 * Time: 下午5:58
 * Description:
 */
@Component
public class UtilFeignHystrixFallback implements UtilFeignClient{

    @Override
    public void sendMail(MailDTO mailDTO) {
    }
}
