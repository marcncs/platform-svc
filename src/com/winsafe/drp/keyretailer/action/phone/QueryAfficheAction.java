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
import com.winsafe.hbm.util.StringUtil;

public class QueryAfficheAction extends Action {
	private Logger logger = Logger.getLogger(QueryAfficheAction.class);
	private AppAffiche appAffiche = new AppAffiche();
	private AppUsers appUsers = new AppUsers();

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception { 
		response.setCharacterEncoding("utf-8");
		String imeinumber = request.getParameter("IMEI_number"); // 手机IMEI号
		String afficheType = request.getParameter("type");// 公告类型
		String username = request.getParameter("Username"); //登陆名
		String afficheId = request.getParameter("afficheId");
		try {
			Users loginUsers = appUsers.getUsers(username);
			// 获取所有发布的公告
			List<Map> list = new ArrayList<Map>();
			if(!StringUtil.isEmpty(afficheId)) {
				List<Affiche> affiches = new ArrayList<Affiche>();
				Affiche affiche = appAffiche.getAfficheByID(Integer.parseInt(afficheId));
				affiches.add(affiche);
				list = getMapDate(affiches, response);
			} else {
				if(!StringUtil.isEmpty(afficheType)) {
					if(AfficheType.BONUS.getValue().toString().equals(afficheType)) {
						List<Affiche> affiches = new ArrayList<Affiche>();
						afficheType = AfficheType.BONUS.getValue()+","+AfficheType.AUDIT.getValue()+","+AfficheType.BONUS_CONFIRM.getValue();
						affiches = appAffiche.getIsPublishAfficheByOrganIdAndType(loginUsers.getMakeorganid(), afficheType);
						list = getMapDate(affiches, response);
					} else {
						List<Map<String,String>> affiches = appAffiche.getIsPublishAfficheByType(afficheType, loginUsers.getUserid(), loginUsers.getMakeorganid());
//						appAffiche.setAfficheToRead(loginUsers.getUserid(), afficheType);
						list = getMapDate2(affiches, response); 
					} 
				} else { 
					List<Affiche> affiches = new ArrayList<Affiche>();
					afficheType = AfficheType.BONUS.getValue()+","+AfficheType.AUDIT.getValue()+","+AfficheType.BONUS_CONFIRM.getValue();
					affiches = appAffiche.getIsPublishAfficheByOrganIdAndType(loginUsers.getMakeorganid(),afficheType);
					list = getMapDate(affiches, response);
				}
			}
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, list, null, "BCS_RI",
					"IMEI:[" + imeinumber + "]", false);
		} catch (Exception e) {
			logger.error("", e);
			ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, Constants.CODE_ERROR_MSG, "", null, "BCS_RI",
					"IMEI:[" + imeinumber + "]", false);
		}
		return null;
	}

	private List<Map> getMapDate2(List<Map<String, String>>
	affiches,
			HttpServletResponse response) { 
		List<Map> message = new ArrayList<Map>();
		Map<String, Object> map = null;
		if (affiches.size() > 0) {
			for (int i = 0; i < affiches.size(); i++) {
				Map<String, String> affiche = affiches.get(i);
				map = new HashMap<String, Object>();
				map.put("id", affiche.get("id"));
				map.put("title", affiche.get("title")); 
				map.put("content", affiche.get("content"));
				map.put("publishDate", affiche.get("publishdate"));
				map.put("endDate", !StringUtil.isEmpty(affiche.get("enddate"))?affiche.get("enddate").split(" ")[0]:"");
				map.put("isRead", affiche.get("isread"));
				if(!StringUtil.isEmpty(affiche.get("type"))) {
					AfficheType at = AfficheType.parseByValue(Integer.parseInt(affiche.get("type")));
					if(at != null) {
						map.put("type", at.getName());
					} else {
						map.put("type", "");
					}
				}
				message.add(map);
			}
		} 
		return message;
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
				map.put("id", affiche.getId());
				map.put("title", affiche.getAffichetitle());
				map.put("content", affiche.getAffichecontent());
				map.put("publishDate", Dateutil.formatDate(affiche.getMakedate(), "yyyy-MM-dd HH:mm:ss"));
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
