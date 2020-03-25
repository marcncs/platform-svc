package com.winsafe.drp.keyretailer.action.phone;

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
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.keyretailer.metadata.AfficheType;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.ResponseUtil;

public class DealReadAfficheAction extends Action {
	private Logger logger = Logger.getLogger(DealReadAfficheAction.class);
	private AppAffiche appAffiche = new AppAffiche();
	private AppUsers appUsers = new AppUsers();

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("utf-8");
		String imeinumber = request.getParameter("IMEI_number"); // 手机IMEI号
		String afficheId = request.getParameter("id");
		try {
			appAffiche.updAffiche(afficheId);
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG,"", null, "手机",
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
				map.put("isRead", (affiche.getIsread()==null||affiche.getIsread()=="")?"0":affiche.getIsread());
				AfficheType at = AfficheType.parseByValue(affiche.getAffichetype());
				if(at != null) {
					map.put("type", at.getName());
				} else {
					map.put("type", "");
				}
				message.add(map);
			}
		} 

		return message;
	}

}
