package com.training.mjunction.usersvcs.data.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user", schema = "users")
public class User extends Auditable<String> {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private Long userId;
	@Column(name = "username", nullable = false, unique = true)
	private String username;
	@Column(name = "password", nullable = false)
	private String password;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "latst_name")
	private String latstName;
	@Column(name = "email")
	private String email;
	@Column(name = "phone")
	private String phone;
	@Builder.Default
	@Column(name = "account_non_expired")
	private final boolean accountNonExpired = true;
	@Builder.Default
	@Column(name = "account_non_locked")
	private final boolean accountNonLocked = true;
	@Builder.Default
	@Column(name = "credentials_non_expired")
	private final boolean credentialsNonExpired = true;
	@Builder.Default
	@Column(name = "enabled")
	private final boolean enabled = true;
	@ManyToMany(cascade = CascadeType.ALL)
	@Builder.Default
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private final Set<Role> authorities = new HashSet<>();

}