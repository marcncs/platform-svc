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

import com.winsafe.drp.dao.Affiche;
import com.winsafe.drp.dao.AppAffiche;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.ResponseUtil;

public class QueryAfficheAction extends Action {
	private Logger logger = Logger.getLogger(QueryAfficheAction.class);
	private AppAffiche appAffiche = new AppAffiche();

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("utf-8");
		String imeinumber = request.getParameter("IMEI_number"); // 手机IMEI号
		String afficheType = request.getParameter("AfficheType");// 公告类型
		try {
			// 获取所有发布的公告
			List<Affiche> affiches = new ArrayList<Affiche>();
			if (!afficheType.equals("-1")) {
				affiches = appAffiche.getIsPublishAffiche(afficheType);
			} else {
				affiches = appAffiche.getIsPublishAffiche();
			}
			List<Map> list = getMapDate(affiches, response);
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, list, null, "手机",
					"IMEI:[" + imeinumber + "]", false);
		} catch (Exception e) {
			logger.error("", e);
			ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, Constants.CODE_ERROR_MSG, "", null, "手机",
					"IMEI:[" + imeinumber + "]", false);
		}
		return null;
	}

	/**
	 * 转化数据为Map形式
	 */
	private List<Map> getMapDate(List<Affiche> list, HttpServletResponse response) throws Exception {
		List<Map> message = new ArrayList<Map>();
		Map<String, Object> map = null;

		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Affiche affiche = list.get(i);
				map = new HashMap<String, Object>();
				map.put("title", affiche.getAffichetitle());
				map.put("content", affiche.getAffichecontent());
				map.put("publishDate", Dateutil.formatDate(affiche.getMakedate(), "yyyy-MM-dd"));
				message.add(map);
			}
		} else {
			map = new HashMap<String, Object>();
			map.put("title", "");
			map.put("content", "");
			map.put("publishDate", "");
			message.add(map);
		}

		return message;
	}

}
