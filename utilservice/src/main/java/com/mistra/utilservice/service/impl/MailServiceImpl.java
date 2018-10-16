package com.mistra.utilservice.service.impl;

import com.mistra.base.result.Result;
import com.mistra.utilservice.dto.MailDTO;
import com.mistra.utilservice.service.MailService;
import net.sargue.mailgun.Configuration;
import net.sargue.mailgun.Mail;
import net.sargue.mailgun.MailBuilder;
import net.sargue.mailgun.Response;
import net.sargue.mailgun.content.Body;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.regex.Pattern;

/**
 * @Author: WangRui
 * @Date: 2018/10/14
 * Time: 下午10:35
 * Description:
 */
@Service
public class MailServiceImpl implements MailService {

    private Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    /**
     * 邮箱验证正则
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 字符串模板解析引擎
     */
    @Qualifier("stringTemplateEngine")
    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private Configuration mailgunConfiguration;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public Result sendMail(MailDTO mailDTO) {
        Result result = new Result();
        if (mailDTO.getSendToAddress().size() == 0 || mailDTO.getParamsMap().size() == 0 || StringUtils.isEmpty(mailDTO.getSubject()) || StringUtils.isEmpty(mailDTO.getTemplate())) {
            result.setSuccess(false);
            result.setMessage("MailDTO参数不正确！");
            return result;
        }
        for (String to : mailDTO.getSendToAddress()) {
            if (!Pattern.matches(REGEX_EMAIL, to)) {
                result.setSuccess(false);
                result.setMessage("邮箱格式错误!");
                return result;
            }
        }
        //编译thymeleaf模板 渲染数据
        Context context = new Context();
        context.setVariables(mailDTO.getParamsMap());
        String mailContent = springTemplateEngine.process(mailDTO.getTemplate(), context);
        Body mailBody = new Body(mailContent,"");

        MailBuilder mailBuilder = Mail.using(mailgunConfiguration);
        mailBuilder.subject(mailDTO.getSubject());
        mailBuilder.content(mailBody);
        for (String sendTo : mailDTO.getSendToAddress()){
            mailBuilder.to(sendTo);
        }

        threadPoolTaskExecutor.submit(() ->{
            Response response = mailBuilder.build().send();
            logger.info("Send mail complete. Code: {}, Response Type: {}. Message: {}", response.responseCode(), response.responseType(), response.responseMessage());
        });
        result.setSuccess(true);
        result.setMessage("邮件发送成功！");
        return result;
    }
}
