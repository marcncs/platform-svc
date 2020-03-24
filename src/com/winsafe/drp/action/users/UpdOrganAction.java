package com.winsafe.drp.action.users;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.winsafe.drp.dao.AppMakeConf;
import com.winsafe.drp.dao.AppOlinkMan;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppOrganVisit;
import com.winsafe.drp.dao.AppRegion;
import com.winsafe.drp.dao.AppRole;
import com.winsafe.drp.dao.AppRuleUserWH;
import com.winsafe.drp.dao.AppUserVisit;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.AppWarehouseVisit;
import com.winsafe.drp.dao.Olinkman;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.OrganForm;
import com.winsafe.drp.dao.OrganVisit;
import com.winsafe.drp.dao.Region;
import com.winsafe.drp.dao.Role;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UserRole;
import com.winsafe.drp.dao.UserVisit;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.keyretailer.dao.AppSBonusAccount;
import com.winsafe.drp.keyretailer.dao.AppSBonusAppraise;
import com.winsafe.drp.keyretailer.pojo.SBonusAccount;
import com.winsafe.drp.keyretailer.pojo.SBonusAppraise;
import com.winsafe.drp.metadata.DealerType;
import com.winsafe.drp.metadata.OrganType;
import com.winsafe.drp.server.AfficheService;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Encrypt;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;
import com.winsafe.hbm.util.StringUtil;

public class UpdOrganAction extends Action {
	
	private static Logger logger = Logger.getLogger(UpdOrganAction.class);
	private AppSBonusAppraise appSBonusAppraise = new AppSBonusAppraise();
	private AppUserVisit auv = new AppUserVisit();
	private AppOrganVisit aov = new AppOrganVisit();
	private AppWarehouseVisit appWV = new AppWarehouseVisit();
	private AppMakeConf appmc = new AppMakeConf();
	private AppRuleUserWH appRuWH = new AppRuleUserWH();
	private AppRole ar = new AppRole();
	private AppOrgan appOrgan = new AppOrgan();
	private AppWarehouse awr = new AppWarehouse();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		String parentid = request.getParameter("parentid");

		String salemainid = request.getParameter("saleManId");
		String organType = request.getParameter("organType");
		String organModel = request.getParameter("organModel");
		String regionid=request.getParameter("RegionId");
		//获取区域信息
		String regionareaid=request.getParameter("regionareaid");
		String regionarea=request.getParameter("regionarea");
		
		Users user= null;
		
