package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class UserGradeForm extends ActionForm implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private Integer userid;
    
    private String useridname;

    /** nullable persistent field */
    private Integer gradeid;
    
    private String gradeidname;


	public String getUseridname() {
		return useridname;
	}

	public void setUseridname(String useridname) {
		this.useridname = useridname;
	}

	/** full constructor */
    public UserGradeForm(Integer id, Integer userid, Integer gradeid) {
        this.id = id;
        this.userid = userid;
        this.gradeid = gradeid;
    }

    /** default constructor */
    public UserGradeForm() {
    }

    /** minimal constructor */
    public UserGradeForm(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return this.userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }


    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof UserGradeForm) ) return false;
        UserGradeForm castOther = (UserGradeForm) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public Integer getGradeid() {
		return gradeid;
	}

	public void setGradeid(Integer gradeid) {
		this.gradeid = gradeid;
	}

	public String getGradeidname() {
		return gradeidname;
	}

	public void setGradeidname(String gradeidname) {
		this.gradeidname = gradeidname;
	}

}
