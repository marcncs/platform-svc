package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class StockMoveForm extends ActionForm implements Serializable {

    /** identifier field */
    private String id;

    /** nullable persistent field */
    private Date movedate;

    /** nullable persistent field */
    private String outwarehouseid;
    
    private String outwarehouseidname;

    /** nullable persistent field */
    private String inorganid;
    
    private String inorganidname;

    /** nullable persistent field */
    private String inwarehouseid;
    
    private String inwarehouseidname;

    /** nullable persistent field */
    private Integer transportmode;
    
    private String transportmodename;

    /** nullable persistent field */
    private Double totalsum;

    /** nullable persistent field */
    private String movecause;

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

    /** nullable persistent field */
    private String makeorganid;
    
    private String makeorganidname;

    /** nullable persistent field */
    private Integer makedeptid;
    
    private String makedeptidname;

    /** nullable persistent field */
    private Integer makeid;
    
    private String makeidname;

    /** nullable persistent field */
    private Date makedate;

    /** nullable persistent field */
    private Integer isshipment;
    
    private String isshipmentname;

    /** nullable persistent field */
    private Integer shipmentid;
    
    private String shipmentidname;

    /** nullable persistent field */
    private Date shipmentdate;

    /** nullable persistent field */
    private Integer iscomplete;
    
    private String iscompletename;

    /** nullable persistent field */
    private Integer receiveid;
    
    private String receiveidname;

    /** nullable persistent field */
    private Date receivedate;

    /** nullable persistent field */
    private Integer isblankout;
    
    private String isblankoutname;

    /** nullable persistent field */
    private Integer blankoutid;
    
    private String blankoutidname;

    /** nullable persistent field */
    private Date blankoutdate;

    /** nullable persistent field */
    private String blankoutreason;

    /** nullable persistent field */
    private Integer printtimes;

    /** nullable persistent field */
    private Integer takestatus;
    
    private String takestatusname;

    /** full constructor */
    public StockMoveForm(String id, Date movedate, String outwarehouseid, String inorganid, String inwarehouseid, Integer transportmode, Double totalsum, String movecause, String remark, Integer isaudit, Integer auditid, Date auditdate, String makeorganid, Integer makedeptid, Integer makeid, Date makedate, Integer isshipment, Integer shipmentid, Date shipmentdate, Integer iscomplete, Integer receiveid, Date receivedate, Integer isblankout, Integer blankoutid, Date blankoutdate, String blankoutreason, Integer printtimes, Integer takestatus) {
        this.id = id;
        this.movedate = movedate;
        this.outwarehouseid = outwarehouseid;
        this.inorganid = inorganid;
        this.inwarehouseid = inwarehouseid;
        this.transportmode = transportmode;
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
        this.isblankout = isblankout;
        this.blankoutid = blankoutid;
        this.blankoutdate = blankoutdate;
        this.blankoutreason = blankoutreason;
        this.printtimes = printtimes;
        this.takestatus = takestatus;
    }

    /** default constructor */
    public StockMoveForm() {
    }

    /** minimal constructor */
    public StockMoveForm(String id) {
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

    public String getInorganid() {
        return this.inorganid;
    }

    public void setInorganid(String inorganid) {
        this.inorganid = inorganid;
    }

    public String getInwarehouseid() {
        return this.inwarehouseid;
    }

    public void setInwarehouseid(String inwarehouseid) {
        this.inwarehouseid = inwarehouseid;
    }

    public Integer getTransportmode() {
        return this.transportmode;
    }

    public void setTransportmode(Integer transportmode) {
        this.transportmode = transportmode;
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

    public Integer getIsblankout() {
        return this.isblankout;
    }

    public void setIsblankout(Integer isblankout) {
        this.isblankout = isblankout;
    }

    public Integer getBlankoutid() {
        return this.blankoutid;
    }

    public void setBlankoutid(Integer blankoutid) {
        this.blankoutid = blankoutid;
    }

    public Date getBlankoutdate() {
        return this.blankoutdate;
    }

    public void setBlankoutdate(Date blankoutdate) {
        this.blankoutdate = blankoutdate;
    }

    public String getBlankoutreason() {
        return this.blankoutreason;
    }

    public void setBlankoutreason(String blankoutreason) {
        this.blankoutreason = blankoutreason;
    }

    public Integer getPrinttimes() {
        return this.printtimes;
    }

    public void setPrinttimes(Integer printtimes) {
        this.printtimes = printtimes;
    }

    public Integer getTakestatus() {
        return this.takestatus;
    }

    public void setTakestatus(Integer takestatus) {
        this.takestatus = takestatus;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof StockMoveForm) ) return false;
        StockMoveForm castOther = (StockMoveForm) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public String getAuditidname() {
		return auditidname;
	}

	public void setAuditidname(String auditidname) {
		this.auditidname = auditidname;
	}

	public String getBlankoutidname() {
		return blankoutidname;
	}

	public void setBlankoutidname(String blankoutidname) {
		this.blankoutidname = blankoutidname;
	}

	public String getInorganidname() {
		return inorganidname;
	}

	public void setInorganidname(String inorganidname) {
		this.inorganidname = inorganidname;
	}

	public String getInwarehouseidname() {
		return inwarehouseidname;
	}

	public void setInwarehouseidname(String inwarehouseidname) {
		this.inwarehouseidname = inwarehouseidname;
	}

	public String getIsauditname() {
		return isauditname;
	}

	public void setIsauditname(String isauditname) {
		this.isauditname = isauditname;
	}

	public String getIsblankoutname() {
		return isblankoutname;
	}

	public void setIsblankoutname(String isblankoutname) {
		this.isblankoutname = isblankoutname;
	}

	public String getIscompletename() {
		return iscompletename;
	}

	public void setIscompletename(String iscompletename) {
		this.iscompletename = iscompletename;
	}

	public String getIsshipmentname() {
		return isshipmentname;
	}

	public void setIsshipmentname(String isshipmentname) {
		this.isshipmentname = isshipmentname;
	}

	public String getMakedeptidname() {
		return makedeptidname;
	}

	public void setMakedeptidname(String makedeptidname) {
		this.makedeptidname = makedeptidname;
	}

	public String getMakeidname() {
		return makeidname;
	}

	public void setMakeidname(String makeidname) {
		this.makeidname = makeidname;
	}

	public String getOutwarehouseidname() {
		return outwarehouseidname;
	}

	public void setOutwarehouseidname(String outwarehouseidname) {
		this.outwarehouseidname = outwarehouseidname;
	}

	public String getReceiveidname() {
		return receiveidname;
	}

	public void setReceiveidname(String receiveidname) {
		this.receiveidname = receiveidname;
	}

	public String getShipmentidname() {
		return shipmentidname;
	}

	public void setShipmentidname(String shipmentidname) {
		this.shipmentidname = shipmentidname;
	}

	public String getTakestatusname() {
		return takestatusname;
	}

	public void setTakestatusname(String takestatusname) {
		this.takestatusname = takestatusname;
	}

	public String getTransportmodename() {
		return transportmodename;
	}

	public void setTransportmodename(String transportmodename) {
		this.transportmodename = transportmodename;
	}

	public String getMakeorganidname() {
		return makeorganidname;
	}

	public void setMakeorganidname(String makeorganidname) {
		this.makeorganidname = makeorganidname;
	}

}
