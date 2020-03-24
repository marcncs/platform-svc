package com.winsafe.hbm.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.dao.AppProductStruct;
import com.winsafe.drp.dao.ProductStruct;
import com.winsafe.drp.dao.UsersBean; 
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.keyretailer.dao.AppSUserArea;
import com.winsafe.drp.metadata.DealerType;
import com.winsafe.drp.metadata.OrganType; 
import com.winsafe.drp.metadata.PlantType;
import com.winsafe.drp.metadata.UserType;
import com.winsafe.hbm.util.pager.PaginationFacility;
import com.winsafe.hbm.util.pager.SimplePageInfo;


public class DbUtil {

  public static int getRecordCount(int pagesize, String str, String tbName) throws
      Exception {
    int[] uscount;
    String hql = "select count(*) from " + tbName + " " + str;
    uscount = EntityManager.getPagesAndRecordcount(hql, pagesize);
    if (uscount != null) {
      return uscount[0];
    }
    else {
      return 0;
    }
  }
  
  public static int getRecordCountBySQL(String str, String tbName) throws
      Exception {
	  ResultSet rs;
	  int c;
    String hql = "select count(*) from " + tbName + " " + str;
	  rs = EntityManager.query(hql);
	  if(rs.getRow()>0){
		 c=rs.getInt(1) ;
	  }else{
		  c=0;
	  }
	  return c;
  }
  


  public static int getExtraRecordCount(int pagesize, String str, String tbName,String fieldName) throws
      Exception {
    int uscount;
    String hql = "select count(distinct("+fieldName+")) from " + tbName + " " + str;
    uscount = EntityManager.getRecordCountQuery(hql);
      return uscount;
  }
  public static String getTimeCondition2(Map map, Map tmpMap, String field) {
	    StringBuffer buf = new StringBuffer();
	    if (map.containsKey("BeginDate")) {
	      String timeField = (String) tmpMap.get("BeginDate");
	      if (timeField != null && !timeField.equals("")) {

	        buf.append(field + ">=to_date('" + timeField + "','yyyy-MM-dd hh24:mi:ss')");
	        buf.append(" and ");
	      }
	    }
	    if (map.containsKey("EndDate")) {
	      String timeField = (String) tmpMap.get("EndDate");
	      if (timeField != null && !timeField.equals("")) {

	        buf.append(field + "<=to_date('" + timeField + " 23:59:59','yyyy-MM-dd hh24:mi:ss')");
	        buf.append(" and ");
	      }
	    }
	    if (map.containsKey("MakeDate")) {
		      String timeField = (String) tmpMap.get("MakeDate");
		      if (timeField != null && !timeField.equals("")) {

		        buf.append("trunc("+field+") = to_date('" + timeField +"','yyyy-MM-dd')");
		        buf.append(" and ");
		      }
		    }
	    return buf.toString().toLowerCase();
	}

  public static String getTimeCondition(Map map, Map tmpMap, String field) {
    StringBuffer buf = new StringBuffer();
    if (map.containsKey("BeginDate")) {
      String timeField = (String) tmpMap.get("BeginDate");
      if (timeField != null && !timeField.equals("")) {

        buf.append(field + ">=to_date('" + timeField + "','yyyy-MM-dd hh24:mi:ss')");
        buf.append(" and ");
      }
    }
    if (map.containsKey("EndDate")) {
      String timeField = (String) tmpMap.get("EndDate");
      if (timeField != null && !timeField.equals("")) {

        buf.append(field + "<=to_date('" + timeField + " 23:59:59','yyyy-MM-dd hh24:mi:ss')");
        buf.append(" and ");
      }
    }
    return buf.toString().toLowerCase();
  }
  
  public static String getTimeConditionForHql(Map map, Map tmpMap, String field, Map<String,Object> param) {
	    StringBuffer buf = new StringBuffer();
	    if (map.containsKey("BeginDate")) {
	      String timeField = (String) tmpMap.get("BeginDate");
	      if (timeField != null && !timeField.equals("")) {

	        buf.append(field + ">=to_date(:" + field.trim().toLowerCase() + ",'yyyy-MM-dd hh24:mi:ss')");
	        param.put(field.trim().toLowerCase(), timeField);
	        buf.append(" and ");
	      }
	    }
	    if (map.containsKey("EndDate")) {
	      String timeField = (String) tmpMap.get("EndDate");
	      if (timeField != null && !timeField.equals("")) {

	        buf.append(field + "<=to_date(:" + field.trim().toLowerCase()+"to,'yyyy-MM-dd hh24:mi:ss')");
	        param.put(field.trim().toLowerCase()+"to", timeField+" 23:59:59");
	        buf.append(" and ");
	      }
	    }
	    return buf.toString().toLowerCase();
	  }
  
