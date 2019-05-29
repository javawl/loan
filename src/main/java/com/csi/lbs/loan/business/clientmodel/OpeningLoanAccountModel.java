package com.csi.lbs.loan.business.clientmodel;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
//import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class OpeningLoanAccountModel {
	
	@NotNull(message="accountnumber is a required field")
//	@NotBlank(message="accountnumber is a required field")
	@Length(min=23, max=23, message="the format of the accountnumber is not correct")
	@ApiModelProperty(notes="A unique number used to identify a deposit bank account."
	,example="HK720001001000000001001")
    private String accountnumber;
	
	@ApiModelProperty(hidden=true)
	private String countrycode;

	@ApiModelProperty(hidden=true)
    private String clearingcode;

	@ApiModelProperty(hidden=true)
    private String branchcode;
	
	@ApiModelProperty(hidden=true)
    private String sandboxid;
	
	@ApiModelProperty(hidden=true)
    private String dockerid;
	
	
	
	
	public String getCountrycode() {
		return countrycode;
	}

	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}

	public String getClearingcode() {
		return clearingcode;
	}

	public void setClearingcode(String clearingcode) {
		this.clearingcode = clearingcode;
	}

	public String getBranchcode() {
		return branchcode;
	}

	public void setBranchcode(String branchcode) {
		this.branchcode = branchcode;
	}

	public String getAccountnumber() {
		return accountnumber;
	}

	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}

	public String getSandboxid() {
		return sandboxid;
	}

	public void setSandboxid(String sandboxid) {
		this.sandboxid = sandboxid;
	}

	public String getDockerid() {
		return dockerid;
	}

	public void setDockerid(String dockerid) {
		this.dockerid = dockerid;
	}

   

}
