package com.cgwx.aop;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.cgwx.aop.Constant.SERVER_ADDRESS;

public class UserManagement {

    public Object getAccountId(){
        String resultJson = "";
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        try {
            HttpClient httpClient = new HttpClient();
            httpClient.getParams().setContentCharset("UTF-8");
            PostMethod postMethod = new PostMethod(SERVER_ADDRESS+"getAccountId");
            RequestEntity requestEntity = new StringRequestEntity("", "application/xml", "UTF-8");
            postMethod.setRequestEntity(requestEntity);
            Header header = new Header();
            header.setName("Cookie");
            header.setValue(request.getHeader("Cookie"));
            postMethod.setRequestHeader(header);
            httpClient.executeMethod(postMethod);
            resultJson = new String(postMethod.getResponseBody());
        } catch (Exception var10) {
            resultJson = "请求用户管理服务出错";
        }
      return resultJson;
    }

    public Object getUserId(){
        String resultJson = "";
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        try {

            HttpClient httpClient = new HttpClient();
            httpClient.getParams().setContentCharset("UTF-8");
            PostMethod postMethod = new PostMethod(SERVER_ADDRESS+"getUserId");
            RequestEntity requestEntity = new StringRequestEntity("", "application/xml", "UTF-8");
            postMethod.setRequestEntity(requestEntity);
            Header header = new Header();
            header.setName("Cookie");
            header.setValue(request.getHeader("Cookie"));
            postMethod.setRequestHeader(header);
            httpClient.executeMethod(postMethod);
            resultJson = new String(postMethod.getResponseBody());
        } catch (Exception var10) {
            resultJson = "请求用户管理服务出错";
        }
        return resultJson;
    }

    public Object getUser(){
        String resultJson = "";
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        try {
            HttpClient httpClient = new HttpClient();
            httpClient.getParams().setContentCharset("UTF-8");
            PostMethod postMethod = new PostMethod(SERVER_ADDRESS+"getUser");
            RequestEntity requestEntity = new StringRequestEntity("", "application/xml", "UTF-8");
            postMethod.setRequestEntity(requestEntity);
            Header header = new Header();
            header.setName("Cookie");
            header.setValue(request.getHeader("Cookie"));
            postMethod.setRequestHeader(header);
            httpClient.executeMethod(postMethod);
            resultJson = new String(postMethod.getResponseBody());
        } catch (Exception var10) {
            resultJson = "请求用户管理服务出错";
        }
        return resultJson;
    }

    public Object getUserbyAccountId(String account){
        String resultJson = "";
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        try {
            HttpClient httpClient = new HttpClient();
            httpClient.getParams().setContentCharset("UTF-8");
            PostMethod postMethod = new PostMethod(SERVER_ADDRESS+"getUserbyAccountId");
            RequestEntity requestEntity = new StringRequestEntity("", "application/xml", "UTF-8");
            postMethod.setRequestEntity(requestEntity);
            postMethod.setParameter("account", account);
            Header header = new Header();
            header.setName("Cookie");
            header.setValue(request.getHeader("Cookie"));
            postMethod.setRequestHeader(header);
            httpClient.executeMethod(postMethod);
            resultJson = new String(postMethod.getResponseBody());
        } catch (Exception var10) {
            resultJson = "请求用户管理服务出错";
        }
        return resultJson;
    }

    public Object getAllCheckUser(){
        String resultJson = "";
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        try {
            HttpClient httpClient = new HttpClient();
            httpClient.getParams().setContentCharset("UTF-8");
            PostMethod postMethod = new PostMethod(SERVER_ADDRESS+"getAllCheckUser");
            RequestEntity requestEntity = new StringRequestEntity("", "application/xml", "UTF-8");
            postMethod.setRequestEntity(requestEntity);
            Header header = new Header();
            header.setName("Cookie");
            header.setValue(request.getHeader("Cookie"));
            postMethod.setRequestHeader(header);
            httpClient.executeMethod(postMethod);
            resultJson = new String(postMethod.getResponseBody());
        } catch (Exception var10) {
            resultJson = "请求用户管理服务出错";
        }
        return resultJson;
    }

    public Object getAllConsumer(){
        String resultJson = "";
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        try {
            HttpClient httpClient = new HttpClient();
            httpClient.getParams().setContentCharset("UTF-8");
            PostMethod postMethod = new PostMethod(SERVER_ADDRESS+"getAllConsumer");
            RequestEntity requestEntity = new StringRequestEntity("", "application/xml", "UTF-8");
            postMethod.setRequestEntity(requestEntity);
            Header header = new Header();
            header.setName("Cookie");
            header.setValue(request.getHeader("Cookie"));
            postMethod.setRequestHeader(header);
            httpClient.executeMethod(postMethod);
            resultJson = new String(postMethod.getResponseBody());
        } catch (Exception var10) {
            resultJson = "请求用户管理服务出错";
        }
        return resultJson;
    }


    public Object judgePermission(){
        String resultJson = "";
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        try {
            HttpClient httpClient = new HttpClient();
            httpClient.getParams().setContentCharset("UTF-8");
            PostMethod postMethod = new PostMethod(SERVER_ADDRESS+"judgePermission");
            RequestEntity requestEntity = new StringRequestEntity("", "application/xml", "UTF-8");
            postMethod.setRequestEntity(requestEntity);
            Header header = new Header();
            header.setName("Cookie");
            header.setValue(request.getHeader("Cookie"));
            postMethod.setRequestHeader(header);
            httpClient.executeMethod(postMethod);
            resultJson = new String(postMethod.getResponseBody());
        } catch (Exception var10) {
            resultJson = "请求用户管理服务出错";
        }
        return resultJson;
    }
}
