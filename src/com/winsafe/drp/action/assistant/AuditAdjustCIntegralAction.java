package com.winsafe.drp.action.assistant;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AdjustCIntegral;
import com.winsafe.drp.dao.AppAdjustCIntegral;
import com.winsafe.drp.dao.AppCIntegral;
import com.winsafe.drp.dao.AppCIntegralDeal;
import com.winsafe.drp.dao.AppOIntegral;
import com.winsafe.drp.dao.AppOIntegralDeal;
import com.winsafe.drp.dao.CIntegral;
import com.winsafe.drp.dao.CIntegralDeal;
import com.winsafe.drp.dao.OIntegral;
import com.winsafe.drp.dao.OIntegralDeal;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DateUtil;

public class AuditAdjustCIntegralAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
//		Long userid = users.getUserid();
		try {
			String id = request.getParameter("id");
			AppAdjustCIntegral apb = new AppAdjustCIntegral();
			AdjustCIntegral pb = apb.getAdjustCIntegralByID(id);

			if (pb.getIsaudit() == 1) {
				String result = "databases.record.audit";
				request.setAttribute("result", "databases.audit.success");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

//			apb.updIsAudit(id, userid, 1);
			
			//加会员积分
			AppCIntegralDeal acid = new AppCIntegralDeal();
			List cidls = acid.getCIntegralDealByBillno(id);
			AppCIntegral aci = new AppCIntegral();
			for(int cd=0;cd<cidls.size();cd++){
				CIntegral ci = new CIntegral();
				CIntegralDeal o=(CIntegralDeal)cidls.get(cd);
				ci.setOrganid(o.getOrganid());
				ci.setCid(o.getCid());
				ci.setIsort(o.getIsort());
				ci.setCintegral(o.getDealintegral());
				ci.setIyear(DateUtil.getCurrentYear());
				aci.addCIntegralIsNoExist(ci);
				aci.updCIntegralByIntegral(ci);

				//修改会员积分预处理
				acid.updCIntegralDealByID(o.getId(), ci.getCintegral());
			}
			//处理机构积分
			AppOIntegralDeal aoid = new AppOIntegralDeal();
			List oidls = aoid.getOIntegralDealByBillno(id);
			AppOIntegral aoi = new AppOIntegral();
			for(int cd=0;cd<oidls.size();cd++){
				OIntegral ci = new OIntegral();
				OIntegralDeal o=(OIntegralDeal)oidls.get(cd);
				ci.setPowerorganid("1");
				ci.setEquiporganid(o.getOid());
				ci.setIsort(o.getIsort());
				ci.setOintegral(o.getDealintegral());
				ci.setIyear(DateUtil.getCurrentYear());
				aoi.addOIntegralIsNoExist(ci);
				aoi.updOIntegralByIntegral(ci);

				//修改机构积分预处理
				aoid.updOIntegralDealByID(o.getId(), ci.getOintegral());
			}

			request.setAttribute("result", "databases.audit.success");
//			DBUserLog.addUserLog(userid, "复核会员积分调整单,编号：" + id);// 日志
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
