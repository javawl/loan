package com.csi.lbs.loan.business.clientmodel;

import java.util.List;

public class MortgageAccountInfoModel {

	    private String accountnumber;

	    private String currencycode;

	    private String repaymentaccountnumber;

	    private String accountstatus;
	    
	    private List<ContractNumberModel> associatedContract;
	    
	    private ReCustomerModel customerInfo;

		public String getAccountnumber() {
			return accountnumber;
		}

		public void setAccountnumber(String accountnumber) {
			this.accountnumber = accountnumber;
		}

		public String getCurrencycode() {
			return currencycode;
		}

		public void setCurrencycode(String currencycode) {
			this.currencycode = currencycode;
		}

		public String getRepaymentaccountnumber() {
			return repaymentaccountnumber;
		}

		public void setRepaymentaccountnumber(String repaymentaccountnumber) {
			this.repaymentaccountnumber = repaymentaccountnumber;
		}

		public String getAccountstatus() {
			return accountstatus;
		}

		public void setAccountstatus(String accountstatus) {
			this.accountstatus = accountstatus;
		}

		public ReCustomerModel getCustomerInfo() {
			return customerInfo;
		}

		public void setCustomerInfo(ReCustomerModel customerInfo) {
			this.customerInfo = customerInfo;
		}

		public List<ContractNumberModel> getAssociatedContract() {
			return associatedContract;
		}

		public void setAssociatedContract(List<ContractNumberModel> associatedContract) {
			this.associatedContract = associatedContract;
		}
}
