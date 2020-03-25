package com.winsafe.drp.action.phone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.assistant.WlmIdcodeService;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.sap.pojo.PrintJob;

public class QueryWlmIdcodeAction extends Action {
	private Logger logger = Logger.getLogger(QueryWlmIdcodeAction.class);
	private WarehouseService ws = new WarehouseService();

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("utf-8");
		AppUsers appUsers = new AppUsers();
		String username = request.getParameter("Username"); // 登陆名
		String password = request.getParameter("Password"); // 密码
		String imeinumber = request.getParameter("IMEI_number"); // 手机IMEI号
		String wlmidcode = request.getParameter("WlmIdcode"); // 查询码
		// 存放小码
		String pcString = "";
		// 存放箱码
		String existString = "";
		// 显示成功
		String wlmessage = "";
		// 提示信息
		String prompt = "";
		// 是否有权限
		String noRight = "";
		// 保存是否有物流信息
		String noLogistic = "";

		PrintJob printJob = null;
		List<TakeTicket> lttall = null;
		
		try {
			UsersBean usersBean = appUsers.getUsersBeanByLoginname(username);

			
			// 获得查询码
			String cartonCode = wlmidcode.toUpperCase().trim();
			// 查询
			WlmIdcodeService wis = new WlmIdcodeService();
			Map<String, Object> map = wis.execute(cartonCode,usersBean);

			// 显示printjob信息
			if (map.get("pg") != null) {
				printJob = (PrintJob) map.get("pg");
			}
			// 显示箱码
			if (map.get("existString") != null) {
				existString = map.get("existString").toString();
			}
			// 物流信息
			if (map.get("lttall") != null) {
				lttall = (List<TakeTicket>) map.get("lttall");
			}
			// 显示提示信息
			if (map.get("prompt") != null) {
				prompt = map.get("prompt").toString();
			}
			// 显示物流信息
			if (map.get("wlmessage") != null) {
				wlmessage = map.get("wlmessage").toString();
			}

			if (map.get("pcString") != null) {
				pcString = map.get("pcString").toString();
			}

			if (map.get("noRight") != null) {
				noRight = map.get("noRight").toString();
			}
			
			if (map.get("noLogistic") != null) {
				noLogistic = map.get("noLogistic").toString();
			}
			// 返回数据
			if (!StringUtil.isEmpty(prompt) && prompt.equals("r")) {
				Map list;
				if (pcString != "") {
					list = getMapDate(existString, printJob, lttall, response, wlmidcode,map);
				} else {
					list = getMapDate(existString, printJob, lttall, response, wlmidcode,map);
				}
				
				if (!StringUtil.isEmpty(wlmessage)) {
					// 正常显示
					ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, list, usersBean.getUserid(), "手机", "IMEI:[" + imeinumber + "]", true);
				}
				
				if (!StringUtil.isEmpty(noRight)) {
					// 没有权限显示
					ResponseUtil.writeJsonMsg(response, Constants.QUERY_ERROR_CODE3, Constants.QUERY_ERROR_CODE3_DATA_MSG, list, usersBean.getUserid(), "手机", "IMEI:[" + imeinumber + "]", true);
				}
				if (!StringUtil.isEmpty(noLogistic)) {
					// 存在箱码，但是没有物流信息显示
					ResponseUtil.writeJsonMsg(response, Constants.QUERY_ERROR_CODE4, Constants.QUERY_ERROR_CODE4_DATA_MSG, list, usersBean.getUserid(), "手机", "IMEI:[" + imeinumber + "]", true);
				}
			}

			if (!StringUtil.isEmpty(prompt) && prompt.equals("nocode")) {
				// 不存在该箱码显示
				ResponseUtil.writeJsonMsg(response, Constants.QUERY_ERROR_CODE2, Constants.QUERY_ERROR_CODE2_DATA_MSG, "", usersBean.getUserid(), "手机", "IMEI:[" + imeinumber + "]", true);
			}

			if (!StringUtil.isEmpty(prompt) && prompt.equals("rightcode")) {
				// 请输入要查询的查询码
				ResponseUtil.writeJsonMsg(response, Constants.CODE_NO_DATA, Constants.CODE_NO_DATA_MSG, "", usersBean.getUserid(), "手机", "IMEI:[" + imeinumber + "]", true);
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	/**
	 * 转化数据为Map形式
	 * @param map2 
	 */
	private Map getMapDate(String exiString, PrintJob pj, List lttall, HttpServletResponse response, String wlmidcode, Map<String, Object> resultMap) throws Exception {
		// 主要数据
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("wlmIdcode", resultMap.get("wlmIdcode"));
		// 正确结果
		// map.put("count", list.size() + 1);
		if(pj != null && pj.getPrintJobId() != -1) {
			map.put("pName", pj.getMaterialName());
			map.put("produceDate", Dateutil.formatDate(DateUtil.formatStrDate(pj.getProductionDate())));
			map.put("packageDate", Dateutil.formatDate(DateUtil.formatStrDate(pj.getPackagingDate())));
			map.put("batch", pj.getBatchNumber());
			map.put("primaryCode", StringUtil.removeNull((String)resultMap.get("primaryCode")));
			map.put("cartonCode", StringUtil.removeNull((String)resultMap.get("cartonCode")));
			map.put("covertCode", StringUtil.removeNull((String)resultMap.get("covertCode")));
		} else {
			map.put("pName", "");
			map.put("produceDate", "");
			map.put("packageDate", "");
			map.put("batch", "");
			map.put("primaryCode", StringUtil.removeNull((String)resultMap.get("primaryCode")));
			map.put("cartonCode", StringUtil.removeNull((String)resultMap.get("cartonCode")));
			map.put("covertCode", StringUtil.removeNull((String)resultMap.get("covertCode")));
		}
		map.put("boxIdcode", exiString);
		
		// 追加传递详情属性
		if (lttall != null) {
			List<Map> listDetail = new ArrayList<Map>();
			for (int i = 0; i < lttall.size(); i++) {
				TakeTicket tt = (TakeTicket) lttall.get(i);
				Map<String, String> dtl = new HashMap<String, String>();
				dtl.put("from", ws.getWarehouseName(tt.getWarehouseid()));
				dtl.put("to", ws.getWarehouseName(tt.getInwarehouseid()));
				dtl.put("moveDate", tt.getAuditdate() + "");
				listDetail.add(dtl);
			}
			map.put("wlmTrace", listDetail);
		}
		return map;
	}
}
