/*
 * Copyright (c) 2019. All right reserved
 * Last Modified 27/06/19 17:39.
 * @aek
 *
 * www.sudcontractors.com
 *
 */

package com.microservice.springsecurityoauth2mongodb.configuration;

import com.microservice.springsecurityoauth2mongodb.common.CORSFilter;
import com.microservice.springsecurityoauth2mongodb.common.exception.CustomAccessDeniedHandler;
import com.microservice.springsecurityoauth2mongodb.common.exception.CustomAuthenticationEntryPoint;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;


@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	private static final String RESOURCE_ID = "resource_id";

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId(RESOURCE_ID).stateless(false);

		// Override exception formatting by injecting the accessDeniedHandler & authenticationEntryPoint
		// custom exception for resource server
		resources.authenticationEntryPoint(new CustomAuthenticationEntryPoint());
		resources.accessDeniedHandler(new CustomAccessDeniedHandler());
	}
	
	@Override 
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests().antMatchers("/api/**").authenticated();
		http.addFilterBefore(new CORSFilter(), ChannelProcessingFilter.class);
	    http.exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());

	}


}