package com.winsafe.drp.action.assistant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOIntegralDeal;
import com.winsafe.drp.dao.AppOIntegralSett;
import com.winsafe.drp.dao.OIntegralDeal;
import com.winsafe.drp.dao.OIntegralSett;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DataValidate;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AddOIntegralSettAction extends Action {


  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    UsersBean users = UserManager.getUser(request);
//    Long userid = users.getUserid();
    

		try {

			String oid = request.getParameter("oid");
			String settintegral = request.getParameter("settintegral");
			String settcash = request.getParameter("settcash");
			
			if (oid.equals("") ) {
				String result = "databases.add.fail";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecord.jsp");
			}

			OIntegralSett so = new OIntegralSett();
			String aciid = MakeCode.getExcIDByRandomTableName("o_integral_sett", 2, "OI");
			so.setId(aciid);
			so.setOid(oid);
			so.setSettintegral(DataValidate.IsDouble(settintegral) ? Double
						.valueOf(settintegral) : 0d);
			so.setSettcash(DataValidate.IsDouble(settcash) ? Double
						.valueOf(settcash) : 0d);			
			so.setIsaudit(0);
			so.setMakeorganid(users.getMakeorganid());
//			so.setMakedeptid(users.getMakedeptid());
//			so.setMakeid(userid);
			so.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
			

			AppOIntegralSett aq = new AppOIntegralSett();
			AppOIntegralDeal appcid = new AppOIntegralDeal();

				
			//新增积分预处理
			OIntegralDeal cid = new OIntegralDeal();
			cid.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
						"o_integral_deal", 0, "")));
			cid.setBillno(aciid);
			cid.setOid(so.getOid());
			cid.setIsort(8);
			cid.setDealintegral(so.getSettintegral());
			cid.setCompleteintegral(0d);
			cid.setIssettlement(0);
			cid.setMakedate(so.getMakedate());
			appcid.addOIntegralDeal(cid);
				

			aq.addOIntegralSett(so);
			
			


			request.setAttribute("result", "databases.add.success");
//			DBUserLog.addUserLog(userid, "新增机构积分结算单");// 日志
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
