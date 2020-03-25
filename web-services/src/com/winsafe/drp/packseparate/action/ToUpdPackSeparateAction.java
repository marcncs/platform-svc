package com.winsafe.drp.packseparate.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.packseparate.dao.AppPackSeparate;
import com.winsafe.drp.packseparate.dao.AppPackSeparateDetail;
import com.winsafe.drp.packseparate.pojo.PackSeparate;
import com.winsafe.drp.packseparate.pojo.PackSeparateDetail;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.Constants;
import com.winsafe.hbm.util.DateUtil;

public class ToUpdPackSeparateAction extends BaseAction {
	private static Logger logger = Logger.getLogger(ToUpdPackSeparateAction.class);
	private AppPackSeparate aps = new AppPackSeparate();
	private AppPackSeparateDetail apsd = new AppPackSeparateDetail();
	private AppWarehouse appw = new AppWarehouse();
	private AppOrgan appo = new AppOrgan();
	private AppProduct appProduct = new AppProduct();
	private AppProductStockpile appPs = new AppProductStockpile();
	private AppFUnit appFunit = new AppFUnit();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception { 
		String psid = request.getParameter("ID");
		super.initdata(request);
		try {
			PackSeparate ps = aps.getPackSeparateById(psid);
			
			if (ps.getIsAudit() == 1) { 
				String result = "databases.record.noupdate";
				request.setAttribute("result", result);
				return mapping.findForward("lock");
			}
			
			PackSeparateDetail psd = apsd.getPackSeparateDetailByPsid(psid);
			Warehouse warehouse = appw.getWarehouseByID(ps.getWarehouseId());
			Organ organ = appo.getOrganByID(ps.getOrganId());
			Product outProduct = appProduct.getProductByID(psd.getOutProductId());
			Product inProduct = appProduct.getProductByID(psd.getInProductId());
			
			FUnit fUnit = appFunit.getFUnit(inProduct.getId(), Constants.DEFAULT_UNIT_ID);
			double stockpile = appPs.getProductStockpileByPIDWID(outProduct.getId(), warehouse.getId());
			
			request.setAttribute("psid", psid);
			
			request.setAttribute("billDate", DateUtil.formatDate(ps.getBillDate()));
			
			request.setAttribute("organid", organ.getId());
			request.setAttribute("oname", organ.getOrganname());
			
			request.setAttribute("warehouseId", warehouse.getId());
			request.setAttribute("wname", warehouse.getWarehousename());
			
			request.setAttribute("outProductId", psd.getOutProductId());
			request.setAttribute("outProductName", outProduct.getPackSizeName());
			request.setAttribute("outMCode", psd.getOutMcode());
			request.setAttribute("outBatch",psd.getOutBatch());
			request.setAttribute("stockpile",stockpile);
			
			request.setAttribute("outQuantity", ArithDouble.mul(psd.getOutQuantity(), outProduct.getBoxquantity()));
			
			request.setAttribute("inProductId", psd.getInProductId());
			request.setAttribute("inProductName", outProduct.getPackSizeName());
			request.setAttribute("inMCode", psd.getInMcode());
			request.setAttribute("inBatch",psd.getInBatch());
			request.setAttribute("defultUnitQty",ArithDouble.mul(fUnit.getXquantity(), inProduct.getBoxquantity()));
//			request.setAttribute("inQuantity", psd.getInQuantity());
			request.setAttribute("inQuantity", ArithDouble.div(psd.getInQuantity(), fUnit.getXquantity()));
			
			request.setAttribute("wasteQuantity", ArithDouble.mul(psd.getWastage(), outProduct.getBoxquantity()));
			
			request.setAttribute("boxQuantity", inProduct.getBoxquantity());
			
			request.setAttribute("inCountUnitId", inProduct.getCountunit());
			request.setAttribute("inUnitId", inProduct.getSunit());
			request.setAttribute("outUnitId", outProduct.getCountunit());
			
			return mapping.findForward("toupd");
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
	}
}
