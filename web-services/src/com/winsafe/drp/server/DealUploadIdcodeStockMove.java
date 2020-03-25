package com.winsafe.drp.server;

import java.util.ArrayList;
import java.util.List;

import com.winsafe.drp.dao.AppStockMove;
import com.winsafe.drp.dao.AppStockMoveDetail;
import com.winsafe.drp.dao.AppStockMoveIdcode;
import com.winsafe.drp.dao.Idcode;
import com.winsafe.drp.dao.StockMove;
import com.winsafe.drp.dao.StockMoveDetail;
import com.winsafe.drp.dao.StockMoveIdcode;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

/** 
 * @author jerry
 * @version 2009-8-17 下午04:36:33 
 * www.winsafe.cn 
 */
public class DealUploadIdcodeStockMove extends DealUploadIdcode{
	private AppStockMove apppi = new AppStockMove();
	private AppStockMoveDetail apppd = new AppStockMoveDetail();
	private AppStockMoveIdcode app = new AppStockMoveIdcode();
	//private ArrayList<String> billErrorList = new ArrayList<String>();
	
	
	public DealUploadIdcodeStockMove(String filepath, String fileaddress, int iuid) {
		super(filepath, fileaddress, iuid);
	}

	public String addIdcode(String uploadidcode) {	
		String t_ret=null;
		try{
			
			String idcode = crs.getIdcode(uploadidcode);
			
			String billno = crs.getBillNo(uploadidcode);
			
			if ( billno.equals("") || billno.indexOf("SM") == -1 ){
				writeTxt(uploadidcode+"[单据编号错误]");
				return null;
			}
			
			StockMove p = apppi.getStockMoveByID(billno);
			if ( p == null  ){
				writeTxt(uploadidcode+"[单据编号不存在]");
				t_ret = billno;
			}
			else
			{
			if(p.getIscomplete()==1)
			{
				writeTxt(uploadidcode+"[单据已签收]");			
			    return null;			
			}			
			}
			
			//String productid = appicode.getICodeByLcode(crs.getLcode(idcode)).getProductid();
			String productid = appicode.getICodeByLcode(idcode.substring(0, 3)).getProductid();
			List<StockMoveDetail> ttdlist = apppd.getStockMoveDetailByPiidPid(billno, productid);
/*			if ( ttdlist == null || ttdlist.isEmpty() ){
				writeTxt(uploadidcode+"[单据产品错误]");
				return null;
			}*/						
			if ( app.getStockMoveIdcodeByidcode(productid, billno, idcode) != null ){
				writeTxt(uploadidcode+"[条码已经存在当前列表中]");
				return null;
			}
			
			Idcode ic =  appidcode.getIdcodeById(idcode);
			if ( ic != null && ic.getIsuse() == 1 ){
				writeTxt(uploadidcode+"该条码已经存在，不能新增！");
				return null;
			}
			if ( ic != null && ic.getIsuse() == 0 && ic.getQuantity().doubleValue() != ic.getFquantity().doubleValue()){
				writeTxt(uploadidcode+"该条码已经存在，数量不一致,不能新增！");
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
			StockMoveIdcode pi = new StockMoveIdcode();
			pi.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("stock_move_idcode", 0, "")));
			pi.setSmid(billno);
			pi.setProductid(productid);
			pi.setIsidcode(1);
			pi.setWarehousebit(crs.getWarehouseBit(uploadidcode));
			pi.setBatch(crs.getBatch(idcode));
			pi.setProducedate(crs.getProduceDate(idcode));
			pi.setValidate("");
			pi.setUnitid(appcu.getCodeUnitByID(crs.getUcode(idcode)).getUnitid());
			pi.setQuantity(1d);
			pi.setPackquantity(crs.getRealQuantity(idcode));
			pi.setIdcode(idcode);//idcode
			pi.setLcode(crs.getLcode(idcode));
			pi.setStartno(crs.getStartNo(idcode));
			pi.setEndno(crs.getEndNo(idcode));		
			pi.setMakedate(DateUtil.getCurrentDate());
			app.addStockMoveIdcode(pi);
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
