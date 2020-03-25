package com.winsafe.sap.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.erp.services.CartonSeqServices;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.RequestTool;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.sap.dao.AppPrintJob;
import com.winsafe.sap.metadata.CartonSeqStatus;
import com.winsafe.sap.pojo.PrintJob;
 
public class SelectCartonSeqAction extends BaseAction {

	private static Logger logger = Logger.getLogger(SelectCartonSeqAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		initdata(request);
		CartonSeqServices css = new CartonSeqServices();
		AppPrintJob appPrintJob = new AppPrintJob();
		try {
			Integer planId = Integer.valueOf(request.getParameter("planId"));
			String range = request.getParameter("range"); 
			
			long startSeq[] = RequestTool.getLongs(request, "startSeq");
			long endSeq[] = RequestTool.getLongs(request, "endSeq");
			
			PrintJob printJob = appPrintJob.getPrintJobByID(planId);
			if (printJob != null 
					&& printJob.getCartonSeqStatus() != null
					&& printJob.getCartonSeqStatus()==CartonSeqStatus.RELATED.getValue()) {
				request.setAttribute("result", "该打印任务已关联过!");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			
			//验证所选的序号范围
			String resultMsg = css.validateRange(startSeq, endSeq, range, printJob.getNumberOfCases());
			if(!StringUtil.isEmpty(resultMsg)) {
				request.setAttribute("result", resultMsg);
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			
			int result = css.updateProductPlanSeq(printJob.getPrintJobId(), printJob.getProductId(), startSeq, endSeq);
			
			//更新数量与实际数量不符
			if(printJob.getNumberOfCases() != result) {
				HibernateUtil.rollbackTransaction();
				request.setAttribute("result", "设置失败,箱码序号状态可能已发生改变,请重试!");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			
			css.closeCartonSeq(printJob.getPrintJobId());
			
			printJob.setCartonSeqStatus(CartonSeqStatus.RELATED.getValue());
			appPrintJob.updPrintJob(printJob);
			
			DBUserLog.addUserLog(request, "编号：" + printJob.getPrintJobId());
			request.setAttribute("result", "箱码关联成功");
		} catch (Exception e) {
			logger.error("UpdProductPlanAction  error:", e);
		}
		return mapping.findForward("success");
	}

}