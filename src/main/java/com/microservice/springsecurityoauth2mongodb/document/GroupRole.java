/*
 * Copyright (c) 2019. All right reserved
 * Last Modified 28/06/19 07:40.
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
import java.util.Set;

/**
 * <h2>GroupRole</h2>
 *
 * @author aek
 *         <p>
 *         Description: group role for application
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Document(collection="group_role")
public class GroupRole implements Serializable {

	@Id
	private String id;

	@NotNull
	@Size(min = 1, max = 255)
	private String name;

	@NotNull
	@Indexed(unique = true)
	@Size(min = 1, max = 50)
	private String code;

	private String description;

	/**
	 * Contain list of permission NAME
	 */
	private Set<String> permissions;

}
