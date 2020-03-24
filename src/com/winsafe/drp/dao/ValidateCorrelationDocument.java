package com.winsafe.drp.dao;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class ValidateCorrelationDocument extends ActionForm{
  /**
	 * 
	 */
	private static final long serialVersionUID = -5870915761089857076L;

private Integer id;
  
  private Integer objsort;

   /** persistent field */
   private String cid;
   
   private String cname;

   /** nullable persistent field */
   private String documentname;

   /** nullable persistent field */
   private String realpathname;

   /** nullable persistent field */
   private String updatedate;

   /** persistent field */
   private Integer makeid;
   
   

   /** full constructor */

   private FormFile doc;
   public ValidateCorrelationDocument(Integer id, String cid, String documentname, String realpathname, String updatedate, Integer userid,FormFile doc) {
       this.id = id;
       this.cid = cid;
       this.documentname = documentname;
       this.realpathname = realpathname;
       this.updatedate = updatedate;
       this.makeid = userid;
       this.doc=doc;
   }

   /** default constructor */
   public ValidateCorrelationDocument() {
   }

   /** minimal constructor */
   public ValidateCorrelationDocument(Integer id, String cid, Integer userid) {
       this.id = id;
       this.cid = cid;
       this.makeid = userid;
   }

   public Integer getObjsort() {
	return objsort;
}

public void setObjsort(Integer objsort) {
	this.objsort = objsort;
}

public String getCname() {
	return cname;
}

public void setCname(String cname) {
	this.cname = cname;
}

public Integer getId() {
       return this.id;
   }

   public void setId(Integer id) {
       this.id = id;
   }

   public String getCid() {
       return this.cid;
   }

   public void setCid(String cid) {
       this.cid = cid;
   }

   public String getDocumentname() {
       return this.documentname;
   }

   public void setDocumentname(String documentname) {
       this.documentname = documentname;
   }

   public String getRealpathname() {
       return this.realpathname;
   }

   public void setRealpathname(String realpathname) {
       this.realpathname = realpathname;
   }

   public String getUpdatedate() {
       return this.updatedate;
   }

   public void setUpdatedate(String updatedate) {
       this.updatedate = updatedate;
   }

   public Integer getMakeid() {
       return this.makeid;
   }

  public FormFile getDoc() {
    return doc;
  }



  public void setMakeid(Integer makeid) {
       this.makeid = makeid;
   }

  public void setDoc(FormFile doc) {
    this.doc = doc;
  }



}
