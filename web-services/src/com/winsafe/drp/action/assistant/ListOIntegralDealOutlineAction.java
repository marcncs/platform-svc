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
import com.winsafe.drp.dao.AppViewOIntegralDeal;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.ViewOIntegralDeal;
import com.winsafe.drp.dao.ViewOIntegralDealForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class ListOIntegralDealOutlineAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		AppViewOIntegralDeal acid = new AppViewOIntegralDeal();
		AppOrgan ao = new AppOrgan();

		int pagesize = 20;
		try {
			UsersBean users = UserManager.getUser(request);
//			Long userid = users.getUserid();
			//String Condition = " c.makeid like '" + userid + "%' ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = {"ViewOIntegralDeal"};
			String whereSql = EntityManager.getTmpWhereSql(map, tablename); 

//			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
//					"HapBegin"); 
			 String blur = DbUtil.getBlur(map, tmpMap, "BillNo");
			// 
			whereSql = whereSql + blur; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			Object obj[] = DbUtil.setPager(request, " ViewOIntegralDeal as c", whereSql,
					pagesize);
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];

			List usList = acid.getViewOIntegralDeal(pagesize, whereSql, tmpPgInfo);
			ArrayList hList = new ArrayList();
			for (int t = 0; t < usList.size(); t++) {
				ViewOIntegralDealForm cdf = new ViewOIntegralDealForm();
				// customer=(Customer)usList.get(t);
				ViewOIntegralDeal o = (ViewOIntegralDeal) usList.get(t);
				//cdf.setId(o.getId());
				cdf.setBillno(o.getVoidPK().getBillno());
				cdf.setOidname(ao.getOrganByID(o.getVoidPK().getOid()).getOrganname());
				cdf.setOid(o.getVoidPK().getOid());
				//cdf.setIsortname(Internation.getStringByKeyPositionDB("ISort",o.getIsort()));
				cdf.setDealintegral(o.getDealintegral());
				cdf.setCompleteintegral(o.getCompleteintegral());
				cdf.setMakedate(String.valueOf(o.getMakedate()));
				hList.add(cdf);

			}

			request.setAttribute("hList", hList);
			
			List ols = ao.getOrganToDown(users.getMakeorganid());
			request.setAttribute("ols",ols);
			
//			String isortselect = Internation.getSelectTagByKeyAllDB("ISort",
//					 "ISort", true);
//


//			DBUserLog.addUserLog(userid, "列表会员积分");// 日志
			return mapping.findForward("list");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

}
