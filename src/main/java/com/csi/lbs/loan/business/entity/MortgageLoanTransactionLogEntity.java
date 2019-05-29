package com.csi.lbs.loan.business.entity;

import java.math.BigDecimal;
import java.util.Date;

public class MortgageLoanTransactionLogEntity {
    private String id;

    private String countrycode;

    private String clearingcode;

    private String branchcode;

    private String sandboxid;
    
    private String dockerid;
    
    private String dealnumber;

    private String accountnumber;

    private String contractnumber;

    private String relaccountnumber;

    private String phaseno;

    private String trantype;

    private BigDecimal tranamt;

    private String ccy;

    private Date trantime;
    
    /*
     * 表外字段
     * @return
     * 
     * */
    
    private String transFromDate;
    
    private String transToDate;

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

    public String getRelaccountnumber() {
        return relaccountnumber;
    }

    public void setRelaccountnumber(String relaccountnumber) {
        this.relaccountnumber = relaccountnumber == null ? null : relaccountnumber.trim();
    }

    public String getPhaseno() {
        return phaseno;
    }

    public void setPhaseno(String phaseno) {
        this.phaseno = phaseno == null ? null : phaseno.trim();
    }

    public String getTrantype() {
        return trantype;
    }

    public void setTrantype(String trantype) {
        this.trantype = trantype == null ? null : trantype.trim();
    }

    public BigDecimal getTranamt() {
        return tranamt;
    }

    public void setTranamt(BigDecimal tranamt) {
        this.tranamt = tranamt;
    }

    public String getCcy() {
        return ccy;
    }

    public void setCcy(String ccy) {
        this.ccy = ccy == null ? null : ccy.trim();
    }

    public Date getTrantime() {
        return trantime;
    }

    public void setTrantime(Date trantime) {
        this.trantime = trantime;
    }

	public String getTransFromDate() {
		return transFromDate;
	}

	public void setTransFromDate(String transFromDate) {
		this.transFromDate = transFromDate;
	}

	public String getTransToDate() {
		return transToDate;
	}

	public void setTransToDate(String transToDate) {
		this.transToDate = transToDate;
	}

	public String getDockerid() {
		return dockerid;
	}

	public void setDockerid(String dockerid) {
		this.dockerid = dockerid;
	}

	public String getDealnumber() {
		return dealnumber;
	}

	public void setDealnumber(String dealnumber) {
		this.dealnumber = dealnumber;
	}
}