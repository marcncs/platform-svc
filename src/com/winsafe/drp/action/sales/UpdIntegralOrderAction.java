package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppIntegralOrder;
import com.winsafe.drp.dao.AppIntegralOrderDetail;
import com.winsafe.drp.dao.AppObjIntegral;
import com.winsafe.drp.dao.IntegralOrder;
import com.winsafe.drp.dao.IntegralOrderDetail;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class UpdIntegralOrderAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		AppIntegralOrder aso = new AppIntegralOrder();

		try {

			String cid = request.getParameter("cid");
			String id = request.getParameter("id");
			if (cid == null || cid.equals("null") || cid.equals("")) {
				request.setAttribute("result", "databases.upd.fail");
				return new ActionForward("/sys/lockrecord.jsp");
			}

			IntegralOrder so = aso.getIntegralOrderByID(id);
			IntegralOrder oldh = (IntegralOrder) BeanUtils.cloneBean(so);
			so.setCid(cid);
			so.setCname(request.getParameter("cname"));
			so.setCmobile(request.getParameter("cmobile"));
			so.setDecideman(request.getParameter("decideman"));
			so.setDecidemantel(request.getParameter("decidemantel"));			
			so.setReceiveman(request.getParameter("receiveman"));
			so.setReceivemobile(request.getParameter("receivemobile"));
			so.setReceivetel(request.getParameter("receivetel"));
			so.setConsignmentdate(DateUtil.StringToDate(request
					.getParameter("consignmentdate")));
			so.setTransportmode(RequestTool.getInt(request,"transportmode"));
			//so.setTransit(Integer.valueOf(request.getParameter("transit")));
			so.setTransportaddr(request.getParameter("transportaddr"));
			so.setEquiporganid(request.getParameter("equiporganid"));
			so.setRemark(request.getParameter("remark"));

			double totalsum =0.00;
			
			String productid[] = request.getParameterValues("productid");
			String productname[] = request.getParameterValues("productname");
			String specmode[] = request.getParameterValues("specmode");
			String warehouseid[] = request.getParameterValues("warehouseid");
			int unitid[] = RequestTool.getInts(request,"unitid");
			double unitprice[] = RequestTool.getDoubles(request,"unitprice");
			double quantity[] = RequestTool.getDoubles(request,"quantity");

			Double subsum;
			
			StringBuffer keyscontent = new StringBuffer();
		      keyscontent.append(so.getId()).append(",").append(so.getCname()).append(",")
		      .append(so.getCmobile()).append(",").append(so.getDecideman()).append(",")
		      .append(so.getDecidemantel()).append(",").append(so.getReceiveman()).append(",")
		      .append(so.getReceivemobile()).append(",");
			
			
			AppIntegralOrderDetail asld = new AppIntegralOrderDetail();
			asld.delIntegralOrderByioid(id);
			
			AppObjIntegral aoi = new AppObjIntegral();
			aoi.delIntegralIByBillNo(id);
			aoi.delIntegralOByBillNo(id);
			aoi.delIntegralDetailByBillNo(id);
			
			for (int i = 0; i < productid.length; i++) {
				
				subsum = quantity[i] *unitprice[i];
				totalsum += subsum;

				IntegralOrderDetail sod = new IntegralOrderDetail();
				sod.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"integral_order_detail", 0, "")));
				sod.setIoid(id);
				sod.setProductid(productid[i]);
				sod.setProductname(productname[i]);
				sod.setSpecmode(specmode[i]);
				sod.setWarehouseid(warehouseid[i]);
				sod.setUnitid(unitid[i]);
				sod.setIntegralprice(unitprice[i]);
				sod.setQuantity(quantity[i]);
				sod.setTakequantity(0d);
				sod.setSubsum(subsum);
				asld.addIntegralOrderDetail(sod);
			}
			so.setIntegralsum(totalsum);
			so.setKeyscontent(keyscontent.toString());
			aso.updIntegralOrder(so);
			//增加积分
			IntegralService ids = new IntegralService();
			ids.IntegralOrderDealIntegral(so);

			request.setAttribute("result", "databases.upd.success");

			DBUserLog.addUserLog(userid, 6,"积分换购>>修改积分换购单,编号："+id,oldh,so);
			return mapping.findForward("updresult");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

		return new ActionForward(mapping.getInput());
	}
}
