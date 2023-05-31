package com.example.mienspa.model;
// Generated May 19, 2023, 4:12:14 PM by Hibernate Tools 4.3.6.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Orderserdetail generated by hbm2java
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "orderserdetail", catalog = "mienspa")
public class OrderSerDetail implements java.io.Serializable {

	private Integer ordSerId;
	private OrdersSer ordersser;
	private String ordSerServiceName;
	private int ordSerServicePrice;

	public OrderSerDetail() {
	}

	public OrderSerDetail(OrdersSer ordersser, String ordSerServiceName, int ordSerServicePrice) {
		this.ordersser = ordersser;
		this.ordSerServiceName = ordSerServiceName;
		this.ordSerServicePrice = ordSerServicePrice;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ordSer_Id", unique = true, nullable = false)
	public Integer getOrdSerId() {
		return this.ordSerId;
	}

	public void setOrdSerId(Integer ordSerId) {
		this.ordSerId = ordSerId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ordSer_OrderId", nullable = false)
	public OrdersSer getOrdersser() {
		return this.ordersser;
	}

	public void setOrdersser(OrdersSer ordersser) {
		this.ordersser = ordersser;
	}

	@Column(name = "ordSer_ServiceName", nullable = false, length = 128)
	public String getOrdSerServiceName() {
		return this.ordSerServiceName;
	}

	public void setOrdSerServiceName(String ordSerServiceName) {
		this.ordSerServiceName = ordSerServiceName;
	}

	@Column(name = "ordSer_ServicePrice", nullable = false)
	public int getOrdSerServicePrice() {
		return this.ordSerServicePrice;
	}

	public void setOrdSerServicePrice(int ordSerServicePrice) {
		this.ordSerServicePrice = ordSerServicePrice;
	}

}
