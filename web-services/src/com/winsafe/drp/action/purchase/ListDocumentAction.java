package com.winsafe.drp.action.purchase;

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
import com.winsafe.hbm.util.DbUtil;

public class ListDocumentAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		AppCorrelationDocument acd = new AppCorrelationDocument();
		Map map = new HashMap(request.getParameterMap());
		Map tmpMap = EntityManager.scatterMap(map);
		super.initdata(request);
		String strpid = request.getParameter("Cid");
		if (strpid == null) {
			strpid = (String) request.getSession().getAttribute("pid");
		}
		String pid = strpid;

		int pagesize = 5;
		// String sql = "select * from correlation_document ";

		try {
			//HibernateUtil.currentSession(true);			
			String condition = " c.cid='" + pid + "' and c.makeid=" + userid
					+ " and c.objsort=2";
			String[] tablename = { "CorrelationDocument" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename); 
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					"MakeDate"); 
			String blur = DbUtil.getBlur(map, tmpMap, "DocumentName"); 
			whereSql = whereSql + timeCondition + blur + condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			// --------------保持查询条件--------------------------------------

			List usList = acd.searchCorrelationDocument(request, pagesize,
					whereSql);
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
				cdf.setDescription(ob[9].toString());
				documentList.add(cdf);

			}

			request.getSession().setAttribute("cid", strpid);
			request.setAttribute("usList", documentList);

			DBUserLog.addUserLog(userid,2, "显示供应商文档");
			return mapping.findForward("documentlist");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.getInputForward();
	}

}
