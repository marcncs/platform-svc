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
import com.winsafe.drp.dao.AppPaymentLog;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.PaymentLog;
import com.winsafe.drp.dao.PaymentLogForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class ListPaymentLogByPayableObjectAction
    extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		String poid=request.getParameter("POID");
		String orgid= request.getParameter("ORGID");
	    if(poid==null){
	    	poid=(String)request.getSession().getAttribute("poid");
	    }
	    if(orgid==null){
	    	orgid = (String)request.getSession().getAttribute("orgid");
	    }
	    request.getSession().setAttribute("poid",poid);
	    request.getSession().setAttribute("orgid", orgid);
		super.initdata(request);super.initdata(request);try{
			String Condition = " pl.poid ='"+poid +"' and pl.makeorganid='"+orgid+"' ";//and pl.makeid like '"+userid+"%' " ;
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			//String sql = "select * from sale_log as sl where "+Condition;
			String[] tablename = { "PaymentLog" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename); 
			String blur = DbUtil.getBlur(map, tmpMap, "KeysContent");
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			whereSql = whereSql + blur +timeCondition +Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			Object obj[] = (DbUtil.setDynamicPager(request, "PaymentLog as pl",
					whereSql, pagesize, "subCondition"));
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];

			AppPaymentLog apl = new AppPaymentLog();
			AppUsers au = new AppUsers();

			List slls = apl.getPaymentLog(request,pagesize, whereSql);
			ArrayList arls = new ArrayList();
			for (int i = 0; i < slls.size(); i++) {
				PaymentLogForm plf = new PaymentLogForm();
				PaymentLog o = (PaymentLog) slls.get(i);
				plf.setId(o.getId());
				plf.setPayee(o.getPayee());
				plf.setPaymodename(Internation.getStringByKeyPosition(
						"PayMode", request, o.getPaymode(), "global.sys.SystemResource"));
				plf.setPaysum(o.getPaysum());
				//plf.setMakeorganid(o.getMakeorganid());
				plf.setMakeidname(au.getUsersByid(o.getMakeid()).getRealname());
				plf.setMakedate(String.valueOf(o.getMakedate()).substring(0,10));

				arls.add(plf);
			}

			request.setAttribute("arls", arls);
			
			//DBUserLog.addUserLog(userid,"列表付款记录"); 
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
