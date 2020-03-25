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
import com.winsafe.drp.dao.AppPayable;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Payable;
import com.winsafe.drp.dao.PayableForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class SelectPayableAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;

		super.initdata(request);
		try {
			String poid = request.getParameter("poid");
			String Condition = " pa.poid='" + poid + "'";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			// String sql = "select * from income_log as il where "+Condition;
			String[] tablename = { "Payable" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			// String timeCondition = DbUtil.getTimeCondition(map, tmpMap,"
			// MakeDate");

			whereSql = whereSql + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			Object obj[] = (DbUtil.setPager(request, "Payable as pa",
					whereSql, pagesize));
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];

			AppPayable aro = new AppPayable();			
			AppUsers au = new AppUsers();		
			

			List pbls = aro.searchPayable(pagesize, whereSql, tmpPgInfo);
			ArrayList alpl = new ArrayList();
			for (int i = 0; i < pbls.size(); i++) {
				Payable r = (Payable) pbls.get(i);
				PayableForm rf = new PayableForm();
				rf.setId(r.getId());
				rf.setPaymode(r.getPaymode());
				rf.setPaymodename(Internation.getStringByKeyPosition(
						"PayMode", request, r.getPaymode(),
						"global.sys.SystemResource"));

				rf.setBillno(r.getBillno());
				rf.setPayablesum(r.getPayablesum() - r.getAlreadysum());
				rf.setMakeidname(au.getUsersByid(r.getMakeid())
						.getRealname());
				rf.setMakedate(DateUtil.formatDate(r.getMakedate()));
				alpl.add(rf);
			}

			String paymentmode = Internation.getSelectTagByKeyAll(
					"PayMode", request, "PayMode", true, null);

			request.setAttribute("paymentmode", paymentmode);
			request.setAttribute("alpl", alpl);

			return mapping.findForward("select");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
