package com.mistra.utilservice.dao;

import com.mistra.utilservice.entity.Template;
import org.springframework.stereotype.Repository;

/**
 * @Author: WangRui
 * @Date: 2018/10/14
 * Time: 下午10:35
 * Description:
 */
public interface MailMapper {

    Template findByKey(String key);
}
