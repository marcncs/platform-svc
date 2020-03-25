package com.winsafe.drp.action.assistant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppRuleUserWH;
import com.winsafe.drp.dao.AppViewWlmIdcode;
import com.winsafe.drp.dao.RuleUserWh;
import com.winsafe.drp.dao.ViewWlmIdcodeForm;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Pager;
/**
 * 物流码溯源查询
 * WEI.LI
 * ADD 2013-07-26 10:38
 */
public class ListViewIdcodeRetrospectAction extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//分页条数
		int pagesize = 20;
		//初始化参数
		super.initdata(request);
		//拼接SQL
		String whereSql = " where ";
		//获取产品ID
		String proudctId = request.getParameter("ProductID");
		//获取批次
		String batch = request.getParameter("batch");
		//获取生产日期
		String produceDate = request.getParameter("produceDate");
		//如果三个参数都不为空的情况，再查询
		if(null != proudctId && !"".equals(proudctId)){
			whereSql += " productid = '"+proudctId+"' ";
			if(null != batch && !"".equals(batch)){
				whereSql += " and batch = '"+batch+"' ";
			}
			if(null != produceDate && !"".equals(produceDate)){
				whereSql += " and producedate = '"+produceDate+"' ";
			}
		//否则，将不查询数据
		}else{
			whereSql += " null between startno and endno ";
			//默认初始化日期
			//produceDate = DateUtil.getCurrentDateString();
		}
		//初始化查询方法和返回集合
		AppRuleUserWH aru = new AppRuleUserWH();
		List<ViewWlmIdcodeForm> vmiList = new ArrayList<ViewWlmIdcodeForm>();
		//执行查询语句
		List pils = new AppViewWlmIdcode().getViewWlmIdcodeByWhereGroup(whereSql);
		//循环集合过滤非登录用户的业务范围的数据
		for (int i = 0; i < pils.size(); i++) {
			Map p = (Map) pils.get(i);
			ViewWlmIdcodeForm vwi = new ViewWlmIdcodeForm();
			vwi.setId(p.get("id").toString());
			vwi.setWarehouseid(p.get("warehouseid").toString());
			vwi.setCid(p.get("cid").toString());
			vwi.setCname(p.get("cname").toString());
			vwi.setProvince(Integer.valueOf(p.get("province").toString()));
			vwi.setNccode(p.get("nccode").toString());
			vwi.setProductname(p.get("productname").toString());
			vwi.setSpecmode(p.get("specmode").toString());
			vwi.setBillname(p.get("billname").toString());
			vwi.setMakedate(p.get("makedate").toString());
			vwi.setProducedate(p.get("producedate")==null?"":p.get("producedate").toString());
			vwi.setBatch(p.get("batch").toString());
			vwi.setPackquantity(Double.valueOf(p.get("packquantity").toString()));
			RuleUserWh ruw = aru.getRuleByWH(vwi.getWarehouseid(), users.getUserid());
			if(null != ruw){
				vmiList.add(vwi);
			}
		}
		vmiList = Pager.initPagerManually(request, vmiList);
		request.setAttribute("alsb", vmiList);
		request.setAttribute("ProductID", proudctId);
		request.setAttribute("batch", batch);
		request.setAttribute("produceDate", produceDate);
		DBUserLog.addUserLog(userid, 1, "列表物流码");
		return mapping.findForward("list");
	}
}
