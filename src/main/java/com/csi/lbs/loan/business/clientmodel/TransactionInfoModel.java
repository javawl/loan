package com.csi.lbs.loan.business.clientmodel;

import java.util.List;

public class TransactionInfoModel {

	private String accountnumber;

    private String contractnumber;
    
    private List<TransactionDetailModel> transactionDetail;

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

	public List<TransactionDetailModel> getTransactionDetail() {
		return transactionDetail;
	}

	public void setTransactionDetail(List<TransactionDetailModel> transactionDetail) {
		this.transactionDetail = transactionDetail;
	}
}
