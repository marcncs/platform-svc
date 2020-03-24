package com.winsafe.drp.action.warehouse;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCodeUnit;
import com.winsafe.drp.dao.AppICode;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppStockMoveIdcode;
import com.winsafe.drp.dao.StockMoveIdcode;
import com.winsafe.drp.exception.IdcodeException;
import com.winsafe.drp.server.CodeRuleService;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AddStockMoveIdcodeiiAction extends BaseAction {

	protected AppCodeUnit appcu = new AppCodeUnit();
	protected CodeRuleService crs = new CodeRuleService();
	protected AppICode appicode = new AppICode();
	protected AppIdcode appic = new AppIdcode();

	protected String idcode = "";
	protected String billid = "";
	protected String productid = "";
	protected String warehousebit = "";

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		idcode = request.getParameter("idcode");
		billid = request.getParameter("billid");
		productid = request.getParameter("prid");
		warehousebit = request.getParameter("warehousebit");
		super.initdata(request);
		try {
			String codeProductid = appicode.getProductIdByLcode(idcode.substring(2, 4));
			if (!productid.equals(codeProductid)) {
				addMsg(request, response, "该条码与当前产品不匹配，请检查条码！");
				return null;
			}
		} catch (IdcodeException e) {
			addMsg(request, response, "条码不符合规则，请检查条码！" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void addIdcode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppStockMoveIdcode app = new AppStockMoveIdcode();
		StockMoveIdcode oldpii = app.getStockMoveIdcodeByidcode(productid, billid, idcode);
		if (oldpii != null) {
			addMsg(request, response, "该条码已经存在当前列表中，不能新增！");
			return;
		}

		StockMoveIdcode pi = new StockMoveIdcode();
		pi.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("stock_move_idcode", 0, "")));
		pi.setSmid(billid);
		pi.setProductid(productid);
		pi.setIsidcode(1);
		pi.setWarehousebit(warehousebit);
		pi.setBatch(crs.getBatch(idcode));
		pi.setProducedate(crs.getProduceDate(idcode));
		pi.setValidate("");
		pi.setUnitid(appcu.getCodeUnitByID(crs.getUcode(idcode)).getUnitid());
		pi.setQuantity(1d);
		pi.setPackquantity(crs.getRealQuantity(idcode));
		pi.setIdcode(idcode);
		pi.setLcode(crs.getLcode(idcode));
		pi.setStartno(crs.getStartNo(idcode));
		pi.setEndno(crs.getEndNo(idcode));
		pi.setMakedate(DateUtil.getCurrentDate());
		app.addStockMoveIdcode(pi);
		addMsg(request, response, "条码新增成功！");
	}

	protected void addMsg(HttpServletRequest request, HttpServletResponse response, String msg) throws Exception {
		JSONObject json = new JSONObject();
		json.put("result", msg);
		response.setContentType("text/html; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		out.print(json.toString());
		out.close();
	}

}
