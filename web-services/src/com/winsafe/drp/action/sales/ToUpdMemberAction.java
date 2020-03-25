package com.winsafe.drp.action.sales;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppCustomerSort;
import com.winsafe.drp.dao.AppLinkMan;
import com.winsafe.drp.dao.AppMemberGrade;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Customer;
import com.winsafe.drp.dao.CustomerForm;
import com.winsafe.drp.dao.Linkman;
import com.winsafe.drp.dao.LinkmanForm;
import com.winsafe.drp.dao.MemberGrade;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.CountryAreaService;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;

public class ToUpdMemberAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {

			AppUsers au = new AppUsers();
			
			String cid=request.getParameter("cid");
			
			initdata(request);
			String mac = "";
			String disk = "009842e29b2e52b00f005583d830c20";
			AppCustomer ac = new AppCustomer();
			AppCustomerSort appcs = new AppCustomerSort();
			Customer c=ac.getCustomer(cid);
			
			CustomerForm cf=new CustomerForm();
			cf.setCid(c.getCid());
			cf.setCname(c.getCname());
			cf.setMembersex(c.getMembersex());
			cf.setMobile(c.getMobile());
			cf.setRemark(c.getRemark());
			cf.setEmail(c.getEmail());
			
			cf.setPwd(c.getPwd());
			cf.setCardno(c.getCardno());
			cf.setParentid(c.getParentid());
			if(cf.getParentid()==null||cf.getParentid().equals("null")||cf.getParentid().equals("")){
				cf.setParentidname("");
			}else{
				try{
				cf.setParentidname(ac.getCustomer(c.getParentid()).getCname());
				}catch (Exception e) {
					cf.setParentidname("");
				}
			}
			cf.setOfficetel(c.getOfficetel());
			cf.setMemberbirthday(c.getMemberbirthday());
			cf.setMemberbirthdayname(DateUtil.formatDate(c.getMemberbirthday()));
			cf.setMemberidcard(c.getMemberidcard());
			cf.setMembercompany(c.getMembercompany());
			cf.setMemberduty(c.getMemberduty());
			cf.setTickettitle(c.getTickettitle());
			cf.setCreditlock(c.getCreditlock());
			cf.setPrompt(c.getPrompt());
			cf.setRatename(Internation.getSelectTagByKeyAllDBDef("PricePolicy", "pricepolicy", c.getRate()==null?1:c.getRate()));
			cf.setPolicyid(c.getPolicyid());
			cf.setCreditlock(c.getCreditlock());
			cf.setPrompt(c.getPrompt());
			cf.setDiscount(c.getDiscount());
			cf.setSource(c.getSource());
			cf.setSourcename(Internation.getSelectTagByKeyAllDBDef("Source", "source", c.getSource()==null?1:c.getSource()));
			cf.setProvince(c.getProvince());
			cf.setCity(c.getCity());
			
			cf.setRate(c.getRate());
			cf.setRatename(Internation.getSelectTagByKeyAllDBDef("PricePolicy", "rate", c.getRate()));
			cf.setAreas(c.getAreas());
			cf.setIsreceivemsgname(Internation.getSelectTagByKeyAll("YesOrNo",
					request, "isreceivemsg", String.valueOf(c.getIsreceivemsg()), null));
			CountryAreaService aca = new CountryAreaService();
			cf.setCityname(aca.getCountryAreaName(c.getCity()));
			cf.setAreasname(aca.getCountryAreaName(c.getAreas()));
			cf.setCommaddr(c.getCommaddr());
			cf.setDetailaddr(c.getDetailaddr());
			cf.setPostcode(c.getPostcode());
			cf.setSendpostcode(c.getSendpostcode());
			cf.setTaxrate(c.getTaxrate());
			
			
			  
			AppLinkMan alm=new AppLinkMan();
			List lmls=alm.getLinkmanByCid(cid);
			List lmfls=new ArrayList();
			for(int i=0 ;i<lmls.size();i++){
				Linkman lm=(Linkman)lmls.get(i);
				LinkmanForm lf=new LinkmanForm();
				lf.setAddr(lm.getAddr());
				lf.setBirthday(lm.getBirthday());
				lf.setCid(lm.getCid());
				lf.setName(lm.getName());
				lf.setDepartment(lm.getDepartment());
				lf.setDuty(lm.getDuty());
				lf.setEmail(lm.getEmail());
				lf.setHometel(lm.getHometel());
				lf.setIdcard(lm.getIdcard());
				lf.setIsmain(lm.getIsmain());
				lf.setIsmainname(Internation.getSelectTagByKeyAll("YesOrNo",request,"ismain",String.valueOf(lm.getIsmain()),null));
				lf.setQq(lm.getQq());
				lf.setMakedate(DateUtil.formatDate(lm.getMakedate()));
				lf.setMobile(lm.getMobile());
				lf.setMsn(lm.getMsn());
				lf.setOfficetel(lm.getOfficetel());
				lf.setSex(lm.getSex());
				lf.setSexname(Internation.getSelectTagByKeyAll("Sex",
						request, "sex", String.valueOf(lm.getSex()), null));
				lf.setStrbirthday(DateUtil.formatDate(lm.getBirthday()));
				lf.setTransit(lm.getTransit());
				lf.setMobile(lm.getMobile());
				lf.setHometel(lm.getHometel());
				lmfls.add(lf);
			}
			if ( lmfls.size() == 0 ){
				lmfls = null;
			}
			
			
			

			 
		      List list = aca.getProvinceObj();

			List uls = au.getIDAndLoginName();
			ArrayList als = new ArrayList();
			for (int u = 0; u < uls.size(); u++) {
				UsersBean ubs = new UsersBean();
				Object[] ub = (Object[]) uls.get(u);
				ubs.setUserid(Integer.valueOf(ub[0].toString()));
				ubs.setRealname(ub[2].toString());
				als.add(ubs);
			}
			
			
			AppMemberGrade amg = new AppMemberGrade();
			List ls = amg.getEnableMemberGradeByVisit(userid);
			ArrayList amgls = new ArrayList();
			for(int m=0;m<ls.size();m++){
				MemberGrade mg = new MemberGrade();
				Object[] og = (Object[])ls.get(m);
				mg.setId(Integer.valueOf(og[0].toString()));
				mg.setGradename(String.valueOf(og[1]));
				amgls.add(mg);
			}
			
	
			request.setAttribute("lmfls", lmfls);
	
			
			request.setAttribute("cf", cf);
			request.setAttribute("als", als);
			request.setAttribute("cals", list);
			request.setAttribute("amgls", amgls);
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
