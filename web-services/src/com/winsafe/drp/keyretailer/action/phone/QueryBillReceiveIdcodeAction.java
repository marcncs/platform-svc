package com.winsafe.drp.keyretailer.action.phone;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppStockAlterMoveDetail;
import com.winsafe.drp.dao.AppStockAlterMoveIdcode;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.StockAlterMoveDetail;
import com.winsafe.drp.dao.StockAlterMoveIdcode;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.keyretailer.dao.AppSBonusSetting;
import com.winsafe.drp.keyretailer.pojo.SBonusSetting;
import com.winsafe.drp.metadata.DealerType;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ResponseUtil;


public class QueryBillReceiveIdcodeAction extends BaseAction {
	private Logger logger = Logger.getLogger(QueryBillReceiveIdcodeAction.class);
	
	private AppUsers appUsers = new AppUsers();
	private AppStockAlterMoveDetail asamd = new AppStockAlterMoveDetail();
	private AppStockAlterMoveIdcode asami = new AppStockAlterMoveIdcode();
	private DecimalFormat decimalFormat = new DecimalFormat("###0.00");
	private DecimalFormat quantityFormat = new DecimalFormat("###0");
	
	
	Map<String, String> unitMap = null;

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String username = request.getParameter("Username"); //登陆名
			String scannerno = request.getParameter("IMEI_number"); 
			String billNo = request.getParameter("billNo"); 
			String productId = request.getParameter("productId"); 
			// 判断用户是否存在
			Users loginUsers = appUsers.getUsers(username);
			String organid = loginUsers.getMakeorganid();
			AppOrgan apo = new AppOrgan();
			int  organmodel = apo.getOrganByID(organid).getOrganModel();
			System.out.println("==========organid="+organid);
			System.out.println("==========organmodel="+organmodel);
			BillReceiveIdcodeJson brij = null;
			
			if(loginUsers != null){
				StockAlterMoveDetail samd = asamd.getStockAlterMoveDetailBySamIDAndPid(billNo, productId);
				
				brij = new BillReceiveIdcodeJson();
				brij.setProductId(samd.getProductid());
				brij.setProductName(samd.getProductname());
				brij.setSpecMode(samd.getSpecmode());
				brij.setMcode(samd.getNccode());
				brij.setTakeQuantity(quantityFormat.format(samd.getTakequantity()));
				brij.setBillNo(samd.getSamid());
				AppSBonusSetting asbp = new AppSBonusSetting();
				SBonusSetting sbs = asbp.getSBonusSetting(samd.getProductname(), samd.getSpecmode(), organmodel==DealerType.BKD.getValue()?DealerType.BKD.getValue():DealerType.BKR.getValue());
				List<StockAlterMoveIdcode> samis = asami.getStockAlterMoveIdcodeByPidBatch(productId, billNo);
				double countbonus = 0d; 
				AppIdcode apsb = new AppIdcode();
				AppProduct app = new AppProduct();
				for(StockAlterMoveIdcode sami : samis) {
					brij.getIdcodes().add(new BillReceiveIdcodeDetailJson(sami.getIdcode()));
					if(sbs != null ) {
					double q = apsb.getIdcodeByCode(sami.getIdcode()).getPackquantity();
					double b = app.getProductByID(sami.getProductid()).getBoxquantity();
					double now = ((q* b) * sbs.getBonusPoint() / sbs.getAmount());
					countbonus = countbonus + now;
					}
				}	
				
				
				String total = String.valueOf(countbonus);
				if (total.indexOf('.')!=-1) {
					brij.setTotal(total.substring(0,total.indexOf('.')));
				} else {
					brij.setTotal(total);
				}
				
			}
			// 如果要下载的信息不为空，则进行下载操作
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, brij
					,loginUsers.getUserid(),"APP","收货确认",true);
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}


	public class BillReceiveIdcodeJson {
		//TT单据号
		private String billNo;
		private String productId;
		private String productName;
		private String specMode;
		private String takeQuantity;
		private String mcode;
		private String total;
		private List<BillReceiveIdcodeDetailJson> idcodes = new ArrayList<BillReceiveIdcodeDetailJson>();
		
		public String getProductId() {
			return productId;
		}
		public void setProductId(String productId) {
			this.productId = productId;
		}
		public String getProductName() {
			return productName;
		}
		public void setProductName(String productName) {
			this.productName = productName;
		}
		public String getSpecMode() {
			return specMode;
		}
		public void setSpecMode(String specMode) {
			this.specMode = specMode;
		}
		public String getTakeQuantity() {
			return takeQuantity;
		}
		public void setTakeQuantity(String takeQuantity) {
			this.takeQuantity = takeQuantity;
		}
		public String getMcode() {
			return mcode;
		}
		public void setMcode(String mcode) {
			this.mcode = mcode;
		}
		public List<BillReceiveIdcodeDetailJson> getIdcodes() {
			return idcodes;
		}
		public void setIdcodes(List<BillReceiveIdcodeDetailJson> idcodes) {
			this.idcodes = idcodes;
		}
		public String getBillNo() {
			return billNo;
		}
		public void setBillNo(String billNo) {
			this.billNo = billNo;
		}
		public String getTotal() {
			return total;
		}
		public void setTotal(String total) {
			this.total = total;
		}
	}
	
	public class BillReceiveIdcodeDetailJson {
		
		public BillReceiveIdcodeDetailJson() {
		}
		
		public BillReceiveIdcodeDetailJson(String idcode) {
			super();
			this.idcode = idcode;
		}

		private String idcode;

		public String getIdcode() {
			return idcode;
		}

		public void setIdcode(String idcode) {
			this.idcode = idcode;
		}
	
	}
}
