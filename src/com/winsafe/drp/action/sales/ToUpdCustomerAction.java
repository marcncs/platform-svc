package com.winsafe.drp.action.sales;
//package com.winsafe.drp.action.sales;
//
//import java.util.ArrayList;
//import java.util.Iterator;
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
//import com.winsafe.drp.dao.AppBank;
//import com.winsafe.drp.dao.AppCountryArea;
//import com.winsafe.drp.dao.AppCustomer;
//import com.winsafe.drp.dao.AppCustomerSort;
//import com.winsafe.drp.dao.AppDept;
//import com.winsafe.drp.dao.AppDitch;
//import com.winsafe.drp.dao.AppLinkMan;
//import com.winsafe.drp.dao.AppUsers;
//import com.winsafe.drp.dao.CountryArea;
//import com.winsafe.drp.dao.Customer;
//import com.winsafe.drp.dao.CustomerForm;
//import com.winsafe.drp.dao.Dept;
//import com.winsafe.drp.dao.Linkman;
//import com.winsafe.drp.dao.LinkmanForm;
//import com.winsafe.drp.dao.UserManager;
//import com.winsafe.drp.dao.UsersBean;
//import com.winsafe.drp.util.DateUtil;
//import com.winsafe.drp.util.Internation;
//
//public class ToUpdCustomerAction extends BaseAction{
//	public ActionForward execute(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//
//		String cid = request.getParameter("Cid");
//		
////		Long userid = users.getUserid();
//		AppCustomer appCustomer = new AppCustomer();
//		AppBank appbank = new AppBank();
//		AppLinkMan applinkman = new AppLinkMan();
//		AppCustomerSort appcs = new AppCustomerSort();
//		AppCountryArea aca = new AppCountryArea();
//		Customer c = new Customer();
//
//		try {
//			c = appCustomer.getCustomer(cid);
//			
////			List sortlist = appcs.getSortByUserid(userid);
////			List list0 = aca.getCity(0, userid);
//			
//			ArrayList cals = new ArrayList();
//
////			for (int i = 0; i < list0.size(); i++) {
////				CountryArea ca = new CountryArea();
////				Object ob[] = (Object[]) list0.get(i);
////				ca.setId(Long.valueOf(ob[0].toString()));
////				ca.setAreaname(ob[1].toString());
////				ca.setParentid(Long.valueOf(ob[2].toString()));
////				ca.setRank(Integer.valueOf(ob[3].toString()));
////				cals.add(ca);
////			}
//
//			AppDitch ad = new AppDitch();
//			AppUsers au = new AppUsers();
//			
//			CustomerForm cf = new CustomerForm();
//			cf.setCid(c.getCid());
//			cf.setCname(c.getCname());
//			cf.setCphoto(c.getCphoto());
////			cf.setSigndate(DateUtil.formatDate(c.getSigndate()));
//			
//			cf.setKindname(Internation.getSelectTagByKeyAll("Kind",request,"kind",String.valueOf(c.getKind()),null));
//			cf.setPrompt(c.getPrompt());
//			cf.setCreditlock(c.getCreditlock());
//			cf.setSort(c.getSort());
//			cf.setRatename(Internation.getSelectTagByKeyAll("Rate",request,"rate",String.valueOf(c.getRate()),null));
//			cf.setDiscount(c.getDiscount());
//			cf.setTaxrate(c.getTaxrate());			
//			
//			cf.setVocationname(Internation.getSelectTagByKeyAllDBDef("Vocation",
//					 "vocation", c.getSource()));
//			cf.setCustomertypename(Internation.getSelectTagByKeyAll("CustomerType",request,"customertype",String.valueOf(c.getCustomertype()),null));
//			cf.setCustomerstatusname(Internation.getSelectTagByKeyAll("CustomerStatus",request,"customerstatus",String.valueOf(c.getCustomerstatus()),null));
//			cf.setYauldname(Internation.getSelectTagByKeyAll("Yauld",
//					request, "yauld", String.valueOf(c.getYauld()), null));
//			cf.setSourcename(Internation.getSelectTagByKeyAllDBDef("Source",
//					 "source", c.getSource()));
//			cf.setDitchid(c.getDitchid());
//			if(c.getDitchid()>0){
//			cf.setDitchidname(ad.getDitchByID(c.getDitchid()).getDname());
//			}else{
//				cf.setDitchidname("");
//			}
////			
////			cf.setRegion(c.getRegion());
////			if(c.getRegion()>0){
////			cf.setRegionname(aca.getAreaByID(Long.valueOf(c.getRegion())).getAreaname());
////			}else{
////			cf.setRegionname("");
////			}
//			
//			cf.setProvince(c.getProvince());
//			if(c.getProvince()>0){
//			cf.setProvincename(aca.getAreaByID(Long.valueOf(c.getProvince())).getAreaname());
//			}else{
//			cf.setProvincename("");
//			}
//			cf.setCity(c.getCity());
//			if(c.getCity()>0){
//			cf.setCityname(aca.getAreaByID(Long.valueOf(c.getCity())).getAreaname());
//			}else{
//			cf.setCityname("");
//			}
//			cf.setAreas(c.getAreas());
//			if(c.getAreas()>0){
//			cf.setAreasname(aca.getAreaByID(Long.valueOf(c.getAreas())).getAreaname());
//			}else{
//			cf.setAreasname("");
//			}
//			cf.setDetailaddr(c.getDetailaddr());
//			cf.setPostcode(c.getPostcode());
//			cf.setHomepage(c.getHomepage());
//			cf.setOfficetel(c.getOfficetel());
//			cf.setMobile(c.getMobile());
//			cf.setFax(c.getFax());
//			cf.setEmail(c.getEmail());
//			cf.setTaxcode(c.getTaxcode());
//			cf.setMakedate(c.getMakedate().toString());
//			cf.setSpecializeid(c.getSpecializeid());
//			cf.setSpecializeidname(au.getUsersByid(c.getSpecializeid()).getRealname());
//			cf.setIsactivatename(Internation.getSelectTagByKeyAll("YesOrNo",
//					request, "isactivate", String.valueOf(c.getIsactivate()), null));
//			cf.setRemark(c.getRemark());
//			cf.setPaymentmodename(Internation.getSelectPayAllDBDef("paymentmode",c.getPaymentmode()));
//			cf.setTransportmodename(Internation.getSelectTagByKeyAllDBDef("TransportMode",
//					 "transportmode", c.getTransportmode()));
//			cf.setAccnumber(c.getAccnumber());
//			
//			List banklist = appbank.getBankByCid(cid);
//			List links = applinkman.getLinkmanByCid(cid);
//			List linkmanlist = new ArrayList();
//			for(Iterator it = links.iterator(); it.hasNext(); ){
//				Linkman lm = (Linkman)it.next();
//				LinkmanForm f = new LinkmanForm();
//				f.setId(lm.getId());
//				f.setName(lm.getName());
//				f.setSexname(Internation.getSelectTagByKeyAll("Sex",request,"sex",String.valueOf(lm.getSex()),null));
//				f.setIdcard(lm.getIdcard());
//				f.setStrbirthday(DateUtil.formatDate(lm.getBirthday()));
//				f.setDepartment(lm.getDepartment());
//				f.setDuty(lm.getDuty());
//				f.setOfficetel(lm.getOfficetel());
//				f.setMobile(lm.getMobile());
//				f.setHometel(lm.getHometel());
//				f.setEmail(lm.getEmail());
//				f.setQq(lm.getQq());
//				f.setMsn(lm.getMsn());
//				f.setAddr(lm.getAddr());
//				f.setIsmainname(Internation.getSelectTagByKeyAll("YesOrNo",request,"ismain",String.valueOf(lm.getIsmain()),null));
//				f.setTransitname(Internation.getSelectTagByKeyAllDBDef("Transit",
//						 "transit", lm.getTransit()));
//				linkmanlist.add(f);
//			}
//			if ( linkmanlist.size() == 0 ){
//				linkmanlist = null;
//			}
//			
//		      List uls = au.getIDAndLoginName();
//		      ArrayList als = new ArrayList();
//		      for(int u=0;u<uls.size();u++){
//		      	UsersBean ubs = new UsersBean();
//		      	Object[] ub = (Object[]) uls.get(u);
//		      	ubs.setUserid(Long.valueOf(ub[0].toString()));
//		      	ubs.setRealname(ub[2].toString());
//		      	als.add(ubs);
//		      }
//			AppDept appdept = new AppDept();
//			List dls = appdept.getDept();
//			ArrayList aldept = new ArrayList();
//			for (int i = 0; i < dls.size(); i++) {
//				Dept d = new Dept();
//				Object[] ob = (Object[]) dls.get(i);
//				d.setId(Long.valueOf(ob[0].toString()));
//				d.setDeptname(ob[1].toString());
//				aldept.add(d);
//			}
//			
//			request.setAttribute("c", cf);
//			request.setAttribute("aldept", aldept);
//			request.setAttribute("banklist", banklist);
//			request.setAttribute("sortlist", sortlist);			
//			request.setAttribute("linkmanlist", linkmanlist);
//			request.setAttribute("als", als);
//			request.setAttribute("cals", cals);
//
//			return mapping.findForward("toupd");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return mapping.getInputForward();
//	}
//
//}
