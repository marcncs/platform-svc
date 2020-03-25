package com.winsafe.drp.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppTakeTicketDetail;
import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.Idcode;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketDetail;
import com.winsafe.drp.dao.TakeTicketIdcode;
import com.winsafe.drp.util.Constants;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

/**
 * @author jerry
 * @version 2009-8-17 下午04:36:33 www.winsafe.cn
 */
public class DealUploadIdcodeOrganWithdraw extends DealUploadIdcode {
	private AppTakeTicketIdcode appTakeTicketIdcode = new AppTakeTicketIdcode();
	private AppTakeTicketDetail attd = new AppTakeTicketDetail();
	
	private TakeTicket tt = null; // 单据编号
	private Product p = null; // 产品
	private String batch = null;

	public DealUploadIdcodeOrganWithdraw(String filepath, String fileaddress,
			int iuid) {
		super(filepath, fileaddress, iuid);
	}

	@Override
	public String addIdcode(String uploadidcode) {
		String t_ret = null;
		try {
			// 判断并去掉采集器上传的条码的bom
			uploadidcode = dealuploadIdcode(uploadidcode);
			// 解析获取条形码
			String idcode = crs.getIdcode(uploadidcode);
			// 扫描类型(退货入库类型)
			t_ret = "";
			// 单据编号
			String ttid = crs.getBillNo(uploadidcode);
			// 产品编号
			String lcode = crs.getProductId(uploadidcode);
			
			//判断该行是否删除
			if (null != t_ret) {
				if ("D".equals(t_ret)) {
					return null;
				} else {
					return t_ret;
				}
			}
			tt = takeTicketMap.get(ttid);
			//验证单据
			t_ret = validateTakeTicket(uploadidcode,ttid,tt,null,null,idcode,lcode);
			//如果验证不通过，则返回验证错误信息
			if (null != t_ret)
			{
				return t_ret;
			}	
			
			//验证条形码是否存在在当前单据中
			t_ret = isExist(0,idcode,ttid,idcode,lcode);
			//如果验证不通过，则返回验证错误信息
			if (null != t_ret)
			{
				return t_ret;
			}
			
			//@author Andy.liu 2014-4-24 15:30:45

			// 无明细退货
			// 1，判断条码是否在idcode中，此码可能为小盒，中盒，外箱码，
			// 如果不存在，则提示*
			// 2，判断条码是否出过库，*
			//    判断码是否在这个仓库中
			// 3，根据ttid获取TT单，找到退货单OWID (判断TT是否存在)
			// 4，根据lcode找产品。根据idcode找批次。 (判断产品编码是否存在)
			// 5，如果idcode第一位是H 则unitid = 4 (判断码长度是否正确)
			// 如果idcode第一位是X 则unitid = 2
			// 否则为1
			// 将条码插入表tti
			// 等待读取完毕之后生成明细
			
			//单位
			int unitid = 0;
			//条码所在仓库
			String warehouseId = null;
			//条码日期
			String productDate = null;
			String prefix = idcode.substring(0, 1);
			//箱
			if(Constants.CODEUNIT_X.equals(prefix)){
				//判断条码长度是否正确
				if (idcode.length() != 28) {
					t_ret = "条码[" + idcode + "]E00006:条码长度错误";
					writeTxt(t_ret);
					return t_ret;
				}
				
				//箱的情况
				Object[] boxStatus = appidcode.getCartonCodeCurrStatus(idcode); 
				Integer count = ((Long)(boxStatus[0])).intValue();
				//判断条码是否在系统中
				if(count<=0 ){
					t_ret = "条码[" + idcode + "]E00007:条码不在系统中";
					writeTxt(t_ret);
					return t_ret;
				}
				//判断条码是否可用
				Integer isuse = ((Long)(boxStatus[1])).intValue();
				if(isuse == 0){
					t_ret = "条码[" + idcode + "]E00008:条码不可用";
					writeTxt(t_ret);
					return t_ret;
				}
				//判断条码是否出库
				Integer isout = ((Long)(boxStatus[2])).intValue();
				if(isout == 1){
					t_ret = "条码[" + idcode + "]E00009:条码已出库";
					writeTxt(t_ret);
					return t_ret;
				}
				//批次
				batch = (String) boxStatus[4];
				//条码所在仓库
				warehouseId = (String) boxStatus[5];
				//条码日期
				productDate = (String) boxStatus[6];
				//单位
				unitid = 2;
				
			}
			//中盒
			else if(Constants.CODEUNIT_H.equals(prefix)){
				//判断条码长度是否正确
				if (idcode.length() != 28) {
					t_ret = "条码[" + idcode + "]E00006:条码长度错误";
					writeTxt(t_ret);
					return t_ret;
				}
				
				//中盒的情况
				Object[] boxStatus = appidcode.getBoxCurrStatus(idcode); 
				Integer count = ((Long)(boxStatus[0])).intValue();
				//判断条码是否在系统中
				if(count<=0 ){
					t_ret = "条码[" + idcode + "]E00007:条码不在系统中";
					writeTxt(t_ret);
					return t_ret;
				}
				//判断条码是否可用
				Integer isuse = ((Long)(boxStatus[1])).intValue();
				if(isuse == 0){
					t_ret = "条码[" + idcode + "]E00008:条码不可用";
					writeTxt(t_ret);
					return t_ret;
				}
				//判断条码是否出库
				Integer isout = ((Long)(boxStatus[2])).intValue();
				if(isout == 1){
					t_ret = "条码[" + idcode + "]E00009:条码已出库";
					writeTxt(t_ret);
					return t_ret;
				}
				
				//批次
				batch = (String) boxStatus[4];
				//条码所在仓库
				warehouseId = (String) boxStatus[5];
				//条码日期
				productDate = (String) boxStatus[6];
				//单位
				unitid = 4;
			}
			//小盒
			else{
				//判断条码长度是否正确
				if (idcode.length() != 16) {
					t_ret = "条码[" + idcode + "]E00006:条码长度错误";
					writeTxt(t_ret);
					return t_ret;
				}
				//中盒的情况
				Idcode ic = appidcode.getIdcodeById(idcode);
				
				//判断条码是否在系统中
				if(ic==null){
					t_ret = "条码[" + idcode + "]E00007:条码不在系统中";
					writeTxt(t_ret);
					return t_ret;
				}
				//判断条码是否可用
				if(ic.getIsuse() == 0){
					t_ret = "条码[" + idcode + "]E00008:条码不可用";
					writeTxt(t_ret);
					return t_ret;
				}
				//判断条码是否出库
				if(ic.getIsout() == 1){
					t_ret = "条码[" + idcode + "]E00009:条码已出库";
					writeTxt(t_ret);
					return t_ret;
				}
				
				//批次
				batch = ic.getBatch();
				//条码所在仓库
				warehouseId = ic.getWarehouseid();
				//条码日期
				productDate = ic.getProducedate();
				//单位
				unitid = 1;
			}
				
			//tt单据信息
			tt = takeTicketMap.get(ttid);
			if (tt == null) {
				t_ret = "单据[" + tt + "]E00010:单据不存在";
				writeTxt(t_ret);
				return t_ret;
			}
			//判断条码是否在当前仓库中
			if(!tt.getWarehouseid().equals(warehouseId)){
				t_ret = "条码[" + idcode + "]E00011:条码不在当前仓库中";
				writeTxt(t_ret);
				return t_ret;
			}
			
			//产品信息
			p = lcodeProductMap.get(lcode);
			if (p == null) {
				t_ret = "条码[" + idcode + "]E00012:产品不存在";
				writeTxt(t_ret);
				return t_ret;
			}
			
			//添加条码到tti表中
			addTakeTicketIdcode(tt, p, idcode, lcode, batch, productDate, unitid);
			 HibernateUtil.commitTransaction();
		} catch (Exception e) {
			e.printStackTrace();
			HibernateUtil.rollbackTransaction();
			t_ret = "条码不符合规则";
		} finally {
			
		}
		return t_ret;
	}
	