		/*Integer validatestatus= null;
		if(!StringUtil.isEmpty(request.getParameter("validatestatus"))) {
			validatestatus = Integer.parseInt(request.getParameter("validatestatus"));
		}*/

		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		OrganForm organf = (OrganForm) form;
		try {
			String strlinkmobile[] = request.getParameterValues("linkmobile");
			if(!strlinkmobile[0].equals("13500000000")){
				if(appOrgan.isUsersExists(strlinkmobile[0], id)) {
					request.setAttribute("result", "更新失败,已存在手机号为["+strlinkmobile[0]+"]的机构!");
					return new ActionForward("/sys/lockrecord2.jsp");
				}
			}
			
			
			AppOrgan ao = new AppOrgan();
			AppUsers appusers = new AppUsers();
			AppOlinkMan appol = new AppOlinkMan();
			AppRegion appregion=new AppRegion();
			AppSBonusAccount acc = new AppSBonusAccount();
			Organ d = ao.getOrganByID(id);

			Organ oldOrgan = (Organ) BeanUtils.cloneBean(d);

			Organ parentOrgan = null;
			if (!d.getParentid().equals("0")) {
				parentOrgan = ao.getOrganByID(parentid);
				String sysid = ao.getOcodeByParent(parentOrgan.getSysid());
				ao.updSysid(d.getSysid(), sysid);
				d.setSysid(sysid);
				if (parentid == null) {
					parentid = "0";
					d.setRank(0);
				} else {
					d.setRank(ao.getOrganByID(parentid).getRank() + 1);
				}
				d.setParentid(parentid);
			}
			
			if (StringUtil.isEmpty(request.getParameter("isKeyRetailer"))) {
				d.setIsKeyRetailer(-1);
			} else {
				d.setIsKeyRetailer(Integer.parseInt(request.getParameter("isKeyRetailer")));
			}
			
			d.setOrganname(request.getParameter("organname"));
			d.setOtel(request.getParameter("otel"));
			d.setOmobile(request.getParameter("omobile"));
			d.setOfax(request.getParameter("ofax"));
			d.setOemail(request.getParameter("oemail"));
			d.setOecode(request.getParameter("oecode"));
			d.setRate(RequestTool.getInt(request, "rate"));
			d.setPrompt(RequestTool.getInt(request, "prompt"));
			d.setPprompt(RequestTool.getInt(request, "pprompt"));
			d.setPaycondition(request.getParameter("paycondition"));
			d.setProvince(RequestTool.getInt(request, "province"));
			d.setCity(RequestTool.getInt(request, "city"));
			d.setAreas(RequestTool.getInt(request, "areas"));
			d.setOaddr(request.getParameter("oaddr"));
			d.setShortname(request.getParameter("shortname"));
			
			d.setLicense(request.getParameter("license"));
			d.setCustomerlevel(request.getParameter("customerlevel"));
			
			d.setRegionareaid(request.getParameter("regionareaid"));
			d.setRegionarea(request.getParameter("regionarea"));
			d.setModificationTime(new Date());			
			// 添加销售员
			d.setSalemanId(salemainid);
			//添加主管所属的区域信息
			d.setRegionareaid(regionid);
			// 添加机构类型
			d.setOrganType(StringUtil.getInt(organType, null));
			//添加机构类别
			d.setOrganModel(StringUtil.getInt(organModel, null));
			//添加区域信息
			if(regionareaid!=null && regionareaid.length() >0){
			      Region rg=	appregion.getRegionByCode(regionareaid);
			      if(rg!=null){
			    	  if(rg.getTypeid().equals("1")){
			    		  d.setBigRegionId(Integer.valueOf(regionareaid));
			    		  d.setBigRegionName(regionarea);
			    		  d.setOfficeId(null);
			    		  d.setOfficeName(null);
			    	  }
			    	  if(rg.getTypeid().equals("2")){
			    		  d.setOfficeId(Integer.valueOf(regionareaid));
			    		  d.setOfficeName(regionarea);
			    		  d.setBigRegionId(Integer.valueOf(rg.getParentid()));
			    		  d.setBigRegionName(rg.getParentname());
			    	  }
			      }
			}
			
			OrganService os = new OrganService();
			
			if(OrganType.Dealer.getValue().equals(d.getOrganType())
					&&  !DealerType.PD.getValue().equals(d.getOrganModel())) {
				if("1".equals(request.getParameter("isvalidate"))
						&& "1".equals(request.getParameter("validatestatus"))) {
					d.setValidatestatus(1);
					d.setValidateuserid(users.getUserid());
					d.setValidatedate(DateUtil.getCurrentDate());
				} else {
					d.setValidatestatus(0);
					d.setValidateuserid(null);
					d.setValidatedate(null);
					AfficheService as = new AfficheService();
					//新增消息
					as.addUpdateAuditRequestAffiche(d);
				}
			} else {
				d.setValidatestatus(null);
				d.setValidateuserid(null);
				d.setValidatedate(null);
			}
			
			if (d.getIsKeyRetailer() == 1) {
				String str = "";
				if (d.getOrganModel() == DealerType.BKD.getValue()) {
					str = DealerType.BKD.getName() + d.getId();
				} else {//if (d.getOrganModel() == DealerType.BKR.getValue()) {
					str = DealerType.BKR.getName() + d.getId();
				}
				SBonusAccount sac = acc.getByOrganId(d.getId());
				if (sac == null) {
					SBonusAccount s = new SBonusAccount();
					s.setName(str);
					s.setCompanyName(d.getOrganname());
					s.setMobile(d.getOmobile());
					s.setActiveFlag(1);
					s.setOrganId(d.getId());
					s.setAreas(d.getAreas());
					s.setProvince(d.getProvince());
					s.setCity(d.getCity());
					s.setAddress(d.getOaddr());
					acc.addSBonusAccount(s);
					
					//添加积分账号信息
					SBonusAppraise sBonusAppraise = new SBonusAppraise();
					sBonusAppraise.setAccountId(s.getAccountId());
					sBonusAppraise.setOrganId(d.getId());
					sBonusAppraise.setBonusTarget(0d);
					sBonusAppraise.setAppraise(0d);
					sBonusAppraise.setActualPoint(0d);
					sBonusAppraise.setBonusPoint(0d);
					sBonusAppraise.setModifiedDate(DateUtil.getCurrentDate());
					sBonusAppraise.setPeriod(Integer.parseInt(DateUtil.getCurrentYear()));
					appSBonusAppraise.addSBonusAppraise(sBonusAppraise); 
					
				}
				/*List list = appusers.getUsersByMakeOrganId(d.getId());
				if (list == null || list.size() == 0) {
					user = new Users();
					Integer uid = Integer.valueOf(MakeCode.getExcIDByRandomTableName("users", 0, ""));
					user.setUserid(uid);
					user.setLoginname(str);
					user.setPassword(Encrypt.getSecret(str, 1));
					user.setApprovepwd(Encrypt.getSecret("aaaa", 1));
					user.setStatus(1);
					user.setMakeorganid(d.getId());
					user.setCreatedate(new Date());
					user.setAreas(d.getAreas());
					user.setProvince(d.getProvince());
					user.setCity(d.getCity());
					user.setMobile(d.getOmobile());
					appusers.InsertUsers(user);
					
					addRole(uid);
					//配置默认管辖权限
				    addSelfRule(user.getMakeorganid(), uid);
					// 许可用户的所属机构的所有下级机构
					addWarehouseVist(user.getMakeorganid(), uid+"");
				}*/
			} else if (d.getIsKeyRetailer() == 0) {
				
				//不是关键的话，就设置为否
				SBonusAccount sac = acc.getByOrganId(d.getId());
				if(sac !=null) {
					sac.setActiveFlag(0);
					acc.updSBonusAccount(sac);
					
					/*List<Users> list = appusers.getUsersByMakeOrganId(d.getId());
					if (list != null) {
						for (Users i : list) {
							appusers.delUsersById(i.getUserid());
						}
					}*/
				}
			}
			d.setOmobile(strlinkmobile[0]);
			d.setIsNeedApprove(RequestTool.getInt(request, "isNeedApprove"));
			os.updOrgan(d);
			
			appol.delOlinkmanByCid(d.getId());

			String strname[] = RequestTool.getStrings(request, "name");
			int strsex[] = RequestTool.getInts(request, "sex");
			//String idcard[] = RequestTool.getStrings(request, "idcard");
			//Date strbirthday[] = RequestTool.getDates(request, "birthday");
			//String department[] = RequestTool.getStrings(request, "department");
			String sttduty[] = request.getParameterValues("duty");
			String strlinkofficetel[] = request.getParameterValues("linkofficetel");
			//String strhometel[] = request.getParameterValues("hometel");
			String strlinkemail[] = request.getParameterValues("linkemail");
			String strqq[] = request.getParameterValues("qq");
			//String strmsn[] = request.getParameterValues("msn");
			String strlkaddr[] = request.getParameterValues("lkaddr");
			int strismain[] = RequestTool.getInts(request, "ismain");
			Olinkman linkman = null;
			for (int i = 0; i < strname.length; i++) {
				if (!"".equals(strname[i])) {
					linkman = new Olinkman();
					linkman.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("olinkman", 0, "")));
					linkman.setCid(d.getId());
					linkman.setName(strname[i]);
					linkman.setSex(strsex[i]);
//					linkman.setIdcard(idcard[i]);
//tommy 					linkman.setBirthday(strbirthday[i]);
					linkman.setDuty(sttduty[i]);
					linkman.setOfficetel(strlinkofficetel[i]);
					linkman.setMobile(strlinkmobile[i]);
					//linkman.setHometel(strhometel[i]);
					linkman.setEmail(strlinkemail[i]);
					linkman.setQq(strqq[i]);
					//linkman.setMsn(strmsn[i]);
					linkman.setAddr(strlkaddr[i]);
					linkman.setIsmain(strismain[i]);
				   // linkman.setTransit(vc.getTransit()[i]);
					linkman.setMakeorganid(users.getMakeorganid());
					linkman.setMakedeptid(users.getMakedeptid());
					linkman.setMakeid(userid);
					linkman.setMakedate(DateUtil.getCurrentDate());
					appol.addOlinkman(linkman);
				}
			}
			FormFile imgFile = (FormFile) organf.getLogo();
			String fileName = imgFile.getFileName().toLowerCase();

			String filePath = request.getRealPath("/");
			String realpathname = "";
			InputStream fis = null;
			OutputStream fos = null;
			if (fileName != null && !fileName.equals("")) {
				try {
					fis = imgFile.getInputStream();
					realpathname = "images\\logo\\" + fileName;

					fos = new FileOutputStream(filePath + realpathname);

					byte[] buffer = new byte[1048576];
					int bytesRead = 0;
					while ((bytesRead = fis.read(buffer, 0, 8192)) != -1) {

						fos.write(buffer, 0, bytesRead);
					}
					d.setLogo(realpathname);
					os.updOrgan(d);
				} catch (Exception e) {
					logger.error("添加机构LOGO时发生异常：", e);
				} finally {
					if(fos != null) {
						fos.close();
					}
					if(fis != null) {
						fis.close();
					}
				}
				
			}
			

		   //修改下级机构的信息
