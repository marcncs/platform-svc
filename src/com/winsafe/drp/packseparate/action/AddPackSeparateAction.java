package com.winsafe.drp.packseparate.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.packseparate.dao.AppPackSeparate;
import com.winsafe.drp.packseparate.dao.AppPackSeparateDetail;
import com.winsafe.drp.packseparate.pojo.PackSeparate;
import com.winsafe.drp.packseparate.pojo.PackSeparateDetail;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.StringUtil;

public class AddPackSeparateAction extends BaseAction {
	private static Logger logger = Logger.getLogger(AddPackSeparateAction.class);
	private AppPackSeparate aps = new AppPackSeparate();
	private AppPackSeparateDetail apsd = new AppPackSeparateDetail();
	private AppWarehouse appw = new AppWarehouse();
	private AppProduct ap = new AppProduct();
	private AppFUnit appFUnit = new AppFUnit();
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String psid = MakeCode.getExcIDByRandomTableName("pack_separate", 2, "PS");
			PackSeparate ps = new PackSeparate();
			ps.setWarehouseId(request.getParameter("warehouseid"));
			ps.setOrganId(request.getParameter("organid"));
			ps.setIsAudit(0);
			ps.setId(psid);
			ps.setIsAccount(1);
			ps.setMakeDate(Dateutil.getCurrentDate());
			ps.setMakeId(userid);
			ps.setMakeOrganId(users.getMakeorganid());
			ps.setRemark(request.getParameter("remark"));
			ps.setBillDate(DateUtil.StringToDate(request.getParameter("billDate")));
			String keyContent = request.getParameter("orgname") + ","+request.getParameter("owname") +"," +
				users.getMakeorganname() + "," +users.getLoginname();
			ps.setKeyContent(keyContent);
			aps.addPackSeparate(ps);
			
			String[] strOutProductId = request.getParameterValues("outProductId");
			String[] strInProductId = request.getParameterValues("inProductId");
//			String[] strOutMCode = request.getParameterValues("outMCode");
//			String[] strIntMCode = request.getParameterValues("inMCode");
			String[] strOutBatch = request.getParameterValues("outBatch");
			String[] strInBatch = request.getParameterValues("inBatch");
			String[] strWastage = request.getParameterValues("wasteQuantity");
			String[] strInQuantity = request.getParameterValues("inQuantity");
			String[] strOutQuantity = request.getParameterValues("outQuantity");
			
			if(strOutProductId != null) {
				for (int i = 0; i < strOutProductId.length; i++) {
					PackSeparateDetail psd = new PackSeparateDetail();
//					Product inProduct = ap.loadProductById(strInProductId[i]);
//					Product outProduct = ap.loadProductById(strOutProductId[i]);
					Product inProduct = ap.getProductByID(strInProductId[i]);
					Product outProduct = ap.getProductByID(strOutProductId[i]);
					if(!StringUtil.isEmpty(strInBatch[i])) {
						psd.setInBatch(strInBatch[i]);
					} else {
						psd.setInBatch(Constants.NO_BATCH);
					}
					if(!StringUtil.isEmpty(strOutBatch[i])) {
						psd.setOutBatch(strOutBatch[i]);
					} else {
						psd.setOutBatch(Constants.NO_BATCH);
					}
					psd.setInMcode(inProduct.getmCode());
					psd.setInProductId(strInProductId[i]);
					Double quantity = appFUnit.getQuantity(psd.getInProductId(), Constants.DEFAULT_UNIT_ID, Double.parseDouble(strInQuantity[i]));
					psd.setInQuantity(quantity);
					psd.setOutMcode(outProduct.getmCode());
					psd.setOutProductId(strOutProductId[i]);
					psd.setOutQuantity(ArithDouble.div2(Double.parseDouble(strOutQuantity[i]), outProduct.getBoxquantity(),3));
					psd.setWastage(ArithDouble.div2(Double.parseDouble(strWastage[i]), outProduct.getBoxquantity(),3));
					psd.setPsid(ps.getId());
					psd.setInUnitId(inProduct.getSunit());
					psd.setOutUnitId(outProduct.getSunit());
					psd.setInSpecMode(inProduct.getSpecmode());
					psd.setOutSpecMode(outProduct.getSpecmode());
					apsd.addPackSeparateDetail(psd);
				}
			}
			
			//页面初始化时，默认机构和仓库
			request.setAttribute("MakeOrganID", users.getMakeorganid());
			request.setAttribute("oname", users.getMakeorganname());
			appw.getEnableWarehouseByvisit(request,users.getUserid(),users.getMakeorganid());
			
			request.setAttribute("result", "添加成功");
			return mapping.findForward("result");
		} catch (Exception e) {
			logger.error("添加分包单据发生异常",e);
			throw e;
		}
	}
}
