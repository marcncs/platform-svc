package com.winsafe.drp.server;

import java.util.List;

import com.winsafe.drp.dao.AppSaleTrades;
import com.winsafe.drp.dao.AppSaleTradesDetail;
import com.winsafe.drp.dao.AppSaleTradesIdcode;
import com.winsafe.drp.dao.Idcode;
import com.winsafe.drp.dao.SaleTrades;
import com.winsafe.drp.dao.SaleTradesDetail;
import com.winsafe.drp.dao.SaleTradesIdcode;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

/** 
 * @author jerry
 * @version 2009-8-17 下午04:36:33 
 * www.winsafe.cn 
 */
public class DealUploadIdcodeSaleTrades extends DealUploadIdcode{
	private AppSaleTrades apppi = new AppSaleTrades();
	private AppSaleTradesDetail apppd = new AppSaleTradesDetail();
	private AppSaleTradesIdcode app = new AppSaleTradesIdcode();
	
	
	public DealUploadIdcodeSaleTrades(String filepath, String fileaddress, int iuid) {
		super(filepath, fileaddress, iuid);
	}

	public String addIdcode(String uploadidcode) {		
		String t_ret =null;
		try{
			
			String idcode = crs.getIdcode(uploadidcode);
			
			String billno = crs.getBillNo(uploadidcode);
			
			if ( billno.equals("") || billno.indexOf("ST") == -1 ){
				writeTxt(uploadidcode+"[单据编号错误]");
				return null;
			}
			
			SaleTrades p = apppi.getSaleTradesByID(billno);
			if ( p == null ){
				writeTxt(uploadidcode+"[单据编号不存在]");
				t_ret=billno;
			}
			else
			{
			if ( p.getIsendcase()==1  ){
				writeTxt(uploadidcode+"[单据编号单据已复核]");
				return null;
			}
			}
			
			String productid = appicode.getICodeByLcode(crs.getLcode(idcode)).getProductid();
			List<SaleTradesDetail> ttdlist = apppd.getSaleTradesDetailByPiidPid(billno, productid);
			if ( ttdlist == null || ttdlist.isEmpty() ){
				writeTxt(uploadidcode+"[单据产品错误]");
				return null;
			}						
			if ( app.getSaleTradesIdcodeByidcode(productid, billno, idcode) != null ){
				writeTxt(uploadidcode+"[条码已经存在当前列表中]");
				return null;
			}
			
			Idcode ic =  appidcode.getIdcodeById(idcode);
			if ( ic != null && ic.getIsuse() == 1 ){
				writeTxt(uploadidcode+"该条码已经存在，不能新增！");
				return null;
			}
			if ( ic != null && ic.getIsuse() == 0 && ic.getQuantity().doubleValue() != ic.getFquantity().doubleValue()){
				writeTxt(uploadidcode+"该条码已经存在，不能新增！");
				return null;
			}
			if ( ic == null ){
				
				String startno = crs.getStartNo(idcode);
				String endno = crs.getEndNo(idcode);
				if ( appidcode.isAlreadyByWLM(startno, endno) ){
					writeTxt(uploadidcode+"该条码已经存在，不能新增！");
					return null;
				}
				if ( appidcode.isBreak(startno, endno) ){
					writeTxt(uploadidcode+"该条码已经存在，不能新增！");
					return null;
				}
			}
			
			HibernateUtil.currentSession(true);
			SaleTradesIdcode pi = new SaleTradesIdcode();
			pi.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("sale_trades_idcode", 0, "")));
			pi.setStid(billno);
			pi.setProductid(productid);
			pi.setIsidcode(1);
			pi.setWarehousebit(crs.getWarehouseBit(uploadidcode));
			pi.setBatch(crs.getBatch(idcode));
			pi.setProducedate(crs.getProduceDate(idcode));
			pi.setValidate("");
			pi.setUnitid(appcu.getCodeUnitByID(crs.getUcode(idcode)).getUnitid());
			pi.setQuantity(1d);
			pi.setPackquantity(crs.getRealQuantity(idcode));
			pi.setIdcode(idcode);
			pi.setLcode(crs.getLcode(idcode));
			pi.setStartno(crs.getStartNo(idcode));
			pi.setEndno(crs.getEndNo(idcode));		
			pi.setMakedate(DateUtil.getCurrentDate());
			app.addSaleTradesIdcode(pi);
			valinum+=1;
			HibernateUtil.commitTransaction();
		}catch ( Exception e ){
			HibernateUtil.rollbackTransaction();
			writeTxt(uploadidcode+"[条码不符合规则]");
		}finally{
			HibernateUtil.closeSession();
		}
		return t_ret;
	}
	
}
