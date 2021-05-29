package com.microservice.springsecurityoauth2mongodb.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import javax.validation.constraints.NotNull;

/**
 * <h2>OAuthAccessToken</h2>
 *
 * @author aek
 * <p>
 * Description: stores OAuth2 tokens for retrieval by client applications
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "oauth_client_token")
public class OAuthClientToken {

    @Id
    private String id;
    @NotNull
    private String tokenId;
    private OAuth2AccessToken token;
    private String authenticationId;
    private String username;
    private String clientId;

}
