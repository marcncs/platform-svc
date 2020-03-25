package com.winsafe.drp.action.ditch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
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
import com.winsafe.hbm.util.BeanCopy;
import com.winsafe.hbm.util.MakeCode;

/**
 * @author : jerry
 * @version : 2009-8-24 下午02:52:42 www.winsafe.cn
 */
public class UpdAlterMoveApplyAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);try{
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();

			AlterMoveApply alterma = new AlterMoveApply();
			AppAlterMoveApply appAMA = new AppAlterMoveApply();
			AppAlterMoveApplyDetail appAMAD = new AppAlterMoveApplyDetail();
			BeanCopy bc = new BeanCopy();
			
			bc.copy(alterma, request);

			AlterMoveApply newAlterma = appAMA.getAlterMoveApplyByID(alterma
					.getId());

			AlterMoveApply oldalterma = (AlterMoveApply) BeanUtils
					.cloneBean(newAlterma);
			String amid = newAlterma.getId();
			newAlterma.setMovedate(alterma.getMovedate());
			newAlterma.setOutorganid(alterma.getOutorganid());
			newAlterma.setInwarehouseid(alterma.getInwarehouseid());
			newAlterma.setPaymentmode(alterma.getPaymentmode());
			newAlterma.setInvmsg(alterma.getInvmsg());
			newAlterma.setTransportmode(alterma.getTransportmode());
			newAlterma.setTransportaddr(alterma.getTransportaddr());
			newAlterma.setTickettitle(alterma.getTickettitle());
			newAlterma.setMovecause(alterma.getMovecause());
			newAlterma.setRemark(alterma.getRemark());

			
			String strproductid[] = request.getParameterValues("productid");
			String strproductname[] = request.getParameterValues("productname");
			String strspecmode[] = request.getParameterValues("specmode");
			String strunitid[] = request.getParameterValues("unitid");
			String strunitprice[] = request.getParameterValues("unitprice");
			String strquantity[] = request.getParameterValues("quantity");
			appAMAD.delAlterMoveApplyDetailByAmid(amid);
			AlterMoveApplyDetail amad = null;
			Double totalsum = 0.00, subsum = 0.00, price, quantity;
			if (strproductid != null) {
				for (int i = 0; i < strproductid.length; i++) {
					price = Double.valueOf(strunitprice[i]);
					quantity = Double.valueOf(strquantity[i]);
					amad = new AlterMoveApplyDetail();
					Integer id = Integer.valueOf(MakeCode
							.getExcIDByRandomTableName("alter_move_apply_detail", 0, ""));
					amad.setId(id);
					amad.setAmid(amid);
					amad.setProductid(strproductid[i]);
					amad.setProductname(strproductname[i]);
					amad.setSpecmode(strspecmode[i]);
					amad.setUnitid(Integer.valueOf(strunitid[i]));
					amad.setUnitprice(price);
					amad.setQuantity(quantity);
					amad.setAlreadyquantity(0d);
					amad.setCanquantity(0d);
					subsum = price * quantity;
					amad.setSubsum(subsum);
					totalsum += subsum;
					appAMAD.addAlterMoveApplyDetail(amad);
				}
			}
			newAlterma.setTotalsum(totalsum);
			appAMA.addAlterMoveApply(newAlterma);
			DBUserLog.addUserLog(userid, 4, "渠道管理>>修改订购申请,编号：" + amid, oldalterma,
					newAlterma);
			request.setAttribute("result", "databases.upd.success");
			return mapping.findForward("success");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return super.execute(mapping, form, request, response);
	}
}
