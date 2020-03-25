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
import com.winsafe.drp.dao.AppCashBank;
import com.winsafe.drp.dao.AppCashWasteBook;
import com.winsafe.drp.dao.CashBank;
import com.winsafe.drp.dao.CashWasteBook;
import com.winsafe.drp.dao.CashWasteBookForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;

public class ListCashWasteBookAction  extends BaseAction {

	  public ActionForward execute(ActionMapping mapping, ActionForm form,
	                               HttpServletRequest request,
	                               HttpServletResponse response) throws Exception {

		  int pagesize = 20;

		  super.initdata(request);
			try {
				AppCashBank appcb = new AppCashBank();
				List cblist = appcb.getAllCashBankByOID(users.getMakeorganid());
				StringBuffer cbid = new StringBuffer(); 
				cbid.append("0");
				for(int i=0; i<cblist.size(); i++){
					CashBank cb = (CashBank)cblist.get(i);
					cbid.append(",").append(cb.getId());
				}
				
				String Condition = " cwb.cbid in ("+cbid.toString()+") "; 
				Map map = new HashMap(request.getParameterMap());
				Map tmpMap = EntityManager.scatterMap(map);
				String[] tablename = { "CashWasteBook" };
				String whereSql = EntityManager.getTmpWhereSql(map, tablename); 

				String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
						" RecordDate");
				whereSql = whereSql + timeCondition + Condition;
				whereSql = DbUtil.getWhereSql(whereSql); 


				AppCashWasteBook ail = new AppCashWasteBook();

				List<CashWasteBook> slls = ail.searchCashWasteBook(request,pagesize, whereSql);
				ArrayList arls = new ArrayList();
				
				for (CashWasteBook cwb: slls ) {
					CashWasteBookForm ilf = new CashWasteBookForm();
					ilf.setId(cwb.getId());
					ilf.setCbid(cwb.getCbid());
					ilf.setCbidname(appcb.getCashBankById(cwb.getCbid()).getCbname());
					ilf.setBillno(cwb.getBillno());
					ilf.setMemo(cwb.getMemo());
					ilf.setCyclefirstsum(cwb.getCyclefirstsum());
					ilf.setCycleinsum(cwb.getCycleinsum());
					ilf.setCycleoutsum(cwb.getCycleoutsum());
					ilf.setCyclebalancesum(cwb.getCyclebalancesum());
					ilf.setRecorddate(DateUtil.formatDate(cwb.getRecorddate()));
					arls.add(ilf);
				}
				
				
				request.setAttribute("arls", arls);
				request.setAttribute("cblist", cblist);
				DBUserLog.addUserLog(userid,9, "资金台账>>列表资金台帐"); 
				return mapping.findForward("list");
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	      String result = "databases.settlement.noexist";
          request.setAttribute("result", result);
          return new ActionForward("/sys/lockrecordclose.jsp");
	    }
	  }
}
