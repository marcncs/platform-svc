package com.winsafe.drp.action.assistant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AdjustOIntegral;
import com.winsafe.drp.dao.AdjustOIntegralDetail;
import com.winsafe.drp.dao.AppAdjustOIntegral;
import com.winsafe.drp.dao.AppAdjustOIntegralDetail;
import com.winsafe.drp.dao.AppOIntegralDeal;
import com.winsafe.drp.dao.OIntegralDeal;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DataValidate;
import com.winsafe.hbm.util.MakeCode;

public class UpdAdjustOIntegralAction extends Action {


  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    UsersBean users = UserManager.getUser(request);
//    Long userid = users.getUserid();
    

		try {
			String id = request.getParameter("id");
			String remark = request.getParameter("remark");
			String[] oids = request.getParameterValues("oid");
			String[] onames = request.getParameterValues("oname");
			String[] adjustintegrals = request.getParameterValues("adjustintegral");
			if (oids == null ) {
				String result = "databases.add.fail";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecord.jsp");
			}

			AppAdjustOIntegral aq = new AppAdjustOIntegral();
			AdjustOIntegral so = aq.getAdjustOIntegralByID(id);
			so.setRemark(remark);
			
			StringBuffer keyscontent = new StringBuffer();
		    keyscontent.append(so.getId()).append(",");

			
			AppAdjustOIntegralDetail asld = new AppAdjustOIntegralDetail();
			AppOIntegralDeal appcid = new AppOIntegralDeal();
			asld.delDetailByAocid(id);
			appcid.delOIntegralDeal(id);
			for (int i = 0; i < oids.length; i++) {
				AdjustOIntegralDetail sod = new AdjustOIntegralDetail();
				sod.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
						"adjust_c_integral_detail", 0, "")));
				sod.setAoiid(id);
				sod.setOid(oids[i]);
				sod.setOname(onames[i]);
				sod.setAdjustintegral(DataValidate.IsDouble(adjustintegrals[i]) ? Double
						.valueOf(adjustintegrals[i]) : 0d);
				asld.addAdjustOIntegralDetail(sod);
				
				//新增积分预处理
				OIntegralDeal cid = new OIntegralDeal();
				cid.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
							"o_integral_deal", 0, "")));
				cid.setBillno(id);
				cid.setOid(sod.getOid());
				cid.setIsort(8);
				cid.setDealintegral(sod.getAdjustintegral());
				cid.setCompleteintegral(0d);
				cid.setIssettlement(0);
				cid.setMakedate(so.getMakedate());
				appcid.addOIntegralDeal(cid);
				
				keyscontent.append(sod.getOid()).append(",").append(sod.getOname())
				.append(",");
			}
			so.setKeyscontent(keyscontent.toString());
			aq.updAdjustOIntegral(so);


			request.setAttribute("result", "databases.upd.success");
//			DBUserLog.addUserLog(userid, "修改机构积分调整单");// 日志
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
