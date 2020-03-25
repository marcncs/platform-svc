package com.winsafe.drp.action.sys;

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

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppRole;
import com.winsafe.drp.dao.AppUserVisit;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Role;
import com.winsafe.drp.dao.UserRole;
import com.winsafe.drp.dao.UserVisit;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.UsersUploadForm;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.hbm.util.Encrypt;
import com.winsafe.hbm.util.MakeCode;

public class ImportRegionUserAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		int CCount = 0, ECount = 0;
		String errorMsg = "";
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
				Workbook wb = Workbook.getWorkbook(usersfile.getInputStream());
				Sheet sheet = wb.getSheet(0);
				int row = sheet.getRows();

				AppRole  ar = new AppRole();
				Role  role=null;
				
				AppUsers appuser = new AppUsers();
				Users user = null;
				
				AppUserVisit app = new AppUserVisit();

				for (int i = 1; i < row; i++) {
					user = new Users();
					Integer userid = Integer.valueOf(MakeCode.getExcIDByRandomTableName("users", 0, ""));
					// 员工编号
					String usercode = sheet.getCell(0, i).getContents();
					if (usercode == null || "".equals(usercode)) {
						errorMsg += "<br/>第[" + (i + 1) + "]行：用户编号不能为空！";
						ECount++;
						continue;
					}
					if(usercode.length()>60){
						usercode=usercode.substring(0, 32);
					}
				    Users u=appuser.getUsers(usercode);
				    if(u!=null ){
				    	errorMsg += "<br/>第[" + (i + 1) + "]行："+usercode+"用户已存在！";
						ECount++;
						continue;
				    }
					// 用户姓名
					String username = sheet.getCell(1, i).getContents();
					if (username == null || "".equals(username)) {
						errorMsg += "<br/>第[" + (i + 1) + "]行：用户姓名不能为空！";
						ECount++;
						continue;
					}
					if(username.length() > 64){
						username.substring(0, 32);
					}
					// 用户类型
					String  usertype = sheet.getCell(2, i).getContents();
					Integer usertypeid=null;
					if("大区经理".equals(usertype.trim())){
						usertypeid=5;
					}
					else if("办事处经理".equals(usertype.trim())){
						usertypeid=4;
					}
					else if("主管".equals(usertype.trim())){
						usertypeid=3;
					}else{
						errorMsg += "<br/>第[" + (i + 1) + "]行：用户类型不符合！";
						ECount++;
						continue;
					}
					
					String syspassword = Encrypt.getSecret("a123456", 1);
					String sysapprovepwd = Encrypt.getSecret("aaaa", 1);
					
					user.setUserid(userid);
					
					user.setPassword(syspassword);
					user.setApprovepwd(sysapprovepwd);
					user.setLoginname(usercode);
					user.setRealname(username);
					user.setUserType(usertypeid);
					user.setIdcard("");
					user.setStatus(1);
					user.setSex(2);//默认值
					user.setCreatedate(Dateutil.getCurrentDate());
					user.setProvince(110000); //省份的默认值
					user.setCity(110100);     //城市的默认值
					user.setAreas(110101);    //地区的默认值
					//添加制单机构
					user.setMakeorganid(users.getMakeorganid());
					appuser.InsertUsers(user);
					
					//添加角色信息
					List roleList= ar.getRoleList();
					UserRole ur=null;
					for( Iterator it= roleList.iterator(); it.hasNext();){
						ur=new UserRole();
						role=(Role) it.next();
						ur.setUserid(userid);
						ur.setIspopedom(0);
						ur.setRoleid(role.getId());
						ar.addUserRole(ur);
					}
					//添加默认机构信息
					UserVisit newuv = new UserVisit();
					Integer id = Integer.valueOf(MakeCode.getExcIDByRandomTableName("User_Visit", 0, ""));
					newuv.setId(id);
					newuv.setUserid(userid);
					newuv.setVisitusers(String.valueOf(userid));
					newuv.setVisitorgan("00000002"); //
					app.SaveUserVisit(newuv);
					
					CCount++;
				}
				wb.close();
			} else {
				request.setAttribute("result", "上传文件失败,请重试");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			request.setAttribute("result", "上传用户资料完成,本次总共添加 :"
					+ (CCount + ECount) + "条! 成功:" + CCount + "条! 失败：" + ECount
					+ "条!" + errorMsg);
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result", "上传文件失败,文件格式不对,请重试");
			return new ActionForward("/sys/lockrecord2.jsp");
		}

	}

}
