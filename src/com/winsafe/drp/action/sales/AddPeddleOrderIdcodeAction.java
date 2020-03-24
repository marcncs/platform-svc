package com.winsafe.drp.action.sales;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppIdcode;

public class AddPeddleOrderIdcodeAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
//		Long userid = users.getUserid();
		try {
	
		String productid = request.getParameter("productid");
		String[] idcode = request.getParameterValues("idcode");
		AppIdcode appidcode = new AppIdcode();
		
//		判断数组有无重复数据
		List idlist = Arrays.asList(idcode);
		if ( idlist.size() != (new HashSet(idlist)).size() ){
		     request.setAttribute("result", "databases.record.doubleidcode");
		     return new ActionForward("/sys/lockrecord.jsp");
		}
//		for ( int i =0; i < idcode.length; i ++){
////			Idcode ic = appidcode.getIdcodeById(productid, idcode[i]);
//			if ( ic == null || ic.getIsuse() == 0){
//			    request.setAttribute("result", "此"+idcode[i]+"序号库存中已被出库或者不存在！");
//			    return new ActionForward("/sys/lockrecord2.jsp");
//			}
//		}
		
//		app.delPeddleOrderIdcodeByPidBatch(productid, batch);
//		PeddleOrderIdcode pii = null;
//		for ( int i =0; i < idcode.length; i ++){
//			pii = new PeddleOrderIdcode();
//			pii.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
//						"peddle_order_idcode", 0, "")));
//			pii.setPoid(poid);
//			pii.setProductid(productid);
//			pii.setBatch(batch);
//			pii.setIdcode(idcode[i]);
//			app.addPeddleOrderIdcode(pii);
//			
//		}
		StringBuffer codes = new StringBuffer();
		for ( int i =0; i < idcode.length; i ++){
			if ( i == 0 ){
				codes.append(idcode[i]);
			}else{
				codes.append(",").append(idcode[i]);
			}
		}
		request.setAttribute("rowx", request.getParameter("rowx"));
		request.setAttribute("codes", codes.toString());
		request.setAttribute("result", "databases.add.success");			
//		DBUserLog.addUserLog(userid, "零售单录入机身号");			
		return mapping.findForward("addresult");
		
		} catch (Exception e) {			
			e.printStackTrace();
		} 
		return new ActionForward(mapping.getInput());
	}

}
