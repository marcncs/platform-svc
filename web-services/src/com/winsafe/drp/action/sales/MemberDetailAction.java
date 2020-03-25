package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppMemberGrade;
import com.winsafe.drp.dao.Customer;
import com.winsafe.drp.dao.CustomerForm;
import com.winsafe.drp.server.CountryAreaService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;

public class MemberDetailAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String cid = request.getParameter("Cid");
		try {
			AppCustomer appCustomer = new AppCustomer();
			CountryAreaService aca = new CountryAreaService();
			UsersService au = new UsersService();
			AppMemberGrade amg = new AppMemberGrade();

			Customer c = appCustomer.getCustomer(cid);
			CustomerForm cf = new CustomerForm();
			cf.setCid(c.getCid());
			cf.setCname(c.getCname());
			cf.setMembersexname(Internation.getStringByKeyPosition("Sex",
					request, c.getMembersex(), "global.sys.SystemResource"));
			cf.setCardno(c.getCardno());
			Customer pc = appCustomer.getCustomer(c.getParentid());
			cf.setParentid(pc!=null?pc.getCname():"");
			cf.setMemberbirthday(c.getMemberbirthday());
			cf.setMemberidcard(c.getMemberidcard());
			cf.setMembercompany(c.getMembercompany());
			cf.setMemberduty(c.getMemberduty());
			cf.setTickettitle(c.getTickettitle());
			cf.setCreditlock(c.getCreditlock());
			cf.setPrompt(c.getPrompt());
			cf.setRatename(amg.getMemberGradeByID(c.getRate()).getGradename());
			cf.setDiscount(c.getDiscount());
			cf.setTaxrate(c.getTaxrate());
			cf.setSourcename(Internation.getStringByKeyPositionDB("Source", c
					.getSource()));
			cf.setProvincename(aca.getCountryAreaName(c.getProvince()));
			cf.setCityname(aca.getCountryAreaName(c.getCity()));
			cf.setAreasname(aca.getCountryAreaName(c.getAreas()));
			cf.setCommaddr(c.getCommaddr());
			cf.setDetailaddr(c.getDetailaddr());
			cf.setPostcode(c.getPostcode());
			cf.setSendpostcode(c.getSendpostcode());
			cf.setPwd(c.getPwd());
			cf.setRemark(c.getRemark());
			cf.setOfficetel(c.getOfficetel());
			cf.setMobile(c.getMobile());
			cf.setEmail(c.getEmail());
			cf.setMakedate(c.getMakedate().toString());
			cf.setMakeorganid(c.getMakeorganid());
			cf.setMakedeptid(c.getMakedeptid());
			cf.setMakeid(c.getMakeid());
			cf.setMakeidname(au.getUsersName(c.getMakeid()));
			cf.setIsactivate(c.getIsactivate());
			cf.setIsactivate(c.getIsactivate());
			cf.setIsactivatename(Internation.getStringByKeyPosition("YesOrNo",
					request, c.getIsactivate(), "global.sys.SystemResource"));
			cf.setActivateidname(au.getUsersName(c.getActivateid()));
			cf.setActivatedate(DateUtil.formatDate(c.getActivatedate()));

			request.getSession().setAttribute("cid", cid);
			request.setAttribute("cf", cf);
			return mapping.findForward("info");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.getInputForward();
	}

}
