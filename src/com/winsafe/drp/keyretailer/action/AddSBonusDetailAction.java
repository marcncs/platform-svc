package com.winsafe.drp.keyretailer.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.keyretailer.dao.AppSBonusAccount;
import com.winsafe.drp.keyretailer.dao.AppSBonusAppraise;
import com.winsafe.drp.keyretailer.dao.AppSBonusDetail;
import com.winsafe.drp.keyretailer.pojo.SBonusAccount;
import com.winsafe.drp.keyretailer.pojo.SBonusAppraise;
import com.winsafe.drp.keyretailer.pojo.SBonusDetail;
import com.winsafe.drp.keyretailer.service.SBonusService;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.RequestTool;
import com.winsafe.hbm.util.StringUtil;
import common.Logger;

public class AddSBonusDetailAction extends BaseAction {
	private AppSBonusDetail asbd = new AppSBonusDetail();
	private AppSBonusAccount asa = new AppSBonusAccount();
	private AppSBonusAppraise appreaise = new AppSBonusAppraise();
	private SBonusService sbservice = new SBonusService();
	private static Logger logger = Logger.getLogger(AddSBonusDetailAction.class);
	
	public static final String TITLE = "积分调整信息";

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 初始化
		initdata(request);
		try {
			
			SBonusDetail abd = new SBonusDetail();
			String organId = request.getParameter("porganid");// 记录到积分管理中
			String porganname = request.getParameter("porganname");
			String oppOrganId = request.getParameter("receiveorganid");
			String oppOname = request.getParameter("oname");
			String dateTime = DateUtil.getCurrentDateTimeString();
			//String years = request.getParameter("years");
			String year = DateUtil.getCurrentYear();
			String month = dateTime.substring(4,6);
			//String strproductid = request.getParameter("productid");
			String productName = request.getParameter("productname");
			String spec = request.getParameter("specmode");
			String mcode = request.getParameter("nccode");
			String countUnit = request.getParameter("countUnit");
			String remark = request.getParameter("remark");
			
			double quantity = RequestTool.getDouble(request, "quantity");
			double bonusPoint = RequestTool.getDouble(request, "bonusPoint");
			
			SBonusAccount account = asa.getByOrganId(organId);
			
			if(account==null || account.getAccountId()==null){
				request.setAttribute("result", "当前机构没有积分账号");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			if(StringUtil.isEmpty(remark)){
				
				remark = "调整:积分账号["+account.getAccountId()+"]的积分!";
			}

			abd.setAccountId(account.getAccountId());
			abd.setAmount(quantity);
			abd.setBonusPoint(bonusPoint);
			abd.setBonusType(5);// 调整
			abd.setOppOrganId(oppOrganId);
			abd.setOrganId(organId);
			abd.setYear(Integer.parseInt(year));
			abd.setMonth(Integer.parseInt(month));
			abd.setMakeDate(DateUtil.getCurrentDate());
			abd.setUploadId(0);
			abd.setProductName(productName);
			abd.setSpec(spec);
			abd.setMcode(mcode);
			abd.setRemark(remark);

			asbd.addSBonusDetail(abd);// 添加明细
			
			//调整积分管理的总积分
			SBonusAppraise abaraise = appreaise.getSBonusAppraiseByAccountId(account.getAccountId().toString());
			
			if(abaraise!=null){
				abaraise.setBonusPoint(abaraise.getBonusPoint() + (bonusPoint));
				abaraise.setActualPoint(abaraise.getBonusPoint() * abaraise.getAppraise());
				appreaise.updSBonusAppraise(abaraise);
			}
			
			//往消息中心添加一条明细信息
			
			sbservice.adjustSBonus(abd,porganname,oppOname,Integer.parseInt(countUnit));
			
			
			request.setAttribute("result", "databases.add.success");
			return mapping.findForward("success");

		} catch (Exception ex) {
			logger.error("", ex);
			throw ex;
		}
	}
}
