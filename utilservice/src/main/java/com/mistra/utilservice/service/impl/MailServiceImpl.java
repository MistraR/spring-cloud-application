package com.mistra.utilservice.service.impl;

import com.mistra.utilservice.dao.MailMapper;
import com.mistra.utilservice.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: WangRui
 * @Date: 2018/10/14
 * Time: 下午10:35
 * Description:
 */
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private MailMapper mailMapper;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("spring.mail.username")
    private String from;

    @Override
    public void sendMail() {
        MimeMessage message = javaMailSender.createMimeMessage();
        /**
         *      dataMap 用于存储模板渲染所需要的数据
         *      用法和model 一致
         *      dataMap 的 key 对应模板中渲染数据的命名
         */
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("title","小猪佩奇 -- 邮件测试");
        String emailText = createTemplates(dataMap,"mailTemplate.html",templateEngine);
        try {
            //消息处理助手对象
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            //设置发件人
            helper.setFrom(from);
            //设置收件人
            helper.setTo("842404548@qq.com");
            //设置邮件标题
            helper.setSubject("主题：邮件服务测试");
            //设置邮件内容 ，true 表示发送html 格式
            helper.setText(emailText, true);

        } catch (MessagingException e) {
            throw new RuntimeException("Messaging  Exception !", e);
        }
        javaMailSender.send(message);
    }

    /**
     *
     * @param dataMap 渲染数据原
     * @param TemplatesName 模板名
     * @param templateEngine   模板操作类
     * @return
     */
    public static String createTemplates(Map<String,Object> dataMap, String TemplatesName, TemplateEngine templateEngine){
        //context 对象用于注入要在模板上渲染的信息
        Context context = new Context();
        context.setVariables(dataMap);
        String emailText = templateEngine.process(TemplatesName,context);
        System.out.println(emailText);
        //返回模板源代码 String 类型
        return emailText;
    }
}
