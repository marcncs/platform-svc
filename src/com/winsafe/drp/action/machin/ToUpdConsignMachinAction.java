package com.winsafe.drp.action.machin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppConsignMachin;
import com.winsafe.drp.dao.AppConsignMachinDetail;
import com.winsafe.drp.dao.AppProvider;
import com.winsafe.drp.dao.ConsignMachin;
import com.winsafe.drp.dao.ConsignMachinDetail;
import com.winsafe.drp.dao.ConsignMachinDetailForm;
import com.winsafe.drp.dao.ConsignMachinForm;
import com.winsafe.hbm.util.HtmlSelect;
import com.winsafe.hbm.util.Internation;

public class ToUpdConsignMachinAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		System.out.println("------------"+id);
		try {
			AppProvider ap = new AppProvider();
			AppConsignMachin acm = new AppConsignMachin();			
			ConsignMachin cm = acm.getConsignMachinByID(id);
			System.out.println("------------"+cm.getCproductid());
			if (cm.getIsaudit()==1) {
				String result = "databases.record.lock";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			ConsignMachinForm cmf = new ConsignMachinForm();
			cmf.setId(cm.getId());
			cmf.setPid(cm.getPid());
			cmf.setPidname(ap.getProviderByID(cm.getPid()).getPname());
			cmf.setPlinkman(cm.getPlinkman());
			cmf.setTel(cm.getTel());
			cmf.setPaymodename(Internation.getSelectTagByKeyAll("PayMode",
		            request,"paymode", 
		            String.valueOf(cm.getPaymode()), null));
			cmf.setCtotalsum(cm.getCtotalsum());
			cmf.setCproductid(cm.getCproductid());
			cmf.setCproductname(cm.getCproductname());
			cmf.setCspecmode(cm.getCspecmode());
			cmf.setCunitid(cm.getCunitid());
			cmf.setCunitidname(HtmlSelect.getResourceName(request, "CountUnit", cm.getCunitid()));
			cmf.setCquantity(cm.getCquantity());
			cmf.setCunitprice(cm.getCunitprice());
			cmf.setCompletequantity(cm.getCompletequantity());
			cmf.setCompleteintenddate(String.valueOf(cm.getCompleteintenddate()));
			cmf.setRemark(cm.getRemark());

			AppConsignMachinDetail asld = new AppConsignMachinDetail();
			List slls = asld.getConsignMachinDetailBySIID(id);
			ArrayList als = new ArrayList();
			ConsignMachinDetail ard = null;
			
			for (int i = 0; i < slls.size(); i++) {
				ConsignMachinDetailForm sodf = new ConsignMachinDetailForm();
				ard = (ConsignMachinDetail) slls.get(i);
				sodf.setProductid(ard.getProductid());
				sodf.setProductname(ard.getProductname());
				sodf.setSpecmode(ard.getSpecmode());
				sodf.setUnitid(ard.getUnitid());
				sodf.setUnitidname(HtmlSelect.getResourceName(request,
						"CountUnit", ard.getUnitid()));
				sodf.setUnitprice(ard.getUnitprice());
				sodf.setQuantity(Double.valueOf(ard.getQuantity()));
				sodf.setSubsum(Double.valueOf(ard.getSubsum()));
				als.add(sodf);
			}

//	    	List dls = ad.getDept();
//	        ArrayList aldept = new ArrayList();
//	        for(int i=0;i<dls.size();i++){
//	       	 Dept d = new Dept();
//	       	 Object[] ob = (Object[]) dls.get(i);
//	       	 d.setId(Long.valueOf(ob[0].toString()));
//	       	 d.setDeptname(ob[1].toString());
//	       	 aldept.add(d);
//	        }
//	        
//	        request.setAttribute("aldept", aldept);

			request.setAttribute("cmf", cmf);
			request.setAttribute("als", als);
			return mapping.findForward("toupd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
