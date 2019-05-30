package com.csi.lbs.loan.business.clientmodel;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

public class RepaymentModel {
	
	@NotNull(message="accountnumber is a required field")
//	@NotBlank(message="accountnumber is a required field")
	@Length(min=23, max=23, message="the format of the accountnumber is not correct")
	@ApiModelProperty(notes="A unique number used to identify a mortgage loan account."
	,example="HK620001001000005086600")
	private String accountnumber;
	
	@NotNull(message="repaymentamount is a required field")
	@ApiModelProperty(notes="",example="269")
	private BigDecimal repaymentamount;
	
	@NotNull(message="repaymentaccountnumber is a required field")
//	@NotBlank(message="repaymentaccountnumber is a required field")
	@Length(min=23, max=23, message="the format of the accountnumber is not correct")
	@ApiModelProperty(notes="A unique number used to identify a saving/current account."
	,example="HK720001001000000001001")
	private String repaymentaccountnumber;
	
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

	public BigDecimal getRepaymentamount() {
		return repaymentamount;
	}

	public void setRepaymentamount(BigDecimal repaymentamount) {
		this.repaymentamount = repaymentamount;
	}

	public String getRepaymentaccountnumber() {
		return repaymentaccountnumber;
	}

	public void setRepaymentaccountnumber(String repaymentaccountnumber) {
		this.repaymentaccountnumber = repaymentaccountnumber;
	}

	public String getContractnumber() {
		return contractnumber;
	}

	public void setContractnumber(String contractnumber) {
		this.contractnumber = contractnumber;
	}

}
