package com.csi.lbs.loan.business.entity;

import java.math.BigDecimal;

public class MortgagePeriodRateEntity {
    private String id;

    private Integer repaymentperiod;

    private BigDecimal rate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getRepaymentperiod() {
        return repaymentperiod;
    }

    public void setRepaymentperiod(Integer repaymentperiod) {
        this.repaymentperiod = repaymentperiod;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}