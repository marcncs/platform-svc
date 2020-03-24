package com.winsafe.drp.server;

import com.winsafe.drp.dao.AppICode;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppIdcodeUploadLog;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.Idcode;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketIdcode;
import com.winsafe.drp.exception.IdcodeException;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.UploadErrorMsg;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.sap.dao.AppCartonCode;
import com.winsafe.sap.pojo.CartonCode;

/**
 * 条码新增service
 * 
 * @Title: AddTakeTicketIdcodeiiService.java
 * @author: wenping
 * @CreateTime: Jan 7, 2013 1:23:29 PM
 * @version:
 */
public class AddTakeTicketIdcodeiiService {

	private AppTakeTicketIdcode app = new AppTakeTicketIdcode();
	private AppICode appi = new AppICode();
	private String lcode;
	AppTakeTicket att = new AppTakeTicket();
	protected AppIdcode appidcode = new AppIdcode();
	private TakeTicket tt = null;
	private AppIdcodeUploadLog appIdcodeUploadLog = new AppIdcodeUploadLog();
	private AppCartonCode appCartonCode = new AppCartonCode();

	public void dealIdcode(String billid, String idcode, String productid,String userid, String organId) throws Exception {

		tt = att.getTakeTicketById(billid);
		if (tt == null) {
			throw new IdcodeException(UploadErrorMsg.getError(UploadErrorMsg.E00001, billid));
		}
		Nidcode aNidcode = new Nidcode();
		//验证条码的正确性
		String errorMsg = validateIdcode(idcode, tt, productid, aNidcode);
		if(errorMsg != null){
			//手工条码上传与采集器条码上传的单据类型不一致，添加错误日志时使用统一的类型
			Integer bSort = 0;
			if(tt.getBsort() == 1) {
				bSort = 4;
			} else if(tt.getBsort() == 2) {
				bSort = 5;
			} else {
				if(tt.getBillno().startsWith("OW")) {
					bSort = 8;
				} else {
					bSort = 20;
				}
				
			}
			// 保存条码的特殊信息日志
			appIdcodeUploadLog.addIdcodeUploadLogs(errorMsg, tt.getBillno(), userid, bSort,organId, tt.getWarehouseid(), idcode);
		}
		//更新条码状态为不可用
		appidcode.updIsUse(idcode, 0);
		// 增加条码
		TakeTicketIdcode pi = new TakeTicketIdcode();
//		pi.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("take_ticket_idcode", 0, "")));
		pi.setTtid(billid);
		pi.setProductid(aNidcode.productid);
		pi.setIsidcode(1);
		//pi.setWarehousebit(aNidcode.warehousebit);
		pi.setBatch(aNidcode.batch);
		pi.setProducedate(aNidcode.productDate);
		pi.setVad("");
		pi.setUnitid(aNidcode.unitid);
		pi.setQuantity(1d);
		pi.setPackquantity(aNidcode.packQuantity);
		pi.setIdcode(idcode);
		pi.setLcode(lcode);
		pi.setStartno(aNidcode.startno);
		pi.setEndno(aNidcode.endno);
		pi.setIssplit(aNidcode.issplit);
		pi.setMakedate(DateUtil.getCurrentDate());
		pi.setWarehousebit(Constants.WAREHOUSE_BIT_DEFAULT);
		app.addTakeTicketIdcode(pi);
	}

	private String validateIdcode(String ic, TakeTicket tt, String productid, Nidcode aNidcode) throws Exception{
		// 记录特殊的错误信息(只记录,不报错)
		String errorMsg = null;
		//判断条码数量是否超过产品数量
		if(att.isIdcodeQtyGreatOrEqualThanProductQty(tt.getId(), productid)) {
			throw new IdcodeException(UploadErrorMsg.getError(UploadErrorMsg.E00016));
		}
		//判断条码是否存在
		Idcode idcode = appidcode.getIdcodeByCode(ic);
		if(idcode == null){
			throw new IdcodeException(UploadErrorMsg.getError(UploadErrorMsg.E00003, ic));
		}
		// 判断条码的产品是否符合条件
		if(!idcode.getProductid().equals(productid)){
			throw new IdcodeException(UploadErrorMsg.getError(UploadErrorMsg.E00108, ic));
		}
		//判断条码是否可用
		if(idcode.getIsuse() != null && idcode.getIsuse() == 0 && idcode.getWarehouseid().equals(tt.getWarehouseid())){
			throw new IdcodeException(UploadErrorMsg.getError(UploadErrorMsg.E00004, ic));
		}
		TakeTicketIdcode tti = app.getTakeTicketIdcodeByidcode(productid, tt.getId(), ic);
		if(tti != null){
			throw new IdcodeException(UploadErrorMsg.getError(UploadErrorMsg.E00006, ic));
		}
		// 判断条码是否在当前仓库(只记录错误信息,不报错)
		if(!idcode.getWarehouseid().equals(tt.getWarehouseid())){
			errorMsg = UploadErrorMsg.getError(UploadErrorMsg.E00005, ic);
		}
		aNidcode.batch = idcode.getBatch();
		aNidcode.productid = idcode.getProductid();
		aNidcode.productDate = idcode.getProducedate();
		aNidcode.warehousebit = idcode.getWarehousebit();
		aNidcode.unitid = idcode.getUnitid();
		aNidcode.packQuantity = idcode.getPackquantity();
		aNidcode.startno = idcode.getStartno();
		aNidcode.endno = idcode.getEndno();
		aNidcode.warehousebit = idcode.getWarehousebit();
		
		return errorMsg;
		
	}
	

	public class Nidcode {
		public String batch;
		public String productid;
		public String productDate;
		public String warehousebit = "000";
		public int issplit = 0;
		public Integer unitid;
		public String scanFlag;
		public Double packQuantity = 1d;
		public String startno;
		public String endno;
		public String warehouseid;
		public Product product;
	}

}
