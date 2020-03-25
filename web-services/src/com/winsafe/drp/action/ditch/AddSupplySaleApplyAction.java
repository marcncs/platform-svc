package com.winsafe.drp.action.ditch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSupplySaleApply;
import com.winsafe.drp.dao.AppSupplySaleApplyDetail;
import com.winsafe.drp.dao.SupplySaleApply;
import com.winsafe.drp.dao.SupplySaleApplyDetail;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.BeanCopy;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

/**
 * @author : jerry
 * @version : 2009-8-24 下午02:12:00 www.winsafe.cn
 */
public class AddSupplySaleApplyAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {

			SupplySaleApply ssa = new SupplySaleApply();
			OrganService os = new OrganService();
			AppSupplySaleApply appAMA = new AppSupplySaleApply();
			AppSupplySaleApplyDetail appAMAD = new AppSupplySaleApplyDetail();
			BeanCopy bc = new BeanCopy();

			bc.copy(ssa, request);
			String amid = MakeCode.getExcIDByRandomTableName(
					"supply_sale_apply", 2, "DA");
			ssa.setId(amid);
			ssa.setMakeid(userid);
			ssa.setMakeorganid(users.getMakeorganid());
			ssa.setMakeorganidname(os.getOrganName(users.getMakeorganid()));
			ssa.setMakedeptid(users.getMakedeptid());
			ssa.setMakedate(DateUtil.getCurrentDate());
			ssa.setIsaudit(0);
			ssa.setIsratify(0);
			ssa.setIsblankout(0);
			ssa.setPrinttimes(0);
			ssa.setIstrans(0);
			ssa.setKeyscontent(amid + "," + ssa.getMakeorganidname() + ","
					+ ssa.getOlinkman() + "," + ssa.getOtel());

			String strproductid[] = request.getParameterValues("productid");
			String strproductname[] = request.getParameterValues("productname");
			String strspecmode[] = request.getParameterValues("specmode");
			String strunitid[] = request.getParameterValues("unitid");
			String strpunitprice[] = request.getParameterValues("punitprice");
			String strsunitprice[] = request.getParameterValues("sunitprice");
			String strquantity[] = request.getParameterValues("quantity");

			Double totalsum = 0.00, stotalsum = 0.00;
			SupplySaleApplyDetail ssad = null;
			if (strproductid != null) {
				for (int i = 0; i < strproductid.length; i++) {
					Double punitprice = Double.valueOf(strpunitprice[i]);
					Double sunitprice = Double.valueOf(strsunitprice[i]);
					Double quantity = Double.valueOf(strquantity[i]);
					ssad = new SupplySaleApplyDetail();
					Integer id = Integer.valueOf(MakeCode
							.getExcIDByRandomTableName(
									"supply_sale_apply_detail", 0, ""));
					ssad.setId(id);
					ssad.setSsid(amid);
					ssad.setProductid(strproductid[i]);
					ssad.setProductname(strproductname[i]);
					ssad.setSpecmode(strspecmode[i]);
					ssad.setUnitid(Integer.valueOf(strunitid[i]));
					ssad.setPunitprice(punitprice);
					ssad.setSunitprice(sunitprice);
					ssad.setQuantity(quantity);
					ssad.setCanquantity(0d);
					ssad.setAlreadyquantity(0d);
					Double psubsum = punitprice * quantity;
					ssad.setPsubsum(psubsum);
					totalsum += psubsum;
					Double ssubsum = sunitprice * quantity;
					ssad.setSsubsum(ssubsum);
					stotalsum += ssubsum;
					appAMAD.save(ssad);
				}
			}
			ssa.setTotalsum(totalsum);
			ssa.setStotalsum(stotalsum);
			appAMA.save(ssa);
			DBUserLog.addUserLog(userid, 4, "渠道管理>>添加代销申请,编号：" + amid);
			request.setAttribute("result", "databases.add.success");
			return mapping.findForward("success");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return super.execute(mapping, form, request, response);
	}
}
