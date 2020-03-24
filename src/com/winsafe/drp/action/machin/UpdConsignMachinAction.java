package com.winsafe.drp.action.machin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
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

public class UpdConsignMachinAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
		    
			AppConsignMachin aso = new AppConsignMachin();
			String id = request.getParameter("id");
			ConsignMachin ar = aso.getConsignMachinByID(id);
			ConsignMachin oldW = (ConsignMachin)BeanUtils.cloneBean(ar);

			ar.setId(id);
			ar.setPid(request.getParameter("pid"));
			ar.setPlinkman(request.getParameter("plinkman"));
			ar.setTel(request.getParameter("tel"));
			ar.setPaymode(Integer.valueOf(request.getParameter("paymode")));
			ar.setCtotalsum(Double.valueOf(request.getParameter("ctotalsum")));
			ar.setCproductid(request.getParameter("cproductid"));
			ar.setCproductname(request.getParameter("cproductname"));
			ar.setCspecmode(request.getParameter("cspecmode"));
			ar.setCunitid(Integer.valueOf(request.getParameter("cunitid")));
			ar.setCquantity(Double.valueOf(request.getParameter("cquantity")));
			ar.setCunitprice(Double.valueOf(request.getParameter("cunitprice")));
			ar.setCompletequantity(0d);
		    ar.setCompleteintenddate(DateUtil.StringToDate(request
					.getParameter("completeintenddate")));
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

			AppConsignMachinDetail asld = new AppConsignMachinDetail();
			asld.delConsignMachinDetail(id);
			if(strproductid!=null){
				ConsignMachinDetail[] cmds = new ConsignMachinDetail[strproductid.length];
			for (int i = 0; i < strproductid.length; i++) {
				productid = strproductid[i];
				productname = strproductname[i];
				specmode = strspecmode[i];
				
		        ConsignMachinDetail sod = new ConsignMachinDetail();
				sod.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("consign_machin_detail", 0, "")));
				sod.setCmid(id);
				sod.setProductid(productid);
				sod.setProductname(productname);				
				sod.setSpecmode(specmode);
				sod.setUnitid(unitid[i]);
				sod.setUnitprice(unitprice[i]);
				sod.setQuantity(quantity[i]);
				sod.setAlreadyquantity(0d);
				sod.setSubsum(unitprice[i] * quantity[i]);
				cmds[i]=sod;
			}
			asld.addConsignMachinDetail(cmds);
			}
			
			aso.updConsignMachin(ar);
			request.setAttribute("result", "databases.upd.success");

			DBUserLog.addUserLog(userid,3, "委外加工>>修改委外加工单,编号："+id, oldW, ar); 		
		
			return mapping.findForward("success");
		} catch (Exception e) {
			System.out.println("the exception is:" + e.toString());			
			e.printStackTrace();
		} 

		return new ActionForward(mapping.getInput());
	}
}
