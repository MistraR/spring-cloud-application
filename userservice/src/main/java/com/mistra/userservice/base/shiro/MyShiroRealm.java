package com.mistra.userservice.base.shiro;

import com.mistra.userservice.entity.SystemRole;
import com.mistra.userservice.entity.User;
import com.mistra.userservice.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

/**
 * @Author: WangRui
 * @Date: 2018/12/28
 * Time: 16:26
 * Description:
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 角色权限和对应权限添加
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取登录用户名 此用户名是在UserHandler里的UsernamePasswordToken设置的
        String name = (String) principalCollection.getPrimaryPrincipal();
        //查询用户
        User user = userService.findUserRolePermission(name);
        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        for (SystemRole role : user.getRoleList()) {
            //添加角色  也可以只添加权限，不用角色控制
            simpleAuthorizationInfo.addRole(role.getRoleName());
            //添加权限
            simpleAuthorizationInfo.addStringPermissions(role.getSysPermissionList().stream().map(systemPermission -> systemPermission.getPermission()).collect(Collectors.toList()));
        }
        return simpleAuthorizationInfo;
    }

    /**
     * 用户认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //加这一步的目的是在Post请求的时候会先进认证，然后在到请求
        if (authenticationToken.getPrincipal() == null) {
            throw new AuthenticationException("授权失败！");
        }
        //获取用户信息
        String name = authenticationToken.getPrincipal().toString();
        User user = userService.findUserRolePermission(name);
        if (user == null) {
            //这里返回后会报出对应异常
            throw new AuthenticationException("授权失败！");
        } else {
            //这里验证authenticationToken和simpleAuthenticationInfo的信息
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
            //当验证都通过后，把用户信息放在session里  此Session是Shiro封装过后的HttpSession
            Session session = SecurityUtils.getSubject().getSession();
            session.setAttribute("userSession", user);
            return simpleAuthenticationInfo;
        }
    }
}
