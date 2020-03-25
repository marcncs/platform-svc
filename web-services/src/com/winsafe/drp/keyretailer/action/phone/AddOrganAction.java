package com.winsafe.drp.keyretailer.action.phone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.keyretailer.service.OrganService;
import com.winsafe.drp.server.AfficheService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.util.StringUtil;

public class AddOrganAction extends Action {
	private static Logger logger = Logger.getLogger(AddOrganAction.class);
	
	private AppOrgan appOrgan = new AppOrgan();
	private OrganService organService = new OrganService();
	private AppUsers appUsers = new AppUsers();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String username = request.getParameter("Username"); //登陆名
		
		try {
			UsersBean loginUsers = appUsers.getUsersBeanByLoginname(username);
			
			String organName = request.getParameter("organName");
			String name = request.getParameter("name");
			String idCard = request.getParameter("idCard");
			String mobile = request.getParameter("mobile");
			String address = request.getParameter("address");
//			String detailAddress = request.getParameter("detailAddress");
			Integer province = Integer.parseInt(request.getParameter("province"));
			Integer city = Integer.parseInt(request.getParameter("city"));
			Integer area = Integer.parseInt(request.getParameter("area"));
			String organkeytype = request.getParameter("organkeytype");	

			
			if(appOrgan.isUsersExists(mobile, null)) {
				ResponseUtil.writeJsonMsg(response, "-3", "创建失败,已存在手机号为["+mobile+"]的客户");
				return null;
			}
			//isOrganExists(String parentId, String organName,String province,String city,String area,String address)
			if(appOrgan.isOrganParentIdExists(loginUsers.getMakeorganid(),organName,address, null)) {
				ResponseUtil.writeJsonMsg(response, "-3", "创建失败,单位:"+organName+"已经存在重复地址");
				return null;
			}
			
			Organ organ = organService.addBKR(loginUsers, organName, mobile, address, name,province,city,area,organkeytype);
			organService.addOlinkman(loginUsers, organ, name, idCard);
			
			
			AfficheService as = new AfficheService();
			//新增消息
			as.addCreateAuditRequestAffiche(organ);
			
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, loginUsers.getUserid(),"APP","新增BKR客户:"+organ.getId(),true);
		} catch (Exception e) {
			logger.error("",e);
			ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, "操作失败,系统异常");
		}
		return null;
	}

}
