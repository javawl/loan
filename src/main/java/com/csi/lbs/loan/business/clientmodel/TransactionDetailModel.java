package com.csi.lbs.loan.business.clientmodel;

import java.math.BigDecimal;

public class TransactionDetailModel {
	
	private String dealnumber;

    private String relaccountnumber;

    private String phaseno;

    private String trantype;

    private BigDecimal tranamt;

    private String ccy;

    private String trantime;

	public String getDealnumber() {
		return dealnumber;
	}

	public void setDealnumber(String dealnumber) {
		this.dealnumber = dealnumber;
	}

	public String getRelaccountnumber() {
		return relaccountnumber;
	}

	public void setRelaccountnumber(String relaccountnumber) {
		this.relaccountnumber = relaccountnumber;
	}

	public String getPhaseno() {
		return phaseno;
	}

	public void setPhaseno(String phaseno) {
		this.phaseno = phaseno;
	}

	public String getTrantype() {
		return trantype;
	}

	public void setTrantype(String trantype) {
		this.trantype = trantype;
	}

	public BigDecimal getTranamt() {
		return tranamt;
	}

	public void setTranamt(BigDecimal tranamt) {
		this.tranamt = tranamt;
	}

	public String getCcy() {
		return ccy;
	}

	public void setCcy(String ccy) {
		this.ccy = ccy;
	}

	public String getTrantime() {
		return trantime;
	}

	public void setTrantime(String trantime) {
		this.trantime = trantime;
	}
	
}
