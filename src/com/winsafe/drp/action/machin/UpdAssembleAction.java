package com.winsafe.drp.action.machin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppAssemble;
import com.winsafe.drp.dao.AppAssembleDetail;
import com.winsafe.drp.dao.Assemble;
import com.winsafe.drp.dao.AssembleDetail;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class UpdAssembleAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
		    
			AppAssemble aso = new AppAssemble();
			String id = request.getParameter("id");
			Assemble ar = aso.getAssembleByID(id);
			Assemble oldW = (Assemble)BeanUtils.cloneBean(ar);
			ar.setId(id);
			ar.setAproductid(request.getParameter("aproductid"));
			ar.setAproductname(request.getParameter("aproductname"));
			ar.setAspecmode(request.getParameter("aspecmode"));
			ar.setAunitid(Integer.valueOf(request.getParameter("aunitid")));
			ar.setAquantity(Double.valueOf(request.getParameter("aquantity")));
			ar.setAdept(RequestTool.getInt(request, "MakeDeptID"));
			ar.setCompleteintenddate(DateUtil.StringToDate(request
					.getParameter("completeintenddate")));
			ar.setRemark(request.getParameter("remark"));
			
			
			
			String strproductid[] = request.getParameterValues("productid");
			String strproductname[] = request.getParameterValues("productname");
			String strspecmode[] = request.getParameterValues("specmode");
			int unitid[] = RequestTool.getInts(request,"unitid");
			double quantity[] = RequestTool.getDoubles(request,"quantity");
			//String strsubsum[] = request.getParameterValues("subsum");			
			String productid;
			String productname, specmode;

			AppAssembleDetail asld = new AppAssembleDetail();
			asld.delAssembleDetailByAid(id);

			for (int i = 0; i < strproductid.length; i++) {
				productid = strproductid[i];
				productname = strproductname[i];
				specmode = strspecmode[i];
				
		        AssembleDetail sod = new AssembleDetail();
				sod.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("assemble_detail", 0, "")));
				sod.setAid(id);
				sod.setProductid(productid);
				sod.setProductname(productname);				
				sod.setSpecmode(specmode);
				sod.setUnitid(unitid[i]);
				//sod.setUnitprice(unitprice);
				sod.setQuantity(quantity[i]);
				sod.setAlreadyquantity(0d);
				//sod.setSubsum(unitprice * quantity);
				asld.addAssembleDetail(sod);
			}
			
			aso.updAssemble(ar);

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
