package com.winsafe.drp.action.report;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrganTrades;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class PrintOrganTradesDetailAction extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		try{
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = getAndVisitOrgan("ot.makeorganid");
			}
			String Condition = " ot.id=otd.otid and ot.isreceive=1 and ot.isblankout=0 " + visitorgan+ "  ";

			String[] tablename = { "OrganTrades", "OrganTradesDetail"  };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition("ot.MakeDate");
			whereSql = whereSql + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			
			AppOrganTrades asod=new AppOrganTrades(); 
	        List list = asod.getOrganTradesDetail(whereSql);
	        request.setAttribute("list", list);
	        DBUserLog.addUserLog(userid, 10,"报表分析>>打印渠道换货明细");
			return mapping.findForward("toprint");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	

}
