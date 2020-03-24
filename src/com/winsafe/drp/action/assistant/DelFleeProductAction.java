package com.winsafe.drp.action.assistant;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppFleeProduct;
import com.winsafe.drp.dao.FleeProduct;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.CountryAreaService;
import com.winsafe.drp.server.HtmlTemplateGenerator;
import com.winsafe.drp.server.SendMailService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;

public class DelFleeProductAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {

			int id = Integer.valueOf(request.getParameter("ID"));
			AppFleeProduct appFleeProduct = new AppFleeProduct();
			 FleeProduct sb = appFleeProduct.getFleeProductByID(id);
					
			 if(sb.getIsdeal()==1){
				 request.setAttribute("result", "对不起，该记录已处理，不能重复操作！");
				 return new ActionForward("/sys/lockrecordclose2.jsp");
			 }

			sb.setIsdeal(1);
			appFleeProduct.updFleeProduct(sb);
			//发邮件
			UsersService us = new UsersService();
			Users u = us.getUsersByid(sb.getMakeid());
			String path = request.getRealPath("WEB-INF\\mail");
			CountryAreaService cas = new CountryAreaService();
			HtmlTemplateGenerator temp = new HtmlTemplateGenerator(path);
			Map map = new HashMap();
			map.put("id", sb.getQueryid());
			map.put("makename", u.getRealname());
			map.put("province", cas.getCountryAreaName(sb.getProvince()));
			map.put("findprovince", cas.getCountryAreaName(sb.getFindprovince()));
			map.put("cname", sb.getCname());
			map.put("syncode", sb.getSyncode());
			map.put("productname", sb.getProductname());
			map.put("specmode", sb.getSpecmode());
			map.put("productid", sb.getProductid());
			map.put("outdate", DateUtil.formatDate(sb.getSenddate()));
			map.put("startno", sb.getStartno());
			map.put("endno", sb.getEndno());
			map.put("querydate", DateUtil.formatDate(sb.getMakedate()));				
			
			String content = temp.create("mailwlmidcode.ftl", map);
			String title = "查询编号为:"+sb.getQueryid()+"的窜货信息已删除!";
			String[] tomail = new String[]{u.getEmail()};
			SendMailService sms = new SendMailService(title, tomail, content);
			sms.sendmail();

			request.setAttribute("result", "databases.del.success");
			UsersBean users = UserManager.getUser(request);
			DBUserLog.addUserLog(users.getUserid(), 1, "删除可疑窜货,编号："+id,sb);

			return mapping.findForward("del");
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

}
