package com.example.tppc.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@Configuration
@EnableOAuth2Client
public class OAuth2Config {

	@Value("${oauth2.clientid}")
	private String oAuth2ClientId;

	@Value("${oauth2.accestoken.uri}")
	private String accessTokenUri;
	
	@Value("${oauth2.scope}")
	private String scope;
	
	@Value("${oauth2.username}")
	private String username;
	
	@Value("${oauth2.password}")
	private String password;
	
	@Value("${oauth2.grantType}")
	private String grantType;

    /**
     * The heart of our interaction with the resource; handles redirection for authentication, access tokens, etc.
     * @param oauth2ClientContext
     * @return
     */
    @Bean
    public OAuth2RestOperations restTemplate(OAuth2ClientContext oauth2ClientContext) {
    	OAuth2RestTemplate oauth2RestTemplate = new OAuth2RestTemplate(resource(), oauth2ClientContext);
        AccessTokenProvider accesTokenProvider = new AccessTokenProviderChain(
        		Arrays.<AccessTokenProvider> asList(
        				new ResourceOwnerPasswordAccessTokenProvider())
        		);
        oauth2RestTemplate.setAccessTokenProvider(accesTokenProvider);
        return oauth2RestTemplate;
    }

    private OAuth2ProtectedResourceDetails resource() {
	    ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
	    resourceDetails.setClientId(oAuth2ClientId);
	    resourceDetails.setAccessTokenUri(accessTokenUri);
	    resourceDetails.setGrantType(grantType);
	    resourceDetails.setScope(Arrays.asList(scope));
	    resourceDetails.setUsername(username);
	    resourceDetails.setPassword(password);

        return resourceDetails;
    }
	
}
