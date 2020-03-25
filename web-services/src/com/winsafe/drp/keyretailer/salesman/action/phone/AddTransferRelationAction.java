package com.winsafe.drp.keyretailer.salesman.action.phone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.keyretailer.dao.AppSTransferRelation;
import com.winsafe.drp.keyretailer.pojo.STransferRelation;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.util.DateUtil;

public class AddTransferRelationAction extends Action{
	
	private AppSTransferRelation appSTransferRelation = new AppSTransferRelation();
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUsers au = new AppUsers();
		String username = request.getParameter("Username");
		// 机构编号
		String organId = request.getParameter("organId");
		String oppOrganId = request.getParameter("oppOrganId");
		//根据用户名称查询用户信息
		UsersBean loginUsers = au.getUsersBeanByLoginname(username);
		
		STransferRelation str =  appSTransferRelation.getSTransferRelation(organId, oppOrganId);
		
		if(str != null) {
			ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, "设置失败,系统中已存在该进货关系");
			return null;
		}
		
		str = new STransferRelation();
		str.setModifieDate(DateUtil.getCurrentDate());
		str.setOppOrganId(oppOrganId);
		str.setOrganizationId(organId);
		
		appSTransferRelation.addSTransferRelation(str);
		
		ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, str
				,loginUsers.getUserid(),"BCS_RI","新增进货关系",true);
		return null;
	}
}

