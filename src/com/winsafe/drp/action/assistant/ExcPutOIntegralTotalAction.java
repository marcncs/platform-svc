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
import com.winsafe.drp.dao.AppViewOIntegralDeal;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.ViewOIntegralDealForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;

public class ExcPutOIntegralTotalAction extends Action {
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
			String[] tablename = { "ViewOIntegralTotal" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap, "makedate");			
			
			String blur = DbUtil.getOrBlur(map, tmpMap, "oid", "organname");
			whereSql = whereSql + timeCondition + blur; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			ArrayList str = new ArrayList();

			AppViewOIntegralDeal aso = new AppViewOIntegralDeal();
			AppOrgan ao = new AppOrgan();
			List pils = aso.getViewOIntegralTotal(whereSql);
			for (int i = 0; i < pils.size(); i++) {
				Object[] o = (Object[]) pils.get(i);
				ViewOIntegralDealForm sodf = new ViewOIntegralDealForm();
				sodf.setOid(String.valueOf(o[0]));
				sodf.setOidname(String.valueOf(o[1]));
				sodf.setDealintegral(o[2]==null?0:Double.valueOf(o[2].toString()));
				sodf.setCompleteintegral(o[3]==null?0:Double.valueOf(o[3].toString()));
				sodf.setTiaozheng(o[4]==null?0:Double.valueOf(o[4].toString()));
				sodf.setLeiji(o[5]==null?0:Double.valueOf(o[5].toString()));
				str.add(sodf);
			}
			request.setAttribute("str", str);
			
		
		

			List alos = ao.getOrganToDown(users.getMakeorganid());

			request.setAttribute("alos", alos);
			Organ organ = ao.getOrganByID(request.getParameter("oid"));
			request.setAttribute("oid", organ!=null?organ.getOrganname():"");
			request.setAttribute("KeyWord", request.getParameter("KeyWord"));
			request.setAttribute("begindate", request.getParameter("BeginDate"));
			request.setAttribute("enddate", request.getParameter("EndDate"));
			
			return mapping.findForward("show");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
