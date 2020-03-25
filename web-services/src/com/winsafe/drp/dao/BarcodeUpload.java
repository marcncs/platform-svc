package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.winsafe.drp.dao.IdcodeUpload;

public class BarcodeUpload implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String filename;

    /** nullable persistent field */
    private Integer billsort;

    /** nullable persistent field */
    private Integer isdeal;

    /** nullable persistent field */
    private Integer valinum;

    /** nullable persistent field */
    private Integer failnum;
    
    private String filepath;

    /** nullable persistent field */
    private String failfilepath;

    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private Integer makedeptid;

    /** nullable persistent field */
    private Integer makeid;

    /** nullable persistent field */
    private Date makedate;
    
    private String physicalpath;
    private String upusername;
    private Integer isupload;
    private Integer isticket;

    /** full constructor */
    public BarcodeUpload(Integer id, String filename, Integer billsort, Integer isdeal, Integer valinum, Integer failnum, String failfilepath, String makeorganid, Integer makedeptid, Integer makeid, Date makedate) {
        this.id = id;
        this.filename = filename;
        this.billsort = billsort;
        this.isdeal = isdeal;
        this.valinum = valinum;
        this.failnum = failnum;
        this.failfilepath = failfilepath;
        this.makeorganid = makeorganid;
        this.makedeptid = makedeptid;
        this.makeid = makeid;
        this.makedate = makedate;
    }

    /** default constructor */
    public BarcodeUpload() {
    }

    /** minimal constructor */
    public BarcodeUpload(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFilename() {
        return this.filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Integer getBillsort() {
        return this.billsort;
    }

    public void setBillsort(Integer billsort) {
        this.billsort = billsort;
    }

    public Integer getIsdeal() {
        return this.isdeal;
    }

    public void setIsdeal(Integer isdeal) {
        this.isdeal = isdeal;
    }

    public Integer getValinum() {
        return this.valinum;
    }

    public void setValinum(Integer valinum) {
        this.valinum = valinum;
    }

    public Integer getFailnum() {
        return this.failnum;
    }

    public void setFailnum(Integer failnum) {
        this.failnum = failnum;
    }
    
    

    public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getFailfilepath() {
        return this.failfilepath;
    }

    public void setFailfilepath(String failfilepath) {
        this.failfilepath = failfilepath;
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

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof IdcodeUpload) ) return false;
        IdcodeUpload castOther = (IdcodeUpload) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public String getPhysicalpath() {
		return physicalpath;
	}

	public void setPhysicalpath(String physicalpath) {
		this.physicalpath = physicalpath;
	}

	public String getUpusername() {
		return upusername;
	}

	public void setUpusername(String upusername) {
		this.upusername = upusername;
	}

	public Integer getIsupload() {
		return isupload;
	}

	public void setIsupload(Integer isupload) {
		this.isupload = isupload;
	}

	public Integer getIsticket() {
		return isticket;
	}

	public void setIsticket(Integer isticket) {
		this.isticket = isticket;
	}

    
}
