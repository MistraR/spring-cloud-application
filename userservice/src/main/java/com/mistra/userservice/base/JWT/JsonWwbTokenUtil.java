package com.mistra.userservice.base.JWT;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mistra.userservice.base.exception.BusinessErrorCode;
import com.mistra.userservice.base.exception.BusinessException;
import com.mistra.userservice.core.CurrentUserSession;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

/**
 * @Author: WangRui
 * @Date: 2018/11/30
 * Time: 15:00
 * Description: JWT工具类
 */
@Data
public class JsonWwbTokenUtil {

    private Logger logger = LoggerFactory.getLogger(JsonWwbTokenUtil.class);

    @Autowired
    private JsonWebTokenProperties jsonWebTokenProperties;

    @Autowired
    private ImUserBaseMapper imUserBaseMapper;

    @Autowired
    private JedisCommands redis;

    @Autowired
    private ImUserLoginLogService imUserLoginLogService;

    /**
     * 包含生成Token的密钥和编码算法
     */
    private Algorithm algorithm;

    private JWTVerifier jwtVerifier;

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public JWTVerifier getJwtVerifier() {
        return jwtVerifier;
    }

    public void setJwtVerifier(JWTVerifier jwtVerifier) {
        this.jwtVerifier = jwtVerifier;
    }

    /**
     * 生成token
     *
     * @param userId 把userId，versionCode编码到token负载中，同时把刷新token操作的过期时间也编码到负载中
     * @return token
     */
    public String generateToken(String userId, Integer versionCode) {
        //过期时间
        Date expire = Date.from(LocalDateTime.now().plusSeconds(jsonWebTokenProperties.getAccessTokenExpireTime()).atZone(ZoneId.systemDefault()).toInstant());
        //刷新时间
        Date refresh = Date.from(LocalDateTime.now().plusSeconds(jsonWebTokenProperties.getRefreshTokenExpireTime()).atZone(ZoneId.systemDefault()).toInstant());
        return JWT.create()
                .withClaim(JsonWebTokenConstant.TOKEN_USER_ID_FLAG, userId)
                .withClaim(JsonWebTokenConstant.REFRESH_TOKEN_FLAG, refresh)
                .withClaim(JsonWebTokenConstant.TOKEN_VERSION_ID_FLAG, versionCode)
                .withIssuer(JsonWebTokenConstant.ISSUER)
                .withExpiresAt(expire)
                .sign(algorithm);
    }

    /**
     * 刷新token时生成token，只需要吧tokenVersion +1
     *
     * @param userId userId
     * @return token
     */
    public String refreshGenerateTokenByRequestCode(String userId, HttpServletRequest request) {
        String token = this.getToken(request);
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        PlatformEnum platformEnum = this.getPlatformFromRequest(request);
        Integer requestCode = decodedJWT.getClaim(JsonWebTokenConstant.TOKEN_VERSION_ID_FLAG).asInt();
        Integer versionCode = this.increaseVersion(requestCode, JsonWebTokenConstant.TOKEN_VERSION_TAG);
        int result;
        if (platformEnum.equals(PlatformEnum.ANDROID) || platformEnum.equals(PlatformEnum.IOS)) {
            result = imUserBaseMapper.updateAppTokenVersion(requestCode, versionCode, Long.valueOf(userId), LocalDateTime.now());
        } else {
            result = imUserBaseMapper.updateWebTokenVersion(requestCode, versionCode, Long.valueOf(userId), LocalDateTime.now());
        }
        logger.debug("uid:{},result:{},requestVersionCode:{},addResultCode:{}--------------------------------------------refreshGenerateTokenByRequestCode()", userId, result, requestCode, versionCode);
        if (result == 0) {
            return null;
        }
        return this.generateToken(userId, versionCode);
    }

    /**
     * 刷新token时生成token，只需要吧tokenVersion +1
     *
     * @param userId userId
     * @return token
     */
    public String refreshGenerateTokenByDbCode(String userId, HttpServletRequest request) {
        PlatformEnum platformEnum = this.getPlatformFromRequest(request);
        Integer versionCode;
        ImUserBase imUserBase = imUserBaseMapper.selectByPrimaryKey(Long.valueOf(userId));
        if (platformEnum.equals(PlatformEnum.ANDROID) || platformEnum.equals(PlatformEnum.IOS)) {
            versionCode = imUserBase.getAppTokenVersion();
        } else {
            versionCode = imUserBase.getWebTokenVersion();
        }
        logger.debug("uid:{},dbResultCode:{}--------------------------------------------refreshGenerateTokenByDbCode()", userId, versionCode);
        return this.generateToken(userId, versionCode);
    }

