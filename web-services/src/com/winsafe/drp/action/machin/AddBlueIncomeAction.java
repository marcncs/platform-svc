package com.winsafe.drp.action.machin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOtherIncomeAll;
import com.winsafe.drp.dao.AppOtherIncomeDetailAll;
import com.winsafe.drp.dao.OtherIncomeAll;
import com.winsafe.drp.dao.OtherIncomeDetailAll;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.WarehouseBitDafService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class AddBlueIncomeAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		int userid = users.getUserid();

		super.initdata(request);try{
			OtherIncomeAll oi = new OtherIncomeAll();
			String oiid = MakeCode.getExcIDByRandomTableName("other_income_all", 2,
					"QR");
			oi.setId(oiid);
			oi.setOrganid(request.getParameter("organid"));
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
//			double unitprice[] = RequestTool.getDoubles(request, "unitprice");
			double quantity[] = RequestTool.getDoubles(request, "quantity");

			AppOtherIncomeDetailAll aoid = new AppOtherIncomeDetailAll();
			WarehouseBitDafService wbds = new WarehouseBitDafService("other_income_idcode_all","oiid",oi.getWarehouseid());

			for (int i = 0; i < productid.length; i++) {
				OtherIncomeDetailAll oid = new OtherIncomeDetailAll();
				oid.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"other_income_detail_all", 0, "")));
				oid.setOiid(oiid);
				oid.setProductid(productid[i]);
				oid.setProductname(productname[i]);
				oid.setSpecmode(specmode[i]);
//				oid.setUnitid(Constants.DEFAULT_UNIT_ID);
				oid.setUnitid(unitid[i]);
				oid.setBatch(batch[i]);
//				oid.setUnitprice(unitprice[i]);
				oid.setQuantity(quantity[i]);
//				oid.setSubsum(oid.getUnitprice() * oid.getQuantity());
				aoid.addOtherIncomeDetail(oid);
				//插入idcode
				wbds.add(oi.getId(), oid.getProductid(), oid.getUnitid(), oid.getQuantity(), oid.getBatch());
			}

			AppOtherIncomeAll aoi = new AppOtherIncomeAll();
			aoi.addOtherIncomeAll(oi);

			request.setAttribute("result", "databases.add.success");

			DBUserLog.addUserLog(request, "编号:" + oiid);
			return mapping.findForward("addresult");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
