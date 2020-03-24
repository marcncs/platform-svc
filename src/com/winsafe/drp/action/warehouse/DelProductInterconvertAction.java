package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProductInterconvert;
import com.winsafe.drp.dao.AppProductInterconvertDetail;
import com.winsafe.drp.dao.ProductInterconvert;
import com.winsafe.drp.util.DBUserLog;

public class DelProductInterconvertAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {

			String smid = request.getParameter("ID");
			AppProductInterconvert asb = new AppProductInterconvert();
			ProductInterconvert sb = asb.getProductInterconvertByID(smid);
			// String warehouseid = sb.getOutwarehouseid();

			if (sb.getIsaudit() == 1) {
				String result = "databases.record.nodel";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			
			AppProductInterconvertDetail asbd = new AppProductInterconvertDetail();
			// AppProductStockpile aps = new AppProductStockpile();
			// List ls = asbd.getProductInterconvertDetailBySamID(smid);

			asb.delProductInterconvert(smid);

			// String freeproductid;
			// String freebatch;
			// Double freequantity;
			// ProductInterconvertDetail pid = null;
			// for(int i=0;i<ls.size();i++){
			// pid=(ProductInterconvertDetail)ls.get(i);
			// freeproductid = pid.getProductid();
			// freebatch = pid.getBatch();
			// freequantity = pid.getQuantity();
			// aps.freeStockpile(freeproductid, warehouseid, freebatch,
			// freequantity);
			// }
			asbd.delProductInterconvertDetailBySamid(smid);

			request.setAttribute("result", "databases.del.success");

			DBUserLog.addUserLog(userid, 7, "删除商品互转,编号：" + smid);

			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
