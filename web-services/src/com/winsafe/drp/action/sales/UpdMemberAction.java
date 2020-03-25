package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCAddr;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppLinkMan;
import com.winsafe.drp.dao.AppReceivableObject;
import com.winsafe.drp.dao.Customer;
import com.winsafe.drp.dao.Linkman;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.PYCode;

public class UpdMemberAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {

			String cid = request.getParameter("cid");

			AppCustomer appCustomer = new AppCustomer();

			
			initdata(request);
			
			

			Customer c = appCustomer.getCustomer(cid);
			Customer oldc = (Customer) BeanUtils.cloneBean(c);
			c.setCname(request.getParameter("cname"));
			c.setCpycode(PYCode.getSampCode(c.getCname()));
			c.setMembersex(Integer.valueOf(request.getParameter("membersex")));
			c.setMembercompany(request.getParameter("membercompany"));

			c.setMemberduty(request.getParameter("memberduty"));
			c.setTickettitle(request.getParameter("tickettitle"));
			c.setCreditlock(Double.valueOf(request.getParameter("creditlock")));
			c.setPrompt(Integer.valueOf(request.getParameter("prompt")));
			c.setCardno(request.getParameter("cardno"));
			c.setParentid(request.getParameter("parentid"));
			String strmbirthday = request.getParameter("memberbirthday");
			if (!strmbirthday.equals("")) {
				c.setMemberbirthday(DateUtil.StringToDate(strmbirthday));
			}
			c.setMemberidcard(request.getParameter("memberidcard"));
			c.setDiscount(Double.valueOf(request.getParameter("discount")));
			c.setTaxrate(Double.valueOf(request.getParameter("taxrate")));
			c.setSource(Integer.valueOf(request.getParameter("source")));
			String province = request.getParameter("province");
			if (province != null && !province.equals("")) {
				c.setProvince(Integer.valueOf(province));
			} else {
				c.setProvince(0);
			}
			String city = request.getParameter("city");
			if (city != null && !city.equals("")) {
				c.setCity(Integer.valueOf(city));
			} else {
				c.setCity(0);
			}
			String areas = request.getParameter("areas");
			if (areas != null && !areas.equals("")) {
				c.setAreas(Integer.valueOf(areas));
			} else {
				c.setAreas(0);
			}
			String commaddr = request.getParameter("commaddr");
			c.setCommaddr(commaddr);
			String detailaddr = request.getParameter("detailaddr");
			c.setDetailaddr(detailaddr);
			c.setPostcode(request.getParameter("postcode"));
			c.setSendpostcode(request.getParameter("sendpostcode"));
			c.setRemark(request.getParameter("remark"));
			c.setPwd(request.getParameter("pwd"));
			c.setOfficetel(request.getParameter("officetel"));
			c.setMobile(request.getParameter("mobile"));
			c.setEmail(request.getParameter("email"));
			c.setIsreceivemsg(Integer.valueOf(request
					.getParameter("isreceivemsg")));
			c.setUpdid(userid);
			c.setUpddate(DateUtil.getCurrentDate());

			appCustomer.updateCustomer(c);

			
			AppReceivableObject aro = new AppReceivableObject();
			aro.updKeysContent(cid, c.getCid() + "," + c.getCname() + ","
					+ c.getMobile());

			String strname[] = request.getParameterValues("name");
			String strsex[] = request.getParameterValues("sex");
			String strbirthday[] = request.getParameterValues("birthday");
			String sttduty[] = request.getParameterValues("duty");
			String strismain[] = request.getParameterValues("ismain");
			String strlinkofficetel[] = request
					.getParameterValues("linkofficetel");
			String strlinkmobile[] = request.getParameterValues("linkmobile");
			String strhometel[] = request.getParameterValues("hometel");
			String strlinkemail[] = request.getParameterValues("linkemail");
			String strqq[] = request.getParameterValues("qq");
			String strmsn[] = request.getParameterValues("msn");
			String strlkaddr[] = request.getParameterValues("lkaddr");

			AppLinkMan applinkman = new AppLinkMan();
			applinkman.delLinkmanByCid(cid);
			
			Linkman linkman = null;
			if (strname != null) {
				for (int i = 0; i < strname.length; i++) {
					if (!"".equals(strname[i])) {
						linkman = new Linkman();
						linkman.setId(Integer.valueOf(MakeCode
								.getExcIDByRandomTableName("linkman", 0, "")));
						linkman.setCid(cid);
						linkman.setName(strname[i]);
						linkman.setSex(Integer.valueOf(strsex[i]));
						linkman.setIdcard("");
						linkman.setBirthday(DateUtil
								.StringToDate(strbirthday[i]));
						linkman.setDepartment("");
						linkman.setDuty(sttduty[i]);
						linkman.setOfficetel(strlinkofficetel[i]);
						linkman.setMobile(strlinkmobile[i]);
						linkman.setHometel(strhometel[i]);
						linkman.setEmail(strlinkemail[i]);
						linkman.setQq(strqq[i]);
						linkman.setMsn(strmsn[i]);
						linkman.setAddr(strlkaddr[i]);
						linkman.setIsmain(Integer.valueOf(strismain[i]));
						DBUserLog.addUserLog(userid, 5, "会员/积分管理>>新增联系人,编号："
								+ linkman.getId());
						applinkman.addLinkman(linkman);

					}
				}
			}

			AppCAddr ca = new AppCAddr();

			Integer caid = Integer.valueOf(MakeCode.getExcIDByRandomTableName(
					"c_addr", 0, ""));
			ca.addCAddrNoExists(caid, cid, detailaddr);
			caid = Integer.valueOf(MakeCode.getExcIDByRandomTableName("c_addr",
					0, ""));
			ca.addCAddrNoExists(caid, cid, commaddr);

			String forward = "../sales/listCustomerAction.do";
			request.setAttribute("forward", forward);
			request.setAttribute("result", "databases.upd.success");
			DBUserLog.addUserLog(userid, 5, "会员/积分管理>>修改会员资料,编号：" + cid, oldc, c);
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.getInputForward();
	}

	// private String formatCustomerId(Integer areas, String maxid) {
	// String strid = "";
	// if (maxid == null) {
	// strid = "0001";
	// } else {
	// strid = MakeCode.getFormatNums(Integer.parseInt(maxid) + 1, 4);
	// }
	// return areas + strid;
	// }

}
