package com.csi.lbs.loan.business.clientmodel;

import java.math.BigDecimal;

import com.csi.lbs.loan.business.clientmodel.LoanStagingModel;

public class MortgageLoanAmountModel {
	
	private BigDecimal maxLoanAmount;
	
	private LoanStagingModel repaymentByMonth;
	
	private LoanStagingModel repaymentByFortnight;

	public BigDecimal getMaxLoanAmount() {
		return maxLoanAmount;
	}

	public void setMaxLoanAmount(BigDecimal maxLoanAmount) {
		this.maxLoanAmount = maxLoanAmount;
	}

	public LoanStagingModel getRepaymentByMonth() {
		return repaymentByMonth;
	}

	public void setRepaymentByMonth(LoanStagingModel repaymentByMonth) {
		this.repaymentByMonth = repaymentByMonth;
	}

	public LoanStagingModel getRepaymentByFortnight() {
		return repaymentByFortnight;
	}

	public void setRepaymentByFortnight(LoanStagingModel repaymentByFortnight) {
		this.repaymentByFortnight = repaymentByFortnight;
	}
}
