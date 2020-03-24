package com.winsafe.drp.action.sales;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPact;
import com.winsafe.drp.dao.PactForm;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.RequestTool;

public class ListPactAllAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		AppPact ap = new AppPact();
		int pagesize = 10;
		Integer objsort = RequestTool.getInt(request, "objsort");
		initdata(request);
		try {
			
			String Condition =" p.userid=" + userid +" and p.objsort = "+objsort;
		
			String[] tablename = { "Pact" };
			String whereSql = getWhereSql2(tablename); 

			String timeCondition = getTimeCondition("SignDate"); 
			whereSql = whereSql + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			List usList = ap.searchPact(request,pagesize, whereSql);
			ArrayList packkist = new ArrayList();
			for (int t = 0; t < usList.size(); t++) {
				PactForm pa = new PactForm();
				Object[] ob = (Object[]) usList.get(t);
				
				pa.setId(Integer.valueOf(ob[0].toString()));
				pa.setPactcode(ob[1].toString());
				pa.setPacttype(Integer.valueOf(ob[2].toString()));
				pa.setObjsort(Integer.valueOf(ob[3].toString()));
				pa.setCid(ob[4].toString());
				pa.setCname(ob[5].toString());
				pa.setCdeputy(ob[6].toString());
				pa.setSigndate((Date)ob[7]);
				pa.setPdeputy(ob[8].toString());
				pa.setSignaddr(ob[9].toString());
				pa.setMakeid(Integer.valueOf(ob[10].toString()));
				pa.setMakedate((Date)ob[11]);
				
				packkist.add(pa);

			}
			request.setAttribute("objsort", objsort);
			request.setAttribute("usList", packkist);

			DBUserLog.addUserLog(userid,5,"会员/积分管理>>列表客户合同");
			return mapping.findForward("list");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}
}
