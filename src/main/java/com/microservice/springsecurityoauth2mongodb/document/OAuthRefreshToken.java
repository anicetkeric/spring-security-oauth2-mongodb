package com.microservice.springsecurityoauth2mongodb.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;

/**
 * <h2>OAuthRefreshToken</h2>
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
@Document(collection = "oauth_refresh_token")
public class OAuthRefreshToken {

    @Id
    private String id;
    private String tokenId;
    private OAuth2RefreshToken token;
    private String authentication;

}
