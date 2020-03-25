package com.winsafe.drp.action.finance;
//package com.winsafe.drp.action.finance;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.struts.action.Action;
//import org.apache.struts.action.ActionForm;
//import org.apache.struts.action.ActionForward;
//import org.apache.struts.action.ActionMapping;
//
//import com.winsafe.drp.dao.AppCashBank;
//import com.winsafe.drp.dao.AppPaymentLog;
//import com.winsafe.drp.dao.AppPaymentLogDetail;
//import com.winsafe.drp.dao.AppUsers;
//import com.winsafe.drp.dao.PaymentLog;
//import com.winsafe.drp.dao.PaymentLogDetail;
//import com.winsafe.drp.dao.PaymentLogDetailForm;
//import com.winsafe.drp.dao.PaymentLogForm;
//import com.winsafe.drp.dao.UserManager;
//import com.winsafe.drp.dao.UsersBean;
//import com.winsafe.drp.util.DateUtil;
//import com.winsafe.drp.util.DbUtil;
//import com.winsafe.drp.util.Internation;
//
//public class ToApprovePaymentLogAction extends BaseAction {
//
//	public ActionForward execute(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//		String id = request.getParameter("ID");
//		String actid = request.getParameter("actid");
//	    String logid = request.getParameter("logid");
//		super.initdata(request);super.initdata(request);try{
//			AppPaymentLog apl = new AppPaymentLog();
//			PaymentLog pl = apl.getPaymentLogByID(id);
//			AppCashBank appcb = new AppCashBank();
//			AppUsers au = new AppUsers();
//
//			PaymentLogForm plf = new PaymentLogForm();
//			plf.setId(pl.getId());
//			plf.setPoid(pl.getPoid());
//			plf.setPayee(pl.getPayee());
//			plf.setPaypurpose(pl.getPaypurpose());
//			Long fundsrc = Long.parseLong(pl.getFundsrc().toString());
//			plf.setFundsrcname(appcb.getCashBankById(fundsrc).getCbname());
//			plf.setPaysum(pl.getPaysum());
//			plf.setAccidentsum(pl.getAccidentsum());
//			plf.setPoundage(pl.getPoundage());
//			plf.setBillnum(pl.getBillnum());
//			plf.setRemark(pl.getRemark());
//			plf.setMakeidname(au.getUsersByid(pl.getMakeid()).getRealname());
//			plf.setBilldate(DateUtil.formatDate(pl.getBilldate()));
//			plf.setMakedate(DateUtil.formatDate(pl.getMakedate()));
//			plf.setIsaudit(pl.getIsaudit());
//			plf.setIsauditname(Internation.getStringByKeyPosition("YesOrNo",
//					request, pl.getIsaudit(), "global.sys.SystemResource"));
//			if (pl.getAuditid() != null && pl.getAuditid() > 0) {
//				plf.setAuditidname(au.getUsersByid(pl.getAuditid())
//						.getRealname());
//			} else {
//				plf.setAuditidname("");
//			}
//			plf.setAuditdate(DateUtil.formatDate(pl.getAuditdate()));
//
//			AppPaymentLogDetail aila = new AppPaymentLogDetail();
//			List ildlist = aila.getPaymentLogDetailByPLID(id);
//			ArrayList als = new ArrayList();
//			PaymentLogDetail ild = null;
//			for (int i = 0; i < ildlist.size(); i++) {
//				ild = (PaymentLogDetail) ildlist.get(i);
//				PaymentLogDetailForm ildf = new PaymentLogDetailForm();
//				ildf.setId(ild.getId());
//				ildf.setProductid(ild.getProductid());
//				ildf.setProductname(ild.getProductname());
//				ildf.setSpecmode(ild.getSpecmode());
//				ildf.setBatch(ild.getBatch());
//				ildf.setBillno(ild.getBillno());				
//				ildf.setUnitidname(Internation.getStringByKeyPositionDB("CountUnit", Integer.valueOf(ild.getUnitid()
//								.toString())));
//				ildf.setQuantity(ild.getQuantity());
//				ildf.setGoodsfund(ild.getGoodsfund());
//				ildf.setScot(ild.getScot());
//				als.add(ildf);
//
//			}
//			
//			 String approvestatus = Internation.getSelectTagByKeyAll("SubApproveStatus2", request,
//			            "ApproveStatus", null, null);
//			      String stractid=Internation.getStringByKeyPositionDB("ActID",
//			              Integer.valueOf(actid));
//			      request.setAttribute("approvestatus",approvestatus);
//			      request.setAttribute("stractid", stractid);
//			      request.setAttribute("logid", logid);
//			request.setAttribute("plf", plf);
//			request.setAttribute("als", als);
//			UsersBean users = UserManager.getUser(request);
//			Long userid = users.getUserid();
//			DBUserLog.addUserLog(userid, "付款记录详情");
//			return mapping.findForward("toapprove");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return new ActionForward(mapping.getInput());
//	}
//}
