package com.winsafe.hbm.util;
/*
 * Created on 2005-10-9
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.hbm.util.RequestTool;

public class BeanCopy {

	public static void copy(Object owner, HttpServletRequest request) throws Exception{
		Class ownerClass = owner.getClass();
		Field[] fields = ownerClass.getDeclaredFields();
		try{
			for ( int i=0; i<fields.length; i++ ){
				Field f = fields[i];
				f.setAccessible(true);
				String typeName = f.getType().getName();
				if ( "java.lang.Integer".equals(typeName) || "int".equals(typeName) ){
					f.set(owner, RequestTool.getInt(request, f.getName()));
				}else if ( "java.lang.Long".equals(typeName) || "long".equals(typeName) ){
					f.set(owner, RequestTool.getLong(request, f.getName()));
				}else if ( "java.lang.Double".equals(typeName) || "double".equals(typeName) ){
					f.set(owner, RequestTool.getDouble(request, f.getName()));
				}else if ( "java.lang.String".equals(typeName) ){
					f.set(owner, RequestTool.getString(request, f.getName()));
				}else if ( "java.util.Date".equals(typeName) ){
					f.set(owner, RequestTool.getDate(request, f.getName()));
				}
			}
		}catch (Exception e ){
			throw new Exception("BeanCopy copy value error:"+e.toString());
		}
	}
}
