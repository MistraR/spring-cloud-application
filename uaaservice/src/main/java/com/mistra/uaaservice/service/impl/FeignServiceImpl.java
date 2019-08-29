package com.mistra.uaaservice.service.impl;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.mistra.uaaservice.entity.EmailTemplate;
import com.mistra.userservice.core.config.result.Result;
import com.mistra.uaaservice.config.UserFeignClient;
import com.mistra.uaaservice.config.UtilFeignClient;
import com.mistra.uaaservice.dao.EmailTemplateDao;
import com.mistra.uaaservice.dto.EmailContentDTO;
import com.mistra.uaaservice.service.FeignService;
import com.mistra.utilservice.dto.MailDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: WangRui
 * @Date: 2018-10-16
 * Time: 下午5:19
 * Description:
 */
@Service
public class FeignServiceImpl implements FeignService {

    private Logger logger = LoggerFactory.getLogger(FeignServiceImpl.class);

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private UtilFeignClient utilFeignClient;

    @Autowired
    private EmailTemplateDao emailTemplateDao;

    @Override
    public Result test() {
        return userFeignClient.test();
    }

    @Override
    public Result sendMail() {
        MailDTO mailDTO = new MailDTO();
        List<String> toList = new ArrayList<>();
        toList.add("842404548@qq.com");
        mailDTO.setSendToAddress(toList);
        mailDTO.setSubject("我与你数千次离合，刹那间上演");
        mailDTO.setTemplate(emailTemplateDao.selectById(1).getContent());
        Map<String, Object> paramsMap = new HashMap<>(32);
        paramsMap.put("name", "丶小王瑞");
        paramsMap.put("haveHeader", 1);
        List<EmailContentDTO> emailContentDTOList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            EmailContentDTO emailContentDTO = new EmailContentDTO();
            emailContentDTO.setSheetName("表" + i);
            emailContentDTO.setCell("单元格" + i);
            emailContentDTO.setReason("原因" + i);
            emailContentDTOList.add(emailContentDTO);
        }
        paramsMap.put("exceptionList", emailContentDTOList);
        mailDTO.setParamsMap(paramsMap);
        logger.info("开始调用邮件发送服务！");
        return utilFeignClient.sendMail(mailDTO);
    }

    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void distributedTransaction() {
        EmailTemplate emailTemplate = new EmailTemplate();
        emailTemplate.setContent("调用者数据");
        emailTemplate.setKey("key");
        emailTemplateDao.insert(emailTemplate);
        userFeignClient.distributedTransaction();
    }
}
