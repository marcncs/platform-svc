package com.winsafe.erp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.erp.dao.AppProductPlan;
import com.winsafe.erp.dao.AppUnitInfo;
import com.winsafe.erp.pojo.UnitInfo;
 
public class UpdUnitInfoAction extends BaseAction {

	private AppUnitInfo appUnitInfo = new AppUnitInfo();
	private static Logger logger = Logger.getLogger(UpdUnitInfoAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		initdata(request);

		try {
			Integer id = Integer.valueOf(request.getParameter("id"));
			
			String organid = request.getParameter("organid");
			String productid = request.getParameter("ProductID");
			String unitCount = request.getParameter("unitCount");
			String labelType = request.getParameter("labelType");
			String needRepackage = request.getParameter("needRepackage");
			String needCovertCode = request.getParameter("needCovertCode");
			String codeSeq = request.getParameter("codeSeq");

			UnitInfo UnitInfo = appUnitInfo.getUnitInfoByOidAndPid(organid, productid);

			if (UnitInfo != null && UnitInfo.getId() != id) {
				request.setAttribute("result", "该机构下的该产品托盘信息已经存在!修改失败!");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			} 
			if("1".equals(needCovertCode)) {
				if(codeSeq.length() != 3) {
					request.setAttribute("result", "暗码前缀不是3位!修改失败!");
					return new ActionForward("/sys/lockrecordclose2.jsp");
				}
				
				if((!StringUtil.isEmpty(UnitInfo.getCodeSeq()) 
						&& !codeSeq.equals(UnitInfo.getCodeSeq().substring(0, 3))) 
						|| StringUtil.isEmpty(UnitInfo.getCodeSeq())) {
					if(appUnitInfo.isProCodeAlreadyExists(codeSeq)) { 
						request.setAttribute("result", "前三位暗码前缀码已存在!修改失败!");
						return new ActionForward("/sys/lockrecordclose2.jsp");
					}
				}
				
//				if(!StringUtil.isEmpty(codeSeq) 
//						&& !StringUtil.isEmpty(UnitInfo.getCodeSeq()) 
//						&& codeSeq.substring(0, 3).equals(UnitInfo.getCodeSeq().substring(0, 3))
//						&& codeSeq.substring(3).compareTo(UnitInfo.getCodeSeq().substring(3)) < 0) {
//					request.setAttribute("result", "暗码当前初始序号设置小于旧值！修改失败！");
//					return new ActionForward("/sys/lockrecordclose2.jsp");
//				} 
			}
			
			UnitInfo = appUnitInfo.getUnitInfoByID(id);
			UnitInfo.setOrganId(organid);
			UnitInfo.setProductId(productid);
			UnitInfo.setUnitCount(Integer.valueOf(unitCount));
			UnitInfo.setModifiedDate(Dateutil.getCurrentDate());
			UnitInfo.setLabelType(labelType);
			UnitInfo.setModifiedUserID(userid);
			UnitInfo.setNeedRepackage(Integer.valueOf(needRepackage));
			UnitInfo.setNeedCovertCode(Integer.parseInt(needCovertCode));
			if("1".equals(needCovertCode)){
				if((!StringUtil.isEmpty(UnitInfo.getCodeSeq()) 
						&& !codeSeq.equals(UnitInfo.getCodeSeq().substring(0, 3))) 
						|| StringUtil.isEmpty(UnitInfo.getCodeSeq())
						|| (UnitInfo.getCodeSeq() !=null && UnitInfo.getCodeSeq().length() != 11)) {
					AppProductPlan appProductPlan = new AppProductPlan();
					String maxCodeTo = appProductPlan.getMaxCovertCodeByPrefix(codeSeq);
					if(maxCodeTo == null || maxCodeTo.length() != 11) {
						codeSeq = codeSeq + Constants.ZERO_PREFIX[10-codeSeq.toString().length()]+"1";
					} else {
						Long maxCodeToLong = Long.valueOf(maxCodeTo) + 1;
						codeSeq = Constants.ZERO_PREFIX[11-maxCodeToLong.toString().length()]+maxCodeToLong;
					}
					UnitInfo.setCodeSeq(codeSeq);
				}
			} 
			appUnitInfo.updUnitInfo(UnitInfo);
			
			DBUserLog.addUserLog(request, "编号：" + id);
			request.setAttribute("result", "修改成功");
		} catch (Exception e) {
			logger.error("UpdUnitInfoAction  error:", e);
		}
		return mapping.findForward("success");
	}
	
	public static void main(String[] args) {
		System.out.println("1234567890".substring(3));
	}

}