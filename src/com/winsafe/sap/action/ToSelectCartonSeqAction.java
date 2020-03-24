package com.winsafe.sap.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger; 
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.erp.dao.AppCartonSeq;
import com.winsafe.erp.services.CartonSeqServices;
import com.winsafe.sap.dao.AppPrintJob;
import com.winsafe.sap.pojo.PrintJob;

public class ToSelectCartonSeqAction extends BaseAction {

	private static Logger logger = Logger.getLogger(ToSelectCartonSeqAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppCartonSeq appCs = new AppCartonSeq();
		CartonSeqServices css = new CartonSeqServices();
		AppPrintJob appPrintJob = new AppPrintJob();
		initdata(request);
		try {
			String id = request.getParameter("ID");

			PrintJob printJob = appPrintJob.getPrintJobByID(Integer.valueOf(id));
			List<String> seqList = appCs.getUsableSeqByProductId(printJob.getProductId());
			if(seqList.size() < printJob.getNumberOfCases()) {
				request.setAttribute("result", "没有足够的箱序号可关联");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			request.setAttribute("range", css.getUsableSeqRangeString(seqList));
			request.setAttribute("qty", printJob.getNumberOfCases());
			request.setAttribute("planId", printJob.getPrintJobId());
			return mapping.findForward("success");
		} catch (Exception e) {
			logger.error("", e);
		}
		return mapping.findForward("success");
	}

}
