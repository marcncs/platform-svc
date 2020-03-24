package com.winsafe.erp.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.StringUtil;
import com.winsafe.erp.dao.AppPrepareCode;
import com.winsafe.erp.dao.AppProductPlan;
import com.winsafe.erp.dao.AppUnitInfo;
import com.winsafe.erp.pojo.PrepareCode;
import com.winsafe.erp.pojo.ProductPlan;
import com.winsafe.erp.pojo.UnitInfo;
import com.winsafe.mail.util.StringUtils;

public class ApproveProductPlanAction extends BaseAction {

	private AppProductPlan appProductPlan = new AppProductPlan();
	private static Logger logger = Logger.getLogger(ApproveProductPlanAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		initdata(request);

		try {
			String type = request.getParameter("type");
			if(!StringUtils.isEmpty(type)){
				Integer id = Integer.valueOf(request.getParameter("ID"));
				ProductPlan plan = appProductPlan.getProductPlanByID(id);
				plan.setApprovalFlag(1);
				plan.setTemp(null);
				plan.setTemp("重新处理");
				appProductPlan.updProductPlan(plan);
				DBUserLog.addUserLog(request, "编号：" + id);
				request.setAttribute("result", "重新处理成功");
			}else{
				Integer id = Integer.valueOf(request.getParameter("ID"));
				ProductPlan plan = appProductPlan.getProductPlanByID(id);
				
				AppUnitInfo appUnitInfo = new AppUnitInfo();
				UnitInfo unitInfo = appUnitInfo.getUnitInfoByOidAndPid(plan.getOrganId(),plan.getProductId());
				if(unitInfo==null){
					request.setAttribute("result", "该工厂未配置产品的托盘信息，请配置!");
					return mapping.findForward("error");
				}
				
				//托对应箱数量
				Integer t_b = unitInfo.getUnitCount();
				//余
				Integer yu = plan.getBoxnum()%t_b;
				//托数量
				Integer t = plan.getBoxnum()/t_b;
				if(yu.intValue()!=0){
					t = t+1;
				}
				//总码数
				Integer total  = plan.getBoxnum() +t;
				
				AppPrepareCode apppre = new AppPrepareCode();
				int count = apppre.countNoUsedByOid(plan.getOrganId());
				if(count<total){
					request.setAttribute("result", "该工厂SAP码数量不足，请上传!");
					return mapping.findForward("error");
				}
				
				plan.setApprovalFlag(1);
				plan.setApprovalMan(users.getUserid());
				appProductPlan.updProductPlan(plan);
				
				DBUserLog.addUserLog(request, "编号：" + id);
				request.setAttribute("result", "审批成功");
			}
		} catch (Exception e) {
			logger.error("ApproveProductPlanAction  error:", e);
		}
		return mapping.findForward("success");
	}

}
