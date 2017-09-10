package com.dazui.childhood.alioss.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.dazui.childhood.alioss.service.AliOssTokenService;
import com.dazui.childhood.user.controller.UserController;

@RestController
@RequestMapping("")
public class AliOssController {
	
	private Logger logger = LoggerFactory.getLogger(AliOssController.class);
	@Autowired
	private AliOssTokenService tokenAervice;

	@GetMapping("/getStsToken")
	public void getStsToken(HttpServletRequest request, HttpServletResponse response) {
		//TODO
		logger.info("[truyayong] register request : " + request.toString());
		try {
			tokenAervice.handleStsRequest(request, response);
		} catch (Exception e) {
			logger.error("getStsToken exception : ", e);
		}
	}
	//,HttpServletRequest request, HttpServletResponse response
	@PostMapping("/callback")
	public Object getCallBack(@RequestBody JSONObject requestBody) {
		logger.info("[truyayong] callback filename : " + requestBody.toString());
		String bucket = requestBody.getString("bucket");
		String object = requestBody.getString("object");
		JSONObject responseBody = new JSONObject();
		String fileUrl = tokenAervice.getUrl(bucket, object);
		responseBody.put("fileUrl", fileUrl);
		return responseBody;
		
	}
}
