package com.winsafe.drp.action.warehouse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSupplySaleMove;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

/**
 * @author : jerry
 * @version : 2009-8-26 下午03:45:59
 * www.winsafe.cn
 */
public class ListSupplySaleMoveReceiveAction extends BaseAction {
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize =10;
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		super.initdata(request);try{

	    	String Condition = "ssa.inorganid = '"+users.getMakeorganid()+"' and ssa.isshipment=1 and ssa.isblankout=0";
	    	
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);

			String[] tablename = { "SupplySaleMove"};
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MoveDate");
			String blur = DbUtil.getOrBlur(map, tmpMap, "KeysContent");
			whereSql = whereSql + blur+ timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppSupplySaleMove appS = new AppSupplySaleMove();
			List lists = appS.getSupplySaleMoveAll(request,pagesize,whereSql);
			
			request.setAttribute("list", lists); 
			request.setAttribute("MakeOrganID", request.getParameter("MakeOrganID"));
			request.setAttribute("MakeDeptID", request.getParameter("MakeDeptID"));
			request.setAttribute("MakeID", request.getParameter("MakeID"));
			request.setAttribute("ReceiveOrganID", request.getParameter("ReceiveOrganID"));
			request.setAttribute("SupplyOrganID", request.getParameter("SupplyOrganID"));
			request.setAttribute("IsComplete", map.get("IsComplete"));
			request.setAttribute("BeginDate", request.getParameter("BeginDate"));
			request.setAttribute("EndDate", request.getParameter("EndDate"));
			request.setAttribute("KeyWord", request.getParameter("KeyWord"));
			DBUserLog.addUserLog(userid,7,"入库>>列表代销签收");
			return mapping.findForward("list");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
}
