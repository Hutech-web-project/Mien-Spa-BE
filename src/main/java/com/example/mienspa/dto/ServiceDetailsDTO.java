package com.example.mienspa.dto;

public class ServiceDetailsDTO {
	private String ordSerServiceName;
	private int ordSerServicePrice;
	
	public ServiceDetailsDTO() {
		super();
	}

	public ServiceDetailsDTO(String ordSerServiceName, int ordSerServicePrice) {
		super();
		this.ordSerServiceName = ordSerServiceName;
		this.ordSerServicePrice = ordSerServicePrice;
	}

	public String getOrdSerServiceName() {
		return ordSerServiceName;
	}

	public void setOrdSerServiceName(String ordSerServiceName) {
		this.ordSerServiceName = ordSerServiceName;
	}

	public int getOrdSerServicePrice() {
		return ordSerServicePrice;
	}

	public void setOrdSerServicePrice(int ordSerServicePrice) {
		this.ordSerServicePrice = ordSerServicePrice;
	}
}
