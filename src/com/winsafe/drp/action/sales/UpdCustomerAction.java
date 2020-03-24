package com.winsafe.drp.action.sales;
//package com.winsafe.drp.action.sales;
//
//import java.io.FileOutputStream;
//import java.io.InputStream;
//import java.io.OutputStream;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.struts.action.Action;
//import org.apache.struts.action.ActionForm;
//import org.apache.struts.action.ActionForward;
//import org.apache.struts.action.ActionMapping;
//import org.apache.struts.upload.FormFile;
//
//import com.winsafe.drp.dao.AppBank;
//import com.winsafe.drp.dao.AppCustomer;
//import com.winsafe.drp.dao.AppLinkMan;
//import com.winsafe.drp.dao.Bank;
//import com.winsafe.drp.dao.Customer;
//import com.winsafe.drp.dao.Linkman;
//import com.winsafe.drp.dao.UserManager;
//import com.winsafe.drp.dao.UsersBean;
//import com.winsafe.drp.dao.ValidateCustomer;
//import com.winsafe.drp.util.DateUtil;
//import com.winsafe.drp.util.DbUtil;
//import com.winsafe.drp.util.MakeCode;
//
//public class UpdCustomerAction extends BaseAction {
//
//	public ActionForward execute(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//		ValidateCustomer vc = (ValidateCustomer) form;
//		AppCustomer appCustomer = new AppCustomer();
//		AppBank appbank = new AppBank();
//		AppLinkMan applinkman = new AppLinkMan();
//
//		try {
//
//			
////			Long userid = users.getUserid();
//
//			Customer c = appCustomer.getCustomer(vc.getCid());
//
//			c.setCname(vc.getCname());
//			c.setKind(vc.getKind());
//			c.setPrompt(vc.getPrompt());
//			c.setCreditlock(vc.getCreditlock());
//			c.setSort(vc.getSort());
//			c.setRate(vc.getRate());
//			c.setDiscount(vc.getDiscount());
//			c.setTaxrate(vc.getTaxrate());
//			c.setDiscount(vc.getDiscount());
//			c.setTaxrate(vc.getTaxrate());
//
//			c.setVocation(vc.getVocation());
//			c.setSigndate(DateUtil.StringToDate(vc.getSigndate()));
//			c.setCustomertype(vc.getCustomertype());
//			c.setCustomerstatus(vc.getCustomerstatus());
//			c.setYauld(vc.getYauld());
//			c.setSource(vc.getSource());
//			c.setDitchid(vc.getDitchid());
//			c.setProvince(vc.getProvince());
//			c.setCity(vc.getCity());
//			c.setAreas(vc.getAreas());
//			c.setDetailaddr(vc.getDetailaddr());
//			c.setPostcode(vc.getPostcode());
//			c.setHomepage(vc.getHomepage());
//			c.setOfficetel(vc.getOfficetel());
//			c.setMobile(vc.getMobile());
//			c.setFax(vc.getFax());			
//			c.setEmail(vc.getEmail());
//			c.setSpecializeid(vc.getSpecializeid());
//
//			c.setTaxcode(vc.getTaxcode());
//			c.setRemark(vc.getRemark());
//			c.setPaymentmode(vc.getPaymentmode());
//			c.setTransportmode(vc.getTransportmode());
//			c.setAccnumber(vc.getAccnumber());
//
//			// 上传图片
//			FormFile pictureFile = null;
//			pictureFile = (FormFile) vc.getPicture();
//			String extName = null;
//			String fileName = null;
//			String contentType = null;
//			String filePath = request.getRealPath("/");
//			if (pictureFile != null && !pictureFile.equals("")) {
//				fileName = pictureFile.getFileName().toLowerCase();
//				contentType = pictureFile.getContentType();
//				if (contentType != null) {
//					if (contentType.indexOf("jpg") >= 0)
//						extName = ".jpg";
//					else if (contentType.indexOf("jpeg") >= 0)
//						extName = ".jpg";
//					else if (contentType.indexOf("gif") >= 0)
//						extName = ".gif";
//				}
//				if (extName != null) {
//					InputStream fis = pictureFile.getInputStream();
//					String sDateTime = DateUtil.getCurrentDateTimeString();
//					String saveFileName = String.valueOf(vc.getCid())
//							+ sDateTime + extName;
//					
//					OutputStream fos = new FileOutputStream(filePath
//							+ "/picture/" + saveFileName);
//					String fileAddress = "picture/" + saveFileName;
//					byte[] buffer = new byte[8192];
//					int bytesRead = 0;
//					while ((bytesRead = fis.read(buffer, 0, 8192)) != -1) {
//
//						
//						fos.write(buffer, 0, bytesRead);
//					}
//					fos.close();
//					fis.close();
//					
//					c.setCphoto(fileAddress);
//				}
//			}
//			// System.out.println("----"+c.getCphoto());
//			if (c.getCphoto() == null || c.getCphoto().equals("")) {
//				c.setCphoto(request.getParameter("cphoto"));
//			}
//
//			
//			Linkman linkman = null;
//			for (int i = 0; i < vc.getName().length; i++) {
//				if (!"".equals(vc.getName()[i])) {
//					linkman = new Linkman();					
//					linkman.setCid(vc.getCid());
//					linkman.setName(vc.getName()[i]);
//					linkman.setSex(vc.getSex()[i]);
//					linkman.setIdcard(vc.getIdcard()[i]);
//					linkman.setDepartment(vc.getDepartment()[i]);
//					linkman.setDuty(vc.getDuty()[i]);
//					linkman.setOfficetel(vc.getLinkofficetel()[i]);
//					linkman.setMobile(vc.getLinkmobile()[i]);
//					linkman.setHometel(vc.getHometel()[i]);
//					linkman.setEmail(vc.getLinkemail()[i]);
//					linkman.setQq(vc.getQq()[i]);
//					linkman.setMsn(vc.getMsn()[i]);
//					linkman.setAddr(vc.getAddr()[i]);
//					linkman.setIsmain(vc.getIsmain()[i]);
//					linkman.setTransit(vc.getTransit()[i]);
//					if (!"".equals(vc.getLinkid()[i])) {
//						linkman.setId(Long.parseLong(vc.getLinkid()[i]));
//						applinkman.updateLinkman(linkman, vc.getBirthday()[i]);
//					} else {						
//						linkman.setId(Long.parseLong(MakeCode
//								.getExcIDByRandomTableName("linkman", 0, "")));
//						applinkman.addLinkman(linkman);
//						
//					}					
//				}
//			}
//
//			
//			Bank bank = null;
//			for (int i = 0; i < vc.getBankname().length; i++) {
//				if (!"".equals(vc.getBankname()[i])) {
//					bank = new Bank();	
//					bank.setCid(vc.getCid());
//					bank.setBankname(vc.getBankname()[i]);
//					bank.setBankaccount(vc.getBankaccount()[i]);
//					bank.setDoorname(vc.getDoorname()[i]);
//					if (!"".equals(vc.getBankid()[i])) {
//						bank.setId(Long.parseLong(vc.getBankid()[i]));
//						appbank.updBankBySql(bank);						
//					} else {						
//						bank.setId(Long.parseLong(MakeCode
//								.getExcIDByRandomTableName("bank", 0, "")));
//						appbank.addBank(bank);
//					}		
//				}
//			}
//
//			 appCustomer.updateCustomer(c);
//						request.setAttribute("result", "databases.upd.success");
//
//			DBUserLog.addUserLog(userid, "修改客户资料,编号：" + vc.getCid());
//			//con.commit();
//			return mapping.findForward("success");
//		} catch (Exception e) {
//			e.printStackTrace();
//			////con.rollback();
//		} finally {
//			//////HibernateUtil.closeSession();
//		}
//		return mapping.getInputForward();
//	}
//
//}
