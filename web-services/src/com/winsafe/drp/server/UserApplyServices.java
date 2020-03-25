package com.winsafe.drp.server;

import javax.servlet.http.HttpServletResponse;

import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.UserApply;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.UsersBean; 
import com.winsafe.drp.keyretailer.service.UserService;
import com.winsafe.drp.metadata.ApplyUserType;
import com.winsafe.drp.metadata.DealerType;
import com.winsafe.drp.metadata.OrganType;
import com.winsafe.drp.metadata.UserType;
import com.winsafe.drp.metadata.ValidateStatus;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.PasswordUtil;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.drp.util.SmsUtil;
import com.winsafe.hbm.util.StringUtil;

public class UserApplyServices {
	
	private AppUsers appUsers = new AppUsers();

	public boolean validateUserApply(UserApply userApply, HttpServletResponse response) throws Exception {
		if(StringUtil.isEmpty(userApply.getOrganName())) {
			ResponseUtil.writeJsonMsg(response, "-1", "机构名称不能为空");
			return false;
		}
		if(StringUtil.isEmpty(userApply.getMobile())) {
			ResponseUtil.writeJsonMsg(response, "-2", "手机号不能为空");
			return false;
		}
		if(StringUtil.isEmpty(userApply.getName())) {
			ResponseUtil.writeJsonMsg(response, "-3", "姓名不能为空");
			return false;
		}
		if(userApply.getProvince() == 0
				|| userApply.getCity() == 0
				|| userApply.getAreas() == 0) {
			ResponseUtil.writeJsonMsg(response, "-4", "省市区信息不能为空");
			return false;
		}
		if(userApply.getUserType() == 0) {
			ResponseUtil.writeJsonMsg(response, "-5", "用户类型不能为空");
			return false;
		}
		String msg = PasswordUtil.isStrongEnough(userApply.getPassword(), "", 0);
		if(!StringUtil.isEmpty(msg)) {
			ResponseUtil.writeJsonMsg(response, "-6", msg);
			return false;
		}
		if(isMobileAlreadyExists(userApply.getMobile(), null)) {
			ResponseUtil.writeJsonMsg(response, "-5", "该手机号已注册过");
			return false;
		}
		return true;
	}

	public boolean isMobileAlreadyExists(String mobile, String userApplyId) {
		return appUsers.isMobileAlreadyExists(mobile, userApplyId);
	}

	public void addUserApply(UserApply userApply) {
		appUsers.addUserApply(userApply);
	}

	public static String getWhereConditionForAuditUser(UsersBean users) {
		StringBuffer condition = new StringBuffer();
		if(UserType.ASM.getValue().equals(users.getUserType())){
			condition.append(" (");
			condition.append(" (u.userType="+ApplyUserType.BKD.getValue()+" and u.areas in (");
			condition.append("select COUNTRYAREAID from SALES_AREA_COUNTRY ");
			condition.append("where salesareaid in (");
			condition.append("select id from S_BONUS_AREA ");
			condition.append("START WITH id in (select areaid from S_USER_AREA where userid = "+users.getUserid()+") ");
			condition.append("connect by prior id = PARENTID))) ");
			
			condition.append(" or (u.userType="+ApplyUserType.BKR.getValue());
			
			condition.append(" and u.areas in (");
			condition.append("select COUNTRYAREAID from SALES_AREA_COUNTRY ");
			condition.append("where salesareaid in (");
			condition.append("select id from S_BONUS_AREA ");
			condition.append("START WITH id in (select areaid from S_USER_AREA where userid = "+users.getUserid()+") ");
			condition.append("connect by prior id = PARENTID)) ");
			
			condition.append(" AND NOT EXISTS (SELECT usa.ID FROM S_USER_AREA usa join USERS us on us.userid = usa.userid and US.USERTYPE in ("+UserType.SS.getValue()+","+UserType.SR.getValue()+") WHERE usa.areaid IN (");
			condition.append("SELECT salesareaid FROM SALES_AREA_COUNTRY WHERE countryareaid = u.areas))) ");
			condition.append(") ");
		} else if(UserType.SS.getValue().equals(users.getUserType())
				|| UserType.SR.getValue().equals(users.getUserType())) {
			condition.append(" u.userType="+ApplyUserType.BKR.getValue()+" and u.areas in (");
			condition.append("select COUNTRYAREAID from SALES_AREA_COUNTRY ");
			condition.append("where salesareaid in (");
			condition.append("select id from S_BONUS_AREA ");
			condition.append("START WITH id in (select areaid from S_USER_AREA where userid = "+users.getUserid()+") ");
			condition.append("connect by prior id = PARENTID)) ");
		}
		
		return condition.toString();
	}

