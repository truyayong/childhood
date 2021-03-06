package com.dazui.childhood.alioss.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.dazui.childhood.alioss.controller.AliOssController;


@Service
public class AliOssTokenService {

    // 目前只有"cn-hangzhou"这个region可用, 不要使用填写其他region的值
    public static final String REGION_CN_HANGZHOU = "cn-hangzhou";
    public static final String STS_API_VERSION = "2015-04-01";
    private static final String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
    private static final String gAccessKeyId = "LTAIgLoRZvwb2rqy";
    private static final String gAccessKeySecret = "EAyBXptPSkq4FVjtEPXcNtFkHSH1SR";
    private static final OSSClient ossClient = new OSSClient(endpoint, gAccessKeyId, gAccessKeySecret);
    
    private Logger logger = LoggerFactory.getLogger(AliOssTokenService.class);
    
    /**
     * 处理客户端获取Token的请求
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    public void handleStsRequest(HttpServletRequest request, HttpServletResponse response) 
    		throws IOException, ServletException {
    	// 只有 RAM用户（子账号）才能调用 AssumeRole 接口
        // 阿里云主账号的AccessKeys不能用于发起AssumeRole请求
        // 请首先在RAM控制台创建一个RAM用户，并为这个用户创建AccessKeys
    	String data = ReadJson("classpath:data/config.json");
    	JSONObject jsonObject = JSON.parseObject(data);
        String accessKeyId = jsonObject.getString("AccessKeyID");
        String accessKeySecret = jsonObject.getString("AccessKeySecret");

        // RoleArn 需要在 RAM 控制台上获取
        String roleArn = jsonObject.getString("RoleArn");
        long durationSeconds = jsonObject.getLong("TokenExpireTime");
        String policy = ReadJson(jsonObject.getString("PolicyFile"));
        logger.error("[truyayong] data : " + data);
        // RoleSessionName 是临时Token的会话名称，自己指定用于标识你的用户，主要用于审计，或者用于区分Token颁发给谁
        // 但是注意RoleSessionName的长度和规则，不要有空格，只能有'-' '_' 字母和数字等字符
        // 具体规则请参考API文档中的格式要求
        String roleSessionName = "alice-001";

        // 此处必须为 HTTPS
        ProtocolType protocolType = ProtocolType.HTTPS;
        
        try {
            final AssumeRoleResponse stsResponse = assumeRole(accessKeyId, accessKeySecret, roleArn, roleSessionName,
                    policy, protocolType, durationSeconds);

            Map<String, String> respMap = new LinkedHashMap<String, String>();
            respMap.put("status", "200");
            respMap.put("AccessKeyId", stsResponse.getCredentials().getAccessKeyId());
            respMap.put("AccessKeySecret", stsResponse.getCredentials().getAccessKeySecret());
            respMap.put("SecurityToken", stsResponse.getCredentials().getSecurityToken());
            respMap.put("Expiration", stsResponse.getCredentials().getExpiration());
            

            String resultString = JSON.toJSONString(respMap);
            logger.error("[truyayong] resultString : " + resultString);
            response(request, response, resultString);

        } catch (ClientException e) {

            Map<String, String> respMap = new LinkedHashMap<String, String>();
            respMap.put("status", e.getErrCode());
            respMap.put("AccessKeyId", "");
            respMap.put("AccessKeySecret", "");
            respMap.put("SecurityToken", "");
            respMap.put("Expiration", "");         
            String resultString = JSON.toJSONString(respMap);
            response(request, response, resultString);
        }
    }
    
    /**
     * 将结果Token返回客户端
     * @param request
     * @param response
     * @param results
     * @throws IOException
     */
    private void response(HttpServletRequest request, HttpServletResponse response, String results) throws IOException {
        String callbackFunName = request.getParameter("callback");
        if (callbackFunName==null || callbackFunName.equalsIgnoreCase("")) {
        	response.getOutputStream().println(results);
            //response.getWriter().println(results);
        } else {
        	response.getOutputStream().println(callbackFunName + "( "+results+" )");
        	//response.getWriter().println(callbackFunName + "( "+results+" )");
        } 
        logger.error("[truyayong] response() response : " + response.toString());
        response.setStatus(HttpServletResponse.SC_OK);
        response.flushBuffer();
    }
    
    /**
     * 向OSS发送鉴权请求
     * @param accessKeyId
     * @param accessKeySecret
     * @param roleArn
     * @param roleSessionName
     * @param policy
     * @param protocolType
     * @param durationSeconds
     * @return
     * @throws ClientException
     */
    protected AssumeRoleResponse assumeRole(String accessKeyId, String accessKeySecret, String roleArn,
            String roleSessionName, String policy, ProtocolType protocolType, long durationSeconds) throws ClientException
    {
        try {
            // 创建一个 Aliyun Acs Client, 用于发起 OpenAPI 请求
            IClientProfile profile = DefaultProfile.getProfile(REGION_CN_HANGZHOU, accessKeyId, accessKeySecret);
            DefaultAcsClient client = new DefaultAcsClient(profile);

            // 创建一个 AssumeRoleRequest 并设置请求参数
            final AssumeRoleRequest request = new AssumeRoleRequest();
            request.setVersion(STS_API_VERSION);
            request.setMethod(MethodType.POST);
            request.setProtocol(protocolType);

            request.setRoleArn(roleArn);
            request.setRoleSessionName(roleSessionName);
            request.setPolicy(policy);
            request.setDurationSeconds(durationSeconds);

            // 发起请求，并得到response
            final AssumeRoleResponse response = client.getAcsResponse(request);

            return response;
        } catch (ClientException e) {
            throw e;
        }
    }
    
    /**
     * 读取本地Json文件
     * @param path
     * @return
     */
    public static String ReadJson(String path){
        BufferedReader reader = null;
        //返回值,使用StringBuffer
        StringBuffer data = new StringBuffer();
        //
        try {
            //从给定位置获取文件
            File file = ResourceUtils.getFile(path);
            reader = new BufferedReader(new FileReader(file));
            //每次读取文件的缓存
            String temp = null;
            while((temp = reader.readLine()) != null){
                data.append(temp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //关闭文件流
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return data.toString();
    }
    
    /**
     * 获取上传文件的url
     * @param bucketName
     * @param object
     * @return
     */
    public String getUrl(String bucketName, String object) {
    	//过期时间为10年
    	Date expires = new Date (new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
    	return getUrl(bucketName, object, expires);
    }
    /**
     * 
     * @param bucketName
     * @param object
     * @param expires
     * @return
     */
    public String getUrl(String bucketName, String object, Date expires) {
    	return ossClient.generatePresignedUrl(bucketName, object, expires).toString();
    }

}
