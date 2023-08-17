package com.user.api;


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// all the annotation coming from Lonbok library
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class User {
	
	@JsonProperty("id")
	private Integer id;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("gender")
	private String gender;
	
	@JsonProperty("status")
	private String status;

	// we generate this construction because we don't want to use the id attribute. We also don't use any annotation to ignore the id 
	public User(String name, String email, String gender, String status) {
		this.name = name;
		this.email = email;
		this.gender = gender;
		this.status = status;
	}
	
	

}
