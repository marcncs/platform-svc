package com.winsafe.drp.action.machin;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppConsignMachin;
import com.winsafe.drp.dao.AppConsignMachinDetail;
import com.winsafe.drp.dao.ConsignMachin;
import com.winsafe.drp.dao.ConsignMachinDetail;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class AddConsignMachinAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			ConsignMachin ar = new ConsignMachin();
			String cmid = MakeCode.getExcIDByRandomTableName("consign_machin",2,"CE");
			ar.setId(cmid);
			ar.setPid(request.getParameter("pid"));
			ar.setPlinkman(request.getParameter("plinkman"));
			ar.setTel(request.getParameter("tel"));
			ar.setPaymode(Integer.valueOf(request.getParameter("paymode")));
			ar.setCtotalsum(Double.valueOf(request.getParameter("ctotalsum")));
			ar.setCproductid(request.getParameter("cproductid"));
			ar.setCproductname(request.getParameter("cproductname"));
			ar.setCspecmode(request.getParameter("cspecmode"));
			ar.setCunitid(RequestTool.getInt(request,"cunitid"));
			ar.setCquantity(RequestTool.getDouble(request,"cquantity"));
			ar.setCunitprice(RequestTool.getDouble(request,"cunitprice"));
			ar.setCompletequantity(0d);
			String completeintenddate = request.getParameter("completeintenddate");
			String tmpcompleteintenddate =completeintenddate.replace('-', '/');
		      if (tmpcompleteintenddate != null && tmpcompleteintenddate.trim().length() > 0) {
		    	  ar.setCompleteintenddate(new Date(tmpcompleteintenddate));
		      }
		    ar.setRemark(request.getParameter("remark"));
		    ar.setMakeorganid(users.getMakeorganid());
		    ar.setMakedeptid(users.getMakedeptid());
		    ar.setMakeid(userid);
		    ar.setMakedate(DateUtil.getCurrentDate());
		    ar.setIsaudit(0);
		    ar.setAuditid(0);
		    ar.setIsendcase(0);
		    

			
			String strproductid[] = request.getParameterValues("productid");
			String productcode[]=RequestTool.getStrings(request, "productcode");
			String strproductname[] = request.getParameterValues("productname");
			String strspecmode[] = request.getParameterValues("specmode");
			int unitid[] = RequestTool.getInts(request,"unitid");
			double unitprice[] = RequestTool.getDoubles(request,"unitprice");
			double quantity[] = RequestTool.getDoubles(request,"quantity");
			String productid;
			String productname, specmode;
			
			AppConsignMachin asl = new AppConsignMachin();
			
			AppConsignMachinDetail asld = new AppConsignMachinDetail();
			if(strproductid!=null){
				ConsignMachinDetail[] sods = new ConsignMachinDetail[strproductid.length];
			for (int i = 0; i < strproductid.length; i++) {
				productid = strproductid[i];
				productname = strproductname[i];
				specmode = strspecmode[i];

						ConsignMachinDetail sod = new ConsignMachinDetail();
				sod.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"consign_machin_detail", 0, "")));
				sod.setCmid(cmid);
				sod.setProductid(productid);
				sod.setProductname(productname);
				sod.setSpecmode(specmode);
				sod.setUnitid(unitid[i]);
				sod.setUnitprice(unitprice[i]);
				sod.setQuantity(quantity[i]);
				sod.setAlreadyquantity(0d);
				sod.setSubsum(unitprice[i] * quantity[i]);
				sods[i] = sod;
				
			}
			asld.addConsignMachinDetail(sods);
			}

			asl.addConsignMachin(ar);

			request.setAttribute("result", "databases.add.success");
			DBUserLog.addUserLog(userid,3, "新增委外加工单,编号："+cmid);
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
