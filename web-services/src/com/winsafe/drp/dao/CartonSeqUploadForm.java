package com.winsafe.drp.dao;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

/** 
 * @author yufeng.wang
 * @version 20190218
 */
public class CartonSeqUploadForm extends ActionForm {
	/** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String filename;

    /** nullable persistent field */
    private Integer isdeal;

    /** nullable persistent field */
    private Integer makeid;

    /** nullable persistent field */
    private String makedate;
    
    private FormFile csfile;
    
    private String plNo;
    
	public String getPlNo() {
		return plNo;
	}

	public void setPlNo(String plNo) {
		this.plNo = plNo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Integer getIsdeal() {
		return isdeal;
	}

	public void setIsdeal(Integer isdeal) {
		this.isdeal = isdeal;
	}

	public Integer getMakeid() {
		return makeid;
	}

	public void setMakeid(Integer makeid) {
		this.makeid = makeid;
	}

	public String getMakedate() {
		return makedate;
	}

	public void setMakedate(String makedate) {
		this.makedate = makedate;
	}

	public FormFile getCsfile() {
		return csfile;
	}

	public void setCsfile(FormFile csfile) {
		this.csfile = csfile;
	}
    
}
