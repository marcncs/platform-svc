package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class MakeConf extends ActionForm implements Serializable {

    /** nullable persistent field */
    private String tablename;

    /** nullable persistent field */
    private Long currentvalue;
    
    /**
     * 0:自动;1:手动
     */
    private Integer runmode;
    
    private String profix;
    
    private Integer extent;
    
    private String chname;
    
    private Integer isdis;
    

    public Integer getExtent() {
		return extent;
	}

	public void setExtent(Integer extent) {
		this.extent = extent;
	}

	public String getProfix() {
		return profix;
	}

	public void setProfix(String profix) {
		this.profix = profix;
	}

	/**
	 * 0:自动;1:手动
	 * @return
	 */
	public Integer getRunmode() {
		return runmode;
	}

	/**
	 * 0:自动;1:手动
	 * @param runmode
	 */
	public void setRunmode(Integer runmode) {
		this.runmode = runmode;
	}

	/** full constructor */
    public MakeConf(String tablename, Long currentvalue) {
        this.tablename = tablename;
        this.currentvalue = currentvalue;
    }

    /** default constructor */
    public MakeConf() {
    }

    public String getTablename() {
        return this.tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public Long getCurrentvalue() {
        return this.currentvalue;
    }

    public void setCurrentvalue(Long currentvalue) {
        this.currentvalue = currentvalue;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getTablename())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof MakeConf) ) return false;
        MakeConf castOther = (MakeConf) other;
        return new EqualsBuilder()
            .append(this.getTablename(), castOther.getTablename())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getTablename())
            .toHashCode();
    }

	public String getChname() {
		return chname;
	}

	public void setChname(String chname) {
		this.chname = chname;
	}

	public Integer getIsdis() {
		return isdis;
	}

	public void setIsdis(Integer isdis) {
		this.isdis = isdis;
	}

}
