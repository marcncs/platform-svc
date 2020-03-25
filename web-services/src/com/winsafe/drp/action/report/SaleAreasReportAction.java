package com.winsafe.drp.action.report;

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

import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppSaleOrderDetail;
import com.winsafe.drp.dao.SaleOrderDetailForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class SaleAreasReportAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		String b= request.getParameter("BeginDate");
		String e= request.getParameter("EndDate");
		
		try{
			String Condition =" so.id=sod.soid and so.isendcase=1 ";
			Map map = new HashMap(request.getParameterMap());
		      Map tmpMap = EntityManager.scatterMap(map);
		      String[] tablename={"SaleOrder","SaleOrderDetail"};
		      String whereSql = EntityManager.getTmpWhereSql(map, tablename);
		      String timeCondition = DbUtil.getTimeCondition(map, tmpMap," MakeDate");
		      whereSql = whereSql + timeCondition + Condition; 
		      whereSql = DbUtil.getWhereSql(whereSql); 
		      //Object obj[] = (DbUtil.setPager(request,"SaleLog as sl ",whereSql,pagesize));
		      Object obj[] = DbUtil.setExtraPager(request," Sale_Order as so,Sale_Order_Detail as sod ",whereSql,pagesize," sod.productid ");
		      SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
		      whereSql = (String) obj[1];
			AppSaleOrderDetail asod = new AppSaleOrderDetail();
			AppProduct ap = new AppProduct();
			List sodls = asod.getSaleAreasReport(whereSql, pagesize, tmpPgInfo);
			ArrayList also = new ArrayList();
			for(int i=0;i<sodls.size();i++){
				SaleOrderDetailForm sodf = new SaleOrderDetailForm();
				Object[] o =(Object[])sodls.get(i);
				sodf.setProductid(o[0].toString());
				sodf.setProductname(ap.getProductByID(sodf.getProductid()).getProductname());
				//sodf.setSpecmode(String.valueOf(o[2]));
				sodf.setUnitidname(Internation.getStringByKeyPositionDB("CountUnit",			          
			            ap.getProductByID(sodf.getProductid()).getCountunit()));
				sodf.setQuantity(Double.valueOf(o[1].toString()));
				also.add(sodf);
				
			}
		      request.setAttribute("also", also);
		      request.setAttribute("b",b);
		      request.setAttribute("e",e);
		      
		      return mapping.findForward("saleareas");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
}
