package com.winsafe.drp.action.sales;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppBank;
import com.winsafe.drp.dao.AppCountryArea;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppCustomerSort;
import com.winsafe.drp.dao.AppDept;
import com.winsafe.drp.dao.AppDitch;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Customer;
import com.winsafe.drp.dao.CustomerForm;
import com.winsafe.drp.dao.CustomerSort;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;

public class CustomerDetailAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String cid = request.getParameter("Cid");
		
//		Long userid = users.getUserid();
		try {
			AppCustomer appCustomer = new AppCustomer();
			AppBank appbank = new AppBank();
			AppCountryArea aca = new AppCountryArea();
			AppCustomerSort appcs = new AppCustomerSort();
			AppUsers au = new AppUsers();
			AppDept appd = new AppDept();
			AppDitch ad = new AppDitch();

			Customer c = appCustomer.getCustomer(cid);
			CustomerForm cf = new CustomerForm();
			cf.setCid(c.getCid());
			cf.setCname(c.getCname());
			cf.setPrompt(c.getPrompt());
			cf.setCreditlock(c.getCreditlock());
			CustomerSort cs = null;
			if (c.getSort() != null)
				cs = appcs.getCustomerSortById(new Long(c.getSort()));
			if (cs != null)
				cf.setSortname(cs.getSortname());
			cf.setRatename(Internation.getStringByKeyPositionDB("PricePolicy",
					c.getRate()));
			cf.setDiscount(c.getDiscount());
			cf.setTaxrate(c.getTaxrate());

			cf.setVocationname(Internation.getStringByKeyPositionDB("Vocation",
					c.getVocation()));
			if (c.getCphoto() == null || c.getCphoto().equals("")) {
				cf.setCphoto("picture/nophoto.jpg");
			} else {
				cf.setCphoto(c.getCphoto());
			}
//			cf.setSigndate(DateUtil.formatDate(c.getSigndate()));
			cf.setCustomertypename(Internation.getStringByKeyPosition(
					"CustomerType", request, c.getCustomertype(),
					"global.sys.SystemResource"));
			cf.setCustomerstatusname(Internation.getStringByKeyPosition(
					"CustomerStatus", request, c.getCustomerstatus(),
					"global.sys.SystemResource"));
			cf.setYauldname(Internation.getStringByKeyPosition("Yauld",
					request, c.getYauld(), "global.sys.SystemResource"));
			cf.setSourcename(Internation.getStringByKeyPositionDB("Source", c
					.getSource()));
//			if (c.getDitchid() > 0) {
//				cf.setDitchidname(ad.getDitchByID(c.getDitchid()).getDname());
//			} else {
//				cf.setDitchidname("");
//			}
			//String region = "";
			String province = "";
			String city = "";
			String areas = "";
//			if (c.getRegion() > 0) {
//				region = aca
//						.getAreaByID(Long.valueOf(c.getRegion().toString()))
//						.getAreaname();
//			}
//			if (c.getProvince()>0) {
//				province = aca.getAreaByID(
//						Long.valueOf(c.getProvince().toString())).getAreaname();
//			}
//			if (c.getCity() > 0) {
//				city = aca.getAreaByID(Long.valueOf(c.getCity().toString()))
//						.getAreaname();
//			}
//			if (c.getAreas() > 0) {
//				areas = aca.getAreaByID(Long.valueOf(c.getAreas().toString()))
//						.getAreaname();
//			}
//			cf.setRegionname(region);
			cf.setProvincename(province);
			cf.setCityname(city);
			cf.setAreasname(areas);
			cf.setDetailaddr(c.getDetailaddr());
			cf.setPostcode(c.getPostcode());
			cf.setHomepage(c.getHomepage());
			cf.setOfficetel(c.getOfficetel());
			cf.setMobile(c.getMobile());
			cf.setFax(c.getFax());
			cf.setEmail(c.getEmail());
			cf.setTaxcode(c.getTaxcode());
			cf.setRemark(c.getRemark());
			cf.setSpecializeidname(au.getUsersByid(c.getSpecializeid())
					.getRealname());
			cf.setMakeidname(au.getUsersByid(c.getMakeid()).getRealname());
			cf.setLastcontact(String.valueOf(c.getLastcontact()));
			cf.setNextcontact(String.valueOf(c.getNextcontact()));
			cf.setIsactivate(c.getIsactivate());
			cf.setIsactivatename(Internation.getStringByKeyPosition("YesOrNo",
					request, c.getIsactivate(), "global.sys.SystemResource"));
			if (c.getActivateid() != null) {
				cf.setActivateidname(au.getUsersByid(c.getActivateid())
						.getRealname());
			}
			cf.setActivatedate(DateUtil.formatDate(c.getActivatedate()));

			//银行帐户
			List banklist = appbank.getBankByCid(cid);

			request.getSession().setAttribute("cid", cid);
			request.setAttribute("cf", cf);
			request.setAttribute("banklist", banklist);
//			DBUserLog.addUserLog(userid, "客户资料详情");
			return mapping.findForward("info");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.getInputForward();
	}

}
