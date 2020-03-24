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
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.packseparate.dao.AppPackSeparate;
import com.winsafe.drp.packseparate.dao.AppPackSeparateDetail;
import com.winsafe.drp.packseparate.pojo.PackSeparate;
import com.winsafe.drp.packseparate.pojo.PackSeparateDetail;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.StringUtil;

public class UpdPackSeparateAction extends BaseAction {
	private static Logger logger = Logger.getLogger(UpdPackSeparateAction.class);
	private AppPackSeparate aps = new AppPackSeparate();
	private AppPackSeparateDetail apsd = new AppPackSeparateDetail();
	private AppProduct ap = new AppProduct();
	private AppFUnit appFUnit = new AppFUnit();
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		String psid = request.getParameter("psid");
		try {
			PackSeparate ps = aps.getPackSeparateById(psid);
			
			if (ps.getIsAudit() == 1) { 
				String result = "databases.record.noupdate";
				request.setAttribute("result", result);
				return mapping.findForward("lock");
			}
			
			ps.setWarehouseId(request.getParameter("warehouseid"));
			ps.setOrganId(request.getParameter("organid"));
			ps.setMakeDate(Dateutil.getCurrentDate());
			ps.setMakeId(userid);
			ps.setMakeOrganId(users.getMakeorganid());
			ps.setRemark(request.getParameter("remark"));
			ps.setBillDate(DateUtil.StringToDate(request.getParameter("billDate")));
			String keyContent = request.getParameter("orgname") + ","+request.getParameter("owname") +"," +
			users.getMakeorganname() + "," +users.getLoginname();
			ps.setKeyContent(keyContent);
			apsd.delPackSeparateDetailByPsid(psid);
			
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
					Product inProduct = ap.loadProductById(strInProductId[i]);
					Product outProduct = ap.loadProductById(strOutProductId[i]);
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
			aps.updPackSeparate(ps);
			request.setAttribute("result", "更新成功");
			return mapping.findForward("result");
		} catch (Exception e) {
			logger.error("更新分包单据发生异常",e);
			throw e;
		}
	}
}
