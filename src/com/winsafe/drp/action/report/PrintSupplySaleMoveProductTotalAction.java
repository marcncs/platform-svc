package com.winsafe.drp.action.report;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSupplySaleMove;
import com.winsafe.hbm.util.DbUtil;

public class PrintSupplySaleMoveProductTotalAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		try {
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = getAndVisitOrgan("makeorganid");
			}

			String[] tablename = { "SupplySaleMove", "SupplySaleMoveDetail" };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition("MoveDate");
			String Condition = "sam.id=samd.ssmid and (iscomplete=1 and isblankout=0  "
					+ visitorgan + ")";
			String blur = getKeyWordCondition("ProductID","ProductName","MoveDate");
			whereSql = whereSql + timeCondition +blur+ Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			AppSupplySaleMove assm = new AppSupplySaleMove();
			List pils = assm.getSupplySaleMoveProductTotal(whereSql);

			request.setAttribute("list", pils);

			return mapping.findForward("toprint");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
