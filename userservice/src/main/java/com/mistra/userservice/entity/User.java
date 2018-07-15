package com.mistra.userservice.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Author: RoronoaZoro丶WangRui
 * Time: 2018/7/15/015
 * Describe:
 */
@Entity
public class User implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false,  unique = true)
	private String username;

	@Column
	private String password;
	public User() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
