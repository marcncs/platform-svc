package com.winsafe.drp.action.sys;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppRuleUserWH;
import com.winsafe.drp.dao.AppUserVisit;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.AppWarehouseVisit;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.RuleUserWh;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.dao.WarehouseVisit;
import com.winsafe.drp.util.Constants;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.StringFilters;

public class ToVisitUsersWarehouseAction extends BaseAction{

	public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
		Integer userId = Integer.valueOf(request.getSession().getAttribute("visituser").toString());
		super.initdata(request);

    
		String type=request.getParameter("type");
		if(StringUtil.isEmpty(type)){
		    return mapping.findForward("toselect");
		}else if("list".equals(type)){

		    AppUsers au = new AppUsers();
		    UsersBean userbean=au.getUsersByID(userId);
		    List<UsersBean> users=new ArrayList();
		    users.add(userbean);
		    AppWarehouse aw=new AppWarehouse();
		 
		    AppWarehouseVisit awv = new AppWarehouseVisit();
		   
		   
//		    //通过userid去取到用户现在有权限的仓库集合
//		    List<WarehouseVisit> alrd = awv.getWarehouseVisitUID(String.valueOf(userId));
//		    List<Warehouse> warehouses=new ArrayList();
//		    for(int i=0;i<alrd.size();i++){
//				WarehouseVisit temp=alrd.get(i);
//				Warehouse tempWarehouse=new Warehouse();
//				tempWarehouse=aw.getWarehouseByID((temp.getWid()));
//				warehouses.add(tempWarehouse);
//		    }
		    
			String KeyWord=request.getParameter("KeyWord");
			String bigRegionName=request.getParameter("bigRegionName");
			String officeName=request.getParameter("officeName");
			String sysid=request.getParameter("sysid");
			String parentOrganid=request.getParameter("parentOrganid");
			String organid=request.getParameter("organid");

			//依据用户编号 得到所有权限仓库
			List list=awv.getWarehouseVisitByUserId(request,Constants.PAGE_SIZE,userId,KeyWord,sysid,parentOrganid,organid);

			List<WarehouseVisit> wvlist=new ArrayList();
			Object[] objs;
			WarehouseVisit wv;
		    for(Object obj:list){
		    	objs=(Object[])obj;
		    	wv=new WarehouseVisit();
		    	wv.setOrganId((String)objs[0]);
		    	wv.setSysId((String)objs[1]);
		    	wv.setOrganName((String)objs[2]);
		    	wv.setWid((String)objs[3]);
		    	wv.setWarehouseName((String)objs[4]);
		    	wv.setId(((BigDecimal)objs[5])==null?null:((BigDecimal)objs[5]).intValue());
		    	wv.setOecode((String)objs[6]);
		    	wv.setBigRegionName((String)objs[7]);
		    	wv.setOfficeName((String)objs[8]);
		    	
				wvlist.add(wv);
		    }
		    
            request.setAttribute("wvlist", wvlist);
		
		    request.setAttribute("uid",String.valueOf(userId));
		    request.setAttribute("user",userbean);
		
			return mapping.findForward("list");

		}else{
			return null;
		}
	}

}
