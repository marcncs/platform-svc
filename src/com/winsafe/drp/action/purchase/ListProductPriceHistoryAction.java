package com.winsafe.drp.action.purchase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sun.org.apache.commons.beanutils.BeanUtils;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductPriceHistory;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.BaseResource;
import com.winsafe.drp.dao.ProductPriceHistory;
import com.winsafe.drp.dao.ProductPriceHistoryForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;

public class ListProductPriceHistoryAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			int pagesize = 10;
			initdata(request);
			
			String condition = " ph.unitId=br.tagsubkey and br.tagname='CountUnit'";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "ProductPriceHistory" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String blur = DbUtil.getOrBlur(map, tmpMap, "productId", "productName",
					"memo");
			String timeCondition = getStartAndEndTimeCondition(map, tmpMap,
					"startTime","endTime");

			whereSql = whereSql + blur + timeCondition + condition;
			
			whereSql = DbUtil.getWhereSql(whereSql);
			AppProductPriceHistory abr = new AppProductPriceHistory();
			AppProduct ap = new AppProduct();
			AppUsers au = new AppUsers();

			List apls = abr.getProductPriceHistoryList(request,pagesize,whereSql);
			List alpl = new ArrayList();
			for (int i = 0; i < apls.size(); i++) {
				ProductPriceHistory pph = null;
				BaseResource br = null;
				Object[] o = (Object[]) apls.get(i);
				pph = (ProductPriceHistory) BeanUtils.cloneBean(o[0]);
				br = (BaseResource) o[1];
				pph.setUnitName(br.getTagsubvalue());
				alpl.add(pph);
			}
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			//分页
			//alpl = Pager.initPagerManually(request, alpl);
			//DBUserLog.addUserLog(userid, "列表产品价格历史");
			request.setAttribute("alpl", alpl);
			request.setAttribute("KeyWord", request.getParameter("KeyWord"));
			request.setAttribute("BeginDate", request.getParameter("BeginDate"));
			request.setAttribute("ProductID", request.getParameter("productId"));
			request.setAttribute("ProductName", request.getParameter("productName"));

			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
	
	//创建日期条件
	  public String getStartAndEndTimeCondition(Map map, Map tmpMap, String starttime, String endtime) {
		    StringBuffer buf = new StringBuffer();
		    if (map.containsKey("BeginDate")) {
		        String timeField = (String) tmpMap.get("BeginDate");
		        if (timeField != null && !timeField.equals("")) {

		          buf.append(starttime + "<='" + timeField + "'");
		          buf.append(" and ");
		          
		          buf.append("'" + timeField + "'"  + "<=" + endtime);
		          buf.append(" and ");
		        }
		      }
		      
		    return buf.toString().toLowerCase();
		  }
}
