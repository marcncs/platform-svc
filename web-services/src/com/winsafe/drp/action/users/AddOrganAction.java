package com.winsafe.drp.action.users;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppMakeConf;
import com.winsafe.drp.dao.AppOlinkMan;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppOrganProduct;
import com.winsafe.drp.dao.AppOrganScan;
import com.winsafe.drp.dao.AppOrganVisit;
import com.winsafe.drp.dao.AppRole;
import com.winsafe.drp.dao.AppRuleUserWH;
import com.winsafe.drp.dao.AppUserVisit;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.AppWarehouseVisit;
import com.winsafe.drp.dao.Olinkman;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.OrganForm;
import com.winsafe.drp.dao.OrganVisit;
import com.winsafe.drp.dao.Role;
import com.winsafe.drp.dao.UserRole;
import com.winsafe.drp.dao.UserVisit;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.dao.WarehouseBit;
import com.winsafe.drp.keyretailer.dao.AppSBonusAccount;
import com.winsafe.drp.keyretailer.dao.AppSBonusAppraise;
import com.winsafe.drp.keyretailer.dao.AppSTransferRelation;
import com.winsafe.drp.keyretailer.pojo.SBonusAccount;
import com.winsafe.drp.keyretailer.pojo.SBonusAppraise;
import com.winsafe.drp.keyretailer.pojo.STransferRelation;
import com.winsafe.drp.metadata.DealerType;
import com.winsafe.drp.metadata.OrganType;
import com.winsafe.drp.server.AfficheService;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.BeanCopy;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Encrypt;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;
import com.winsafe.hbm.util.StringUtil;

public class AddOrganAction extends BaseAction {
	private static Logger logger = Logger.getLogger(AddOrganAction.class);
	
	private OrganService ao = new OrganService();
	private AppOlinkMan appol = new AppOlinkMan();//联系人
	private AppOrganScan appos = new AppOrganScan();
	private AppOrganProduct aop = new AppOrganProduct();
	private AppMakeConf appmc = new AppMakeConf();
	private WarehouseService aw = new WarehouseService();
	private AppUserVisit auv = new AppUserVisit();
	private AppWarehouse appWarehouse = new AppWarehouse();
	private AppRuleUserWH appRuWH = new AppRuleUserWH();
	private AppOrganVisit aov = new AppOrganVisit();
	private AppWarehouseVisit appWV = new AppWarehouseVisit();
	private AppSBonusAccount acc = new AppSBonusAccount();
	private AppSBonusAppraise appSBonusAppraise = new AppSBonusAppraise(); 
	private UsersService userService = new UsersService();
	private AppRole ar = new AppRole();
	private AppOrgan appOrgan = new AppOrgan();
	private AppSTransferRelation astr = new AppSTransferRelation();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		initdata(request);
		
