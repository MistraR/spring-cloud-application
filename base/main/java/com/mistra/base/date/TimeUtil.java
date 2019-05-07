package com.mistra.base.date;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @Author: WangRui
 * @Date: 2018-09-18
 * Time: 下午3:39
 * Description:
 */
public class TimeUtil {

    public static Date getNowTime() {
        LocalDateTime dateTime = LocalDateTime.now(ZoneId.of("UTC"));
        Date date = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
        return date;
    }

    public static Long getNowTimeStamp() {
        return getNowTime().getTime();
    }

}
