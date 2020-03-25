package com.winsafe.drp.action.ditch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSupplySaleMove;
import com.winsafe.drp.dao.AppSupplySaleMoveDetail;
import com.winsafe.drp.dao.SupplySaleMove;
import com.winsafe.drp.dao.SupplySaleMoveDetail;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.BeanCopy;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

/**
 * @author : jerry
 * @version : 2009-8-24 下午02:12:00 www.winsafe.cn
 */
public class AddSupplySaleMoveAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {

			SupplySaleMove ssm = new SupplySaleMove();
			OrganService os = new OrganService();
			AppSupplySaleMove appAMA = new AppSupplySaleMove();
			AppSupplySaleMoveDetail appAMAD = new AppSupplySaleMoveDetail();
			BeanCopy bc = new BeanCopy();

			bc.copy(ssm, request);
			String amid = MakeCode.getExcIDByRandomTableName(
					"supply_sale_move", 2, "DM");
			ssm.setId(amid);
			ssm.setMakeid(userid);
			ssm.setMakeorganid(users.getMakeorganid());
			ssm.setMakeorganidname(os.getOrganName(users.getMakeorganid()));
			ssm.setMakedeptid(users.getMakedeptid());
			ssm.setMakedate(DateUtil.getCurrentDate());

			ssm.setIsmaketicket(0);
			ssm.setIscomplete(0);
			ssm.setIsshipment(0);
			ssm.setIstally(0);
			ssm.setIsaudit(0);
			ssm.setIsblankout(0);
			ssm.setPrinttimes(0);
			ssm.setKeyscontent(amid + "," + ssm.getMakeorganidname() + ","
					+ ssm.getOlinkman() + "," + ssm.getOtel());

			String strproductid[] = request.getParameterValues("productid");
			String strproductname[] = request.getParameterValues("productname");
			String strspecmode[] = request.getParameterValues("specmode");
			String strunitid[] = request.getParameterValues("unitid");
			String strpunitprice[] = request.getParameterValues("punitprice");
			String strsunitprice[] = request.getParameterValues("sunitprice");
			String strquantity[] = request.getParameterValues("quantity");
			Double totalsum = 0.00, stotalsum = 0.00;
			SupplySaleMoveDetail ssmd = null;
			if (strproductid != null) {
				for (int i = 0; i < strproductid.length; i++) {
					Double punitprice = Double.valueOf(strpunitprice[i]);
					Double sunitprice = Double.valueOf(strsunitprice[i]);
					Double quantity = Double.valueOf(strquantity[i]);
					ssmd = new SupplySaleMoveDetail();
					Integer id = Integer.valueOf(MakeCode
							.getExcIDByRandomTableName(
									"supply_sale_move_detail", 0, ""));
					ssmd.setId(id);
					ssmd.setSsmid(amid);
					ssmd.setProductid(strproductid[i]);
					ssmd.setProductname(strproductname[i]);
					ssmd.setSpecmode(strspecmode[i]);
					ssmd.setUnitid(Integer.valueOf(strunitid[i]));
					ssmd.setPunitprice(punitprice);
					ssmd.setSunitprice(sunitprice);
					ssmd.setQuantity(quantity);
					ssmd.setTakequantity(0d);
					Double psubsum = punitprice * quantity;
					ssmd.setPsubsum(psubsum);
					totalsum += psubsum;
					Double ssubsum = sunitprice * quantity;
					ssmd.setSsubsum(ssubsum);
					stotalsum += ssubsum;
					appAMAD.save(ssmd);
				}
			}
			ssm.setTotalsum(totalsum);
			ssm.setStotalsum(stotalsum);
			appAMA.save(ssm);
			DBUserLog.addUserLog(userid, 4, "渠道管理>>添加渠道代销,编号：" + amid);
			request.setAttribute("result", "databases.add.success");
			return mapping.findForward("success");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return super.execute(mapping, form, request, response);

	}
}
