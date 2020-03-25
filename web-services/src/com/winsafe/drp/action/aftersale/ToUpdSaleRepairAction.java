package com.winsafe.drp.action.aftersale;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppSaleRepair;
import com.winsafe.drp.dao.AppSaleRepairDetail;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.SaleRepair;
import com.winsafe.drp.dao.SaleRepairDetail;
import com.winsafe.drp.dao.SaleRepairDetailForm;
import com.winsafe.drp.dao.SaleRepairForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;

public class ToUpdSaleRepairAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		UsersBean usersBean = UserManager.getUser(request);
//		Long userid = usersBean.getUserid();
		try {

			AppSaleRepair aso = new AppSaleRepair();
			SaleRepair so = aso.getSaleRepairByID(id);
			if (so.getIsaudit() == 1) {
				String result = "databases.record.lock";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecord.jsp");
			}
			if (so.getIsblankout() == 1) {
				String result = "databases.record.blankoutnooperator";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecord.jsp");
			}
			SaleRepairForm sof = new SaleRepairForm();
			sof.setId(so.getId());
			sof.setCid(so.getCid());
			sof.setCname(so.getCname());
			sof.setClinkman(so.getClinkman());
			sof.setTel(so.getTel());
			sof.setWarehouseinid(so.getWarehouseinid());
			sof.setWarehouseoutid(so.getWarehouseoutid());
			sof.setTradesdate(DateUtil.formatDate(so.getTradesdate()));
			sof.setTotalsum(so.getTotalsum());
			sof.setRemark(so.getRemark());
			

			AppWarehouse aw = new AppWarehouse();
//			List wls = aw.getEnableWarehouseByVisit(userid);
			ArrayList alw = new ArrayList();
//			for (int i = 0; i < wls.size(); i++) {
//				Warehouse w = new Warehouse();
//				Object[] o = (Object[]) wls.get(i);
//				w.setId(Long.valueOf(o[0].toString()));
//				w.setWarehousename(o[1].toString());
//				alw.add(w);
//			}

			AppSaleRepairDetail asld = new AppSaleRepairDetail();
			List slls = asld.getSaleRepairDetailBySrid(id);
			ArrayList als = new ArrayList();

			SaleRepairDetail wd = null;
			SaleRepairDetailForm sldf = null;
			for (int i = 0; i < slls.size(); i++) {
				wd = (SaleRepairDetail) slls.get(i);
				sldf = new SaleRepairDetailForm();
				sldf.setProductid(wd.getProductid());
				sldf.setProductname(wd.getProductname());
				sldf.setSpecmode(wd.getSpecmode());
				sldf.setUnitid(wd.getUnitid());
				sldf.setUnitidname(Internation.getStringByKeyPositionDB("CountUnit", wd.getUnitid()));			
				sldf.setQuantity(wd.getQuantity());		
				sldf.setBatch(wd.getBatch());				
				sldf.setUnitprice(wd.getUnitprice());
				sldf.setSubsum(wd.getSubsum());
				als.add(sldf);
			}

			
//			String warehourseselect = getWarehourseselect(wls, null);
			request.setAttribute("sof", sof);
			request.setAttribute("als", als);
			request.setAttribute("alw", alw);
//			request.setAttribute("warehourseselect", warehourseselect);

			return mapping.findForward("toupd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
	
	private String getWarehourseselect(List whlist, Long whid){
		  StringBuffer sb = new StringBuffer();
		  sb.append("<select name=\"warehouseid\">");
		  for (int i = 0; i < whlist.size(); i++) {
				Object[] o = (Object[]) whlist.get(i);
				String selected = "";			
				if ( whid !=null && o[0].toString().equals(whid.toString()) ){
					selected = "selected";
				}
				sb.append("<option value=\"").append(
						Long.valueOf(o[0].toString())).append("\" ").append(selected).append(">").append(
						o[1].toString()).append("</option>");
			}
		  sb.append("</select>");
		  return sb.toString();
	  }
}
