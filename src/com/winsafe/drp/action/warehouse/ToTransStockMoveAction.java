package com.winsafe.drp.action.warehouse;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppMoveApply;
import com.winsafe.drp.dao.AppMoveApplyDetail;
import com.winsafe.drp.dao.MoveApply;
import com.winsafe.drp.dao.MoveApplyDetail;
import com.winsafe.drp.dao.MoveApplyDetailForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.Internation;

public class ToTransStockMoveAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		super.initdata(request);try{
			AppMoveApply asm = new AppMoveApply();

			MoveApply sm = asm.getMoveApplyByID(id);


			AppMoveApplyDetail asmd = new AppMoveApplyDetail();
			List smdls = asmd.getMoveApplyDetailByNoTransAmID(id);
			ArrayList als = new ArrayList();

			for (int i = 0; i < smdls.size(); i++) {
				MoveApplyDetailForm smdf = new MoveApplyDetailForm();
				MoveApplyDetail o = (MoveApplyDetail) smdls.get(i);
				smdf.setId(o.getId());
				smdf.setProductid(o.getProductid());
				smdf.setProductname(o.getProductname());
				smdf.setSpecmode(o.getSpecmode());
				smdf.setUnitid(o.getUnitid());
				smdf.setUnitidname(Internation.getStringByKeyPositionDB(
						"CountUnit", o.getUnitid()));
				smdf.setUnitprice(o.getUnitprice());
				smdf.setQuantity(o.getQuantity());
				smdf.setCanquantity(o.getCanquantity());
				smdf.setAlreadyquantity(o.getAlreadyquantity());
				smdf.setSubsum(o.getSubsum());
				als.add(smdf);
			}

			request.setAttribute("als", als);
			request.setAttribute("smf", sm);
			DBUserLog.addUserLog(userid, 4, "渠道管理>>转仓申请转转仓,编号：" + id);
			return mapping.findForward("totrans");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
