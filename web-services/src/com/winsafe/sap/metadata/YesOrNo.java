package com.winsafe.sap.metadata;

/**
 * Project:winsafepdp->Class:YesOrNoEnum.java
 * <p style="font-size:16px;">Description：是否选择器枚举</p>
 * Create Time Dec 2, 2011 1:17:05 PM 
 * @author <a href='fazuo.du@winsafe.com'>dufazuo</a>
 * @version 0.8
 */
public enum YesOrNo
{

	/** 否 */
	NO((int) 0),
	
	/** 是 */
	YES((int) 1);
	
	private int value;
	
	private YesOrNo(int value)
	{
		this.value = value;
	}
	
	public int getValue()
	{
		return value;
	}
}