	public boolean auditUsers(String userId, String isApprove, String organId,
			String remark, HttpServletResponse response, UsersBean loginUsers, String loginName) throws Exception {
		if(StringUtil.isEmpty(isApprove)
				|| "0".equals(isApprove)) {
			ResponseUtil.writeJsonMsg(response, "-1", "审批失败,审核状态值不正确");
			return false;
		}
		UserApply ua = appUsers.getUserApplyById(Integer.valueOf(userId));
		if(ua == null) {
			ResponseUtil.writeJsonMsg(response, "-1", "审批失败,该申请记录未找到");
			return false;
		}
		if(!ValidateStatus.NOT_AUDITED.getValue().equals(ua.getIsApproved())) {
			ResponseUtil.writeJsonMsg(response, "-1", "审批失败,该申请已审核过");
			return false;
		}
		ua.setApproveDate(Dateutil.getCurrentDate());
		ua.setApproveId(loginUsers.getUserid());
		ua.setRemark(remark);
		
		if("2".equals(isApprove)) {
			ua.setIsApproved(ValidateStatus.NOT_PASSED.getValue());
			appUsers.updUserApply(ua);
			SmsUtil.createSmsMessage(ua.getMobile(), "您注册的RTCI系统账号"+ValidateStatus.NOT_PASSED.getName());
			return true;
		}
		
		AppOrgan appOrgan = new AppOrgan();
		Organ organ = appOrgan.getOrganByID_Isrepeal(organId);
		if(!OrganType.Dealer.getValue().equals(organ.getOrganType())) {
			ResponseUtil.writeJsonMsg(response, "-1", "审批失败,机构类型不正确");
			return false;
		}
		if(!DealerType.PD.getValue().equals(organ.getOrganModel())
				&& !ValidateStatus.PASSED.getValue().equals(organ.getValidatestatus())) {
			ResponseUtil.writeJsonMsg(response, "-1", "审批失败,机构未通过审核");
			return false;
		}
		if(!((ApplyUserType.BKD.getValue().equals(ua.getUserType())
				&&DealerType.BKD.getValue().equals(organ.getOrganModel()))
				|| (ApplyUserType.BKR.getValue().equals(ua.getUserType())
						&&!DealerType.PD.getValue().equals(organ.getOrganModel())
						&&!DealerType.BKD.getValue().equals(organ.getOrganModel()))
						|| (ApplyUserType.TD.getValue().equals(ua.getUserType())
								&&DealerType.PD.getValue().equals(organ.getOrganModel()))
								|| (ApplyUserType.WH.getValue().equals(ua.getUserType())
										&&DealerType.PD.getValue().equals(organ.getOrganModel())))) {
			ResponseUtil.writeJsonMsg(response, "-1", "审批失败,机构类型与用户类型不匹配");
			return false;
		} 
		
		UserService us = new UserService();
		Users users = us.addUsers(ua, organ, loginName);
		
		ua.setIsApproved(ValidateStatus.PASSED.getValue());
		appUsers.updUserApply(ua);
		
		SmsUtil.createSmsMessage(ua.getMobile(), "您注册的RTCI系统账号("+users.getLoginname()+")"+ValidateStatus.PASSED.getName());
		
		return true;
	}

}
