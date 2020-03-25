package com.winsafe.drp.action.ditch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AlterMoveApply;
import com.winsafe.drp.dao.AlterMoveApplyDetail;
import com.winsafe.drp.dao.AppAlterMoveApply;
import com.winsafe.drp.dao.AppAlterMoveApplyDetail;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.BeanCopy;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

/**
 * @author : jerry
 * @version : 2009-8-24 下午02:12:00 www.winsafe.cn
 */
public class AddAlterMoveApplyAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {

			AlterMoveApply alterma = new AlterMoveApply();
			OrganService os = new OrganService();
			AppAlterMoveApply appAMA = new AppAlterMoveApply();
			AppAlterMoveApplyDetail appAMAD = new AppAlterMoveApplyDetail();
			BeanCopy bc = new BeanCopy();

			bc.copy(alterma, request);
			String amid = MakeCode.getExcIDByRandomTableName(
					"alter_move_apply", 2, "OA");
			alterma.setId(amid);
			alterma.setMakeid(userid);
			alterma.setMakeorganid(users.getMakeorganid());
			alterma.setMakeorganidname(os.getOrganName(users.getMakeorganid()));
			alterma.setMakedeptid(users.getMakedeptid());
			alterma.setMakedate(DateUtil.getCurrentDate());
			alterma.setIsaudit(0);
			alterma.setIsratify(0);
			alterma.setIsblankout(0);
			alterma.setPrinttimes(0);
			alterma.setIstrans(0);

			String strproductid[] = request.getParameterValues("productid");
			String strproductname[] = request.getParameterValues("productname");
			String strspecmode[] = request.getParameterValues("specmode");
			String strunitid[] = request.getParameterValues("unitid");
			String strunitprice[] = request.getParameterValues("unitprice");
			String strquantity[] = request.getParameterValues("quantity");

			Double subsum = 0.00, totalsum = 0.00, price, quantity;
			AlterMoveApplyDetail amad = null;
			if (strproductid != null) {
				for (int i = 0; i < strproductid.length; i++) {

					price = Double.valueOf(strunitprice[i]);
					quantity = Double.valueOf(strquantity[i]);
					amad = new AlterMoveApplyDetail();
					Integer id = Integer.valueOf(MakeCode
							.getExcIDByRandomTableName(
									"alter_move_apply_detail", 0, ""));
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
			alterma.setTotalsum(totalsum);
			appAMA.addAlterMoveApply(alterma);
			DBUserLog.addUserLog(userid, 4, "渠道管理>>添加订购申请,编号：" + amid);
			request.setAttribute("result", "databases.add.success");
			return mapping.findForward("success");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return super.execute(mapping, form, request, response);
	}
}
