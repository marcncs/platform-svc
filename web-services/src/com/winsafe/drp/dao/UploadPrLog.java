package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class UploadPrLog implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private Integer isdeal;

    /** nullable persistent field */
    private String filename;

    /** nullable persistent field */
    private String filepath;

    /** nullable persistent field */
    private Integer makeid;

    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private Integer makedeptid;

    /** nullable persistent field */
    private Date makedate;
    
    private String logFilePath;
    
    private Integer errorCount;
    
    private Integer totalCount;
    //文件MD5值
    private String fileHashCode;

    /** full constructor */
    public UploadPrLog(Integer id, Integer isdeal, String filename, String filepath, Integer makeid, String makeorganid, Integer makedeptid, Date makedate) {
        this.id = id;
        this.isdeal = isdeal;
        this.filename = filename;
        this.filepath = filepath;
        this.makeid = makeid;
        this.makeorganid = makeorganid;
        this.makedeptid = makedeptid;
        this.makedate = makedate;
    }

    /** default constructor */
    public UploadPrLog() {
    }

    /** minimal constructor */
    public UploadPrLog(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogFilePath() {
		return logFilePath;
	}

	public void setLogFilePath(String logFilePath) {
		this.logFilePath = logFilePath;
	}

	public Integer getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(Integer errorCount) {
		this.errorCount = errorCount;
	}

	public Integer getIsdeal() {
        return this.isdeal;
    }

    public void setIsdeal(Integer isdeal) {
        this.isdeal = isdeal;
    }

    public String getFilename() {
        return this.filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilepath() {
        return this.filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public Integer getMakeid() {
        return this.makeid;
    }

    public void setMakeid(Integer makeid) {
        this.makeid = makeid;
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

    public Date getMakedate() {
        return this.makedate;
    }

    public void setMakedate(Date makedate) {
        this.makedate = makedate;
    }

    public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public String getFileHashCode() {
		return fileHashCode;
	}

	public void setFileHashCode(String fileHashCode) {
		this.fileHashCode = fileHashCode;
	}

	public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof UploadPrLog) ) return false;
        UploadPrLog castOther = (UploadPrLog) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}
