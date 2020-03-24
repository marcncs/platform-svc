package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class BaseResource extends ActionForm implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** identifier field */
    private Long id;

    /** nullable persistent field */
    private String tagname;

    /** nullable persistent field */
    private Integer tagsubkey;

    /** nullable persistent field */
    private String tagsubvalue;
    
    private Integer isdefault;
    
    private Integer isuse;

    /** full constructor */
    public BaseResource(Long id, String tagname, Integer tagsubkey, String tagsubvalue) {
        this.id = id;
        this.tagname = tagname;
        this.tagsubkey = tagsubkey;
        this.tagsubvalue = tagsubvalue;
    }

    /** default constructor */
    public BaseResource() {
    }

    /** minimal constructor */
    public BaseResource(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTagname() {
        return this.tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

    public Integer getTagsubkey() {
        return this.tagsubkey;
    }

    public void setTagsubkey(Integer tagsubkey) {
        this.tagsubkey = tagsubkey;
    }

    public String getTagsubvalue() {
        return this.tagsubvalue;
    }

    public void setTagsubvalue(String tagsubvalue) {
        this.tagsubvalue = tagsubvalue;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof BaseResource) ) return false;
        BaseResource castOther = (BaseResource) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public Integer getIsdefault() {
		return isdefault;
	}

	public void setIsdefault(Integer isdefault) {
		this.isdefault = isdefault;
	}

	public Integer getIsuse() {
		return isuse;
	}

	public void setIsuse(Integer isuse) {
		this.isuse = isuse;
	}

    
}