	/**
	 * 验证单据 Create Time: Oct 11, 2011 4:03:35 PM
	 * @param uploadidcode
	 * @return
	 * @author dufazuo
	 * @throws Exception
	 */
	private String validateTakeTicket(String uploadidcode,String billno,TakeTicket tt,
			List<TakeTicketDetail> ttdlist,Idcode ic,String idcode,String lcode) throws Exception
	{
		//单号不存在或非TT开头的条码
		if (billno.equals("") || !billno.startsWith("TT"))
		{
			if(null != p){
				writeTxt("E00008:"+uploadidcode + "[单据编号错误]" + "，单据号：" + billno + "，产品：" + p.getProductname() + ", " + p.getSpecmode());
			}else{
				writeTxt("E00008:"+uploadidcode + "[单据编号错误]" + "，单据号：" + billno + "，产品物流码前缀：" + lcode);
			}
			return "单据编号错误";
		}
		//单号不存在的情况(也导入数据库)
		if (tt == null)
		{
			if(null != p){
				writeTxt("E00008:"+uploadidcode + "[单据不存在]" + "，单据号：" + billno + "，产品：" + p.getProductname() + ", " + p.getSpecmode());
			}else{
				writeTxt("E00008:"+uploadidcode + "[单据不存在]" + "，单据号：" + billno + "，产品物流码前缀：" + lcode);
			}
			return "单据不存在";
		}
		else
		{
//			//如果是退货，则不可以上传未曾出库的条码
//			if(ic!=null && tt.getBillno().startsWith("OW")&&tt.getInwarehouseid().equals(ic.getWarehouseid())){
//				if(null != p){
//					writeTxt("E00010:"+idcode + "[该条码未曾出库，不能作为退货条码上传]" + "，单据号：" + billno + "，产品：" + p.getProductname() + ", " + p.getSpecmode());
//				}else{
//					writeTxt("E00010:"+idcode + "[该条码未曾出库，不能作为退货条码上传]" + "，单据号：" + billno + "，产品物流码前缀：" + lcode);
//				}
//				return "该条码未曾出库，不能作为退货条码上传";
//			}
			
			//判断对应的单据状态(已复核的不能进行操作)
			if (tt.getIsaudit() == 1)
			{
				if(null != p){
					writeTxt("E00011:"+uploadidcode + "[单据已复核]" + "，单据号：" + billno + "，产品：" + p.getProductname() + ", " + p.getSpecmode());
				}else{
					writeTxt("E00011:"+uploadidcode + "[单据已复核]" + "，单据号：" + billno + "，产品物流码前缀：" + lcode);
				}
				return "单据已复核";
			}
			//判断对应的单据状态(已作废的不能进行操作)
			if(tt.getIsblankout() == 1){
				if(null != p){
					writeTxt("E00012:"+uploadidcode + "[单据已作废]" + "，单据号：" + billno + "，产品：" + p.getProductname() + ", " + p.getSpecmode());
				}else{
					writeTxt("E00012:"+uploadidcode + "[单据已作废]" + "，单据号：" + billno + "，产品物流码前缀：" + lcode);
				}
				return "单据已作废";
			}
			
			//判断列表是否存在
			/*
			 * 
			 * if (ttdlist == null || ttdlist.isEmpty())
			{
				if(null != p){
					writeTxt("E00013:"+uploadidcode + "[单据中无对应的产品]" + "，单据号：" + billno + "，产品：" + p.getProductname() + ", " + p.getSpecmode());
				}else{
					writeTxt("E00013:"+uploadidcode + "[单据中无对应的产品]" + "，单据号：" + billno + "，产品物流码前缀：" + lcode);
				}
				return "单据中无对应的产品";
			}*/
			
		}
		return null;
	}