  public static String getTimeConditionForSql(Map map, Map tmpMap, String field, Map<String,Object> param) {
	    StringBuffer buf = new StringBuffer();
	    if (map.containsKey("BeginDate")) {
	      String timeField = (String) tmpMap.get("BeginDate");
	      if (timeField != null && !timeField.equals("")) {

	        buf.append(field + ">=to_date(?,'yyyy-MM-dd hh24:mi:ss')");
	        param.put(field.trim().toLowerCase(), timeField);
	        buf.append(" and ");
	      }
	    }
	    if (map.containsKey("EndDate")) {
	      String timeField = (String) tmpMap.get("EndDate");
	      if (timeField != null && !timeField.equals("")) {

	        buf.append(field + "<=to_date(?,'yyyy-MM-dd hh24:mi:ss')");
	        param.put(field.trim().toLowerCase()+"to", timeField+" 23:59:59");
	        buf.append(" and ");
	      }
	    }
	    return buf.toString().toLowerCase();
	  }
  
  public static String getTimeConditionforCreateModify(Map map, Map tmpMap, String field) {
	    StringBuffer buf = new StringBuffer();
	    String timeField =null;
	    String timeField1=null;
	    boolean flag=false;
	    boolean flag1=false;
	    if (map.containsKey("BeginDate")) {
		       timeField = (String) tmpMap.get("BeginDate");
		       if(timeField != null && !timeField.equals("")){
		    	   flag=true;
		       }
		  }
	    if (map.containsKey("EndDate")) {
		       timeField1 = (String) tmpMap.get("EndDate");
		       if(timeField1 != null && !timeField1.equals("")){
		    	   flag1=true;
		       }
		  }
	    if(flag&&flag1){
	    	 buf.append(field + ">=to_date('" + timeField + "','yyyy-MM-dd hh24:mi:ss') and "+field + "<=to_date('" + timeField + " 23:59:59','yyyy-MM-dd hh24:mi:ss') or ");
	    }else if(flag==true&&flag1==false){
	        buf.append(field + ">=to_date('" + timeField + "','yyyy-MM-dd hh24:mi:ss') or ");
	    }else if(flag==false&&flag1==true){
	        buf.append(field + "<=to_date('" + timeField + " 23:59:59','yyyy-MM-dd hh24:mi:ss') or ");
	    }
	   
	    return buf.toString().toLowerCase();
	  }
  public static String getTimeCondition3(Map map, Map tmpMap, String field) {
	    StringBuffer buf = new StringBuffer();
	    if(map.containsKey(field)){
	    	String timeField = (String) map.get(field);
	    	buf.append(field + " = to_date('"+timeField+" 00:00:00','yyyy-MM-dd hh24:mi:ss')");
	    	 buf.append(" and ");
	    }
	    return buf.toString().toLowerCase();
	  }
  
  public static String getTimeCondition4(Map map, Map tmpMap, String field) {
	    StringBuffer buf = new StringBuffer();
	    if (map.containsKey("BeginDate")) {
	      String timeField = (String) tmpMap.get("BeginDate");
	      if (timeField != null && !timeField.equals("")) {

	        buf.append(field + ">=to_date('" + timeField + "','yyyy-MM-dd hh24:mi:ss')");
	        buf.append(" and ");
	      }
	    }
	    if (map.containsKey("EndDate")) {
	      String timeField = (String) tmpMap.get("EndDate");
	      if (timeField != null && !timeField.equals("")) {

	        buf.append(field + "<=to_date('" + timeField + " 23:59:59','yyyy-MM-dd hh24:mi:ss')");
	        buf.append(" and ");
	      }
	    }
	    return buf.toString();
	  }
  
  	public static String getTimeCondition4ForHql(Map map, Map tmpMap, String field, Map<String,Object> param) {
	    StringBuffer buf = new StringBuffer();
	    if (map.containsKey("BeginDate")) {
	      String timeField = (String) tmpMap.get("BeginDate");
	      if (timeField != null && !timeField.equals("")) {

	        buf.append(field + ">=to_date(:"+field.trim()+"from,'yyyy-MM-dd hh24:mi:ss')");
	        param.put(field.trim()+"from", timeField);
	        buf.append(" and ");
	      }	
	    }
	    if (map.containsKey("EndDate")) {
	      String timeField = (String) tmpMap.get("EndDate");
	      if (timeField != null && !timeField.equals("")) {

	        buf.append(field + "<=to_date(:"+field.trim()+"to,'yyyy-MM-dd hh24:mi:ss')");
	        param.put(field.trim()+"to", timeField+ " 23:59:59");
	        buf.append(" and ");
	      }
	    }
	    return buf.toString();
	  }
  
  public static String getTimeConditionhql(Map map, Map tmpMap, String field) {
	    StringBuffer buf = new StringBuffer();
	    if (map.containsKey("BeginDate")) {
	      String timeField = (String) tmpMap.get("BeginDate");
	      if (timeField != null && !timeField.equals("")) {

	        buf.append(field + ">='" + timeField + "'");
	        buf.append(" and ");
	      }
	    }
	    if (map.containsKey("EndDate")) {
	      String timeField = (String) tmpMap.get("EndDate");
	      if (timeField != null && !timeField.equals("")) {

	        buf.append(field + "<'" + timeField + " 23:59:59'");
	        buf.append(" and ");
	      }
	    }
	    return buf.toString().toLowerCase();
	  }
  
  
  public static String getTimeConditionHQL(Map map, Map tmpMap, String field) {
	    StringBuffer buf = new StringBuffer();
	    if (map.containsKey("BeginDate")) {
	      String timeField = (String) tmpMap.get("BeginDate");
	      if (timeField != null && !timeField.equals("")) {

	        buf.append(field + ">=toDate('" + timeField + "','yyyy-MM-dd hh24:mi:ss')");
	        buf.append(" and ");
	      }
	    }
	    if (map.containsKey("EndDate")) {
	      String timeField = (String) tmpMap.get("EndDate");
	      if (timeField != null && !timeField.equals("")) {

	        buf.append(field + "<=toDate('" + timeField + " 23:59:59','yyyy-MM-dd hh24:mi:ss')");
	        buf.append(" and ");
	      }
	    }
	    return buf.toString().toLowerCase();
	  }
  
