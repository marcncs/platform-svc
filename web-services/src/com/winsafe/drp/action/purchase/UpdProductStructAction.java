package com.winsafe.drp.action.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProductStruct;
import com.winsafe.drp.dao.ProductStruct;
import com.winsafe.drp.util.DBUserLog;

public class UpdProductStructAction  extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		initdata(request);
		AppProductStruct aa=new AppProductStruct();		
		
		try{
			String acode = request.getParameter("acode");
			String sortname=request.getParameter("sortname");
			String sortnameen =request.getParameter("sortnameen");
			
			ProductStruct ps = aa.getProductStructById(acode);
			ProductStruct oldps = (ProductStruct)BeanUtils.cloneBean(ps);
			ps.setSortname(sortname);
			ps.setSortnameen(sortnameen);
			aa.updProductStruct(ps);
			DBUserLog.addUserLog(userid,"产品管理", "产品资料>>修改产品类别,编号:"+acode, oldps, ps);
			
			request.setAttribute("result","databases.upd.success");
			return mapping.findForward("success");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
		}
		return null;
	}

}
