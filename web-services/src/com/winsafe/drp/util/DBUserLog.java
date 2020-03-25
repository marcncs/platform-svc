package com.winsafe.drp.util;

import java.io.BufferedWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import oracle.sql.CLOB;

import org.hibernate.Session;

import com.winsafe.drp.dao.AppLeftMenu;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.StringUtil;

public class DBUserLog {

	  private static Integer maxLenght = 3000;
	
	  public static void addUserLog(int userid,String detail)throws Exception{
		  String sql="insert into user_log(userid,logtime,detail) values("+userid+",sysdate,'"+detail+"')";
		  EntityManager.updateOrdelete(sql);	  
	  }
	  
	  public static void addUserLog(HttpServletRequest request, String content)throws Exception{
		  UsersBean users = UserManager.getUser(request);
		  Integer modeltype = AppLeftMenu.getMenuIdByUrl(request.getServletPath());
		  String menusTrace = AppLeftMenu.getOpreateTarace(request.getServletPath());
		  content = menusTrace + "[" + content + "]";
		  String sql="insert into user_log(userid,modeltype,logtime,detail) values("+users.getUserid()+","+modeltype+",to_date('"+DateUtil.getCurrentDateTime()+"','YYYY-MM-DD HH24:MI:SS'),'"+content+"')";
		  EntityManager.updateOrdelete(sql);	
	  }
	  
	  public static void addUserLog(int userid, String modelName, String content)throws Exception{
		  String sql="insert into user_log(userid,modeltype,logtime,detail) values("+userid+","+
		  " (select id from left_menu l where l.lmenuname='"+ modelName +"')"+
		  ",to_date('"+DateUtil.getCurrentDateTime()+"','YYYY-MM-DD HH24:MI:SS'),'"+content+"')";
		  EntityManager.updateOrdelete(sql);
	  }
	  
	  public static void addUserLog(int userid, String modelName, String content,String modifycontent)throws Exception{
		  if(checkLength(modifycontent)){
			  addUserLog(userid, modelName, content, modifycontent, true);
			  return;
		  }
		  String sql="insert into user_log(userid,modeltype,logtime,detail,modifycontent) values("+userid+","+
		  " (select id from left_menu l where l.lmenuname='"+ modelName +"')"+
		  ",to_date('"+DateUtil.getCurrentDateTime()+"','YYYY-MM-DD HH24:MI:SS'),'"+content+"','" + modifycontent + "')";
		  EntityManager.updateOrdelete(sql);
	  }
	  /**
	   * 用于clob字段增加
	   * @param userid
	   * @param modelName
	   * @param content
	   * @param modifycontent
	   * @param isClob
	   * @throws Exception
	   */
	  public static void addUserLog(int userid, String modelName, String content,String modifycontent,boolean isClob)throws Exception{
		  Session session = HibernateUtil.currentSession();
		  Connection conn = session.connection();
		  
		  String insert="insert into user_log(userid,modeltype,logtime,detail,modifycontent) values("+userid+","+
		  " (select id from left_menu l where l.lmenuname='"+ modelName +"')"+
		  ",to_date('"+DateUtil.getCurrentDateTime()+"','YYYY-MM-DD HH24:MI:SS'),'"+content+"',' ')";
		  PreparedStatement st = conn.prepareStatement(insert,Statement.RETURN_GENERATED_KEYS);
		  st.executeUpdate();
		  ResultSet resultSet = st.getGeneratedKeys();
		  String key = "";
		  if(resultSet.next()){
			  key = resultSet.getString(1);
		  }
		  String update = "select modifycontent from user_log where rowid = '" + key +  "' for update ";
		  PreparedStatement pst = conn.prepareStatement(update);
		  ResultSet rs = pst.executeQuery();
		  while (rs.next()) {
			CLOB clob = (CLOB) rs.getClob("modifycontent");  
			BufferedWriter out = new BufferedWriter(clob.getCharacterOutputStream());
			out.write(modifycontent);
			out.flush();
			out.close();
		  }
	  }
	  