    public static String getTimeCondition(String beginDate, String endDate, String field) {
	    StringBuffer buf = new StringBuffer();
	    if (beginDate != null && !beginDate.equals("")) {
	        buf.append(field + ">=to_date('" + beginDate + "','yyyy-MM-dd hh24:mi:ss')");
	        buf.append(" and ");
	    }
	    if (endDate != null && !endDate.equals("")) {
	        buf.append(field + "<=to_date('" + endDate + " 23:59:59','yyyy-MM-dd hh24:mi:ss')");
	        buf.append(" and ");
	    }
	    return buf.toString();
	}
  
  public static String getPriceCondition(Map map, Map tmpMap, String field) {
    StringBuffer buf = new StringBuffer();
    if (map.containsKey("BeginPrice")) {
      String timeField = (String) tmpMap.get("BeginPrice");
      if (timeField != null && !timeField.equals("")) {

        buf.append(field + ">=" + timeField + " ");
        buf.append(" and ");
      }
    }
    if (map.containsKey("EndPrice")) {
      String timeField = (String) tmpMap.get("EndPrice");
      if (timeField != null && !timeField.equals("")) {

        buf.append(field + "<=" + timeField + " ");
        buf.append(" and ");
      }
    }
    return buf.toString().toLowerCase();
  }
  
  public static String getOrBlur(Map map, Map tmpMap, String... fields) {
	    StringBuffer buf = new StringBuffer();
	    String sql = "";
	    if (map.containsKey("KeyWord")) {
	      String KeyWord = (String) tmpMap.get("KeyWord");	 
	      KeyWord = KeyWord.trim();
	      if (KeyWord != null && !KeyWord.equals("")) {	    	
	    	  buf.append("( ");
	    	  for ( int i=0; i<fields.length; i ++ ){
	    		  if(fields[i].toLowerCase().contains("mobile")) {
	    			  buf.append(fields[i].toLowerCase() + " like '%" + Encrypt.getSecret(KeyWord, 3) + "%' or ");	
	    		  } else {
	    			  buf.append(fields[i].toLowerCase() + " like '%" + KeyWord + "%' or ");	
	    		  }
	    		  buf.append(fields[i].toLowerCase() + " like '%" + KeyWord + "%' or ");	 
	    	  }	
	    	  sql = buf.substring(0, buf.lastIndexOf("or"));
	    	  sql = sql +" ) and ";  	 
	      }
	    }
	    
	   
	    return sql;
	  }
  
  public static String getOrBlurForHql(Map map, Map tmpMap, Map<String,Object> param, String... fields) {
	    StringBuffer buf = new StringBuffer();
	    String sql = "";
	    if (map.containsKey("KeyWord")) {
	      String KeyWord = (String) tmpMap.get("KeyWord");	 
	      KeyWord = KeyWord.trim();
	      if (KeyWord != null && !KeyWord.equals("")) {	    	
	    	  buf.append("( ");
	    	  for ( int i=0; i<fields.length; i ++ ){	  
	    		  String paramName = fields[i].toLowerCase().replaceAll("\\.", "");
//	    		  buf.append(fields[i].toLowerCase() + " like '%" + KeyWord + "%' or ");	
	    		  buf.append(fields[i].toLowerCase() + " like :" + paramName + " or ");	
	    		  param.put(paramName, "%"+KeyWord+"%");
	    	  }	
	    	  sql = buf.substring(0, buf.lastIndexOf("or"));
	    	  sql = sql +" ) and ";  	 
	      }
	    }
	    
	   
	    return sql;
	  }
  
  public static String getOrBlurForSql(Map map, Map tmpMap, Map<String,Object> param, String... fields) {
	    StringBuffer buf = new StringBuffer();
	    String sql = "";
	    if (map.containsKey("KeyWord")) {
	      String KeyWord = (String) tmpMap.get("KeyWord");	 
	      KeyWord = KeyWord.trim();
	      if (KeyWord != null && !KeyWord.equals("")) {	    	
	    	  buf.append("( ");
	    	  for ( int i=0; i<fields.length; i ++ ){	    	 
	    		  buf.append(fields[i].toLowerCase() + " like ? or ");	
	    		  if(fields[i].toLowerCase().contains("mobile")) {
	    			  param.put(fields[i].toLowerCase(), "%"+Encrypt.getSecret(KeyWord, 3)+"%");
	    		  } else {
	    			  param.put(fields[i].toLowerCase(), "%"+KeyWord+"%");
	    		  }
	    	  }	
	    	  sql = buf.substring(0, buf.lastIndexOf("or"));
	    	  sql = sql +" ) and ";  	 
	      }
	    }
	    
	   
	    return sql;
	  }
  
