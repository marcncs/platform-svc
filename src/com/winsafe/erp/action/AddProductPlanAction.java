package com.winsafe.erp.action;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.erp.dao.AppPrepareCode;
import com.winsafe.erp.dao.AppProductPlan;
import com.winsafe.erp.dao.AppUnitInfo;
import com.winsafe.erp.pojo.ProductPlan;
import com.winsafe.erp.pojo.UnitInfo;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.mail.dao.AppBatchCompleteMail;
import com.winsafe.mail.metadata.MailType;
import com.winsafe.mail.pojo.BatchCompleteMail;

public class AddProductPlanAction extends BaseAction { 

	private AppProductPlan appProductPlan = new AppProductPlan();
	private static Logger logger = Logger.getLogger(AddProductPlanAction.class);
	private AppBatchCompleteMail abcm = new AppBatchCompleteMail();
	private AppOrgan aog = new AppOrgan(); 
	private AppProduct apd = new AppProduct(); 
	private AppUnitInfo appu = new AppUnitInfo();

	/* (non-Javadoc)
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		initdata(request);
		UsersBean users = UserManager.getUser(request);
		try {
			
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
			
			String wise = request.getParameter("wise");//License-in 用wise字段
			
			UnitInfo unitinfo = appu.getUnitInfoByOidAndPid(organid, productid);
			if (!StringUtil.isEmpty(wise)) {
				if(null==unitinfo){
					request.setAttribute("result", "该工厂未配置产品的托盘信息，请配置!");
					return new ActionForward("/sys/lockrecordclose2.jsp");
				}
				
			}
			
			if(unitinfo.getNeedCovertCode() != null && unitinfo.getNeedCovertCode() == 1) {
//				if(!StringUtil.isEmpty(unitinfo.getCodeSeq()) && unitinfo.getCodeSeq().compareTo(codeFrom) > 0) {
//					request.setAttribute("result", "新增失败,暗码初始值 小于当前初始值");
//					return new ActionForward("/sys/lockrecordclose2.jsp");
//				}
				
				if(codeFrom.length() != 11) {
					request.setAttribute("result", "新增失败,暗码长度不是11位");
					return new ActionForward("/sys/lockrecordclose2.jsp");
				}
				
//				if(!codeFrom.substring(0, 3).equals(codeTo.substring(0, 3))) {
//					request.setAttribute("result", "新增失败,起始码与截至码前三位不一致");
//					return new ActionForward("/sys/lockrecordclose2.jsp");
//				}
				
				
				if(!StringUtil.isEmpty(unitinfo.getCodeSeq()) && !unitinfo.getCodeSeq().substring(0, 3).equals(codeFrom.substring(0, 3))) {
					request.setAttribute("result", "新增失败,暗码前三位产品代码与托盘信息中设置的不一致");
					return new ActionForward("/sys/lockrecordclose2.jsp");
				} 
				
				Long codeToLong = Long.valueOf(codeFrom) + Long.valueOf(boxnum) - 1;
				codeTo = Constants.ZERO_PREFIX[11-codeToLong.toString().length()]+codeToLong;
//				if((codeToLong - codeFromLong + 1) != Long.valueOf(boxnum)) {
//					request.setAttribute("result", "新增失败,暗码数量不等于箱码数量");
//					return new ActionForward("/sys/lockrecordclose2.jsp");
//				}
				
				if(appProductPlan.isCodeExists(codeFrom, codeTo, null) || (unitinfo.getCodeSeq().length() == 11 && codeTo.compareTo(unitinfo.getCodeSeq()) < 0)) {
					request.setAttribute("result", "新增失败,该范围内部分暗码已被使用,请使用"+unitinfo.getCodeSeq()+"以及之后的暗码!");
					return new ActionForward("/sys/lockrecordclose2.jsp");
				}
				
				if(StringUtil.isEmpty(unitinfo.getCodeSeq())){
					if(appu.isProCodeAlreadyExists(codeFrom.substring(0, 3))) { 
						request.setAttribute("result", "新增失败,前三位产品码已被其他产品使用过!");
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
			
//			if(!StringUtil.isEmpty(codeFrom) && !StringUtil.isEmpty(codeTo) && appProductPlan.isCodeExists(codeFrom, codeTo)){
//				request.setAttribute("result", "新增失败,暗码范围已使用过");
//				return new ActionForward("/sys/lockrecordclose2.jsp");
//			}
 
//			ProductPlan productPlan = appProductPlan.getProductPlanByOidAndPid(organid, productid);
//			if (productPlan != null) {
//				request.setAttribute("result", "该机构下的该产品已经存在！新增失败！");
//				return new ActionForward("/sys/lockrecordclose2.jsp");
//			}
			
			ProductPlan productPlan = new ProductPlan();
			productPlan.setOrganId(organid);
			productPlan.setProductId(productid);
			productPlan.setPONO(PONO);
			productPlan.setMbatch(mbatch);
			productPlan.setPbatch(pbatch);
			productPlan.setProDate(DateUtil.StringToDate(proDate));
			productPlan.setPackDate(DateUtil.StringToDate(packDate));
			productPlan.setBoxnum(Integer.valueOf(boxnum));
			productPlan.setCopys(Integer.valueOf(copys));
			productPlan.setApprovalFlag(0);
			
//			AppBaseResource appBaseResource = new AppBaseResource();
//			int count = 0;
//			Long codeFrom = 0l;
//			Long codeTo = 0l;
//			
//			while(count > 0) {
//				BaseResource bs = appBaseResource.getBaseResourceValue("CovertCode", 1);
//				codeFrom = Long.valueOf(bs.getTagsubvalue());
//			}
			
			if(!StringUtil.isEmpty(codeFrom)) {
				productPlan.setCodeFrom(codeFrom);
			}
			if(!StringUtil.isEmpty(codeTo)) {
				productPlan.setCodeTo(codeTo);
			}
			
			//add tommy  当为License-in 是自动审批，不发送邮件
			//增加检查数量
			if (!StringUtil.isEmpty(wise)
					&& "1".equals(wise.trim())) { 
				//自动
				//托对应箱数量
				Integer t_b = unitinfo.getUnitCount();
				//余
				Integer yu = productPlan.getBoxnum()%t_b;
				//托数量
				Integer t = productPlan.getBoxnum()/t_b;
				if(yu.intValue()!=0){
					t = t+1;
				}
				//总码数
				Integer total  = productPlan.getBoxnum() +t;
				
				AppPrepareCode apppre = new AppPrepareCode();
				int count = apppre.countNoUsedByOid(productPlan.getOrganId());
				if(count<total){
					request.setAttribute("result", "该工厂SAP码数量不足，请上传!");
					return new ActionForward("/sys/lockrecordclose2.jsp");
				}
				
				productPlan.setApprovalFlag(1);
			}
			
			appProductPlan.AddProductPlan(productPlan);

			//add tommy  当为License-in 不发送邮件
			if (!StringUtil.isEmpty(wise)
					&& "1".equals(wise.trim())) {
			} else {
				//生产计划添加之后，添加发送邮件信息
				addMail(productPlan);
			}
			request.setAttribute("result", "新增成功");
			DBUserLog.addUserLog(request, "编号：" + productPlan.getId());
			
			
		} catch (Exception e) {
			logger.error("", e);
			throw e;
		}
		return mapping.findForward("success");
	}
	
	
	/**
	 * 读取配置文件
	 * 
	 * @return
	 */
	private static Properties getJFProperties() {
		InputStream is = AddProductPlanAction.class.getClassLoader().getResourceAsStream("mail.conf");
		Properties p = new Properties();
		try {
			p.load(is);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return p;
	}
	
	public void addMail(ProductPlan productPlan) throws Exception{
		BatchCompleteMail	mail = new BatchCompleteMail();
//		String dateType = DateUtil.formatDate(new Date()) + "_" +MailType.PRODUCT_MSG.getDbValue();
		String orgName = aog.getOrganByID(productPlan.getOrganId()).getOrganname();
		Product pt = apd.getProductByID(productPlan.getProductId());
		mail.setMailSender(getJFProperties().getProperty("Bayer_smtp_from"));
		mail.setMailFrom(getJFProperties().getProperty("Bayer_smtp_from"));
		mail.setMailTo(getJFProperties().getProperty("plant_mail_to"));//发送给谁
		mail.setMailCc(getJFProperties().getProperty("plant_mail_cc"));
		mail.setMailSubject(getJFProperties().getProperty("plant_mail_subject"));//邮件标题
		String mailBody = MessageFormat.format(getJFProperties().getProperty("plant_mail_body"),productPlan.getPONO(),orgName,pt.getmCode(),pt.getProductname());
		mail.setMailBody(mailBody);//内容
		mail.setCreateDate(DateUtil.getCurrentDate());
		mail.setMailType(MailType.PRODUCT_MSG.getDbValue());
//		mail.setMailDateType(dateType);
		abcm.add(mail);
	}

}
