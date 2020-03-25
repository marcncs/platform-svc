package com.winsafe.drp.action.assistant;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppIdcodeDetail;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.IdcodeDetail;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AddIdcodeResetIdcodeAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
//		Long userid = users.getUserid();
		
	
		String productid = request.getParameter("productid");
		String[] idcode = request.getParameterValues("idcode");
		String piid = request.getParameter("piid");
		AppIdcodeDetail app = new AppIdcodeDetail();
		AppIdcode appidcode = new AppIdcode();
		try {
			//判断数组有无重复数据
			List idlist = Arrays.asList(idcode);
			if ( idlist.size() != (new HashSet(idlist)).size() ){
			     request.setAttribute("result","databases.record.doubleidcode");
			     return new ActionForward("/sys/lockrecord.jsp");
			}
//			Idcode ic = null;
//			for ( int i =0; i < idcode.length; i ++){
//				ic = appidcode.getIdcodeById(productid, idcode[i]);
//				if ( ic != null ){
//				    request.setAttribute("result","databases.record.doubleidcode");
//				    return new ActionForward("/sys/lockrecord.jsp");
//				}
//			}			
			
			
			app.delIdcodeDetailByPidBillid(productid, piid);
			IdcodeDetail pii = null;	
			AppProduct ap = new AppProduct();
			Product p = ap.getProductByID(productid);
			for ( int i =0; i < idcode.length; i ++){				
				pii = new IdcodeDetail();
				pii.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
							"idcode_detail", 0, "")));
				pii.setBillid(piid);
				pii.setProductid(productid);
				pii.setProductname(p.getProductname());
				pii.setSpecmode(p.getSpecmode());
				pii.setIdcode(idcode[i]);
				pii.setMakedate(DateUtil.getCurrentDate());
//				pii.setMakeid(userid);
				pii.setMakeuser(users.getRealname());
				pii.setIdbilltype(8);
				pii.setMakeorganid(users.getMakeorganid());
				pii.setEquiporganid(users.getMakeorganid());
				app.addIdcodeDetail(pii);
			}
			
	
			request.setAttribute("result", "databases.add.success");			
//			DBUserLog.addUserLog(userid, "序号重置录入机身号");// 日志			
			return mapping.findForward("addresult");
		} catch (Exception e) {			
			e.printStackTrace();
		} 
		return new ActionForward(mapping.getInput());
	}
}
