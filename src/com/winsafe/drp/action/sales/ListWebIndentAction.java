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
import com.winsafe.drp.dao.AppWebIndent;
import com.winsafe.drp.dao.InvoiceConf;
import com.winsafe.drp.dao.WebIndent;
import com.winsafe.drp.dao.WebIndentForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class ListWebIndentAction extends BaseAction {

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
			
			
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "WebIndent"};
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			String blur = DbUtil.getOrBlur(map, tmpMap, "ID","CName","Tel","ReceiveMan");

			whereSql = whereSql + timeCondition + blur; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			
			Object obj[] = DbUtil.setDynamicPager(request, "WebIndent as so ",
					whereSql, pagesize, "subCondition");
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];

			AppUsers au = new AppUsers();
			AppOrgan ao = new AppOrgan();
			AppWebIndent asl = new AppWebIndent();
			List pils = asl.searchWebIndent(pagesize, whereSql, tmpPgInfo);
			ArrayList also = new ArrayList();
			WebIndent so = null;
			for (int i = 0; i < pils.size(); i++) {
				so = (WebIndent) pils.get(i);
				WebIndentForm sof = new WebIndentForm();
				sof.setId(so.getId());
				sof.setCid(so.getCid());
				sof.setCname(so.getCname());
				sof.setMakedate(String.valueOf(so.getMakedate()).substring(0,16));
				sof.setEquiporganidname(ao.getOrganByID(so.getEquiporganid()).getOrganname());
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

//			DBUserLog.addUserLog(userid, "列表网站订单");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
