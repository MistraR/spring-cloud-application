package com.mistra.utilservice.dao;

import com.mistra.utilservice.entity.Template;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: WangRui
 * @Date: 2018/10/14
 * Time: 下午10:35
 * Description:
 */
@Repository
@Mapper
public interface MailMapper {

    /**
     *
     * @param key
     * @return
     */
    Template findByKey(String key);
}
