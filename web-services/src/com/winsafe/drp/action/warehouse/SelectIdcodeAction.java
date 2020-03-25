package com.winsafe.drp.action.warehouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.Idcode;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class SelectIdcodeAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 5;
		String pid = request.getParameter("pid");
		String batch = "";
		UsersBean users = UserManager.getUser(request);
//		Long userid = users.getUserid();

		super.initdata(request);try{
			String Condition = " isuse=0 and productid='"+pid+"' and batch='"+batch+"' ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Idcode" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			// String blur = DbUtil.getBlur(map, tmpMap, "CName");

			whereSql = whereSql + Condition;
			whereSql = DbUtil.getWhereSql(whereSql); 
			Object obj[] = (DbUtil.setPager(request, "Idcode", whereSql,
					pagesize));

			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];
			AppIdcode ac = new AppIdcode();
//			List pls = ac.searchIdcode(pagesize, whereSql, tmpPgInfo);
			ArrayList sls = new ArrayList();

			AppProduct ap = new AppProduct();
			Idcode code = null;
//			for (int i = 0; i < pls.size(); i++) {
//				code = (Idcode) pls.get(i);
//				IdcodeForm f = new IdcodeForm();
//				f.setId(code.getId());
//				f.setProductid(code.getProductid());
//				f.setProductidname(ap.getProductByID(code.getProductid())
//						.getProductname());
//				f.setIdcode(code.getIdcode());
//				sls.add(f);
//			}
			request.setAttribute("pid", pid);
			request.setAttribute("batch", batch);
			request.setAttribute("sls", sls);
			return mapping.findForward("selectidcode");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
