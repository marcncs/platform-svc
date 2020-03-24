package com.winsafe.drp.dao;

import org.apache.struts.action.ActionForm;

public class ValidateContactLog extends ActionForm {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5043489816087413369L;

	private Long id;

   /** persistent field */
   private String cid;

   /** persistent field */
   private String contactdate;

      /** nullable persistent field */
   private String contactcontent;

   private String memo;
   
   private String relateactivity;

   /** nullable persistent field */
   private Long userid;

   /** full constructor */
   public ValidateContactLog(Long id, String cid, String contactdate,  String contactcontent,String relateactivity, String memo,  Long userid) {
       this.id = id;
       this.cid = cid;
       this.contactdate = contactdate;
       this.contactcontent = contactcontent;
       this.memo=memo;
       this.userid = userid;
   }

   /** default constructor */
   public ValidateContactLog() {
   }

   /** minimal constructor */
   public ValidateContactLog(Long id, String cid, String contactdate) {
       this.id = id;
       this.cid = cid;
       this.contactdate = contactdate;

   }

   public Long getId() {
       return this.id;
   }

   public void setId(Long id) {
       this.id = id;
   }

   public String getCid() {
       return this.cid;
   }

   public void setCid(String cid) {
       this.cid = cid;
   }

   public String getContactdate() {
       return this.contactdate;
   }

   public void setContactdate(String contactdate) {
       this.contactdate = contactdate;
   }

   public String getContactcontent() {
       return this.contactcontent;
   }

   public void setContactcontent(String contactcontent) {
       this.contactcontent = contactcontent;
   }



   public Long getUserid() {
       return this.userid;
   }

   public void setUserid(Long userid) {
       this.userid = userid;
   }

public String getMemo() {
	return memo;
}

public void setMemo(String memo) {
	this.memo = memo;
}

public String getRelateactivity() {
	return relateactivity;
}

public void setRelateactivity(String relateactivity) {
	this.relateactivity = relateactivity;
}

}
