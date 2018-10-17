package com.mistra.utilservice.service.impl;

import com.mistra.base.result.Result;
import com.mistra.utilservice.dto.MailDTO;
import com.mistra.utilservice.service.MailService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import net.sargue.mailgun.*;
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

import javax.ws.rs.core.MediaType;
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
        return sendOne(mailDTO);
    }

    /**
     * 第一种写法  发送thyemleaf引擎渲染过后的html
     * @param mailDTO
     * @return
     */
    public Result sendOne(MailDTO mailDTO) {
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
        Body mailBody = new Body(mailContent, "");

        MailBuilder mailBuilder = Mail.using(mailgunConfiguration);
        //邮件主题
        mailBuilder.subject(mailDTO.getSubject());
        //邮件内容
        mailBuilder.content(mailBody);
        //设置收件人，可群发
        for (String sendTo : mailDTO.getSendToAddress()) {
            mailBuilder.to(sendTo);
        }

        threadPoolTaskExecutor.submit(() -> {
            Response response = mailBuilder.build().send();
            logger.info("发送邮件服务调用完成. Code: {}, Response Type: {}. Message: {}", response.responseCode(), response.responseType(), response.responseMessage());
        });
        result.setSuccess(true);
        result.setMessage("邮件发送成功！");
        return result;
    }

    /**
     * 第二种写法  发送纯文本
     * @param mailDTO
     * @return
     */
    public Result sendTwo(MailDTO mailDTO) {
        Result result = new Result();
        Client client = Client.create();
        client.addFilter(new HTTPBasicAuthFilter("api", "9ef4cf242df9f5fca6af5b9218523d54-a3d67641-247111d9"));
        WebResource webResource = client.resource("https://api.mailgun.net/v3/mistra.wang/messages");
        MultivaluedMapImpl formData = new MultivaluedMapImpl();
        formData.add("from", "丶小王瑞 <wrmistra@gmail.com>");
        //收件人，可以多个群发
        formData.add("to", "842404548@qq.com");
        formData.add("to", "2296147307@qq.com");
        //邮件主题
        formData.add("subject", "纯文本邮件测试");
        //邮件内容
        formData.add("text", "这是一个纯文本邮件！");
        webResource.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, formData);
        result.setSuccess(true);
        result.setMessage("邮件发送成功！");
        return result;
    }
}
