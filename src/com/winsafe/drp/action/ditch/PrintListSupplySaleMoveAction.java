package com.winsafe.drp.action.ditch;

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
import com.winsafe.drp.dao.SupplySaleMove;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

/**
 * @author : jerry
 * @version : 2009-8-26 下午03:45:59
 * www.winsafe.cn
 */
public class PrintListSupplySaleMoveAction extends BaseAction {
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try{

	    	String Condition = "ssa.makeorganid = '"+users.getMakeorganid()+"'";
	    	
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);

			String[] tablename = { "SupplySaleMove"};
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MoveDate");
			String blur = DbUtil.getOrBlur(map, tmpMap, "ID","KeysContent");
			whereSql = whereSql + blur+ timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppSupplySaleMove appS = new AppSupplySaleMove();
			List<SupplySaleMove> lists = appS.getSupplySaleMoveAll(whereSql);
			request.setAttribute("list", lists); 
			
			DBUserLog.addUserLog(userid,4,"渠道管理>>打印渠道代销!");
			return mapping.findForward("toprint");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
}
