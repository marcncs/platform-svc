package com.winsafe.drp.action.warehouse;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppShipmentBill;
import com.winsafe.drp.dao.AppShipmentBillDetail;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.ShipmentBill;
import com.winsafe.drp.dao.ShipmentBillDetail;
import com.winsafe.drp.dao.ShipmentBillDetailForm;
import com.winsafe.drp.dao.ShipmentBillForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;

public class ToUpdSaleShipmentAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		UsersBean usersBean = UserManager.getUser(request);

//		Long userid = usersBean.getUserid();
		super.initdata(request);try{
//			if (DbUtil.judgeApproveStatusToRefer("ShipmentBill", id)) {
//				String result = "databases.record.approvestatus";
//				request.setAttribute("result", result);
//				return new ActionForward("/sys/lockrecord.jsp");//;mapping.findForward("lock");
//			}
			AppShipmentBill asb = new AppShipmentBill();

			ShipmentBill sb = new ShipmentBill();
			AppCustomer ac = new AppCustomer();
			AppUsers au = new AppUsers();
			AppWarehouse aw = new AppWarehouse();
			sb = asb.getShipmentBillByID(id);
			if (sb.getIsaudit().intValue() == 1) {
				String result = "databases.record.lock";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecord.jsp");
			}
//			if ( sb.getIsblankout() == 1 ){
//				String result = "databases.record.blankoutnooperator";
//				request.setAttribute("result", result);
//				return new ActionForward("/sys/lockrecord.jsp");
//			}
			ShipmentBillForm sbf = new ShipmentBillForm();
			sbf.setId(id);
//			sbf.setSoid(sb.getSoid());			
//			sbf.setSaledept(sb.getSaledept());	
			sbf.setCid(sb.getCid());
//			Customer c = ac.getCustomer(sb.getCid());
//			if ( c != null ){
//				sbf.setCidname(c.getCname());
//			}
			sbf.setCname(sb.getCname());
			sbf.setLinkman(sb.getLinkman());
			sbf.setTel(sb.getTel());
			sbf.setReceiveaddr(sb.getReceiveaddr());
			sbf.setTransportmodename(Internation.getSelectTagByKeyAllDBDef("TransportMode","transportmode",sb.getTransportmode()));
			sbf.setTransportnum(sb.getTransportnum());
			sbf.setRequiredate(DateUtil.formatDate(sb.getRequiredate()));
			sbf.setRemark(sb.getRemark());
			sbf.setTotalsum(sb.getTotalsum());


			AppShipmentBillDetail asbd = new AppShipmentBillDetail();
			List slls = asbd.getShipmentBillDetailBySbID(id);
			ArrayList als = new ArrayList();

			ShipmentBillDetail sbd = null;
			for (int i = 0; i < slls.size(); i++) {				
				sbd = (ShipmentBillDetail)slls.get(i);
				ShipmentBillDetailForm sbdf = new ShipmentBillDetailForm();
				sbdf.setProductid(sbd.getProductid());
				sbdf.setProductname(sbd.getProductname());
//				sbdf.setWarehouseid(sbd.getWarehouseid());
//				if ( sbd.getWarehouseid() != null && sbd.getWarehouseid()> 0 ){
//					sbdf.setWarehouseidname(aw.getWarehouseByID(
//							sbd.getWarehouseid()).getWarehousename());
//				}
				sbdf.setSpecmode(sbd.getSpecmode());
//				sbdf.setUnitid(sbd.getUnitid());
				sbdf.setUnitidname(Internation.getStringByKeyPositionDB("CountUnit",
						Integer.parseInt(sbd.getUnitid().toString())));
				sbdf.setBatch(sbd.getBatch());
				sbdf.setUnitprice(sbd.getUnitprice());
				sbdf.setQuantity(sbd.getQuantity());
				sbdf.setDiscount(sbd.getDiscount());
				sbdf.setTaxrate(sbd.getTaxrate());
				sbdf.setSubsum(sbd.getSubsum());
				als.add(sbdf);
			}
			

//
//			AppDept ad = new AppDept();
//			List dls = ad.getDept();
//			ArrayList aldept = new ArrayList();
//			for (int i = 0; i < dls.size(); i++) {
//				Dept d = new Dept();
//				Object[] ob = (Object[]) dls.get(i);
//				d.setId(Long.valueOf(ob[0].toString()));
//				d.setDeptname(ob[1].toString());
//				aldept.add(d);
//			}
//			
//			List uls = au.getIDAndLoginName();
//			ArrayList users = new ArrayList();
//			for (int u = 0; u < uls.size(); u++) {
//				UsersBean ubs = new UsersBean();
//				Object[] ub = (Object[]) uls.get(u);
//				ubs.setUserid(Long.valueOf(ub[0].toString()));
//				ubs.setRealname(ub[2].toString());
//				users.add(ubs);
//			}

			request.setAttribute("sbf", sbf);
			request.setAttribute("als", als);
//			request.setAttribute("users", users);
//			request.setAttribute("aldept", aldept);

			return mapping.findForward("toupd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
