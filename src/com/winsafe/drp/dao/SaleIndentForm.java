package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

/** @author Hibernate CodeGenerator */
public class SaleIndentForm implements Serializable {

    /** identifier field */
    private String id;

    /** nullable persistent field */
    private String customerbillid;

    /** nullable persistent field */
    private String cid;

    /** nullable persistent field */
    private String cname;

    /** nullable persistent field */
    private String receiveman;

    /** nullable persistent field */
    private String tel;

    /** nullable persistent field */
    private Integer saledept;
    
    private String saledeptname;

    /** nullable persistent field */
    private Integer saleid;
    
    private String saleidname;

    /** nullable persistent field */
    private Integer paymentmode;
    
    private String paymentmodename;

    /** nullable persistent field */
    private String consignmentdate;
    
    
    private String  consignmentdatename;
    /** nullable persistent field */
    private Double totalsum;
    
    private String totalsumname;

    /** nullable persistent field */
    private Integer transportmode;
    
    private String transportmodename;
    
    

    /** nullable persistent field */
    private Integer transit;

    
    private String transitname;
    /** nullable persistent field */
    private String transportaddr;

    /** nullable persistent field */
    private Integer makeid;
    private String makename;

    /** nullable persistent field */
    private Date makedate;
    private String makedatename;
    /** nullable persistent field */
    private Integer updateid;

    private String updateidname;
    /** nullable persistent field */
    private Date lastupdate;
    
    private String lastupdatename;

    /** nullable persistent field */
    private String remark;

    /** nullable persistent field */
    private Integer isaudit;
    private String isauditname;
    /** nullable persistent field */
    private Integer auditid;
    
    private String auditidname;

    /** nullable persistent field */
    private Date auditdate;
    private String auditdatename;

    /** nullable persistent field */
    private Integer isendcase;
    
    private String isendcasename;

    /** nullable persistent field */
    private Integer endcaseid;
    
    private String endcaseidname;

    /** nullable persistent field */
    private Date endcasedate;
    
    private String endcasedatename;

    /** full constructor */


    /** default constructor */
    public SaleIndentForm() {
    }

