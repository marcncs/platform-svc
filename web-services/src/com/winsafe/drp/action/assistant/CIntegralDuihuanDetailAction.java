package com.winsafe.drp.action.assistant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
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
import com.winsafe.drp.dao.ViewCIntegralDuihuanDetail;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class CIntegralDuihuanDetailAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		AppViewCIntegralDeal acid = new AppViewCIntegralDeal();
		AppOrgan ao = new AppOrgan();

		int pagesize = 20;
		try {
			UsersBean users = UserManager.getUser(request);
//			Long userid = users.getUserid();
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = {"ViewCIntegralDuihuanDetail"};
			String whereSql = EntityManager.getTmpWhereSql(map, tablename); 

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap, "makedate"); 
			String priceCondition = DbUtil.getPriceCondition(map, tmpMap, "dealintegral"); 
			 String blur = DbUtil.getOrBlur(map, tmpMap, "cid", "cname", "mobile");
			// 
			whereSql = whereSql + blur +timeCondition + priceCondition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			Object obj[] = DbUtil.setPager(request, " ViewCIntegralDuihuanDetail", whereSql,
					pagesize);
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];

			List usList = acid.getViewCIntegralDuihuanDetail(pagesize, whereSql, tmpPgInfo);
			ArrayList hList = new ArrayList();
			for (int t = 0; t < usList.size(); t++) {
				ViewCIntegralDuihuanDetail cdf = (ViewCIntegralDuihuanDetail)usList.get(t);
				Organ organ = ao.getOrganByID(cdf.getOrganid());
				cdf.setOrganid(organ!=null?organ.getOrganname():"");
				cdf.setStrmakedate(DateUtil.formatDateTime(cdf.getMakedate()));
				hList.add(cdf);

			}

			request.setAttribute("hList", hList);
			
			List ols = ao.getOrganToDown(users.getMakeorganid());
			request.setAttribute("ols",ols);
			
			request.setAttribute("organid", request.getParameter("organid"));
			request.setAttribute("KeyWord", request.getParameter("KeyWord"));
			request.setAttribute("begindate", request.getParameter("BeginDate"));
			request.setAttribute("enddate", request.getParameter("EndDate"));
			request.setAttribute("beginprice", request.getParameter("BeginPrice"));
			request.setAttribute("endprice", request.getParameter("EndPrice"));


//			DBUserLog.addUserLog(userid, "会员已兑换积分明细");// 日志
			return mapping.findForward("show");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

}
