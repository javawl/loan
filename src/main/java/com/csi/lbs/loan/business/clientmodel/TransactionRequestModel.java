package com.csi.lbs.loan.business.clientmodel;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

public class TransactionRequestModel {
	
	@NotNull(message="accountnumber is a required field")
	@NotBlank(message="accountnumber is a required field")
    @ApiModelProperty(notes="A unique bank accunt number.",allowEmptyValue=false,required=true,example="HK620001001000005086600")
	private String accountnumber;
	
	@ApiModelProperty(allowEmptyValue=true,required=false,notes="The start date of the transactions."
   			,example="2019-05-01")
	private String transFromDate;
	
   	@ApiModelProperty(allowEmptyValue=true,required=false,notes="The end date of the transactions."
			,example="2019-06-30")
	private String transToDate;
   	
   	@NotNull(message="contractnumber is a required field")
	@NotBlank(message="contractnumber is a required field")
   	@ApiModelProperty(notes="A unique number to identify mortgage loan contract.",allowEmptyValue=false,required=true,example="000000001")
   	private String contractnumber;

	public String getContractnumber() {
		return contractnumber;
	}

	public void setContractnumber(String contractnumber) {
		this.contractnumber = contractnumber;
	}

	public String getTransToDate() {
		return transToDate;
	}

	public void setTransToDate(String transToDate) {
		this.transToDate = transToDate;
	}

	public String getTransFromDate() {
		return transFromDate;
	}

	public void setTransFromDate(String transFromDate) {
		this.transFromDate = transFromDate;
	}

	public String getAccountnumber() {
		return accountnumber;
	}

	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}

}
