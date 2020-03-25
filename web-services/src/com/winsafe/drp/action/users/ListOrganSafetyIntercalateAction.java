package com.winsafe.drp.action.users;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrganSafetyIntercalate;
import com.winsafe.drp.dao.AppProductStruct;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;

public class ListOrganSafetyIntercalateAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 50;
		super.initdata(request);
		try {
			String oid = request.getParameter("OID");
			if(oid ==null){
				oid=(String) request.getSession().getAttribute("sjoid");	
			}
			request.getSession().setAttribute("sjoid", oid);

			String Condition = " p.id=ws.productid and ws.organid='"+oid+"' ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Product", "OrganSafetyIntercalate" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename); 

			String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");
			String blur = DbUtil.getOrBlur(map, tmpMap, "ProductID","ProductName","SpecMode","PYCode");
			whereSql = whereSql + leftblur + blur + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 


			AppOrganSafetyIntercalate apl = new AppOrganSafetyIntercalate();
			List apls = apl.getOrganSafetyIntercalateList(request, pagesize, whereSql);
			

			request.setAttribute("OID", oid);
			request.setAttribute("alpl", apls);
			

			AppProductStruct appProductStruct = new AppProductStruct();
			List uls = appProductStruct.getProductStructCanUse();
			request.setAttribute("uls", uls);
			
//			UsersBean users = UserManager.getUser(request);
//			Integer userid = users.getUserid();
//			DBUserLog.addUserLog(userid, "列表报警设置");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
