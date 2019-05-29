package com.csi.lbs.loan.business.clientmodel;

import java.math.BigDecimal;

public class ReConstractDetailsModel {

	private String phaseno;

    private BigDecimal loanamountperphase;

    private BigDecimal loaninterestperphase;

    private BigDecimal totalpayment;

    private String currencycode;

    private String repaymentduedate;

    private String repaymentstatus;
    
    private BigDecimal penaltyamount;

	public String getPhaseno() {
		return phaseno;
	}

	public void setPhaseno(String phaseno) {
		this.phaseno = phaseno;
	}

	public BigDecimal getLoanamountperphase() {
		return loanamountperphase;
	}

	public void setLoanamountperphase(BigDecimal loanamountperphase) {
		this.loanamountperphase = loanamountperphase;
	}

	public BigDecimal getLoaninterestperphase() {
		return loaninterestperphase;
	}

	public void setLoaninterestperphase(BigDecimal loaninterestperphase) {
		this.loaninterestperphase = loaninterestperphase;
	}

	public BigDecimal getTotalpayment() {
		return totalpayment;
	}

	public void setTotalpayment(BigDecimal totalpayment) {
		this.totalpayment = totalpayment;
	}

	public String getCurrencycode() {
		return currencycode;
	}

	public void setCurrencycode(String currencycode) {
		this.currencycode = currencycode;
	}

	public String getRepaymentduedate() {
		return repaymentduedate;
	}

	public void setRepaymentduedate(String repaymentduedate) {
		this.repaymentduedate = repaymentduedate;
	}

	public String getRepaymentstatus() {
		return repaymentstatus;
	}

	public void setRepaymentstatus(String repaymentstatus) {
		this.repaymentstatus = repaymentstatus;
	}

	public BigDecimal getPenaltyamount() {
		return penaltyamount;
	}

	public void setPenaltyamount(BigDecimal penaltyamount) {
		this.penaltyamount = penaltyamount;
	}
}
