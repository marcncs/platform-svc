package com.winsafe.drp.action.scanner;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOlinkMan;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppOrganPriceii;
import com.winsafe.drp.dao.AppOrganProduct;
import com.winsafe.drp.dao.AppOrganScan;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppRole;
import com.winsafe.drp.dao.AppRuleUserWH;
import com.winsafe.drp.dao.AppUserVisit;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.AppWarehouseVisit;
import com.winsafe.drp.dao.Olinkman;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.OrganProduct;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.UserVisit;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.dao.WarehouseBit;
import com.winsafe.drp.dao.WarehouseVisit;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.StringUtil;

public class CustomerManagerAction extends Action {
	private Logger logger = Logger.getLogger(CustomerManagerAction.class);
	
	private AppOrgan appo = new AppOrgan();

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		AppUsers au = new AppUsers();
		String type = request.getParameter("type");
		String username = request.getParameter("username");
		// 根据用户名称查询用户信息
		Users users = au.getUsers(username);
		if (users == null) {
			return null;
		}

		String outString = "";
		// type [1：查询 2：新增 3：修改 4：删除]
//		if ("1".equals(type)) {
//			// 管辖机构
//			outString = getOrgan(users);
//		} else if ("2".equals(type)) {
//			// 新增
//
//			outString = add(request, users);
//		} else if ("3".equals(type)) {
//			// 修改
//
//			outString = update(request);
//		} else if ("4".equals(type)) {
//			// 删除
//
//			outString = del(request,users);
//		} else {
//			// do nothing
//		}
		//--------------屏蔽功能---------S------------
		if ("1".equals(type)) {
			// 管辖机构
			//outString = getOrgan(users);
			outString = getWarehouseVisitByUserInfo(users);
		}else {
			outString = "returnCode=1\r\nreturnMsg=操作失败";
		}
		//--------------屏蔽功能---------E------------
		ResponseUtil.write(response, outString);

