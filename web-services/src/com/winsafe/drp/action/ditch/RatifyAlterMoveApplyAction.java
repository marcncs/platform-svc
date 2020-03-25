package com.winsafe.drp.action.ditch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AlterMoveApply;
import com.winsafe.drp.dao.AlterMoveApplyDetail;
import com.winsafe.drp.dao.AppAlterMoveApply;
import com.winsafe.drp.dao.AppAlterMoveApplyDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;

/**
 * @author : jerry
 * @version : 2009-8-24 下午06:23:34
 * www.winsafe.cn
 */
public class RatifyAlterMoveApplyAction extends BaseAction{

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);try{
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			String id = request.getParameter("id");
			AppAlterMoveApply appAMA = new AppAlterMoveApply();
			AlterMoveApply alterma = appAMA.getAlterMoveApplyByID(id);
			Double totalsum = Double.valueOf(request.getParameter("totalsum"));
			
			String strid[] = request.getParameterValues("detailid");
			String strunitprice[] = request.getParameterValues("unitprice");
			String strcanquantity[] = request.getParameterValues("canquantity");
			String strsubsum[] = request.getParameterValues("subsum");
			
			AppAlterMoveApplyDetail  appamad = new AppAlterMoveApplyDetail();
			for(int i = 0 ; i < strid.length ;i++){
				AlterMoveApplyDetail amad = appamad.getAlterMoveApplyDetailByID(strid[i]);
				amad.setUnitprice(Double.valueOf(strunitprice[i]));
				amad.setCanquantity(Double.valueOf(strcanquantity[i]));
				amad.setSubsum(Double.valueOf(strsubsum[i]));
				appamad.updAlterMoveApplyDetail(amad);
			}
			
			alterma.setTotalsum(totalsum);
			alterma.setIsratify(1);
			alterma.setRatifyid(userid);
			alterma.setRatifydate(DateUtil.getCurrentDate());
			appAMA.updAlterMoveApply(alterma);
			
			request.setAttribute("result", "databases.refer.success");
			DBUserLog.addUserLog(userid,4, "渠道管理>>批准订购申请,编号："+id);
			
			return mapping.findForward("success");
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return super.execute(mapping, form, request, response);
	}
}
