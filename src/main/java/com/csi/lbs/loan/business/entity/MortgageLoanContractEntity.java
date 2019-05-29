package com.csi.lbs.loan.business.entity;

import java.math.BigDecimal;
import java.util.Date;

public class MortgageLoanContractEntity {
    private String id;

    private String accountnumber;

    private String contractnumber;

    private BigDecimal loanamount;

    private BigDecimal loanmonthlyrate;

    private BigDecimal interestamount;

    private String currencycode;

    private BigDecimal repaymentperiod;

    private Integer totalphase;

    private Date contracttime;

    private String repaymentaccountnumber;

    private String repaymentcycle;

    private String contractstatus;

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

    public BigDecimal getLoanamount() {
        return loanamount;
    }

    public void setLoanamount(BigDecimal loanamount) {
        this.loanamount = loanamount;
    }

    public BigDecimal getLoanmonthlyrate() {
        return loanmonthlyrate;
    }

    public void setLoanmonthlyrate(BigDecimal loanmonthlyrate) {
        this.loanmonthlyrate = loanmonthlyrate;
    }

    public BigDecimal getInterestamount() {
        return interestamount;
    }

    public void setInterestamount(BigDecimal interestamount) {
        this.interestamount = interestamount;
    }

    public String getCurrencycode() {
        return currencycode;
    }

    public void setCurrencycode(String currencycode) {
        this.currencycode = currencycode == null ? null : currencycode.trim();
    }

    public BigDecimal getRepaymentperiod() {
        return repaymentperiod;
    }

    public void setRepaymentperiod(BigDecimal repaymentperiod) {
        this.repaymentperiod = repaymentperiod;
    }

    public Integer getTotalphase() {
        return totalphase;
    }

    public void setTotalphase(Integer totalphase) {
        this.totalphase = totalphase;
    }

    public Date getContracttime() {
        return contracttime;
    }

    public void setContracttime(Date contracttime) {
        this.contracttime = contracttime;
    }

    public String getRepaymentaccountnumber() {
        return repaymentaccountnumber;
    }

    public void setRepaymentaccountnumber(String repaymentaccountnumber) {
        this.repaymentaccountnumber = repaymentaccountnumber == null ? null : repaymentaccountnumber.trim();
    }

    public String getRepaymentcycle() {
        return repaymentcycle;
    }

    public void setRepaymentcycle(String repaymentcycle) {
        this.repaymentcycle = repaymentcycle == null ? null : repaymentcycle.trim();
    }

    public String getContractstatus() {
        return contractstatus;
    }

    public void setContractstatus(String contractstatus) {
        this.contractstatus = contractstatus == null ? null : contractstatus.trim();
    }
}