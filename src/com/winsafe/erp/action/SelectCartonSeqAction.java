package com.winsafe.erp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger; 
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.erp.dao.AppProductPlan;
import com.winsafe.erp.pojo.ProductPlan;
import com.winsafe.erp.services.CartonSeqServices;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.RequestTool;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.sap.metadata.YesOrNo;
 
public class SelectCartonSeqAction extends BaseAction {

	private AppProductPlan appProductPlan = new AppProductPlan();
	private static Logger logger = Logger.getLogger(SelectCartonSeqAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		initdata(request);
		CartonSeqServices css = new CartonSeqServices();
		try {
			Integer planId = Integer.valueOf(request.getParameter("planId"));
			String range = request.getParameter("range");
			
			long startSeq[] = RequestTool.getLongs(request, "startSeq");
			long endSeq[] = RequestTool.getLongs(request, "endSeq");
			
			ProductPlan productPlan = appProductPlan.getProductPlanByID(planId);
			if (productPlan != null 
					&& productPlan.getCloseFlag() != null
					&& productPlan.getCloseFlag()==1) {
				request.setAttribute("result", "该计划已结束!无法修改!");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			
			//验证所选的序号范围
			String resultMsg = css.validateRange(startSeq, endSeq, range, productPlan.getBoxnum());
			if(!StringUtil.isEmpty(resultMsg)) { 
				request.setAttribute("result", resultMsg);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			int result = css.updateProductPlanSeq(productPlan.getPrintJobId(), productPlan.getProductId(), startSeq, endSeq);
			
			//更新数量与实际数量不符
			if(productPlan.getBoxnum() != result) {
				HibernateUtil.rollbackTransaction();
				request.setAttribute("result", "设置失败,箱码序号状态可能已发生改变,请重试!");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			
			productPlan.setCartonSeqFlag(YesOrNo.YES.getValue());
			appProductPlan.updProductPlan(productPlan);
			
			DBUserLog.addUserLog(request, "编号：" + productPlan.getId());
			request.setAttribute("result", "箱码关联成功");
		} catch (Exception e) {
			logger.error("UpdProductPlanAction  error:", e);
		}
		return mapping.findForward("success");
	}

}