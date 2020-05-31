# SpringCloudApplication
## SpringCloud综合测试Demo
### 包含基础的各类组件应用：
- EurekaServer 注册中心
- Route/Zuul Server  路由
- ConfigServer 配置中心
- AdminServer 管理和监控Spring Boot程序
- 其他的SpringCloud组件：Ribbon、Feign、Hystrix、Hystrix Dashboard、Hystrix Turbine

### 其他的开发中常用的第三方中间件(持续新增中，都在userservice模块中)
- Redis
- Mybatis/MybatisPlus
- JsonWebToken(JWT) 用户身份令牌完整设计方案 JsonWebTokenUtils
- Shiro 用户验证与鉴权
- Swagger2 可定义的接口文档
- Mailgun 邮件服务
- Excel 常规的Excel操作

### 其他的一些开发思想和编码技巧应用(都在userservice模块中)
- 全局异常处理 ExceptionControllerAdvice
- 自定义注解封装请求响应数据格式 MistraResponseBodyAdvice
- 国际化 InternationalizationUtil
