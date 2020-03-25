package com.winsafe.drp.action.purchase;

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

import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppOrganPriceHistory;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.OrganPriceHistory;
import com.winsafe.drp.dao.OrganPriceHistoryForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class ListOrganPriceHistoryAction extends Action{

	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		UsersBean users = UserManager.getUser(request);
	      Integer userid = users.getUserid();
	      int pagesize = 10;
		try{
			String productid = request.getParameter("PID");
			if ( productid == null ){
				productid = (String)request.getSession().getAttribute("pid");
			}
			request.getSession().setAttribute("pid", productid);
			String oid = users.getMakeorganid();
			
			 String Condition =" oh.organid='"+oid+"' and oh.productid='"+productid+"'";
			
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "OrganPriceHistory" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename); 

			// String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
			// " InvoiceDate");
			whereSql = whereSql + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			Object obj[] = (DbUtil.setDynamicPager(request,
					"OrganPriceHistory as oh", whereSql, pagesize,
					"OrganPriceHistory"));
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];

	      AppOrganPriceHistory abr = new AppOrganPriceHistory();
	      AppProduct ap = new AppProduct();
	      AppOrgan ao = new AppOrgan();
	      AppUsers au = new AppUsers();
	      
	      List apls = abr.getOrganPriceHistory(pagesize, whereSql,
					tmpPgInfo);
	      ArrayList alpl = new ArrayList();
	      for(int i=0;i<apls.size();i++){
	    	  OrganPriceHistoryForm opf = new OrganPriceHistoryForm();
	    	  OrganPriceHistory o = (OrganPriceHistory)apls.get(i);
	    	  opf.setId(o.getId());
	    	  opf.setOrganid(o.getOrganid());
	    	  opf.setOrganidname(ao.getOrganByID(o.getOrganid()).getOrganname());
	    	  opf.setProductid(o.getProductid());
	    	  opf.setProductidname(ap.getProductByID(o.getProductid()).getProductname());
	    	  opf.setUnitid(o.getUnitid());
	    	  opf.setUnitidname(Internation.getStringByKeyPositionDB("CountUnit", 
	                o.getUnitid()));
	    	  opf.setPolicyid(o.getPolicyid());
	    	  opf.setPolicyidname(Internation.getStringByKeyPositionDB("PricePolicy", 
	                o.getPolicyid().intValue()));
	    	  opf.setUnitprice(o.getUnitprice());
	    	  
	    	//  opf.setUseridname(au.getUsersByid(o.getUserid()).getRealname());
	    	  opf.setModifydate(DateUtil.formatDateTime(o.getModifydate()));
	        
	        alpl.add(opf);
	      }
	      
	      //DBUserLog.addUserLog(userid,"列表机构价格历史记录");
	      request.setAttribute("alpl",alpl);

	      return mapping.findForward("list");
	    }catch(Exception e){
	      e.printStackTrace();
	    }
		return new ActionForward(mapping.getInput());
	}
}
