package com.winsafe.drp.action.users;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppMakeConf;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppOrganVisit;
import com.winsafe.drp.dao.AppRole;
import com.winsafe.drp.dao.AppRuleUserWH;
import com.winsafe.drp.dao.AppUserVisit;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.AppWarehouseVisit;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.OrganVisit;
import com.winsafe.drp.dao.Role;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UserRole;
import com.winsafe.drp.dao.UserVisit;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.keyretailer.service.UserService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.PasswordUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Encrypt;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;
import com.winsafe.hbm.util.StringUtil;

public class AddUsersAction extends Action {
	private static Logger logger = Logger.getLogger(AddUsersAction.class);
	
	private AppRole ar = new AppRole();
	private UserService appUsers = new UserService();
	private  AppUserVisit auv = new AppUserVisit();
	private AppMakeConf appmc = new AppMakeConf();
	private AppRuleUserWH appRuWH = new AppRuleUserWH();
	private AppOrgan appOrgan = new AppOrgan();
	private AppOrganVisit aov = new AppOrganVisit();
	private AppWarehouseVisit appWV = new AppWarehouseVisit();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String syspassword = Encrypt.getSecret(
				request.getParameter("password"), 1);
		String sysapprovepwd = Encrypt.getSecret("aaaa", 1);
		String userType=request.getParameter("userType");
		String path = request.getSession().getServletContext().getRealPath("/").toString();
		String[] categaries = request.getParameterValues("ucategary");
		try {
			
			Integer userid = Integer.valueOf(MakeCode.getExcIDByRandomTableName("users", 0, ""));
			//验证密码强度
			String msg = PasswordUtil.checkPwdForAdd(request.getParameter("password"), request.getParameter("loginname"), userid);
			if(!StringUtil.isEmpty(msg)) {
				request.setAttribute("result", msg);
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			
			//验证手机号是否重复
			String mobile = request.getParameter("mobile");
			if (!StringUtil.isEmpty(mobile)) {
				AppUsers appUsersDao = new AppUsers();
				Users users = appUsersDao.getUsersByLoginname(mobile);
				
				if (null != users && !StringUtil.isEmpty(users.getUserid().toString())) {
					request.setAttribute("result", "新增失败，该手机号已被使用");
					return new ActionForward("/sys/lockrecordclose2.jsp");
				}
			}
			
			
			Users us = new Users();
			
			us.setUserid(userid);
			us.setLoginname(request.getParameter("loginname"));
			us.setPassword(syspassword);
			us.setApprovepwd(sysapprovepwd);
			us.setRealname(request.getParameter("realname"));
			us.setNameen(request.getParameter("nameen"));
//			us.setSex(Integer.valueOf(request.getParameter("sex")));
//			us.setBirthday(DateUtil.StringToDate(request
//					.getParameter("birthday")));
//			us.setBirthday(DateUtil.StringToDate(request.getParameter("birthday")));
//			us.setIdcard(request.getParameter("idcard"));
			us.setMobile(request.getParameter("mobile"));
			us.setOfficetel(request.getParameter("officetel"));
//			us.setHometel(request.getParameter("hometel"));
			us.setEmail(request.getParameter("email"));
//			us.setQq(request.getParameter("qq"));
//			us.setMsn(request.getParameter("msn"));
			us.setProvince(RequestTool.getInt(request, "province"));
			us.setCity(RequestTool.getInt(request, "city"));
			us.setAreas(RequestTool.getInt(request, "areas"));
//			us.setAddr(request.getParameter("addr"));
			us.setCreatedate(DateUtil.getCurrentDate());
			us.setLastlogin(DateUtil.getCurrentDate());
			us.setLogintimes(0);
			us.setMakeorganid(request.getParameter("makeorganid"));
			us.setMakedeptid(RequestTool.getInt(request, "makedeptid"));
			us.setStatus(Integer.valueOf(request.getParameter("status")));
			us.setIsonline(0);
//			us.setIscall(Integer.valueOf(request.getParameter("iscall")));
			if(StringUtil.isEmpty(request.getParameter("validate"))) {
				Calendar now = Calendar.getInstance();
				now.add(Calendar.DAY_OF_YEAR, Constants.PWD_VAL_DATE);
				us.setValidate(now.getTime());
			} else { 
				us.setValidate(RequestTool.getDate(request, "validate"));
			}
			us.setIsCwid(Integer.valueOf(request.getParameter("isCwid")));
			
			//添加用户性质
			if(!StringUtil.isEmpty(userType)){
				us.setUserType(Integer.valueOf(userType));
			}
			
			appUsers.addUsers(us);
			
			addRole(userid);
			//配置默认管辖权限 
		    addSelfRule(us.getMakeorganid(), userid);
			// 许可用户的所属机构的所有下级机构
			addWarehouseVist(us.getMakeorganid(), userid+"");
			//许可用户的所属机构的进货关系客户
			AppOrgan appOrgan = new AppOrgan();
			Organ makeOrgan = appOrgan.getOrganByID(us.getMakeorganid());
			if(makeOrgan.getOrganType()!=null
					&&makeOrgan.getOrganType() == 2
					&&makeOrgan.getOrganModel()!=null
					&& makeOrgan.getOrganModel() == 1) {
				addWarehouseVistForTransRelationOrgan(us.getMakeorganid(), userid+"");
			}
			//添加用户类型
			if(categaries !=null) {
				appUsers.addCategary(us.getUserid(), categaries);
			}
			
			request.setAttribute("result", "databases.add.success");

			UsersBean users = UserManager.getUser(request);
			DBUserLog.addUserLog(users.getUserid(), "系统管理", "用户管理>>新增用户,编号:"+userid);

			return mapping.findForward("addresult");
		} catch (Exception e) {
			logger.error("", e);
			throw e;
		} finally {
		}
	}
	private void addWarehouseVistForTransRelationOrgan(String organId,
			String userId) throws Exception {
		aov.addOrganVisitForTR(organId,userId);
		//增加业务往来仓库
		appWV.addWarehousVisitForTR(organId,userId);
		//更新make_conf
		appmc.updMakeConf("organ_visit","organ_visit");
		appmc.updMakeConf("warehouse_visit","warehouse_visit");
		
	}
	/**
	 * 为所有角色增加记录,方便角色勾选用户
	 */
	private void addRole(Integer userid) throws Exception{
		List list = ar.getRoleList();
		Iterator it = list.iterator();
		UserRole ur = null;
		while (it.hasNext()) {
			ur = new UserRole();
			Role r = (Role) it.next();
			ur.setUserid(userid);
			ur.setIspopedom(0);
			ur.setRoleid(r.getId());
			ar.addUserRole(ur);
		}
	}
	/**
	 * 增加自己机构的管辖权限
	 * @throws Exception 
	 */
	private void addSelfRule(String organId,Integer userid) throws  Exception{
	 	UserVisit uv = new UserVisit();
	    uv.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("user_visit", 0, "")));
	    uv.setUserid(userid);
	    uv.setVisitorgan(organId);
	    uv.setVisitdept("");
	    uv.setVisitusers(String.valueOf(userid));
	    auv.SaveUserVisit(uv);
	      
		//增加管辖仓库
		appRuWH.addRuleWhByOid(organId, userid+"");
		//更新make_conf
		appmc.updMakeConf("RULE_USER_WH","RULE_USER_WH");
		logger.debug("userid[" +  userid + "],增加自己机构[" + organId + "]的管辖权限");
	}
	/**
	 * 增加用户所属机构下的子机构业务往来权限
	 * @throws Exception 
	 */
	private void addWarehouseVist(String parentOrganId,String userid) throws Exception{
		/*List<Organ> childOrganList = appOrgan.getListByParentId(parentOrganId);
		for(Organ childOrgan : childOrganList){
			OrganVisit newov = new OrganVisit();
			Integer id = Integer.valueOf(MakeCode.getExcIDByRandomTableName("organ_visit", 0, ""));
			newov.setId(id);
			newov.setUserid(Integer.valueOf(userid));
			newov.setVisitorgan(childOrgan.getId());
			aov.SaveOrganVisit(newov);
			//增加业务往来仓库
			appWV.addVWByOid(parentOrganId, userid);
			//更新make_conf
			appmc.updMakeConf("warehouse_visit","warehouse_visit");
		}*/
		aov.addOrganVisitByParentOrganId(userid, parentOrganId);
		//增加业务往来仓库
		appWV.addVWByPOid(parentOrganId, userid);
		//更新make_conf
		appmc.updMakeConf("organ_visit","organ_visit");
		appmc.updMakeConf("warehouse_visit","warehouse_visit");
	}
	
}
