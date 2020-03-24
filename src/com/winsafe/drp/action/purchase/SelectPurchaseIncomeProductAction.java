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

import com.winsafe.drp.dao.AppPurchaseIncome;
import com.winsafe.drp.dao.PurchaseIncomeProductForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class SelectPurchaseIncomeProductAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 20;
		String strpid = request.getParameter("pid");
		if(strpid==null){
			strpid=(String)request.getSession().getAttribute("pid");
	    }
	    request.getSession().setAttribute("pid",strpid);
	    String pid = strpid;

		try {
			String strCondition = " pi.id=pid.piid and pi.provideid='" + pid+"'";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			//String sql = "select * from product where " + strCondition;
			String[] tablename = { "PurchaseIncome","PurchaseIncomeDetail" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String blur = DbUtil.getBlur(map, tmpMap, "ProductName");
			whereSql = whereSql + blur + strCondition;
			whereSql = DbUtil.getWhereSql(whereSql); 

			Object obj[] = (DbUtil.setDynamicPager(request, "PurchaseIncome as pi,PurchaseIncomeDetail as pid",
					whereSql, pagesize, "subCondition"));
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];

			AppPurchaseIncome api = new AppPurchaseIncome();
			List pls = api.getPurchaseIncomeForPurchaseInvoice(pagesize, whereSql, tmpPgInfo);
			ArrayList sls = new ArrayList();

			for (int i = 0; i < pls.size(); i++) {
				PurchaseIncomeProductForm pipf = new PurchaseIncomeProductForm();
				Object[] o = (Object[]) pls.get(i);
				pipf.setId(Long.valueOf(o[0].toString()));
				pipf.setProductid(String.valueOf(o[2]));
				pipf.setProductname(String.valueOf(o[3]));
				pipf.setSpecmode(String.valueOf(o[4]));
				pipf.setUnitid(Integer.parseInt(o[5].toString()));
				pipf.setUnitidname(Internation.getStringByKeyPositionDB("CountUnit", 
						Integer.parseInt(o[5].toString())));
				pipf.setBatch(String.valueOf(o[6]));
				pipf.setUnitprice(Double.valueOf(o[7].toString()));
				pipf.setQuantity(Double.valueOf(o[8].toString()));
				pipf.setSubsum(Double.valueOf(o[9].toString()));
				sls.add(pipf);
			}

			request.setAttribute("pid", pid);
			request.setAttribute("sls", sls);
			return mapping.findForward("selectproduct");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
