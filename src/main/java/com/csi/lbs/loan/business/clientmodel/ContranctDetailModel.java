package com.csi.lbs.loan.business.clientmodel;

import java.util.List;

public class ContranctDetailModel {
	
	 	private String accountnumber;

	    private String contractnumber;
	    
	    private List<ReConstractDetailModel> contractPlan;


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

		public List<ReConstractDetailModel> getContractPlan() {
			return contractPlan;
		}

		public void setContractPlan(List<ReConstractDetailModel> contractPlan) {
			this.contractPlan = contractPlan;
		}

}
