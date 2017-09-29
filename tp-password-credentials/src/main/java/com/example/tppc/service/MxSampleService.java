package com.example.tppc.service;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Service;

@Service
public class MxSampleService {
	
	@Autowired
	OAuth2ClientContext oAuth2ClientContext;
	
	@Autowired
	OAuth2RestOperations restTemplate;
	
	@Value("${mxsample.uri}")
	String uri;
	
	@Value("${mxsample.appId}")
	String appId;
	
	@Value("${mxsample.devId}")
	String devId;
	
	@Value("${mxsample.servChannel}")
	String servChannel;
	
	@Value("${mxsample.serCount}")
	String serCount;
	
	@Value("${mxsample.servLang}")
	String servLang;
	
	@Value("${mxsample.servProc}")
	String servProc;
	
	@Value("${mxsample.trId}")
	String trId;

	public String mxsampleNoAuth() {
		if (oAuth2ClientContext.getAccessToken() == null) {
			return "no authorized";
		} else {	
			HttpClient client = HttpClients.custom().build();
			HttpUriRequest request = RequestBuilder.get()
			  .setUri(uri)
			  .setHeader("header.applicationid",appId)
			  .setHeader("header.device_id",devId)
			  .setHeader("header.service_channel",servChannel)
			  .setHeader("header.service_country",serCount)
			  .setHeader("header.service_language",servLang)
			  .setHeader("header.service_procedence",servProc)
			  .setHeader("header.tracking_id",trId)
			  .setHeader("Authorization","Bearer " + oAuth2ClientContext.getAccessToken().getValue())	  
			  .build();
			try {
				HttpResponse response = client.execute(request);
				HttpEntity entity = response.getEntity();
				String responseString = EntityUtils.toString(entity, "UTF-8");
				return responseString;
			} catch (IOException e) {
				e.printStackTrace();
				return "io error";
			}
		}
	}
	
	public String mxsampleWithAuth() {
		HttpClient client = HttpClients.custom().build();
		HttpUriRequest request = RequestBuilder.get()
		  .setUri(uri)
		  .setHeader("header.applicationid",appId)
		  .setHeader("header.device_id",devId)
		  .setHeader("header.service_channel",servChannel)
		  .setHeader("header.service_country",serCount)
		  .setHeader("header.service_language",servLang)
		  .setHeader("header.service_procedence",servProc)
		  .setHeader("header.tracking_id",trId)
		  .setHeader("Authorization","Bearer " + restTemplate.getAccessToken().getValue())	  
		  .build();
		try {
			HttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
			String responseString = EntityUtils.toString(entity, "UTF-8");
			return responseString;
		} catch (IOException e) {
			e.printStackTrace();
			return "io error";
		}
	}
	
}
