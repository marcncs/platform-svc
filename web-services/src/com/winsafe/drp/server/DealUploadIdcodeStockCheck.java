package com.winsafe.drp.server;

import java.util.List;

import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppStockCheck;
import com.winsafe.drp.dao.AppStockCheckDetail;
import com.winsafe.drp.dao.AppStockCheckIdcode;
import com.winsafe.drp.dao.StockCheck;
import com.winsafe.drp.dao.StockCheckDetail;
import com.winsafe.drp.dao.StockCheckIdcode;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;

/** 
 * @author jerry
 * @version 2009-8-17 下午04:36:33 
 * www.winsafe.cn 
 */
public class DealUploadIdcodeStockCheck extends DealUploadIdcode{
	private AppStockCheck apptt = new AppStockCheck();
	private AppStockCheckDetail appttd = new AppStockCheckDetail();
	private AppStockCheckIdcode app = new AppStockCheckIdcode();
	private AppFUnit af = new AppFUnit();
	
	
	public DealUploadIdcodeStockCheck(String filepath, String fileaddress, int iuid) {
		super(filepath, fileaddress, iuid);
	}

	public String addIdcode(String uploadidcode) {		
		String t_ret=null;
		try{
			
			String idcode = crs.getIdcode(uploadidcode);
			
			String billno = crs.getBillNo(uploadidcode);
			
			String batch = crs.getBatch(idcode);
			
			if ( billno.equals("") || billno.indexOf("SC") == -1 ){
				writeTxt(uploadidcode+"[单据编号错误]");
				return null;
			}
			
			StockCheck tt = apptt.getStockCheckByID(billno);
			if ( tt == null  ){
				writeTxt(uploadidcode+"[单据编号不存在]");
				t_ret= billno;
			}
			else
			{
			if ( tt.getIsaudit()==1  ){
				writeTxt(uploadidcode+"[单据编号单据已复核]");
				return null;
			}
			}
			String productid = appicode.getICodeByLcode(crs.getLcode(idcode)).getProductid();

			List<StockCheckDetail> ttdlist = appttd.getStockCheckDetailBySmID(billno, productid);
			if ( ttdlist == null || ttdlist.isEmpty() ){
				writeTxt(uploadidcode+"[单据产品错误]");
				return null;
			}
//			boolean batchError = true;
//			for ( StockCheckDetail ttd : ttdlist ){
//				if ( batch.equalsIgnoreCase(ttd.getBatch()) ){
//					batchError = false;
//					break;
//				}
//			}
//			if ( batchError ){
//				writeTxt(fileName,uploadidcode+"[批次不正确]");
//				return;
//			}
			
			
			if ( app.getStockCheckIdcodeByCidcode(productid, billno, idcode) != null ){
				writeTxt(uploadidcode+"[条码已经存在当前列表中]");
				return null;
			}
			
			StockCheckIdcode ic =  app.getStockCheckIdcodeByscid(billno, idcode);
			
//			if ( ic == null ){
//								
//				String startno = crs.getStartNo(idcode);
//				String endno = crs.getEndNo(idcode);
//				Idcode bic = appidcode.getIdcodeByWLM(startno, endno);

//				if ( bic == null || !bic.getWarehouseid().equals(tt.getWarehouseid()) ){
//					writeTxt(fileName,uploadidcode+"[条码不存在或者不存在仓位中]");
//					return;					
//				}else{
//					if ( appidcode.isBreak(startno, endno) ){
//						writeTxt(fileName,uploadidcode+"[条码已被拆过]");
//						return;
//					}
//				}
//				warehousebit = bic.getWarehousebit();
//				issplit=1;
//			}else{
				if ( ic != null && ic.getQuantity().doubleValue() != ic.getFquantity().doubleValue() ) {
						//|| !ic.getWarehouseid().equals(tt.getWarehouseid())){
					writeTxt(uploadidcode+"[条码已拆过]");
					return null;	
				}				
			//}
			
			HibernateUtil.currentSession(true);
			if ( ic != null ){
				
				ic.setCquantity(ic.getQuantity());	
				ic.setCidcode(idcode);
				app.updStockCheckIdcode(ic);
				appttd.updCheckQuantity(billno, ic.getWarehousebit(), ic.getProductid(),
						ic.getBatch(), ic.getQuantity());
			}else{
				StockCheckIdcode pi = new StockCheckIdcode();
				pi.setScid(billno);
				pi.setProductid(productid);
				pi.setWarehousebit(crs.getWarehouseBit(uploadidcode));
				pi.setBatch(batch);
				pi.setProducedate(crs.getProduceDate(idcode));
				pi.setValidate("");
				pi.setUnitid(appcu.getCodeUnitByID(crs.getUcode(idcode)).getUnitid());
				pi.setQuantity(0d);
				pi.setCquantity(af.getQuantity(pi.getProductid(), pi.getUnitid(), 1));
				pi.setFquantity(pi.getCquantity());
				pi.setPackquantity(crs.getRealQuantity(idcode));
				pi.setIdcode("");
				pi.setCidcode(idcode);
				pi.setLcode(crs.getLcode(idcode));
				pi.setStartno(crs.getStartNo(idcode));
				pi.setEndno(crs.getEndNo(idcode));		
				pi.setMakedate(DateUtil.getCurrentDate());
				
				StockCheckIdcode parentIdcode = app.getIdcodeByWLM(pi.getStartno(), pi.getEndno(), billno);				
				if ( parentIdcode != null ){					
					
					pi.setQuantity(pi.getCquantity());
					parentIdcode.setCquantity(parentIdcode.getCquantity()+pi.getCquantity());
					app.updStockCheckIdcode(parentIdcode);
				}				
				app.addStockCheckIdcode(pi);
				appttd.updCheckQuantity(billno, pi.getWarehousebit(), pi.getProductid(),
						pi.getBatch(), pi.getCquantity());
			}			
			valinum+=1;
			HibernateUtil.commitTransaction();
		}catch ( Exception e ){
			HibernateUtil.rollbackTransaction();
			e.printStackTrace();
			writeTxt(uploadidcode+"[条码不符合规则]");
		}finally{
			HibernateUtil.closeSession();
		}
		return t_ret;
	}
	
}
