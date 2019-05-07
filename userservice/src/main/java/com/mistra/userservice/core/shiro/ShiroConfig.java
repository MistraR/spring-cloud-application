package com.mistra.userservice.core.shiro;

import com.mistra.userservice.core.JWT.JsonWebTokenProperties;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author: WangRui
 * @Date: 2018/12/28
 * Time: 16:20
 * Description:
 */
@Configuration
public class ShiroConfig {

    @Autowired
    private JsonWebTokenProperties jsonWebTokenProperties;

    /**
     * 将自定义的验证方式加入容器
     * @return
     */
    @Bean
    public MyShiroRealm myShiroRealm(){
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        return myShiroRealm;
    }

    @Bean
    public CacheManager cacheManager(){
        return new EhCacheManager();
    }


    @Bean
    public SessionDAO sessionDAO(){
        return new EnterpriseCacheSessionDAO();
    }

    @Bean
    public SessionManager sessionManager(SessionDAO sessionDAO){
        DefaultWebSessionManager manager = new DefaultWebSessionManager();
        manager.setSessionDAO(sessionDAO);
        manager.setGlobalSessionTimeout(3600000);
        manager.setSessionValidationInterval(3600000);
        return manager;
    }

    /**
     * 权限管理，配置主要是Realm的管理认证
     * @return
     */
    @Bean
    public SecurityManager securityManager(CacheManager cacheManager, SessionManager sessionManager){
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        securityManager.setSessionManager(sessionManager);
        securityManager.setRealm(myShiroRealm());
        securityManager.setCacheManager(cacheManager);
        return securityManager;
    }

    /**
     * Filter工厂，设置对应的过滤条件和跳转条件
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 设置SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Set<String> urlSet = new HashSet<>(jsonWebTokenProperties.getIgnoreUrl());
        //必须采用LinkedHashMap有序存储过滤条件
        Map<String, String> map = new LinkedHashMap<>();
        //anon表示所有用户都可以不鉴权匿名访问
        urlSet.stream().forEach(temp -> map.put(temp, "anon"));
        //登出
        //map.put("/loginOut", "logout");
        //此路径必须放在最后
        map.put("/**", "authc");
        //登录
        shiroFilterFactoryBean.setLoginUrl("/user/login");
        //首页
        shiroFilterFactoryBean.setSuccessUrl("/index");
        //错误页面，认证不通过跳转
        shiroFilterFactoryBean.setUnauthorizedUrl("/error");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    /**
     * 加入注解的使用，不加入这个注解不生效
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
