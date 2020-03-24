package com.winsafe.drp.action.machin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
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
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class UpdBlueIncomeAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);try{
			String oiid = request.getParameter("ID");
			AppOtherIncomeAll aoi = new AppOtherIncomeAll();
			OtherIncomeAll oi = aoi.getOtherIncomeByID(oiid);
			OtherIncomeAll oldoi = (OtherIncomeAll)BeanUtils.cloneBean(oi);
			oi.setOrganid(request.getParameter("organid"));
			oi.setWarehouseid(request.getParameter("warehouseid"));
			oi.setIncomesort(RequestTool.getInt(request, "incomesort"));
			oi.setBillno(request.getParameter("billno"));
			oi.setRemark(request.getParameter("remark"));
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
			//删除原来的单号明细
			aoid.delOtherIncomeDetailByPbID(oiid);
			WarehouseBitDafService wbds = new WarehouseBitDafService("other_income_idcode_all","oiid",oi.getWarehouseid());
			wbds.del(oi.getId(), productid);
			for (int i = 0; i < productid.length; i++) {
				OtherIncomeDetailAll oid = new OtherIncomeDetailAll();
				oid.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"other_income_detail_all", 0, "")));
				oid.setOiid(oiid);
				oid.setProductid(productid[i]);
				oid.setProductname(productname[i]);
				oid.setSpecmode(specmode[i]);
				oid.setUnitid(unitid[i]);
				oid.setBatch(batch[i]);
//				oid.setUnitprice(unitprice[i]);
				oid.setQuantity(quantity[i]);
//				oid.setSubsum(oid.getUnitprice() * oid.getQuantity());
				aoid.addOtherIncomeDetail(oid);
				wbds.add(oi.getId(), oid.getProductid(), oid.getUnitid(), oid.getQuantity(), oid.getBatch());
			}

			aoi.updOtherIncomeAll(oi);

			request.setAttribute("result", "databases.upd.success");
			UsersBean users = UserManager.getUser(request);
			DBUserLog.addUserLog(request, "编号:"+oiid, oldoi, oi);
			return mapping.findForward("updresult");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

		return new ActionForward(mapping.getInput());
	}
}
