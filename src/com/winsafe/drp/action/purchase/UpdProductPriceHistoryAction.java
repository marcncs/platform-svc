package com.winsafe.drp.action.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductPriceHistory;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductPriceHistory;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.PYCode;
import com.winsafe.hbm.util.RequestTool;

public class UpdProductPriceHistoryAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		int userid = users.getUserid();
		AppProductPriceHistory apph = new AppProductPriceHistory();

		try {
			//界面数据
			String pphid = request.getParameter("pphid");
			String productid = request.getParameter("productId");
			String productname = request.getParameter("productName");
			String countunit = request.getParameter("unitId");
			String productprice = request.getParameter("productprice");
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String memo = request.getParameter("memo");
			//判断开始日期与结束日期是否有重叠部分
			boolean isFlag = apph.getProductPriceHistoryByProductIdAndTime(Long.valueOf(pphid),productid, starttime, endtime);
			//如果有重叠部分返回
			if(isFlag){
				request.setAttribute("result", productid+"此商品历史价格在" + starttime +"与" + endtime + "之间有重叠部分，请重新录入!");
        		return new ActionForward("/sys/lockrecord2.jsp");
			}
		
			//封装数据
			ProductPriceHistory pph = apph.getProductPriceHistoryById(Long.valueOf(pphid));
			if(pph==null){
				request.setAttribute("result", "未查到此商品信息！");
        		return new ActionForward("/sys/lockrecord2.jsp");
			}
			pph.setProductId(productid);
			pph.setProductName(productname);
			pph.setUnitId(countunit==null?-1:("".equals(countunit)?-1:Integer.parseInt(countunit)));
			pph.setUnitPrice(Double.parseDouble(productprice));
			pph.setMakeUserId(Long.valueOf(users.getUserid()));
			pph.setMakeOrganId(users.getMakeorganid());
			pph.setMemo(memo);
			pph.setStartTime(DateUtil.StringToDate(starttime));
			pph.setEndTime(DateUtil.StringToDate(endtime));
			pph.setMakeDate(DateUtil.getCurrentDate());
			apph.updProductPriceHistory(pph);
			
			request.setAttribute("result", "databases.upd.success");
			DBUserLog.addUserLog(userid, 11, "修改商品历史价格");
			return mapping.findForward("updresult");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

		return mapping.getInputForward();
	}

}
