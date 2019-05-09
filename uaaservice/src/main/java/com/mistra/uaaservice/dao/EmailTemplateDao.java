package com.mistra.uaaservice.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mistra.uaaservice.entity.EmailTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: WangRui
 * @Date: 2018-10-16
 * Time: 下午5:34
 * Description:
 */
@Repository
@Mapper
public interface EmailTemplateDao extends BaseMapper<EmailTemplate> {

}
