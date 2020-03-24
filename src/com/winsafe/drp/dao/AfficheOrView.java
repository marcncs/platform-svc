package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class AfficheOrView implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String affichetitle;

    /** nullable persistent field */
    private String affichecontent;

    /** nullable persistent field */
    private Integer affichetype;

    /** nullable persistent field */
    private Integer makeid;

    /** nullable persistent field */
    private Date makedate;
    
    private Integer isbrowse;

    /** default constructor */
    public AfficheOrView() {
    }
   
    public AfficheOrView(Integer id, String affichetitle, String affichecontent,
			Integer affichetype, Integer makeid, Date makedate, Integer isbrowse) {
		super();
		this.id = id;
		this.affichetitle = affichetitle;
		this.affichecontent = affichecontent;
		this.affichetype = affichetype;
		this.makeid = makeid;
		this.makedate = makedate;
		this.isbrowse = isbrowse;
	}

	public Integer getIsbrowse() {
		return isbrowse;
	}

	public void setIsbrowse(Integer isbrowse) {
		this.isbrowse = isbrowse;
	}

	

    /** minimal constructor */
    public AfficheOrView(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAffichetitle() {
        return this.affichetitle;
    }

    public void setAffichetitle(String affichetitle) {
        this.affichetitle = affichetitle;
    }

    public String getAffichecontent() {
        return this.affichecontent;
    }

    public void setAffichecontent(String affichecontent) {
        this.affichecontent = affichecontent;
    }

    public Integer getAffichetype() {
        return this.affichetype;
    }

    public void setAffichetype(Integer affichetype) {
        this.affichetype = affichetype;
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
        if ( !(other instanceof AfficheOrView) ) return false;
        AfficheOrView castOther = (AfficheOrView) other;
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
