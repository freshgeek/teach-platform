package org.ck.teach.teachplatform.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;

/**
 * @author chen.chao
 */
@Slf4j
public class NetUtils {

    /***
     * 获取当前网络ip
     * @param request
     * @param serial 连续IP
     * @return
     */
    public static String getIpAddr(HttpServletRequest request, boolean serial) {
        // nginx反向代理
        String remote_addr = request.getHeader("X-Real-IP");
        if (!StringUtils.isEmpty(remote_addr)) {
            return remote_addr;
        }
        // apache反向代理
        remote_addr = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isEmpty(remote_addr)) {
            String[] ips = remote_addr.split(",");
            for (String ip : ips) {
                if (!"null".equalsIgnoreCase(ip)) {
                    return ip;
                }
            }
        }
        if (remote_addr == null || remote_addr.length() == 0 || "unknown".equalsIgnoreCase(remote_addr)) {
            remote_addr = request.getHeader("Proxy-Client-IP");
        }
        if (remote_addr == null || remote_addr.length() == 0 || "unknown".equalsIgnoreCase(remote_addr)) {
            remote_addr = request.getHeader("WL-Proxy-Client-IP");
        }
        if (remote_addr == null || remote_addr.length() == 0 || "unknown".equalsIgnoreCase(remote_addr)) {
            remote_addr = request.getRemoteAddr();
        }
        if (remote_addr != null && remote_addr.length() != 0 && !"unknown".equalsIgnoreCase(remote_addr)
                && ("localhost".equals(remote_addr)||"127.0.0.1".equals(remote_addr) || "0:0:0:0:0:0:0:1".equals(remote_addr) || "0:0:0:0:0:0:0:1%0".equals(remote_addr))) {
            try {
                InetAddress iaClient  = InetAddress.getLocalHost();
                remote_addr = iaClient.getHostAddress() ;
            } catch (Exception e) {
                log.error("",e);
            }
        }
        // 如果是多级代理的话,通过x-forwarded-for可能返回多个IP的现象,这里我始终取第一个有效的IP
        return serial?remote_addr:remote_addr.split(",")[0];
    }


}