//			List list=ao.getTreeByCate(d.getId());
//			if(list!=null && !list.isEmpty()){
//				for(int i=0;i<list.size();i++){
//					Organ o=(Organ) list.get(i);
//					 if(d.getBigRegionId()!=null ){
//						 o.setBigRegionId(d.getBigRegionId());
//						 o.setBigRegionName(d.getBigRegionName());
//					 }
//					 if(d.getOfficeId()!=null){
//						 o.setOfficeId(d.getOfficeId());
//						 o.setOfficeName(d.getOfficeName());
//						 o.setSalemanId(null);
//					 }
//				}
//			}
			
			request.setAttribute("result", "databases.upd.success");
			DBUserLog.addUserLog(userid, "系统管理", "修改机构资料" + d.getId(), oldOrgan, d);

			return mapping.findForward("updResult");
		} catch (Exception e) {
			logger.error("更新机构时发生异常：", e);
			request.setAttribute("result", "databases.upd.fail");

		} finally {

		}
		return mapping.findForward("updResult");
	}
	
	/**
	 * 新增业务往来权限
	 * @throws Exception 
	 */
	private void addOrganVisit(Integer uid,String oid) throws Exception{
		OrganVisit newov = new OrganVisit();
		Integer id = Integer.valueOf(MakeCode.getExcIDByRandomTableName("organ_visit", 0, ""));
		newov.setId(id);
		newov.setUserid(Integer.valueOf(uid));
		newov.setVisitorgan(oid);
		aov.SaveOrganVisit(newov);
		//增加业务往来仓库
		appWV.addVWByOid(oid, uid+"");
		//更新make_conf
	
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
		List<Organ> childOrganList = appOrgan.getListByParentId(parentOrganId);
		for(Organ childOrgan : childOrganList){
			OrganVisit newov = new OrganVisit();
			Integer id = Integer.valueOf(MakeCode.getExcIDByRandomTableName("organ_visit", 0, ""));
			newov.setId(id);
			newov.setUserid(Integer.valueOf(userid));
			newov.setVisitorgan(childOrgan.getId());
			aov.SaveOrganVisit(newov);
			//增加业务往来仓库
			appWV.addVWByOid(childOrgan.getId(), userid);
			//更新make_conf
			appmc.updMakeConf("warehouse_visit","warehouse_visit");
		}
	}
}
