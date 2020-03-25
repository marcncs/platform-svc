package com.winsafe.drp.server;

import java.util.ArrayList;
import java.util.List;

import com.winsafe.drp.dao.AppDrawShipmentBill;
import com.winsafe.drp.dao.AppDrawShipmentBillDetail;
import com.winsafe.drp.dao.AppDrawShipmentBillIdcode;
import com.winsafe.drp.dao.Idcode;
import com.winsafe.drp.dao.DrawShipmentBill;
import com.winsafe.drp.dao.DrawShipmentBillDetail;
import com.winsafe.drp.dao.DrawShipmentBillIdcode;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

/** 
 * @author jerry
 * @version 2009-8-17 下午04:36:33 
 * www.winsafe.cn 
 */
public class DealUploadIdcodeDrawShipmentBill extends DealUploadIdcode{
	private AppDrawShipmentBill apptt = new AppDrawShipmentBill();
	private AppDrawShipmentBillDetail appttd = new AppDrawShipmentBillDetail();
	private AppDrawShipmentBillIdcode app = new AppDrawShipmentBillIdcode();
	//private ArrayList<String> billErrorList = new ArrayList<String>();
	
	
	public DealUploadIdcodeDrawShipmentBill(String filepath, String fileaddress, int iuid) {
		super(filepath, fileaddress, iuid);
	}

	public String addIdcode(String uploadidcode) {
		String t_ret=null;
		try{
			//int issplit = 0;
			
			String idcode = crs.getIdcode(uploadidcode);
			
			String billno = crs.getBillNo(uploadidcode);
			
			String batch = crs.getBatch(idcode);
			
			if ( billno.equals("") || billno.indexOf("DS") == -1 ){
				writeTxt(uploadidcode+"[单据编号错误]");
				return null;
			}
			
			DrawShipmentBill tt = apptt.getDrawShipmentBillByID(billno);
			if ( tt == null  ){
				writeTxt(uploadidcode+"[单据编号不存在]");
				t_ret =billno;
				//return;
			}
			else
			{
			if( tt.getIsaudit()==1 || tt.getIsblankout() == 1)
			{
				writeTxt(uploadidcode+"[单据已复核、作废]");
			    return null;				
			}	
			}
		
			
			
			String productid = appicode.getICodeByLcode(crs.getLcode(idcode)).getProductid();

			List<DrawShipmentBillDetail> ttdlist = appttd.getDrawShipmentBillDetailByTtidPid(billno, productid);
			if ( ttdlist == null || ttdlist.isEmpty() ){
				writeTxt(uploadidcode+"[单据产品错误]");
				return null;
			}
/*			boolean batchError = true;
			for ( DrawShipmentBillDetail ttd : ttdlist ){
				if ( batch.equalsIgnoreCase(ttd.getBatch()) ){
					batchError = false;
					break;
				}
			}
			if ( batchError ){
				writeTxt(uploadidcode+"[批次不正确]");
				return;
			}*/
			
		
			if ( app.getDrawShipmentBillIdcodeByidcode(productid, billno, idcode) != null ){
				writeTxt(uploadidcode+"[条码已经存在当前列表中]");
				return null;
			}
			
			Idcode ic =  appidcode.getIdcodeById(idcode);
			String warehousebit = ic.getWarehousebit();
			if ( ic == null ){
								
				String startno = crs.getStartNo(idcode);
				String endno = crs.getEndNo(idcode);
				Idcode bic = appidcode.getIdcodeByWLM(startno, endno);

				if ( bic == null || !bic.getWarehouseid().equals(tt.getWarehouseid()) ){
					writeTxt(uploadidcode+"[条码不存在或者不存在仓位中]");
					return null;					
				}else{
					if ( appidcode.isBreak(startno, endno) ){
						writeTxt(uploadidcode+"[条码已被拆过]");
						return null;
					}
				}
				warehousebit = bic.getWarehousebit();
				//issplit=1;
			}else{
				if ( ic.getIsuse() == 0 || !ic.getWarehouseid().equals(tt.getWarehouseid())){
					writeTxt(uploadidcode+"[条码已出库]");
					return null;	
				}				
			}
			
			HibernateUtil.currentSession(true);
			DrawShipmentBillIdcode di = new DrawShipmentBillIdcode();
			di.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("draw_shipment_bill_idcode", 0, "")));
			di.setdsid(billno);
			di.setProductId(productid);
			di.setIsIdcode(1);
			di.setWarehouseBit(warehousebit);
			di.setBatch(batch);
			di.setProduceDate(crs.getProduceDate(idcode));
			di.setValiDate("");
			di.setUnitId(appcu.getCodeUnitByID(crs.getUcode(idcode)).getUnitid());
			di.setQuantity(1d);
			di.setPackQuantity(crs.getRealQuantity(idcode));
			di.setIdcode(idcode);
			di.setLcode(crs.getLcode(idcode));
			di.setStartNo(crs.getStartNo(idcode));
			di.setEndNo(crs.getEndNo(idcode));		
			//di.setIsSplit(issplit);
			di.setMakeDate(DateUtil.getCurrentDate());
   		    app.addDrawShipmentBillIdcode(di);
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

