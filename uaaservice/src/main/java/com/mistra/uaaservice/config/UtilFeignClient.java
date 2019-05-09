package com.mistra.uaaservice.config;

import com.mistra.utilservice.dto.MailDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: WangRui
 * @Date: 2018-10-16
 * Time: 下午5:14
 * Description:
 */
@FeignClient(value = "util-service", configuration = FeignConfig.class,fallback = UtilFeignHystrixFallback.class)
@Component
public interface UtilFeignClient {

    /**
     * 调用邮件服务
     * @param mailDTO
     * @return
     */
    @GetMapping(value = "/mail/send")
    void sendMail(MailDTO mailDTO);
}
