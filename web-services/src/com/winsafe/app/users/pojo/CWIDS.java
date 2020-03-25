package com.winsafe.app.users.pojo;

import java.util.ArrayList;
import java.util.List; 

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="ArrayOfString")
@XmlAccessorType(XmlAccessType.NONE)
public class CWIDS {
	@XmlElement(name="string")
	private List<String> cwidList = new ArrayList<String>();

	public List<String> getCwidList() {
		return cwidList;
	}

	public void setCwidList(List<String> cwidList) {
		this.cwidList = cwidList;
	}
	
}
