package com.csi.lbs.loan.business.clientmodel;

import java.math.BigDecimal;

public class RepaymentModel {
	
	private String accountnumber;
	
	private BigDecimal repaymentamount;
	
	private String repaymentaccountnumber;
	
	private String contractnumber;

	public String getAccountnumber() {
		return accountnumber;
	}

	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}

	public BigDecimal getRepaymentamount() {
		return repaymentamount;
	}

	public void setRepaymentamount(BigDecimal repaymentamount) {
		this.repaymentamount = repaymentamount;
	}

	public String getRepaymentaccountnumber() {
		return repaymentaccountnumber;
	}

	public void setRepaymentaccountnumber(String repaymentaccountnumber) {
		this.repaymentaccountnumber = repaymentaccountnumber;
	}

	public String getContractnumber() {
		return contractnumber;
	}

	public void setContractnumber(String contractnumber) {
		this.contractnumber = contractnumber;
	}

}
