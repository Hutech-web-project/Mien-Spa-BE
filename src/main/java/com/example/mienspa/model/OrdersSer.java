package com.example.mienspa.model;
// Generated May 19, 2023, 4:12:14 PM by Hibernate Tools 4.3.6.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Ordersser generated by hbm2java
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "ordersser", catalog = "mienspa")
public class OrdersSer implements java.io.Serializable {

	private String orSerId;
	private Users users;
	private String orSerPhoneNo;
	private String orSerStatus;
	private String orSerNote;
	private String orSerStartTime;
	private String orSerEndTime;
	private Integer orSerTotal;
	private Date createdAt;
	private Date updatedAt;
	private Set<OrderSerDetail> orderserdetails = new HashSet<OrderSerDetail>(0);

	public OrdersSer() {
	}

	public OrdersSer(String orSerId, String orSerPhoneNo, String orSerStatus) {
		this.orSerId = orSerId;
		this.orSerPhoneNo = orSerPhoneNo;
		this.orSerStatus = orSerStatus;
	}

	public OrdersSer(String orSerId, Users users, String orSerPhoneNo, String orSerStatus, String orSerNote,
			String orSerStartTime, String orSerEndTime, Integer orSerTotal, Date createdAt, Date updatedAt,
			Set<OrderSerDetail> orderserdetails) {
		this.orSerId = orSerId;
		this.users = users;
		this.orSerPhoneNo = orSerPhoneNo;
		this.orSerStatus = orSerStatus;
		this.orSerNote = orSerNote;
		this.orSerStartTime = orSerStartTime;
		this.orSerEndTime = orSerEndTime;
		this.orSerTotal = orSerTotal;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.orderserdetails = orderserdetails;
	}

	@Id

	@Column(name = "orSer_id", unique = true, nullable = false, length = 128)
	public String getOrSerId() {
		return this.orSerId;
	}

	public void setOrSerId(String orSerId) {
		this.orSerId = orSerId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orSer_UserId")
	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	@Column(name = "orSer_PhoneNo", nullable = false, length = 15)
	public String getOrSerPhoneNo() {
		return this.orSerPhoneNo;
	}

	public void setOrSerPhoneNo(String orSerPhoneNo) {
		this.orSerPhoneNo = orSerPhoneNo;
	}

	@Column(name = "orSer_Status", nullable = false, length = 50)
	public String getOrSerStatus() {
		return this.orSerStatus;
	}

	public void setOrSerStatus(String orSerStatus) {
		this.orSerStatus = orSerStatus;
	}

	@Column(name = "orSer_Note", length = 65535)
	public String getOrSerNote() {
		return this.orSerNote;
	}

	public void setOrSerNote(String orSerNote) {
		this.orSerNote = orSerNote;
	}

	@Column(name = "orSer_StartTime", length = 65535)
	public String getOrSerStartTime() {
		return this.orSerStartTime;
	}

	public void setOrSerStartTime(String orSerStartTime) {
		this.orSerStartTime = orSerStartTime;
	}

	@Column(name = "orSer_EndTime", length = 65535)
	public String getOrSerEndTime() {
		return this.orSerEndTime;
	}

	public void setOrSerEndTime(String orSerEndTime) {
		this.orSerEndTime = orSerEndTime;
	}

	@Column(name = "orSer_Total")
	public Integer getOrSerTotal() {
		return this.orSerTotal;
	}

	public void setOrSerTotal(Integer orSerTotal) {
		this.orSerTotal = orSerTotal;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "ordersser")
	public Set<OrderSerDetail> getOrderserdetails() {
		return this.orderserdetails;
	}

	public void setOrderserdetails(Set<OrderSerDetail> orderserdetails) {
		this.orderserdetails = orderserdetails;
	}

}