	  @Deprecated
	  public static void addUserLog(int userid, int modeltype, String content)throws Exception{
		  String sql="insert into user_log(userid,modeltype,logtime,detail) values("+userid+","+modeltype+",to_date('"+DateUtil.getCurrentDateTime()+"','YYYY-MM-DD HH24:MI:SS'),'"+content+"')";
		  EntityManager.updateOrdelete(sql);	  
	  }
	  
	  public static void addUserLog1(int userid,String detail)throws Exception{
		  String sql="insert into user_log(userid,logtime,detail) values("+userid+",sysdate,'"+detail+"')";
		  EntityManager.updateOrdelete(sql);
	  }
	  public static void addUserLog(HttpServletRequest request, String content, Object obj)throws Exception{	
		  UsersBean users = UserManager.getUser(request);
		  Integer userid = users.getUserid();
		  Integer modeltype = AppLeftMenu.getMenuIdByUrl(request.getServletPath());
		  String menusTrace = AppLeftMenu.getOpreateTarace(request.getServletPath());
		  content = menusTrace + "[" + content + "]";
		  String sql="insert into user_log(userid,modeltype,logtime,detail,modifycontent) values("+userid+","+modeltype+",to_date('"+DateUtil.getCurrentDateTime()+"','YYYY-MM-DD HH24:MI:SS'),'"+content+"','"+getContent(obj)+"')";
		  EntityManager.updateOrdelete(sql);	  
	  }
	  @Deprecated
	  public static void addUserLog(int userid, int modeltype, String content, Object obj)throws Exception{	  
		  String sql="insert into user_log(userid,modeltype,logtime,detail,modifycontent) values("+userid+","+modeltype+",to_date('"+DateUtil.getCurrentDateTime()+"','YYYY-MM-DD HH24:MI:SS'),'"+content+"','"+getContent(obj)+"')";
		  EntityManager.updateOrdelete(sql);	  
	  }
	  public static void addUserLog(int userid, String modelName, String content, Object obj)throws Exception{	  
		  String sql="insert into user_log(userid,modeltype,logtime,detail,modifycontent) values("+userid+","
		  +" (select id from left_menu l where l.lmenuname='"+ modelName +"')"+
		  ",to_date('"+DateUtil.getCurrentDateTime()+"','YYYY-MM-DD HH24:MI:SS'),'"+content+"','"+getContent(obj)+"')";
		  EntityManager.updateOrdelete(sql);	  
	  }
	  @Deprecated
	  public static void addUserLog(int userid, int modeltype, String content, Object oldObj, Object newObj)throws Exception{
		  StringBuffer sb = new StringBuffer();
		  sb.append("修改前：").append(getContent(oldObj)).append("<br>")
		  .append("修改后：").append(getContent(newObj));
		  String sql="insert into user_log(userid,modeltype,logtime,detail,modifycontent) values("+userid+","+modeltype+",to_date('"+DateUtil.getCurrentDateTime()+"','YYYY-MM-DD HH24:MI:SS'),'"+content+"','"+sb.toString()+"')";
		  EntityManager.updateOrdelete(sql);	  
	  }
	  
	  public static void addUserLog(int userid, String modelName, String content, Object oldObj, Object newObj)throws Exception{
		  StringBuffer sb = new StringBuffer();
		  sb.append("修改前：").append(getContent(oldObj)).append("<br>")
		  .append("修改后：").append(getContent(newObj));
		  
		  if(checkLength(sb.toString())){
			  addUserLog(userid, modelName, content, sb.toString(), true);
			  return;
		  }
		  
		  String sql="insert into user_log(userid,modeltype,logtime,detail,modifycontent) values("+userid+","
		  +" (select id from left_menu l where l.lmenuname='"+ modelName +"')"+
		  ",to_date('"+DateUtil.getCurrentDateTime()+"','YYYY-MM-DD HH24:MI:SS'),'"+content+"','"+sb.toString()+"')";
		  EntityManager.updateOrdelete(sql);	  
	  }
	  