	/**
	 * 验证条形码是否存在在当前单据中
	 */
	private String isExist(int flag,String currentIdcode, String billno, String idcode, String lcode) throws Exception
	{
		if(takeTicketIdcodeSet.contains(billno + "_" + currentIdcode))
		{
			if(flag==0){
				if(null != p){
					writeTxt("E00014:"+idcode + "[条码已经存在当前列表中]" + "，单据号：" + billno + "，产品：" + p.getProductname() + ", " + p.getSpecmode());
				}else{
					writeTxt("E00014:"+idcode + "[条码已经存在当前列表中]" + "，单据号：" + billno + "，产品物流码前缀：" + lcode);
				}
				return "条码已经存在当前列表中";
			}else if(flag==1){
				if(null != p){
					writeTxt("E00015:"+idcode + "[该条码的箱码("+currentIdcode+")已经存在当前列表中]" + "，单据号：" + billno + "，产品：" + p.getProductname() + ", " + p.getSpecmode());
				}else{
					writeTxt("E00015:"+idcode + "[该条码的箱码("+currentIdcode+")已经存在当前列表中]" + "，单据号：" + billno + "，产品物流码前缀：" + lcode);
				}
				return "该条码的箱码已经存在当前列表中";
			}else if(flag==2){
				if(null != p){
					writeTxt("E00016:"+idcode + "[该托码中的箱码("+currentIdcode+")已经存在当前列表中]" + "，单据号：" + billno + "，产品：" + p.getProductname() + ", " + p.getSpecmode());
				}else{
					writeTxt("E00016:"+idcode + "[该托码中的箱码("+currentIdcode+")已经存在当前列表中]" + "，单据号：" + billno + "，产品物流码前缀：" + lcode);
				}
				return "该托码中的箱码已经存在当前列表中";
			}else{
				//无
			}
			
		}
		return null;
	}