    /** minimal constructor */
    public SaleIndentForm(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerbillid() {
        return this.customerbillid;
    }

    public void setCustomerbillid(String customerbillid) {
        this.customerbillid = customerbillid;
    }

    public String getCid() {
        return this.cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCname() {
        return this.cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getReceiveman() {
        return this.receiveman;
    }

    public void setReceiveman(String receiveman) {
        this.receiveman = receiveman;
    }

    public String getTel() {
        return this.tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getSaledept() {
        return this.saledept;
    }

    public void setSaledept(Integer saledept) {
        this.saledept = saledept;
    }

    public Integer getSaleid() {
        return this.saleid;
    }

    public void setSaleid(Integer saleid) {
        this.saleid = saleid;
    }

    public Integer getPaymentmode() {
        return this.paymentmode;
    }

    public void setPaymentmode(Integer paymentmode) {
        this.paymentmode = paymentmode;
    }

    
    public Double getTotalsum() {
        return this.totalsum;
    }

    public void setTotalsum(Double totalsum) {
        this.totalsum = totalsum;
    }

    public Integer getTransportmode() {
        return this.transportmode;
    }

    public void setTransportmode(Integer transportmode) {
        this.transportmode = transportmode;
    }

    public Integer getTransit() {
        return this.transit;
    }

    public void setTransit(Integer transit) {
        this.transit = transit;
    }

    public String getTransportaddr() {
        return this.transportaddr;
    }

    public void setTransportaddr(String transportaddr) {
        this.transportaddr = transportaddr;
    }

    public Integer getMakeid() {
        return this.makeid;
    }

    public void setMakeid(Integer makeid) {
        this.makeid = makeid;
    }

    public Date getMakedate() {
        return this.makedate;
    }

    public void setMakedate(Date makedate) {
        this.makedate = makedate;
    }

    public Integer getUpdateid() {
        return this.updateid;
    }

    public void setUpdateid(Integer updateid) {
        this.updateid = updateid;
    }

    public Date getLastupdate() {
        return this.lastupdate;
    }

    public void setLastupdate(Date lastupdate) {
        this.lastupdate = lastupdate;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIsaudit() {
        return this.isaudit;
    }

    public void setIsaudit(Integer isaudit) {
        this.isaudit = isaudit;
    }

    public Integer getAuditid() {
        return this.auditid;
    }

    public void setAuditid(Integer auditid) {
        this.auditid = auditid;
    }

    public Date getAuditdate() {
        return this.auditdate;
    }

    public void setAuditdate(Date auditdate) {
        this.auditdate = auditdate;
    }

    public Integer getIsendcase() {
        return this.isendcase;
    }

    public void setIsendcase(Integer isendcase) {
        this.isendcase = isendcase;
    }

    public Integer getEndcaseid() {
        return this.endcaseid;
    }

    public void setEndcaseid(Integer endcaseid) {
        this.endcaseid = endcaseid;
    }

    public Date getEndcasedate() {
        return this.endcasedate;
    }

    public void setEndcasedate(Date endcasedate) {
        this.endcasedate = endcasedate;
    }
 

    

	public String getAuditdatename() {
		return auditdatename;
	}

	public void setAuditdatename(String auditdatename) {
		this.auditdatename = auditdatename;
	}

	public String getAuditidname() {
		return auditidname;
	}

	public void setAuditidname(String auditidname) {
		this.auditidname = auditidname;
	}

	public String getConsignmentdatename() {
		return consignmentdatename;
	}

	public void setConsignmentdatename(String consignmentdatename) {
		this.consignmentdatename = consignmentdatename;
	}

	public String getEndcasedatename() {
		return endcasedatename;
	}

	public void setEndcasedatename(String endcasedatename) {
		this.endcasedatename = endcasedatename;
	}

	public String getEndcaseidname() {
		return endcaseidname;
	}

	public void setEndcaseidname(String endcaseidname) {
		this.endcaseidname = endcaseidname;
	}

	public String getIsauditname() {
		return isauditname;
	}

	public void setIsauditname(String isauditname) {
		this.isauditname = isauditname;
	}

	public String getIsendcasename() {
		return isendcasename;
	}

	public void setIsendcasename(String isendcasename) {
		this.isendcasename = isendcasename;
	}

	public String getLastupdatename() {
		return lastupdatename;
	}

	public void setLastupdatename(String lastupdatename) {
		this.lastupdatename = lastupdatename;
	}

	public String getMakedatename() {
		return makedatename;
	}

	public void setMakedatename(String makedatename) {
		this.makedatename = makedatename;
	}

 

	public String getMakename() {
		return makename;
	}

	public void setMakename(String makename) {
		this.makename = makename;
	}

	public String getPaymentmodename() {
		return paymentmodename;
	}

	public void setPaymentmodename(String paymentmodename) {
		this.paymentmodename = paymentmodename;
	}

	public String getSaledeptname() {
		return saledeptname;
	}

	public void setSaledeptname(String saledeptname) {
		this.saledeptname = saledeptname;
	}

	public String getSaleidname() {
		return saleidname;
	}

	public void setSaleidname(String saleidname) {
		this.saleidname = saleidname;
	}

	public String getTotalsumname() {
		return totalsumname;
	}

	public void setTotalsumname(String totalsumname) {
		this.totalsumname = totalsumname;
	}

	public String getTransitname() {
		return transitname;
	}

	public void setTransitname(String transitname) {
		this.transitname = transitname;
	}

	public String getTransportmodename() {
		return transportmodename;
	}

	public void setTransportmodename(String transportmodename) {
		this.transportmodename = transportmodename;
	}

	public String getUpdateidname() {
		return updateidname;
	}

	public void setUpdateidname(String updateidname) {
		this.updateidname = updateidname;
	}

	public String getConsignmentdate() {
		return consignmentdate;
	}

	public void setConsignmentdate(String consignmentdate) {
		this.consignmentdate = consignmentdate;
	}

}
