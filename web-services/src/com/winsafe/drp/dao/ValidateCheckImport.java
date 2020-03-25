package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorForm;

/** @author Hibernate CodeGenerator */
public class ValidateCheckImport extends ValidatorForm implements Serializable {

	 /** identifier field */
    private Long id;

    /** nullable persistent field */
    private String docname;

    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private Long makedeptid;

    /** nullable persistent field */
    private Long makeid;

    /** nullable persistent field */
    private Date makedate;

    /** nullable persistent field */
    private Integer isimport;
    
    private FormFile doc;

    public FormFile getDoc() {
		return doc;
	}

	public void setDoc(FormFile doc) {
		this.doc = doc;
	}

	/** full constructor */
    public ValidateCheckImport(Long id, String docname, Integer isimport) {
        this.id = id;
        this.docname = docname;

        this.isimport = isimport;
    }

    /** default constructor */
    public ValidateCheckImport() {
    }

    /** minimal constructor */
    public ValidateCheckImport(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocname() {
        return this.docname;
    }

    public void setDocname(String docname) {
        this.docname = docname;
    }


    public Integer getIsimport() {
        return this.isimport;
    }

    public void setIsimport(Integer isimport) {
        this.isimport = isimport;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof CheckImport) ) return false;
        CheckImport castOther = (CheckImport) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public Date getMakedate() {
		return makedate;
	}

	public void setMakedate(Date makedate) {
		this.makedate = makedate;
	}

	public Long getMakedeptid() {
		return makedeptid;
	}

	public void setMakedeptid(Long makedeptid) {
		this.makedeptid = makedeptid;
	}

	public Long getMakeid() {
		return makeid;
	}

	public void setMakeid(Long makeid) {
		this.makeid = makeid;
	}

	public String getMakeorganid() {
		return makeorganid;
	}

	public void setMakeorganid(String makeorganid) {
		this.makeorganid = makeorganid;
	}

}

