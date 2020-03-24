package com.winsafe.drp.action.sales;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCIntegral;
import com.winsafe.drp.dao.AppCountryArea;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.CountryArea;
import com.winsafe.drp.dao.Customer;
import com.winsafe.drp.dao.CustomerForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class ListNoMemberAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		int pagesize = 10;
		super.initdata(request);
		try {
			AppCustomer ac = new AppCustomer();

			String Condition = " (c.makeid="+userid+" "+getOrVisitOrgan("c.makeorganid")+") and c.isdel=0 and c.rate = 0";
			String tel = request.getParameter("tel");
			if ( tel != null && "0".equals(tel) ){
				Condition = Condition + " and c.mobile!='' ";
			}
			if ( tel != null && "1".equals(tel) ){
				Condition = Condition + " and c.mobile='' ";
			}
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = {"Customer"};
			String whereSql = EntityManager.getTmpWhereSql(map, tablename); 
			String blur = DbUtil.getOrBlur(map, tmpMap, "CID","CName", "CPYCode","Mobile");
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,"MakeDate");
			whereSql = whereSql + blur + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			

			List usList = ac.searchCustomer(request,pagesize, whereSql);
			ArrayList customerList = new ArrayList();
			
			AppCIntegral appci = new AppCIntegral();
			AppOrgan ao = new AppOrgan();

			for (int t = 0; t < usList.size(); t++) {
				CustomerForm cf = new CustomerForm();
				Customer c = (Customer) usList.get(t);
				cf.setCid(c.getCid());
				cf.setCname(c.getCname());
				cf.setMembersexname(Internation.getStringByKeyPosition(
						"Sex", request, c.getMembersex(), "global.sys.SystemResource"));
				cf.setMembercompany(c.getMembercompany());
				cf.setMobile(c.getMobile());
				cf.setOfficetel(c.getOfficetel());
				cf.setSourcename(Internation.getStringByKeyPositionDB("Source",
						c.getSource()));
				cf.setMakedate(DateUtil.formatDateTime(c.getMakedate()));				
				cf.setTotalintegral(appci.getCIntegralByCID(cf.getCid()));
				cf.setMakeorganid(c.getMakeorganid());
				//System.out.println("++++"+cf.getTotalintegral());

				customerList.add(cf);
			}
//			}

			AppCountryArea aca = new AppCountryArea();
			List list = aca.getProvince();

			ArrayList cals = new ArrayList();

			for (int i = 0; i < list.size(); i++) {
				CountryArea ca = new CountryArea();
				Object ob[] = (Object[]) list.get(i);
				ca.setId(Integer.valueOf(ob[0].toString()));
				ca.setAreaname(ob[1].toString());
				ca.setParentid(Integer.valueOf(ob[2].toString()));
				ca.setRank(Integer.valueOf(ob[3].toString()));
				cals.add(ca);
			}
			String sexname = Internation.getSelectTagByKeyAll("Sex",
					request, "MemberSex", true, null);
			String sourcename = Internation.getSelectTagByKeyAllDB("Source",
					"Source", true);
			AppUsers au = new AppUsers();
			List ols = ao.getOrganToDown(users.getMakeorganid());		    
		    List uls = au.getIDAndLoginNameByOID2(users.getMakeorganid());

				

			
			String isactivateselect = Internation.getSelectTagByKeyAll(
					"YesOrNo", request, "IsActivate", true, null);

			request.setAttribute("isactivateselect", isactivateselect);

			request.setAttribute("cals", cals);
			request.setAttribute("usList", customerList);
			request.setAttribute("ols",ols);
			request.setAttribute("uls",uls);
			request.setAttribute("sexname",sexname);
			request.setAttribute("sourcename",sourcename);
			request.setAttribute("province",request.getParameter("Province"));
			request.setAttribute("city",request.getParameter("City"));
			request.setAttribute("areas",request.getParameter("Areas"));
			request.setAttribute("IsActivate",request.getParameter("IsActivate"));
			request.setAttribute("Source",request.getParameter("Source"));
			request.setAttribute("MemberSex",request.getParameter("MemberSex"));
			request.setAttribute("MakeOrganID",request.getParameter("MakeOrganID"));
			request.setAttribute("MakeID",request.getParameter("MakeID"));
			request.setAttribute("tel",request.getParameter("tel"));
			request.setAttribute("BeginDate",request.getParameter("BeginDate"));
			request.setAttribute("EndDate",request.getParameter("EndDate"));
			request.setAttribute("KeyWord",request.getParameter("KeyWord"));
			DBUserLog.addUserLog(userid,5,"会员/积分管理>>列表会员资料");
			return mapping.findForward("mlist");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

}
