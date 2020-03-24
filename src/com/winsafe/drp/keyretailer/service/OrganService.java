package com.winsafe.drp.keyretailer.service;

import java.util.Date;
import java.util.List;

import com.winsafe.drp.dao.AppMakeConf;
import com.winsafe.drp.dao.AppOlinkMan;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppOrganProduct;
import com.winsafe.drp.dao.AppOrganScan;
import com.winsafe.drp.dao.AppOrganVisit;
import com.winsafe.drp.dao.AppUserVisit;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.AppWarehouseVisit;
import com.winsafe.drp.dao.Olinkman;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.OrganVisit;
import com.winsafe.drp.dao.UserVisit;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.dao.WarehouseBit;
import com.winsafe.drp.keyretailer.dao.AppSTransferRelation;
import com.winsafe.drp.keyretailer.pojo.STransferRelation;
import com.winsafe.drp.metadata.OrganType;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class OrganService {
	
	private AppOrgan appOrgan = new AppOrgan();
	private AppMakeConf appmc = new AppMakeConf();
	private AppUserVisit auv = new AppUserVisit();
	private AppWarehouse appWarehouse = new AppWarehouse();
	private AppOrganVisit aov = new AppOrganVisit();
	private AppWarehouseVisit appWV = new AppWarehouseVisit();
	private WarehouseService aw = new WarehouseService();
	private AppOlinkMan appol = new AppOlinkMan();
	
	public Organ addBKR(UsersBean loginUsers, String organName, String mobile, String address, String name, Integer province, Integer city, Integer area,String organkeytype) throws Exception {
		Organ parentOrgan = appOrgan.getOrganByID(loginUsers.getMakeorganid());
		
		String organId = MakeCode.getExcIDByRandomTableName("organ", 4, "").trim(); 
		
		Organ organ = new Organ();
		organ.setId(organId);
		organ.setIsKeyRetailer(0);
		organ.setIsrepeal(0);
		organ.setOaddr(address);
		organ.setOecode(organId);
		organ.setOmobile(mobile);
		organ.setOrganname(organName);
		organ.setOrganType(OrganType.Dealer.getValue());
//		organ.setOrganModel(DealerType.BKR.getValue());
		organ.setParentid(loginUsers.getMakeorganid());
		organ.setRank(parentOrgan.getRank() + 1);
		organ.setSysid(appOrgan.getOcodeByParent(parentOrgan.getSysid()));
		organ.setValidatestatus(0);
//		organ.setValidatedate(DateUtil.getCurrentDate());
//		organ.setValidateuserid(loginUsers.getUserid());
		organ.setCity(city);
		organ.setProvince(province);
		organ.setAreas(area);
		organ.setOrganModel(Integer.valueOf(organkeytype));
		appOrgan.AddOrgan(organ);
		
		// 添加进货关系
		STransferRelation s = new STransferRelation();
		s.setModifieDate(new Date());
		s.setOrganizationId(organId);
		s.setOppOrganId(loginUsers.getMakeorganid());
		AppSTransferRelation app = new AppSTransferRelation();
		app.addSTransferRelation(s);
		
		AppOrganScan appos = new AppOrganScan();
		appos.insertOrganScan(organId);
		
		AppOrganProduct aop = new AppOrganProduct();
		//机构上架所有产品
		aop.addAllProductToOrganProduct(organ.getId());
		
		
		//更新make_conf
		appmc.updMakeConf("organ_product","organ_product");
		
		//新增默认仓库
		addWarehouse(organ, mobile, name);
		
		// 增加用户(管辖上级机构)的业务往来权限
		List<UserVisit> userVisitList  = auv.queryByOrganId(parentOrgan.getId());
		for(UserVisit userVisit : userVisitList){
			addOrganVisit(userVisit.getUserid(), organ.getId());
		}
		
		return organ;
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
	 * @param name 
	 * @param mobile 
	 * @throws Exception 
	 */
	private Warehouse addWarehouse(Organ organ, String mobile, String name) throws Exception{
		Warehouse w = new Warehouse();
		Integer warehouseid = Integer.valueOf(MakeCode.getExcIDByRandomTableName("warehouse", 0, ""));
		w.setId(MakeCode.getFormatNums(warehouseid, 5));
		w.setWarehousename(organ.getOrganname()+"默认仓库");
		//设置部门
		w.setDept(1);
		w.setUserid(0);
		w.setUsername(name);
		w.setWarehousetel(mobile);
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
		w.setIsMinusStock(0);
		aw.addWarehouse(w);
		
		// 默认仓库的库位
		WarehouseBit bit = new WarehouseBit();
		bit.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("warehouse_bit", 1, "")));
		bit.setWid(w.getId());
		bit.setWbid("000");  //默认库位000
		appWarehouse.addWarehouseBit(bit);
		
		return w;
	}

	public void addOlinkman(UsersBean loginUsers, Organ organ, String name, String idCard) throws Exception {
		Olinkman linkman =  new Olinkman();
		linkman.setId(Integer.valueOf(MakeCode .getExcIDByRandomTableName("olinkman", 0, "")));
		linkman.setCid(organ.getId());
		linkman.setAddr(organ.getOaddr());
		linkman.setIdcard(idCard); 
		linkman.setName(name);
		linkman.setSex(1);
		linkman.setMobile(organ.getOmobile());
		linkman.setIsmain(1);
		linkman.setMakeorganid(loginUsers.getMakeorganid());
		linkman.setMakedeptid(loginUsers.getMakedeptid());
		linkman.setMakeid(loginUsers.getUserid());
		linkman.setMakedate(DateUtil.getCurrentDate());
		appol.addOlinkman(linkman);
	}
}
