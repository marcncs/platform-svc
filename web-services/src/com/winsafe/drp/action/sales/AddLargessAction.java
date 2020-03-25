package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppLargess;
import com.winsafe.drp.dao.Largess;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class AddLargessAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		
		initdata(request);
		AppLargess al = new AppLargess();

		try {

			String cid = request.getParameter("cid");
			Largess h = new Largess();
			h.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
					"largess", 0, "")));
			h.setCid(cid);
			h.setObjsort(RequestTool.getInt(request, "objsort"));
			h.setCname(RequestTool.getString(request, "cname"));
			h.setLargessname(RequestTool.getString(request, "largessname"));
			h.setLargessdescribe(request.getParameter("largessdescribe"));
			h.setLfee(Double.valueOf(request.getParameter("lfee")));
			h.setLdate(RequestTool.getDate(request, "ldate"));
			h.setMemo(request.getParameter("memo"));
			h.setMakeorganid(users.getMakeorganid());
			h.setMakedeptid(users.getMakedeptid());
			h.setMakeid(userid);
			h.setMakedate(DateUtil.StringToDatetime(DateUtil
					.getCurrentDateTime()));

			al.addLargess(h);

			DBUserLog.addUserLog(userid, 4, "渠道管理>>新增赠品,编号：" + h.getId());

			request.setAttribute("result", "databases.add.success");
			return mapping.findForward("success");
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.getInputForward();
	}

}
