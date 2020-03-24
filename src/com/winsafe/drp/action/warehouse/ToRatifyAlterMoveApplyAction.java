package com.winsafe.drp.action.warehouse;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AlterMoveApply;
import com.winsafe.drp.dao.AlterMoveApplyDetail;
import com.winsafe.drp.dao.AlterMoveApplyDetailForm;
import com.winsafe.drp.dao.AlterMoveApplyForm;
import com.winsafe.drp.dao.AppAlterMoveApply;
import com.winsafe.drp.dao.AppAlterMoveApplyDetail;
import com.winsafe.drp.dao.AppInvoiceConf;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.InvoiceConf;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.OrganForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.Internation;

public class ToRatifyAlterMoveApplyAction
    extends BaseAction {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    String id = request.getParameter("AAID");
    UsersBean users = UserManager.getUser(request);
    Integer userid = users.getUserid();
    super.initdata(request);try{
      AppAlterMoveApply asm = new AppAlterMoveApply();
      AlterMoveApply sm = asm.getAlterMoveApplyByID(id);

      if (sm.getIsaudit() == 0) { 
        String result = "databases.record.noauditnoratify";
        request.setAttribute("result", result);
        return new ActionForward("/sys/lockrecordclose.jsp");
      }
      if (sm.getIsratify() == 1) { 
          String result = "databases.record.approve";
          request.setAttribute("result", result);
          return new ActionForward("/sys/lockrecordclose.jsp");
        }
      
      if (!sm.getOutorganid().equals(users.getMakeorganid())) { 
          String result = "databases.record.noorgan";
          request.setAttribute("result", result);
          return new ActionForward("/sys/lockrecordclose.jsp");
        }

      AlterMoveApplyForm smf= new AlterMoveApplyForm();
      smf.setId(sm.getId());
      smf.setMovedate(sm.getMovedate());
      smf.setOutorganid(sm.getOutorganid());
      smf.setMakeorganid(sm.getMakeorganid());
      smf.setMakeorganidname(sm.getMakeorganidname());     
      smf.setTotalsum(sm.getTotalsum());
      smf.setPaymentmodename(Internation.getSelectPayAllDBDef(
				"paymentmode", sm.getPaymentmode()));
      smf.setInvmsg(sm.getInvmsg());
      smf.setTransportmodename(Internation.getSelectTagByKeyAllDBDef(
				"TransportMode", "transportmode", sm.getTransportmode()));
      smf.setTransportaddr(sm.getTransportaddr());
      smf.setTickettitle(sm.getTickettitle());
      smf.setMovecause(sm.getMovecause());
      smf.setRemark(sm.getRemark());


      AppAlterMoveApplyDetail asmd = new AppAlterMoveApplyDetail();
      List smdls = asmd.getAlterMoveApplyDetailByAmID(id);
      ArrayList als = new ArrayList();

      for(int i=0;i<smdls.size();i++){
    	  AlterMoveApplyDetailForm smdf = new AlterMoveApplyDetailForm();
    	  AlterMoveApplyDetail o = (AlterMoveApplyDetail)smdls.get(i);
        smdf.setProductid(o.getProductid());
        smdf.setProductname(o.getProductname());
        smdf.setSpecmode(o.getSpecmode());
        smdf.setUnitid(o.getUnitid());
        smdf.setUnitidname(Internation.getStringByKeyPositionDB("CountUnit",         
            o.getUnitid()));
        smdf.setUnitprice(o.getUnitprice());
        smdf.setQuantity(o.getQuantity());
        smdf.setCanquantity(0d);
        smdf.setSubsum(o.getSubsum());
        als.add(smdf);
      }


		AppOrgan ao = new AppOrgan();

//		
//		AppWarehouse aw = new AppWarehouse();
//		List wls = aw.getWarehouseListByOID(users.getMakeorganid());
//		ArrayList alw = new ArrayList();
//		for (int i = 0; i < wls.size(); i++) {
//			// Warehouse w = new Warehouse();
//			Warehouse o = (Warehouse) wls.get(i);
//			// w.setId(Long.valueOf(o[0].toString()));
//			// w.setWarehousename(o[1].toString());
//			alw.add(o);
//		}
		

		List ols = ao.getAllOrgan();
		ArrayList alos = new ArrayList();
		for (int o = 0; o < ols.size(); o++) {
			OrganForm ub = new OrganForm();
			Organ of = (Organ) ols.get(o);
			ub.setId(of.getId());
			ub.setOrganname(of.getOrganname());
			alos.add(ub);
		}

		request.setAttribute("alos", alos);
		
		AppInvoiceConf aic = new AppInvoiceConf();
		List uls = aic.getAllInvoiceConf();
		ArrayList icls = new ArrayList();
		for (int u = 0; u < uls.size(); u++) {
			InvoiceConf ic = (InvoiceConf) uls.get(u);
			icls.add(ic);
		}
		
		request.setAttribute("icls", icls);
      
//      request.setAttribute("alw", alw);
      request.setAttribute("smf",smf);
      request.setAttribute("als",als);

      return mapping.findForward("toratify");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