	/**
	 * 处理完条码后，新增明细单，ttd
	 * 
	 * @author Andy.liu
	 */
	@Override
	public void dealAfter() throws Exception {
		AppFUnit appfn = new AppFUnit();
		Map<String,Map<Integer,Double>> funitMap = new HashMap<String,Map<Integer,Double>>();
		Map<String,Product> pMap = new HashMap<String, Product>();
		//存储单据详情信息
		Map<String,TakeTicketDetail> ttdMap = new HashMap<String, TakeTicketDetail>();
		//循环单据信息
		for(Map.Entry<String,TakeTicket> entry : takeTicketMap.entrySet()){
			TakeTicket objtt = entry.getValue();
			//查询单据下的条码信息
			List ttiList = appTakeTicketIdcode.getTakeTicketIdcodeByttid(entry.getKey());
			//循环遍历条码信息
			if(ttiList!=null && !ttiList.isEmpty()){
				TakeTicketIdcode tti = null;
				for(int i=0;i<ttiList.size();i++){
					tti = (TakeTicketIdcode) ttiList.get(i);
					TakeTicketDetail ttd = null;
					//查询产品单位信息
					if(!funitMap.containsKey(tti.getProductid())){
						List fnList = appfn.getFUnitByProductID(tti.getProductid());
						if(fnList!=null && !fnList.isEmpty()){
							Map<Integer,Double> tempMap = new HashMap<Integer, Double>();
							for(int j=0;j<fnList.size();j++){
								FUnit fn = (FUnit) fnList.get(j);
								tempMap.put(fn.getFunitid(), fn.getXquantity());
							}
							funitMap.put(tti.getProductid(), tempMap);
						}
					}
					//查询产品信息
					if(!pMap.containsKey(tti.getProductid())){
						Product pr = appPro.getProductByID(tti.getProductid());
						pMap.put(tti.getProductid(), pr);
					}
					
					//判断单据详情中是否存在该产品信息
					if(ttdMap.containsKey(tti.getProductid())){
						ttd = ttdMap.get(tti.getProductid());
						//数量
						ttd.setQuantity(funitMap.get(tti.getProductid()).get(tti.getUnitid()) + ttd.getQuantity());
						//箱数
						ttd.setBoxnum(0);
						//散数
						ttd.setScatternum(funitMap.get(tti.getProductid()).get(tti.getUnitid()) + ttd.getScatternum());
					}
					else{
						ttd = new TakeTicketDetail();
						Product p = pMap.get(tti.getProductid());
						ttd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("take_ticket_detail", 0, "")));
						ttd.setProductid(p.getId());
						ttd.setProductname(p.getProductname());
						ttd.setSpecmode(p.getSpecmode());
						ttd.setUnitid(tti.getUnitid());
						ttd.setBatch(tti.getBatch());
						ttd.setUnitprice(0d);
						ttd.setCost(0d);
						//数量
						ttd.setQuantity(funitMap.get(tti.getProductid()).get(tti.getUnitid()));
						//箱数
						ttd.setBoxnum(0);
						//散数
						ttd.setScatternum(funitMap.get(tti.getProductid()).get(tti.getUnitid()));
						ttd.setTtid(tt.getId());
						ttd.setIsPicked(0);
						ttdMap.put(tti.getProductid(), ttd);
					}
				}
			}
		}
		
		//保存单据详情信息
		for(Map.Entry<String, TakeTicketDetail> entry : ttdMap.entrySet()){
			attd.addTakeTicketDetail(entry.getValue());
		}
		
	}

	/**
	 * 保存条码信息
	 * @param tt
	 * @param p
	 * @param idcode
	 * @param lcode
	 * @param batch
	 * @param producedate
	 * @param unitid
	 * @throws Exception
	 */
	public void addTakeTicketIdcode(TakeTicket tt, Product p, String idcode,
			String lcode, String batch, String producedate, int unitid)
			throws Exception {
		TakeTicketIdcode tti = new TakeTicketIdcode();
//		tti.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("take_ticket_idcode", 0, "")));
		tti.setLcode(lcode);
		tti.setIsidcode(1);
		tti.setTtid(tt.getId());
		tti.setWarehousebit("000");
		tti.setProductid(p.getId());
		tti.setIssplit(0);
		tti.setBatch(batch);
		tti.setProducedate(producedate);
		tti.setUnitid(unitid);
		tti.setQuantity(1d);
		tti.setPackquantity(1d);
		tti.setIdcode(idcode);
		tti.setStartno(idcode);
		tti.setEndno(idcode);
		tti.setMakedate(DateUtil.getCurrentDate());
		appTakeTicketIdcode.addTakeTicketIdcode(tti);

	}

}
