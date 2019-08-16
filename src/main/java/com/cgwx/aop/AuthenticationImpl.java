package com.cgwx.aop;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.cgwx.aop.Constant.SERVER_ADDRESS;

@Component
@Aspect
public class AuthenticationImpl {

    @Pointcut("@annotation(com.cgwx.aop.Permission)")
    private void cut() {
    }

    @Around("cut()")
    public Object doAuthorization(ProceedingJoinPoint joinPoint) throws Throwable {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURI();
        String resultJson = "";
        try {
            HttpClient httpClient = new HttpClient();
            httpClient.getParams().setContentCharset("UTF-8");
            PostMethod postMethod = new PostMethod(SERVER_ADDRESS+"judgePermission");
            RequestEntity requestEntity = new StringRequestEntity("", "application/xml", "UTF-8");
            postMethod.setRequestEntity(requestEntity);
            postMethod.setParameter("url", url);
            Header header = new Header();
            header.setName("Cookie");
            header.setValue(request.getHeader("Cookie"));
            postMethod.setRequestHeader(header);
            httpClient.executeMethod(postMethod);
            resultJson = new String(postMethod.getResponseBody());
            if (resultJson == null || resultJson.equals("")) {
                resultJson = "未登录";
            }
        } catch (Exception var10) {
            resultJson = "请求用户管理服务出错";
        }
        System.out.println(resultJson);
        if (resultJson.equals("请求用户管理服务出错"))
            return "请求用户管理服务出错!";
        if (resultJson.equals("未登录"))
            return "NotLoggedIn";
        if (resultJson.equals("false"))
            return "NoPermission";
        try {
            joinPoint.proceed();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Object object = joinPoint.proceed();
        return object;
    }
}
