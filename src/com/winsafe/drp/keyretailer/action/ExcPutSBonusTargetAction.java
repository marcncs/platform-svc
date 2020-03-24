package com.winsafe.drp.keyretailer.action;

import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.keyretailer.dao.AppSBonusTarget;
import com.winsafe.drp.keyretailer.service.SBonusService;
import com.winsafe.drp.metadata.OrganType;
import com.winsafe.drp.util.Constants; 
import com.winsafe.drp.util.ESAPIUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;

/**
 * @author : jerry
 */
public class ExcPutSBonusTargetAction extends BaseAction {
	private AppSBonusTarget app = new AppSBonusTarget();
	private AppBaseResource appBr = new AppBaseResource();
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		// 初始化
		initdata(request);
		try {
			initdata(request);
			
			String condition = SBonusService.getWhereCondition(users);
			if(OrganType.Dealer.getValue().equals(users.getOrganType())) {
				condition += " and fromo.id = "+users.getMakeorganid();
			}
			
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = {"SBonusTarget"};
			//where=
			String whereSql = EntityManager.getTmpWhereSql(map, tablename); 
			//时间
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,"modifiedDate");
			whereSql = whereSql + timeCondition + condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			List<Map<String,String>> list = app.getSBonusTarget(request, 0, whereSql);
			Map<Integer, String> countUnitMap = appBr.getBaseResourceMap("CountUnit");
			for(Map<String,String> sbs : list) {
				sbs.put("countunitname", countUnitMap.get(Integer.parseInt(sbs.get("countunit"))));
			}
			if (list.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=sbonustarget.xls");
			response.setContentType("application/msexcel");
			// 写入excel
			writeXls(list, os, request);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/*
	 * 将数据写入excel中
	 */
	public void writeXls(List list,OutputStream os,HttpServletRequest request) throws Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		int snum = 1;
		int m = 0;
		snum = list.size() / 50000;
		if (list.size() % 50000 >= 0) {
			snum++;
		}
		WritableSheet[] sheets = new WritableSheet[snum];
		for (int j = 0; j < snum; j++) {
			sheets[j] = workbook.createSheet("sheet" + j, j);
			int currentnum = (j + 1) * 50000;
			if (currentnum >= list.size()) {
				currentnum = list.size();
			}
			int start = j * 50000;

			sheets[j].mergeCells(0, start,24, start);
			sheets[j].addCell(new Label(0, start, "积分目标", wchT));
			m = 0;
			sheets[j].addCell(new Label(m++, start + 1, "导出机构:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, ESAPIUtil.decodeForHTML((String)request.getAttribute("porganname"))));
			sheets[j].addCell(new Label(m++, start + 1, "导出人:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, (String)request.getAttribute(
					"pusername")));
			sheets[j].addCell(new Label(m++, start + 1, "导出时间:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, DateUtil
					.getCurrentDateTime()));

			m = 0;
			sheets[j].addCell(new Label(m++, start + 2, "编号", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "出货客户", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "进货机构", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "年度", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "产品名", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "规格", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "计量单位", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "目标数量", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "积分点", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "最后修改日期", wcfFC));
			DecimalFormat decimalFormat = new DecimalFormat("###,###");
			for (int i = start; i < currentnum; i++) {
				int row = i - start + 3;
				Map<String,String> sbt = (Map<String,String>) list.get(i);
				m = 0;
				sheets[j].addCell(new Label(m++, row, sbt.get("id")));
				sheets[j].addCell(new Label(m++, row, ESAPIUtil.decodeForHTML(sbt.get("fromorganname"))));
				sheets[j].addCell(new Label(m++, row, ESAPIUtil.decodeForHTML(sbt.get("toorganname"))));
				sheets[j].addCell(new Label(m++, row, sbt.get("year")));
				sheets[j].addCell(new Label(m++, row, sbt.get("productname")));
				sheets[j].addCell(new Label(m++, row, sbt.get("spec")));
				sheets[j].addCell(new Label(m++, row, sbt.get("countunitname")));
				sheets[j].addCell(new Label(m++, row, sbt.get("targetamount")));
				sheets[j].addCell(new Label(m++, row, decimalFormat.format(Double.valueOf(sbt.get("bonuspoint")!=null?sbt.get("bonuspoint"):"0"))));
				sheets[j].addCell(new Label(m++, row, sbt.get("modifieddate")));
			}
		}
		workbook.write();
		workbook.close();
		os.flush();
		os.close();
	}
}
