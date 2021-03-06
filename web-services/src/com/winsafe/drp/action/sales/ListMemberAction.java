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
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.Customer;
import com.winsafe.drp.dao.CustomerForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.CountryAreaService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class ListMemberAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		int pagesize = 10;
		super.initdata(request);
		try {
			AppCustomer ac = new AppCustomer();

			String Condition = " (c.makeid="+userid+" "+getOrVisitOrgan("c.makeorganid")+") and c.isdel=0 ";
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
				cf.setMakeorganid(c.getMakeorganid());

				customerList.add(cf);
			}
//			}

			CountryAreaService aca = new CountryAreaService();
			List list = aca.getProvinceObj();
			request.setAttribute("cals", list);
			request.setAttribute("usList", customerList);
			
			request.setAttribute("Province",request.getParameter("Province"));
			request.setAttribute("CityName",aca.getCountryAreaName(getInt("City")));
			request.setAttribute("City",request.getParameter("City"));
			request.setAttribute("AreasName",aca.getCountryAreaName(getInt("Areas")));
			request.setAttribute("Areas",request.getParameter("Areas"));
		
			
			DBUserLog.addUserLog(userid,5,"会员/积分管理>>列表会员资料");
			return mapping.findForward("mlist");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

}
