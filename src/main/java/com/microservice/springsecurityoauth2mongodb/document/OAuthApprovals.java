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

package com.microservice.springsecurityoauth2mongodb.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.oauth2.provider.approval.Approval;

import java.time.LocalDateTime;

/**
 * <h2>OAuthApprovals</h2>
 *
 * @author aek
 * <p>
 * Description: stores approval status, supports for Authorization Code flow
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "oauth_approvals")
public class OAuthApprovals {

    @Id
    private String id;
    private String userId;
    private String clientId;
    private String scope;
    private Approval.ApprovalStatus status;
    private LocalDateTime expiresAt;
    private LocalDateTime lastUpdatedAt;


}
