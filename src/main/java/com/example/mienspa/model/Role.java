package com.example.mienspa.model;
// Generated May 19, 2023, 4:12:14 PM by Hibernate Tools 4.3.6.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Role generated by hbm2java
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "role", catalog = "mienspa")
public class Role implements java.io.Serializable {

	private Integer roId;
	private String roName;
	private String roDisplayName;
	private Date createdAt;
	private Date updatedAt;
	private Set<UserRole> userRoles = new HashSet<UserRole>(0);

	public Role() {
	}

	public Role(String roName) {
		this.roName = roName;
	}

	public Role(String roName, String roDisplayName, Date createdAt, Date updatedAt, Set<UserRole> userRoles) {
		this.roName = roName;
		this.roDisplayName = roDisplayName;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.userRoles = userRoles;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ro_Id", unique = true, nullable = false)
	public Integer getRoId() {
		return this.roId;
	}

	public void setRoId(Integer roId) {
		this.roId = roId;
	}

	@Column(name = "ro_Name", nullable = false, length = 250)
	public String getRoName() {
		return this.roName;
	}

	public void setRoName(String roName) {
		this.roName = roName;
	}

	@Column(name = "ro_DisplayName", length = 250)
	public String getRoDisplayName() {
		return this.roDisplayName;
	}

	public void setRoDisplayName(String roDisplayName) {
		this.roDisplayName = roDisplayName;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at", length = 26)
	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at", length = 26)
	public Date getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
	public Set<UserRole> getUserRoles() {
		return this.userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

}
