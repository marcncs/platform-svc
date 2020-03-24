package com.winsafe.drp.action.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppRegion;
import com.winsafe.drp.dao.AppRegionUsers;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Region;
import com.winsafe.drp.dao.RegionUsers;
import com.winsafe.drp.dao.UserRegionForm;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;

public class SelectRegionAreaAction  extends BaseAction {
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		int pagesize = 10;
		String saleManId = request.getParameter("saleManId");
		String RegionId = request.getParameter("RegionId");
		String KeyWord=request.getParameter("KeyWord");
		
		super.initdata(request);
		
		AppRegion appregion=new AppRegion();
		AppRegionUsers appregionusers=new AppRegionUsers();
		AppOrgan apporgan=new AppOrgan();
		try {
			String Condition = " u.status=1  and  u.UserType=3  and  u.userid=ru.userid and r.id=ru.rid   ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			
			String[] tablename = { "Region","RegionUsers" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String blur = DbUtil.getOrBlur(map, tmpMap, "UserID", "UserName","userlogin","SortName");
		  //TODO
            if(blur.length() >0 && KeyWord!=null&&KeyWord.length() >0 ){
            	blur="( ru.userid like '%"+KeyWord+"%' or ru.username like '%"+KeyWord+"%' or userlogin like '%"+KeyWord+"%' or sortname like '%"+KeyWord+"%'  ) and "	;
            }		

			whereSql = whereSql + blur + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);
			
			AppUsers ap = new AppUsers();
			List pls = ap.getUsersByRegion(request, pagesize, whereSql);
			ArrayList sls = new ArrayList();
			int num=1;
			for (int i = 0; i < pls.size(); i++) {
				UserRegionForm urf = new UserRegionForm();
				Object[] objs = (Object[]) pls.get(i);
				Users u = (Users) objs[0];
				Region r = (Region) objs[1];
				RegionUsers ru = (RegionUsers) objs[2];
				
				urf.setId(num++);
				urf.setUserid(ru.getUserid());
				urf.setUsername(ru.getUsername());
				urf.setUserlogin(ru.getUserlogin());
				urf.setRegionid(ru.getRid());
				urf.setRegionname(r.getSortname());
				
				if (saleManId != null && saleManId.length() > 0 && RegionId.length() >0) {
					String[] saleManIds = saleManId.split(",");
					for (int j = 0; j < saleManIds.length; j++) {
						if (String.valueOf(urf.getUserid()).equals(saleManIds[j]) && String.valueOf( urf.getRegionid()).equals(RegionId) ) {
							urf.setIsChecked(1);
							break;
						}
					}
				}
				sls.add(urf);
			}
			request.setAttribute("sls", sls);
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
