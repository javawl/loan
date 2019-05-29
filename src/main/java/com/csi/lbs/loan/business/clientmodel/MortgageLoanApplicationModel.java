package com.csi.lbs.loan.business.clientmodel;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class MortgageLoanApplicationModel {
	
	@NotNull(message="accountnumber is a required field")
//	@NotBlank(message="accountnumber is a required field")
	@Length(min=23, max=23, message="the format of the accountnumber is not correct")
	@ApiModelProperty(notes="A unique number used to identify a mortgage loan account."
	,example="")
	private String accountnumber;
	
	private BigDecimal borringneeds;
	
	@NotNull(message="monthlysalary is a required field")
	@ApiModelProperty(notes="The salary paid by his/her employer company every month."
			,example="10000.00")
	private BigDecimal monthlysalary;
	
	@NotNull(message="loanPeriod is a required field")
	@Pattern(regexp="^[5|10|20|30]$",message="The inputted loan period is not supported. Please set 5, 10, 20, 30.")
	@ApiModelProperty(notes="The period which you want to loan"
	,example="5")
	private String loanPeriod;
	
	private String debitaccountnumber;
	
	private String repaymentCycle;

	public BigDecimal getMonthlysalary() {
		return monthlysalary;
	}

	public void setMonthlysalary(BigDecimal monthlysalary) {
		this.monthlysalary = monthlysalary;
	}

	public String getLoanPeriod() {
		return loanPeriod;
	}

	public void setLoanPeriod(String loanPeriod) {
		this.loanPeriod = loanPeriod;
	}

	public String getAccountnumber() {
		return accountnumber;
	}

	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}

	public BigDecimal getBorringneeds() {
		return borringneeds;
	}

	public void setBorringneeds(BigDecimal borringneeds) {
		this.borringneeds = borringneeds;
	}

	public String getDebitaccountnumber() {
		return debitaccountnumber;
	}

	public void setDebitaccountnumber(String debitaccountnumber) {
		this.debitaccountnumber = debitaccountnumber;
	}

	public String getRepaymentCycle() {
		return repaymentCycle;
	}

	public void setRepaymentCycle(String repaymentCycle) {
		this.repaymentCycle = repaymentCycle;
	}
}
