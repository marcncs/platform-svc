package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class ProductInterconvert extends ActionForm implements Serializable {

    /** identifier field */
    private String id;

    /** nullable persistent field */
    private Date movedate;

    /** nullable persistent field */
    private String outwarehouseid;

    /** nullable persistent field */
    private String inwarehouseid;

    /** nullable persistent field */
    private Double totalsum;

    /** nullable persistent field */
    private String movecause;

    /** nullable persistent field */
    private String remark;

    /** nullable persistent field */
    private Integer isaudit;

    /** nullable persistent field */
    private Integer auditid;

    /** nullable persistent field */
    private Date auditdate;

    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private Integer makedeptid;

    /** nullable persistent field */
    private Integer makeid;

    /** nullable persistent field */
    private Date makedate;

    /** nullable persistent field */
    private Integer isshipment;

    /** nullable persistent field */
    private Integer shipmentid;

    /** nullable persistent field */
    private Date shipmentdate;

    /** nullable persistent field */
    private Integer iscomplete;

    /** nullable persistent field */
    private Integer receiveid;

    /** nullable persistent field */
    private Date receivedate;
    
    /** nullable persistent field */
    private Integer isblankout;

    /** nullable persistent field */
    private Integer blankoutid;

    /** nullable persistent field */
    private Date blankoutdate;

    /** nullable persistent field */
    private String blankoutreason;
    
    private Integer takestatus;

    /** nullable persistent field */
    private Integer printtimes;
    
    private String keyscontent;

    public String getKeyscontent() {
		return keyscontent;
	}

	public void setKeyscontent(String keyscontent) {
		this.keyscontent = keyscontent;
	}

	/** full constructor */
    public ProductInterconvert(String id, Date movedate, String outwarehouseid, String inwarehouseid, Double totalsum, String movecause, String remark, Integer isaudit, Integer auditid, Date auditdate, String makeorganid, Integer makedeptid, Integer makeid, Date makedate, Integer isshipment, Integer shipmentid, Date shipmentdate, Integer iscomplete, Integer receiveid, Date receivedate, Integer printtimes) {
        this.id = id;
        this.movedate = movedate;
        this.outwarehouseid = outwarehouseid;
        this.inwarehouseid = inwarehouseid;
        this.totalsum = totalsum;
        this.movecause = movecause;
        this.remark = remark;
        this.isaudit = isaudit;
        this.auditid = auditid;
        this.auditdate = auditdate;
        this.makeorganid = makeorganid;
        this.makedeptid = makedeptid;
        this.makeid = makeid;
        this.makedate = makedate;
        this.isshipment = isshipment;
        this.shipmentid = shipmentid;
        this.shipmentdate = shipmentdate;
        this.iscomplete = iscomplete;
        this.receiveid = receiveid;
        this.receivedate = receivedate;
        this.printtimes = printtimes;
    }

    /** default constructor */
    public ProductInterconvert() {
    }

    /** minimal constructor */
    public ProductInterconvert(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getMovedate() {
        return this.movedate;
    }

    public void setMovedate(Date movedate) {
        this.movedate = movedate;
    }

    public String getOutwarehouseid() {
        return this.outwarehouseid;
    }

    public void setOutwarehouseid(String outwarehouseid) {
        this.outwarehouseid = outwarehouseid;
    }

    public String getInwarehouseid() {
        return this.inwarehouseid;
    }

    public void setInwarehouseid(String inwarehouseid) {
        this.inwarehouseid = inwarehouseid;
    }

    public Double getTotalsum() {
        return this.totalsum;
    }

    public void setTotalsum(Double totalsum) {
        this.totalsum = totalsum;
    }

    public String getMovecause() {
        return this.movecause;
    }

    public void setMovecause(String movecause) {
        this.movecause = movecause;
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

    public String getMakeorganid() {
        return this.makeorganid;
    }

    public void setMakeorganid(String makeorganid) {
        this.makeorganid = makeorganid;
    }

    public Integer getMakedeptid() {
        return this.makedeptid;
    }

    public void setMakedeptid(Integer makedeptid) {
        this.makedeptid = makedeptid;
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

    public Integer getIsshipment() {
        return this.isshipment;
    }

    public void setIsshipment(Integer isshipment) {
        this.isshipment = isshipment;
    }

    public Integer getShipmentid() {
        return this.shipmentid;
    }

    public void setShipmentid(Integer shipmentid) {
        this.shipmentid = shipmentid;
    }

    public Date getShipmentdate() {
        return this.shipmentdate;
    }

    public void setShipmentdate(Date shipmentdate) {
        this.shipmentdate = shipmentdate;
    }

    public Integer getIscomplete() {
        return this.iscomplete;
    }

    public void setIscomplete(Integer iscomplete) {
        this.iscomplete = iscomplete;
    }

    public Integer getReceiveid() {
        return this.receiveid;
    }

    public void setReceiveid(Integer receiveid) {
        this.receiveid = receiveid;
    }

    public Date getReceivedate() {
        return this.receivedate;
    }

    public void setReceivedate(Date receivedate) {
        this.receivedate = receivedate;
    }
    
    

    public Integer getTakestatus() {
		return takestatus;
	}

	public void setTakestatus(Integer takestatus) {
		this.takestatus = takestatus;
	}

	public Integer getPrinttimes() {
        return this.printtimes;
    }

    public void setPrinttimes(Integer printtimes) {
        this.printtimes = printtimes;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ProductInterconvert) ) return false;
        ProductInterconvert castOther = (ProductInterconvert) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public Date getBlankoutdate() {
		return blankoutdate;
	}

	public void setBlankoutdate(Date blankoutdate) {
		this.blankoutdate = blankoutdate;
	}

	public Integer getBlankoutid() {
		return blankoutid;
	}

	public void setBlankoutid(Integer blankoutid) {
		this.blankoutid = blankoutid;
	}

	public String getBlankoutreason() {
		return blankoutreason;
	}

	public void setBlankoutreason(String blankoutreason) {
		this.blankoutreason = blankoutreason;
	}

	public Integer getIsblankout() {
		return isblankout;
	}

	public void setIsblankout(Integer isblankout) {
		this.isblankout = isblankout;
	}

}
