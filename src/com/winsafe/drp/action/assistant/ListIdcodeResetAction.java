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

import com.winsafe.drp.dao.AppDept;
import com.winsafe.drp.dao.AppIdcodeReset;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.IdcodeReset;
import com.winsafe.drp.dao.IdcodeResetForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class ListIdcodeResetAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 20;

		String isauditselect = Internation.getSelectTagByKeyAll("YesOrNo",
				request, "IsAudit", true, null);		

		UsersBean users = UserManager.getUser(request);
//		Long userid = users.getUserid();

		try {
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = " or makeorganid in(" + users.getVisitorgan()
						+ ")";
			}
//			String Condition = " (makeid=" + userid + " " + visitorgan + ") ";

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "IdcodeReset" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			//String blur = DbUtil.getOrBlur(map, tmpMap, "MobileNO");

//			whereSql = whereSql + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			Object obj[] = DbUtil.setDynamicPager(request, "IdcodeReset", whereSql,
					pagesize, "IdcodeReset");
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];

			AppUsers au = new AppUsers();
			AppOrgan ao = new AppOrgan();
			AppDept ad = new AppDept();
			AppIdcodeReset asb = new AppIdcodeReset();
			List pils = asb.getIdcodeReset(pagesize, whereSql, tmpPgInfo);
			ArrayList alsb = new ArrayList();
			IdcodeReset sb = null;
			for (int i = 0; i < pils.size(); i++) {
				sb = (IdcodeReset) pils.get(i);
				IdcodeResetForm sbf = new IdcodeResetForm();
				sbf.setId(sb.getId());
				sbf.setMemo(sb.getMemo());
				sbf.setIsauditname(Internation.getStringByKeyPosition(
						"YesOrNo", request, sb.getIsaudit(),
						"global.sys.SystemResource"));
				sbf.setMakeorganidname(ao.getOrganByID(sb.getMakeorganid())
						.getOrganname());
				if(sb.getMakedeptid()>0){
//				sbf.setMakedeptidname(ad.getDeptByID(sb.getMakedeptid())
//						.getDeptname());
				}else{
					sbf.setMakedeptidname("");
				}
//				sbf.setMakeidname(au.getUsersByid(sb.getMakeid())
//						.getRealname());
				sbf.setMakedate(DateUtil.formatDateTime(sb.getMakedate()));				
				alsb.add(sbf);
			}

			List als = au.getIDAndLoginNameByOID2(users.getMakeorganid());
			List ols = ao.getOrganToDown(users.getMakeorganid());
			List dls = ad.getDeptByOID(users.getMakeorganid());

			request.setAttribute("ols", ols);
			request.setAttribute("dls", dls);
			request.setAttribute("als", als);

			request.setAttribute("alsb", alsb);
			request.setAttribute("isauditselect", isauditselect);

//			DBUserLog.addUserLog(userid, "列表序号重置信息");// 日志
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
