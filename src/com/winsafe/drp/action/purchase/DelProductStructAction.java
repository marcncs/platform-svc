package com.winsafe.drp.action.purchase;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStruct;
import com.winsafe.drp.dao.ProductStruct;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class DelProductStructAction  extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String dcode = request.getParameter("acode");

		try{
			if ( dcode.equals("1") ){
				request.setAttribute("result", "不能删除总类别!");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			AppProductStruct aa=new AppProductStruct();
			AppProduct ap = new AppProduct();
			ProductStruct ps = aa.getProductStructById(dcode);
			int pc = ap.getCountProductByPSID(dcode);
			if ( pc > 0 ){
				request.setAttribute("result", ps.getSortname()+"该类别已经有产品，不能删除!");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			List childlist = aa.getChild(dcode);
			if ( !childlist.isEmpty() && childlist.size() > 1){
				request.setAttribute("result", ps.getSortname()+"该类别已经有下级类别，不能删除!");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
				
			aa.del(dcode);
			UsersBean users = UserManager.getUser(request);
			int userid = users.getUserid();
			DBUserLog.addUserLog(userid, "产品管理", "产品资料>>删除产品类别,编号:"+dcode,ps);	
			request.setAttribute("result","databases.del.success");
			return mapping.findForward("success");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
