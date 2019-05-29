package com.csi.lbs.loan.business.entity;

import java.util.Date;

public class MortgageLoanAccountEntity {
    private String id;

    private String countrycode;

    private String clearingcode;

    private String branchcode;

    private String sandboxid;
    
    private String dockerid;

    private String customernumber;

    private String accountnumber;

    private String currencycode;

    private String relaccountnumber;

    private String accountstatus;

    private Date reportcanceldate;
    
    private Date lastupdateddate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCountrycode() {
        return countrycode;
    }

    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode == null ? null : countrycode.trim();
    }

    public String getClearingcode() {
        return clearingcode;
    }

    public void setClearingcode(String clearingcode) {
        this.clearingcode = clearingcode == null ? null : clearingcode.trim();
    }

    public String getBranchcode() {
        return branchcode;
    }

    public void setBranchcode(String branchcode) {
        this.branchcode = branchcode == null ? null : branchcode.trim();
    }

    public String getSandboxid() {
        return sandboxid;
    }

    public void setSandboxid(String sandboxid) {
        this.sandboxid = sandboxid == null ? null : sandboxid.trim();
    }

    public String getCustomernumber() {
        return customernumber;
    }

    public void setCustomernumber(String customernumber) {
        this.customernumber = customernumber == null ? null : customernumber.trim();
    }

    public String getAccountnumber() {
        return accountnumber;
    }

    public void setAccountnumber(String accountnumber) {
        this.accountnumber = accountnumber == null ? null : accountnumber.trim();
    }

    public String getCurrencycode() {
        return currencycode;
    }

    public void setCurrencycode(String currencycode) {
        this.currencycode = currencycode == null ? null : currencycode.trim();
    }

    public String getRelaccountnumber() {
        return relaccountnumber;
    }

    public void setRelaccountnumber(String relaccountnumber) {
        this.relaccountnumber = relaccountnumber == null ? null : relaccountnumber.trim();
    }

    public String getAccountstatus() {
        return accountstatus;
    }

    public void setAccountstatus(String accountstatus) {
        this.accountstatus = accountstatus == null ? null : accountstatus.trim();
    }

    public Date getReportcanceldate() {
        return reportcanceldate;
    }

    public void setReportcanceldate(Date reportcanceldate) {
        this.reportcanceldate = reportcanceldate;
    }

	public Date getLastupdateddate() {
		return lastupdateddate;
	}

	public void setLastupdateddate(Date lastupdateddate) {
		this.lastupdateddate = lastupdateddate;
	}

	public String getDockerid() {
		return dockerid;
	}

	public void setDockerid(String dockerid) {
		this.dockerid = dockerid;
	}
}