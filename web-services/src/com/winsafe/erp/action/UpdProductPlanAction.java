package com.winsafe.erp.action;

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
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.StringUtil;
 
public class UpdProductPlanAction extends BaseAction {

	private AppProductPlan appProductPlan = new AppProductPlan();
	private static Logger logger = Logger.getLogger(UpdProductPlanAction.class);
	private AppUnitInfo appu = new AppUnitInfo();

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		initdata(request);

		try {
			Integer id = Integer.valueOf(request.getParameter("id"));
			
			String organid = request.getParameter("organid");
			String productid = request.getParameter("ProductID");
			String PONO = request.getParameter("PONO");
			String pbatch = request.getParameter("pbatch");
			String mbatch = request.getParameter("mbatch");
			String proDate = request.getParameter("proDate");
			String packDate = request.getParameter("packDate");
			String boxnum = request.getParameter("boxnum");
			String copys = request.getParameter("copys");
			String codeFrom = request.getParameter("codeFrom");
			String codeTo = "";
			
			ProductPlan productPlan = appProductPlan.getProductPlanByID(id);
			if (productPlan != null &&productPlan.getApprovalFlag()==1) {
				request.setAttribute("result", "该计划已审批！无法修改！");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			
			UnitInfo unitinfo = appu.getUnitInfoByOidAndPid(organid, productid);
			if(unitinfo.getNeedCovertCode() != null && unitinfo.getNeedCovertCode() == 1) {
//				if(!StringUtil.isEmpty(unitinfo.getCodeSeq()) && unitinfo.getCodeSeq().compareTo(codeFrom) > 0) {
//					request.setAttribute("result", "修改失败,暗码初始值 小于当前初始值");
//					return new ActionForward("/sys/lockrecordclose2.jsp");
//				}
				
//				if(!codeFrom.substring(0, 3).equals(codeTo.substring(0, 3))) {
//					request.setAttribute("result", "修改失败,起始码与截至码前三位不一致");
//					return new ActionForward("/sys/lockrecordclose2.jsp");
//				}
				
				if(codeFrom.length() != 11) {
					request.setAttribute("result", "修改失败,暗码长度不是11位");
					return new ActionForward("/sys/lockrecordclose2.jsp");
				}
				
				Long codeToLong = Long.valueOf(codeFrom) + Long.valueOf(boxnum) - 1;
				codeTo = Constants.ZERO_PREFIX[11-codeToLong.toString().length()]+codeToLong;
				
//				if((codeToLong - codeFromLong + 1) != Long.valueOf(boxnum)) {
//					request.setAttribute("result", "修改失败,暗码数量不等于箱码数量");
//					return new ActionForward("/sys/lockrecordclose2.jsp");
//				}
				
				if(!StringUtil.isEmpty(unitinfo.getCodeSeq()) && !unitinfo.getCodeSeq().substring(0, 3).equals(codeFrom.substring(0, 3))) {
					request.setAttribute("result", "修改失败,暗码前三位产品代码与托盘信息中设置的不一致");
					return new ActionForward("/sys/lockrecordclose2.jsp");
				} 
				
				if(appProductPlan.isCodeExists(codeFrom, codeTo , productPlan.getId())) {
					request.setAttribute("result", "修改失败,该范围内部分暗码已被使用!");
					return new ActionForward("/sys/lockrecordclose2.jsp");
				}
				
				if(StringUtil.isEmpty(unitinfo.getCodeSeq())){
					if(appu.isProCodeAlreadyExists(codeFrom.substring(0, 3))) { 
						request.setAttribute("result", "修改失败,前三位产品码已被其他产品使用过!");
						return new ActionForward("/sys/lockrecordclose2.jsp");
					} else {
						unitinfo.setCodeSeq(codeFrom.substring(0, 3));
						appu.updUnitInfo(unitinfo);
					}
				}
				
				//更新托盘信息
				String codeSeq = String.valueOf(codeToLong+1);
				unitinfo.setCodeSeq(Constants.ZERO_PREFIX[11-codeSeq.length()]+codeSeq);
				appu.updUnitInfo(unitinfo);
			}
//			ProductPlan productPlan = appProductPlan.getProductPlanByOidAndPid(organid, productid);
//
//			if (productPlan != null && productPlan.getId() != id) {
//				request.setAttribute("result", "该机构下的该产品托盘信息已经存在！修改失败！");
//				return new ActionForward("/sys/lockrecordclose2.jsp");
//			}
			
			productPlan.setOrganId(organid);
			productPlan.setProductId(productid);
			productPlan.setPONO(PONO);
			productPlan.setMbatch(mbatch);
			productPlan.setPbatch(pbatch);
			productPlan.setProDate(DateUtil.StringToDate(proDate));
			productPlan.setPackDate(DateUtil.StringToDate(packDate));
			productPlan.setBoxnum(Integer.valueOf(boxnum));
			productPlan.setCopys(Integer.valueOf(copys));
			if(!StringUtil.isEmpty(codeFrom)) {
				productPlan.setCodeFrom(codeFrom);
			} else {
				productPlan.setCodeFrom(null);
			}
			if(!StringUtil.isEmpty(codeTo)) {
				productPlan.setCodeTo(codeTo);
			} else {
				productPlan.setCodeTo(null);
			}
			
			appProductPlan.updProductPlan(productPlan);
			
			DBUserLog.addUserLog(request, "编号：" + id);
			request.setAttribute("result", "修改成功");
		} catch (Exception e) {
			logger.error("UpdProductPlanAction  error:", e);
		}
		return mapping.findForward("success");
	}

}