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
import com.winsafe.drp.dao.AppHap;
import com.winsafe.drp.dao.HapForm;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.RequestTool;

public class ListHapAllAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		Integer objsort = RequestTool.getInt(request, "objsort");
		int pagesize = 15;
		  initdata(request);
		try {
			
			String Condition = " h.makeid = " + userid +" and h.objsort= "+objsort;

			String[] tablename = { "Hap" };
			String whereSql =getWhereSql2(tablename); 
			String timeCondition = getTimeCondition("HapBegin"); 			
		
			whereSql = whereSql + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			AppHap appHap = new AppHap();
			List usList = appHap.searchHap(request,pagesize, whereSql);
			List hList = new ArrayList();
			for (int t = 0; t < usList.size(); t++) {
				HapForm hf = new HapForm();
				Object[] ob = (Object[]) usList.get(t);
				hf.setId(Integer.valueOf(ob[0].toString()));
				hf.setObjsort(Integer.valueOf(ob[1].toString()));
				hf.setCname(ob[2].toString());
				hf.setHapcontent(Integer.valueOf(ob[3].toString()));
				hf.setHapkind(Integer.valueOf(ob[4].toString()));
				hf.setHapstatus(Integer.valueOf(ob[5].toString()));
				hf.setIntend(Double.valueOf(ob[6].toString()));
				hf.setHapbegin((Date)ob[7]);
				hf.setHapend((Date)ob[8]);
				hf.setMakeorganid(ob[9].toString());
				hf.setMakeid(Integer.valueOf(ob[10].toString()));
				hf.setMakedate((Date)ob[11]);
				hList.add(hf);

			}

			request.setAttribute("hList", hList);
			request.setAttribute("objsort", objsort);
			DBUserLog.addUserLog(userid,5, "会员/积分管理>>列表销售机会");
			return mapping.findForward("list");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

}
