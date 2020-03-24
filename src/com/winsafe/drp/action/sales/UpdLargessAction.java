package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppLargess;
import com.winsafe.drp.dao.Largess;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.RequestTool;

public class UpdLargessAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		
		initdata(request);

		AppLargess ah = new AppLargess();

		try {
			Largess h = ah.getLargessByID(Integer.valueOf(request.getParameter("id")));
			Largess oldh = (Largess) BeanUtils.cloneBean(h);
			h.setObjsort(RequestTool.getInt(request, "objsort"));
			h.setCid(request.getParameter("cid"));
			h.setCname(RequestTool.getString(request, "cname"));
			h.setLargessname(RequestTool.getString(request, "largessname"));
			h.setLargessdescribe(request.getParameter("largessdescribe"));
			h.setLfee(Double.valueOf(request.getParameter("lfee")));
			h.setLdate(RequestTool.getDate(request, "ldate"));
			h.setMemo(request.getParameter("memo"));
			ah.updLargess(h);

			DBUserLog.addUserLog(userid,4, "渠道管理>>修改赠品,编号：" + h.getId(),oldh,h);
			// con.commit();
			request.setAttribute("result", "databases.upd.success");
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.getInputForward();
	}

}
