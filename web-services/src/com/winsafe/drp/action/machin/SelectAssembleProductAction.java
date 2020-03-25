package com.winsafe.drp.action.machin;

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

import com.winsafe.drp.dao.AppAssembleRelation;
import com.winsafe.drp.dao.AssembleRelation;
import com.winsafe.drp.dao.AssembleRelationForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class SelectAssembleProductAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 20;
		try {
			String Condition = " ar.isaudit=1 ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "AssembleRelation" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			// String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");
			String blur = DbUtil.getBlur(map, tmpMap, "KeysContent");

			whereSql = whereSql + blur + Condition;
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppAssembleRelation aar = new AppAssembleRelation();
			List pls = aar.getAssembleRelation(request, pagesize, whereSql);
			ArrayList sls = new ArrayList();
			AssembleRelation ar = null;

			for (int i = 0; i < pls.size(); i++) {
				AssembleRelationForm pf = new AssembleRelationForm();
				ar = (AssembleRelation) pls.get(i);
				pf.setId(ar.getId());
				pf.setArproductid(ar.getArproductid());
				pf.setArproductname(ar.getArproductname());
				pf.setArspecmode(ar.getArspecmode());
				pf.setArunitid(ar.getArunitid());
				pf.setArunitidname(Internation.getStringByKeyPositionDB(
						"CountUnit", ar.getArunitid()));

				sls.add(pf);
			}

			request.setAttribute("sls", sls);
			return mapping.findForward("selectproduct");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
