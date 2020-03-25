package com.winsafe.drp.action.sales;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
import com.winsafe.drp.dao.AppCorrelationDocument;
import com.winsafe.drp.dao.CorrelationDocumentForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.RequestTool;

public class ListDocumentAllAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Map map = new HashMap(request.getParameterMap());
		Map tmpMap = EntityManager.scatterMap(map);
		Integer objsort = RequestTool.getInt(request, "objsort");
		int pagesize = 10;

		try {
			HibernateUtil.currentSession(true);
			
			initdata(request);
			String Condition = " c.makeid=" + userid + " and c.objsort= " +objsort;
			String[] tablename = { "CorrelationDocument" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename); 
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					"MakeDate"); 
			String blur = DbUtil.getBlur(map, tmpMap, "DocumentName"); 
			whereSql = whereSql + timeCondition + blur + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			
			AppCorrelationDocument appCorrelationDocument = new AppCorrelationDocument();
			List usList = appCorrelationDocument.searchCorrelationDocument(request,
					pagesize, whereSql);
			ArrayList documentList = new ArrayList();
			
			for (int t = 0; t < usList.size(); t++) {
				CorrelationDocumentForm cdf = new CorrelationDocumentForm();
				Object[] ob = (Object[]) usList.get(t);
				cdf.setId(Integer.valueOf(ob[0].toString()));
				cdf.setObjsort(Integer.valueOf(ob[1].toString()));
				cdf.setCid(ob[2].toString());
				cdf.setCname(ob[3].toString());
				cdf.setDocumentname(ob[4].toString());
				cdf.setRealpathname(ob[5].toString());
				cdf.setMakeorganid(ob[6].toString());
				cdf.setMakedate((Date) ob[7]);
				cdf.setMakeid(Integer.valueOf(ob[8].toString()));

				documentList.add(cdf);

			}
			request.setAttribute("objsort", objsort);
			request.setAttribute("usList", documentList);

			DBUserLog.addUserLog(userid,5 ,"会员/积分管理>>列表客户文档");
			return mapping.findForward("documentlist");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.getInputForward();
	}

}
