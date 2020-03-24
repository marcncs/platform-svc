package com.winsafe.drp.dao;

import java.util.Date;

/** @author Hibernate CodeGenerator */
public class ProductRedeployForm   {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private Integer redeployid;
    
    private String redeployidname;

    /** nullable persistent field */
    private Integer redeploydept;
    
    private String redeploydeptname;

    /** nullable persistent field */
    private String redeploymemo;

    /** nullable persistent field */
    private Integer makeid;
    
    private String makeidname;

    /** nullable persistent field */
    private Date makedate;
    private String makedatename;

    /** nullable persistent field */
    private Integer isaudit;
    
    private String isauditname;

    /** nullable persistent field */
    private Integer auditid;
    
    private String auditidname;

    /** nullable persistent field */
    private Date auditdate;
    
    private String auditdatename;

    

    /** default constructor */
    public ProductRedeployForm() {
    }

    /** minimal constructor */
    public ProductRedeployForm(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRedeployid() {
        return this.redeployid;
    }

    public void setRedeployid(Integer redeployid) {
        this.redeployid = redeployid;
    }

    public Integer getRedeploydept() {
        return this.redeploydept;
    }

    public void setRedeploydept(Integer redeploydept) {
        this.redeploydept = redeploydept;
    }

    public String getRedeploymemo() {
        return this.redeploymemo;
    }

    public void setRedeploymemo(String redeploymemo) {
        this.redeploymemo = redeploymemo;
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

    

	public String getAuditdatename() {
		return auditdatename;
	}

	public void setAuditdatename(String auditdatename) {
		this.auditdatename = auditdatename;
	}

	public String getAuditidname() {
		return auditidname;
	}

	public void setAuditidname(String auditidname) {
		this.auditidname = auditidname;
	}

	public String getMakedatename() {
		return makedatename;
	}

	public void setMakedatename(String makedatename) {
		this.makedatename = makedatename;
	}

	public String getMakeidname() {
		return makeidname;
	}

	public void setMakeidname(String makeidname) {
		this.makeidname = makeidname;
	}

	public String getRedeploydeptname() {
		return redeploydeptname;
	}

	public void setRedeploydeptname(String redeploydeptname) {
		this.redeploydeptname = redeploydeptname;
	}

	public String getRedeployidname() {
		return redeployidname;
	}

	public void setRedeployidname(String redeployidname) {
		this.redeployidname = redeployidname;
	}

	public String getIsauditname() {
		return isauditname;
	}

	public void setIsauditname(String isauditname) {
		this.isauditname = isauditname;
	}

}