		return null;
	}

	private String getOrgan(Users user) throws Exception {
		StringBuffer organInfos = new StringBuffer();
		
		AppOlinkMan appolm = new AppOlinkMan();

		Organ organ = appo.getOrganByID(user.getMakeorganid());

		// String sysid=organ.getSysid();
		// String
		// hql="select  from Organ as o where sysid like '"+sysid+"%' and length(sysid)>"+sysid.length()+"  order by sysid";
		// return EntityManager.getAllByHql(hql);

		List<Organ> organList = appo.getOrganLikeSysid(organ.getSysid());

		Olinkman aOlinkMan;
		for (Organ o : organList) {
			aOlinkMan = appolm.getMainLinkmanByCid(o.getId());

			organInfos.append(o.getId());
			organInfos.append(",");
			organInfos.append(o.getOrganname());
			organInfos.append(",");
			organInfos.append(o.getOaddr());
			if (aOlinkMan != null) {
				organInfos.append(",");
				organInfos.append(aOlinkMan.getId());
				organInfos.append(",");
				organInfos.append(StringUtil.parseString(aOlinkMan.getName(),
						""));
				organInfos.append(",");
				organInfos.append(StringUtil.parseString(aOlinkMan.getMobile(),
						""));
			} else {
				organInfos.append(",");
				organInfos.append(",");
				organInfos.append(",");
			}
			organInfos.append(";");
		}

		return organInfos.toString();
	}

	@SuppressWarnings("unchecked")
	private String add(HttpServletRequest request, Users users) {
		try {
			OrganService app = new OrganService();
			AppOrganScan appos = new AppOrganScan();
			AppOrgan appOrgan = new AppOrgan();

			// 机构名称
			String Cname = request.getParameter("Cname");
			// 机构地址
			String Cadress = request.getParameter("Cadress");
			// 联系人名称
			String Lname = request.getParameter("Lname");
			// 联系人电话
			String Lphone = request.getParameter("Lphone");

			if (StringUtil.isEmpty(Cname) || StringUtil.isEmpty(Cadress)
					|| StringUtil.isEmpty(Lname) || StringUtil.isEmpty(Lphone)) {

				return "returnCode=1\r\nreturnMsg=信息不完整，请填写完整";
			}
			//----lipeng----20131206----add-----S
			//----判断机构名是否重复
			Organ organ = appOrgan.getOrganByOrganName(Cname.trim());
			if(organ != null){
				return "returnCode=1\r\nreturnMsg=机构名已存在，请正确填写";
			}
			//----lipeng----20131206----add-----E
			/*
			 * 新增机构
			 */

			String parentid = users.getMakeorganid();

			Organ parentOrgan = app.getOrganByID(parentid);
			if (parentOrgan == null) {
				return "returnCode=1\r\nreturnMsg=用户所属机构不存在";
			}
			String sysid = app.getOcodeByParent(parentOrgan.getSysid());

			// 机构实例
			Organ o = new Organ();

			String id = "";

			id = MakeCode.getExcIDByRandomTableName("organ", 4, "");

			// 编号
			o.setId(id);

			// 系统编号
			o.setSysid(sysid);
			//-----lipeng-----20131206-----add----S---
			//-----基础数据不全,先设置内部编号为ID-----
			//内部编号 
			o.setOecode(id);
			//-----lipeng-----20131206-----add----E---

			// 省
			o.setProvince(parentOrgan.getProvince());
			o.setCity(parentOrgan.getCity());
			o.setAreas(parentOrgan.getAreas());

			// 内部编号
			// o.setOecode("");

			// 机构名称,仓库名称默认相同+默认仓库
			o.setOrganname(Cname);

			o.setOaddr(Cadress);
			o.setOmobile(Lphone);
			o.setOtel(Lphone);

			o.setRate(0);
			o.setPrompt(0);
			o.setPprompt(0);
			o.setIsrepeal(0);

			o.setParentid(parentid);
			o.setRank(parentOrgan.getRank() + 1);
			o.setRepealid(0);
			o.setLogo("");
			app.AddOrgan(o);

			/*
			 * 新增联系人
			 */
			AppOlinkMan appLinkMan = new AppOlinkMan();
			Olinkman ol1 = new Olinkman();
			ol1.setOfficetel(Lphone);
			ol1.setMobile(Lphone);
			ol1.setName(Lname);
			ol1.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
					"olinkman", 0, "")));
			// 设置联系人性别(默认:男)
			ol1.setSex(2);
			// 设置从属机构
			ol1.setCid(id);
			appLinkMan.addOlinkman(ol1);

			/*
			 * 新增仓库
			 */
			// 默认仓库
			Warehouse warehouse = new Warehouse();
			warehouse.setId(MakeCode.getExcIDByRandomTableName("warehouse", 1,
					""));
			// 仓库名默认与机构名相同 + "默认仓库"
			warehouse.setWarehousename(o.getOrganname() + "默认仓库");
			// 仓库位置
			warehouse.setWarehouseaddr(o.getOaddr());
			// 仓库联系人
			warehouse.setUsername(ol1.getName());
			// 仓库联系人id
			warehouse.setUserid(ol1.getId());
			// 相关的组织关联
			warehouse.setMakeorganid(o.getId());
			// 默认非自动签收仓库
			warehouse.setIsautoreceive(0);
			warehouse.setProvince(o.getProvince());
			warehouse.setCity(o.getCity());
			warehouse.setAreas(o.getAreas());

			// 设置可用
			warehouse.setUseflag(1);
			AppWarehouse appWarehouse = new AppWarehouse();
			// 插入数据库
			appWarehouse.addWarehouse(warehouse);

			// 默认仓库的库位
			WarehouseBit bit = new WarehouseBit();
			bit.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
					"warehouse_bit", 1, "")));
			bit.setWid(warehouse.getId());
			// 默认库位000
			bit.setWbid("000");
			// 插入数据库
			appWarehouse.addWarehouseBit(bit);

			/*
			 * 入库扫描设置
			 */
			appos.insertOrganScan(o.getId());

			/*
			 * 上架产品
			 */
			AppOrganProduct aop = new AppOrganProduct();
			AppOrganPriceii appprice = new AppOrganPriceii();
			OrganProduct op = new OrganProduct();
			AppProduct appProduct=new AppProduct();
