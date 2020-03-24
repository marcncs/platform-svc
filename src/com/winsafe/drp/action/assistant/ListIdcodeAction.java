package com.winsafe.drp.action.assistant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.IdcodeDetailForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class ListIdcodeAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
//		Long userid = users.getUserid();
		try {
			int pagesize = 10;
			String Condition = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				Condition = "  makeorganid in(" + users.getVisitorgan()
						+ ")";
			}
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Idcode" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			String blur = DbUtil.getOrBlur(map, tmpMap, "ProductID","ProductName","BillID","ProvideName","IDCode");

			whereSql = whereSql + timeCondition +blur+Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			Object obj[] = DbUtil.setDynamicPager(request, "Idcode", whereSql,
					pagesize, "Idcode");
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];

			AppIdcode ap = new AppIdcode();
			AppOrgan ao = new AppOrgan();
			AppWarehouse aw = new AppWarehouse();
//			List<Idcode> idlist = ap.searchIdcode(pagesize, whereSql, tmpPgInfo);
			IdcodeDetailForm idf = null;
			List list = new ArrayList();
//			for ( Idcode id : idlist ){
//				idf = new IdcodeDetailForm();
//				idf.setId(id.getId());
//				idf.setProductid(id.getProductid());
//				idf.setProductname(id.getProductname());
//				idf.setMakedate(DateUtil.formatDateTime(id.getMakedate()));
//				idf.setIdbilltype(id.getIdbilltype());
//				idf.setIdbilltypename(Internation.getStringByKeyPosition(
//						"IdBillType", request, id.getIdbilltype(),
//						"global.sys.SystemResource"));
//				idf.setBillid(id.getBillid());	
//				idf.setIdcode(id.getIdcode());
//				if ( id.getMakeorganid()!=null && !"".equals(id.getMakeorganid()) ){
//					idf.setMakeorganidname(ao.getOrganByID(id.getMakeorganid()).getOrganname());
//				}				
//				if ( id.getWarehouseid() !=null && id.getWarehouseid()>0 ){
//					idf.setWarehouseidname(aw.getWarehouseByID(id.getWarehouseid()).getWarehousename());
//				}
//				idf.setProvidename(id.getProvidename()==null?"":id.getProvidename());
//				idf.setIsusename(Internation.getStringByKeyPosition(
//						"YesOrNo", request, id.getIsuse(),
//						"global.sys.SystemResource"));
//				list.add(idf);
//			}
			List ols = ao.getOrganToDown(users.getMakeorganid());
			List wls = aw.getWarehouseListByOID(users.getMakeorganid());
			request.setAttribute("ols", ols);
			request.setAttribute("wls", wls);			
			request.setAttribute("list", list);
			
			request.setAttribute("MakeOrganID", request.getParameter("MakeOrganID"));
			request.setAttribute("IDCode", request.getParameter("IDCode"));
			request.setAttribute("BillID", request.getParameter("BillID"));
			request.setAttribute("ProvideID", request.getParameter("ProvideID"));
			request.setAttribute("BeginDate", request.getParameter("BeginDate"));
			request.setAttribute("EndDate", request.getParameter("EndDate"));
			request.setAttribute("WarehouseID", request.getParameter("WarehouseID"));
			request.setAttribute("KeyWord", request.getParameter("KeyWord"));
			
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
