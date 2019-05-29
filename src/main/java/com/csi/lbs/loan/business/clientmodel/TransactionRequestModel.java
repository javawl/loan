package com.csi.lbs.loan.business.clientmodel;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

public class TransactionRequestModel {
	
	@NotNull(message="accountnumber is a required field")
	@NotBlank(message="accountnumber is a required field")
    @ApiModelProperty(notes="A unique bank accunt number.",allowEmptyValue=false,required=true,example="")
	private String accountnumber;
	
	@ApiModelProperty(allowEmptyValue=true,required=false,notes="The start date of the transactions."
   			,example="2018-02-14")
	private String transFromDate;
	
   	@ApiModelProperty(allowEmptyValue=true,required=false,notes="The end date of the transactions."
			,example="2019-05-26")
	private String transToDate;
   	
   	@NotNull(message="contractnumber is a required field")
	@NotBlank(message="contractnumber is a required field")
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
