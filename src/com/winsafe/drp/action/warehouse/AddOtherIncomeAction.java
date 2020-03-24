package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOtherIncome;
import com.winsafe.drp.dao.AppOtherIncomeDetail;
import com.winsafe.drp.dao.OtherIncome;
import com.winsafe.drp.dao.OtherIncomeDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.WarehouseBitDafService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class AddOtherIncomeAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		int userid = users.getUserid();

		super.initdata(request);try{
			OtherIncome oi = new OtherIncome();
			String oiid = MakeCode.getExcIDByRandomTableName("other_income", 2,
					"PY");
			oi.setId(oiid);
			oi.setWarehouseid(request.getParameter("warehouseid"));
			oi.setIncomesort(RequestTool.getInt(request, "incomesort"));
			oi.setBillno(request.getParameter("billno"));
			oi.setRemark(request.getParameter("remark"));
			oi.setIsaudit(0);
			oi.setAuditid(0);
			oi.setMakeorganid(users.getMakeorganid());
			oi.setMakedeptid(users.getMakedeptid());
			oi.setMakeid(userid);
			oi.setMakedate(DateUtil.getCurrentDate());
			oi.setKeyscontent(oi.getId() + "," + "," + oi.getBillno() + ","
					+ oi.getRemark());
			
			String isaccount = request.getParameter("isaccount");
			//不记账
			if(isaccount==null){
				oi.setIsaccount(0);
			}else{
				//记账
				oi.setIsaccount(1);
			}

			String productid[] = request.getParameterValues("productid");
			String productname[] = request.getParameterValues("productname");
			String specmode[] = request.getParameterValues("specmode");
			int unitid[] = RequestTool.getInts(request, "unitid");
			String batch[] = request.getParameterValues("batch");
			double unitprice[] = RequestTool.getDoubles(request, "unitprice");
			double quantity[] = RequestTool.getDoubles(request, "quantity");

			AppOtherIncomeDetail aoid = new AppOtherIncomeDetail();
			WarehouseBitDafService wbds = new WarehouseBitDafService("other_income_idcode","oiid",oi.getWarehouseid());

			for (int i = 0; i < productid.length; i++) {
				OtherIncomeDetail oid = new OtherIncomeDetail();
				oid.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"other_income_detail", 0, "")));
				oid.setOiid(oiid);
				oid.setProductid(productid[i]);
				oid.setProductname(productname[i]);
				oid.setSpecmode(specmode[i]);
				oid.setUnitid(unitid[i]);
				oid.setBatch(batch[i]);
				oid.setUnitprice(unitprice[i]);
				oid.setQuantity(quantity[i]);
				oid.setSubsum(oid.getUnitprice() * oid.getQuantity());
				aoid.addOtherIncomeDetail(oid);
				wbds.add(oi.getId(), oid.getProductid(), oid.getUnitid(), oid.getQuantity(), oid.getBatch());
			}

			AppOtherIncome aoi = new AppOtherIncome();
			aoi.addOtherIncome(oi);

			request.setAttribute("result", "databases.add.success");

			DBUserLog.addUserLog(userid, 7, "库存盘点>>新增盘盈单,编号:" + oiid);
			return mapping.findForward("addresult");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
