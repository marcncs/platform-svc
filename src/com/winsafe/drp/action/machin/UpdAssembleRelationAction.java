package com.winsafe.drp.action.machin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppAssembleRelation;
import com.winsafe.drp.dao.AppAssembleRelationDetail;
import com.winsafe.drp.dao.AssembleRelation;
import com.winsafe.drp.dao.AssembleRelationDetail;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class UpdAssembleRelationAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
		    
			AppAssembleRelation aso = new AppAssembleRelation();
			String id = request.getParameter("id");
			AssembleRelation ar = aso.getAssembleRelationByID(id);
			AssembleRelation oldW = (AssembleRelation)BeanUtils.cloneBean(ar);
			ar.setId(id);
			ar.setArproductid(request.getParameter("arproductid"));
			ar.setArproductname(request.getParameter("arproductname"));
			ar.setArspecmode(request.getParameter("arspecmode"));
			ar.setArunitid(Integer.valueOf(request.getParameter("arunitid")));
			ar.setArquantity(Double.valueOf(request.getParameter("arquantity")));
			ar.setRemark(request.getParameter("remark"));
			
			
			
			String strproductid[] = request.getParameterValues("productid");
			String strproductname[] = request.getParameterValues("productname");
			String strspecmode[] = request.getParameterValues("specmode");
			int unitid[] = RequestTool.getInts(request, "unitid");
			double unitprice[] = RequestTool.getDoubles(request, "unitprice");
			double quantity[] = RequestTool.getDoubles(request, "quantity");
			//String strsubsum[] = request.getParameterValues("subsum");			
			String productid;
			String productname, specmode;

			
			AppAssembleRelationDetail asld = new AppAssembleRelationDetail();
			asld.delAssembleRelationDetail(id);
			if(strproductid!=null){
			AssembleRelationDetail[] pidsum = new AssembleRelationDetail[strproductid.length];
			for (int i = 0; i < strproductid.length; i++) {
				productid = strproductid[i];
				productname = strproductname[i];
				specmode = strspecmode[i];
				
		        AssembleRelationDetail sod = new AssembleRelationDetail();
				sod.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("assemble_relation_detail", 0, "")));
				sod.setArid(id);
				sod.setProductid(productid);
				sod.setProductname(productname);				
				sod.setSpecmode(specmode);
				sod.setUnitid(unitid[i]);
				sod.setUnitprice(unitprice[i]);
				sod.setQuantity(quantity[i]);
				sod.setSubsum(unitprice[i] * quantity[i]);
				pidsum[i] = sod;
			}
			asld.addAssembleRelationDetail(pidsum);
			}
			aso.updAssembleRelationByID(ar);

			request.setAttribute("result", "databases.upd.success");

			DBUserLog.addUserLog(userid,3, "组装关系>>修改组装关系,编号："+id, oldW, ar); 	
			return mapping.findForward("success");
		} catch (Exception e) {
			System.out.println("the exception is:" + e.toString());			
			e.printStackTrace();
		} 

		return new ActionForward(mapping.getInput());
	}
}
