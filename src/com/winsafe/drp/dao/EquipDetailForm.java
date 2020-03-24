package com.winsafe.drp.dao;

import java.io.Serializable;

/** @author Hibernate CodeGenerator */
public class EquipDetailForm implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8645727246481961837L;

	/** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String eid;

    /** nullable persistent field */
    private String sbid;

    /** nullable persistent field */
    private Double erasum;
    
    private String erasumname;
    
    /** nullable persistent field */
    private Integer paymentmode;
    
    private String paymentmodename;
    
    private Integer invmsg;
    
    private String invmsgname;

    /** nullable persistent field */
    private Double billsum;

    /** full constructor */
    

    public String getErasumname() {
		return erasumname;
	}

	public void setErasumname(String erasumname) {
		this.erasumname = erasumname;
	}

	/** default constructor */
    public EquipDetailForm() {
    }

    /** minimal constructor */
    public EquipDetailForm(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEid() {
        return this.eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getSbid() {
        return this.sbid;
    }

    public void setSbid(String sbid) {
        this.sbid = sbid;
    }

    public Double getErasum() {
        return this.erasum;
    }

    public void setErasum(Double erasum) {
        this.erasum = erasum;
    }

	public Double getBillsum() {
		return billsum;
	}

	public void setBillsum(Double billsum) {
		this.billsum = billsum;
	}

	public Integer getPaymentmode() {
		return paymentmode;
	}

	public void setPaymentmode(Integer paymentmode) {
		this.paymentmode = paymentmode;
	}

	public String getPaymentmodename() {
		return paymentmodename;
	}

	public void setPaymentmodename(String paymentmodename) {
		this.paymentmodename = paymentmodename;
	}

	public Integer getInvmsg() {
		return invmsg;
	}

	public void setInvmsg(Integer invmsg) {
		this.invmsg = invmsg;
	}

	public String getInvmsgname() {
		return invmsgname;
	}

	public void setInvmsgname(String invmsgname) {
		this.invmsgname = invmsgname;
	}

 

}
