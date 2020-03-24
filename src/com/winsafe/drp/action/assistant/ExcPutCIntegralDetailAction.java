package com.winsafe.drp.action.assistant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppViewCIntegralDeal;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.ViewCIntegralDetail;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;

public class ExcPutCIntegralDetailAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
//		Long userid = users.getUserid();
		try {
			int pagesize = 20;
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = " makeorganid in(" + users.getVisitorgan()
						+ ") ";
			}

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "ViewCIntegralDetail" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap, "makedate");
			String priceCondition = " having 1=1 ";
			String BeginPrice = request.getParameter("BeginPrice");
			String EndPrice = request.getParameter("EndPrice");
			if ( BeginPrice != null && !BeginPrice.equals("") ){
				priceCondition += " and sum(dealintegral)>="+BeginPrice+" ";
			}
			if ( EndPrice != null && !EndPrice.equals("") ){
				priceCondition += " and sum(dealintegral)<="+EndPrice+" ";
			}
			
			String blur = DbUtil.getOrBlur(map, tmpMap, "cid", "cname", "mobile");
			whereSql = whereSql + timeCondition + blur; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			ArrayList str = new ArrayList();

			AppViewCIntegralDeal aso = new AppViewCIntegralDeal();
			AppOrgan ao = new AppOrgan();
			List pils = aso.getViewCIntegralDetail(whereSql, priceCondition);
			for (int i = 0; i < pils.size(); i++) {
				Object[] o = (Object[]) pils.get(i);
				ViewCIntegralDetail sodf = new ViewCIntegralDetail();
				sodf.setCid(String.valueOf(o[0]));
				sodf.setCname(String.valueOf(o[1]));
				sodf.setMobile(String.valueOf(o[2]));
				Organ organ = ao.getOrganByID(String.valueOf(o[3]));
				sodf.setOrganid(organ!=null?organ.getOrganname():"");
				sodf.setXiaofei(o[4]==null?0:Double.valueOf(o[4].toString()));
				sodf.setJibei(o[5]==null?0:Double.valueOf(o[5].toString()));
				sodf.setTuijian(o[6]==null?0:Double.valueOf(o[6].toString()));
				sodf.setDinghua(o[7]==null?0:Double.valueOf(o[7].toString()));
				sodf.setFukuan(o[8]==null?0:Double.valueOf(o[8].toString()));
				sodf.setSonghua(o[9]==null?0:Double.valueOf(o[9].toString()));
				sodf.setDealintegral(o[10]==null?0:Double.valueOf(o[10].toString()));
				str.add(sodf);
			}
			request.setAttribute("str", str);
			
		
		

			List alos = ao.getOrganToDown(users.getMakeorganid());

			request.setAttribute("alos", alos);
			Organ organ = ao.getOrganByID(request.getParameter("organid"));
			request.setAttribute("organid", organ!=null?organ.getOrganname():"");
			request.setAttribute("KeyWord", request.getParameter("KeyWord"));
			request.setAttribute("begindate", request.getParameter("BeginDate"));
			request.setAttribute("enddate", request.getParameter("EndDate"));
			request.setAttribute("beginprice", BeginPrice);
			request.setAttribute("endprice", EndPrice);
			
			return mapping.findForward("show");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
