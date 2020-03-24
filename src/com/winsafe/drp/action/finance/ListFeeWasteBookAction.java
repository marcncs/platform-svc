package com.winsafe.drp.action.finance;

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
import com.winsafe.drp.dao.AppFeeWasteBook;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.FeeWasteBook;
import com.winsafe.drp.dao.FeeWasteBookForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class ListFeeWasteBookAction  extends BaseAction {

	  public ActionForward execute(ActionMapping mapping, ActionForm form,
	                               HttpServletRequest request,
	                               HttpServletResponse response) throws Exception {

		  int pagesize = 10;
		  super.initdata(request);
			try {
				//String Condition = " il.makeid like '"+userid+"%' ";
				Map map = new HashMap(request.getParameterMap());
				Map tmpMap = EntityManager.scatterMap(map);
				//String sql = "select * from sale_log as sl where "+Condition;
				String[] tablename = { "FeeWasteBook" };
				String whereSql = EntityManager.getTmpWhereSql(map, tablename); 

//				String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
//						" MakeDate");
				//whereSql = whereSql + timeCondition;// + Condition; 
				whereSql = DbUtil.getWhereSql(whereSql); 
				Object obj[] = (DbUtil.setDynamicPager(request, "FeeWasteBook as fwb",
						whereSql, pagesize, "IncomeCondition"));
				SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
				whereSql = (String) obj[1];

				AppFeeWasteBook ail = new AppFeeWasteBook();

				List<FeeWasteBook> slls = ail.searchFeeWasteBook(pagesize, whereSql, tmpPgInfo);
				ArrayList arls = new ArrayList();				
				AppDept ad = new AppDept();
				AppUsers au = new AppUsers();
				for (FeeWasteBook cwb: slls ) {
					FeeWasteBookForm ilf = new FeeWasteBookForm();
					ilf.setId(cwb.getId());
					ilf.setCid(cwb.getCid());
					ilf.setCname(cwb.getCname());
//					if ( cwb.getFeedept() != null && cwb.getFeedept() >0 ){
//						ilf.setFeedeptname(ad.getDeptByID(cwb.getFeedept()).getDeptname());
//					}
//					if ( cwb.getFeeid() != null && cwb.getFeeid() >0 ){
//						ilf.setFeeidname(au.getUsersByid(cwb.getFeeid()).getRealname());
//					}
					ilf.setPorject(cwb.getPorject());					
					ilf.setBillno(cwb.getBillno());
					ilf.setMemo(cwb.getMemo());				
					ilf.setCycleinsum(cwb.getCycleinsum());
					ilf.setCycleoutsum(cwb.getCycleoutsum());					
					ilf.setRecorddate(DateUtil.formatDate(cwb.getRecorddate()));
					arls.add(ilf);
				}
				String objectsortselect = Internation.getSelectTagByKeyAll(
						"ObjectSort", request, "ObjectSort", true, null);

				request.setAttribute("objectsortselect", objectsortselect);
				
				request.setAttribute("arls", arls);			
				//DBUserLog.addUserLog(userid,"列表费用台帐"); 
				return mapping.findForward("list");
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	      String result = "databases.settlement.noexist";
          request.setAttribute("result", result);
          return new ActionForward("/sys/lockrecordclose.jsp");
	    }
	    //return null;
	  }
}
