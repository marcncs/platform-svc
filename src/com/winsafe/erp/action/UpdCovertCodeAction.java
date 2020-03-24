package com.winsafe.erp.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;  

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.erp.dao.AppProductPlan;
import com.winsafe.erp.dao.AppUnitInfo;
import com.winsafe.erp.pojo.ProductPlan;
import com.winsafe.erp.pojo.UnitInfo;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.sap.dao.AppPrimaryCode;
 
public class UpdCovertCodeAction extends BaseAction {

	private AppProductPlan appProductPlan = new AppProductPlan();
	private AppUnitInfo appUnitInfo = new AppUnitInfo();
	private static Logger logger = Logger.getLogger(UpdCovertCodeAction.class);
	private AppPrimaryCode appPrimaryCode = new AppPrimaryCode();

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		initdata(request);

		try {
			Integer id = Integer.valueOf(request.getParameter("ID"));
			
			Set<String> codeSet = new HashSet<String>();
			String[] oldCodes = request.getParameterValues("oldCode");
			String[] newCodes = request.getParameterValues("newCode");
			String[] cartonCodes = request.getParameterValues("cartonCode");
			
			ProductPlan productPlan = appProductPlan.getProductPlanByID(id);
			UnitInfo unitInfo = appUnitInfo.getUnitInfoByOidAndPid(productPlan.getOrganId(),productPlan.getProductId());
			
			if (productPlan != null && productPlan.getCloseFlag() != null && productPlan.getCloseFlag() == 1) {
				request.setAttribute("result", "该计划已结束!无法修改！");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			
			String mincc = null;
			String maxcc = null;
			StringBuffer codes = new StringBuffer();
			StringBuffer ccodes = new StringBuffer();
			for(int i = 0; i<newCodes.length; i++) {
				if(oldCodes[i].equals(newCodes[i])) {
					continue;
				}
				if(StringUtil.isEmpty(newCodes[i])) {
					request.setAttribute("result", "修改失败,新暗码不能为空");
					return new ActionForward("/sys/lockrecordclose2.jsp");
				}
				if(!codeSet.add(newCodes[i])) {
					request.setAttribute("result", "修改失败,暗码"+newCodes[i]+"重复");
					return new ActionForward("/sys/lockrecordclose2.jsp");
				}
				codes.append(",'"+newCodes[i]+"'");
				ccodes.append(",'"+cartonCodes[i]+"'");
				if(newCodes[i].length() != 11) {
					request.setAttribute("result", "修改失败,暗码"+newCodes[i]+"长度不是11位");
					return new ActionForward("/sys/lockrecordclose2.jsp");
				}
				if(!StringUtil.isEmpty(unitInfo.getCodeSeq()) && !unitInfo.getCodeSeq().substring(0, 3).equals(newCodes[i].substring(0, 3))) {
					request.setAttribute("result", "修改失败,暗码前三位产品代码与托盘信息中设置的不一致");
					return new ActionForward("/sys/lockrecordclose2.jsp");
				} 
				if(StringUtil.isEmpty(mincc) && StringUtil.isEmpty(maxcc)) {
					mincc = newCodes[i];
					maxcc = newCodes[i];
					continue;
				}
				if(newCodes[i].compareTo(mincc) < 0) {
					mincc = newCodes[i];
				}
				if(newCodes[i].compareTo(maxcc) > 0) {
					maxcc = newCodes[i];
				}
			}
			
			if(codes.length() > 0) {
				if(appProductPlan.isCodeExists(mincc, maxcc, productPlan.getId()) || appProductPlan.isCodeExistsInPirmayCode(codes.substring(1),ccodes.substring(1))) {
					request.setAttribute("result", "修改失败,修改的暗码已被使用过!请使用"+unitInfo.getCodeSeq()+"以及之后的暗码");
					return new ActionForward("/sys/lockrecordclose2.jsp");
				}
				
				//更新暗码信息
				List<String> sqls = new ArrayList<String>();
				for(int i = 0; i < newCodes.length; i++ ) {
					if(!oldCodes[i].equals(newCodes[i])) {
						sqls.add("update PRIMARY_CODE set covert_code = '"+newCodes[i]+"' where PRIMARY_CODE = '"+cartonCodes[i]+"'");
					}
				}
				appPrimaryCode.updPrimaryCodes(sqls);
				
				//更新托盘信息
				if(maxcc.compareTo(unitInfo.getCodeSeq()) >= 0) {
					Long codeSeq = Long.valueOf(maxcc) + 1;
					unitInfo.setCodeSeq(Constants.ZERO_PREFIX[11-codeSeq.toString().length()]+codeSeq);
					appUnitInfo.updUnitInfo(unitInfo);
				}
				
				//更新生产计划序号
				Map<String,String> maxMinCovertCode = appProductPlan.getMaxMinCovertCodeByProductPlaId(productPlan.getId());
				if(maxMinCovertCode != null) {
					String minCode = maxMinCovertCode.get("min");
					String maxCode = maxMinCovertCode.get("max");
					if(!minCode.equals(productPlan.getCodeFrom()) || !maxCode.equals(productPlan.getCodeTo())) {
						productPlan.setCodeFrom(minCode);
						productPlan.setCodeTo(maxCode);
						appProductPlan.updProductPlan(productPlan);
					}
				}
			}
			
			/*for(String covertCode : covertCodes) {
				if(StringUtil.isEmpty(covertCode)) {
					continue;
				} else {
					if(!codeSet.add(covertCode)) {
						request.setAttribute("result", "修改失败,暗码"+covertCode+"重复");
						return new ActionForward("/sys/lockrecordclose2.jsp");
					}
					codes.append(",'"+covertCode+"'");
				}
				if(covertCode.length() != 11) {
					request.setAttribute("result", "修改失败,暗码"+covertCode+"长度不是11位");
					return new ActionForward("/sys/lockrecordclose2.jsp");
				}
				if(!StringUtil.isEmpty(unitInfo.getCodeSeq()) && !unitInfo.getCodeSeq().substring(0, 3).equals(covertCode.substring(0, 3))) {
					request.setAttribute("result", "修改失败,暗码前三位产品代码与托盘信息中设置的不一致");
					return new ActionForward("/sys/lockrecordclose2.jsp");
				} 
				if(StringUtil.isEmpty(mincc) && StringUtil.isEmpty(maxcc)) {
					mincc = covertCode;
					maxcc = covertCode;
					continue;
				}
				if(covertCode.compareTo(mincc) < 0) {
					mincc = covertCode;
				}
				if(covertCode.compareTo(maxcc) > 0) {
					maxcc = covertCode;
				}
			}
			
			
			if(appProductPlan.isCodeExists(mincc, maxcc , null) || appProductPlan.isCodeExistsInPirmayCode(codes.substring(1))) {
				request.setAttribute("result", "修改失败,修改的暗码已被使用过!请使用"+unitInfo.getCodeSeq()+"之后的暗码");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			
			//更新暗码信息
			List<String> sqls = new ArrayList<String>();
			for(int i = 0; i < covertCodes.length; i++ ) {
				if(!StringUtil.isEmpty(covertCodes[i])) {
					sqls.add("update PRIMARY_CODE set covert_code = '"+covertCodes[i]+"' where PRIMARY_CODE = '"+cartonCodes[i]+"'");
				}
			}
			appPrimaryCode.updPrimaryCodes(sqls);
			
			
			//更新托盘信息
			Long codeSeq = Long.valueOf(maxcc) + 1;
			unitInfo.setCodeSeq(Constants.ZERO_PREFIX[11-codeSeq.toString().length()]+codeSeq);
			appUnitInfo.updUnitInfo(unitInfo);
			*/
			DBUserLog.addUserLog(request, "编号：" + id);
			request.setAttribute("result", "修改成功");
		} catch (Exception e) {
			logger.error("UpdProductPlanAction  error:", e);
		}
		return mapping.findForward("success");
	}

}