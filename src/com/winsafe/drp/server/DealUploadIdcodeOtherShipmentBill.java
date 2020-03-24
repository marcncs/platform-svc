package com.winsafe.drp.server;

import java.util.List;

import org.hibernate.hql.ast.tree.BinaryArithmeticOperatorNode;

import com.winsafe.drp.dao.AppOtherShipmentBill;
import com.winsafe.drp.dao.AppOtherShipmentBillDetail;
import com.winsafe.drp.dao.AppOtherShipmentBillIdcode;
import com.winsafe.drp.dao.Idcode;
import com.winsafe.drp.dao.OtherShipmentBill;
import com.winsafe.drp.dao.OtherShipmentBillDetail;
import com.winsafe.drp.dao.OtherShipmentBillIdcode;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

/** 
 * @author jerry
 * @version 2009-8-17 下午04:36:33 
 * www.winsafe.cn 
 */
public class DealUploadIdcodeOtherShipmentBill extends DealUploadIdcode{
	private AppOtherShipmentBill apptt = new AppOtherShipmentBill();
	private AppOtherShipmentBillDetail appttd = new AppOtherShipmentBillDetail();
	private AppOtherShipmentBillIdcode app = new AppOtherShipmentBillIdcode();
	
	
	public DealUploadIdcodeOtherShipmentBill(String filepath, String fileaddress, int iuid) {
		super(filepath, fileaddress, iuid);
	}

	public String addIdcode(String uploadidcode) {	
        String t_ret=null;
		try{
			int issplit = 0;
			
			String idcode = crs.getIdcode(uploadidcode);
			
			String billno = crs.getBillNo(uploadidcode);
			
			String batch = crs.getBatch(idcode);
			
			if ( billno.equals("") || billno.indexOf("PK") == -1 ){
				writeTxt(uploadidcode+"[单据编号错误]");
				return null;
			}
			
			OtherShipmentBill tt = apptt.getOtherShipmentBillByID(billno);
			if ( tt == null ){
				writeTxt(uploadidcode+"[单据编号不存在]");
				t_ret = billno;
			}
			else
			{
			if ( tt.getIsaudit()==1 || tt.getIsblankout() == 1 ){
				writeTxt(uploadidcode+"[单据编号单据已复核、作废]");
				return null;
			}
			}
			
			String productid = appicode.getICodeByLcode(crs.getLcode(idcode)).getProductid();

			List<OtherShipmentBillDetail> ttdlist = appttd.getOtherShipmentBillDetailByTtidPid(billno, productid);
			if ( ttdlist == null || ttdlist.isEmpty() ){
				writeTxt(uploadidcode+"[单据产品错误]");
				return null;
			}
			boolean batchError = true;
			for ( OtherShipmentBillDetail ttd : ttdlist ){
				if ( batch.equalsIgnoreCase(ttd.getBatch()) ){
					batchError = false;
					break;
				}
			}
			if ( batchError ){
				writeTxt(uploadidcode+"[批次不正确]");
				return null;
			}
			
		
			if ( app.getOtherShipmentBillIdcodeByidcode(productid, billno, idcode) != null ){
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
				issplit=1;
			}else{
				if ( ic.getIsuse() == 0 || !ic.getWarehouseid().equals(tt.getWarehouseid())){
					writeTxt(uploadidcode+"[条码已出库]");
					return null;	
				}				
			}
			
			HibernateUtil.currentSession(true);
			OtherShipmentBillIdcode pi = new OtherShipmentBillIdcode();
			pi.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("other_shipment_bill_idcode", 0, "")));
			pi.setOsid(billno);
			pi.setProductid(productid);
			pi.setIsidcode(1);
			pi.setWarehousebit(warehousebit);
			pi.setBatch(batch);
			pi.setProducedate(crs.getProduceDate(idcode));
			pi.setValidate("");
			pi.setUnitid(appcu.getCodeUnitByID(crs.getUcode(idcode)).getUnitid());
			pi.setQuantity(1d);
			pi.setPackquantity(crs.getRealQuantity(idcode));
			pi.setIdcode(idcode);
			pi.setLcode(crs.getLcode(idcode));
			pi.setStartno(crs.getStartNo(idcode));
			pi.setEndno(crs.getEndNo(idcode));		
			pi.setIssplit(issplit);
			pi.setMakedate(DateUtil.getCurrentDate());
			app.addOtherShipmentBillIdcode(pi);
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
