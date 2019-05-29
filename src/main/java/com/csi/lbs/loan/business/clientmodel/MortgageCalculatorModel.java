package com.csi.lbs.loan.business.clientmodel;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class MortgageCalculatorModel {
	
	@NotNull(message="monthlysalary is a required field")
	@ApiModelProperty(notes="The salary paid by his/her employer company every month."
			,example="10000.00")
	private BigDecimal monthlysalary;
	
	@NotNull(message="loanPeriod is a required field")
	@Pattern(regexp="^[5|10|20|30]$",message="The inputted loan period is not supported. Please set 5, 10, 20, 30.")
	@ApiModelProperty(notes="The period which you want to loan"
	,example="5")
	private String loanPeriod;

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
}
