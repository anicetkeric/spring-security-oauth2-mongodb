/*
 * Copyright (c) 2019. @aek - (anicetkeric@gmail.com)
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.microservice.springsecurityoauth2mongodb.security;

import com.microservice.springsecurityoauth2mongodb.document.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomTokenEnhancer extends JwtAccessTokenConverter {



    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);

        User user = (User) authentication.getPrincipal();

        Map<String, Object> info = new LinkedHashMap<>(accessToken.getAdditionalInformation());

        info.put("id", user.getId());
        info.put("email", user.getEmail());
        info.put("fullname", String.format("%s %s", user.getFirstName(), user.getLastName()));
        info.put("roles", user.getRoles().stream().map(role -> String.format("ROLE_%s", role).toUpperCase())
                .collect(Collectors.toList()));

        customAccessToken.setAdditionalInformation(info);
        return super.enhance(customAccessToken, authentication);
    }



}
