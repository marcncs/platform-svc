package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.winsafe.drp.dao.AppRegion;
import com.winsafe.drp.dao.AppRegionUsers;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Region;
import com.winsafe.drp.dao.RegionUsers;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.UsersUploadForm;

public class ImportRegionAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
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

				AppRegion appregion = new AppRegion();
				AppRegionUsers appregionuser = new AppRegionUsers();
				AppUsers appuser = new AppUsers();

				Region region = null;
				Region regionoffice = null;
				RegionUsers regionUsers = null;
				Users user = null;

				for (int i = 1; i < row; i++) {
					region = new Region();// 添加大区
					regionoffice = new Region(); // 添加办事处
					regionUsers = new RegionUsers();
					// 大区名称
					String regioncode = sheet.getCell(0, i).getContents();
					if (regioncode == null || "".equals(regioncode.trim())) {
						errorMsg += "<br/>第[" + (i + 1) + "]行:大区名称不能为空！";
						ECount++;
						continue;
					}
					if (regioncode.trim().length() > 32) {
						regioncode = regioncode.substring(0, 32);
					}
					// 大区经理编号
					String regionManagerId = sheet.getCell(1, i).getContents();
					if (regionManagerId == null|| "".equals(regionManagerId.trim())) {
						errorMsg += "<br/>第[" + (i + 1) + "]行:大区经理编号为空!";
					}
					if (  regionManagerId!=null && regionManagerId.trim().length() > 32) {
						regionManagerId = regionManagerId.substring(0, 32);
					}
					// 办事处名称
					String officeName = sheet.getCell(2, i).getContents();
					if (officeName == null || "".equals(officeName.trim())) {
						errorMsg += "<br/>第[" + (i + 1) + "]行:办事处名称不能为空！";
						ECount++;
						continue;
					}
					if (officeName.trim().length() > 32) {
						officeName = officeName.substring(0, 32);
					}
					// 办事处经理
					String officeManagerID = sheet.getCell(3, i).getContents();
					if (officeManagerID == null|| "".equals(officeManagerID.trim())) {
						errorMsg += "<br/>第[" + (i + 1) + "]行:办事处经理编号为空!";
					}
					if ( officeManagerID!=null && officeManagerID.trim().length() > 32) {
						officeManagerID = officeManagerID.substring(0, 32);
					}
					// 主管编号
					String leaderId = sheet.getCell(4, i).getContents();
					if (leaderId == null || "".equals(leaderId)) {
						errorMsg += "<br/>第[" + (i + 1) + "]行:主管编号不能为空！";
					}
					if (leaderId!=null && leaderId.length() > 32) {
						leaderId = leaderId.substring(0, 32);
					}
					// ----------------------判别大区和办事处的是否存在---------------------------------//--------
					AppRegion aa = new AppRegion();
					// 大区不允许重复
					Region r = aa.getRegionBySortName(regioncode);
					// 办事处不允许重复
					Region roffice = aa.getRegionBySortName(officeName);
					if (r != null && roffice != null) {
						  //添加主管信息
						regionUsers.setRid(roffice.getId());
						regionUsers.setRname("办事处");
						user = appuser.getUsers(leaderId);
						RegionUsers leaderRegion = null;
						if (user != null) {
								leaderRegion = appregionuser.getRegionUsersByUserid(String.valueOf(user.getUserid()));
								if(leaderRegion==null){
									regionUsers.setUserid(user.getUserid().toString());
									regionUsers.setUsername(user.getRealname());
									regionUsers.setUserlogin(leaderId);
								}else{
									errorMsg += "<br/>第[" + (i + 1) + "]行:该主管已经存在关联！";
									ECount++;
									continue;
								}
						}else if(user == null) {
							errorMsg += "<br/>第[" + (i + 1) + "]行:主管编号有误！请核查";
							ECount++;
							continue;
						}
						appregionuser.insertRegionUser(regionUsers);
						
					}
					if (r != null && roffice == null) {
						// 添加办事处code
						String acodeoffice = aa.getAcodeByParent(r.getRegioncode());
						regionoffice.setRegioncode(acodeoffice);
						regionoffice.setSortname(officeName);
						regionoffice.setParentid(r.getRegioncode());
						regionoffice.setParentname(r.getSortname());

						regionoffice.setParentuserid(r.getUserid());
						regionoffice.setParentusername(r.getUsername());
						user = appuser.getUsers(officeManagerID);
						Region officeRegion = null;
						if (user != null) {
								officeRegion = appregion.getRegionByUserid(String.valueOf(user.getUserid()));
								if(officeRegion==null){
									regionoffice.setUserid(user.getUserid().toString());
									regionoffice.setUsername(user.getRealname());
								}else{
									errorMsg += "<br/>第[" + (i + 1) + "]行:办事处经理已经关联！";
									//ECount++;
								    //continue;
								}
						}else if(user == null) {
							errorMsg += "<br/>第[" + (i + 1) + "]行:办事处经理编号有误！请核查";
						//	ECount++;
						//	continue;
						}
						regionoffice.setTypeid("2");
						regionoffice.setTypename("办事处");
						// appregion.flash(r);
						appregion.saveRegion(regionoffice);

						// 关联主管
						regionUsers.setRid(regionoffice.getId());
						regionUsers.setRname("办事处");

						user = appuser.getUsers(leaderId);
						RegionUsers leaderRegion = null;
						if (user != null) {
								leaderRegion = appregionuser.getRegionUsersByUserid(String.valueOf(user.getUserid()));
								if(leaderRegion==null){
									regionUsers.setUserid(user.getUserid().toString());
									regionUsers.setUsername(user.getRealname());
									regionUsers.setUserlogin(leaderId);
								}else{
									errorMsg += "<br/>第[" + (i + 1) + "]行:主管已经关联！";
									ECount++;
									continue;
								}
						}else if(user == null) {
							errorMsg += "<br/>第[" + (i + 1) + "]行:主管编号有误！请核查";
							ECount++;
							continue;
						}
						appregionuser.insertRegionUser(regionUsers);

					} else if (r == null && roffice != null) {
						// 添加大区
						// 获取父区域的name
						Region parent = aa.getRegionById("1");
						// 获取子区域的code
						String acode = aa.getAcodeByParent("1");
						// 顶级的 name:
						region.setRegioncode(acode);
						region.setSortname(regioncode);
						region.setParentid(parent.getRegioncode());
						region.setParentname(parent.getSortname());

						user = appuser.getUsers(regionManagerId);
						Region managerRegion = null;
						if (user != null) {
							managerRegion = appregion.getRegionByUserid(String.valueOf(user.getUserid()));
							if (managerRegion == null) {
								region.setUserid(user.getUserid().toString());
								region.setUsername(user.getRealname());
							}else{
								errorMsg += "<br/>第[" + (i + 1) + "]行:大区经理已经存在关联！";
							}
						}
						else if (user == null) {
							errorMsg += "<br/>第[" + (i + 1) + "]行:大区经理编号有误！请核查";
						}
						region.setTypeid("1");
						region.setTypename("大区");
						appregion.saveRegion(region);
					} else  if(r == null && roffice == null){
						// 添加大区
						// 获取父区域的name
						Region parent = aa.getRegionById("1");
						// 获取子区域的code
						String acode = aa.getAcodeByParent("1");
						// 顶级的 name:
						region.setRegioncode(acode);
						region.setSortname(regioncode);
						region.setParentid(parent.getRegioncode());
						region.setParentname(parent.getSortname());

						Users uus = appuser.getUsers(regionManagerId);
						Region managerRegion = null;
						if (uus != null) {
							managerRegion = appregion.getRegionByUserid(String.valueOf(uus.getUserid()));
							if (managerRegion == null) {
								region.setUserid(uus.getUserid().toString());
								region.setUsername(uus.getRealname());
							}else{
								errorMsg += "<br/>第[" + (i + 1) + "]行：大区经理已经存在关联！";
							}
						}
						else if (uus == null) {
							errorMsg += "<br/>第[" + (i + 1) + "]行：大区经理编号有误！请核查";
						}
						region.setTypeid("1");
						region.setTypename("大区");
						appregion.saveRegion(region);

						// 添加办事处
						// 获取子区域的code
						String acodeoffice = aa.getAcodeByParent(region.getRegioncode());

						regionoffice.setRegioncode(acodeoffice);
						regionoffice.setSortname(officeName);
						regionoffice.setParentid(region.getRegioncode());
						regionoffice.setParentname(region.getSortname());

						// 添加父级标签
						regionoffice.setParentuserid(region.getUserid());
						regionoffice.setParentusername(region.getUsername());

						regionoffice.setTypeid("2");
						regionoffice.setTypename("办事处");
						Users us = appuser.getUsers(officeManagerID);
						Region officeRegion = null;
						if (us != null) {
								officeRegion = appregion.getRegionByUserid(String.valueOf(us.getUserid()));
								if(officeRegion==null){
									regionoffice.setUserid(us.getUserid().toString());
									regionoffice.setUsername(us.getRealname());
								}else{
									errorMsg += "<br/>第[" + (i + 1) + "]行:办事处经理已经存在关联！";
								//	ECount++;
								//	continue;
								}
						}
						else if(us == null) {
							errorMsg += "<br/>第[" + (i + 1) + "]行:办事处经理编号有误！请核查";
						//	ECount++;
						//	continue;
						}
						appregion.saveRegion(regionoffice);
						// 关联主管
						regionUsers.setRid(regionoffice.getId());
						regionUsers.setRname("办事处");
						user = appuser.getUsers(leaderId);
						RegionUsers leaderRegion = null;
						if (user != null) {
								leaderRegion = appregionuser.getRegionUsersByUserid(String.valueOf(user.getUserid()));
								if(leaderRegion==null){
									regionUsers.setUserid(user.getUserid().toString());
									regionUsers.setUsername(user.getRealname());
									regionUsers.setUserlogin(leaderId);
								}else{
									errorMsg += "<br/>第[" + (i + 1) + "]行:主管已经存在关联！";
									ECount++;
									continue;
								}
						}
						else if(user == null) {
							errorMsg += "<br/>第[" + (i + 1) + "]行:主管编号有误！请核查";
							ECount++;
							continue;
						}
						appregionuser.insertRegionUser(regionUsers);
					}
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
