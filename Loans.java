package com.dylan.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

public class Loans {
	@JsonProperty("Loan")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<Loan> loans = new ArrayList<>();

	public List<Loan> getLoans() {
		return loans;
	}

	public void setLoans(List<Loan> loans) {
		this.loans = loans;
	}
}
