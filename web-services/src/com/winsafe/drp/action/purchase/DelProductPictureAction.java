package com.winsafe.drp.action.purchase;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppProductPicture;
import com.winsafe.drp.dao.ProductPicture;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class DelProductPictureAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			AppProductPicture ap = new AppProductPicture();
			int id = Integer.valueOf(request.getParameter("ID"));
			ProductPicture pp = ap.getProductPictureById(id);
			try{
				File file = new File(pp.getPictureurl());
				if(file.exists()){
					if(file.isFile()){
						file.delete();
					}
				}
			}catch (Exception e) {
				// TODO: handle exception
			}
			ap.delProductPictureByID(id);

			request.setAttribute("result", "databases.del.success");
			UsersBean users = UserManager.getUser(request);
			int userid = users.getUserid();
			DBUserLog.addUserLog(userid, 11, "商品资料>>删除产品图片,编号：" + id, pp);

			return mapping.findForward("del");
		} catch (Exception e) {

			e.printStackTrace();
		} finally {

		}
		return null;
	}

}
