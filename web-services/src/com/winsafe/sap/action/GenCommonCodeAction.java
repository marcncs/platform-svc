package com.winsafe.sap.action;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.sap.dao.AppCommonCodeLog;
import com.winsafe.sap.metadata.CommonCodeStatus;
import com.winsafe.sap.metadata.SyncStatus;
import com.winsafe.sap.pojo.CommonCodeLog;

public class GenCommonCodeAction extends BaseAction{
	
	private AppCommonCodeLog appCommonCodeLog = new AppCommonCodeLog();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		StringBuffer resultMsg = new StringBuffer();
		
		String printJobId = request.getParameter("printJobId");
		String commonCodeCount = request.getParameter("codeCount");
		String mCode = request.getParameter("mCode");
		if(doValidate(printJobId, commonCodeCount, resultMsg, mCode)) {
			CommonCodeLog commonCodeLog = new CommonCodeLog();
			commonCodeLog.setCount(Integer.parseInt(commonCodeCount));
			commonCodeLog.setMakeDate(DateUtil.getCurrentDate());
			commonCodeLog.setMakeId(userid);
			commonCodeLog.setPrintJobId(Integer.parseInt(printJobId));
			commonCodeLog.setMaterialCode(mCode);
			commonCodeLog.setStatus(CommonCodeStatus.NOT_GENERATED.getDatabaseValue());
			commonCodeLog.setSyncStatus(SyncStatus.NOT_UPLOADED.getValue());
			appCommonCodeLog.addCommonCodeLog(commonCodeLog);
			resultMsg.append("通用码打印任务添加成功，请通过日志查看处理结果");
			DBUserLog.addUserLog(request, "编号：" + commonCodeLog.getId());
		}
		request.setAttribute("result", resultMsg.toString());
		return mapping.findForward("result");
	}
	
	public boolean doValidate(String printJobId, String codeCount, StringBuffer resultMsg, String mCode) {
		if (StringUtil.isEmpty(printJobId)) {
			resultMsg.append("打印任务编号为空");
			return false;
		}

		if (StringUtil.isEmpty(codeCount)) {
			resultMsg.append("生成数量不能为空");
			return false;
		}
		if (StringUtil.isEmpty(codeCount)) {
			resultMsg.append("物料号为空");
			return false;
		}
		try {
			Integer.parseInt(codeCount);
		} catch (Exception e) {
			resultMsg.append("生成数量必须为数字");
			return false;
		}

		return true;
	}
}
