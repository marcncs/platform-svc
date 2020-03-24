package com.winsafe.drp.action.purchase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppPaymentApply;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.PaymentApply;
import com.winsafe.drp.dao.PaymentApplyForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class ListPaymentApplyAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		AppUsers au = new AppUsers();
		try {
			// String Condition = " pl.pid='"+pid +"' ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			// String sql = "select * from provide_linkman ";
			String[] tablename = { "PaymentApply" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename); 
			StringBuffer buf = new StringBuffer();

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			whereSql = whereSql + timeCondition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			Object obj[] = (DbUtil.setDynamicPager(request,
					"PaymentApply as pa", whereSql, pagesize, "pasubCondition"));
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];

			AppPaymentApply apa = new AppPaymentApply();
			List<PaymentApply> apls = apa.getPaymentApply(request, pagesize,
					whereSql);
			ArrayList alpl = new ArrayList();
			for (int i = 0; i < apls.size(); i++) {
				PaymentApplyForm paf = new PaymentApplyForm();
				PaymentApply p = (PaymentApply) apls.get(i);
				paf.setId(p.getId());
				paf.setPname(p.getPname());
				paf.setPlinkman(p.getPlinkman());
				paf.setTel(p.getTel());
				paf.setTotalsum(p.getTotalsum());
				paf.setMakeid(p.getMakeid());
				paf.setMakedate(p.getMakedate().toString());
				alpl.add(paf);
			}

			request.setAttribute("alpl", alpl);
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			//DBUserLog.addUserLog(userid, "列表付款申请");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
