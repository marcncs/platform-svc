package com.winsafe.drp.action.ditch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSupplySaleApply;
import com.winsafe.drp.dao.AppSupplySaleApplyDetail;
import com.winsafe.drp.dao.SupplySaleApply;
import com.winsafe.drp.dao.SupplySaleApplyDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;

/**
 * @author : jerry
 * @version : 2009-8-24 下午06:23:34
 * www.winsafe.cn
 */
public class RatifySupplySaleApplyAction extends BaseAction{

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);try{
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			String id = request.getParameter("id");
			AppSupplySaleApply appAMA = new AppSupplySaleApply();
			SupplySaleApply alterma = appAMA.getSupplySaleApplyByID(id);
			
			alterma.setTickettitle(request.getParameter("tickettitle"));
			alterma.setTransportmode(Integer.valueOf(request.getParameter("transportmode")));
			alterma.setInvmsg(Integer.valueOf(request.getParameter("invmsg")));
			alterma.setTransportaddr(request.getParameter("transportaddr"));
			
			String strid[] = request.getParameterValues("detailid");
			String strpunitprice[] = request.getParameterValues("punitprice");
			String strsunitprice[] = request.getParameterValues("sunitprice");
			String strcanquantity[] = request.getParameterValues("canquantity");
			String strpsubsum[] = request.getParameterValues("psubsum");
			String strssubsum[] = request.getParameterValues("ssubsum");
			
			AppSupplySaleApplyDetail  appamad = new AppSupplySaleApplyDetail();
			for(int i = 0 ; i < strid.length ;i++){
				SupplySaleApplyDetail amad = appamad.getSupplySaleApplyDetailByID(strid[i]);
				amad.setPunitprice(Double.valueOf(strpunitprice[i]));
				amad.setSunitprice(Double.valueOf(strsunitprice[i]));
				amad.setCanquantity(Double.valueOf(strcanquantity[i]));
				amad.setPsubsum(Double.valueOf(strpsubsum[i]));
				amad.setSsubsum(Double.valueOf(strssubsum[i]));
				appamad.update(amad);
			}
			
			alterma.setTotalsum(Double.valueOf(request.getParameter("totalsum")));
			alterma.setIsratify(1);
			alterma.setRatifyid(userid);
			alterma.setRatifydate(DateUtil.getCurrentDate());
			appAMA.update(alterma);
			
			request.setAttribute("result", "databases.refer.success");
			DBUserLog.addUserLog(userid,4, "渠道管理>>批准订购申请,编号："+id);
			
			return mapping.findForward("success");
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return super.execute(mapping, form, request, response);
	}
}
