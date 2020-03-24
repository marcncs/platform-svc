package com.winsafe.drp.action.assistant;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

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
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.ESAPIUtil;
import com.winsafe.drp.util.WfLogger;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.sap.dao.AppCartonCode;
import com.winsafe.sap.dao.AppPrimaryCode;
import com.winsafe.sap.pojo.CartonCode;
import com.winsafe.sap.pojo.PrimaryCode;

public class ExcPutFWNReportAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		initdata(request);
		AppQuery aq = new AppQuery();
		List llq = new ArrayList();
		try {
			/*
			 * FindFWNReport findFWNReport = new FindFWNReport(); String
			 * whereSql = findFWNReport.getWhereSql(request); List<Fwnumber>
			 * list = findFWNReport.getFWNReport(whereSql);
			 */
			// 获取查询条件
			/*String qcount = request.getParameter("qcount");
			String countString = "";
			Map map = new HashMap(request.getParameterMap());
			String timeBeginDate = "";
			if (map.containsKey("BeginDate")) {
				timeBeginDate = (String) map.get("BeginDate");
				if (timeBeginDate == null || "".equals(timeBeginDate)) {
					timeBeginDate = DateUtil.getCurrentDateString();
					map.put("BeginDate", timeBeginDate);
					request.setAttribute("BeginDate", timeBeginDate);
				}
			} else {
				timeBeginDate = DateUtil.getCurrentDateString();
				map.put("BeginDate", timeBeginDate);
				request.setAttribute("BeginDate", timeBeginDate);
			}

			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Query" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String timeCondition = getTimeCondition(map, tmpMap, "findDT");
			String blur = DbUtil.getOrBlur(map, tmpMap, "proNumber", "findDt", "telNumber", "queryNum", "areas");
			if (!StringUtil.isEmpty(qcount)) {
				countString = " queryNum >= '" + qcount + "' ";
			}
			// whereSql = whereSql + timeCondition + blur + Condition;
			whereSql = whereSql + timeCondition + blur + countString;
			whereSql = DbUtil.getWhereSql(whereSql);
			
			
			List<Query> list = aq.selectQuery(whereSql);
			// 设置用于显示界面
			llq = viewQuery(list, llq);*/
			llq = aq.selectQuerybySql(request, 0, "");
			if (llq.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过" + Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}

			String fname = "FWNReport";
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition", "attachment; filename=" + fname + ".xls");
			response.setContentType("application/msexcel");
			testWrite(request, llq, os);
			DBUserLog.addUserLog(request, "列表");
		} catch (Exception e) {
			WfLogger.error("", e);
		}

		return null;

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
	
	public void testWrite(HttpServletRequest request, List<QueryForm> list, OutputStream os)
			throws Exception {
		WritableWorkbook workbook = jxl.Workbook.createWorkbook(os);

		int snum = 1;
		int rowssize = 50000;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		snum = list.size() / rowssize;
		if (list.size() % rowssize >= 0) {
			snum++;
		}
		WritableSheet[] sheets = new WritableSheet[snum];
		for (int j = 0; j < snum; j++) {
			sheets[j] = workbook.createSheet("sheet" + j, j);
			sheets[j].setRowView(0, false);
			sheets[j].getSettings().setDefaultColumnWidth(20);

			int currentnum = (j + 1) * rowssize;
			if (currentnum >= list.size()) {
				currentnum = list.size();
			}
			int start = j * rowssize;

			sheets[j].mergeCells(0, start, 6, start);
			sheets[j].addCell(new Label(0, start, "产品验证报表", wchT));
			sheets[j].addCell(new Label(0, start + 1, "查询时间：", seachT));
			sheets[j].addCell(new Label(1, start + 1, request.getParameter("BeginDate") == "" ? "" : request.getParameter("BeginDate")));
			sheets[j].addCell(new Label(2, start + 1, "-"));
			sheets[j].addCell(new Label(3, start + 1, request.getParameter("EndDate") == "" ? "" : request.getParameter("EndDate")));
			sheets[j].addCell(new Label(4, start + 1, "查询次数:", seachT));
			sheets[j].addCell(new Label(5, start + 1, request.getParameter("qcount") == "" ? "" : request.getParameter("qcount")));

			sheets[j].addCell(new Label(0, start + 3, "导出机构:", seachT));
			
			sheets[j]
					.addCell(new Label(1, start + 3, ESAPIUtil.decodeForHTML(request.getAttribute("porganname").toString())));
			sheets[j].addCell(new Label(2, start + 3, "导出人:", seachT));
			sheets[j]
					.addCell(new Label(3, start + 3, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start + 3, "导出时间:", seachT));
			sheets[j].addCell(new Label(5, start + 3, DateUtil.getCurrentDateTime()));

			sheets[j].addCell(new Label(0, start + 4, "查询码", wcfFC));
			sheets[j].addCell(new Label(1, start + 4, "箱码", wcfFC));
			sheets[j].addCell(new Label(2, start + 4, "mPin码", wcfFC));
			sheets[j].addCell(new Label(3, start + 4, "唯一码", wcfFC));
			sheets[j].addCell(new Label(4, start + 4, "产品名称", wcfFC));
			sheets[j].addCell(new Label(5, start + 4, "规格", wcfFC));
			sheets[j].addCell(new Label(6, start + 4, "查询日期", wcfFC));
			sheets[j].addCell(new Label(7, start + 4, "查询方式", wcfFC));
			sheets[j].addCell(new Label(8, start + 4, "查询电话/IP", wcfFC));
			sheets[j].addCell(new Label(9, start + 4, "查询省份", wcfFC));
			sheets[j].addCell(new Label(10, start + 4, "累计次数", wcfFC));

			for (int i = start; i < currentnum; i++) {
				int row = i - start + 5;
				/*QueryForm ul = (QueryForm) list.get(i);
				sheets[j].addCell(new Label(0, row, ul.getProNumber() == "" ? "" : ul.getProNumber()));
				sheets[j].addCell(new Label(1, row, ul.getCartonCode() == "" ? "" : ul.getCartonCode()));
				sheets[j].addCell(new Label(2, row, ul.getmPin() == "" ? "" : ul.getmPin()));
				sheets[j].addCell(new Label(3, row, ul.getPrimaryCode() == "" ? "" : ul.getPrimaryCode()));
				sheets[j].addCell(new Label(4, row, ul.getProductName() == "" ? "" : ul.getProductName()));
				sheets[j].addCell(new Label(5, row, ul.getSpecmode() == "" ? "" : ul.getSpecmode()));
				sheets[j].addCell(new Label(6, row, ul.getFindDt() == null ? null : sdf.format(ul.getFindDt())));
				sheets[j].addCell(new Label(7, row, ul.getFindType() == null ? null : HtmlSelect.getResourceName(request,
						"QueryMode", Integer.valueOf(ul.getFindType()))));
				sheets[j].addCell(new Label(8, row, ul.getTelNumber() == "" ? "" : ul.getTelNumber()));
				sheets[j].addCell(new Label(9, row, ul.getAreas() == "" ? "" : ul.getAreas()));
				sheets[j].addCell(new Label(10, row, ul.getQueryNum()+""));*/
				Map<String,String> ul = (Map<String,String>) list.get(i);
				String str = getVal(ul.get("pronumber"));
				sheets[j].addCell(new Label(0, row, getVal(ul.get("pronumber"))));
				sheets[j].addCell(new Label(1, row, getVal(ul.get("cartoncode"))));
				sheets[j].addCell(new Label(2, row, getVal(ul.get("mpin"))));
				sheets[j].addCell(new Label(3, row, getVal(ul.get("primarycode"))));
				sheets[j].addCell(new Label(4, row, getVal(ul.get("productname"))));
				sheets[j].addCell(new Label(5, row, getVal(ul.get("specmode"))));
				sheets[j].addCell(new Label(6, row, getVal(ul.get("finddt"))));
				sheets[j].addCell(new Label(7, row, HtmlSelect.getResourceName(request,
						"QueryMode", Integer.valueOf(getVal(ul.get("findtype"))))));
				sheets[j].addCell(new Label(8, row, getVal(ul.get("telnumber"))));
				sheets[j].addCell(new Label(9, row, getVal(ul.get("areas"))));
				sheets[j].addCell(new Label(10, row, getVal(ul.get("querynum"))));
			}
		}
		workbook.write();
		workbook.close();
		os.close();
	}

	private String getVal(String string) {
		if(StringUtil.isEmpty(string)) {
			return "";
		}
		return string;
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
