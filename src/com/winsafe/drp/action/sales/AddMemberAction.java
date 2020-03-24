package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCAddr;
import com.winsafe.drp.dao.AppCTitle;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppLinkMan;
import com.winsafe.drp.dao.AppMemberGrade;
import com.winsafe.drp.dao.Customer;
import com.winsafe.drp.dao.Linkman;
import com.winsafe.drp.server.MsgService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.PYCode;
import com.winsafe.hbm.util.StringFilters;

public class AddMemberAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			AppCustomer appCustomer = new AppCustomer();

			
			initdata(request);

			Customer c = new Customer();
			AppMemberGrade amg = new AppMemberGrade(); 
			
			String cid =request.getParameter("cid");
		//	appCustomer.getCustomerByCcode(cid)
			c.setCid(cid);
			c.setCname(request.getParameter("cname"));
			c.setCpycode(PYCode.getSampCode(c.getCname()));
			c.setCardno(request.getParameter("cardno"));
			c.setParentid(request.getParameter("parentid"));
			c.setPwd(request.getParameter("pwd"));
			
			c.setMembersex(Integer.valueOf(request.getParameter("membersex")));
			String strmbirthday = request.getParameter("memberbirthday");
			if(!strmbirthday.equals("")){
			c.setMemberbirthday(DateUtil.StringToDate(strmbirthday));
			}
			c.setMemberidcard(request.getParameter("memberidcard"));
			c.setMembercompany(request.getParameter("membercompany"));
			c.setMemberduty(request.getParameter("memberduty"));
			String tickettitle=request.getParameter("tickettitle");
			c.setTickettitle(tickettitle);
			c.setPrompt(Integer.valueOf(request.getParameter("prompt")));
			c.setCreditlock(Double.valueOf(request.getParameter("creditlock")));
			c.setRate(Integer.valueOf(request.getParameter("rate")));
			c.setPolicyid(amg.getMemberGradeByID(Integer.valueOf(c.getRate())).getPolicyid());
			c.setDiscount(Double.valueOf(request.getParameter("discount")));
			c.setTaxrate(Double.valueOf(request.getParameter("taxrate")));
			c.setSource(Integer.valueOf(request.getParameter("source")));
			c.setTransportmode(0);
			String province = request.getParameter("province");
			if (province != null&& !province.equals("")) {
				c.setProvince(Integer.valueOf(province));
			} else {
				c.setProvince(0);
			}
			String city = request.getParameter("city");
			if (city != null&& !city.equals("")) {
				c.setCity(Integer.valueOf(city));
			} else {
				c.setCity(0);
			}
			String areas = request.getParameter("areas");
			if (areas != null&& !areas.equals("")) {
				c.setAreas(Integer.valueOf(areas));
			} else {
				c.setAreas(0);
			}
			String commaddr=request.getParameter("commaddr");
			c.setCommaddr(commaddr);
			String detailaddr = request.getParameter("detailaddr");
			c.setDetailaddr(detailaddr);
			c.setPostcode(request.getParameter("postcode"));
			c.setSendpostcode(request.getParameter("sendpostcode"));
			c.setRemark(request.getParameter("remark"));
			c.setMobile(request.getParameter("mobile"));
			c.setOfficetel(request.getParameter("officetel"));
			
			c.setEmail(request.getParameter("email"));
			
			c.setMakedate(DateUtil.getCurrentDate());
			c.setMakeorganid(users.getMakeorganid());
			c.setMakedeptid(users.getMakedeptid());
			c.setMakeid(userid);
			c.setUpdid(userid);
			c.setUpddate(DateUtil.StringToDatetime(DateUtil
					.getCurrentDateTime()));
			c.setSpecializeid(0);
			c.setIsdel(0);
			c.setIsreceivemsg(Integer.valueOf(request.getParameter("isreceivemsg")));
			c.setIsactivate(1);
			c.setActivateid(userid);
			c.setActivatedate(DateUtil.StringToDatetime(DateUtil
					.getCurrentDateTime()));

			appCustomer.addCustomer(c);
			
			String strname[] = request.getParameterValues("name");
		    String strsex[] = request.getParameterValues("sex");
		    String strbirthday[] = request.getParameterValues("birthday");
		    String sttduty[] = request.getParameterValues("duty");
		    String strismain[] = request.getParameterValues("ismain");
		    String strlinkofficetel[] = request.getParameterValues("linkofficetel");
		    String strlinkmobile[] = request.getParameterValues("linkmobile");
		    String strhometel[] = request.getParameterValues("hometel");
		    String strlinkemail[] = request.getParameterValues("linkemail");
		    String strqq[] = request.getParameterValues("qq");
		    String strmsn[] = request.getParameterValues("msn");
		    String strlkaddr[] = request.getParameterValues("lkaddr");
			
		    AppLinkMan applinkman = new AppLinkMan();
		    
		    

			Linkman linkman = null;
			linkman = new Linkman();
			linkman.setId(Integer.valueOf(MakeCode
					.getExcIDByRandomTableName("linkman", 0, "")));
			linkman.setCid(cid);
			linkman.setName(request.getParameter("cname"));
			linkman.setSex(Integer.parseInt(request.getParameter("membersex")));
			linkman.setIdcard("");
			linkman.setBirthday(DateUtil.StringToDate(strmbirthday));
			linkman.setDepartment("");
			linkman.setDuty(request.getParameter("memberduty"));
			linkman.setOfficetel(request.getParameter("officetel"));
			linkman.setMobile(request.getParameter("mobile"));
			linkman.setHometel(request.getParameter("officetel"));
			linkman.setEmail(request.getParameter("email"));
			linkman.setQq("");
			linkman.setMsn("");
			linkman.setAddr(request.getParameter("commaddr"));
			linkman.setIsmain(Integer.valueOf(request.getParameter("ismain")));
			applinkman.addLinkman(linkman);
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
					//linkman.setTransit(vc.getTransit()[i]);
					applinkman.addLinkman(linkman);
				}
			}
			
			AppCAddr ca = new AppCAddr();
			Integer caid=null;
			if(detailaddr!=null&&!detailaddr.equals("")){
			caid= Integer.valueOf(MakeCode.getExcIDByRandomTableName("c_addr",0,""));
			ca.addCAddrNoExists(caid, cid, StringFilters.CommaToDot(detailaddr));
			}
			if(commaddr!=null&&!commaddr.equals("")){
			caid= Integer.valueOf(MakeCode.getExcIDByRandomTableName("c_addr",0,""));
			ca.addCAddrNoExists(caid, cid, StringFilters.CommaToDot(commaddr));
			}
			
			AppCTitle act = new AppCTitle();
			Integer ctid = null;
			if(tickettitle!=null&&!tickettitle.equals("")){
				ctid = Integer.valueOf(MakeCode.getExcIDByRandomTableName("c_title",0,""));
				act.addCTitleNoExists(ctid, cid, tickettitle);
			}
			
			
			int sendmsg = Integer.valueOf(request.getParameter("sendmsg"));
			int autosend = Integer.valueOf(request.getParameter("autosend"));
			if ( sendmsg == 1 ){				
				String[] param = new String[]{"name"};
				String[] values = new String[]{c.getCname()};
				MsgService ms = new MsgService(param, values, users, 1);
				ms.addmag(autosend, c.getMobile());				
			}

			request.setAttribute("result", "databases.add.success");
			request.setAttribute("gourl", "../sales/toAddMemberAction.do");
			
			//返回呼叫中心
			request.setAttribute("op", request.getParameter("op"));
			request.setAttribute("calltel", request.getParameter("calltel"));
			request.setAttribute("cid", cid);
			
			DBUserLog.addUserLog(userid,5, "会员/积分管理>>新增会员资料,编号："+cid);
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.getInputForward();
	}



}
