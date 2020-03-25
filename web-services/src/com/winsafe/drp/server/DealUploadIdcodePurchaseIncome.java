package com.winsafe.drp.server;

import java.util.ArrayList;
import java.util.List;

import com.winsafe.drp.dao.AppPurchaseIncome;
import com.winsafe.drp.dao.AppPurchaseIncomeDetail;
import com.winsafe.drp.dao.AppPurchaseIncomeIdcode;
import com.winsafe.drp.dao.Idcode;
import com.winsafe.drp.dao.PurchaseIncome;
import com.winsafe.drp.dao.PurchaseIncomeDetail;
import com.winsafe.drp.dao.PurchaseIncomeIdcode;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

/** 
 * @author jerry
 * www.winsafe.cn 
 */
public class DealUploadIdcodePurchaseIncome extends DealUploadIdcode{
	private AppPurchaseIncome apppi = new AppPurchaseIncome();
	private AppPurchaseIncomeDetail apppd = new AppPurchaseIncomeDetail();
	private AppPurchaseIncomeIdcode app = new AppPurchaseIncomeIdcode();
	private ArrayList<String> billErrorList = new ArrayList<String>();
	
	
	public DealUploadIdcodePurchaseIncome(String filepath, String fileaddress, int iuid) {
		super(filepath, fileaddress, iuid);
	}

	public String addIdcode(String uploadidcode) {	
		String t_ret=null;
		try{
			
			String idcode = crs.getIdcode(uploadidcode);
			String billno = crs.getBillNo(uploadidcode);
			
			if ( billno.equals("") || billno.indexOf("PI") == -1 ){
				writeTxt(uploadidcode+"[单据编号错误]");				
				return null;
			}
			
			PurchaseIncome p = apppi.getPurchaseIncomeByID(billno);
			if ( p == null || p.getIsaudit()==1  ){
				writeTxt(uploadidcode+"[单据编号不存在]");				
				//加入单据编号错误列表
				t_ret = billno;
			
			}
			else
			{ if(p.getIsaudit()==1  ){
					writeTxt(uploadidcode+"[单据编号已复核]");				
					//加入单据编号错误列表
				  return null;
				}
			}
			String productid = appicode.getICodeByLcode(crs.getLcode(idcode)).getProductid();
			List<PurchaseIncomeDetail> ttdlist = apppd.getPurchaseIncomeDetailByPiidPid(billno, productid);
			if ( ttdlist == null || ttdlist.isEmpty() ){
				writeTxt(uploadidcode+"[单据产品错误]");
				return null;
			}						
			if ( app.getPurchaseIncomeIdcodeByidcode(productid, billno, idcode) != null ){
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
			PurchaseIncomeIdcode pi = new PurchaseIncomeIdcode();
			pi.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("purchase_income_idcode", 0, "")));
			pi.setPiid(billno);
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
			app.addPurchaseIncomeIdcode(pi);
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
