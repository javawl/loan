package com.csi.lbs.loan.business.clientmodel;

import java.util.List;

public class ContractOutstandingModel {

	private String accountnumber;

    private String contractnumber;
    
    private List<ReConstractDetailsModel> outstandingrepayment;

	public String getAccountnumber() {
		return accountnumber;
	}

	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}

	public String getContractnumber() {
		return contractnumber;
	}

	public void setContractnumber(String contractnumber) {
		this.contractnumber = contractnumber;
	}

	public List<ReConstractDetailsModel> getOutstandingrepayment() {
		return outstandingrepayment;
	}

	public void setOutstandingrepayment(List<ReConstractDetailsModel> outstandingrepayment) {
		this.outstandingrepayment = outstandingrepayment;
	}
}
