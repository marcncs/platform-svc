package com.winsafe.sap.metadata;

/**
 * Project:winsafepdp->Class:YesOrNoEnum.java
 * <p style="font-size:16px;">Description：是否选择器枚举</p>
 * Create Time Dec 2, 2011 1:17:05 PM 
 * @author <a href='fazuo.du@winsafe.com'>dufazuo</a>
 * @version 0.8
 */
public enum FileType 
{

	PRODUCT(1),
	PRINT_JOB(2),
	COMMON_CODE(3);
	
	private Integer value;
	
	private FileType(Integer value)
	{
		this.value = value;
	}
	
	public Integer getValue()
	{
		return value;
	}
}
