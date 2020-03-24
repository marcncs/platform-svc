package com.winsafe.drp.action.users;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.winsafe.drp.dao.AppRole;
import com.winsafe.drp.dao.RoleLeftMenu;
import com.winsafe.drp.dao.RoleMenu;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class UpdPurviewAjax extends Action{
	/** 异常日志 */
	private static Logger logger = Logger.getLogger(UpdPurviewAjax.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		String oids=request.getParameter("oid");
		String lmids=request.getParameter("lmid");
		Integer roleid = Integer.valueOf(request.getParameter("roleid"));
		String op = request.getParameter("op");

		String result="";
		try{
			AppRole ar = new AppRole();
			String[] oidarray = oids.split(",");
			String[] lmidarray = lmids.split(",");
			if ( op.equals("add") ){
				for ( int i=0; i<lmidarray.length; i++ ){
					if ( lmidarray[i].equals("") ){
						continue;
					}
					Integer lmid = Integer.valueOf(lmidarray[i]);
					RoleLeftMenu rm=new RoleLeftMenu();	      		
		      		
		      		rm.setRoleid(roleid);
		      		rm.setLmid(lmid);
		      		ar.addRoleLeftMenuNoExists(rm);	
				}
				for ( int i=0; i<oidarray.length; i++ ){
					if ( oidarray[i].equals("") ){
						continue;
					}
					Integer oid = Integer.valueOf(oidarray[i]);
					RoleMenu rm=new RoleMenu();	      		
		      		rm.setRoleid(roleid);
		      		rm.setOperateid(oid);
		      		ar.addRoleMenuNoExists(rm);	
				}
			}else if ( op.equals("del") ){
				if ( roleid.intValue() == 1 ){
					result="超级系统管理员,不允许删除功能!";
					response.setContentType("text/xml;charset=utf-8");
					response.setHeader("Cache-Control", "no-cache");
					
					Document document = (Document) DocumentHelper.createDocument();
					Element root = document.addElement("result");
					Element strResult = null;
					strResult=root.addElement("resultStr");
					strResult.setText(result);
					PrintWriter out = response.getWriter();
					String s = root.asXML();
					out.write(s);
					out.close();
				}else {
					for ( int i=0; i<lmidarray.length; i++ ){
						if ( lmidarray[i].equals("") ){
							continue;
						}
						Integer lmid = Integer.valueOf(lmidarray[i]);
						ar.delRoleLeftMenuByRidLmid(roleid, lmid);					
					}
					for ( int i=0; i<oidarray.length; i++ ){
						if ( oidarray[i].equals("") ){
							continue;
						}
						Integer oid = Integer.valueOf(oidarray[i]);
						ar.delRMbyRidOid(roleid, oid);					
					}
				}
			}
			result="修改成功";
			response.setContentType("text/xml;charset=utf-8");
			response.setHeader("Cache-Control", "no-cache");

			
			Document document = (Document) DocumentHelper.createDocument();
			Element root = document.addElement("result");
			Element strResult = null;
			strResult=root.addElement("resultStr");
			strResult.setText(result);
			PrintWriter out = response.getWriter();
			String s = root.asXML();
			out.write(s);
			out.close();
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(request,"角色编号:"+roleid);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	

}
