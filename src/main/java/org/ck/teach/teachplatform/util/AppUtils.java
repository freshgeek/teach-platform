package org.ck.teach.teachplatform.util;

import org.ck.teach.teachplatform.common.AppConst;
import org.ck.teach.teachplatform.common.Response;
import org.ck.teach.teachplatform.common.SysCodeEnum;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * @author chen.chao
 * @version 1.0
 * @date 2019/9/27 9:58
 * @description
 */
public class AppUtils {


    public static String MD5(String pd) {
        return DigestUtils.md5DigestAsHex(pd.getBytes());
    }

    public static String getMapStr(Map param, String key) {
        Object o = param.get(key);
        return o == null ? AppConst.EMPTY_STR : o.toString();
    }

    public static Date getMapDateTime(Map param, String key) {
        return getMapDateTime(param, key, true);
    }

    public static Date getMapDateTime(Map param, String key, boolean timeMillis) {
        Object o = param.get(key);
        if (o == null) {
            return null;
        }
        long parseLong = 0L;
        if (timeMillis) {
            parseLong = Long.parseLong(o.toString().trim());
        } else {
            parseLong = Long.parseLong(o.toString().trim() + "000");
        }
        return new Date(parseLong);
    }

    public static Double getMapDouble(Map param, String key) {
        Object o = param.get(key);
        return o == null ? 0d : Double.valueOf(o.toString());
    }


    public static String getStrExceptionStack(Throwable e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw, true));
        return sw.getBuffer().toString();
    }

    public static boolean isEmptyStr(String forwardurl) {
        if (StringUtils.isEmpty(forwardurl) || AppConst.NULL_STR.equals(forwardurl)) {
            return true;
        }
        return false;
    }

    public static boolean isSuccess(Response login) {
        return SysCodeEnum.SUCCESS_EXECUTE.getCode().equals(login.getCode());
    }

    public static Integer getMapInteger(Map<String, String> convertStoreMap, String ip) {
        return Integer.valueOf(getMapStr(convertStoreMap, ip));
    }


    public static Date toDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getBeforeDay(int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -i);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /***
     *  判断是否相差 day 天
     * @param signDate
     * @param day
     * @param date
     * @return
     */
    public static boolean dateStep(Date signDate, int day, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(signDate);
        calendar.add(Calendar.DATE, day);
        Calendar end = Calendar.getInstance();
        end.setTime(date);
        return sameDay(calendar.getTime(),end.getTime());
    }

    /***
     *  判断同一天
     * @param s
     * @param t
     * @return
     */
    public static boolean sameDay(Date s, Date t) {
        Calendar calDateA = Calendar.getInstance();
        calDateA.setTime(s);

        Calendar calDateB = Calendar.getInstance();
        calDateB.setTime(t);

        return calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR)
                && calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH)
                && calDateA.get(Calendar.DAY_OF_MONTH) == calDateB
                .get(Calendar.DAY_OF_MONTH);

    }
}
