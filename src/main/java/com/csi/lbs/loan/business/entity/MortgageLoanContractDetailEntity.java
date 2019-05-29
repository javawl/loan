package com.csi.lbs.loan.business.entity;

import java.math.BigDecimal;
import java.util.Date;

public class MortgageLoanContractDetailEntity {
    private String id;

    private String accountnumber;

    private String contractnumber;
    
    private String repaymentcycle;

    private String phaseno;

    private BigDecimal loanamountperphase;

    private BigDecimal loaninterestperphase;

    private BigDecimal totalpayment;

    private String currencycode;

    private Date repaymentduedate;

    private String repaymentstatus;

    private BigDecimal penaltyamount;
    
    /*
     * 表外字段
     */

    private String nowDate; 
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAccountnumber() {
        return accountnumber;
    }

    public void setAccountnumber(String accountnumber) {
        this.accountnumber = accountnumber == null ? null : accountnumber.trim();
    }

    public String getContractnumber() {
        return contractnumber;
    }

    public void setContractnumber(String contractnumber) {
        this.contractnumber = contractnumber == null ? null : contractnumber.trim();
    }

    public String getPhaseno() {
        return phaseno;
    }

    public void setPhaseno(String phaseno) {
        this.phaseno = phaseno == null ? null : phaseno.trim();
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
        this.currencycode = currencycode == null ? null : currencycode.trim();
    }

    public Date getRepaymentduedate() {
        return repaymentduedate;
    }

    public void setRepaymentduedate(Date repaymentduedate) {
        this.repaymentduedate = repaymentduedate;
    }

    public String getRepaymentstatus() {
        return repaymentstatus;
    }

    public void setRepaymentstatus(String repaymentstatus) {
        this.repaymentstatus = repaymentstatus == null ? null : repaymentstatus.trim();
    }

    public BigDecimal getPenaltyamount() {
        return penaltyamount;
    }

    public void setPenaltyamount(BigDecimal penaltyamount) {
        this.penaltyamount = penaltyamount;
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

	public String getRepaymentcycle() {
		return repaymentcycle;
	}

	public void setRepaymentcycle(String repaymentcycle) {
		this.repaymentcycle = repaymentcycle;
	}

	public String getNowDate() {
		return nowDate;
	}

	public void setNowDate(String nowDate) {
		this.nowDate = nowDate;
	}
}