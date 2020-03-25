package com.winsafe.drp.action.assistant;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOIntegral;
import com.winsafe.drp.dao.AppOIntegralDeal;
import com.winsafe.drp.dao.AppOIntegralSett;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppReceivable;
import com.winsafe.drp.dao.AppReceivableObject;
import com.winsafe.drp.dao.OIntegral;
import com.winsafe.drp.dao.OIntegralDeal;
import com.winsafe.drp.dao.OIntegralSett;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Receivable;
import com.winsafe.drp.dao.ReceivableObject;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AuditOIntegralSettAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
//		Long userid = users.getUserid();
		try {
			String id = request.getParameter("id");
			AppOIntegralSett apb = new AppOIntegralSett();			
			OIntegralSett pb = apb.getOIntegralSettByID(id);
			AppOIntegral aci = new AppOIntegral();

			if (pb.getIsaudit() == 1) {
				request.setAttribute("result", "databases.record.audit");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			//当前机构积分
			double  ointegral = aci.getOIntegralByOID(pb.getOid());
			//结算积分不能大于当前机构积分
			if ( ointegral >= 0 ){
				if ( pb.getSettintegral() < 0 ){
					if ( -pb.getSettintegral() > ointegral ){
						request.setAttribute("result", "datebases.record.overintegral");
						return new ActionForward("/sys/lockrecordclose.jsp");
					}
				}
			}else {
				if ( pb.getSettintegral() > 0 ){
					if ( pb.getSettintegral() > -ointegral ){
						request.setAttribute("result", "datebases.record.overintegral");
						return new ActionForward("/sys/lockrecordclose.jsp");
					}
				}
			}

//			apb.updIsAudit(id, userid, 1);
			
			//加机构积分
			AppOIntegralDeal acid = new AppOIntegralDeal();
			List cidls = acid.getOIntegralDealByBillno(id);
			
			for(int cd=0;cd<cidls.size();cd++){
				OIntegral ci = new OIntegral();
				OIntegralDeal o=(OIntegralDeal)cidls.get(cd);
				ci.setPowerorganid("1");
				ci.setEquiporganid(o.getOid());
				ci.setIsort(o.getIsort());
				ci.setOintegral(o.getDealintegral());
				ci.setIyear(DateUtil.getCurrentYear());
				aci.addOIntegralIsNoExist(ci);
				aci.updOIntegralByIntegral(ci);

				//修改机构积分预处理
				acid.updOIntegralDealByID(o.getId(), ci.getOintegral());
			}
			
			//---------------------生成应收款-----------------------
			AppOrgan ao = new AppOrgan();
			Organ organ = ao.getOrganByID(pb.getOid());
			// 生成应收款对象
			AppReceivableObject appro = new AppReceivableObject();
			ReceivableObject ro = new ReceivableObject();
			ro.setOid(pb.getOid());
			ro.setObjectsort(0);
			ro.setPayer(organ.getOrganname());
			ro.setMakeorganid(users.getMakeorganid());
			ro.setMakedeptid(users.getMakedeptid());
//			ro.setMakeid(userid);
			ro.setMakedate(DateUtil.StringToDatetime(DateUtil
					.getCurrentDateTime()));
			ro.setKeyscontent(ro.getOid()+","+ro.getPayer()+",");
			appro.addReceivableObjectIsNoExist(ro);
			
			double receivablesum = 0.00;
			if ( pb.getSettintegral() >= 0 ){
				//充积分收款为正数
				receivablesum = pb.getSettcash();
			}else{
				//减积分收款为负数
				receivablesum = -pb.getSettcash();
			}

			// 生成应收款
			AppReceivable ar = new AppReceivable();
			Receivable r = new Receivable();
			r.setId(MakeCode.getExcIDByRandomTableName("receivable",2, ""));
			r.setRoid(pb.getOid());
			r.setReceivablesum(receivablesum);
			r.setPaymentmode(0);
			r.setBillno(pb.getId());
			r.setReceivabledescribe(pb.getId() + "机构积分结算生成应收款");
			r.setAlreadysum(0.00d);
			r.setIsclose(0);
			r.setMakeorganid(users.getMakeorganid());
			r.setMakedeptid(users.getMakedeptid());
//			r.setMakeid(pb.getMakeid());
			r.setMakedate(DateUtil.StringToDatetime(DateUtil
					.getCurrentDateTime()));
			ar.addReceivable(r);
			//---------------------生成应收款 end-----------------------

			request.setAttribute("result", "databases.audit.success");
//			DBUserLog.addUserLog(userid, "复核机构积分结算单,编号：" + id);// 日志
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
