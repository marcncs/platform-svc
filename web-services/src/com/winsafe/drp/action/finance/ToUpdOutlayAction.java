package com.winsafe.drp.action.finance;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCashBank;
import com.winsafe.drp.dao.AppOutlay;
import com.winsafe.drp.dao.AppOutlayDetail;
import com.winsafe.drp.dao.Outlay;
import com.winsafe.drp.dao.OutlayDetail;
import com.winsafe.drp.dao.OutlayDetailForm;
import com.winsafe.hbm.util.Internation;

public class ToUpdOutlayAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		super.initdata(request);super.initdata(request);try{
		      AppOutlay aol = new AppOutlay();
		      Outlay ol = new Outlay();
//		      AppUsers au = new AppUsers();
//		      AppCustomer ac =new AppCustomer();
		      ol = aol.getOutlayByID(id);

		      if (ol.getIsaudit().intValue() == 1) { 
		        request.setAttribute("result", "databases.record.lock");
		        return new ActionForward("/sys/lockrecordclose.jsp");
		      }

//		      OutlayForm olf= new OutlayForm();
//		      olf.setId(id);
//		      olf.setOutlayid(ol.getOutlayid());
//		      olf.setOutlaydept(ol.getOutlaydept());
//		      olf.setCastdept(ol.getCastdept());
//		      olf.setCaster(ol.getCaster());
//		      olf.setTotaloutlay(ol.getTotaloutlay());
//		      olf.setThisresist(ol.getThisresist());
//		      olf.setFactpay(ol.getFactpay());
//		      olf.setFundsrc(ol.getFundsrc());
//		      olf.setRemark(ol.getRemark());
		      AppOutlayDetail aod = new AppOutlayDetail();
		      List wddls = aod.getOutlayDetailByOID(id);
		      ArrayList als = new ArrayList();

		      for(int i=0;i<wddls.size();i++){
		    	  OutlayDetailForm odf = new OutlayDetailForm();
		    	  OutlayDetail o = (OutlayDetail)wddls.get(i);
		        odf.setOutlayprojectidname(Internation.getSelectTagByKeyAllDBDef("OutlayProject", "OutlayProject", o.getOutlayprojectid()));
		        odf.setVoucher(o.getVoucher());
		        odf.setRemark(o.getRemark());
		        odf.setOutlaysum(o.getOutlaysum());
		        als.add(odf);
		      }
		      
//		      AppDept ad = new AppDept();
//		  	  List dls = ad.getDept();
//		      ArrayList aldept = new ArrayList();
//		      for(int i=0;i<dls.size();i++){
//		     	 Dept d = new Dept();
//		     	 Object[] ob = (Object[]) dls.get(i);
//		     	 d.setId(Long.valueOf(ob[0].toString()));
//		     	 d.setDeptname(ob[1].toString());
//		     	 aldept.add(d);
//		      }
//		      
//
//		      List uls = au.getIDAndLoginName();
//		      ArrayList auls = new ArrayList();
//		      for(int u=0;u<uls.size();u++){
//		      	UsersBean us = new UsersBean();
//		      	Object[] ub = (Object[]) uls.get(u);
//		      	us.setUserid(Long.valueOf(ub[0].toString()));
//		      	us.setRealname(ub[2].toString());
//		      	auls.add(us);
//		      }
		      
		      String outlayprojectselect = Internation.getSelectTagByKeyAllDB("OutlayProject","OutlayProject", false);
		      AppCashBank apcb = new AppCashBank();
		      List cblist = apcb.getAllCashBank();
		      
		      request.setAttribute("outlayprojectselect", outlayprojectselect);
		      request.setAttribute("cblist", cblist);
//		      request.setAttribute("aldept", aldept);
		      request.setAttribute("olf",ol);
		      request.setAttribute("als",als);
//		      request.setAttribute("auls",auls);

		      return mapping.findForward("toupd");
		    } catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
	
	
}
