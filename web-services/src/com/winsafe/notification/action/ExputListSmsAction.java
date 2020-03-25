package com.winsafe.notification.action;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.SheetSettings;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;
import com.winsafe.sms.dao.AppSms;
import com.winsafe.sms.pojo.Sms;

@SuppressWarnings("unchecked")
public class ExputListSmsAction extends BaseAction {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ListSmsAction.class);
	private AppSms appSms = new AppSms();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Sms" };
			String whereSql = EntityManager.getTmpWhereSql2(map, tablename);
			String timeCondition = DbUtil.getTimeCondition4(map, tmpMap,
					" sendTime");
			whereSql = whereSql + timeCondition;
			String blur = DbUtil.getOrBlur2(map, tmpMap, "id", "mobileNo",
					"content");
			whereSql = whereSql + blur;
			whereSql = DbUtil.getWhereSql(whereSql);

			List sms = appSms.getExputSms(whereSql);

			if (sms.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=ListSms.xls");
			response.setContentType("application/msexcel");
			writeXls(sms, os, request);
			os.flush();
			os.close();
			DBUserLog.addUserLog(request, "[列表]");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void writeXls(List<Sms> list, OutputStream os,
			HttpServletRequest request) throws NumberFormatException, Exception {
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
			int index = 0;
			int startNum = 2;
			sheets[j].setColumnView(0, 10);
			sheets[j].setColumnView(1, 20);
			sheets[j].setColumnView(2, 150);
			sheets[j].setColumnView(3, 20);
			sheets[j].setColumnView(4, 20);
			sheets[j].mergeCells(0, start, 4, start);
			sheets[j].addCell(new Label(0, start, "短信信息  ", wchT));
			sheets[j].addCell(new Label(index++, start + startNum, "编号", wcfFC));
			sheets[j].addCell(new Label(index++, start + startNum, "手机号码", wcfFC));
			sheets[j].addCell(new Label(index++, start + startNum, "短信内容", wcfFC));
			sheets[j].addCell(new Label(index++, start + startNum, "发送时间", wcfFC));
			sheets[j].addCell(new Label(index++, start + startNum, "发送状态", wcfFC));
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 3;
				Sms p = (Sms) list.get(i);
				int rowNum = 0;
				sheets[j].addCell(new Label(rowNum++, row, p.getId().toString()));
				sheets[j].addCell(new Label(rowNum++, row, p.getMobileNo()));
				sheets[j].addCell(new Label(rowNum++, row, p.getContent()));
				sheets[j].addCell(new Label(rowNum++, row, Dateutil.formatDate(p.getSendTime())));
				String IsWrite = HtmlSelect.getNameByOrder(request, "SmsSendStatus",p.getSendStatus());
				sheets[j].addCell(new Label(rowNum++, row, IsWrite));
			}
			
		}
		workbook.write();
		workbook.close();
		os.close();
	}
}
