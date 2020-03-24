package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class TakeBill implements Serializable {

    /** identifier field */
    private String id;
    
    private Integer bsort;

    /** nullable persistent field */
    private String oid;

    /** nullable persistent field */
    private String oname;

    /** nullable persistent field */
    private String rlinkman;

    /** nullable persistent field */
    private String tel;

    /** nullable persistent field */
    private String inwarehouseid;
    
    private Date senddate;

    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private Integer makedeptid;

    /** nullable persistent field */
    private Integer makeid;

    /** nullable persistent field */
    private Date makedate;

    /** nullable persistent field */
    private String equiporganid;

    /** nullable persistent field */
    private Integer isaudit;

    /** nullable persistent field */
    private Integer auditid;

    /** nullable persistent field */
    private Date auditdate;

    /** nullable persistent field */
    private Integer isblankout;

    /** nullable persistent field */
    private Integer blankoutid;

    /** nullable persistent field */
    private Date blankoutdate;

    /** nullable persistent field */
    private String blankoutreason;
    
    private Integer isread;
    
    private String nccode;
    
    private int tbBsort;//订购类型
    
    private Integer hasDetail;

    public Integer getIsread() {
		return isread;
	}

	public void setIsread(Integer isread) {
		this.isread = isread;
	}

	/** full constructor */
    public TakeBill(String id, String oid, String oname, String rlinkman, String tel, String inwarehouseid, String makeorganid, Integer makedeptid, Integer makeid, Date makedate, String equiporganid, Integer isaudit, Integer auditid, Date auditdate, Integer isblankout, Integer blankoutid, Date blankoutdate, String blankoutreason, String nccode) {
        this.id = id;
        this.oid = oid;
        this.oname = oname;
        this.rlinkman = rlinkman;
        this.tel = tel;
        this.inwarehouseid = inwarehouseid;
        this.makeorganid = makeorganid;
        this.makedeptid = makedeptid;
        this.makeid = makeid;
        this.makedate = makedate;
        this.equiporganid = equiporganid;
        this.isaudit = isaudit;
        this.auditid = auditid;
        this.auditdate = auditdate;
        this.isblankout = isblankout;
        this.blankoutid = blankoutid;
        this.blankoutdate = blankoutdate;
        this.blankoutreason = blankoutreason;
        this.nccode=nccode;
    }

    /** default constructor */
    public TakeBill() {
    }

    /** minimal constructor */
    public TakeBill(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getHasDetail() {
		return hasDetail;
	}

	public void setHasDetail(Integer hasDetail) {
		this.hasDetail = hasDetail;
	}

	public String getOid() {
        return this.oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getOname() {
        return this.oname;
    }

    public void setOname(String oname) {
        this.oname = oname;
    }

    public String getRlinkman() {
        return this.rlinkman;
    }

    public void setRlinkman(String rlinkman) {
        this.rlinkman = rlinkman;
    }

    public String getTel() {
        return this.tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getInwarehouseid() {
        return this.inwarehouseid;
    }

    public void setInwarehouseid(String inwarehouseid) {
        this.inwarehouseid = inwarehouseid;
    }
    
    

    public Date getSenddate() {
		return senddate;
	}

	public void setSenddate(Date senddate) {
		this.senddate = senddate;
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

    public String getEquiporganid() {
        return this.equiporganid;
    }

    public void setEquiporganid(String equiporganid) {
        this.equiporganid = equiporganid;
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
    
    

    public Integer getBsort() {
		return bsort;
	}

	public void setBsort(Integer bsort) {
		this.bsort = bsort;
	}

	public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof TakeBill) ) return false;
        TakeBill castOther = (TakeBill) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public String getNccode() {
		return nccode;
	}

	public void setNccode(String nccode) {
		this.nccode = nccode;
	}

	public int getTbBsort() {
		return tbBsort;
	}

	public void setTbBsort(int tbBsort) {
		this.tbBsort = tbBsort;
	}
    
    

}
