package com.winsafe.drp.action.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPlinkman;
import com.winsafe.drp.dao.AppProvider;
import com.winsafe.drp.dao.Plinkman;
import com.winsafe.drp.dao.Provider;
import com.winsafe.drp.dao.ProviderForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;

public class SelectProvideAndLinkmanAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		super.initdata(request);
		try {
			String Condition = " (p.makeid=" + userid + " "
					+ this.getOrVisitOrgan("p.makeorganid")
					+ ") and p.isdel=0 ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Provider" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String blur = DbUtil.getOrBlur(map, tmpMap, "PID", "PName", "Tel",
					"Mobile","NCcode");

			whereSql = whereSql + blur + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppProvider ap = new AppProvider();
			List pls = ap.getProvider(request, pagesize, whereSql);
			ArrayList sls = new ArrayList();

			AppPlinkman apl = new AppPlinkman();
			for (int i = 0; i < pls.size(); i++) {
				ProviderForm pf = new ProviderForm();
				Provider o = (Provider) pls.get(i);
				pf.setPid(o.getPid());
				pf.setPname(o.getPname());
				pf.setTel(o.getTel());
				List lms = apl.getLinkmanByPid(o.getPid());
				if (lms != null) {
					Plinkman lm = (Plinkman) lms.get(0);
					pf.setPlinkman(lm.getName());
					pf.setPlinkmantel(lm.getMobile());
					pf.setPlinkmanaddr(pf.getAddr());
				}
				sls.add(pf);
			}

			request.setAttribute("sls", sls);
			return mapping.findForward("selectprovide");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
