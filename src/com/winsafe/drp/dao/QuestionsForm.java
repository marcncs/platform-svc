package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : jerry
 * @version : 2009-10-10 下午06:16:29
 * www.winsafe.cn
 */
public class QuestionsForm implements Serializable {
	
    private Integer id;

    private String title;

    private String content;

    private String makeorganid;

    private Integer makedeptid;

    private Integer makeid;

    private Date makedate;
    
    private Integer number;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMakeorganid() {
		return makeorganid;
	}

	public void setMakeorganid(String makeorganid) {
		this.makeorganid = makeorganid;
	}

	public Integer getMakedeptid() {
		return makedeptid;
	}

	public void setMakedeptid(Integer makedeptid) {
		this.makedeptid = makedeptid;
	}

	public Integer getMakeid() {
		return makeid;
	}

	public void setMakeid(Integer makeid) {
		this.makeid = makeid;
	}

	public Date getMakedate() {
		return makedate;
	}

	public void setMakedate(Date makedate) {
		this.makedate = makedate;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
    
    
}
