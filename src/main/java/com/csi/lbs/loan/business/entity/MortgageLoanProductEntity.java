package com.csi.lbs.loan.business.entity;

import java.math.BigDecimal;

public class MortgageLoanProductEntity {
    private String id;

    private String productcode;

    private String productname;

    private Integer ageeligibility;

    private BigDecimal incomeeligibility;

    private BigDecimal earlyredemptioncharge;

    private BigDecimal maxamount;

    private BigDecimal minamount;

    private String maxperiod;

    private String minperiod;

    private BigDecimal apr;

    private String interestratetype;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode == null ? null : productcode.trim();
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname == null ? null : productname.trim();
    }

    public Integer getAgeeligibility() {
        return ageeligibility;
    }

    public void setAgeeligibility(Integer ageeligibility) {
        this.ageeligibility = ageeligibility;
    }

    public BigDecimal getIncomeeligibility() {
        return incomeeligibility;
    }

    public void setIncomeeligibility(BigDecimal incomeeligibility) {
        this.incomeeligibility = incomeeligibility;
    }

    public BigDecimal getEarlyredemptioncharge() {
        return earlyredemptioncharge;
    }

    public void setEarlyredemptioncharge(BigDecimal earlyredemptioncharge) {
        this.earlyredemptioncharge = earlyredemptioncharge;
    }

    public BigDecimal getMaxamount() {
        return maxamount;
    }

    public void setMaxamount(BigDecimal maxamount) {
        this.maxamount = maxamount;
    }

    public BigDecimal getMinamount() {
        return minamount;
    }

    public void setMinamount(BigDecimal minamount) {
        this.minamount = minamount;
    }

    public String getMaxperiod() {
        return maxperiod;
    }

    public void setMaxperiod(String maxperiod) {
        this.maxperiod = maxperiod == null ? null : maxperiod.trim();
    }

    public String getMinperiod() {
        return minperiod;
    }

    public void setMinperiod(String minperiod) {
        this.minperiod = minperiod == null ? null : minperiod.trim();
    }

    public BigDecimal getApr() {
        return apr;
    }

    public void setApr(BigDecimal apr) {
        this.apr = apr;
    }

    public String getInterestratetype() {
        return interestratetype;
    }

    public void setInterestratetype(String interestratetype) {
        this.interestratetype = interestratetype == null ? null : interestratetype.trim();
    }
}