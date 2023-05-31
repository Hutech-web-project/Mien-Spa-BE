package com.example.mienspa.dto;

public class ServiceDetailsDTO {
	private String ordSerServiceName;
	private int ordSerServicePrice;
	private String ordSerServiceId ;
	
	public ServiceDetailsDTO() {
		super();
	}

	public ServiceDetailsDTO(String ordSerServiceName, int ordSerServicePrice, String ordSerServiceId) {
		super();
		this.ordSerServiceName = ordSerServiceName;
		this.ordSerServicePrice = ordSerServicePrice;
		this.ordSerServiceId = ordSerServiceId;
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

	public String getOrdSerServiceId() {
		return ordSerServiceId;
	}

	public void setOrdSerServiceId(String ordSerServiceId) {
		this.ordSerServiceId = ordSerServiceId;
	}
}
