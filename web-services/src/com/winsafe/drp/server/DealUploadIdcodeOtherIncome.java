package com.winsafe.drp.server;

import java.util.List;

import com.winsafe.drp.dao.AppOtherIncome;
import com.winsafe.drp.dao.AppOtherIncomeDetail;
import com.winsafe.drp.dao.AppOtherIncomeIdcode;
import com.winsafe.drp.dao.Idcode;
import com.winsafe.drp.dao.OtherIncome;
import com.winsafe.drp.dao.OtherIncomeDetail;
import com.winsafe.drp.dao.OtherIncomeIdcode;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

/** 
 * @author jerry
 * @version 2009-8-17 下午04:36:33 
 * www.winsafe.cn 
 */
public class DealUploadIdcodeOtherIncome extends DealUploadIdcode{
	private AppOtherIncome apppi = new AppOtherIncome();
	private AppOtherIncomeDetail apppd = new AppOtherIncomeDetail();
	private AppOtherIncomeIdcode app = new AppOtherIncomeIdcode();
	
	
	public DealUploadIdcodeOtherIncome(String filepath, String fileaddress, int iuid) {
		super(filepath, fileaddress, iuid);
	}

	public String addIdcode(String uploadidcode) {	
		String t_ret=null;
		try{
			
			String idcode = crs.getIdcode(uploadidcode);
			
			String billno = crs.getBillNo(uploadidcode);
			
			if ( billno.equals("") || billno.indexOf("PY") == -1 ){
				writeTxt(uploadidcode+"[单据编号错误]");
				return null;
			}
			
			OtherIncome p = apppi.getOtherIncomeByID(billno);
			if ( p == null){
				writeTxt(uploadidcode+"[单据编号不存在]");
				t_ret = billno;
			}
			else
			{
				if(p.getIsaudit()==1  ){
					writeTxt(uploadidcode+"[单据编号单据已复核]");
					return null;
				}
			}
			
			String productid = appicode.getICodeByLcode(crs.getLcode(idcode)).getProductid();
			String batch = crs.getBatch(idcode);
			List<OtherIncomeDetail> ttdlist = apppd.getOtherIncomeDetailByPiidPid(billno, batch, productid);
			if ( ttdlist == null || ttdlist.isEmpty() ){
				writeTxt(uploadidcode+"[单据产品错误]");
				return null;
			}						
			if ( app.getOtherIncomeIdcodeByidcode(productid, billno, idcode) != null ){
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
			OtherIncomeIdcode pi = new OtherIncomeIdcode();
			pi.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("other_income_idcode", 0, "")));
			pi.setOiid(billno);
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
			app.addOtherIncomeIdcode(pi);
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