//			List<Product> list=appProduct.findByWhereSql("");
//			for (Product p:list) {
//				op.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
//						"organ_product", 0, "")));
//				op.setOrganid(o.getId());
//				op.setProductid(p.getId());
//				aop.addOrganProductNoExist(op);
//
//				appprice.delOrganPriceiiByOIDPID(o.getId(), p.getId());
//				appprice.copyProductPriceii(o.getId(),p.getId(), o.getRate());
//			}
			aop.addAllProduct(o.getId());

			/*
			 *  设置管辖机构 和 设置管辖仓库
			 */
			AppUserVisit appUserVisit=new AppUserVisit();
			AppRuleUserWH appRuleUserWH=new AppRuleUserWH();
			//对角色是“系统管理员” 的所有用户设置权限
			List<UserVisit> uvList=appUserVisit.queryIncludeOrgan();
			for(UserVisit uv:uvList){ 
				//设置管辖机构
				String visitOrgan = uv.getVisitorgan();
				if(visitOrgan==null || visitOrgan.equals("")){
					uv.setVisitorgan(o.getId());
				}else{
					uv.setVisitorgan( visitOrgan+ "," + o.getId());
				}
				
				EntityManager.update2(uv);		
				
				//设置管辖仓库
				Integer userId = uv.getUserid();
				appRuleUserWH.addRuleUserWh(warehouse.getId(), userId);
			}

			/*
			 *  设置业务往来仓库
			 */
			AppRole ar = new AppRole();
			//对角色是“系统管理员” 的所有用户设置权限
			List<Integer> userids=ar.getUserByRoleInAdmin();
			WarehouseVisit wv;
			for(Integer userId:userids){
	    		wv = new WarehouseVisit();
	    		wv.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("warehouse_visit",0,"")));
	    		wv.setUserid(userId);
	    		wv.setWid(warehouse.getId());

	    		EntityManager.save3(wv);
			}
			
			AppWarehouseVisit awv = new AppWarehouseVisit();
			WarehouseVisit _wv = awv.getWarehouseVisit(users.getUserid(), warehouse.getId());
			if(null == _wv){
				wv = new WarehouseVisit();
	    		wv.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("warehouse_visit",0,"")));
	    		wv.setUserid(users.getUserid());
	    		wv.setWid(warehouse.getId());
	    		EntityManager.save3(wv);
			}
			
			return "returnCode=0\r\nreturnMsg=新增成功\r\nCID="+o.getId()+"\r\nLID="+ol1.getId();
		} catch (Exception e) {
			logger.error("新增失败", e);
			HibernateUtil.rollbackTransaction();
			return "returnCode=1\r\nreturnMsg=新增失败";
		}
	}

	private String update(HttpServletRequest request) {
		try {
			AppOlinkMan appLinkMan = new AppOlinkMan();
			AppWarehouse appWarehouse = new AppWarehouse();
			AppOrgan appOrgan = new AppOrgan();
			
			//机构编号
			String CID = request.getParameter("CID");
			String LID = request.getParameter("LID");
			// 机构名称
			String Cname = request.getParameter("Cname");
			// 机构地址
			String Cadress = request.getParameter("Cadress");
			// 联系人名称
			String Lname = request.getParameter("Lname");
			// 联系人电话
			String Lphone = request.getParameter("Lphone");

			
			if (StringUtil.isEmpty(Cname) || StringUtil.isEmpty(Cadress)
					|| StringUtil.isEmpty(Lname) || StringUtil.isEmpty(Lphone)) {

				return "returnCode=1\r\nreturnMsg=信息不完整，请填写完整";
			}
			//----lipeng----20131206----add-----S
			//----判断机构名是否重复
			Organ organ = appOrgan.getOrganByOrganName(Cname.trim());
			if(organ != null){
				return "returnCode=1\r\nreturnMsg=机构名已存在，请正确填写";
			}
			//----lipeng----20131206----add-----E
			
			Organ o = appo.getOrganByID(CID);
			if(o==null){
				return "returnCode=1\r\nreturnMsg=所要修改的机构不存在";
			}
			
			Olinkman olk = appLinkMan.getOlinkmanByID(Integer.parseInt(LID));
			if(olk==null){
				return "returnCode=1\r\nreturnMsg=所要修改机构的联系人不存在";
			}
			
			o.setOrganname(Cname);
			o.setOaddr(Cadress);
			o.setOmobile(Lphone);
			EntityManager.update2(o);
			
			Warehouse w = appWarehouse.getWarehouseByOID(CID);
			if(w!=null){
				w.setWarehousename(Cname+"默认仓库");
				EntityManager.update2(w);
			}
			
			olk.setOfficetel(Lphone);
			olk.setMobile(Lphone);
			olk.setName(Lname);
			EntityManager.update2(olk);
			
			return "returnCode=0\r\nreturnMsg=修改成功";
			
		} catch (Exception e) {
			logger.error("修改失败", e);
			HibernateUtil.rollbackTransaction();
			return "returnCode=1\r\nreturnMsg=修改失败";
		}
	}

	private String del(HttpServletRequest request,Users user) {
		//check 是否已被使用
		
		try {
			//机构编号
			String CID = request.getParameter("CID");
			Organ o = appo.getOrganByID(CID);
			if(o==null){
				return "returnCode=1\r\nreturnMsg=所要删除的机构不存在";
			}
			
			o.setIsrepeal(1);
			o.setRepealdate(Dateutil.getCurrentDate());
			o.setRepealid(user.getUserid());
			EntityManager.update2(o);
			
			return "returnCode=0\r\nreturnMsg=删除成功";
		} catch (Exception e) {
			logger.error("删除失败", e);
			HibernateUtil.rollbackTransaction();
			return "returnCode=1\r\nreturnMsg=删除失败";
		}
	}
	
	/**
	 * 根据USER查询Warehouse表
	 * @param users
	 * @return
	 * @throws Exception
	 */
	private String getWarehouseVisitByUserInfo(Users users) throws Exception{
		String organs = "";
		String organInfos = "";
		AppOrgan ao = new AppOrgan();
		AppWarehouse aw = new AppWarehouse();

		List<Warehouse> warehouseList = aw.getEnableWarehouseByVisit(users.getUserid());
		if(null != warehouseList && !warehouseList.isEmpty()){
			organs = castOrgan(warehouseList);
		}
		if(!"".equals(organs)){
			//根据机构ID查询机构信息
			List<Organ> organList = ao.getOrgan(" where   o.isrepeal=0  and  o.id in ("+organs+")  and (o.oecode is not null and o.oecode != '') ");
			if(null != organList && !organList.isEmpty()){
				//循环写入返回的机构ID和名称信息
				for (Organ organ : organList) {
					//----lipeng----20131112----upd----s----
					//--下载数据增加oecode
//					organInfos += organ.getId() + "," + organ.getOrganname() + ";";
					organInfos += organ.getId() + "," + organ.getOrganname() + "," + organ.getOecode() + ";";
					//----lipeng----20131112----upd----e----
				}
			}
		}
		return organInfos;
	}
	/**
	 * 通过仓库集合查询到机构信息
	 * @param warehouseList
	 * @return
	 * @throws Exception
	 */
	private String castOrgan(List<Warehouse> warehouseList) throws Exception{
		String organs = "";
		for (Warehouse warehouse : warehouseList) {
			organs += warehouse.getMakeorganid() + ","; 
		}
		String[] oIds = organs.split(",");
		organs = StringUtil.getString(oIds, 0, true);
		return organs;
	}
}
