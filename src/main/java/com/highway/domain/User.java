package com.highway.domain;

import java.time.LocalDate;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User implements UserDetails {
	
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Email(message = "Username needs to be an email")
	@NotBlank(message = "Username is required.")
	@Column(unique = true,length = 225)
	private String username;
	@NotBlank(message = "Please enter your full name.")
	private String fullname;
	@NotBlank(message = "Password filed is required.")
	private String password;
	
	@Transient
	private String confirmpassword;
	private LocalDate createdAt;
	private LocalDate updatedAt;

	public User() {}
	
	
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


	public String getFullname() {
		return fullname;
	}


	public void setFullname(String fullname) {
		this.fullname = fullname;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getConfirmpassword() {
		return confirmpassword;
	}


	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}


	public LocalDate getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}


	public LocalDate getUpdatedAt() {
		return updatedAt;
	}


	public void setUpdatedAt(LocalDate updatedAt) {
		this.updatedAt = updatedAt;
	}


	@PrePersist
	public void onCreate() {
		this.createdAt = LocalDate.now();
	}

	@PreUpdate
	public void onUpdate() {
		this.updatedAt = LocalDate.now();
	}


	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return true;
	}


	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return true;
	}


	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return true;
	}


	@Override
	@JsonIgnore
	public boolean isEnabled() {
		return true;
	}

}
