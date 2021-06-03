/*
 * Copyright (c) 2019. All right reserved
 * Last Modified 28/06/19 07:38.
 * @aek
 *
 * www.sudcontractors.com
 *
 */

package com.microservice.springsecurityoauth2mongodb.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * <h2>Permission</h2>
 *
 * @author aek
 *         <p>
 *         Description: permission for application
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Document(collection="permission")
public class Permission implements Serializable {

	@Id
	private String id;

	@NotNull
	@Indexed(unique = true)
	@Size(min = 1, max = 100)
	private String name;

}
