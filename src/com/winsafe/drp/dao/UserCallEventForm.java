package com.winsafe.drp.dao;

import java.io.Serializable;

/** @author Hibernate CodeGenerator */
public class UserCallEventForm implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -9164228446785630720L;

	/** identifier field */
    private Long id;

    /** nullable persistent field */
    private Long userid;
    
    private String useridname;

   
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserid() {
        return this.userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

	/**
	 * @return the useridname
	 */
	public String getUseridname() {
		return useridname;
	}

	/**
	 * @param useridname the useridname to set
	 */
	public void setUseridname(String useridname) {
		this.useridname = useridname;
	}
    
    

 

}
