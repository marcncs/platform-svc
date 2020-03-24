package com.winsafe.drp.action.ditch.productreturn;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrganWithdraw;
import com.winsafe.drp.dao.AppOrganWithdrawDetail;
import com.winsafe.drp.dao.OrganWithdraw;
import com.winsafe.drp.dao.OrganWithdrawDetail;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.BeanCopy;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

/**
 * @author : jerry
 * @version : 2009-8-24 下午02:12:00 www.winsafe.cn
 */
public class DoAddProductReturnAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {

			OrganWithdraw ow = new OrganWithdraw();
			AppOrganWithdraw appAMA = new AppOrganWithdraw();
			AppOrganWithdrawDetail appAMAD = new AppOrganWithdrawDetail();
			BeanCopy bc = new BeanCopy();

			bc.copy(ow, request);
			String pwid = MakeCode.getExcIDByRandomTableName("organ_withdraw",
					2, "OW");
			ow.setId(pwid);
			ow.setMakeid(userid);
			ow.setMakeorganid(users.getMakeorganid());
			ow.setMakedeptid(users.getMakedeptid());
			ow.setMakedate(DateUtil.getCurrentDate());

			ow.setIsaudit(0);
			ow.setIsratify(0);
			ow.setIsaffirm(0);
			ow.setIscomplete(0);
			ow.setIsblankout(0);
			ow.setPrinttimes(0);
			ow.setTakestatus(0);

			String strproductid[] = request.getParameterValues("productid");
			String strproductname[] = request.getParameterValues("productname");
			String strspecmode[] = request.getParameterValues("specmode");
			int unitid[] = RequestTool.getInts(request, "unitid");
			String strbatch[] = request.getParameterValues("batch");
			double unitprice[] = RequestTool.getDoubles(request, "unitprice");
			double quantity[] = RequestTool.getDoubles(request, "quantity");
			Double totalsum = 0.00;
			OrganWithdrawDetail owd = null;
			if (strproductid != null) {
				for (int i = 0; i < strproductid.length; i++) {
					owd = new OrganWithdrawDetail();
					Integer id = Integer.valueOf(MakeCode
							.getExcIDByRandomTableName("organ_withdraw_detail",
									0, ""));
					owd.setId(id);
					owd.setOwid(pwid);
					owd.setProductid(strproductid[i]);
					owd.setProductname(strproductname[i]);
					owd.setSpecmode(strspecmode[i]);
					owd.setUnitid(Integer.valueOf(unitid[i]));
					owd.setBatch(strbatch[i]);
					owd.setUnitprice(unitprice[i]);
					owd.setQuantity(quantity[i]);
					owd.setRatifyquantity(0d);
					owd.setTakequantity(0d);
					owd.setSubsum(owd.getUnitprice() * owd.getQuantity());
					appAMAD.save(owd);
					totalsum += owd.getSubsum();
				}
			}
			ow.setKeyscontent(ow.getId() + "," + ow.getPorganid() + ","
					+ ow.getPorganname() + "," + ow.getPlinkman() + ","
					+ ow.getTel());
			ow.setTotalsum(totalsum);

			appAMA.save(ow);
			DBUserLog.addUserLog(userid, 4, "渠道管理>>退货,编号：" + pwid);
			request.setAttribute("result", "databases.add.success");
			return mapping.findForward("success");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return super.execute(mapping, form, request, response);
	}
}
