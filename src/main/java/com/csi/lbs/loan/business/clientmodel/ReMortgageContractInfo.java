package com.csi.lbs.loan.business.clientmodel;

import java.math.BigDecimal;

public class ReMortgageContractInfo {
	
	private String accountnumber;
	
	private String contractNumber;
	
	private String installmentDueDate;
	
	private BigDecimal interestamount;
	
	private BigDecimal loanAmount;
	
	private BigDecimal penatlyAmount;
	
	private BigDecimal loanperiod;

	public String getAccountnumber() {
		return accountnumber;
	}

	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public String getInstallmentDueDate() {
		return installmentDueDate;
	}

	public void setInstallmentDueDate(String installmentDueDate) {
		this.installmentDueDate = installmentDueDate;
	}

	public BigDecimal getInterestamount() {
		return interestamount;
	}

	public void setInterestamount(BigDecimal interestamount) {
		this.interestamount = interestamount;
	}

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public BigDecimal getPenatlyAmount() {
		return penatlyAmount;
	}

	public void setPenatlyAmount(BigDecimal penatlyAmount) {
		this.penatlyAmount = penatlyAmount;
	}

	public BigDecimal getLoanperiod() {
		return loanperiod;
	}

	public void setLoanperiod(BigDecimal loanperiod) {
		this.loanperiod = loanperiod;
	}
}
