package com.winsafe.drp.action.sales;
//package com.winsafe.drp.action.sales;
//
//import java.sql.Connection;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.struts.action.Action;
//import org.apache.struts.action.ActionForm;
//import org.apache.struts.action.ActionForward;
//import org.apache.struts.action.ActionMapping;
//import org.hibernate.Session;
//
//import com.winsafe.drp.dao.AppOutlay;
//import com.winsafe.drp.dao.AppPayable;
//import com.winsafe.drp.dao.Outlay;
//import com.winsafe.drp.dao.Payable;
//import com.winsafe.drp.dao.UserManager;
//import com.winsafe.drp.dao.UsersBean;
//import com.winsafe.drp.entity.HibernateUtil;
//import com.winsafe.drp.util.DateUtil;
//import com.winsafe.drp.util.MakeCode;
//
//public class MakePayableAction extends BaseAction{
//	public ActionForward execute(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//		
//	    Long userid = users.getUserid();
//	    String oid = request.getParameter("OID");
//	    //Session 
//	    ////Connection 
//	    
//		try{
//			AppOutlay ao = new AppOutlay();
//			Outlay ol = new Outlay();
//			ol = ao.getOutlayByID(oid);
//			
//			if (ol.getIscreate().intValue() == 1) { //鍒ゆ柇
//		          String result = "databases.record.already";
//		          request.setAttribute("result", "databases.add.success");
//		          return mapping.findForward("lock");
//		        }
//			
//			Payable pa = new Payable();
//	    	pa.setId(MakeCode.getExcIDByRandomTableName("payable",2,""));
//	    	pa.setObjectsort(1);
//	    	pa.setPayeeid(ol.getCustomerid());
//	    	pa.setPayablesum(ol.getTotaloutlay());
//	    	pa.setMakeid(userid);
//	    	pa.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
//	    	pa.setIsred(0);
//	    	pa.setIsrefer(0);
//	    	pa.setApprovestatus(0);
//	    	pa.setIstally(0);
//	    	pa.setTallyid(Long.valueOf(0));
//	    	pa.setPaystatus(0);
//
//	      AppPayable ar = new AppPayable();
//	      String add = ar.addPayable(pa);
//	      
//	      String upd = ao.updIsCreate(oid);
//
//	      String result = "";
//
//	      if (add.equals("s")) {
//	        result = "databases.add.success";
//	      }
//	      else {
//	        result = "databases.add.fail";
//	      }
//	      request.setAttribute("result", "databases.add.success");
//
//	      
//	      return mapping.findForward("make");
//		}catch(Exception e){
//			
//			e.printStackTrace();
//		}finally {
//		      //
//		}
//		return mapping.getInputForward();
//	}
//
//}
