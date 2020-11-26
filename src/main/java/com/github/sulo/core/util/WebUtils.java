package com.github.sulo.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author sorata 2020-11-17:15:36
 */
public abstract class WebUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger("WebUtils");
    private static final String UNKNOWN = "unknown";

    public static HttpServletRequest getCurrentRequest() {
        final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }
        ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
        return attributes.getRequest();
    }

    public static String getAllIp(HttpServletRequest request) {
        Objects.requireNonNull(request, "HttpServletRequest must not be null");
        String ip = request.getHeader("X-REAL-IP");
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-FORWARDED-FOR");
        }
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("PROXY-CLIENT-IP");
        }
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static String getIp(HttpServletRequest request) {
        String ip = getAllIp(request);
        if (ip.contains(",")) {
            ip = ip.split(",")[0];
        }
        return ip;
    }

    public static String getIp() {
        final HttpServletRequest currentRequest = getCurrentRequest();
        return getIp(currentRequest);
    }


    public static void write(HttpServletResponse response, byte[] data, int httpStatus, String contentType) {
        Objects.requireNonNull(response, "HttpServletResponse must not be null");
        response.setContentType(contentType);
        response.setCharacterEncoding("utf-8");
        response.setStatus(httpStatus);
        try (final ServletOutputStream outputStream = response.getOutputStream()) {
            outputStream.write(data);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void write(HttpServletResponse response, byte[] bytes) {
        write(response, bytes, 200, "application/json");
    }


    public static Cookie getCookie(String name) {
        final HttpServletRequest request = getCurrentRequest();
        return getCookie(name, request);
    }

    public static Cookie getCookie(String name, HttpServletRequest request) {
        if (request == null) {
            throw new IllegalStateException("no request");
        }
        final Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return cookie;
            }
        }
        return null;
    }

    public static String getBasePath(){
        final HttpServletRequest currentRequest = getCurrentRequest();
        if (currentRequest == null){
            LOGGER.error("无法获取到服务器地址");
            return "";
        }
        String scheme = currentRequest.getHeader("X-Forwarded-Proto");
        if (scheme == null || scheme.isEmpty()){
            scheme = currentRequest.getScheme();
        }
        return scheme +"://" + currentRequest.getServerName() + ":" + currentRequest.getServerPort() + currentRequest.getContextPath() +"/";
    }
}
