package com.winsafe.erp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.erp.dao.AppProductPlan;
import com.winsafe.erp.dao.AppUnitInfo;
import com.winsafe.erp.pojo.UnitInfo;
import com.winsafe.hbm.util.DateUtil;

public class AddUnitInfoAction extends BaseAction {

	private AppUnitInfo appUnitInfo = new AppUnitInfo();
	private static Logger logger = Logger.getLogger(AddUnitInfoAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
 
		initdata(request);
		UsersBean users = UserManager.getUser(request);
		try {
			String organid = request.getParameter("organid");
			String productid = request.getParameter("ProductID");
			String unitCount = request.getParameter("unitCount");
			String labelType = request.getParameter("labelType");
			String needRepackage = request.getParameter("needRepackage");
			String needCovertCode = request.getParameter("needCovertCode");
			String codeSeq = request.getParameter("codeSeq");

			UnitInfo UnitInfo = appUnitInfo.getUnitInfoByOidAndPid(organid, productid);
			if (UnitInfo != null) {
				request.setAttribute("result", "该机构下的该产品已经存在!新增失败!");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			
			if("1".equals(needCovertCode)) {
				if(StringUtil.isEmpty(codeSeq) || codeSeq.length() != 3) {
					request.setAttribute("result", "当前暗码前缀不是3位!新增失败!");
					return new ActionForward("/sys/lockrecordclose2.jsp");
				}
				
				if(appUnitInfo.isProCodeAlreadyExists(codeSeq)) { 
					request.setAttribute("result", "前三位产品码已存在!新增失败!");
					return new ActionForward("/sys/lockrecordclose2.jsp");
				}
			}
			
			UnitInfo = new UnitInfo();
			UnitInfo.setOrganId(organid);
			UnitInfo.setProductId(productid);
			UnitInfo.setUnitCount(Integer.valueOf(unitCount));
			UnitInfo.setUnitId(17);
			UnitInfo.setIsactive(1);
			UnitInfo.setLabelType(labelType);
			UnitInfo.setModifiedDate(DateUtil.getCurrentDate());
			UnitInfo.setModifiedUserID(users.getUserid());
			UnitInfo.setNeedRepackage(Integer.parseInt(needRepackage));
			UnitInfo.setNeedCovertCode(Integer.parseInt(needCovertCode));
			
			AppProductPlan appProductPlan = new AppProductPlan();
			String maxCodeTo = appProductPlan.getMaxCovertCodeByPrefix(codeSeq);
			if(maxCodeTo == null || maxCodeTo.length() != 11) {
				codeSeq = codeSeq + Constants.ZERO_PREFIX[10-codeSeq.toString().length()]+"1";
			} else {
				Long maxCodeToLong = Long.valueOf(maxCodeTo) + 1;
				codeSeq = Constants.ZERO_PREFIX[11-maxCodeToLong.toString().length()]+maxCodeToLong;
			}
			
			UnitInfo.setCodeSeq(codeSeq); 
			appUnitInfo.AddUnitInfo(UnitInfo);
			request.setAttribute("result", "新增成功");
			DBUserLog.addUserLog(request, "编号：" + UnitInfo.getId());
		} catch (Exception e) {
			logger.error("", e);
		}
		return mapping.findForward("success");
	}

}
