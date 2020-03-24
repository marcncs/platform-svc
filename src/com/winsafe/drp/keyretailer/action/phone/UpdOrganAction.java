package com.winsafe.drp.keyretailer.action.phone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOlinkMan;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Olinkman;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.keyretailer.service.OrganService;
import com.winsafe.drp.server.AfficheService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.util.StringUtil;

public class UpdOrganAction extends Action {
	private static Logger logger = Logger.getLogger(UpdOrganAction.class);
	
	private AppOrgan appOrgan = new AppOrgan();
	private AppUsers appUsers = new AppUsers();
	private AppOlinkMan appOlinkMan = new AppOlinkMan();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String username = request.getParameter("Username"); //登陆名
		
		boolean isChanged = false;
		
		try {
			UsersBean loginUsers = appUsers.getUsersBeanByLoginname(username);
			
			String organId = request.getParameter("organId");
			Integer province = Integer.parseInt(request.getParameter("province"));
			Integer city = Integer.parseInt(request.getParameter("city"));
			Integer area = Integer.parseInt(request.getParameter("area"));
			
			String organName = request.getParameter("organName");
			String name = request.getParameter("name");
			String idCard = request.getParameter("idCard");
			String mobile = request.getParameter("mobile");
			String address = request.getParameter("address");
			String typeId = request.getParameter("typeId");
			
			Organ organ = appOrgan.getOrganByID(organId);
			if(organ == null) {
				ResponseUtil.writeJsonMsg(response,  Constants.CODE_ERROR, "更新失败,编号为"+organId+"的机构不存在");
				return null;
			}
			
			if(!mobile.equals(organ.getOmobile()) && appOrgan.isUsersExists(mobile, organId)) {
				ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, "更新失败,已存在手机号为["+mobile+"]的客户");
				return null;
			}
			//isOrganExists(String parentId, String organName,String province,String city,String area,String address)
			if(appOrgan.isOrganParentIdExists(loginUsers.getMakeorganid(),organName,address, organId)) {
				ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, "更新失败,单位:"+organName+"已经存在重复地址");
				return null;
			}
			
			
			
			if(!province.equals(organ.getProvince())
					|| !city.equals(organ.getCity())
					|| !area.equals(organ.getAreas())
					|| !mobile.equals(organ.getOmobile())) {
				isChanged = true;
			}
			
			
			Olinkman olinkman = appOlinkMan.findOlinkmanByCidAndMobile(organ.getId(), organ.getOmobile());
			
			organ.setCity(city);
			organ.setProvince(province);
			organ.setAreas(area);
			organ.setOrganname(organName);
			organ.setValidatestatus(0);
			organ.setOmobile(mobile);
			organ.setOaddr(address);
			
			if(!StringUtil.isEmpty(typeId)) {
				organ.setOrganModel(Integer.valueOf(typeId));
			}
			
			
			if(olinkman != null) {
				if(!StringUtil.isEmpty(idCard)
						&& !idCard.equals(olinkman.getIdcard())) {
					olinkman.setIdcard(idCard);
					isChanged = true;
				}
				if(!StringUtil.isEmpty(name)
						&& !name.equals(olinkman.getName())) {
					olinkman.setName(name);
					isChanged = true;
				}
				if(!StringUtil.isEmpty(mobile)
						&& !mobile.equals(olinkman.getMobile())) {
					olinkman.setMobile(mobile);
					isChanged = true;
				}
				appOlinkMan.updOlinkman(olinkman);
			} else {
				OrganService organService = new OrganService();
				organService.addOlinkman(loginUsers, organ, name, idCard);
			}
			
			if(isChanged) {
				AfficheService as = new AfficheService();
				//新增消息
				as.addUpdateAuditRequestAffiche(organ);
			}
			
			appOrgan.updOrgan(organ);
			
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, loginUsers.getUserid(),"APP","更新客户:"+organ.getId()+" province"+province+" city"+city+" area"+area,true);
		} catch (Exception e) {
			logger.error("",e);
			ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, "操作失败,系统异常");
		}
		return null;
	}

}
