package com.winsafe.drp.action.assistant;

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
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppQuery;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.Query;
import com.winsafe.drp.dao.QueryForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.WfLogger;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.sap.dao.AppCartonCode;
import com.winsafe.sap.dao.AppPrimaryCode;
import com.winsafe.sap.dao.AppPrintJob;
import com.winsafe.sap.pojo.CartonCode;
import com.winsafe.sap.pojo.PrimaryCode;
import com.winsafe.sap.pojo.PrintJob;

public class ListFWNReportInitAction extends BaseAction {

	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initdata(request);
		int pageSize = 10;
		AppQuery aq = new AppQuery();
		List<Query> lq = new ArrayList();
		List llq = new ArrayList();
		try {
			// String Condition = " ( makeid=" + users.getUserid() + " ) ";
			String qcount = request.getParameter("qcount");

			/*Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Query" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String timeCondition = getTimeCondition(map, tmpMap, "findDT");
			String blur = DbUtil.getOrBlur(map, tmpMap, "proNumber", "findDt", "telNumber", "queryNum", "areas");
			if (!StringUtil.isEmpty(qcount)) {
				countString = " queryNum >= '" + qcount + "' ";
			}
			// whereSql = whereSql + timeCondition + blur + Condition;
			whereSql = whereSql + timeCondition + blur + countString;
			whereSql = DbUtil.getWhereSql(whereSql);*/

			//lq = aq.selectQuery(request, pageSize, whereSql);
			// 设置用于显示界面
			//llq = viewQuery(lq, llq);
			llq = aq.selectQuerybySql(request, pageSize, "");
			if (qcount == null) {
				request.setAttribute("qcount", 1);
			}else {
				request.setAttribute("qcount", qcount);
			}
			request.setAttribute("lq", llq);
			DBUserLog.addUserLog(request, "列表");
			return mapping.findForward("list");

		} catch (Exception e) {
			WfLogger.error("", e);
		}
		return new ActionForward(mapping.getInput());
	}

	public static String getTimeCondition(Map map, Map tmpMap, String field) {
		StringBuffer buf = new StringBuffer();
		if (map.containsKey("BeginDate")) {
			String timeField = (String) tmpMap.get("BeginDate");
			if (timeField != null && !timeField.equals("")) {

				buf.append(field + ">=to_date('" + timeField + "','yyyy-MM-dd hh24:mi:ss')");
				buf.append(" and ");
			}
		}
		if (map.containsKey("EndDate")) {
			String timeField = (String) tmpMap.get("EndDate");
			if (timeField != null && !timeField.equals("")) {

				buf.append(field + "<=to_date('" + timeField + " 23:59:59','yyyy-MM-dd hh24:mi:ss')");
				buf.append(" and ");
			}
		}
		return buf.toString().toLowerCase();
	}

	public List viewQuery(List<Query> lq, List llq) throws Exception {
		CartonCode cc = new CartonCode();
		PrimaryCode pc = new PrimaryCode();
		Product p = new Product();
		AppProduct ap = new AppProduct();
		AppPrimaryCode apc = new AppPrimaryCode();
		AppCartonCode acc = new AppCartonCode();

		for (Query query : lq) {
			QueryForm qf = new QueryForm();
			qf.setProNumber(query.getProNumber());
			qf.setFindDt(query.getFindDt());
			qf.setFindType(query.getFindType());
			qf.setQueryNum(query.getQueryNum());
			qf.setAreas(query.getAreas());
			qf.setCity(query.getCity());
			// 根据小码查找箱码
			pc = apc.getPrimaryCodeByPCode(query.getProNumber());
			if (pc != null) {
				qf.setCartonCode(pc.getCartonCode());
				qf.setPrimaryCode(pc.getPrimaryCode());
				if (!StringUtil.isEmpty(pc.getCartonCode())) {
					cc = acc.getByCartonCode(pc.getCartonCode());
					if (cc != null && !StringUtil.isEmpty(cc.getProductID())) {
						if (!StringUtil.isEmpty(cc.getOutPinCode())) {
							qf.setmPin(cc.getOutPinCode());
						} else {
							qf.setmPin(cc.getInnerPinCode());
						}
						p = ap.getProductByID(cc.getProductID());
						if (p != null) {
							qf.setProductName(p.getProductname());
							qf.setSpecmode(p.getSpecmode());
						}
					}
				}
			}
			qf.setTelNumber(query.getTelNumber());
			llq.add(qf);
		}
		return llq;
	}

}
