package com.winsafe.drp.action.assistant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketIdcode;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.sap.pojo.CartonCode;
import com.winsafe.sap.pojo.PrimaryCode;
import com.winsafe.sap.pojo.PrintJob;
public class WlmIdcodeDetailAction extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrimaryCode pc = new PrimaryCode();
		CartonCode cc = new CartonCode();
		Product p = new Product();
		PrintJob pg = new PrintJob();
		TakeTicketIdcode ttid = new TakeTicketIdcode();
		TakeTicket tt = new TakeTicket();
		int pagesize = 20;
		super.initdata(request);
		try {
			pc.setPrimaryCode(request.getParameter("primaryCode"));
			pc.setCovertCode(request.getParameter("covertCode"));
			pc.setCartonCode(request.getParameter("cartonCode"));
			pg.setPackSize(request.getParameter("packSize"));
			p.setSpecmode(request.getParameter("specmode"));
			pg.setBatchNumber(request.getParameter("batchNumber"));
			ttid.setProducedate(request.getParameter("producedate"));
			ttid.setVad(request.getParameter("cad"));
			pg.setPlantName(request.getParameter("plantName"));
			tt.setOname(request.getParameter("oname"));
			tt.setInOid(request.getParameter("inOid"));
			tt.setInOname(request.getParameter("inOname"));
			pg.setExpiryDate(request.getParameter("expiryDate"));
		   
			request.setAttribute("cc", cc);
			request.setAttribute("pc", pc);
			request.setAttribute("p", p);
			request.setAttribute("tt", tt);
			request.setAttribute("pg", pg);
			request.setAttribute("ttid", ttid);
			request.setAttribute("prompt", "r");
			DBUserLog.addUserLog(userid, "流向查询", "流向查询>>物流码全链查询");
			return mapping.findForward("success");
	} catch (Exception e) {
		e.printStackTrace();
	}
	return new ActionForward(mapping.getInput());
	
	}
}