  public static String getOrBlur2(Map map, Map tmpMap, String... fields) {
	    StringBuffer buf = new StringBuffer();
	    String sql = "";
	    if (map.containsKey("KeyWord")) {
	      String KeyWord = (String) tmpMap.get("KeyWord");	 
	      KeyWord = KeyWord.trim();
	      if (KeyWord != null && !KeyWord.equals("")) {	    	
	    	  buf.append("( ");
	    	  for ( int i=0; i<fields.length; i ++ ){	    	 
	    		  buf.append(fields[i] + " like '%" + KeyWord + "%' or ");	 
	    	  }	
	    	  sql = buf.substring(0, buf.lastIndexOf("or"));
	    	  sql = sql +" ) and ";  	 
	      }
	    }
	    
	   
	    return sql;
	  }
  
  public static String getOrBlur2ForSql(Map map, Map tmpMap, Map<String,Object> param, String... fields) {
	    StringBuffer buf = new StringBuffer();
	    String sql = "";
	    if (map.containsKey("KeyWord")) {
	      String KeyWord = (String) tmpMap.get("KeyWord");	 
	      KeyWord = KeyWord.trim();
	      if (KeyWord != null && !KeyWord.equals("")) {	    	
	    	  buf.append("( ");
	    	  for ( int i=0; i<fields.length; i ++ ){	    	 
	    		  buf.append(fields[i] + " like ? or ");	
	    		  param.put(fields[i], "%"+KeyWord+"%");
	    	  }	
	    	  sql = buf.substring(0, buf.lastIndexOf("or"));
	    	  sql = sql +" ) and ";  	 
	      }
	    }
	    
	   
	    return sql;
	  }
  
  public static String getOrBlur2ForHql(Map map, Map tmpMap, Map<String,Object> param, String... fields) {
	    StringBuffer buf = new StringBuffer();
	    String sql = "";
	    if (map.containsKey("KeyWord")) {
	      String KeyWord = (String) tmpMap.get("KeyWord");	 
	      KeyWord = KeyWord.trim();
	      if (KeyWord != null && !KeyWord.equals("")) {	    	
	    	  buf.append("( ");
	    	  for ( int i=0; i<fields.length; i ++ ){	    	 
	    		  buf.append(fields[i] + " like :"+fields[i]+" or ");	 
	    		  param.put(fields[i], "%"+KeyWord+"%");
	    	  }	
	    	  sql = buf.substring(0, buf.lastIndexOf("or"));
	    	  sql = sql +" ) and ";  	 
	      }
	    }
	    
	   
	    return sql;
	  }

  public static String getBlur(Map map, Map tmpMap, String field) {
    StringBuffer buf = new StringBuffer();
    if (map.containsKey("KeyWord")) {
      String KeyWord = (String) tmpMap.get("KeyWord");
      KeyWord = KeyWord.trim();
      if (KeyWord != null && !KeyWord.equals("")) {
        buf.append("");
        buf.append(field.toLowerCase() + " like '%" + KeyWord + "%'");
        buf.append(" and ");
      }
    }
    return buf.toString();
  }
  
  public static String getBlurForSql(Map map, Map tmpMap, String field, Map<String, Object> param) {
	    StringBuffer buf = new StringBuffer();
	    if (map.containsKey("KeyWord")) {
	      String KeyWord = (String) tmpMap.get("KeyWord");
	      KeyWord = KeyWord.trim();
	      if (KeyWord != null && !KeyWord.equals("")) {
	        buf.append("");
	        buf.append(field.toLowerCase() + " like ? ");
	        param.put(field.toLowerCase(), "%" + KeyWord + "%");
	        buf.append(" and ");
	      }
	    }
	    return buf.toString();
	  }
  
  public static String getBlurForHql(Map map, Map tmpMap, String field, Map<String, Object> param) {
	    StringBuffer buf = new StringBuffer();
	    if (map.containsKey("KeyWord")) {
	      String KeyWord = (String) tmpMap.get("KeyWord");
	      KeyWord = KeyWord.trim();
	      if (KeyWord != null && !KeyWord.equals("")) {
	        buf.append("");
	        buf.append(field.toLowerCase() + " like :"+field.toLowerCase().trim()+" ");
	        param.put(field.toLowerCase().trim(), "%" + KeyWord + "%");
	        buf.append(" and ");
	      }
	    }
	    return buf.toString();
	  }
  
  public static String getBlurOr(Map map, Map tmpMap, String field) {
	    StringBuffer buf = new StringBuffer();
	    if (map.containsKey("KeyWord")) {
	      String KeyWord = (String) tmpMap.get("KeyWord");
	      if (KeyWord != null && !KeyWord.equals("")) {
	        buf.append("");
	        buf.append(field.toLowerCase() + " like '%" + KeyWord + "%'");
	        buf.append(" or ");
	      }
	    }
	    return buf.toString();
	  }

  
  public static String getBlurLeft(Map map, Map tmpMap, String field) {
    StringBuffer buf = new StringBuffer();
    if (map.containsKey("KeyWordLeft")) {
      String KeyWord = (String) tmpMap.get("KeyWordLeft");
      if (KeyWord != null && !KeyWord.equals("")) {
        buf.append("");
        buf.append(field.toLowerCase() + " like '" + KeyWord + "%'");
        buf.append(" and ");
      }
    }
    return buf.toString();
  }
  
