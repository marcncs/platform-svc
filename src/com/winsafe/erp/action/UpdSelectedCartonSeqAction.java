package com.winsafe.erp.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;  

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
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.sap.pojo.CartonSeq;
 
public class UpdSelectedCartonSeqAction extends BaseAction {

	private AppProductPlan appProductPlan = new AppProductPlan();
	private static Logger logger = Logger.getLogger(UpdSelectedCartonSeqAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		initdata(request);
		CartonSeqServices css = new CartonSeqServices();
		try {
			Integer id = Integer.valueOf(request.getParameter("ID"));
			
			Set<String> newSeqSet = new HashSet<String>();
			//{旧序号,新序号,箱码}
			List<String[]> newSeqListToUpdate = new ArrayList<String[]>();
			String[] oldSeqs = request.getParameterValues("oldCode");
			String[] newSeqs = request.getParameterValues("newCode");
			String[] cartonCodes = request.getParameterValues("cartonCode");
			
			ProductPlan productPlan = appProductPlan.getProductPlanByID(id);
			
			if (productPlan != null && productPlan.getCloseFlag() != null && productPlan.getCloseFlag() == 1) {
				request.setAttribute("result", "该计划已结束!无法修改!");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			
			StringBuffer seqs = new StringBuffer();
			StringBuffer ccodes = new StringBuffer();
			for(int i = 0; i<newSeqs.length; i++) {
				if(oldSeqs[i].equals(newSeqs[i])) {
					continue;
				}
				if(StringUtil.isEmpty(newSeqs[i])) {
					request.setAttribute("result", "修改失败,新序号不能为空");
					return new ActionForward("/sys/lockrecordclose2.jsp");
				}
				String newSeqStr = CartonSeq.getSeqWithPrefix(newSeqs[i]);
				if(!newSeqSet.add(newSeqStr)) {
					request.setAttribute("result", "修改失败,箱码顺序号"+newSeqs[i]+"重复");
					return new ActionForward("/sys/lockrecordclose2.jsp");
				}
				seqs.append(",'"+newSeqStr+"'");
				ccodes.append(",'"+cartonCodes[i]+"'");
				newSeqListToUpdate.add(new String[]{newSeqStr, cartonCodes[i]});
			}
			
			if(seqs.length() > 0) {
				Set<String> notAvailableSeq = css.getNotAvailableSeq(productPlan.getProductId(), newSeqSet,seqs.substring(1),ccodes.substring(1));
				if(notAvailableSeq.size() >0) {
					StringBuffer errMsg = new StringBuffer();
					for(String seq : notAvailableSeq) {
						errMsg.append(","+Long.valueOf(seq));
					}
					request.setAttribute("result", "修改失败,以下序号不可用：<br/>"+errMsg.substring(1));
					return new ActionForward("/sys/lockrecordclose2.jsp");
				}
				
				//更新暗码信息
				int result = css.updateSelectedSeq(productPlan.getProductId(), newSeqListToUpdate, ccodes.substring(1));
				//更新数量与实际数量不符
				if(newSeqListToUpdate.size() != result) {
					HibernateUtil.rollbackTransaction();
					request.setAttribute("result", "修改失败,箱码序号状态可能已发生改变,请重试!");
					return new ActionForward("/sys/lockrecordclose2.jsp");
				}
			}
			
			DBUserLog.addUserLog(request, "编号：" + id);
			request.setAttribute("result", "修改成功");
		} catch (Exception e) {
			logger.error("UpdProductPlanAction  error:", e);
		}
		return mapping.findForward("success");
	}

}