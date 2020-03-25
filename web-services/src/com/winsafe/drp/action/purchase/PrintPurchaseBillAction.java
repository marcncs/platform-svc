package com.winsafe.drp.action.purchase;

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
import com.winsafe.drp.dao.AppPurchaseBill;
import com.winsafe.drp.dao.PurchaseBill;
import com.winsafe.drp.dao.PurchaseBillForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class PrintPurchaseBillAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = this.getOrVisitOrgan("pb.makeorganid");
			}

			String Condition = " (pb.makeid=" + userid + " " + visitorgan
					+ ") ";

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "PurchaseBill" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" ReceiveDate");
			String blur = DbUtil.getBlur(map, tmpMap, "KeysContent");
			whereSql = whereSql + blur + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppPurchaseBill apb = new AppPurchaseBill();
			List<PurchaseBill> pbls = apb.getPurchaseBill(whereSql);
			List<PurchaseBillForm> alpb = new ArrayList<PurchaseBillForm>();
			PurchaseBillForm pbf= null;
			for (PurchaseBill o:pbls) {
				pbf = new PurchaseBillForm();
				pbf.setId(o.getId());
				pbf.setPname(o.getPname());
				pbf.setTotalsum(o.getTotalsum());
				pbf.setMakeorganid(o.getMakeorganid());
				pbf.setMakedate(o.getMakedate());
				pbf.setMakeid(o.getMakeid());
				pbf.setMakedeptid(o.getMakedeptid());
				pbf.setReceivedate(o.getReceivedate());
				pbf.setIsaudit(o.getIsaudit());
				pbf.setIsratify(o.getIsratify());
				pbf.setPaymode(o.getPaymode());
				alpb.add(pbf);
			}


			request.setAttribute("alpb", alpb);

			 DBUserLog.addUserLog(userid,2,"产品采购>>打印采购单");
			return mapping.findForward("toprint");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
