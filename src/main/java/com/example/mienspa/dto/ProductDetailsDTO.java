package com.example.mienspa.dto;

public class ProductDetailsDTO {
	private String ProProductName;
	private int ProProductPrice;
	private int ProQuantity;
	
	public ProductDetailsDTO() {
		super();
	}
	
	public ProductDetailsDTO(String proProductName, int proProductPrice, int proQuantity) {
		super();
		ProProductName = proProductName;
		ProProductPrice = proProductPrice;
		ProQuantity = proQuantity;
	}

	public String getProProductName() {
		return ProProductName;
	}

	public void setProProductName(String proProductName) {
		ProProductName = proProductName;
	}

	public int getProProductPrice() {
		return ProProductPrice;
	}

	public void setProProductPrice(int proProductPrice) {
		ProProductPrice = proProductPrice;
	}

	public int getProQuantity() {
		return ProQuantity;
	}

	public void setProQuantity(int proQuantity) {
		ProQuantity = proQuantity;
	}
	
	
}
