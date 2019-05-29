package com.csi.lbs.loan.business.clientmodel;

public class LoanStagingModel {

	private String repaymentCycle;
	
	private double repaymentPerPhase;
	
	private double loanPeriod;
	
	private int totalPhase;
	
	private int totalInterestPayable;
	
	private int totalRepaymentAmount;

	public String getRepaymentCycle() {
		return repaymentCycle;
	}

	public void setRepaymentCycle(String repaymentCycle) {
		this.repaymentCycle = repaymentCycle;
	}

	public double getRepaymentPerPhase() {
		return repaymentPerPhase;
	}

	public void setRepaymentPerPhase(double repaymentPerPhase) {
		this.repaymentPerPhase = repaymentPerPhase;
	}

	public double getLoanPeriod() {
		return loanPeriod;
	}

	public void setLoanPeriod(double loanPeriod) {
		this.loanPeriod = loanPeriod;
	}

	public int getTotalInterestPayable() {
		return totalInterestPayable;
	}

	public void setTotalInterestPayable(int totalInterestPayable) {
		this.totalInterestPayable = totalInterestPayable;
	}

	public int getTotalRepaymentAmount() {
		return totalRepaymentAmount;
	}

	public void setTotalRepaymentAmount(int totalRepaymentAmount) {
		this.totalRepaymentAmount = totalRepaymentAmount;
	}

	public int getTotalPhase() {
		return totalPhase;
	}

	public void setTotalPhase(int totalPhase) {
		this.totalPhase = totalPhase;
	}
}