	  public static void addUserLog(HttpServletRequest request, String content, Object oldObj, Object newObj)throws Exception{
		  UsersBean users = UserManager.getUser(request);
		  Integer userid = users.getUserid();
		  Integer modeltype = AppLeftMenu.getMenuIdByUrl(request.getServletPath());
		  String menusTrace = AppLeftMenu.getOpreateTarace(request.getServletPath());
		  content = menusTrace + "[" + content + "]";
		  StringBuffer sb = new StringBuffer();
		  sb.append("修改前：").append(getContent(oldObj)).append("<br>")
		  .append("修改后：").append(getContent(newObj));
		  
		  if(checkLength(sb.toString())){
			  addUserLog(true,userid, modeltype, content, sb.toString());
			  return;
		  }
		  
		  String sql="insert into user_log(userid,modeltype,logtime,detail,modifycontent) values("+userid+","+modeltype+",to_date('"+DateUtil.getCurrentDateTime()+"','YYYY-MM-DD HH24:MI:SS'),'"+content+"','"+sb.toString()+"')";
		  EntityManager.updateOrdelete(sql);	  
	  }
	  
	  public static void addUserLog(boolean isClob,int userid, int modelType, String content,String modifycontent)throws Exception{
		  Session session = HibernateUtil.currentSession();
		  Connection conn = session.connection();
		  
		  String insert="insert into user_log(userid,modeltype,logtime,detail,modifycontent) values("+userid+","+
		  modelType +
		  ",to_date('"+DateUtil.getCurrentDateTime()+"','YYYY-MM-DD HH24:MI:SS'),'"+content+"',' ')";
		  PreparedStatement st = conn.prepareStatement(insert,Statement.RETURN_GENERATED_KEYS);
		  st.executeUpdate();
		  ResultSet resultSet = st.getGeneratedKeys();
		  String key = "";
		  if(resultSet.next()){
			  key = resultSet.getString(1);
		  }
		  String update = "select modifycontent from user_log where rowid = '" + key +  "' for update ";
		  PreparedStatement pst = conn.prepareStatement(update);
		  ResultSet rs = pst.executeQuery();
		  while (rs.next()) {
			CLOB clob = (CLOB) rs.getClob("modifycontent"); 
			BufferedWriter out = new BufferedWriter(clob.getCharacterOutputStream());
			out.write(modifycontent);
			out.flush();
			out.close();
		  }
	  }
	  
	  private static String getContent(Object owner) throws Exception{			
			StringBuffer sb = new StringBuffer();
			try{
				Class ownerClass = owner.getClass();
				
				Field[] fields = ownerClass.getDeclaredFields();
				for ( int i=0; i<fields.length; i++ ){
					Field f = fields[i];
					
					f.setAccessible(true);
					String typeName = f.getType().getName();
					if ( f.getName().equalsIgnoreCase("keyscontent") ){
						continue;
					}
					if ( "java.util.Date".equals(typeName) ){
						sb.append(f.getName()+"="+DateUtil.formatDateTime((Date)f.get(owner))).append(",");
					}else{
						sb.append(f.getName()+"="+f.get(owner)).append(",");
					}
				}
			}catch (Exception e ){
				throw new Exception("getContent error:"+e.toString());
			}
			return sb.toString().substring(0, sb.toString().lastIndexOf(","));
		}
	  /**
	   * 检查内容的字节长度
	   */
	  private  static boolean checkLength(String str) throws Exception{
		if(StringUtil.isEmpty(str)){
			return false;
		}
		if(str.getBytes("utf-8").length > maxLenght){
			return true;
		}
		return false;
	  }
}
