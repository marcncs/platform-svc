package com.winsafe.drp.action.assistant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AdjustCIntegral;
import com.winsafe.drp.dao.AdjustCIntegralDetail;
import com.winsafe.drp.dao.AppAdjustCIntegral;
import com.winsafe.drp.dao.AppAdjustCIntegralDetail;
import com.winsafe.drp.dao.AppCIntegralDeal;
import com.winsafe.drp.dao.AppOIntegralDeal;
import com.winsafe.drp.dao.CIntegralDeal;
import com.winsafe.drp.dao.OIntegralDeal;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DataValidate;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AddAdjustCIntegralAction extends Action {


  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    UsersBean users = UserManager.getUser(request);
//    Long userid = users.getUserid();
    

		try {

			String remark = request.getParameter("remark");
			String[] cids = request.getParameterValues("cid");
			String[] cnames = request.getParameterValues("cname");
			String[] cmobiles = request.getParameterValues("cmobile");
			String[] adjustintegrals = request.getParameterValues("adjustintegral");
			String[] oids = request.getParameterValues("oid");
			if (cids == null ) {
				String result = "databases.add.fail";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecord.jsp");
			}

			AdjustCIntegral so = new AdjustCIntegral();
			String aciid = MakeCode.getExcIDByRandomTableName("adjust_c_integral", 2, "AC");
			so.setId(aciid);		
			so.setRemark(remark);
			so.setIsaudit(0);
			so.setMakeorganid(users.getMakeorganid());
//			so.setMakedeptid(users.getMakedeptid());
//			so.setMakeid(userid);
			so.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
			
			StringBuffer keyscontent = new StringBuffer();
		    keyscontent.append(so.getId()).append(",");

			AppAdjustCIntegral aq = new AppAdjustCIntegral();
			AppAdjustCIntegralDetail asld = new AppAdjustCIntegralDetail();
			AppCIntegralDeal appcid = new AppCIntegralDeal();
			AppOIntegralDeal appoid = new AppOIntegralDeal();
			for (int i = 0; i < cids.length; i++) {
				AdjustCIntegralDetail sod = new AdjustCIntegralDetail();
				sod.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
						"adjust_c_integral_detail", 0, "")));
				sod.setAciid(aciid);
				sod.setCid(cids[i]);
				sod.setCname(cnames[i]);
				sod.setCmobile(cmobiles[i]);
				sod.setAdjustintegral(DataValidate.IsDouble(adjustintegrals[i]) ? Double
						.valueOf(adjustintegrals[i]) : 0d);
				sod.setOid(oids[i]);
				asld.addAdjustCIntegralDetail(sod);
				
				//新增积分预处理
				CIntegralDeal cid = new CIntegralDeal();
				cid.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
							"c_integral_deal", 0, "")));
				cid.setBillno(aciid);
				cid.setOrganid(so.getMakeorganid());
				cid.setCid(sod.getCid());
				cid.setIsort(8);
				cid.setDealintegral(sod.getAdjustintegral());
				cid.setCompleteintegral(0d);
				cid.setIssettlement(0);
				cid.setMakedate(so.getMakedate());
				appcid.addCIntegralDeal(cid);
				
				//新增机构积分预处理
				OIntegralDeal oid = new OIntegralDeal();
				oid.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
							"o_integral_deal", 0, "")));
				oid.setBillno(aciid);
				oid.setOid(sod.getOid());
				oid.setIsort(8);
				oid.setDealintegral(-sod.getAdjustintegral());
				oid.setCompleteintegral(0d);
				oid.setIssettlement(0);
				oid.setMakedate(so.getMakedate());
				appoid.addOIntegralDeal(oid);
				
				keyscontent.append(sod.getCid()).append(",").append(sod.getCname())
				.append(",").append(sod.getCmobile()).append(",");
			}
			so.setKeyscontent(keyscontent.toString());
			aq.addAdjustCIntegral(so);
			
			


			request.setAttribute("result", "databases.add.success");
//			DBUserLog.addUserLog(userid, "新增会员积分调整单");// 日志
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