    /**
     * 登录时生成token，需要把loginVersion和tokenVersion +1
     *
     * @param userId userId
     * @return token
     */
    public String loginGenerateToken(String userId, HttpServletRequest request) {
        //统计登录人数
        this.countLoginUser(userId);
        //loginVersion和tokenVersion都+1
        Integer versionCode = this.addAndUpdateVersionCodeGetBuyDb(userId, request, JsonWebTokenConstant.LOGIN_VERSION_TAG);
        try {
            // 添加登陆日志 start
            // 十进制转二进制
            String sourceBinary = BitUtils.decimalToBinary(versionCode);
            // 截取登陆版本号
            String loginVersionBinary = sourceBinary.substring(sourceBinary.length() - jsonWebTokenProperties.getLoginTokenVersionCodeTokenLength());
            //记录登陆日志
            ImUserLoginLogDTO loginLogDTO = getLoginHeader(request);
            loginLogDTO.setRevUid(Long.parseLong(userId));
            loginLogDTO.setLoginVersion(BitUtils.binaryToDecimal(loginVersionBinary));
            imUserLoginLogService.insert(loginLogDTO);
        } catch (Exception e) {
            logger.error("add login log fail,error message:{}", e.getMessage());
        }
        return this.generateToken(userId, versionCode);
    }

    /**
     * 根据userId和HttpServletRequest 从数据库获取login和token版本号,版本+1之后更新数据库
     *
     * @param userId  userId
     * @param request HttpServletRequest
     * @param type    1-loginVersion和tokenVersion都+1   2-只是刷新token，只tokenVersion +1
     * @return Integer
     */
    public Integer addAndUpdateVersionCodeGetBuyDb(String userId, HttpServletRequest request, int type) {
        ImUserBase imUserBase = imUserBaseMapper.selectByPrimaryKey(Long.valueOf(userId));
        PlatformEnum platformEnum = this.getPlatformFromRequest(request);
        Integer versionCode;
        if (platformEnum.equals(PlatformEnum.ANDROID) || platformEnum.equals(PlatformEnum.IOS)) {
            versionCode = this.increaseVersion(imUserBase.getAppTokenVersion(), type);
            imUserBaseMapper.updateAppTokenVersion(imUserBase.getAppTokenVersion(), versionCode, Long.valueOf(userId), LocalDateTime.now());
        } else {
            versionCode = this.increaseVersion(imUserBase.getWebTokenVersion(), type);
            imUserBaseMapper.updateWebTokenVersion(imUserBase.getWebTokenVersion(), versionCode, Long.valueOf(userId), LocalDateTime.now());
        }
        return versionCode;
    }

    /**
     * 统计登录人数
     *
     * @param userId 用户ID
     */
    private void countLoginUser(String userId) {
        try {
            String prefix;
            if (userId.length() > StatisticsUtils.USER_ID_LENGTH) {
                prefix = userId.substring(0, 2);
            } else {
                prefix = userId.substring(0, 1);
            }
            String redisKey = StatisticsUtils.createRedisKey(StatisticsUtils.StatisticsTypeEnum.LOGIN_USERS.name()) + prefix;
            redis.setbit(redisKey, Long.valueOf(userId) - Long.valueOf(prefix) * StatisticsUtils.USER_ID_DEFAULT_VALUE, true);
            redis.expire(redisKey, StatisticsUtils.REDIS_KEY_EXPIRE_TIME);
        } catch (Exception e) {
            logger.error("statistics LOGIN_USERS error ,userId:{},e:{}", userId, e);
        }
    }

    /**
     * 从token获取version code
     *
     * @param request
     * @return
     */
    public Integer getVersionCodeFromHttpServletRequest(HttpServletRequest request) {
        String token = this.getToken(request);
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        return decodedJWT.getClaim(JsonWebTokenConstant.TOKEN_VERSION_ID_FLAG).asInt();
    }

