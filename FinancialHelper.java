package com.dylan.entry.financialhelper;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.xml.xpath.XPathExpressionException;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import com.dylan.models.Asset;
import com.dylan.models.Assets;
import com.dylan.models.Loan;
import com.dylan.models.Loans;
import com.dylan.utility.Utility;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class FinancialHelper {

	private static Logger logger = Logger.getLogger(FinancialHelper.class);

	public static void main(String[] args) throws SAXException, IOException, XPathExpressionException {
		Utility.clearLogFile("./logs/FinancialHelper.log");
		logger.info("Starting Financial Helper application");
		logger.info("------------------------------------------------------------");

		// Obtain amount of months to pay off loans
		readXMLFile();
		logger.info("------------------------------------------------------------");
		logger.info("END OF APPLICATION");
	}

	public static void readXMLFile() throws SAXException, IOException, XPathExpressionException {
		List<Loan> loanList = null;
		List<Asset> assetList = null;

		// Grab loans from XML and calculate
		// how long it will take to pay them off
		logger.info("START OF LOAN PAYOFF");
		logger.info("------------------------------------------------------------");
		loanList = loadLoans("./data/FinancialHelper/Loans.xml");
		calculateLoanPeriod(loanList);
		logger.info("------------------------------------------------------------");
		logger.info("END OF LOAN PAYOFF");
		
		int yearsToPayOffDebt = (loanList.get(loanList.size()-1).getAmountOfMonthsToPayOff()) / 12;
		
		// Grab assets from XML and calculate
		// how much they will be worth when loans get paid off
		logger.info("Start of Asset Appreciation");
		logger.info("------------------------------------------------------------");
		assetList = loadAssets();
		calculateAssetPeriod(yearsToPayOffDebt, assetList);
		logger.info("------------------------------------------------------------");
		logger.info("END OF Asset Appreciation");
	}

	private static List<Asset> loadAssets() throws JsonParseException, JsonMappingException, IOException {
		File file = new File("./data/FinancialHelper/Assets.xml");
		XmlMapper xmlMapper = new XmlMapper();
		Assets assets = xmlMapper.readValue(file, Assets.class);
		
		return assets.getAssets();
	}

	private static void calculateAssetPeriod(int yearsToPayOffDebt, List<Asset> assets) {
		double totalLiquidAssetAmount, totalEquityAssetAmount, salary;
		int initialYearsToPayOffDebt = yearsToPayOffDebt;
		
		totalLiquidAssetAmount = totalEquityAssetAmount = salary = 0.00;
		
		while (yearsToPayOffDebt != 0){
			for (Asset asset : assets){
				asset.setAssetAmount(asset.getAssetAmount() + asset.getAnnualInvestment());
				asset.setAssetAmount(asset.getAssetAmount() * (1+asset.getAssetAppreciation()));
			}
			yearsToPayOffDebt -=1;
		}
		
		for (Asset asset : assets){
			if (asset.getAssetName().equalsIgnoreCase("House") || asset.getAssetName().equalsIgnoreCase("Retirement 401k + IRA")){
				totalEquityAssetAmount += asset.getAssetAmount();
			} else if (!asset.getAssetName().equalsIgnoreCase("Salary")){
				totalLiquidAssetAmount += asset.getAssetAmount();
			} else {
				salary = asset.getAssetAmount();
			}
			logger.info(
					"Asset " + asset.getAssetName() + " amount: $" + Math.round(asset.getAssetAmount() * 100) / 100.0);
		}
		logger.info(
				"Total amount of equity assets in "+initialYearsToPayOffDebt
				+" years: $"+Math.round(totalEquityAssetAmount * 100) / 100.0);
		logger.info(
				"Total amount of liquid assets in "+initialYearsToPayOffDebt
				+" years: $"+Math.round(totalLiquidAssetAmount * 100) / 100.0);
		logger.info(
				"Total minimum gross monthly income in "+initialYearsToPayOffDebt
				+" years: $"+Math.round(salary) / 12);
	}

	private static void calculateLoanPeriod(List<Loan> loans) {
		double loanAmount, minimum, loanInterest, amountOver;
		double yearlyAmountOverPerMonth = 60.00;
		int monthsToPayOff = 0;
		LocalDate localDate = LocalDate.now();
		
		loanAmount = minimum = loanInterest = amountOver = 0.00;
		
		for (Loan loan : loans) {
			loanAmount = loan.getLoanAmount();
			minimum = loan.getMinimum() + amountOver;
			loanInterest = 1 + (loan.getLoanInterest() / 365);
			
			while (loanAmount > 0) {
				loanAmount *= loanInterest;
				
				localDate = localDate.plusDays(1);
				if (localDate.getDayOfMonth() == 20) { //Pay on 20th of the month
					loanAmount -= minimum;
					monthsToPayOff++;
				} else if (localDate == LocalDate.of(localDate.getYear(), 1, 1)) { //Add this amount every year
					amountOver += yearlyAmountOverPerMonth;
				}
			}
			
			loan.setAmountOfMonthsToPayOff(monthsToPayOff);
			logger.info(
					"Loan " + loan.getLoanName() + " months to pay off:" + loan.getAmountOfMonthsToPayOff());
			
			amountOver += minimum;
		}
	}

	public static List<Loan> loadLoans(String loanLocation) throws JsonParseException, JsonMappingException, IOException {
		File file = new File(loanLocation);
		XmlMapper xmlMapper = new XmlMapper();
		Loans loans = xmlMapper.readValue(file, Loans.class);
		double amountPaying = 0.00;
		
		for (Loan loan : loans.getLoans()) {
			amountPaying += loan.getMinimum();
		}
		
		logger.info("Total amount paying: $" + (amountPaying));
		return loans.getLoans();
	}
}
