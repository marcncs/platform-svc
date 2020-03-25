package com.winsafe.drp.action.ditch;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppOrganWithdraw;
import com.winsafe.drp.dao.AppOrganWithdrawDetail;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.OrganWithdraw;
import com.winsafe.drp.dao.OrganWithdrawDetail;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.StockMoveDetail;
import com.winsafe.drp.server.OrganWithdrawService;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.BeanCopy;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

/**
 Update By WenPing 2012-6-20
 新增退货单
 */
public class AddOrganWithdrawAction extends BaseAction {
	Logger logger = Logger.getLogger(AddOrganWithdrawAction.class);
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		initdata(request);
		AppOrganWithdraw appAMA = new AppOrganWithdraw();
		AppOrganWithdrawDetail appAMAD = new AppOrganWithdrawDetail();
		AppProduct ap = new AppProduct();
		AppFUnit  af = new AppFUnit();
		OrganWithdrawService service = new OrganWithdrawService();
		
		try {
			OrganWithdraw ow = new OrganWithdraw();
			BeanCopy.copy(ow, request);
			String pwid = MakeCode.getExcIDByRandomTableName("organ_withdraw", 2, "OW");
			ow.setWarehouseid(request.getParameter("outwarehouseid"));
			ow.setId(pwid);
			ow.setMakeid(userid);
			ow.setMakeorganid(users.getMakeorganid());
			ow.setMakedeptid(users.getMakedeptid());
			ow.setMakedate(DateUtil.getCurrentDate());
			//备注
			ow.setWithdrawcause(request.getParameter("remark"));
			//退货原因
			ow.setRemark(request.getParameter("returnreason"));
			
			ow.setIsaudit(0);
			ow.setAuditid(0);
			ow.setIsratify(0);
			ow.setIsaffirm(0);
			ow.setIscomplete(0);
			ow.setReceiveid(0);
			ow.setIsblankout(0);
			ow.setBlankoutid(0);
			ow.setPrinttimes(0);
			ow.setTakestatus(0);
			ow.setBonusStatus(0); 

			String[] strproductid = request.getParameterValues("productid");
			String[] strproductname = request.getParameterValues("productname");
			String[] strspecmode = request.getParameterValues("specmode");
			int[] unitid = RequestTool.getInts(request, "unitid");
			String[] strbatch = request.getParameterValues("batch");
			String[] strNccode = request.getParameterValues("nccode");
			double[] quantity = RequestTool.getDoubles(request, "quantity");
			
			Double totalsum = 0.00;
			OrganWithdrawDetail owd = null;
			
			Product p;
			List<OrganWithdrawDetail> owdList = new ArrayList<OrganWithdrawDetail>();
			//有产品明细单
			if (strproductid != null) {
				//生成产品明细
				for (int i = 0; i < strproductid.length; i++) {
					owd = new OrganWithdrawDetail();
					Integer id = Integer.valueOf(MakeCode.getExcIDByRandomTableName("organ_withdraw_detail", 0, ""));
					owd.setId(id);
					owd.setProductid(strproductid[i]);
					owd.setOwid(pwid);
					owd.setProductname(strproductname[i]);
					owd.setSpecmode(strspecmode[i]);
					
					owd.setUnitid(Integer.valueOf(unitid[i]));
					//owd.setBatch(strbatch[i]);
					owd.setUnitprice(0d);
					
					owd.setQuantity(quantity[i]);
					owd.setNccode(strNccode[i]);
					owd.setRatifyquantity(0d);
					owd.setBoxnum(0);
					owd.setScatternum(0d);
					owd.setTakequantity(0d);
					owd.setSubsum(owd.getUnitprice() * owd.getQuantity());
					appAMAD.save(owd);
					totalsum += owd.getSubsum();
					owdList.add(owd);
				}
			}
			ow.setKeyscontent(ow.getId() + "," + ow.getPorganid() + "," + ow.getPorganname() + "," + ow.getPlinkman() + ","
					+ ow.getTel());
			ow.setTotalsum(totalsum);
			appAMA.save(ow);
			DBUserLog.addUserLog(request,"编号：" + pwid);
			
			// 默认复核单据,如果单据中的产品库存不足,不复核
			//if(service.checkStock(ow, owdList)){
				// 生成检货出库单
				service.addTakeTicket(ow, owdList, users);
				// 设置复核
				ow.setIsaudit(1);
				ow.setAuditid(userid);
				ow.setAuditdate(DateUtil.getCurrentDate());
				// 设置批准
				ow.setIsratify(1);
				ow.setRatifyid(userid);
				ow.setRatifydate(DateUtil.getCurrentDate());
				// 设置确认
				ow.setIsaffirm(1);
				ow.setAffirmid(userid);
				ow.setAffirmdate(DateUtil.getCurrentDate());
				appAMA.update(ow);
			//}
			
			request.setAttribute("result", "databases.add.success");
			return mapping.findForward("success");

		} catch (Exception ex) {
			logger.error("", ex);
			throw ex;
		}
	}
}
