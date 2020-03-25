package com.winsafe.drp.action.users;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppRole;
import com.winsafe.drp.dao.AppRuleUserWH;
import com.winsafe.drp.dao.AppUserVisit;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.AppWarehouseVisit;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Role;
import com.winsafe.drp.dao.RuleUserWh;
import com.winsafe.drp.dao.UserRole;
import com.winsafe.drp.dao.UserVisit;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.UsersUploadForm;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.dao.WarehouseVisit;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.util.Constants;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Encrypt;
import com.winsafe.hbm.util.MakeCode;

public class ImportUsersAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		int CCount = 0, ECount = 0;
		try {
			UsersUploadForm mf = (UsersUploadForm) form;
			FormFile usersfile = (FormFile) mf.getUsrsfile();
			boolean bool = false;
			if (usersfile != null && !usersfile.equals("")) {

				if (usersfile.getContentType() != null) {
					if (usersfile.getFileName().indexOf("xls") >= 0) {
						bool = true;
					}
				}
			}
			if (bool) {
				String syspassword = Encrypt.getSecret("a123456", 1);
				String sysapprovepwd = Encrypt.getSecret("aaaa", 1);
				Workbook wb = Workbook.getWorkbook(usersfile.getInputStream());
				Sheet sheet = wb.getSheet(0);
				int row = sheet.getRows();
				//CountryAreaService countApp = new CountryAreaService();
				//OrganService app = new OrganService();
				AppOrgan ao= new AppOrgan();
				UsersService appUsers = new UsersService();
				AppRuleUserWH appRuleUserWH = new AppRuleUserWH();
				AppUserVisit auv = new AppUserVisit();
				AppWarehouse awh = new AppWarehouse();
				AppRole ar = new AppRole();
				AppWarehouseVisit appwv = new AppWarehouseVisit();
				//AppOrganScan appos = new AppOrganScan();
				//AppMakeConf appm = new AppMakeConf();
				//MakeConf mc = appm.getMakeConfByID("organ");
				Organ o = null;
				Users user = null;
				UserVisit uv = null;
				//Warehouse wh = null;
				for (int i = 2; i < row; i++) {
					
					try {
						user = new Users();
						//用户id
						Integer userid = Integer.valueOf(MakeCode.getExcIDByRandomTableName("users", 0, ""));
						//登录用户账号
						String uid = sheet.getCell(0, i).getContents();
						if(uid==null || "".equals(uid)){
							ECount++;
							continue;
						}
						//登录名称
						//String realname = sheet.getCell(1, i).getContents();
						//通过用户获取机构信息
						//o = ao.getOrganByOldsyscode(uid);
						Users u = appUsers.getUsers(uid.trim());
						//判断当前是否存在用户
						if(u!=null){
							// 用户存在
							ECount++;
							continue;
						}else {
							//用户不存在
							String oid = uid;
							// 判断机构是否存在
							List<Organ> organs = ao.getByOecode2(oid);
							// 当查询结果为多个机构时则不导入
							if(organs != null && organs.size()==1){
								o = organs.get(0);
							}else {
								ECount++;
								continue;
							}
							if(o != null){
								//机构存在,则新建用户
								user.setUserid(userid);
								user.setLoginname(uid);
								user.setPassword(syspassword);
								user.setApprovepwd(sysapprovepwd);
								user.setRealname("none");
								user.setProvince(o.getProvince());
								user.setCity(o.getCity());
								user.setAreas(o.getAreas());
								user.setAddr(o.getOaddr());
								user.setMakeorganid(o.getId());
								user.setSex(2);
								user.setCreatedate(DateUtil.getCurrentDate());
								user.setLastlogin(DateUtil.getCurrentDate());
								user.setLogintimes(0);
								user.setStatus(1);
								user.setIsonline(0);
								user.setIslogin(1);
								user.setIscall(0);
								user.setIdcard("");
								
								List list = ar.getRoleList();
								Iterator it = list.iterator();
								UserRole ur = null;
								while (it.hasNext()) {
									ur = new UserRole();
									Role r = (Role) it.next();
									ur.setUserid(userid);
									ur.setRoleid(r.getId());
//								if(r.getId()==Constants.ROLE_ID){
									if(r.getId()==10028){ //10028为经销商角色
										ur.setIspopedom(1);
									}else{
										ur.setIspopedom(0);
									}
									 ar.addUserRole(ur);
								}
								
								appUsers.InsertUsers(user);
								
								//添加UserVisit(管辖机构)
								 uv=new UserVisit();
								 uv.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("user_visit", 0, "")));
								 uv.setUserid(userid);
								 uv.setVisitorgan(user.getMakeorganid());
								 uv.setVisitdept("");
								 uv.setVisitusers(String.valueOf(userid));
								 auv.SaveUserVisit(uv);
								  
								//添加RuleUserWh(管辖仓库)
								 //通过机构获取仓库信息
								List<Warehouse> whs = awh.getWarehouseListByOID(o.getId());
								for(Warehouse wh : whs){
								     RuleUserWh ruw = null;
								     
								     ruw = new RuleUserWh();
						    		 ruw.setWarehouseId(wh.getId());
						    		 ruw.setUserId(userid);
						    		 ruw.setActiveFlag(true);
						    		 appRuleUserWH.addRuleUserWh(ruw);
						    		 
						    		 /*WarehouseVisit wv = null;
						    		 wv = new WarehouseVisit();
						    		 wv.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("warehouse_visit",0,"")));
						    		 wv.setWid(wh.getId());
						    		 wv.setUserid(userid);
						    		 appwv.addWarehouseVisit(wv);*/
								}
								CCount++;
								
							}else{
								//机构不存在
								ECount++;
								continue;
							}
							
						}
						HibernateUtil.commitTransaction();
					} catch (Exception e) {
						e.printStackTrace();
						ECount++;
						HibernateUtil.rollbackTransaction();
					}
				}
				wb.close();
			} else {
				request.setAttribute("result", "上传文件失败,请重试");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			request.setAttribute("result", "上传用户资料成功,本次总共添加 :"
					+ (CCount + ECount) + "条! 成功:" + CCount + "条! 失败：" + ECount
					+ "条!");
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result", "上传文件失败,文件格式不对,请重试");
			return new ActionForward("/sys/lockrecord2.jsp");
		}
	}
}