    /**
     * 验证token，返回状态
     *
     * @param request request
     * @return JsonWebTokenVerifyStatus
     */
    public JsonWebTokenVerifyStatus verification(HttpServletRequest request) {
        String token = this.getToken(request);
        if (StringUtils.isEmpty(token)) {
            logger.info("Token is empty！To login！");
            return JsonWebTokenVerifyStatus.LOGIN;
        }
        logger.debug("uid:{},token:{}", request.getHeader(RequestConstans.USER_ID), token);
        try {
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            Map<String, Claim> claimMap = decodedJWT.getClaims();

            Optional<Claim> refreshOptional = Optional.ofNullable(claimMap.get(JsonWebTokenConstant.REFRESH_TOKEN_FLAG));
            Long refresh = refreshOptional.isPresent() ? refreshOptional.get().asLong() : claimMap.get("refresh").asLong();
            Optional<Claim> userIdOptional = Optional.ofNullable(claimMap.get(JsonWebTokenConstant.TOKEN_USER_ID_FLAG));
            String userId = userIdOptional.isPresent() ? userIdOptional.get().asString() : claimMap.get("userId").asString();
            //保存当前userId
            CurrentUserSession.setUserId(Long.valueOf(userId));
            if (!userId.equals(request.getHeader(RequestConstans.USER_ID))) {
                //验证token中的uid是否与header中的uid一致
                logger.error("header in uid and user_token in uid differ error {} ", userId);
                throw new BusinessException(BusinessErrorCode.VOICE_CALL_PARAM_ERROR);
            }
            if (refresh < System.currentTimeMillis()) {
                //刷新 token
                Integer versionCode = claimMap.get(JsonWebTokenConstant.TOKEN_VERSION_ID_FLAG).asInt();
                if (versionCode == null) {
                    //兼容老token
                    return JsonWebTokenVerifyStatus.CREATE_NEW;
                }
                //验证token中的版本号是否与数据库中的是否一致，不一致的话转登录
                LoginTokenVersionCompareEnum result = this.checkVersion(versionCode, request, Long.valueOf(userId));
                if (result.equals(LoginTokenVersionCompareEnum.TOKEN_DIFFERENCE)) {
                    //tokenVersion版本不一致，提示去登录
                    return JsonWebTokenVerifyStatus.LOGIN;
                } else if (result.equals(LoginTokenVersionCompareEnum.IN_CONSISTENT)) {
                    //loginVersion和tokenVersion不一致，在别处登录过
                    return JsonWebTokenVerifyStatus.LOGIN_OTHER;
                } else if (result.equals(LoginTokenVersionCompareEnum.LATEST_REFRESH)) {
                    //近期(30秒内)刷新过token，
                    return JsonWebTokenVerifyStatus.LATEST_REFRESH;
                } else {
                    //loginVersion和tokenVersion都一致，刷新token
                    logger.info(JsonWebTokenVerifyStatus.CREATE_NEW.getMessage());
                    return JsonWebTokenVerifyStatus.CREATE_NEW;
                }
            }
            return JsonWebTokenVerifyStatus.SUCCESS;
        } catch (BusinessException b) {
            throw b;
        } catch (JWTVerificationException JWTVerificationException) {
            //token验证失败，token过期或者是假token
            logger.info(JsonWebTokenVerifyStatus.LOGIN.getMessage());
            return JsonWebTokenVerifyStatus.LOGIN;
        }
    }

