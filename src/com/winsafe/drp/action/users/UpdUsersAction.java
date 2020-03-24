package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppUserVisit;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UserVisit;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.keyretailer.service.UserService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.RequestTool;
import com.winsafe.hbm.util.StringUtil;

public class UpdUsersAction extends Action {
	private AppUsers au = new AppUsers(); 
	private UserService service = new UserService();
//	private AppMakeConf appmc = new AppMakeConf();
//	private AppOrganVisit aov = new AppOrganVisit();
//	private AppWarehouseVisit appWV = new AppWarehouseVisit();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			String[] categaries = request.getParameterValues("ucategary");
			Integer userid = Integer.valueOf(request.getParameter("userid"));
			
			Users us = au.getUsersByid(userid);
			
			//验证手机号是否重复
			String mobile = request.getParameter("mobile");
			if (!StringUtil.isEmpty(mobile)) {
				AppUsers appUsersDao = new AppUsers();
				Users users = appUsersDao.getUsersByLoginname(mobile);
				
				if (null != users && !StringUtil.isEmpty(users.getUserid().toString()) && users.getUserid() != us.getUserid()) {
					request.setAttribute("result", "修改失败，该手机号已被其他用户使用");
					return new ActionForward("/sys/lockrecordclose2.jsp");
				}
			}
			
			Users oldus = (Users) BeanUtils.cloneBean(us);

			String loginname = request.getParameter("loginname");
			String realname = request.getParameter("realname");
			String nameen = request.getParameter("nameen");
//			Integer sex = Integer.valueOf(request.getParameter("sex"));
//			String birthday = request.getParameter("birthday");
//			String idcard = request.getParameter("idcard");
			String officetel = request.getParameter("officetel");
//			String hometel = request.getParameter("hometel");
			String email = request.getParameter("email");
//			String qq = request.getParameter("qq");
//			String msn = request.getParameter("msn");
//			String addr = request.getParameter("addr");
			Integer status = Integer.valueOf(request.getParameter("status"));
//			Integer iscall = Integer.valueOf(request.getParameter("iscall"));
			String userType= request.getParameter("userType");
			
			us.setLoginname(loginname);
			us.setRealname(realname);
			us.setNameen(nameen);
//			us.setSex(sex);
//			us.setBirthday(DateUtil.StringToDate(birthday));
//			us.setIdcard(idcard);
			us.setMobile(request.getParameter("mobile"));
			us.setOfficetel(officetel);
//			us.setHometel(hometel);
			us.setEmail(email);
//			us.setQq(qq);
//			us.setMsn(msn);
			us.setProvince(RequestTool.getInt(request, "province"));
			us.setCity(RequestTool.getInt(request, "city"));
			us.setAreas(RequestTool.getInt(request, "areas"));
//			us.setAddr(addr);
			us.setMakeorganid(request.getParameter("makeorganid"));
			us.setMakedeptid(RequestTool.getInt(request, "makedeptid"));
			us.setStatus(status);
//			us.setIscall(iscall);
			
			us.setIsCwid(Integer.valueOf(request.getParameter("isCwid")));
		
			if(!StringUtil.isEmpty(userType)){
				us.setUserType(Integer.valueOf(userType));
			} else {
				us.setUserType(null);
			}
			//判断是否为超级管理员
			if (us.getUserid() == 1) {

			} else {
				us.setValidate(RequestTool.getDate(request, "validate"));
			}
			
			service.updUsers(us);

			if (!us.getMakeorganid().equals(oldus.getMakeorganid())) {
				AppUserVisit auv = new AppUserVisit();
				UserVisit uv = auv.getUserVisitByUserID(userid);
				if (uv != null) {
					uv.setVisitorgan(us.getMakeorganid());
				}
				
				/*aov.delOrganVisitForTR(oldus.getMakeorganid(), oldus.getUserid());
				appWV.delWarehousVisitForTR(oldus.getMakeorganid(), oldus.getUserid());
				
				//许可用户的所属机构的进货关系客户
				AppOrgan appOrgan = new AppOrgan();
				Organ makeOrgan = appOrgan.getOrganByID(us.getMakeorganid());
				if(makeOrgan.getOrganType()!=null
						&&makeOrgan.getOrganType() == 2
						&&makeOrgan.getOrganModel()!=null
						&& makeOrgan.getOrganModel() == 1) {
					addWarehouseVistForTransRelationOrgan(us.getMakeorganid(), userid+"");
				}*/
			}
			
			if(categaries != null) {
				service.updUserCategary(us.getUserid(), categaries);
			} else {
				service.delUserCategary(us.getUserid().toString());
			}

			request.setAttribute("result", "databases.upd.success");
			UsersBean users = UserManager.getUser(request);
			DBUserLog.addUserLog(users.getUserid(), "系统管理", "系统管理>>修改用户密码,编号:"
					+ userid, oldus, us);

			return mapping.findForward("updResult");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("updResult");
	}
	
	/*private void addWarehouseVistForTransRelationOrgan(String organId,
			String userId) throws Exception {
		aov.addOrganVisitForTR(organId,userId);
		//增加业务往来仓库
		appWV.addWarehousVisitForTR(organId,userId);
		//更新make_conf
		appmc.updMakeConf("organ_visit","organ_visit");
		appmc.updMakeConf("warehouse_visit","warehouse_visit");
		
	}*/

}
