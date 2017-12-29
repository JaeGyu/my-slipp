package me.jaegyu.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class User {

	@Id
	@GeneratedValue
	@JsonProperty  //getter이 없어도 jsonson에서 자동으로 json으로 변환하는데 참여 시킨다.
	private Long id;

	@Column(nullable = false, length = 20, unique = true)
	@JsonProperty
	private String userId;
	
	@JsonIgnore
	private String password;
	
	@JsonProperty
	private String name;
	
	@JsonProperty
	private String email;

	public boolean matchId(Long newId) {
		if (newId == null) {
			return false;
		}

		return newId.equals(id);
	}

	public boolean matchPassword(String newPassword) {
		if (newPassword == null) {
			return false;
		}

		return newPassword.equals(password);
	}

	public Long getId() {
		return this.id;
	}

//	public String getUserId() {
//		return userId;
//	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

//	public String getPassword() {
//		return password;
//	}

	public void setPassword(String password) {
		this.password = password;
	}

//	public String getName() {
//		return name;
//	}

	public void setName(String name) {
		this.name = name;
	}

//	public String getEmail() {
//		return email;
//	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void update(User newUser) {
		this.password = newUser.password; // ?
		this.name = newUser.name;
		this.email = newUser.email;

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
	}

}
