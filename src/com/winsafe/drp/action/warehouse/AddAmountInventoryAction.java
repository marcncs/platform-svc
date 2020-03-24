package com.winsafe.drp.action.warehouse;

import java.text.MessageFormat;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AmountInventory;
import com.winsafe.drp.dao.AppAmountInventory;
import com.winsafe.drp.dao.AppBarcodeInventory;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.BarcodeInventory;
import com.winsafe.drp.dao.OtherShipmentBillAll;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.metadata.InventoryConfirmStatus;
import com.winsafe.drp.util.JProperties;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.mail.dao.AppBatchCompleteMail;
import com.winsafe.mail.metadata.MailType;
import com.winsafe.mail.pojo.BatchCompleteMail;
import com.winsafe.mail.smtp.base.SMTPMailFactory;

public class AddAmountInventoryAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		super.initdata(request);
		AppBarcodeInventory api = new AppBarcodeInventory();

		try {
			Properties sysPro = JProperties.loadProperties("system.properties", JProperties.BY_CLASSLOADER);
			/*BarcodeInventory bi = new BarcodeInventory();
			String biid = MakeCode.getExcIDByRandomTableName(
					"barcode_inventory", 2, "BI");
			bi.setId(biid);
			bi.setOrganid(request.getParameter("organid"));
			bi.setWarehouseid(request.getParameter("warehouseid"));
			//设置billsort类型
			bi.setShipmentsort(99);
			bi.setIsaudit(0);
			bi.setAuditid(0);
			bi.setMakeorganid(users.getMakeorganid());
			bi.setMakedeptid(users.getMakedeptid());
			bi.setMakeid(users.getUserid());
			bi.setMakedate(DateUtil.getCurrentDate());
			bi.setIsblankout(0);
			bi.setBlankoutid(0);
			bi.setIsendcase(0);
			bi.setEndcaseid(0);
			if ("1".equals(sysPro.getProperty("autoAuditBarcodeInventory"))) {
				bi.setIsapprove(1);
			} else {
				bi.setIsapprove(0);
				addNotificationMail(bi);
			}
			
			//设置记账
			bi.setIsaccount(1);
			bi.setTakestaus(0);
			api.addBarcodeInventory(bi);*/
			
			
			
			
			AppAmountInventory aai = new AppAmountInventory();
			
			
			
			AmountInventory ai = new AmountInventory();
			String aiid = MakeCode.getExcIDByRandomTableName(
					"amount_inventory", 2, "AI");
			ai.setId(aiid);
			ai.setOrganid(request.getParameter("organid"));
			ai.setWarehouseid(request.getParameter("warehouseid"));
			// 设置billsort类型(无类型)
			ai.setShipmentsort(0);
			if ("1".equals(sysPro.getProperty("autoAuditAmountInventory"))) {
				ai.setIsapprove(InventoryConfirmStatus.APPROVED.getValue());
			} else {
				ai.setIsapprove(InventoryConfirmStatus.NOT_AUDITED.getValue());
				addNotificationMail(ai);
			}
			ai.setIsaudit(0);
			ai.setAuditid(users.getUserid());
			ai.setMakeorganid(users.getMakeorganid());
			ai.setMakedeptid(users.getMakedeptid());
			ai.setMakeid(users.getUserid());
			ai.setMakedate(DateUtil.getCurrentDate());
			ai.setRequiredate(DateUtil.getCurrentDate());
			ai.setAuditdate(DateUtil.getCurrentDate());
			ai.setIsblankout(0);
			ai.setBlankoutid(0);
			ai.setIsendcase(0);
			ai.setEndcaseid(0);
			// 设置记账
			ai.setIsaccount(1);
			ai.setTakestaus(0);
			
			
			aai.addAmountInventory(ai);
			
			
			
			
			
			
			
			request.setAttribute("result", "databases.add.success");
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

	private void addNotificationMail(AmountInventory bi) {
		AppUsers appUser = new AppUsers();
		List<Users> auditUsers = appUser.getUsersByOperate("审核","数量盘点");
		for(Users user : auditUsers) {
			if(!StringUtil.isEmpty(user.getEmail())) {
				addToAuditNotificationMail(user, bi.getId());
			}
		}
	}
	
	private void addToAuditNotificationMail(Users user, String billNo) {
		AppBatchCompleteMail abcm = new AppBatchCompleteMail();
		BatchCompleteMail mail = new BatchCompleteMail();
		Properties mailPro = SMTPMailFactory.getSMTPMailFactory();
		mail.setMailSender(mailPro.getProperty("Bayer_smtp_from"));
		mail.setMailFrom(mailPro.getProperty("Bayer_smtp_from"));
		mail.setMailTo(user.getEmail());//发送给谁
		mail.setMailSubject(mailPro.getProperty("ai_mail_subject"));//邮件标题
		mail.setCreateDate(DateUtil.getCurrentDate());
		mail.setMailType(MailType.AMOUNT_INVENTORY_AUDIT.getDbValue());
		String mailBody = MessageFormat.format(mailPro.getProperty("ai_mail_body"),billNo);
		mail.setMailBody(mailBody);//内容
		abcm.add(mail);
	}

}