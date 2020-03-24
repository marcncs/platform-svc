package com.winsafe.drp.keyretailer.action;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.keyretailer.dao.AppSBonusAppraise;
import com.winsafe.drp.keyretailer.service.SBonusService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.ESAPIUtil; 
import com.winsafe.hbm.util.DateUtil;
@SuppressWarnings({ "unchecked", "deprecation" })
public class ExputSBonusAppraiseAction extends BaseAction {
	private static Logger logger = Logger.getLogger(ExputSBonusAppraiseAction.class);
	private AppSBonusAppraise appSBonusAppraise = new AppSBonusAppraise();

	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 初始化
		initdata(request);
		try {
			initdata(request);
			
			String whereSql = " where " + SBonusService.getWhereCondition(users);
			
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
//			String printJobId = (String)map.get("printJobId");
//			String[] tablename = { "SBonusAppraise" };
//			String whereSql = EntityManager.getTmpWhereSql2(map, tablename);
//			String timeCondition = DbUtil.getTimeCondition4(map, tmpMap," transOrderDate");
//			whereSql = whereSql + timeCondition;
//			whereSql = DbUtil.getWhereSql(whereSql);
//			whereSql = whereSql + condition;
//			List<SBonusAppraise> sBonusAppraises = appSBonusAppraise.getSBonusAppraise(request, pagesize, whereSql);
			List<Map<String,String>> pils = appSBonusAppraise.getSBonusAppraise(request, 0, whereSql);
			if (pils.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition","attachment; filename=ListSBonusAppraise.xls");
			response.setContentType("application/msexcel");
			writeXls(pils, os, request);
			os.flush();
			os.close();
			DBUserLog.addUserLog(userid, 3, "积分管理>>导出积分列表!");

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("", e);
			throw e;
		}
		return null;
	}

	public void writeXls(List list, OutputStream os,HttpServletRequest request) throws NumberFormatException, Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);

		int snum = 1;
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

			sheets[j].mergeCells(0, start, 10, start);
			sheets[j].addCell(new Label(0, start, "积分管理", wchT));

			sheets[j].addCell(new Label(0, start + 1, "导出机构:", seachT));
			sheets[j].addCell(new Label(1, start + 1, ESAPIUtil.decodeForHTML(users.getMakeorganname())));
			sheets[j].addCell(new Label(2, start + 1, "导出人:", seachT));
			sheets[j].addCell(new Label(3, start + 1, users.getLoginname()));
			sheets[j].addCell(new Label(4, start + 1, "导出时间:", seachT));
			sheets[j].addCell(new Label(5, start + 1, DateUtil.getCurrentDateTime()));
			
			sheets[j].addCell(new Label(0, start+2, "积分账号",wcfFC));
			sheets[j].addCell(new Label(1, start+2, "姓名",wcfFC));
			sheets[j].addCell(new Label(2, start+2, "单位名称",wcfFC));
			sheets[j].addCell(new Label(3, start+2, "单位地址",wcfFC));
			sheets[j].addCell(new Label(4, start+2, "机构编号",wcfFC));
			sheets[j].addCell(new Label(5, start+2, "手机号码",wcfFC));
			sheets[j].addCell(new Label(6, start+2, "所属区域",wcfFC));
			sheets[j].addCell(new Label(7, start+2, "年份",wcfFC));
			sheets[j].addCell(new Label(8, start+2, "目标积分",wcfFC));
			sheets[j].addCell(new Label(9, start+2, "当前总积分",wcfFC));
			sheets[j].addCell(new Label(10, start+2, "积分评价系数",wcfFC));
			sheets[j].addCell(new Label(11, start+2,"实际积分",wcfFC));
			
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 3;
				int number = 0;
				Map map = (Map) list.get(i);
				
				sheets[j].addCell(new Label(number++, row, map.get("accountid")==null?"":map.get("accountid").toString()));
				sheets[j].addCell(new Label(number++, row, map.get("name")==null?"":map.get("name").toString()));
				sheets[j].addCell(new Label(number++, row, map.get("companyname")==null?"":map.get("companyname").toString()));
				sheets[j].addCell(new Label(number++, row, map.get("address")==null?"":map.get("address").toString()));
				sheets[j].addCell(new Label(number++, row, map.get("organid")==null?"":map.get("organid").toString()));
				sheets[j].addCell(new Label(number++, row, map.get("mobile")==null?"":map.get("mobile").toString()));
				sheets[j].addCell(new Label(number++, row, map.get("areaname")==null?"":map.get("areaname").toString()));
				sheets[j].addCell(new Label(number++, row, map.get("period")==null?"":map.get("period").toString()));
				sheets[j].addCell(new Number(number++, row,map.get("targetpoint")==null?0:Double.parseDouble(map.get("targetpoint").toString()),QWCF2));
				sheets[j].addCell(new Number(number++, row,map.get("bonuspoint")==null?0:Double.parseDouble(map.get("bonuspoint").toString()),QWCF2));
				sheets[j].addCell(new Number(number++, row,map.get("appraise")==null?0:Double.parseDouble(map.get("appraise").toString()),QWCF2));
				sheets[j].addCell(new Number(number++, row,map.get("actualpoint")==null?0:Double.parseDouble(map.get("actualpoint").toString()),QWCF2));
				
			}
		}
		workbook.write();
		workbook.close();
		os.close();
	}

}
