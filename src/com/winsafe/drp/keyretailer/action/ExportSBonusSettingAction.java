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

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.keyretailer.dao.AppSBonusSetting;
import com.winsafe.drp.keyretailer.pojo.SBonusSetting;
import com.winsafe.drp.util.Constants;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

/**
 * @author : jerry
 */
public class ExportSBonusSettingAction extends BaseAction {
	private AppSBonusSetting appSBonusSetting = new AppSBonusSetting();
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		// 初始化
		initdata(request);
		try {
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "SBonusSetting" };
			String whereSql = EntityManager.getTmpWhereSql2(map, tablename);
			String timeCondition = DbUtil.getTimeCondition4(map, tmpMap," modifiedDate");
			String blur = DbUtil.getOrBlur(map, tmpMap, "year","month","productName","spec");
			whereSql = whereSql + timeCondition + blur;
			whereSql = DbUtil.getWhereSql(whereSql);
			List<SBonusSetting> sBonusSettings = appSBonusSetting.getSBonusSetting(whereSql);
			AppBaseResource appBr = new AppBaseResource();
			Map<Integer, String> countUnitMap = appBr.getBaseResourceMap("CountUnit");
			for(SBonusSetting sbs : sBonusSettings) { 
				sbs.setCountUnitName(countUnitMap.get(sbs.getCountUnit()));
			}
			
			if (sBonusSettings.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=sbonusseeting_"+DateUtil.getCurrentDateTimeString()+".xls");
			response.setContentType("application/msexcel");
			// 写入excel
			writeXls(sBonusSettings, os, request);
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
			sheets[j].addCell(new Label(0, start, "积分设置", wchT));
			m = 0;
			sheets[j].addCell(new Label(m++, start + 1, "产品名称:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, request.getAttribute(
					"productName").toString()));
			sheets[j].addCell(new Label(m++, start + 1, "规格:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, request.getAttribute(
					"spec").toString()));
			sheets[j].addCell(new Label(m++, start + 1, "账户类型：", seachT));
			String accType = request.getParameter("accountType");
			if("2".equals(accType)) {
				sheets[j].addCell(new Label(m++, start + 1, "BKD"));
			} else if("3".equals(accType)) {
				sheets[j].addCell(new Label(m++, start + 1, "BKR"));
			} else {
				sheets[j].addCell(new Label(m++, start + 1, ""));
			}
			
			
			m = 0;
			sheets[j].addCell(new Label(m++, start + 2, "日期：", seachT));
			sheets[j].addCell(new Label(m++, start + 2, request.getParameter(
					"BeginDate")+"-"+request.getParameter("EndDate")));
			sheets[j].addCell(new Label(m++, start + 2, "是否激活：", seachT));
			String activeFlag = request.getParameter("activeFlag");
			if("1".equals(activeFlag)) {
				sheets[j].addCell(new Label(m++, start + 2, "是"));
			} else if("0".equals(activeFlag)) {
				sheets[j].addCell(new Label(m++, start + 2, "否"));
			} else {
				sheets[j].addCell(new Label(m++, start + 2, ""));
			}
			
			sheets[j].addCell(new Label(m++, start + 2, "关键字:", seachT));
			sheets[j].addCell(new Label(m++, start + 2, request.getParameter("KeyWord")));

			m = 0;
			sheets[j].addCell(new Label(m++, start + 3, "年", wcfFC));
			sheets[j].addCell(new Label(m++, start + 3, "截止月份", wcfFC));
			sheets[j].addCell(new Label(m++, start + 3, "产品名称 ", wcfFC));
			sheets[j].addCell(new Label(m++, start + 3, "规格", wcfFC));
			sheets[j].addCell(new Label(m++, start + 3, "积分点 ", wcfFC));
			sheets[j].addCell(new Label(m++, start + 3, "计量单位数量", wcfFC));
			sheets[j].addCell(new Label(m++, start + 3, "计量单位", wcfFC));
			sheets[j].addCell(new Label(m++, start + 3, "账户类型", wcfFC));
			sheets[j].addCell(new Label(m++, start + 3, "是否激活", wcfFC));
			sheets[j].addCell(new Label(m++, start + 3, "最后修改日期", wcfFC));

			for (int i = start; i < currentnum; i++) {
				int row = i - start + 4;
				SBonusSetting sbt = (SBonusSetting) list.get(i);
				m = 0;
				sheets[j].addCell(new Label(m++, row, sbt.getYear().toString()));
				sheets[j].addCell(new Label(m++, row, sbt.getMonth().toString()));
				sheets[j].addCell(new Label(m++, row, sbt.getProductName()));
				sheets[j].addCell(new Label(m++, row, sbt.getSpec()));
				if(sbt.getBonusPoint() != null) {
					sheets[j].addCell(new Number(m++, row,sbt.getBonusPoint()));
				} else {
					sheets[j].addCell(new Number(m++, row,0d));
				}
				
				if(sbt.getAmount() != null) {
					sheets[j].addCell(new Number(m++, row, sbt.getAmount()));
				} else {
					sheets[j].addCell(new Number(m++, row,0d));
				}
				
				sheets[j].addCell(new Label(m++, row, sbt.getCountUnitName()));
				sheets[j].addCell(new Label(m++, row, HtmlSelect.getNameByOrder(request, "AccountType", sbt.getAccountType())));
				sheets[j].addCell(new Label(m++, row, HtmlSelect.getNameByOrder(request, "YesOrNo", sbt.getActiveFlag())));
				sheets[j].addCell(new Label(m++, row, DateUtil.formatDateTime(sbt.getModifiedDate())));
			}
		}
		workbook.write();
		workbook.close();
		os.flush();
		os.close();
	}
}
