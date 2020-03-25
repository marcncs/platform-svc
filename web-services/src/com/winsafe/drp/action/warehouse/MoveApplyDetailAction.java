package com.winsafe.drp.action.warehouse;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppMoveApply;
import com.winsafe.drp.dao.AppMoveApplyDetail;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppRole;
import com.winsafe.drp.dao.MoveApply;
import com.winsafe.drp.dao.MoveApplyDetail;
import com.winsafe.drp.dao.MoveApplyDetailForm;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.Role;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.keyretailer.dao.AppUserCustomer;
import com.winsafe.drp.metadata.StockMoveConfirmStatus;
import com.winsafe.drp.service.MoveApplyServices;
import com.winsafe.hbm.util.Internation;

public class MoveApplyDetailAction extends BaseAction {
	AppUserCustomer appUserCustomer = new AppUserCustomer();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// System.out.println("--"+MakeCode.getRandom());
		String id = request.getParameter("ID");
		super.initdata(request);
		try{
			AppRole appRole = new AppRole();
			//查看是否有审批权限
			boolean firstAuditRole = false;
			boolean secondAuditRole = false;
			
			List<Role> roles = appRole.getRolesByUserid(userid);
			for(Role role : roles) {
				if("转仓审批一".equals(role.getRolename())) {
					firstAuditRole = true;
				}
				if("转仓审批二".equals(role.getRolename())) {
					secondAuditRole = true;
				}
			}
			
			AppMoveApply asm = new AppMoveApply();
			AppProduct appProduct = new AppProduct();
			AppFUnit appFUnit = new AppFUnit();
			MoveApply sm = asm.getMoveApplyByID(id);
			
			//检查是否有审核权限
			if(firstAuditRole && StockMoveConfirmStatus.OUT_ASM_APPROVED.getValue().equals(sm.getIsratify())) {
				sm.setCanAudit(true);
			} else if(secondAuditRole && StockMoveConfirmStatus.IN_ASM_APPROVED.getValue().equals(sm.getIsratify())) {
				sm.setCanAudit(true);
			} else {
				sm.setCanAudit(false);
			}

			AppMoveApplyDetail asmd = new AppMoveApplyDetail();
			List spils = asmd.getMoveApplyDetailByAmID(id);
			ArrayList als = new ArrayList();

			for (int i = 0; i < spils.size(); i++) {
				MoveApplyDetailForm smdf = new MoveApplyDetailForm();
				MoveApplyDetail o = (MoveApplyDetail) spils.get(i);
				smdf.setProductid(o.getProductid());
				smdf.setProductname(o.getProductname());
				smdf.setSpecmode(o.getSpecmode());
				smdf.setUnitid(o.getUnitid());
				smdf.setUnitidname(Internation.getStringByKeyPositionDB(
						"CountUnit", o.getUnitid().intValue()));
				smdf.setUnitprice(o.getUnitprice());
				smdf.setCanquantity(o.getCanquantity());
				smdf.setQuantity(o.getQuantity());
				smdf.setAlreadyquantity(o.getAlreadyquantity());
				smdf.setSubsum(o.getSubsum());
				
				Product product = appProduct.getProductByID(o.getProductid());
				if(product == null) continue;
				smdf.setCountunit(product.getCountunit());
				// 转化数量为计量单位的数量
				Double quantity = appFUnit.getQuantity(o.getProductid(), o.getUnitid(), o.getQuantity());
				quantity = quantity * product.getBoxquantity();
				smdf.setcUnitQuantity(quantity);
				
				als.add(smdf);
			}
			
			//获取当前审批人
			MoveApplyServices mas = new MoveApplyServices();
			request.setAttribute("approvers", mas.getApprovers(sm)); 
			
			request.setAttribute("smid", id);
			request.setAttribute("als", als);
			request.setAttribute("smf", sm);
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			//DBUserLog.addUserLog(userid, "转仓申请单详情,编号：" + id);
			return mapping.findForward("detail");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}

}