  public static String getBlurLeftForSql(Map map, Map tmpMap, String field, Map<String,Object> param) {
	    StringBuffer buf = new StringBuffer();
	    if (map.containsKey("KeyWordLeft")) {
	      String KeyWord = (String) tmpMap.get("KeyWordLeft");
	      if (KeyWord != null && !KeyWord.equals("")) {
	        buf.append("");
	        buf.append(field.toLowerCase() + " like ?");
	        param.put(field.toLowerCase(), KeyWord+"%");
	        buf.append(" and ");
	      }
	    }
	    return buf.toString();
	  }
  
  public static String getBlurLeftForHql(Map map, Map tmpMap, Map<String,Object> param, String field) {
	    StringBuffer buf = new StringBuffer();
	    if (map.containsKey("KeyWordLeft")) {
	      String KeyWord = (String) tmpMap.get("KeyWordLeft");
	      if (KeyWord != null && !KeyWord.equals("")) {
	        buf.append("");
	        buf.append(field.toLowerCase() + " like :" + field.toLowerCase());
  		    param.put(field.toLowerCase(), KeyWord+"%");
	        buf.append(" and ");
	      }
	    }
	    return buf.toString();
	  }

  public static String getOtherField(Map map, Map tmpMap, String field) {
  StringBuffer buf = new StringBuffer();
  if (map.containsKey("OtherKey")) {
    String OtherKey = (String) tmpMap.get("OtherKey");
    if (OtherKey != null && !OtherKey.equals("")) {

      buf.append(field.toLowerCase() + " =" + OtherKey);
      buf.append(" and ");
    }
  }
  return buf.toString();
}

  public static String getParentSubAll(StringBuffer pbuf,String pid) {

	  
	  try{
	  AppProductStruct aps = new AppProductStruct();
	  List psls = aps.getProductStructChild(Integer.parseInt(pid));
	  if(psls.size()>0){
	    for(int i=0;i<psls.size();i++){
	      ProductStruct ps = (ProductStruct)psls.get(i);

	      pbuf.append("psid =" + ps.getId());
	      pbuf.append(" or ");
	      
	      getParentSubAll(pbuf,ps.getId().toString());
	      //continue;
	    }
	    
	  }else{
		  
	  }
	  }catch(Exception e){
		  e.printStackTrace();
	  }

	  return pbuf.toString();
	}


