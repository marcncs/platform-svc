package com.winsafe.drp.action.finance;

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
import com.winsafe.drp.dao.AppCashBank;
import com.winsafe.drp.dao.AppIncomeLog;
import com.winsafe.drp.dao.IncomeLog;
import com.winsafe.drp.dao.IncomeLogForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.UsersService;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;

public class ListIncomeLogByReceivableObjectAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		UsersService au = new UsersService();
		String roid=request.getParameter("ROID");
		String orgid= request.getParameter("ORGID");
	    if(roid==null){
	    	roid=(String)request.getSession().getAttribute("roid");
	    }
	    if(orgid==null){
	    	orgid = (String)request.getSession().getAttribute("orgid");
	    }
	    request.getSession().setAttribute("roid",roid);
	    request.getSession().setAttribute("orgid", orgid);	    

		super.initdata(request);super.initdata(request);try{
			String Condition = " il.roid ='"+roid+"' and il.makeorganid='"+orgid+"' " ;
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "IncomeLog" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename); 
			String blur = DbUtil.getBlur(map, tmpMap, "KeysContent");
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			whereSql = whereSql + blur +timeCondition +Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 


			AppIncomeLog ail = new AppIncomeLog();

			List slls = ail.getIncomeLog(request,pagesize, whereSql);
			ArrayList arls = new ArrayList();
			AppCashBank apcb = new AppCashBank();
			for (int i = 0; i < slls.size(); i++) {
				IncomeLog il = (IncomeLog)slls.get(i);
				IncomeLogForm ilf = new IncomeLogForm();			
				ilf.setId(il.getId());
				ilf.setDrawee(il.getDrawee());
				ilf.setIncomesum(il.getIncomesum());
				ilf.setFundattachname(apcb.getCBName(il.getFundattach()));
				ilf.setMakeidname(au.getUsersName(il.getMakeid()));
				ilf.setMakedate(DateUtil.formatDate(il.getMakedate()));
				arls.add(ilf);
			}

			request.setAttribute("arls", arls);

			//DBUserLog.addUserLog(userid,"列表收款记录从收款对象"); 
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
