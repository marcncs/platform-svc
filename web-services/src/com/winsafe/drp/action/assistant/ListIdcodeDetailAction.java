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

import com.winsafe.drp.dao.AppIdcodeDetail;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.IdcodeDetail;
import com.winsafe.drp.dao.IdcodeDetailForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class ListIdcodeDetailAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String idcode = request.getParameter("idcode");
		UsersBean users = UserManager.getUser(request);
//		Long userid = users.getUserid();
		try {
			int pagesize = 10;
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = " or makeorganid in(" + users.getVisitorgan()
						+ ")";
			}
//			String Condition = " (makeid=" + userid + " " + visitorgan + ") ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "IdcodeDetail" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			String blur = DbUtil.getOrBlur(map, tmpMap, "ProductID","ProductName","Specmode","CName","CMobile","IDCode");

//			whereSql = whereSql + timeCondition +blur+Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			Object obj[] = DbUtil.setDynamicPager(request, "IdcodeDetail", whereSql,
					pagesize, "IdcodeDetail");
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];

			AppIdcodeDetail ap = new AppIdcodeDetail();
			AppOrgan ao = new AppOrgan();
			AppWarehouse aw = new AppWarehouse();
			List<IdcodeDetail> idlist = ap.getIdcodeDetail(pagesize, whereSql, tmpPgInfo);
			IdcodeDetailForm idf = null;
			List list = new ArrayList();
			for ( IdcodeDetail id : idlist ){
				idf = new IdcodeDetailForm();
				idf.setId(id.getId());
				idf.setProductid(id.getProductid());
				idf.setProductname(id.getProductname());
				idf.setSpecmode(id.getSpecmode());
				idf.setMakeuser(id.getMakeuser());
				idf.setMakedate(DateUtil.formatDateTime(id.getMakedate()));
				idf.setIdbilltype(id.getIdbilltype());
				idf.setIdbilltypename(Internation.getStringByKeyPosition(
						"IdBillType", request, id.getIdbilltype(),
						"global.sys.SystemResource"));
				idf.setBillid(id.getBillid());	
				idf.setIdcode(id.getIdcode());
				if ( id.getMakeorganid()!=null && !"".equals(id.getMakeorganid()) ){
					idf.setMakeorganidname(ao.getOrganByID(id.getMakeorganid()).getOrganname());
				}
				if ( id.getEquiporganid()!=null && !"".equals(id.getEquiporganid()) ){
					idf.setEquiporganidname(ao.getOrganByID(id.getEquiporganid()).getOrganname());
				}
				if ( id.getWarehouseid() !=null && id.getWarehouseid()>0 ){
//					idf.setWarehouseidname(aw.getWarehouseByID(id.getWarehouseid()).getWarehousename());
				}
				idf.setCname(id.getCname());
				idf.setCmobile(id.getCmobile());
				list.add(idf);
			}
			List ols = ao.getOrganToDown(users.getMakeorganid());
			List wls = aw.getWarehouseListByOID(users.getMakeorganid());
			request.setAttribute("ols", ols);
			request.setAttribute("wls", wls);
			
			request.setAttribute("list", list);
			request.setAttribute("idcode", idcode);
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
