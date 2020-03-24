package com.winsafe.drp.util.tag;

import java.io.PrintWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.winsafe.common.util.StringUtil;

public class SubStringTag extends TagSupport
{
	private static final long serialVersionUID = 1L;

	private Integer length;
	private String value;

	public int doStartTag()throws JspException{
		if(StringUtil.isEmpty(value)){
			return super.doStartTag();
		}

		PrintWriter tmpPrint=new PrintWriter(pageContext.getOut());
		String newString=StringUtil.substring(value, length);
		if(newString.length()!=value.length()){
			newString+="...";
		}
		tmpPrint.println(newString);
		return super.doStartTag();
	}


	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}

}

