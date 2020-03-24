package com.winsafe.drp.action.machin;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

public class AddAssembleAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			Assemble ar = new Assemble();
			String aid = MakeCode.getExcIDByRandomTableName("assemble",2,"A");
			ar.setId(aid);
			ar.setAproductid(request.getParameter("aproductid"));
			ar.setAproductname(request.getParameter("aproductname"));
			ar.setAspecmode(request.getParameter("aspecmode"));
			ar.setAunitid(Integer.valueOf(request.getParameter("aunitid")));
			ar.setAquantity(RequestTool.getDouble(request, "aquantity"));
			ar.setCquantity(0d);
			ar.setAdept(RequestTool.getInt(request, "MakeDeptID"));
			String completeintenddate = request.getParameter("completeintenddate");
			String tmpcompleteintenddate =completeintenddate.replace('-', '/');
		      if (tmpcompleteintenddate != null && tmpcompleteintenddate.trim().length() > 0) {
		    	  ar.setCompleteintenddate(new Date(tmpcompleteintenddate));
		      }
		      
			ar.setRemark(request.getParameter("remark"));
			ar.setMakeorganid(users.getMakeorganid());
			ar.setMakeid(userid);
			ar.setMakedate(DateUtil.getCurrentDate());
			ar.setIsaudit(0);	
			ar.setAuditid(0);
			ar.setIsendcase(0);
			ar.setMakedeptid(users.getMakedeptid());
			
			String strproductid[] = request.getParameterValues("productid");
			String strproductname[] = request.getParameterValues("productname");
			String strspecmode[] = request.getParameterValues("specmode");
			int unitid[] = RequestTool.getInts(request, "unitid");
			double unitprice[] = RequestTool.getDoubles(request, "unitprice");
			double quantity[] = RequestTool.getDoubles(request, "quantity");
			String productid;
			String productname, specmode;
			
			AppAssemble asl = new AppAssemble();
			
			AppAssembleDetail asld = new AppAssembleDetail();
			for (int i = 0; i < strproductid.length; i++) {
				productid = strproductid[i];
				productname = strproductname[i];
				specmode = strspecmode[i];
				
				AssembleDetail sod = new AssembleDetail();
				sod.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"assemble_detail", 0, "")));
				sod.setAid(aid);
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

			asl.addAssemble(ar);

			request.setAttribute("result", "databases.add.success");
			DBUserLog.addUserLog(userid,3, "组装单>>新增组装单,编号："+aid);
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
