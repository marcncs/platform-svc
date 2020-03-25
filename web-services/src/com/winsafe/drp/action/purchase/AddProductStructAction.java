package com.winsafe.drp.action.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProductStruct;
import com.winsafe.drp.dao.ProductStruct;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.MakeCode;

public class AddProductStructAction  extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		initdata(request);
		AppProductStruct aa=new AppProductStruct();
		
		try{
			String strparentid = request.getParameter("parentid");
			String sortname=request.getParameter("sortname");
			String acode=aa.getAcodeByParent(strparentid);
			ProductStruct as=new ProductStruct ();
			as.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("product_struct",0,"")));
			as.setStructcode(acode);
			as.setSortname(sortname);
			as.setSortnameen(request.getParameter("sortnameen"));
			
			aa.addNewProductStruct(as);
			DBUserLog.addUserLog(userid,"产品管理", "产品资料>>新增产品类别,编号:"+acode);
			request.setAttribute("result","databases.add.success");
			return mapping.findForward("success");

		}catch(Exception e){
			e.printStackTrace();
		}finally{
		}
		return null;
	}

}
