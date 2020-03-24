package com.winsafe.drp.action.assistant;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppIdcodeDetail;
import com.winsafe.drp.dao.AppIdcodeReset;
import com.winsafe.drp.dao.AppIdcodeResetDetail;
import com.winsafe.drp.dao.Idcode;
import com.winsafe.drp.dao.IdcodeDetail;
import com.winsafe.drp.dao.IdcodeReset;
import com.winsafe.drp.dao.IdcodeResetDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class AuditIdcodeResetAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		UsersBean users = UserManager.getUser(request);
//		Long userid = users.getUserid();
		try {
			String piid = request.getParameter("PIID");
			AppIdcodeReset api = new AppIdcodeReset();
			AppIdcodeDetail apppii = new AppIdcodeDetail();
			IdcodeReset pi = api.getIdcodeResetById(piid);

			if (pi.getIsaudit() == 1) {
				request.setAttribute("result", "databases.record.audit");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			// 从入库产品详情里查询产品编号及数量
			AppIdcodeResetDetail apid = new AppIdcodeResetDetail();
			List pils = apid.getIdcodeResetDetailByIrid(piid);

			IdcodeResetDetail pid = null;
			for (int i = 0; i < pils.size(); i++) {
				pid = (IdcodeResetDetail) pils.get(i);
				// 判断机身号是否存在
				//Product p = appproduct.getProductByID(pid.getProductid());
				//if (p.getIsidcode() == 1) {
					List idcodelist = apppii.getIdcodeDetailByPidBillid(
							pid.getProductid(), piid);
					if (pid.getQuantity().intValue() > idcodelist.size()) {
						request.setAttribute("result", "databases.record.needidcode");
						return new ActionForward("/sys/lockrecord.jsp");
					}
				//}
			}
			

			// 写入机身号总表
			List idcodelist = apppii.getIdcodeDetailByBillid(piid);
			AppIdcode appidcode = new AppIdcode();
			IdcodeDetail wi = null;
			Idcode ic = null;
			for (int i = 0; i < idcodelist.size(); i++) {
				wi = (IdcodeDetail) idcodelist.get(i);
				ic = new Idcode();
//				ic.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
//						"idcode", 0, "")));
				ic.setIdcode(wi.getIdcode());
				ic.setProductid(wi.getProductid());
				ic.setIsuse(1);
				ic.setProductname(wi.getProductname());
				ic.setBillid(wi.getBillid());
				ic.setIdbilltype(wi.getIdbilltype());
				ic.setMakeorganid(wi.getMakeorganid());
//				ic.setWarehouseid(0l);
				ic.setProvideid("");
				ic.setProvidename("");
				ic.setMakedate(wi.getMakedate());
				appidcode.addIdcode(ic);
			}

//			api.updIsAudit(piid, userid, 1);
			
			request.setAttribute("result", "databases.audit.success");
//			DBUserLog.addUserLog(userid, "复核序号重置,编号：" + piid);// 日志

			return mapping.findForward("audit");
		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
