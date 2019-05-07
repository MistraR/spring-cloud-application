package com.mistra.uaaservice.config;

import com.mistra.userservice.core.config.result.RequestResultBuilder;
import com.mistra.userservice.core.config.result.Result;
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
    public Result sendMail(MailDTO mailDTO) {
        return RequestResultBuilder.failed("Util服务远程调用出错！");
    }
}
