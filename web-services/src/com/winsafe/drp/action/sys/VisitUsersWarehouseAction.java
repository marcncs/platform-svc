package com.winsafe.drp.action.sys;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.AppWarehouseVisit;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.dao.WarehouseVisit;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.ReturnPage;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.StringFilters;

public class VisitUsersWarehouseAction extends Action{

	
	
  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {

    
	String type=request.getParameter("type");
	Integer userId = Integer.valueOf(request.getSession().getAttribute("visituser").toString());
	AppUsers appUsers = new AppUsers();
	Users users = appUsers.getUsersByid(userId);

	String KeyWord=request.getParameter("KeyWord");
	String sysid=request.getParameter("sysid");
	String parentOrganid=request.getParameter("parentOrganid");
	String organid=request.getParameter("organid");
	String bigRegionName=request.getParameter("bigRegionName");
	String officeName=request.getParameter("officeName");

    AppWarehouseVisit awv = new AppWarehouseVisit();
	if("set".equals(type)){

		DBUserLog.addUserLog(userId,11,"用户管理>>许可仓库,用户名："+users.getRealname());

		String idsString = request.getParameter("ids");
		String valuesString = request.getParameter("values");
		
		if(StringUtil.isEmpty(idsString) || StringUtil.isEmpty(valuesString)){
			return ReturnPage.getReturn(request, "0", "操作成功！");
		}
		String[] ids=idsString.split(",");
		String[] values=valuesString.split(",");
		
		 
    	//更新数据库仓库权限
		awv.update(userId,ids,values); 
		 
		return ReturnPage.getReturn(request, "0", "操作成功！");
	}else if("checkAll".equals(type)){

		DBUserLog.addUserLog(userId,11,"用户管理>>许可仓库,用户名："+users.getRealname());
		awv.update(userId,true,KeyWord,bigRegionName,officeName,sysid,parentOrganid,organid);
		return ReturnPage.getReturn(request, "0", "操作成功！");
	}else if("uncheckAll".equals(type)){

		DBUserLog.addUserLog(userId,11,"用户管理>>许可仓库,用户名："+users.getRealname());
		awv.update(userId,false,KeyWord,bigRegionName,officeName,sysid,parentOrganid,organid);
		return ReturnPage.getReturn(request, "0", "操作成功！");
	}else{
	    String strwid = request.getParameter("wid");
	    String strspeed = request.getParameter("speedstr");
	    int count = Integer.parseInt(request.getParameter("uscount"));

	    try{

	      //awv.delWarehouseVisitByWID();
	      awv.delWarehouseVisitByUID(strwid);

	      StringTokenizer st = new StringTokenizer(strspeed, ",");
	      int i=0;
	      for(int p=0;p<count;p++){
	    	  WarehouseVisit wv = new WarehouseVisit();
	    	  wv.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("warehouse_visit",0,"")));
	    	  wv.setUserid(Integer.valueOf(strwid));
	    	  wv.setWid(String.valueOf(st.nextToken().trim()));

	        awv.addWarehouseVisit(wv);
	        i=p;
	      }
	      System.out.println(i);
	      request.setAttribute("result", "操作成功！");

	      UsersBean users2 = UserManager.getUser(request);
	      Integer userid = users2.getUserid();
	      DBUserLog.addUserLog(userid, 11, "仓库设置>>新增许可仓库用户，仓库总数："+i);
	      return mapping.findForward("visit");
	    }catch(Exception e){
	      e.printStackTrace();
	    }finally {

	    }

	    return new ActionForward(mapping.getInput());
	}
  }
  
  
}
