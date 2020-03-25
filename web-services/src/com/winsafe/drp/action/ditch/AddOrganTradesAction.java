package com.winsafe.drp.action.ditch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrganTrades;
import com.winsafe.drp.dao.AppOrganTradesDetail;
import com.winsafe.drp.dao.OrganTrades;
import com.winsafe.drp.dao.OrganTradesDetail;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.BeanCopy;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

/**
 * @author : jerry
 * @version : 2009-9-3 上午10:33:06 www.winsafe.cn
 */
public class AddOrganTradesAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {

			OrganTrades ot = new OrganTrades();
			AppOrganTrades appAMA = new AppOrganTrades();
			AppOrganTradesDetail appAMAD = new AppOrganTradesDetail();
			BeanCopy bc = new BeanCopy();

			bc.copy(ot, request);
			String pwid = MakeCode.getExcIDByRandomTableName("organ_trades", 2,
					"OT");
			ot.setId(pwid);
			ot.setIdii(pwid.replace("OT", "OD"));
			ot.setMakeid(userid);
			ot.setMakeorganid(users.getMakeorganid());
			ot.setMakedeptid(users.getMakedeptid());
			ot.setMakedate(DateUtil.getCurrentDate());

			ot.setIsaudit(0);
			ot.setIsratify(0);
			ot.setIsaffirm(0);
			ot.setIsreceive(0);
			ot.setIsblankout(0);
			ot.setPrinttimes(0);
			ot.setTakestatus(0);
			ot.setPtakestatus(0);

			String strproductid[] = request.getParameterValues("productid");
			String strproductname[] = request.getParameterValues("productname");
			String strspecmode[] = request.getParameterValues("specmode");
			int unitid[] = RequestTool.getInts(request, "unitid");
			String strbatch[] = request.getParameterValues("batch");
			double quantity[] = RequestTool.getDoubles(request, "quantity");
			OrganTradesDetail otd = null;
			Double totalsum = 0.00;
			if (strproductid != null) {
				for (int i = 0; i < strproductid.length; i++) {
					otd = new OrganTradesDetail();
					Integer id = Integer.valueOf(MakeCode
							.getExcIDByRandomTableName("organ_withdraw_detail",
									0, ""));
					otd.setId(id);
					otd.setOtid(pwid);
					otd.setProductid(strproductid[i]);
					otd.setProductname(strproductname[i]);
					otd.setSpecmode(strspecmode[i]);
					otd.setUnitid(unitid[i]);
					otd.setBatch(strbatch[i]);
					otd.setUnitprice(0d);
					otd.setQuantity(quantity[i]);
					otd.setCanquantity(0d);
					otd.setTakequantity(0d);
					otd.setSubsum(otd.getUnitprice() * otd.getQuantity());
					appAMAD.save(otd);
					totalsum += otd.getSubsum();
				}
			}
			ot.setTotalsum(totalsum);
			appAMA.save(ot);
			DBUserLog.addUserLog(userid, 4, "渠道管理>>渠道换货,编号：" + pwid);
			request.setAttribute("result", "databases.add.success");
			return mapping.findForward("success");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return super.execute(mapping, form, request, response);
	}
}
