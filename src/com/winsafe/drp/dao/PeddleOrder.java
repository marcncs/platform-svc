package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class PeddleOrder extends ActionForm implements Serializable {

    /** nullable persistent field */
    private String id;

    /** nullable persistent field */
    private String cid;

    /** nullable persistent field */
    private String cname;
    
    private String cmobile;

    /** nullable persistent field */
    private Integer province;

    /** nullable persistent field */
    private Integer city;

    /** nullable persistent field */
    private Integer areas;

    /** nullable persistent field */
    private String receiveman;

    /** nullable persistent field */
    private String receivetel;

    /** nullable persistent field */
    private Integer paymentmode;
    
    private Long warehouseid;

    /** nullable persistent field */
    private Double totalsum;
    
    private Double factsum;

    /** nullable persistent field */
    private Integer invmsg;

    /** nullable persistent field */
    private Integer ismaketicket;

    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private Long makedeptid;

    /** nullable persistent field */
    private Long makeid;

    /** nullable persistent field */
    private Date makedate;
    
    private String equiporganid;

    /** nullable persistent field */
    private Long updateid;

    /** nullable persistent field */
    private Date lastupdate;

    /** nullable persistent field */
    private String remark;

    /** nullable persistent field */
    private Integer isaudit;

    /** nullable persistent field */
    private Long auditid;

    /** nullable persistent field */
    private Date auditdate;

    /** nullable persistent field */
    private Integer isblankout;

    /** nullable persistent field */
    private Long blankoutid;

    /** nullable persistent field */
    private Date blankoutdate;

    /** nullable persistent field */
    private String blankoutreason;

    /** nullable persistent field */
    private Integer printtimes;
    
    private Integer isdaybalance;
    
    private String keyscontent;
    
    private Integer isaccount;

    /** full constructor */
    public PeddleOrder(String id, String cid, String cname, Integer province, Integer city, Integer areas, String receiveman, String receivetel, Integer paymentmode, Double totalsum, Integer invmsg, Integer ismaketicket, String makeorganid, Long makedeptid, Long makeid, Date makedate, Long updateid, Date lastupdate, String remark, Integer isaudit, Long auditid, Date auditdate, Integer isblankout, Long blankoutid, Date blankoutdate, String blankoutreason, Integer printtimes) {
        this.id = id;
        this.cid = cid;
        this.cname = cname;
        this.province = province;
        this.city = city;
        this.areas = areas;
        this.receiveman = receiveman;
        this.receivetel = receivetel;
        this.paymentmode = paymentmode;
        this.totalsum = totalsum;
        this.invmsg = invmsg;
        this.ismaketicket = ismaketicket;
        this.makeorganid = makeorganid;
        this.makedeptid = makedeptid;
        this.makeid = makeid;
        this.makedate = makedate;
        this.updateid = updateid;
        this.lastupdate = lastupdate;
        this.remark = remark;
        this.isaudit = isaudit;
        this.auditid = auditid;
        this.auditdate = auditdate;
        this.isblankout = isblankout;
        this.blankoutid = blankoutid;
        this.blankoutdate = blankoutdate;
        this.blankoutreason = blankoutreason;
        this.printtimes = printtimes;
    }

    /** default constructor */
    public PeddleOrder() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Integer getProvince() {
        return this.province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public Integer getCity() {
        return this.city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public Integer getAreas() {
        return this.areas;
    }

    public void setAreas(Integer areas) {
        this.areas = areas;
    }

    public String getReceiveman() {
        return this.receiveman;
    }

    public void setReceiveman(String receiveman) {
        this.receiveman = receiveman;
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

    public Integer getIsmaketicket() {
        return this.ismaketicket;
    }

    public void setIsmaketicket(Integer ismaketicket) {
        this.ismaketicket = ismaketicket;
    }

    public String getMakeorganid() {
        return this.makeorganid;
    }

    public void setMakeorganid(String makeorganid) {
        this.makeorganid = makeorganid;
    }

    public Long getMakedeptid() {
        return this.makedeptid;
    }

    public void setMakedeptid(Long makedeptid) {
        this.makedeptid = makedeptid;
    }

    public Long getMakeid() {
        return this.makeid;
    }

    public void setMakeid(Long makeid) {
        this.makeid = makeid;
    }

    public Date getMakedate() {
        return this.makedate;
    }

    public void setMakedate(Date makedate) {
        this.makedate = makedate;
    }

    public Long getUpdateid() {
        return this.updateid;
    }

    public void setUpdateid(Long updateid) {
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

    public Long getAuditid() {
        return this.auditid;
    }

    public void setAuditid(Long auditid) {
        this.auditid = auditid;
    }

    public Date getAuditdate() {
        return this.auditdate;
    }

    public void setAuditdate(Date auditdate) {
        this.auditdate = auditdate;
    }

    public Integer getIsblankout() {
        return this.isblankout;
    }

    public void setIsblankout(Integer isblankout) {
        this.isblankout = isblankout;
    }

    public Long getBlankoutid() {
        return this.blankoutid;
    }

    public void setBlankoutid(Long blankoutid) {
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

    public String toString() {
        return new ToStringBuilder(this)
            .toString();
    }

	public Integer getInvmsg() {
		return invmsg;
	}

	public void setInvmsg(Integer invmsg) {
		this.invmsg = invmsg;
	}

	public String getEquiporganid() {
		return equiporganid;
	}

	public void setEquiporganid(String equiporganid) {
		this.equiporganid = equiporganid;
	}

	public String getCmobile() {
		return cmobile;
	}

	public void setCmobile(String cmobile) {
		this.cmobile = cmobile;
	}

	public String getReceivetel() {
		return receivetel;
	}

	public void setReceivetel(String receivetel) {
		this.receivetel = receivetel;
	}

	public Long getWarehouseid() {
		return warehouseid;
	}

	public void setWarehouseid(Long warehouseid) {
		this.warehouseid = warehouseid;
	}

	public Integer getIsdaybalance() {
		return isdaybalance;
	}

	public void setIsdaybalance(Integer isdaybalance) {
		this.isdaybalance = isdaybalance;
	}

	public Double getFactsum() {
		return factsum;
	}

	public void setFactsum(Double factsum) {
		this.factsum = factsum;
	}

	public String getKeyscontent() {
		return keyscontent;
	}

	public void setKeyscontent(String keyscontent) {
		this.keyscontent = keyscontent;
	}

	/**
	 * @return the isaccount
	 */
	public Integer getIsaccount() {
		return isaccount;
	}

	/**
	 * @param isaccount the isaccount to set
	 */
	public void setIsaccount(Integer isaccount) {
		this.isaccount = isaccount;
	}
	
	

}
