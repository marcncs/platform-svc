package com.winsafe.drp.action.warehouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppDept;
import com.winsafe.drp.dao.AppStuffShipmentBill;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.StuffShipmentBillForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class ListStuffShipmentBillAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;

		String isauditselect =  Internation.getSelectTagByKeyAll("YesOrNo", request,
	          "IsAudit", true, null);
	      
	      String isreferselect = Internation.getSelectTagByKeyAll("IsRefer", request,
	              "IsRefer", true, null);

		UsersBean users = UserManager.getUser(request);
//		Long userid = users.getUserid();
		
		super.initdata(request);try{
//			String Condition=" ssb.warehouseid=wv.wid and wv.userid="+userid ;
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = {"StuffShipmentBill","WarehouseVisit"};
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" RequireDate");
//			whereSql = whereSql + timeCondition +Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			// Object obj[] = (DbUtil.setPager(request,"SaleLog as sl
			// ",whereSql,pagesize));
			Object obj[] = DbUtil.setDynamicPager(request,
					"StuffShipmentBill as ssb,WarehouseVisit as wv ", whereSql, pagesize, "subCondition");
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];

			AppUsers au = new AppUsers();
			AppWarehouse aw = new AppWarehouse();
			AppDept ad = new AppDept();
			AppStuffShipmentBill asb = new AppStuffShipmentBill();
			List pils = asb.getStuffShipmentBill(pagesize, whereSql, tmpPgInfo);
			ArrayList alsb = new ArrayList();
			for (int i = 0; i < pils.size(); i++) {
				StuffShipmentBillForm ssf = new StuffShipmentBillForm();
				Object[] o = (Object[]) pils.get(i);
				ssf.setId(o[0].toString());
//				ssf.setWarehouseidname(aw.getWarehouseByID(Long.valueOf(o[1].toString())).getWarehousename());
				ssf.setShipmentsortname(Internation.getStringByKeyPositionDB(
						"StuffShipmentSort", Integer.parseInt(o[2].toString())));
//				ssf.setShipmentdeptname(ad.getDeptByID(Long.valueOf(o[3].toString())).getDeptname());
				ssf.setRequiredate(o[4].toString().substring(0,10));

				ssf.setIsauditname(Internation.getStringByKeyPosition(
						"YesOrNo", request, Integer.parseInt(o[8].toString()),
						"global.sys.SystemResource"));
				ssf.setIsrefername(Internation.getStringByKeyPosition(
						"IsRefer", request, Integer.parseInt(o[7].toString()),
						"global.sys.SystemResource"));
				
				alsb.add(ssf);
			}
			

//			List wls = aw.getEnableWarehouse();
//			ArrayList alw = new ArrayList();
//			for (int i = 0; i < wls.size(); i++) {
//				Warehouse w = new Warehouse();
//				Object[] o = (Object[]) wls.get(i);
//				w.setId(Long.valueOf(o[0].toString()));
//				w.setWarehousename(o[1].toString());
//				alw.add(w);
//			}
			
			//request.setAttribute("alw", alw);
			request.setAttribute("alsb", alsb);
			request.setAttribute("isauditselect", isauditselect);
			request.setAttribute("isreferselect", isreferselect);

//			DBUserLog.addUserLog(userid,"列表材料出库单"); 
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
