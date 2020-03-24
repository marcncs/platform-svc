package com.winsafe.drp.action.aftersale;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppWithdraw;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;

public class ListViewSaleBillAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;

		super.initdata(request);
		try {
			String cid = request.getParameter("cid");
			if ( cid==null ){
				cid = (String)request.getSession().getAttribute("cid");
			}
			request.getSession().setAttribute("cid", cid);
//			String visitorgan = "";
//			if(users.getVisitorgan()!=null&&users.getVisitorgan().length()>0){
//				visitorgan = " or makeorganid in("+users.getVisitorgan()+")";
//			}
//			
//	    	String Condition=" (makeid="+userid+" "+visitorgan+") and cid='"+cid+"' ";
//	    	
	    	String Condition = " (makeid="+userid+" "+getOrVisitOrgan("makeorganid")+") and cid='"+cid+"' ";

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "ViewSaleBill" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String blur = DbUtil.getOrBlur(map, tmpMap, "id");
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			whereSql = whereSql + timeCondition + blur+Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

//			Object obj[] = DbUtil.setDynamicPager(request, "ViewSaleBill",
//					whereSql, pagesize, "ViewSaleBillCondition");
//			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
//			whereSql = (String) obj[1];

//			AppUsers au = new AppUsers();
//			AppOrgan ao = new AppOrgan();
			AppWithdraw asl = new AppWithdraw();
//			List pils = asl.getViewSaleBill(request,pagesize, whereSql);
//			ArrayList also = new ArrayList();
//			ViewSaleBill w = null;
//			for (int i = 0; i < pils.size(); i++) {
//				w = (ViewSaleBill) pils.get(i);
//				ViewSaleBillForm dpf = new ViewSaleBillForm();
//				dpf.setId(w.getId());
//				dpf.setCid(w.getCid());
//				dpf.setCname(w.getCname());				
//				dpf.setTotalsum(w.getTotalsum());				
//				dpf.setMakeorganidname(ao.getOrganByID(String.valueOf(w.getMakeorganid())).getOrganname());
//				dpf.setMakeidname(au.getUsersByid(w.getMakeid()).getRealname());
//				dpf.setMakedate(DateUtil.formatDate(w.getMakedate()));
//				also.add(dpf);
//			}

//			request.setAttribute("also", pils);

//			List als = au.getIDAndLoginNameByOID2(users.getMakeorganid());
//			request.setAttribute("als", als);
//			
//			List ols = ao.getOrganToDown(users.getMakeorganid());
//
//			request.setAttribute("ols",ols);

			//DBUserLog.addUserLog(userid, "列表销售单");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
