package com.winsafe.drp.action.assistant;

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
import com.winsafe.drp.dao.AppCountryArea;
import com.winsafe.drp.dao.AppFleeProduct;
import com.winsafe.drp.dao.AppViewWlmIdcode;
import com.winsafe.drp.dao.AppWlmIdcodeLog;
import com.winsafe.drp.dao.CountryArea;
import com.winsafe.drp.dao.FleeProduct;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.ViewWlmIdcode;
import com.winsafe.drp.dao.WlmIdcodeLog;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.CountryAreaService;
import com.winsafe.drp.server.HtmlTemplateGenerator;
import com.winsafe.drp.server.SendMailService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class ListViewWlmIdcodeMailAction extends BaseAction {
	private AppViewWlmIdcode asb = new AppViewWlmIdcode();
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 20;
		super.initdata(request);
		

		try {
			int province = RequestTool.getInt(request, "province");
			String wlmidcode = request.getParameter("wlmidcode");
			String op = request.getParameter("op");

			String Condition = " '"+wlmidcode+"' between startno and endno ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "ViewWlmIdcode" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);


			whereSql = whereSql + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			
			List pils = asb.getViewWlmIdcode(request, pagesize, whereSql);
	
			request.setAttribute("alsb", pils);
			AppCountryArea aca = new AppCountryArea();
			List list0 = aca.getProvince();
			ArrayList cals = new ArrayList();
			for (int a = 0; a < list0.size(); a++) {
				CountryArea ca = new CountryArea();
				Object ob[] = (Object[]) list0.get(a);
				ca.setId(Integer.valueOf(ob[0].toString()));
				ca.setAreaname(ob[1].toString());
				ca.setParentid(Integer.valueOf(ob[2].toString()));
				ca.setRank(Integer.valueOf(ob[3].toString()));
				cals.add(ca);
			}
			request.setAttribute("cals",cals);
			request.setAttribute("wlmidcode",wlmidcode);
			request.setAttribute("province", province);
		
			
			if ( pils != null && !pils.isEmpty() ){
				//查询日志
				addWlmIdcodeLog(province, wlmidcode, users);
				
				//疑似窜货邮件通知
				if ( op != null && "1".equals(op) ){	
					String path = request.getRealPath("WEB-INF\\mail");
					sendMail(whereSql, wlmidcode, province, users, path);
				}
			}
			
			
			DBUserLog.addUserLog(userid, 1, "列表物流码");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void addWlmIdcodeLog(int province, String wlmidcode, UsersBean users) throws Exception{
		AppWlmIdcodeLog appwl = new AppWlmIdcodeLog();
		WlmIdcodeLog wil = new WlmIdcodeLog();
		wil.setProvince(province);
		wil.setWlmidcode(wlmidcode);
		wil.setMakeorganid(users.getMakeorganid());
		wil.setMakedeptid(users.getMakedeptid());
		wil.setMakeid(users.getUserid());
		wil.setMakedate(DateUtil.getCurrentDate());
		if ( !appwl.isAready(wil) ){
			appwl.insertWlmIdcodeLog(wil);
		}
	}
	
	private void sendMail(String wheresql, String wlmidcode, int province, UsersBean users, String path)throws Exception{
		//wheresql += " and billtype=1 ";
		ViewWlmIdcode vwi = asb.getViewWlmIdcodeByWhere(wheresql);
		if ( vwi != null ){
			if ( vwi.getProvince().intValue() != province ){
				
				System.out.println("你好，你已经疑似窜货，请查询情况！");				
				FleeProduct fp = new FleeProduct();
				fp.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("flee_product", 0, "")));
				fp.setQueryid(DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyyMMdd")+"_"+wlmidcode);
				fp.setProvince(vwi.getProvince());
				fp.setFindprovince(province);
				fp.setCid(vwi.getCid());
				fp.setSyncode(vwi.getSyncode());
				fp.setCname(vwi.getCname());
				fp.setProductid(vwi.getProductid());
				fp.setProductname(vwi.getProductname());
				fp.setSpecmode(vwi.getSpecmode());
				fp.setStartno(vwi.getStartno());
				fp.setEndno(vwi.getEndno());
				fp.setSenddate(vwi.getMakedate());
				fp.setIsdeal(0);
				fp.setMakeorganid(users.getMakeorganid());
				fp.setMakedeptid(users.getMakedeptid());
				fp.setMakeid(users.getUserid());
				fp.setMakedate(DateUtil.getCurrentDate());
				AppFleeProduct appfp = new AppFleeProduct();
				appfp.addFleeProduct(fp);
				
				//发送邮件
				CountryAreaService cas = new CountryAreaService();
				HtmlTemplateGenerator temp = new HtmlTemplateGenerator(path);
				Map map = new HashMap();
				map.put("id", fp.getQueryid());
				map.put("makename", users.getRealname());
				map.put("province", cas.getCountryAreaName(fp.getProvince()));
				map.put("findprovince", cas.getCountryAreaName(fp.getFindprovince()));
				map.put("cname", fp.getCname());
				map.put("syncode", fp.getSyncode());
				map.put("productname", fp.getProductname());
				map.put("specmode", fp.getSpecmode());
				map.put("productid", fp.getProductid());
				map.put("outdate", DateUtil.formatDate(vwi.getMakedate()));
				map.put("startno", fp.getStartno());
				map.put("endno", fp.getEndno());
				map.put("querydate", DateUtil.getCurrentDateString());		
				
				UsersService us = new UsersService();
				Users u = us.getUsersByid(fp.getMakeid());
				String content = temp.create("mailwlmidcode.ftl", map);
				String title = "可疑窜货信息: 查询编号:"+fp.getQueryid();
				String[] tomail = new String[]{u.getEmail(),vwi.getEmail()};
				SendMailService sms = new SendMailService(title, tomail, content);
				sms.sendmail();
			}
		}
		

	}
}
