package com.winsafe.drp.action.sales;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppDept;
import com.winsafe.drp.dao.AppIntegralOrder;
import com.winsafe.drp.dao.AppIntegralOrderDetail;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.IntegralOrder;
import com.winsafe.drp.dao.IntegralOrderDetail;
import com.winsafe.drp.dao.IntegralOrderForm;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;

public class ToAuditIntegralOrderAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		
		initdata(request);

		try {
			AppIntegralOrder aso = new AppIntegralOrder();
			AppUsers au = new AppUsers();
			AppDept ad = new AppDept();
			IntegralOrder so = aso.getIntegralOrderByID(id);
			IntegralOrderForm sof = new IntegralOrderForm();
			if ( so.getIsblankout() == 1 ){
		        request.setAttribute("result","databases.record.blankoutnooperator");
		        return new ActionForward("/sys/lockrecord.jsp");
			}
			if ( so.getIsaudit() == 1 ){
		        request.setAttribute("result","databases.record.lock");
		        return new ActionForward("/sys/lockrecord.jsp");
			}

			sof.setId(id);
			sof.setCid(so.getCid());
			sof.setCname(so.getCname());
			sof.setCmobile(so.getCmobile());
			sof.setDecideman(so.getDecideman());
			sof.setDecidemantel(so.getDecidemantel());
			sof.setReceiveman(so.getReceiveman());
			sof.setReceivemobile(so.getReceivemobile());
			sof.setReceivetel(so.getReceivetel());
//			sof.setSaledeptname(ad.getDeptByID(Long.valueOf(so.getSaledept())).getDeptname());
			sof.setSaleidname(au.getUsersByid(so.getSaleid()).getRealname());
			sof.setConsignmentdate(DateUtil.formatDateTime(so
							.getConsignmentdate()));
			sof.setSourcename(Internation.getStringByKeyPositionDB("Source", so.getSource()));
			sof.setTransportmodename(Internation.getStringByKeyPositionDB(
					"TransportMode", so.getTransportmode()));
			// sof.setTransitname(Internation.getStringByKeyPositionDB("Transit",
			// so.getTransit()));
			sof.setTransportaddr(so.getTransportaddr());			
			AppOrgan appor = new AppOrgan();
			sof.setMakeorganidname(appor.getOrganByID(so.getMakeorganid())
					.getOrganname());
			sof.setMakedeptidname(ad.getDeptByID(so.getMakedeptid())
					.getDeptname());
			sof.setEquiporganid(so.getEquiporganid());
//			sof.setEquiporganidname(appor.getOrganByID(so.getEquiporganid())
//					.getOrganname());
			sof.setRemark(so.getRemark());
			sof.setIntegralsum(so.getIntegralsum());

			sof.setMakeidname(au.getUsersByid(so.getMakeid()).getRealname());
			sof.setMakedate(DateUtil.formatDateTime(so.getMakedate()));
			sof.setIsaudit(so.getIsaudit());
			sof.setIsauditname(Internation.getStringByKeyPosition("YesOrNo",
					request, so.getIsaudit(), "global.sys.SystemResource"));
			if (so.getAuditid() != null && so.getAuditid() > 0) {
				sof.setAuditidname(au.getUsersByid(so.getAuditid())
						.getRealname());
			} else {
				sof.setAuditidname("");
			}
			sof.setAuditdate(DateUtil.formatDateTime(so.getAuditdate()));
			sof.setIsendcase(so.getIsendcase());
			sof.setIsendcasename(Internation.getStringByKeyPosition("YesOrNo",
					request, so.getIsendcase(), "global.sys.SystemResource"));
			if (so.getEndcaseid() != null && so.getEndcaseid() > 0) {
				sof.setEndcaseidname(au.getUsersByid(so.getEndcaseid())
						.getRealname());
			} else {
				sof.setEndcaseidname("");
			}
			sof.setEndcasedate(DateUtil.formatDateTime(so.getEndcasedate()));
			sof.setIsblankout(so.getIsblankout());
			sof.setIsblankoutname(Internation.getStringByKeyPosition("YesOrNo",
					request, so.getIsblankout(), "global.sys.SystemResource"));
			if (so.getBlankoutid() != null && so.getBlankoutid() > 0) {
				sof.setBlankoutidname(au.getUsersByid(so.getBlankoutid())
						.getRealname());
			} else {
				sof.setBlankoutidname("");
			}
			sof.setBilltype(so.getBilltype());
			sof.setBilltypename(Internation.getStringByKeyPosition("IOBillType",
					request, so.getBilltype(), "global.sys.SystemResource"));
			sof.setBlankoutdate(DateUtil.formatDateTime(so.getBlankoutdate()));
			sof.setBlankoutreason(so.getBlankoutreason());

			AppIntegralOrderDetail asld = new AppIntegralOrderDetail();
//			List sals = asld.getIntegralOrderDetailObjectByioid(id);
			ArrayList als = new ArrayList();
			AppWarehouse aw = new AppWarehouse();

			IntegralOrderDetail sod = null;
//			for (int i = 0; i < sals.size(); i++) {
////				sod = (IntegralOrderDetail) sals.get(i);
//				IntegralOrderDetailForm sodf = new IntegralOrderDetailForm();
//				sodf.setId(sod.getId());
//				sodf.setProductid(sod.getProductid());
//				sodf.setProductname(sod.getProductname());
//				sodf.setSpecmode(sod.getSpecmode());
//				sodf.setWarehouseid(sod.getWarehouseid());
////				if ( null != sod.getWarehouseid() && sod.getWarehouseid() > 0){
////		        	sodf.setWarehouseidname(aw.getWarehouseByID(sod.getWarehouseid()).getWarehousename());   
////		        }
//				sodf.setUnitidname(Internation.getStringByKeyPositionDB(
//						"CountUnit", Integer.parseInt(sod.getUnitid()
//								.toString())));
//				sodf.setIntegralprice(sod.getIntegralprice());
//				sodf.setQuantity(sod.getQuantity());
//				sodf.setTakequantity(sod.getTakequantity());
//				sodf.setSubsum(sod.getSubsum());
//				als.add(sodf);
//			}

			AppOrgan ao = new AppOrgan();
			List aolist = ao.getOrganToDown(users.getMakeorganid());
			//配送机构仓库
			List wlist = aw.getWarehouseListByOID(so.getEquiporganid());
					
			request.setAttribute("als", als);
			request.setAttribute("sof", sof);
			request.setAttribute("aolist", aolist);
			request.setAttribute("wlist", wlist);

			return mapping.findForward("toaudit");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