  public static String getWhereSql(String whereSql) {
    whereSql=whereSql.trim();
    if (whereSql != null && whereSql.equalsIgnoreCase("where")) {
      whereSql = "";
    }
    int whereSqlLength=whereSql.length();
    if(whereSql.lastIndexOf("and")!=-1&&(whereSql.lastIndexOf("and")==whereSqlLength -3))
       {
               whereSql=whereSql.substring(0,whereSqlLength -3);

       }
    
    if(whereSql.lastIndexOf("or")!=-1&&(whereSql.lastIndexOf("or")==whereSqlLength -2))
    {
            whereSql=whereSql.substring(0,whereSqlLength -2);

    }
    try {
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    return whereSql;
  }
  

  public static Object[]  setPager(HttpServletRequest request,String tableName,String whereSql1,int pagesize)throws Exception
  {
    Object[] o=new Object[2];
    SimplePageInfo tmpPgInfo = PaginationFacility.setSimplePaginationInfo(
         request);
     String whereSql = "";
     String tmpCurPgNoStr = request.getParameter(PaginationFacility.
                                                 PAGINATION_PARA_NAME);
     if (tmpCurPgNoStr == null || tmpCurPgNoStr.equals("")) {
       whereSql = whereSql1;
       request.getSession().setAttribute("SearchCondition", whereSql);
       tmpCurPgNoStr = "1";
     }
     else {
       whereSql = (String) request.getSession().getAttribute("SearchCondition");
     }
     int tmpCurPgNo = 1;
     tmpCurPgNo = Integer.parseInt(tmpCurPgNoStr);
     tmpPgInfo.setPageSize(pagesize);
     tmpPgInfo.setCurrentPageNo(tmpCurPgNo);
     int totalrows = getRecordCount(pagesize, whereSql, tableName);
     tmpPgInfo.setTotalRcdNum(totalrows);
     o[0]=tmpPgInfo;
     o[1]=whereSql;
     return o;
  }
  
  
	public static Object[] setDynamicPager(HttpServletRequest request,
			String tableName, String whereSql1, int pagesize,String sessionAttributeName) throws Exception {
		Object[] o = new Object[2];
		SimplePageInfo tmpPgInfo = PaginationFacility
				.setSimplePaginationInfo(request);
		String whereSql = "";
		String tmpCurPgNoStr = request
				.getParameter(PaginationFacility.PAGINATION_PARA_NAME);
		if (tmpCurPgNoStr == null || tmpCurPgNoStr.equals("")) {
			whereSql = whereSql1;
			request.getSession().setAttribute(sessionAttributeName, whereSql);
			tmpCurPgNoStr = "1";
		} else {
			whereSql = (String) request.getSession().getAttribute(
					sessionAttributeName);
		}
		int tmpCurPgNo = 1;
		tmpCurPgNo = Integer.parseInt(tmpCurPgNoStr);
		tmpPgInfo.setPageSize(pagesize);
		tmpPgInfo.setCurrentPageNo(tmpCurPgNo);
		int totalrows = getRecordCount(pagesize, whereSql, tableName);
		tmpPgInfo.setTotalRcdNum(totalrows);
		o[0] = tmpPgInfo;
		o[1] = whereSql;
		return o;
	}
	

	public static Object[] setGroupByPager(HttpServletRequest request,
			String sql, int pagesize,String sessionAttributeName) throws Exception {
		Object[] o = new Object[2];
		SimplePageInfo tmpPgInfo = PaginationFacility
				.setSimplePaginationInfo(request);
		String whereSql = "";
		String tmpCurPgNoStr = request
				.getParameter(PaginationFacility.PAGINATION_PARA_NAME);
		if (tmpCurPgNoStr == null || tmpCurPgNoStr.equals("")) {
			whereSql = sql;
			request.getSession().setAttribute(sessionAttributeName, whereSql);
			tmpCurPgNoStr = "1";
		} else {
			whereSql = (String) request.getSession().getAttribute(
					sessionAttributeName);
		}
		int tmpCurPgNo = 1;
		tmpCurPgNo = Integer.parseInt(tmpCurPgNoStr);
		tmpPgInfo.setPageSize(pagesize);
		tmpPgInfo.setCurrentPageNo(tmpCurPgNo);
		int totalrows = getRecordCountBySQL(whereSql);
		tmpPgInfo.setTotalRcdNum(totalrows);
		o[0] = tmpPgInfo;
		o[1] = whereSql;
		return o;
	}
	
	public static int getRecordCountBySQL(String sql) throws Exception {
	  ResultSet rs = null;
	  int c = 0;
	  try{
		int index = sql.toLowerCase().indexOf("order by");
		if ( index > 0 ){
			sql = sql.substring(0, index);
		}
		String hql = "select count(1) from (" + sql + ") t";
		rs = EntityManager.query(hql);
		if(rs.getRow()>0){
			c=rs.getInt(1) ;
		}else{
			c=0;
		}
	  }catch ( Exception e ){
		  throw e;
	  }finally{
		  if ( rs != null ){
			  rs.close();
		  }
	  }

	  return c;
	}
	


	  public static Object[]  setExtraPager(HttpServletRequest request,String tableName,String whereSql1,int pagesize,String strid)throws Exception
	  {
	    Object[] o=new Object[2];
	    SimplePageInfo tmpPgInfo = PaginationFacility.setSimplePaginationInfo(
	         request);
	     String whereSql = "";
	     String tmpCurPgNoStr = request.getParameter(PaginationFacility.
	                                                 PAGINATION_PARA_NAME);
	     if (tmpCurPgNoStr == null || tmpCurPgNoStr.equals("")) {
	       whereSql = whereSql1;
	       request.getSession().setAttribute("ExtraCondition", whereSql);
	       tmpCurPgNoStr = "1";
	     }
	     else {
	       whereSql = (String) request.getSession().getAttribute("ExtraCondition");
	     }
	     int tmpCurPgNo = 1;
	     tmpCurPgNo = Integer.parseInt(tmpCurPgNoStr);
	     tmpPgInfo.setPageSize(pagesize);
	     tmpPgInfo.setCurrentPageNo(tmpCurPgNo);
	     int totalrows = getExtraRecordCount(pagesize, whereSql, tableName,strid);
	     tmpPgInfo.setTotalRcdNum(totalrows);
	     o[0]=tmpPgInfo;
	     o[1]=whereSql;
	     return o;
	  }
  public static boolean judgeApproveStatusToRefer(String tablename,String id)throws Exception{
	  boolean flag=false;
	  Object o=EntityManager.find(" from "+tablename+"  where approvestatus>=1 and id='"+id+"'");
	  if(o!=null){
		  flag=true;
	  }
	  return flag;
  }
  

  public static boolean judgeApproveStatusToApprover(String tablename,String id)throws Exception{
	  boolean flag=false;
	  Object o=EntityManager.find("from "+tablename+"  where approvestatus=2 and id='"+id+"'");
	  if(o!=null){
		  flag=true;
	  }
	  return flag;
  }
  

  public static boolean judgeApproveStatusToApproverVeto(String tablename,String id)throws Exception{
	  boolean flag=false;
	  Object o=EntityManager.find("from "+tablename+"  where approvestatus=3 and id='"+id+"'");
	  if(o!=null){
		  flag=true;
	  }
	  return flag;
  }
  
  public static boolean judgeApproveByUsers(String tablename,String id)throws Exception{
	  boolean flag=false;
	  Object o=EntityManager.find("from "+tablename+"  where approve=1 and id='"+id+"'");
	  if(o!=null){
		  flag=true;
	  }
	  return flag;
  }
  
  
  
  public static boolean judgeApprove(String tablename,String field,String id)throws Exception{
	  boolean flag=false;
	  String sql="from "+tablename+" where approve!=1 and "+field+"='"+id+"'";
	  Object o=EntityManager.find(sql);
	  if(o==null){
		  flag=true;
	  }
	  return flag;
  }
  
  public static boolean judgeCancelApprove(String tablename,String field,String id)throws Exception{
	  boolean flag=false;
	  String sql="from "+tablename+" where (approve=1 or approve=2) and "+field+"='"+id+"'";

	  Object o=EntityManager.find(sql);
	  if(o==null){
		  flag=true;
	  }
	  return flag;
  }
//  public static boolean judgeApprove(String tablename,String field,Long id,Long approveid)throws Exception{
//	  boolean flag=false;
//	  String sql="from "+tablename+"  where approve=1 and "+field+" = "+id+" and approveid="+approveid;
//	  Object o=EntityManager.find(sql);

//	  if(o!=null){`	
//		  flag=true;
//	  }
//	  return flag;
//  }

  public static String getWhereConditionForBill(UsersBean users) throws Exception { 
	  	AppSUserArea asua = new AppSUserArea();
		StringBuffer condition = new StringBuffer();
		if((users.getUserType() != null && UserType.getAllValueString().contains(users.getUserType().toString()))
				|| "拜耳作物科学有限公司".equals(users.getMakeorganname())
				|| "拜耳作物科学杭州工厂".equals(users.getMakeorganname())) { //销售人员与管理人员
			//非经销商机构按管辖权限
			condition.append(" (o.id in ("); 
			//负责的机构
			condition.append("select organId from USER_CUSTOMER where userid = "+users.getUserid()+" ");
			condition.append("UNION ALL ");
			//区域下级用户负责以及关联的机构
			condition.append("select organId from USER_CUSTOMER uc ");
			condition.append("join S_USER_AREA sua on uc.userid = sua.userid and sua.areaid in (");
			condition.append("select id from S_BONUS_AREA ");
			condition.append("START WITH id in (select areaid from S_USER_AREA where userid = "+users.getUserid()+") ");
			condition.append("connect by prior id = PARENTID)) ");
			condition.append("or (o.organType="+OrganType.Dealer.getValue()+" and o.organModel not in ("+DealerType.PD.getValue()+","+DealerType.BKD.getValue()+") and o.areas in (");
			condition.append("select COUNTRYAREAID from SALES_AREA_COUNTRY ");
			condition.append("where salesareaid in (");
			condition.append("select id from S_BONUS_AREA ");
			condition.append("START WITH id in (select areaid from S_USER_AREA where userid = "+users.getUserid()+") ");
			condition.append("connect by prior id = PARENTID))) ");
			//工厂按管辖权限
			condition.append("or (o.organType="+OrganType.Plant.getValue()+" and select visitorgan from user_visit where USERID = "+users.getUserid()+")");
			
			condition.append(") ");
		} else {//渠道人员
			/*condition.append(" (o.id in (");
			condition.append("select VISITORGAN from USER_VISIT where USERID ="+users.getUserid());
			condition.append(") or o.id in (");
			condition.append("select VISITORGAN from ORGAN_VISIT where USERID ="+users.getUserid());
			condition.append(") or o.id in (");
			condition.append("select organizationid from S_TRANSFER_RELATION where  opporganid = '"+users.getMakeorganid()+"')) ");*/
			condition.append(" o.id in (");
			condition.append("select organizationid from S_TRANSFER_RELATION where  opporganid = '"+users.getMakeorganid()+"') ");
		}
		return condition.toString();
	}
  
  public static String getWhereCondition(UsersBean users, String organAlias) throws Exception { 
	    if(users == null) {
		    return " 1=1 ";
	    }
	  	StringBuffer condition = new StringBuffer();
	  	AppSUserArea asua = new AppSUserArea();
	  	if(asua.isAll(users.getUserid())) {//全国,不限制条件
	  		return " 1=1 ";
	  	} else if((users.getUserType() != null && UserType.getAllValueString().contains(users.getUserType().toString()))
				|| "拜耳作物科学有限公司".equals(users.getMakeorganname())
				|| "拜耳作物科学杭州工厂".equals(users.getMakeorganname())){//销售人员内部人员
			condition.append(" ("+organAlias+".id in ("); 
			//负责的机构
			condition.append("select organId from USER_CUSTOMER where userid = "+users.getUserid()+" ");
			condition.append("UNION ALL ");
			//区域下级用户负责以及关联的机构
			condition.append("select organId from USER_CUSTOMER uc ");
			condition.append("join S_USER_AREA sua on uc.userid = sua.userid and sua.areaid in (");
			condition.append("select id from S_BONUS_AREA ");
			condition.append("START WITH id in (select areaid from S_USER_AREA where userid = "+users.getUserid()+") ");
			condition.append("connect by prior id = PARENTID)) ");
			//按区域关联的BKR
			condition.append("or ("+organAlias+".organType="+OrganType.Dealer.getValue()+" and "+organAlias+".organModel not in ("+DealerType.PD.getValue()+","+DealerType.BKD.getValue()+") and "+organAlias+".areas in (");
			condition.append("select COUNTRYAREAID from SALES_AREA_COUNTRY ");
			condition.append("where salesareaid in (");
			condition.append("select id from S_BONUS_AREA ");
			condition.append("START WITH id in (select areaid from S_USER_AREA where userid = "+users.getUserid()+") ");
			condition.append("connect by prior id = PARENTID))) ");
			//按区域关联的没有分配负责人的BKD
			condition.append("or ("+organAlias+".organType="+OrganType.Dealer.getValue()+" and "+organAlias+".organModel =" +DealerType.BKD.getValue()+" and "+organAlias+".areas in (");
			condition.append("select COUNTRYAREAID from SALES_AREA_COUNTRY ");
			condition.append("where salesareaid in (");
			condition.append("select id from S_BONUS_AREA ");
			condition.append("START WITH id in (select areaid from S_USER_AREA where userid = "+users.getUserid()+") ");
			condition.append("connect by prior id = PARENTID)) ");
			condition.append("and not EXISTS ( ");
			condition.append("SELECT organId FROM USER_CUSTOMER WHERE organId = "+organAlias+".id");
			condition.append(")) ");
			//工厂按管辖权限
			condition.append("or ("+organAlias+".organType<>"+OrganType.Dealer.getValue()+" and "+organAlias+".id in (select visitorgan from user_visit where USERID = "+users.getUserid()+"))");
			condition.append(") ");
		} else {//分装厂
			//管辖权限
			condition.append(organAlias+".id in (");
			condition.append("select visitorgan from user_visit where USERID = "+users.getUserid()+") ");
		}
		return condition.toString();
	}
  
  	public static String getWhereConditionForReport(UsersBean users, String organAlias) throws Exception { 
	  	StringBuffer condition = new StringBuffer();
	  	AppSUserArea asua = new AppSUserArea();
	  	if(asua.isAll(users.getUserid())) {//全国,不限制条件
	  		return "";
	  	} else if((users.getUserType() != null && UserType.getAllValueString().contains(users.getUserType().toString()))
				|| "拜耳作物科学有限公司".equals(users.getMakeorganname())
				|| "拜耳作物科学杭州工厂".equals(users.getMakeorganname())){//销售人员内部人员
			condition.append(" ("+organAlias+".id in ("); 
			//负责的机构
			condition.append("select organId from USER_CUSTOMER where userid = "+users.getUserid()+" ");
			condition.append("UNION ALL ");
			//区域下级用户负责以及关联的机构
			condition.append("select organId from USER_CUSTOMER uc ");
			condition.append("join S_USER_AREA sua on uc.userid = sua.userid and sua.areaid in (");
			condition.append("select id from S_BONUS_AREA ");
			condition.append("START WITH id in (select areaid from S_USER_AREA where userid = "+users.getUserid()+") ");
			condition.append("connect by prior id = PARENTID)) ");
			condition.append(") ");
		} else {//渠道人员
			//管辖权限
			condition.append(organAlias+".id in (");
			condition.append("select visitorgan from user_visit where USERID = "+users.getUserid()+") ");
		}
		return condition.toString();
	}
  	
  	public static String getUserVisitSqlCondition(int userId, String organAlias) throws Exception { 
	  	StringBuffer condition = new StringBuffer();
	  	condition.append(organAlias+".id in (select visitorgan from User_Visit where userid=" + userId + ") ");
		return condition.toString(); 
	}
  	
  	public static String getOrganVisitSqlCondition(int userId, String organAlias) throws Exception { 
	  	StringBuffer condition = new StringBuffer();
	  	condition.append(organAlias+".id in (select visitorgan from Organ_Visit where userid=" + userId + ") ");
		return condition.toString();
	}

	public static boolean isDealer(UsersBean users) {
		if(users == null) {
			return false;
		}
		return OrganType.Dealer.getValue().equals(users.getOrganType()) || 
				(OrganType.Plant.getValue().equals(users.getOrganType())
						&&PlantType.Toller.getValue().equals(users.getOrganModel()));
	} 
	
	public static void setParamForSql(Map<String,Object> param, PreparedStatement stmt) throws SQLException {
		if (null != param && param.size() > 0)
		{
			int i = 0;
            Set<Map.Entry<String, Object>> set = param.entrySet();
            for (Map.Entry<String, Object> entry : set) {
            	Object value = entry.getValue();
            	if(value instanceof Integer) {
            		stmt.setInt(i+1, (int)value);
            	} else if(value instanceof String) {
            		stmt.setString(i+1, (String)value);
            	}
                i++;
            }
		}
	}

	public static void setParamForSql(Map<String, Object> param, org.hibernate.Query query) {
		//如果查询条件不为空则匹配查询条件
		if (null != param && param.size() > 0)
		{
			int i = 0;
            Set<Map.Entry<String, Object>> set = param.entrySet();
            for (Map.Entry<String, Object> entry : set) {
                query.setParameter(i, entry.getValue());
                i++;
            }
		}
	}

	public static void replaceWithInteger(String key, Map<String, Object> param) {
		if(param.containsKey(key)) {
			try {
				param.put(key, Integer.valueOf((String)param.get(key)));
			} catch (Exception e) {
				param.put(key, -1);
			}
		}
	}

}
