package com.winsafe.drp.action.aftersale;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppWithdraw;
import com.winsafe.drp.dao.Withdraw;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class PrintListWithdrawAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
  	
	    	String Condition = " (w.makeid="+userid+" "+getOrVisitOrgan("w.makeorganid")+") ";

			String[] tablename = { "Withdraw" };
			String whereSql =getWhereSql2(tablename);

			String blur = getKeyWordCondition("KeysContent");
			String timeCondition = getTimeCondition("MakeDate");
			whereSql = whereSql + timeCondition + blur+ Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 


			AppWithdraw asl = new AppWithdraw();
			List<Withdraw> pils = asl.getWithdraw(whereSql);


			request.setAttribute("also", pils);
			
			DBUserLog.addUserLog(userid, 6,"零售管理>>列表退货单");
			return mapping.findForward("toprint");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