    /**
     * 校验版本号
     *
     * @param requestVersionCode 请求token里携带的版本号
     * @param request            request
     * @param userId             userId
     * @return Boolean
     */
    public LoginTokenVersionCompareEnum checkVersion(Integer requestVersionCode, HttpServletRequest request, Long userId) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        int loginAndTokenLength = jsonWebTokenProperties.getLoginTokenVersionCodeTokenLength();
        ImUserBase imUserBase = imUserBaseMapper.selectByPrimaryKey(userId);
        PlatformEnum platformEnum = this.getPlatformFromRequest(request);
        Date date;
        Integer dbVersionCode;
        if (platformEnum.equals(PlatformEnum.ANDROID) || platformEnum.equals(PlatformEnum.IOS)) {
            dbVersionCode = imUserBase.getAppTokenVersion();
            date = imUserBase.getAppTokenRefreshTime();
        } else {
            dbVersionCode = imUserBase.getWebTokenVersion();
            date = imUserBase.getWebTokenRefreshTime();
        }
        String requestVersionCodeBinary = BitUtils.decimalToBinary(requestVersionCode);
        String dbVersionCodeBinary = BitUtils.decimalToBinary(dbVersionCode);
        Boolean tokenCompareResult = requestVersionCodeBinary.substring(requestVersionCodeBinary.length() - loginAndTokenLength)
                .equals(dbVersionCodeBinary.substring(dbVersionCodeBinary.length() - loginAndTokenLength));
        Boolean loginCompareResult = requestVersionCodeBinary.substring(requestVersionCodeBinary.length() - 2 * loginAndTokenLength, requestVersionCodeBinary.length() - loginAndTokenLength)
                .equals(dbVersionCodeBinary.substring(dbVersionCodeBinary.length() - 2 * loginAndTokenLength, dbVersionCodeBinary.length() - loginAndTokenLength));
        //token版本仅相差1的情况  说明近期有请求刷新过token
        Integer addResultCode = this.increaseVersion(requestVersionCode, JsonWebTokenConstant.TOKEN_VERSION_TAG);
        logger.debug("requestVersionCode:{},addResultCode:{},dbVersionCode:{}", requestVersionCode, addResultCode, dbVersionCode);
        boolean tokenVersionDifferOne = addResultCode.equals(dbVersionCode);
        Long intervalTime = System.currentTimeMillis() - date.getTime();
        logger.debug("loginCompareResult:{},tokenCompareResult:{},tokenVersionDifferOne:{},intervalTime:{},latestRefresh:{},SystemTime:{},dbTime:{}",
                loginCompareResult, tokenCompareResult, tokenVersionDifferOne, intervalTime, intervalTime <= JsonWebTokenConstant.LATEST_REFRESH_TIME, simpleDateFormat.format(new Date()), simpleDateFormat.format(date));
        if (loginCompareResult && tokenCompareResult) {
            logger.debug("uid:{}--------------------------------------------,LoginTokenVersionCompareEnum.CONSISTENT", userId);
            //都一致
            return LoginTokenVersionCompareEnum.CONSISTENT;
        }
//        if (loginCompareResult && tokenVersionDifferOne && intervalTime <= JsonWebTokenConstant.LATEST_REFRESH_TIME) {
        if (loginCompareResult && tokenVersionDifferOne) {
            //暂时忽略时间判断
            logger.debug("uid:{}--------------------------------------------,LoginTokenVersionCompareEnum.LATEST_REFRESH", userId);
            //版本相差1且同时期有请求刷新过token  30秒内
            return LoginTokenVersionCompareEnum.LATEST_REFRESH;
        }
        if (loginCompareResult) {
            logger.debug("uid:{}--------------------------------------------,LoginTokenVersionCompareEnum.TOKEN_DIFFERENCE", userId);
            //login版本一致，token版本不一致，提示去登录
            return LoginTokenVersionCompareEnum.TOKEN_DIFFERENCE;
        }
        logger.debug("uid:{}--------------------------------------------,LoginTokenVersionCompareEnum.IN_CONSISTENT", userId);
        //都不一致，说明该账号在其他设备登录过，提示去登录
        return LoginTokenVersionCompareEnum.IN_CONSISTENT;
    }

    /**
     * 从HttpServletRequest 获取登录平台
     *
     * @param request HttpServletRequest
     * @return PlatformEnum
     */
    public PlatformEnum getPlatformFromRequest(HttpServletRequest request) {
        String tag = request.getHeader(RequestConstans.HEAD_PLATFORM);
        return this.getPlatformByString(tag);
    }

    /**
     * 获取登录平台
     *
     * @param platform platform
     * @return PlatformEnum
     */
    public PlatformEnum getPlatformByString(String platform) {
        if (platform.equals(PlatformEnum.ANDROID.getMessage())) {
            return PlatformEnum.ANDROID;
        } else if (platform.equals(PlatformEnum.IOS.getMessage())) {
            return PlatformEnum.IOS;
        } else {
            return PlatformEnum.H5;
        }
    }

    /**
     * login或token版本号增加1
     * 先转换成二进制,拆分出login版本和token版本,然后增加1,最后拼接数据返回
     *
     * @param source 当前version
     * @param type   1-login和token+1  2-只token+1
     * @return 增加后的version
     */
    public Integer increaseVersion(Integer source, int type) {
        String sourceBinary = BitUtils.decimalToBinary(source);
        int loginAndTokenLength = jsonWebTokenProperties.getLoginTokenVersionCodeTokenLength() * 2;
        if (sourceBinary.length() < loginAndTokenLength) {
            logger.info("Login and token version code error!");
            throw new BusinessException(BusinessErrorCode.FAIL);
        }
        //拆分二进制字符串
        String headerBinary = sourceBinary.substring(0, sourceBinary.length() - loginAndTokenLength);
        String loginVersionBinary = this.getLoginVersion(sourceBinary, loginAndTokenLength);
        String tokenVersionBinary = this.getTokenVersion(sourceBinary);
        if (type == JsonWebTokenConstant.LOGIN_VERSION_TAG) {
            loginVersionBinary = BitUtils.binaryAdd(loginVersionBinary, "1");
            //超过14位就复位
            loginVersionBinary = loginVersionBinary.length() > jsonWebTokenProperties.getLoginTokenVersionCodeTokenLength() ? JsonWebTokenConstant.VERSION_INITIAL_VALUE : loginVersionBinary;
            tokenVersionBinary = BitUtils.binaryAdd(tokenVersionBinary, "1");
        } else if (type == JsonWebTokenConstant.TOKEN_VERSION_TAG) {
            tokenVersionBinary = BitUtils.binaryAdd(tokenVersionBinary, "1");
        } else {
            logger.info("JsonWebTokenUtils increaseVersion type error!");
            throw new BusinessException(BusinessErrorCode.FAIL);
        }
        tokenVersionBinary = tokenVersionBinary.length() > jsonWebTokenProperties.getLoginTokenVersionCodeTokenLength() ? JsonWebTokenConstant.VERSION_INITIAL_VALUE : tokenVersionBinary;
        Integer resultCode = BitUtils.binaryToDecimal(headerBinary + loginVersionBinary + tokenVersionBinary);
        logger.debug("sourceCode:{},resultCode:{}", source, resultCode);
        return resultCode;
    }

    /**
     * 从token中解析出userId
     *
     * @param token
     * @return
     */
    public String parseTokenGetUserId(String token) {
        DecodedJWT jwt = jwtVerifier.verify(token);
        return jwt.getClaim(JsonWebTokenConstant.TOKEN_USER_ID_FLAG).asString();
    }

    /**
     * 从HttpServletRequest拿到token
     *
     * @param httpServletRequest 从httpServletRequest中获取token
     * @return
     */
    public String getToken(HttpServletRequest httpServletRequest) {
        String token = this.getTokenFromHeader(httpServletRequest);
        if (StringUtils.isEmpty(token)) {
            token = this.getTokenFromQuery(httpServletRequest);
        }
        return token;
    }

    /**
     * 从请求头获取
     *
     * @param request
     * @return
     */
    private String getTokenFromHeader(HttpServletRequest request) {
        String header = request.getHeader("user_token");
        return StringUtils.isEmpty(header) ? null : header;
    }

    /**
     * 从携带参数获取
     *
     * @param request
     * @return
     */
    private String getTokenFromQuery(HttpServletRequest request) {
        return request.getParameter("user_token");
    }

    /**
     * 获取登陆headers
     *
     * @param request
     * @return ImUserLoginLogDTO
     */
    private static ImUserLoginLogDTO getLoginHeader(HttpServletRequest request) {
        //mac地址
        String deviceId = request.getHeader(RequestConstans.HEAD_DEVICE_ID);
        String platform = request.getHeader(RequestConstans.CLIENT_OS);
        //操作系统
        Integer clientOs = platform == null ? ClientOsEnum.UNKNOWN.getIndex() : Integer.valueOf(platform);
        //操作系统版本号
        String clientOsVersion = request.getHeader(RequestConstans.HEAD_PLATFORM_V);
        //客户端版本号
        String clientAppVersion = request.getHeader(RequestConstans.CLIENT_V);
        //ip
        String ip = HttpServletHelper.getRequestIpAddr(request);
        // mac(android)
        String mac = request.getHeader(RequestConstans.MAC);
        // IMEI手机序列号、手机“串号”(Android)
        String imei = request.getHeader(RequestConstans.IMEI);
        // IDFA广告标示符（IOS）
        String idfa = request.getHeader(RequestConstans.IDFA);

        // builder ImUserLoginLogDTO
        return ImUserLoginLogDTO.builder().
                deviceId(deviceId)
                .ip(ip)
                .clientOs(clientOs)
                .clientOsVersion(clientOsVersion)
                .clientAppVersion(clientAppVersion)
                .mac(mac)
                .imei(imei)
                .idfa(idfa)
                .build();
    }

    /**
     * 截取登陆版本二进制
     *
     * @param sourceBinary
     * @param loginAndTokenLength login 与 token 版本的长度
     * @return login version
     */
    public String getLoginVersion(String sourceBinary, Integer loginAndTokenLength) {
        int start = sourceBinary.length() - loginAndTokenLength;
        int end = sourceBinary.length() - jsonWebTokenProperties.getLoginTokenVersionCodeTokenLength();
        return sourceBinary.substring(start, end);
    }

    /**
     * 获取token版本二进制
     *
     * @param sourceBinary
     * @return TokenVersion
     */
    public String getTokenVersion(String sourceBinary) {
        return sourceBinary.substring(sourceBinary.length() - jsonWebTokenProperties.getLoginTokenVersionCodeTokenLength());
    }
}
