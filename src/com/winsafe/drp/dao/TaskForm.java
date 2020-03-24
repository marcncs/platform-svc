package com.winsafe.drp.dao;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class TaskForm {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String tptitle;

    /** nullable persistent field */
    private Date begindate;

    /** nullable persistent field */
    private Date enddate;

    /** nullable persistent field */
    private Integer status;
    
    private String statusname;

    /** nullable persistent field */
    private String tpcontent;

    /** persistent field */
    private Integer userid;
    
    private String username;

    /** nullable persistent field */
    private Integer isallot;
    
    private String isallotname;
    
    private String executename;
    
    private Date makedate;
    
    private Integer isaffirm;

    public Date getMakedate() {
		return makedate;
	}

	public void setMakedate(Date makedate) {
		this.makedate = makedate;
	}

	public String getExecutename() {
		return executename;
	}

	public void setExecutename(String executename) {
		this.executename = executename;
	}

	public String getIsallotname() {
		return isallotname;
	}

	public void setIsallotname(String isallotname) {
		this.isallotname = isallotname;
	}

	public String getStatusname() {
		return statusname;
	}

	public void setStatusname(String statusname) {
		this.statusname = statusname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTptitle() {
        return this.tptitle;
    }

    public void setTptitle(String tptitle) {
        this.tptitle = tptitle;
    }

    public Date getBegindate() {
        return this.begindate;
    }

    public void setBegindate(Date begindate) {
        this.begindate = begindate;
    }

    public Date getEnddate() {
        return this.enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTpcontent() {
        return this.tpcontent;
    }

    public void setTpcontent(String tpcontent) {
        this.tpcontent = tpcontent;
    }

    public Integer getUserid() {
        return this.userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getIsallot() {
        return this.isallot;
    }

    public void setIsallot(Integer isallot) {
        this.isallot = isallot;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Task) ) return false;
        Task castOther = (Task) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public Integer getIsaffirm() {
		return isaffirm;
	}

	public void setIsaffirm(Integer isaffirm) {
		this.isaffirm = isaffirm;
	}

}
