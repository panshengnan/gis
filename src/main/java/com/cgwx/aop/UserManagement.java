package com.cgwx.aop;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import static com.cgwx.aop.Constant.SERVER_ADDRESS;

public class UserManagement {

    public Object getAccountId(){
        String resultJson = "";
        try {
            HttpClient httpClient = new HttpClient();
            httpClient.getParams().setContentCharset("UTF-8");
            PostMethod postMethod = new PostMethod(SERVER_ADDRESS+"getAccountId");
            RequestEntity requestEntity = new StringRequestEntity("", "application/xml", "UTF-8");
            postMethod.setRequestEntity(requestEntity);
            httpClient.executeMethod(postMethod);
            resultJson = new String(postMethod.getResponseBody());
        } catch (Exception var10) {
            resultJson = "请求用户管理服务出错";
        }
      return resultJson;
    }

    public Object getUserId(){
        String resultJson = "";
        try {
            HttpClient httpClient = new HttpClient();
            httpClient.getParams().setContentCharset("UTF-8");
            PostMethod postMethod = new PostMethod(SERVER_ADDRESS+"getUserId");
            RequestEntity requestEntity = new StringRequestEntity("", "application/xml", "UTF-8");
            postMethod.setRequestEntity(requestEntity);
            httpClient.executeMethod(postMethod);
            resultJson = new String(postMethod.getResponseBody());
        } catch (Exception var10) {
            resultJson = "请求用户管理服务出错";
        }
        return resultJson;
    }

    public Object getUser(){
        String resultJson = "";
        try {
            HttpClient httpClient = new HttpClient();
            httpClient.getParams().setContentCharset("UTF-8");
            PostMethod postMethod = new PostMethod(SERVER_ADDRESS+"getUser");
            RequestEntity requestEntity = new StringRequestEntity("", "application/xml", "UTF-8");
            postMethod.setRequestEntity(requestEntity);
            httpClient.executeMethod(postMethod);
            resultJson = new String(postMethod.getResponseBody());
        } catch (Exception var10) {
            resultJson = "请求用户管理服务出错";
        }
        return resultJson;
    }

    public Object getUserbyAccountId(String account){
        String resultJson = "";
        try {
            HttpClient httpClient = new HttpClient();
            httpClient.getParams().setContentCharset("UTF-8");
            PostMethod postMethod = new PostMethod(SERVER_ADDRESS+"getUserbyAccountId");
            RequestEntity requestEntity = new StringRequestEntity("", "application/xml", "UTF-8");
            postMethod.setRequestEntity(requestEntity);
            postMethod.setParameter("account", account);
            httpClient.executeMethod(postMethod);
            resultJson = new String(postMethod.getResponseBody());
        } catch (Exception var10) {
            resultJson = "请求用户管理服务出错";
        }
        return resultJson;
    }

    public Object getAllCheckUser(){
        String resultJson = "";
        try {
            HttpClient httpClient = new HttpClient();
            httpClient.getParams().setContentCharset("UTF-8");
            PostMethod postMethod = new PostMethod(SERVER_ADDRESS+"getAllCheckUser");
            RequestEntity requestEntity = new StringRequestEntity("", "application/xml", "UTF-8");
            postMethod.setRequestEntity(requestEntity);
            httpClient.executeMethod(postMethod);
            resultJson = new String(postMethod.getResponseBody());
        } catch (Exception var10) {
            resultJson = "请求用户管理服务出错";
        }
        return resultJson;
    }

    public Object getAllConsumer(){
        String resultJson = "";
        try {
            HttpClient httpClient = new HttpClient();
            httpClient.getParams().setContentCharset("UTF-8");
            PostMethod postMethod = new PostMethod(SERVER_ADDRESS+"getAllConsumer");
            RequestEntity requestEntity = new StringRequestEntity("", "application/xml", "UTF-8");
            postMethod.setRequestEntity(requestEntity);
            httpClient.executeMethod(postMethod);
            resultJson = new String(postMethod.getResponseBody());
        } catch (Exception var10) {
            resultJson = "请求用户管理服务出错";
        }
        return resultJson;
    }

}
