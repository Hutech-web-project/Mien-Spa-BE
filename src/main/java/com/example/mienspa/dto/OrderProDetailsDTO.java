package com.example.mienspa.dto;

public class OrderProDetailsDTO {
	private Integer ordProId;
	private String ordProOrderId;
	private String ordProProductName;
	private int ordProQuantity;
	
	public OrderProDetailsDTO() {
		super();
	}

	public OrderProDetailsDTO(Integer ordProId, String ordProOrderId, String ordProProductName, int ordProQuantity) {
		super();
		this.ordProId = ordProId;
		this.ordProOrderId = ordProOrderId;
		this.ordProProductName = ordProProductName;
		this.ordProQuantity = ordProQuantity;
	}

	public Integer getOrdProId() {
		return ordProId;
	}

	public void setOrdProId(Integer ordProId) {
		this.ordProId = ordProId;
	}

	public String getOrdProOrderId() {
		return ordProOrderId;
	}

	public void setOrdProOrderId(String ordProOrderId) {
		this.ordProOrderId = ordProOrderId;
	}

	public String getOrdProProductName() {
		return ordProProductName;
	}

	public void setOrdProProductName(String ordProProductName) {
		this.ordProProductName = ordProProductName;
	}

	public int getOrdProQuantity() {
		return ordProQuantity;
	}

	public void setOrdProQuantity(int ordProQuantity) {
		this.ordProQuantity = ordProQuantity;
	}
	
}
