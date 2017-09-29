package com.example.tppc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.tppc.service.MxSampleService;

@RestController
@RequestMapping("/test")
public class ThirdPartyController {
	
	@Autowired
	OAuth2RestOperations restTemplate;
	
	@Autowired
	MxSampleService mxsampleservice;
	
	@Autowired
	OAuth2ClientContext oAuth2ClientContext;
	
	@RequestMapping(value = "/oauth2", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DefaultOAuth2AccessToken getOauth2Token() {
		restTemplate.getAccessToken();
		return  (DefaultOAuth2AccessToken) restTemplate.getAccessToken();
	}
	
	@RequestMapping(value = "/accestoken", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String getAccesToken() {
		restTemplate.getAccessToken();
		return  restTemplate.getAccessToken().getValue();
	}
	
	@RequestMapping(value = "/auth", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String testAuth() {
		if (oAuth2ClientContext.getAccessToken() == null) {
			return "not authorized";
		}else {
			return "authorized!";
		}
	}
	
	@RequestMapping(value = "/service", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String testService() {
		return mxsampleservice.mxsampleNoAuth();
	}
	
	@RequestMapping(value = "/auth/service", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String testServiceAuth() {
		return mxsampleservice.mxsampleWithAuth();
	}

}