		try {
			OrganForm organf = (OrganForm) form;
			//销售员信息
			String saleManId=request.getParameter("saleManId");
			String id = request.getParameter("id");//机构编号
			Organ co = ao.getOrganByID(id.trim());
			if (co != null) {
				request.setAttribute("result", co.getId() + "此机构编号已经，请重新录入!");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			String parentid = request.getParameter("parentid");
			Organ parentOrgan = ao.getOrganByID(parentid);//上级机构
			String sysid=null;
			if(parentOrgan!=null&&!"".equals(parentOrgan )){
				 sysid = ao.getOcodeByParent(parentOrgan.getSysid());
			 }
			Organ dt = new Organ();
			BeanCopy bc = new BeanCopy();
			bc.copy(dt, request); 
			if (StringUtil.isEmpty(request.getParameter("isKeyRetailer"))) {
				dt.setIsKeyRetailer(-1);
			}
			dt.setCreationTime(new Date());
			//撤消人为空
			dt.setRepealid(null);
			dt.setSysid(sysid);
			if (parentid == null||"".equals(parentid )) {
				parentid = "0";
				dt.setRank(0);
			} else {
				dt.setRank(ao.getOrganByID(parentid).getRank() + 1);
			}
			
			String strlinkmobile[] = request.getParameterValues("linkmobile");
			
			if(appOrgan.isUsersExists(strlinkmobile[0], null)) {
				request.setAttribute("result", "新增失败,已存在手机号为["+strlinkmobile[0]+"]的客户!");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			
			dt.setOmobile(strlinkmobile[0]);

			String strname[] = RequestTool.getStrings(request, "name");
			int strsex[] = RequestTool.getInts(request, "sex");
//			String idcard[] = RequestTool.getStrings(request, "idcard");
//			Date strbirthday[] = RequestTool.getDates(request, "birthday");
//			String department[] = RequestTool.getStrings(request, "department");
			String sttduty[] = request.getParameterValues("duty");
			String strlinkofficetel[] = request.getParameterValues("linkofficetel");
//			String strhometel[] = request.getParameterValues("hometel");
			String strlinkemail[] = request.getParameterValues("linkemail");
			String strqq[] = request.getParameterValues("qq");
//			String strmsn[] = request.getParameterValues("msn");
			String strlkaddr[] = request.getParameterValues("lkaddr");
			int strismain[] = RequestTool.getInts(request, "ismain");
			
			//记录联系人的个数
			int olCount = 0;
			for (int i = 0; i < strname.length; i++) {
				if (!"".equals(strname[i])) {
					Olinkman linkman =  new Olinkman();
					linkman.setId(Integer.valueOf(MakeCode .getExcIDByRandomTableName("olinkman", 0, "")));
					linkman.setCid(dt.getId());    
					linkman.setName(strname[i]);
					linkman.setSex(strsex[i]);
					//tommy modify 12-30 
					//linkman.setIdcard(idcard[i]);
					//linkman.setBirthday(strbirthday[i]);
					//linkman.setDepartment(department[i]);
					linkman.setDuty(sttduty[i]);
					linkman.setOfficetel(strlinkofficetel[i]);
					linkman.setMobile(strlinkmobile[i]);
					//linkman.setHometel(strhometel[i]);
					linkman.setEmail(strlinkemail[i]);
					linkman.setQq(strqq[i]);
					//linkman.setMsn(strmsn[i]);
					linkman.setAddr(strlkaddr[i]);
					linkman.setIsmain(strismain[i]);
					linkman.setMakeorganid(users.getMakeorganid());
					linkman.setMakedeptid(users.getMakedeptid());
					linkman.setMakeid(userid);
					linkman.setMakedate(DateUtil.getCurrentDate());
					appol.addOlinkman(linkman);
					olCount++;
				}
			}
			//当无联系人时,则新增一个默认联系人
			if(olCount == 0){
				Olinkman linkman =  new Olinkman();
				linkman.setId(Integer.valueOf(MakeCode .getExcIDByRandomTableName("olinkman", 0, "")));
				linkman.setCid(dt.getId());
				linkman.setName("默认联系人");
				linkman.setMobile("13500000000");
				linkman.setMakeorganid(users.getMakeorganid());
				linkman.setMakedeptid(users.getMakedeptid());
				linkman.setMakeid(userid);
				linkman.setMakedate(DateUtil.getCurrentDate());
				appol.addOlinkman(linkman);
			}
			
			if(OrganType.Dealer.getValue().equals(dt.getOrganType())
					&&  !DealerType.PD.getValue().equals(dt.getOrganModel())) {
				dt.setValidatestatus(0);
			} else {
				dt.setValidatestatus(null);
				dt.setValidateuserid(null);
			}
			
			appos.insertOrganScan(dt.getId());//机构
			
			FormFile imgFile = (FormFile) organf.getLogo();
			String fileName = imgFile.getFileName().toLowerCase();
			String filePath = request.getRealPath("/");
			String realpathname="";
			if (fileName != null && !fileName.equals("") ) {

				InputStream fis = imgFile.getInputStream();
				 realpathname = "images\\logo\\" + fileName;

				OutputStream fos = new FileOutputStream(filePath + realpathname);

				byte[] buffer = new byte[1048576];
				int bytesRead = 0;
				while ((bytesRead = fis.read(buffer, 0, 8192)) != -1) {
					
					fos.write(buffer, 0, bytesRead);
				}
				fos.close();
				fis.close();
			}
			dt.setLogo(realpathname);
			//默认增加机构为非撤销
			dt.setIsrepeal(0);
			
			//主管信息
			if("".equals(saleManId)){
				saleManId=null;
			}
			dt.setSalemanId(saleManId);
			dt.setIsNeedApprove(RequestTool.getInt(request, "isNeedApprove"));
			ao.AddOrgan(dt);
			addWarehouse(dt);
			
			// 如果为关键经销商，添加积分账户、创建用户
			if (dt.getIsKeyRetailer() == 1) {
				String str = "";
				if (dt.getOrganModel() == DealerType.BKD.getValue()) {
					str = DealerType.BKD.getName() + dt.getId();
				} else {//if (dt.getOrganModel() == DealerType.BKR.getValue()) {
					str = DealerType.BKR.getName() + dt.getId();
				}
				SBonusAccount sac = acc.getByOrganId(dt.getId());
				if (sac == null) {
					SBonusAccount s = new SBonusAccount();
					s.setName(str);
					s.setCompanyName(dt.getOrganname());
					s.setMobile(dt.getOmobile());
					s.setOrganId(dt.getId());
					s.setAreas(dt.getAreas());
					s.setProvince(dt.getProvince());
					s.setCity(dt.getCity());
					s.setActiveFlag(1);
					s.setAddress(dt.getOaddr());
					acc.addSBonusAccount(s);
					
					//添加积分账号信息
					SBonusAppraise sBonusAppraise = new SBonusAppraise();
					sBonusAppraise.setAccountId(s.getAccountId());
					sBonusAppraise.setOrganId(dt.getId());
					sBonusAppraise.setBonusTarget(0d);
					sBonusAppraise.setAppraise(0d);
					sBonusAppraise.setActualPoint(0d);
					sBonusAppraise.setBonusPoint(0d);
					sBonusAppraise.setModifiedDate(DateUtil.getCurrentDate());
					sBonusAppraise.setPeriod(Integer.parseInt(DateUtil.getCurrentYear()));
					appSBonusAppraise.addSBonusAppraise(sBonusAppraise);
				}
				/*Users user = new Users();
				Integer userid = Integer.valueOf(MakeCode.getExcIDByRandomTableName("users", 0, ""));
				user.setUserid(userid); 
				user.setLoginname(str);
				user.setPassword(Encrypt.getSecret(str, 1));
				user.setApprovepwd(Encrypt.getSecret("aaaa", 1));
				user.setStatus(1);
				user.setMakeorganid(dt.getId());
				user.setCreatedate(new Date());
				user.setAreas(dt.getAreas());
				user.setProvince(dt.getProvince());
				user.setCity(dt.getCity());
				userService.InsertUsers(user);
				
				addRole(userid);
				//配置默认管辖权限
				addSelfRule(user.getMakeorganid(),userid);
				// 许可用户的所属机构的所有下级机构
				addWarehouseVist(user.getMakeorganid(), userid+"");*/
			}
			 
			//机构上架所有产品
			aop.addAllProductToOrganProduct(dt.getId());
			//更新make_conf
			appmc.updMakeConf("organ_product","organ_product");
			//新增默认仓库
			//addWarehouse(dt);
			
			// 增加用户(管辖上级机构)的业务往来权限
			List<UserVisit> userVisitList  = auv.queryByOrganId(parentOrgan.getId());
			for(UserVisit userVisit : userVisitList){
				addOrganVisit(userVisit.getUserid(), dt.getId());
			} 
			
			addSTransferRelation(dt);
			
			AfficheService as = new AfficheService();
			//新增消息
			as.addCreateAuditRequestAffiche(dt);
			
			request.setAttribute("result", "databases.add.success");
			DBUserLog.addUserLog(userid, "系统管理", "新增机构" + dt.getId());
			return mapping.findForward("addresult");

		} catch (Exception e) {
			logger.error("",e);
			throw e;
		}
	}
	
	//新增默认进货机构
	private void addSTransferRelation(Organ dt) throws Exception {
		if(OrganType.Dealer.getValue().equals(dt.getOrganType())
				&& !DealerType.PD.getValue().equals(dt.getOrganModel())) {
			STransferRelation str= new STransferRelation();
			str.setModifieDate(DateUtil.getCurrentDate());
			str.setOppOrganId(dt.getParentid());
			str.setOrganizationId(dt.getId());
			astr.addSTransferRelation(str);
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
	 * 新增默认仓库
	 * @throws Exception 
	 */
	private Warehouse addWarehouse(Organ organ) throws Exception{
		Warehouse w = new Warehouse();
		Integer warehouseid = Integer.valueOf(MakeCode.getExcIDByRandomTableName("warehouse", 0, ""));
		w.setId(MakeCode.getFormatNums(warehouseid, 5));
		w.setWarehousename(organ.getOrganname()+"默认仓库");
		//设置部门
		w.setDept(1);
		w.setUserid(0);
		Olinkman olinkman = appol.findOlinkmanByCid(organ.getId());
		if(olinkman != null){
			w.setUsername(olinkman.getName());
			w.setWarehousetel(olinkman.getMobile());
		}else {
			w.setUsername(organ.getOrganname()+"默认联系人");
			w.setWarehousetel("13500000000");
		}
		w.setWarehouseproperty(0);
		//设置省份
		w.setProvince(organ.getProvince());
		w.setCity(organ.getCity());
		w.setAreas(organ.getAreas());
		w.setWarehouseaddr(organ.getOaddr());
		w.setMakeorganid(organ.getId());
		w.setUseflag(1);
		//设置是否自动签收(PD自动签收为否,其它都为是)
		if(organ.getOrganModel() == 1){
			w.setIsautoreceive(0);
		}else {
			w.setIsautoreceive(1);
		}
		w.setRemark("");
		//设置内部编号
		w.setNccode(organ.getOecode());
		//设置仓库简称
		w.setShortname(organ.getOrganname()+"默认仓库");
		// 设置上限
		w.setHighNumber("");
		// 设置下限
		w.setBelowNumber("");
		// 地址
		w.setWarehouseaddr(organ.getOaddr());
		// 设置是否负库存
//		if(OrganType.Dealer.getValue().equals(organ.getOrganType())
//				&& DealerType.PD.getValue().equals(organ.getOrganModel())) {
			w.setIsMinusStock(0);
//		} else {
//			w.setIsMinusStock(1);
//		}
		aw.addWarehouse(w);
		
		// 默认仓库的库位
		WarehouseBit bit = new WarehouseBit();
		bit.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("warehouse_bit", 1, "")));
		bit.setWid(w.getId());
		bit.setWbid("000");  //默认库位000
		appWarehouse.addWarehouseBit(bit);
		
		return w;
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
