package com.microservice.springsecurityoauth2mongodb.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * <h2>OAuthClientDetails</h2>
 *
 * @author aek
 * <p>
 * Description: Stores OAuth2 client details application
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "oauth_client_details")
public class OAuthClientDetails implements ClientDetails {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@NotNull
	private String clientId;

	private Set<String> resourceIds = Collections.emptySet();
	private boolean secretRequired;
	private String clientSecret;
	private boolean scoped;
	private Set<String> scope = Collections.emptySet();
	private Set<String> authorizedGrantTypes = Collections.emptySet();
	private Set<String> registeredRedirectUri;
	private Collection<GrantedAuthority> authorities;
	private Integer accessTokenValiditySeconds;
	private Integer refreshTokenValiditySeconds;
	private boolean autoApprove;
	private Map<String, Object> additionalInformation = new LinkedHashMap<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	@Override
	public Set<String> getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(Set<String> resourceIds) {
		this.resourceIds = resourceIds;
	}

	@Override
	public boolean isSecretRequired() {
		return secretRequired;
	}

	public void setSecretRequired(boolean secretRequired) {
		this.secretRequired = secretRequired;
	}

	@Override
	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	@Override
	public boolean isScoped() {
		return scoped;
	}

	public void setScoped(boolean scoped) {
		this.scoped = scoped;
	}

	@Override
	public Set<String> getScope() {
		return scope;
	}

	public void setScope(Set<String> scope) {
		this.scope = scope;
	}

	@Override
	public Set<String> getAuthorizedGrantTypes() {
		return authorizedGrantTypes;
	}

	public void setAuthorizedGrantTypes(Set<String> authorizedGrantTypes) {
		this.authorizedGrantTypes = authorizedGrantTypes;
	}

	@Override
	public Set<String> getRegisteredRedirectUri() {
		return registeredRedirectUri;
	}

	public void setRegisteredRedirectUri(Set<String> registeredRedirectUri) {
		this.registeredRedirectUri = registeredRedirectUri;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public Integer getAccessTokenValiditySeconds() {
		return accessTokenValiditySeconds;
	}

	public void setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
		this.accessTokenValiditySeconds = accessTokenValiditySeconds;
	}

	@Override
	public Integer getRefreshTokenValiditySeconds() {
		return refreshTokenValiditySeconds;
	}

	public void setRefreshTokenValiditySeconds(Integer refreshTokenValiditySeconds) {
		this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
	}

	public boolean isAutoApprove() {
		return autoApprove;
	}
	
	@Override
	public boolean isAutoApprove(String scope) {
		return autoApprove;
	}

	public void setAutoApprove(boolean autoApprove) {
		this.autoApprove = autoApprove;
	}

	@Override
	public Map<String, Object> getAdditionalInformation() {
		return additionalInformation;
	}

	public void setAdditionalInformation(Map<String, Object> additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

}
