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

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCorrelationDocument;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.CorrelationDocument;
import com.winsafe.drp.dao.Customer;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DbUtil;

public class ListDocumentAction extends BaseAction {
	Logger logger = Logger.getLogger(ListDocumentAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		AppCustomer appCustomer = new AppCustomer();
		Map map = new HashMap(request.getParameterMap());
		Map tmpMap = EntityManager.scatterMap(map);

		String strCid = request.getParameter("Cid");
		if (strCid == null) {
			strCid = (String) request.getSession().getAttribute("cid");
		}
		String cid = strCid;
		int pagesize = 5;		

		try {
			HibernateUtil.currentSession(true);
			
			initdata(request);
			String Condition = "c.cid='" + cid + "' and c.makeid=" + userid;
			String[] tablename = { "CorrelationDocument" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename); 
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					"UpdateDate"); 
			String blur = DbUtil.getBlur(map, tmpMap, "DocumentName"); 
			whereSql = whereSql + timeCondition + blur + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			
			// --------------保持查询条件--------------------------------------
			String keyword = request.getParameter("KeyWord");
			if (keyword == null) {
				keyword = "";
			}
			request.setAttribute("keyword", keyword);
			// --------------保持查询条件End--------------------------------------
			AppCorrelationDocument appCorrelationDocument = new AppCorrelationDocument();
			List usList = appCorrelationDocument.searchCorrelationDocument(request,pagesize, whereSql);
			ArrayList documentList = new ArrayList();

			for (int t = 0; t < usList.size(); t++) {
				CorrelationDocument correlationDocument = new CorrelationDocument();
				Object[] ob = (Object[]) usList.get(t);
				correlationDocument.setId(Integer.valueOf(ob[0].toString()));
				correlationDocument.setCid(ob[1].toString());
				correlationDocument.setDocumentname(ob[2].toString());
				correlationDocument.setRealpathname(ob[3].toString());
				correlationDocument.setMakedate((Date) ob[4]);
				correlationDocument.setMakeid(Integer.valueOf(ob[5].toString()));

				documentList.add(correlationDocument);

			}

			Customer customer = appCustomer.getCustomer(cid);
			String cname = customer.getCname();
			request.setAttribute("cname", cname);

			request.getSession().setAttribute("cid", strCid);
			request.setAttribute("usList", documentList);

			DBUserLog.addUserLog(userid, 5, "相关文档>>列表相关文档");
			return mapping.findForward("documentlist");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.getInputForward();
	}

}
