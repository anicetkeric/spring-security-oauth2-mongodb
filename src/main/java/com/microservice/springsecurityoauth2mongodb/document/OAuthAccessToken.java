package com.microservice.springsecurityoauth2mongodb.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

/**
 * <h2>OAuthAccessToken</h2>
 *
 * @author aek
 * <p>
 * Description:
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "oauth_access_token")
public class OAuthAccessToken {

	@Id
	private String id;
	private String tokenId;
	private OAuth2AccessToken token;
	private String authenticationId;
	private String username;
	private String clientId;
	private String authentication;
	private String refreshToken;


}
