package com.example.mienspa.dto;

public class OrderSerDetailDTO extends AbstractDTO<OrderSerDetailDTO>{
	private Integer ordSerId;
	private String ordersSerOrderId;
	private String ordSerServiceName;
	private int ordSerServicePrice;
	
	
	public OrderSerDetailDTO() {
	}


	public OrderSerDetailDTO(Integer ordSerId, String ordersSerOrderId, String ordSerServiceName,
			int ordSerServicePrice) {
		super();
		this.ordSerId = ordSerId;
		this.ordersSerOrderId = ordersSerOrderId;
		this.ordSerServiceName = ordSerServiceName;
		this.ordSerServicePrice = ordSerServicePrice;
	}


	public Integer getOrdSerId() {
		return ordSerId;
	}


	public void setOrdSerId(Integer ordSerId) {
		this.ordSerId = ordSerId;
	}


	public String getOrdersSerOrderId() {
		return ordersSerOrderId;
	}


	public void setOrdersSerOrderId(String ordersSerOrderId) {
		this.ordersSerOrderId = ordersSerOrderId;
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
