package com.winsafe.drp.dao;

public class BatchQuantity implements java.io.Serializable {
	private String batch;
	private double maxQuantity;
	private double quantity;
	private int status;

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public double getMaxQuantity() {
		return maxQuantity;
	}

	public void setMaxQuantity(double maxQuantity) {
		this.maxQuantity = maxQuantity;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
