package com.winsafe.drp.action.sales;

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
import com.winsafe.drp.dao.AppInvoiceConf;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.AppVocationOrder;
import com.winsafe.drp.dao.InvoiceConf;
import com.winsafe.drp.dao.VocationOrder;
import com.winsafe.drp.dao.VocationOrderForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class ListVocationOrderAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;

		String isauditselect = Internation.getSelectTagByKeyAll("YesOrNo",
				request, "IsAudit", true, null);
		String isblankoutselect = Internation.getSelectTagByKeyAll("YesOrNo",
				request, "IsBlankOut", true, null);
		String isendcaseselect = Internation.getSelectTagByKeyAll("YesOrNo",
				request, "IsEndcase", true, null);

		
//		Long userid = users.getUserid();
		try {
			
			String visitorgan = "";
			if(users.getVisitorgan()!=null&&users.getVisitorgan().length()>0){
				visitorgan = "or so.makeorganid in("+users.getVisitorgan()+") or so.equiporganid in("+users.getVisitorgan()+")";
			}

//			String Condition = " (so.makeid="+userid+" "+visitorgan+" )";

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "VocationOrder"};
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			String blur = DbUtil.getBlur(map, tmpMap, "KeysContent");

//			whereSql = whereSql + timeCondition + blur + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			
			Object obj[] = DbUtil.setDynamicPager(request, "VocationOrder as so ",
					whereSql, pagesize, "subCondition");
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];

			AppUsers au = new AppUsers();
			AppOrgan ao = new AppOrgan();
			AppVocationOrder asl = new AppVocationOrder();
			List pils = asl.searchVocationOrder(pagesize, whereSql, tmpPgInfo);
			ArrayList also = new ArrayList();
			VocationOrder so = null;
			for (int i = 0; i < pils.size(); i++) {
				so = (VocationOrder) pils.get(i);
				VocationOrderForm sof = new VocationOrderForm();
				sof.setId(so.getId());
				sof.setCustomerbillid(so.getCustomerbillid());
				sof.setCid(so.getCid());
				sof.setCname(so.getCname());
				sof.setMakeorganidname(ao.getOrganByID(so.getMakeorganid()).getOrganname());
				sof.setMakedate(String.valueOf(so.getMakedate()).substring(0,16));
				sof.setConsignmentdate(DateUtil.formatDate(so
						.getConsignmentdate()));
				sof.setTotalsum(so.getTotalsum());
				sof.setIsauditname(Internation.getStringByKeyPosition(
						"YesOrNo", request, so.getIsaudit(),
						"global.sys.SystemResource"));
				sof.setIsblankout(so.getIsblankout());
				sof.setIsblankoutname(Internation.getStringByKeyPosition(
						"YesOrNo", request, so.getIsblankout(),
						"global.sys.SystemResource"));
				sof.setIsendcasename(Internation.getStringByKeyPosition(
						"YesOrNo", request, so.getIsendcase(),
						"global.sys.SystemResource"));
//				sof.setMakeidname(au.getUsersByid(so.getMakeid())
//						.getRealname());
				if (so.getIsaudit() == 1) {
					sof.setTakestatusname(Internation.getStringByKeyPosition(
							"TakeStatus", request, so.getTakestatus(),
							"global.sys.SystemResource"));
				}
				also.add(sof);
			}

			List als =  au.getIDAndLoginNameByOID2(users.getMakeorganid());
			
			
			
			AppInvoiceConf aic = new AppInvoiceConf();
			List ils = aic.getAllInvoiceConf();
			ArrayList ivls = new ArrayList();
			for (int u = 0; u < ils.size(); u++) {
				InvoiceConf ic = (InvoiceConf) ils.get(u);
				ivls.add(ic);
			}
			request.setAttribute("ivls", ivls);
			
			List ols = ao.getOrganToDown(users.getMakeorganid());

			request.setAttribute("ols",ols);
			request.setAttribute("als", als);
			request.setAttribute("also", also);
			//request.setAttribute("isreferselect", isreferselect);
			request.setAttribute("isauditselect", isauditselect);
			request.setAttribute("isblankoutselect", isblankoutselect);
			request.setAttribute("isendcaseselect", isendcaseselect);

//			DBUserLog.addUserLog(userid, "列表销售订单");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
