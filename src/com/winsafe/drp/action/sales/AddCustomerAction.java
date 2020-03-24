package com.winsafe.drp.action.sales;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppBank;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppLinkMan;
import com.winsafe.drp.dao.Bank;
import com.winsafe.drp.dao.Customer;
import com.winsafe.drp.dao.Linkman;
import com.winsafe.drp.dao.ValidateCustomer;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.PYCode;

public class AddCustomerAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			ValidateCustomer vc = (ValidateCustomer) form;
			AppCustomer appCustomer = new AppCustomer();
			AppLinkMan applinkman = new AppLinkMan();
			AppBank appbank = new AppBank();


			// if (appCustomer.customerNameExist(customer.getCname())) {
			// ActionError error = new ActionError("该客户名已经存在!!");
			// errors.add("customerNameExist", error);
			// this.saveErrors(request, errors);
			// return mapping.getInputForward();
			// }

			Customer c = new Customer();
//			String maxid = appCustomer.getMaxCustomerIdByAreas(vc.getAreas());
//			// String id = MakeCode.getExcIDByRandomTableName("customer",0,"");
//			String cid = formatCustomerId(vc.getAreas(), maxid);
//			
//			int cidQ = appCustomer.getCustomerByCcode(cid);
//			Integer cidN=0;
//			if(cidQ>=1){
//				cidN = Integer.parseInt(cid)+1;
//				cid = String.valueOf(cidN);
//			}
			String cid = MakeCode.getExcIDByRandomTableName("customer",3,"C");
			
			c.setCid(cid);
			c.setCname(vc.getCname());
			c.setCpycode(PYCode.getSampCode(vc.getCname()));
			c.setKind(1);
			c.setPrompt(vc.getPrompt());
			c.setCreditlock(vc.getCreditlock());
			c.setSort(vc.getSort());
			c.setRate(vc.getRate());
			c.setDiscount(vc.getDiscount());
			c.setTaxrate(vc.getTaxrate());
			c.setDiscount(vc.getDiscount());
			c.setVocation(vc.getVocation());
//			c.setSigndate(vc.getSigndate().toCharArray());
			c.setCustomertype(vc.getCustomertype());
			c.setCustomerstatus(vc.getCustomerstatus());
			c.setYauld(vc.getYauld());
			c.setSource(vc.getSource());
//			c.setDitchid(vc.getDitchid());
//			c.setRegion(vc.getRegion());
			c.setProvince(vc.getProvince());
			c.setCity(vc.getCity());
			c.setAreas(vc.getAreas());
			c.setCommaddr(vc.getCommaddr());
			c.setDetailaddr(vc.getDetailaddr());
			c.setPostcode(vc.getPostcode());
			c.setHomepage(vc.getHomepage());
			c.setOfficetel(vc.getOfficetel());
			c.setMobile(vc.getMobile());
			c.setFax(vc.getFax());
			c.setEmail(vc.getEmail());
			c.setTaxcode(vc.getTaxcode());
			c.setMakedate(DateUtil.StringToDate(DateUtil
					.getCurrentDateString()));
//			c.setSpecializeid(vc.getSpecializeid());
			c.setMakeorganid(users.getMakeorganid());
			c.setMakedeptid(users.getMakedeptid());
			c.setMakeid(userid);
			c.setRemark(vc.getRemark());
//			c.setPaymentmode(vc.getPaymentmode());
			c.setTransportmode(vc.getTransportmode());
//			c.setAccnumber(vc.getAccnumber());
			c.setIsdel(0);
			c.setIsreceivemsg(vc.getIsreceivemsg());
			c.setIsactivate(0);

			// System.out.println("-------------"+vc.getPicture());
			// 上传图片
			FormFile pictureFile = null;
			pictureFile = (FormFile) vc.getPicture();
			String extName = null;
			 String fileName = null;
			String contentType = null;
			String filePath = request.getRealPath("/");
			if (pictureFile != null && !pictureFile.equals("")) {
				 fileName = pictureFile.getFileName().toLowerCase();
				contentType = pictureFile.getContentType();
				if (contentType != null) {
					if (contentType.indexOf("jpg") >= 0)
						extName = ".jpg";
					else if (contentType.indexOf("jpeg") >= 0)
						extName = ".jpg";
					else if (contentType.indexOf("gif") >= 0)
						extName = ".gif";
				}
				if (extName != null) {
					InputStream fis = pictureFile.getInputStream();
					String firstName = fileName.substring(0, fileName.indexOf("."));
					String sDateTime = DateUtil.getCurrentDateTimeString();
					String saveFileName =firstName+"_"
							+ sDateTime + extName;
					
					OutputStream fos = new FileOutputStream(filePath
							+ "/picture/" + saveFileName);
					String fileAddress = "picture/" + saveFileName;
					byte[] buffer = new byte[8192];
					int bytesRead = 0;
					while ((bytesRead = fis.read(buffer, 0, 8192)) != -1) {

						
						fos.write(buffer, 0, bytesRead);
					}
					fos.close();
					fis.close();
					
					c.setCphoto(fileAddress);
				}
			}

			
			Linkman linkman = null;
			for (int i = 0; i < vc.getName().length; i++) {
				if (!"".equals(vc.getName()[i])) {
					linkman = new Linkman();
					linkman.setId(Integer.valueOf(MakeCode
							.getExcIDByRandomTableName("linkman", 0, "")));
					linkman.setCid(cid);
					linkman.setName(vc.getName()[i]);
					linkman.setSex(vc.getSex()[i]);
					linkman.setIdcard(vc.getIdcard()[i]);
					linkman.setBirthday(DateUtil
							.StringToDate(vc.getBirthday()[i]));
					linkman.setDepartment(vc.getDepartment()[i]);
					linkman.setDuty(vc.getDuty()[i]);
					linkman.setOfficetel(vc.getLinkofficetel()[i]);
					linkman.setMobile(vc.getLinkmobile()[i]);
					linkman.setHometel(vc.getHometel()[i]);
					linkman.setEmail(vc.getLinkemail()[i]);
					linkman.setQq(vc.getQq()[i]);
					linkman.setMsn(vc.getMsn()[i]);
					linkman.setAddr(vc.getAddr()[i]);
					linkman.setIsmain(vc.getIsmain()[i]);
					linkman.setTransit(vc.getTransit()[i]);
					applinkman.addLinkman(linkman);
				}
			}
			// 新增银行帐户
			Bank bank = null;
			for (int i = 0; i < vc.getBankname().length; i++) {
				if (!"".equals(vc.getBankname()[i])) {
					bank = new Bank();
					bank.setId(Long.parseLong(MakeCode
							.getExcIDByRandomTableName("bank", 0, "")));
					bank.setCid(cid);
					bank.setBankname(vc.getBankname()[i]);
					bank.setBankaccount(vc.getBankaccount()[i]);
					bank.setDoorname(vc.getDoorname()[i]);
					appbank.addBank(bank);
				}
			}

			appCustomer.addCustomer(c);

			String forward = "../sales/listCustomerAction.do";
			request.setAttribute("forward", forward);
			request.setAttribute("result", "databases.add.success");
			DBUserLog.addUserLog(userid, "新增客户");
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.getInputForward();
	}

//	private String formatCustomerId(Integer areas, String maxid) {
//		String strid = "";
//		if (maxid == null) {
//			strid = "0001";
//		} else {
//			strid = MakeCode.getFormatNums(Integer.parseInt(maxid) + 1, 4);
//		}
//		return areas + strid;
//	}

}
