package com.dylan.models;

public class Asset {
	private double assetAmount;
	private double assetAppreciation;
	private String assetName;
	private double annualInvestment;
	
	public double getAssetAmount() {
		return assetAmount;
	}
	public void setAssetAmount(double assetAmount) {
		this.assetAmount = assetAmount;
	}
	public double getAssetAppreciation() {
		return assetAppreciation;
	}
	public void setAssetAppreciation(double assetAppreciation) {
		this.assetAppreciation = assetAppreciation;
	}
	public String getAssetName() {
		return assetName;
	}
	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}
	public double getAnnualInvestment() {
		return annualInvestment;
	}
	public void setAnnualInvestment(double annualInvestment) {
		this.annualInvestment = annualInvestment;
	}
}
