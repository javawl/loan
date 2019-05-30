package com.csi.lbs.loan.business.clientmodel;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class MortgageAccountNumberModel {
	
	@NotNull(message="accountnumber is a required field")
//	@NotBlank(message="accountnumber is a required field")
	@Length(min=23, max=23, message="the format of the accountnumber is not correct")
	@ApiModelProperty(notes="A unique number used to identify a mortgage loan account."
	,example="HK620001001000005086600")
	private String accountnumber;

	public String getAccountnumber() {
		return accountnumber;
	}

	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}

}
