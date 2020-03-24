package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class MemberGradeForm extends ActionForm implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private Double integralrate;

    /** nullable persistent field */
    private String gradename;
    
    private Integer policyid;
    
    private String policyidname;

    /** full constructor */
    public MemberGradeForm(Integer id, Double integralrate, String gradename) {
        this.id = id;
        this.integralrate = integralrate;
        this.gradename = gradename;
    }

    /** default constructor */
    public MemberGradeForm() {
    }

    /** minimal constructor */
    public MemberGradeForm(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    

	public Double getIntegralrate() {
		return integralrate;
	}

	public void setIntegralrate(Double integralrate) {
		this.integralrate = integralrate;
	}

	public String getGradename() {
		return gradename;
	}

	public void setGradename(String gradename) {
		this.gradename = gradename;
	}

	public Integer getPolicyid() {
		return policyid;
	}

	public void setPolicyid(Integer policyid) {
		this.policyid = policyid;
	}

	public String getPolicyidname() {
		return policyidname;
	}

	public void setPolicyidname(String policyidname) {
		this.policyidname = policyidname;
	}

}
