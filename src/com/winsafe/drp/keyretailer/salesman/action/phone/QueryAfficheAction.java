package com.winsafe.drp.keyretailer.salesman.action.phone;

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
import com.winsafe.drp.util.MapUtil;
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
//		String afficheType = request.getParameter("type");// 公告类型
		String username = request.getParameter("Username"); //登陆名
		String afficheId = request.getParameter("afficheId");
		try {
			Users loginUsers = appUsers.getUsers(username);
			// 获取所有发布的公告
			List list = new ArrayList();
			if(!StringUtil.isEmpty(afficheId)) {
				List<Affiche> affiches = new ArrayList<Affiche>();
				Affiche affiche = appAffiche.getAfficheByID(Integer.parseInt(afficheId));
				affiches.add(affiche);
				list = getMapDate(affiches, response);
			} else {
				List<Map<String,String>> affiches = appAffiche.getIsPublishAfficheForSaleman(loginUsers.getUserid());
				list = getAfficheJsonList(affiches);
			}
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, list, null, "BCS_RT",
					"IMEI:[" + imeinumber + "]", false);
		} catch (Exception e) {
			logger.error("", e);
			ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, Constants.CODE_ERROR_MSG, "", null, "BCS_RT",
					"IMEI:[" + imeinumber + "]", false);
		}
		return null;
	}

	private List<AfficheJson> getAfficheJsonList(List<Map<String, String>> affiches) throws Exception {
		List<AfficheJson> afficheJsonList = new ArrayList<AfficheJson>();
		for(Map<String, String> map : affiches) {
			AfficheJson afficheJson = new AfficheJson();
			MapUtil.mapToObject(map, afficheJson);
			if(!StringUtil.isEmpty(afficheJson.getIsRead())) {
				afficheJson.setIsRead("1");
			} else {
				afficheJson.setIsRead("0");
			}
			AfficheType at = AfficheType.parseByValue(Integer.valueOf(afficheJson.getType()));
			if(at != null) {
				afficheJson.setTypeName(at.getName());
			} 
			afficheJsonList.add(afficheJson);
		}
		return afficheJsonList;
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
				map.put("organId", affiche.getMakeorganid());
				AfficheType at = AfficheType.parseByValue(affiche.getAffichetype());
				if(at != null) {
					map.put("type", at.getValue());
					map.put("typeName", at.getName());
				} else {
					map.put("type", "");
					map.put("typeName", "");
				}
				message.add(map);
			}
		} 
		return message;
	}
	
	public class AfficheJson {
		private String id;
		private String title;
		private String content;
		private String publishDate;
		private String isRead;
		private String organId;
		private String type;
		private String typeName;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public String getPublishDate() {
			return publishDate;
		}
		public void setPublishDate(String publishDate) {
			this.publishDate = publishDate;
		}
		public String getIsRead() {
			return isRead;
		}
		public void setIsRead(String isRead) {
			this.isRead = isRead;
		}
		public String getOrganId() {
			return organId;
		}
		public void setOrganId(String organId) {
			this.organId = organId;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getTypeName() {
			return typeName;
		}
		public void setTypeName(String typeName) {
			this.typeName = typeName;
		}
	}

}
