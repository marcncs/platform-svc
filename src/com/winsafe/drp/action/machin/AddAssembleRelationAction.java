package com.winsafe.drp.action.machin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppAssembleRelation;
import com.winsafe.drp.dao.AppAssembleRelationDetail;
import com.winsafe.drp.dao.AssembleRelation;
import com.winsafe.drp.dao.AssembleRelationDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class AddAssembleRelationAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();

		try {
			AssembleRelation ar = new AssembleRelation();
			String arid = MakeCode.getExcIDByRandomTableName("assemble_relation",2,"");
			ar.setId(arid);
			ar.setArproductid(request.getParameter("arproductid"));
			ar.setArproductname(request.getParameter("arproductname"));
			ar.setArspecmode(request.getParameter("arspecmode"));
			ar.setArunitid(Integer.valueOf(request.getParameter("arunitid")));
			ar.setArquantity(RequestTool.getDouble(request,"arquantity"));
			ar.setRemark(request.getParameter("remark"));
			ar.setMakeid(userid);
			ar.setMakeorganid(users.getMakeorganid());
			ar.setMakedate(DateUtil.getCurrentDate());
			ar.setIsaudit(0);	
			ar.setAuditid(0);
			ar.setMakedeptid(users.getMakedeptid());
			ar.setKeyscontent(ar.getArproductid()+","+ar.getArproductname());
			
			String strproductid[] = request.getParameterValues("productid");
			String strproductname[] = request.getParameterValues("productname");
			String strspecmode[] = request.getParameterValues("specmode");
			int unitid[] = RequestTool.getInts(request,"unitid");
			double unitprice[] = RequestTool.getDoubles(request,"unitprice");
			double quantity[] = RequestTool.getDoubles(request,"quantity");
			String productid;
			String productname, specmode;
			
			AppAssembleRelation asl = new AppAssembleRelation();
			
			
			AppAssembleRelationDetail asld = new AppAssembleRelationDetail();
			if(strproductid!=null){
			AssembleRelationDetail[] pidsum = new AssembleRelationDetail[strproductid.length];
			for (int i = 0; i < strproductid.length; i++) {
				productid = strproductid[i];
				productname = strproductname[i];
				specmode = strspecmode[i];

				AssembleRelationDetail sod = new AssembleRelationDetail();
				sod.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"assemble_relation_detail", 0, "")));
				sod.setArid(arid);
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
			asl.addAssembleRelation(ar);

			request.setAttribute("result", "databases.add.success");
			DBUserLog.addUserLog(userid, 3, "组装关系设置>>新增组装关系,编号："+arid);
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
