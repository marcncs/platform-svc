package com.winsafe.drp.keyretailer.action;

import java.io.OutputStream;
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
import com.winsafe.drp.keyretailer.dao.AppSBonusAppraise;
import com.winsafe.drp.keyretailer.service.SBonusService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog; 
import com.winsafe.drp.util.ESAPIUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.HtmlSelect;
@SuppressWarnings({ "unchecked", "deprecation" })
public class ExputSBonusAppraiseSettingAction extends BaseAction {
	private static Logger logger = Logger.getLogger(ExputSBonusAppraiseSettingAction.class);
	private AppSBonusAppraise appSBonusAppraise = new AppSBonusAppraise();

	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 初始化
		initdata(request);
		try {
			String whereSql = " where " + SBonusService.getWhereCondition(users);
			List<Map<String,String>> pils = appSBonusAppraise.getSBonusAppraiseSetting(request, 0, whereSql);
			if (pils.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition","attachment; filename=ListSBonusAppraiseSetting_"+DateUtil.getCurrentDateTimeString()+".xls");
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
			
			sheets[j].addCell(new Label(0, start+2, "年份",wcfFC));
			sheets[j].addCell(new Label(1, start+2, "上级机构",wcfFC));
			sheets[j].addCell(new Label(2, start+2, "下级客户",wcfFC));
			sheets[j].addCell(new Label(3, start+2, "目标积分",wcfFC));
			sheets[j].addCell(new Label(4, start+2, "当前积分",wcfFC));
			sheets[j].addCell(new Label(5, start+2, "积分完成率",wcfFC));
			sheets[j].addCell(new Label(6, start+2, "协议支持度",wcfFC));
			sheets[j].addCell(new Label(7, start+2, "最终积分系数",wcfFC));
			sheets[j].addCell(new Label(8, start+2, "最终积分",wcfFC));
			sheets[j].addCell(new Label(9, start+2, "客户是否已确认",wcfFC));
			sheets[j].addCell(new Label(10, start+2,"管理员是否已确认",wcfFC));
			
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 3;
				int number = 0;
				Map map = (Map) list.get(i);
				
				sheets[j].addCell(new Label(number++, row, map.get("year").toString()));
				sheets[j].addCell(new Label(number++, row, ESAPIUtil.decodeForHTML(map.get("fromorganname").toString())));
				sheets[j].addCell(new Label(number++, row, ESAPIUtil.decodeForHTML(map.get("toorganname").toString())));
				sheets[j].addCell(new Number(number++, row,map.get("targetbonus")==null?0:Double.parseDouble(map.get("targetbonus").toString()),QWCF));
				sheets[j].addCell(new Number(number++, row,map.get("curbonus")==null?0:Double.parseDouble(map.get("curbonus").toString()),QWCF));
				sheets[j].addCell(new Label(number++, row, map.get("completerate")+"%"));
				if(map.get("issupported")==null) {
					map.put("issupported", "1");
				}
				sheets[j].addCell(new Label(number++, row, HtmlSelect.getNameByOrder(request, "IsSupported", Integer.parseInt(map.get("issupported").toString()))));
				sheets[j].addCell(new Label(number++, row, map.get("rebaterate")+"%"));
				sheets[j].addCell(new Number(number++, row,map.get("rebate")==null?0:Double.parseDouble(map.get("rebate").toString()),QWCF));
				if(map.get("isconfirmed")==null) {
					map.put("isconfirmed", "0");
				}
				sheets[j].addCell(new Label(number++, row, HtmlSelect.getNameByOrder(request, "YesOrNo", Integer.parseInt(map.get("isconfirmed").toString()))));
				if(map.get("iscomplete")==null) {
					map.put("iscomplete", "0");
				}
				sheets[j].addCell(new Label(number++, row, HtmlSelect.getNameByOrder(request, "YesOrNo", Integer.parseInt(map.get("iscomplete").toString()))));
			}
		}
		workbook.write();
		workbook.close();
		os.close();
	}

}
