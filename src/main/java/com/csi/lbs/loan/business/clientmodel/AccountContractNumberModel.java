package com.csi.lbs.loan.business.clientmodel;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class AccountContractNumberModel {

		@NotNull(message="accountnumber is a required field")
	//	@NotBlank(message="accountnumber is a required field")
		@Length(min=23, max=23, message="the format of the accountnumber is not correct")
		@ApiModelProperty(notes="A unique number used to identify a mortgage loan account."
		,example="HK620001001000005086600")
		private String accountnumber;
		
		@NotNull(message="contractnumber is a required field")
		@NotBlank(message="contractnumber is a required field")
		@ApiModelProperty(notes="A unique number used to identify a mortgage loan contract."
		,example="000000001")
		private String contractnumber;

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
}
