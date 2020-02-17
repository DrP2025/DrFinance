package com.dylan.models;

public class Loan {
	private double loanAmount;
	private double loanInterest;
	private double minimum;
	private int amountOfMonthsToPayOff;
	private String loanName;

	public Loan() {}
	
	public Loan(double loanAmount, double loanInterest, double minimum, int amountOfMonthsToPayOff, String loanName) {
		this.loanAmount = loanAmount;
		this.loanInterest = loanInterest;
		this.minimum = minimum;
		this.amountOfMonthsToPayOff = amountOfMonthsToPayOff;
		this.loanName = loanName;
	}
	
	/**
	 * @return the loanAmount
	 */
	public double getLoanAmount() {
		return loanAmount;
	}

	/**
	 * @param loanAmount the loanAmount to set
	 */
	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}

	/**
	 * @return the loanInterest
	 */
	public double getLoanInterest() {
		return loanInterest;
	}

	/**
	 * @param loanInterest the loanInterest to set
	 */
	public void setLoanInterest(double loanInterest) {
		this.loanInterest = loanInterest;
	}

	/**
	 * @return the minimum
	 */
	public double getMinimum() {
		return minimum;
	}

	/**
	 * @param minimum the minimum to set
	 */
	public void setMinimum(double minimum) {
		this.minimum = minimum;
	}

	public String getLoanName() {
		return loanName;
	}

	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}

	public int getAmountOfMonthsToPayOff() {
		return amountOfMonthsToPayOff;
	}

	public void setAmountOfMonthsToPayOff(int amountOfMonthsToPayOff) {
		this.amountOfMonthsToPayOff = amountOfMonthsToPayOff;
	}
}
