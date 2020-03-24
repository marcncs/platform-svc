package com.winsafe.drp.action.assistant;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AdjustOIntegral;
import com.winsafe.drp.dao.AppAdjustOIntegral;
import com.winsafe.drp.dao.AppOIntegral;
import com.winsafe.drp.dao.AppOIntegralDeal;
import com.winsafe.drp.dao.OIntegral;
import com.winsafe.drp.dao.OIntegralDeal;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DateUtil;

public class CancalAuditAdjustOIntegralAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
//		Long userid = users.getUserid();
		try {
			String id = request.getParameter("id");
			AppAdjustOIntegral apb = new AppAdjustOIntegral();
			AdjustOIntegral pb = new AdjustOIntegral();
			pb = apb.getAdjustOIntegralByID(id);

			if (pb.getIsaudit() == 0) {
				String result = "databases.record.audit";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

//			apb.updIsAudit(id, userid, 0);
			
			
			AppOIntegralDeal acid = new AppOIntegralDeal();
			AppOIntegral aci = new AppOIntegral();
			List cidls = acid.getOIntegralDealByBillno(id);
			for(int cd=0;cd<cidls.size();cd++){
				OIntegral ci = new OIntegral();
				OIntegralDeal o=(OIntegralDeal)cidls.get(cd);
				ci.setPowerorganid("1");
				ci.setEquiporganid(o.getOid());
				ci.setIsort(o.getIsort());
				ci.setOintegral(-(o.getDealintegral()));
				ci.setIyear(DateUtil.getCurrentYear());
				aci.updOIntegralByIntegral(ci);
				
				
				acid.updOIntegralDealByID(o.getId(), ci.getOintegral());
			}

			request.setAttribute("result", "databases.audit.success");
//			DBUserLog.addUserLog(userid, "取消复核机构积分调整单,编号：" + id);// 日志
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
