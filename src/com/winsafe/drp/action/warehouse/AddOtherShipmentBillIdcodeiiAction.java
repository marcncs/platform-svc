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
import com.winsafe.drp.dao.AppOtherShipmentBillIdcode;
import com.winsafe.drp.dao.Idcode;
import com.winsafe.drp.dao.OtherShipmentBillIdcode;
import com.winsafe.drp.exception.IdcodeException;
import com.winsafe.drp.server.CodeRuleService;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AddOtherShipmentBillIdcodeiiAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		AppOtherShipmentBillIdcode app = new AppOtherShipmentBillIdcode();
		AppCodeUnit appcu = new AppCodeUnit();
		CodeRuleService crs = new CodeRuleService();
		AppICode appicode = new AppICode();
		AppIdcode appic = new AppIdcode();
		String idcode = request.getParameter("idcode");
		idcode = crs.addQuantityCode(idcode);
		String billid = request.getParameter("billid");
		String productid = request.getParameter("prid");

		String wid = request.getParameter("wid");
		String batch = request.getParameter("batch");
		
		String warehousebit = request.getParameter("warehousebit");		
		String result = "";
		
		int issplit=0;
		super.initdata(request);try{			
			String codeProductid = appicode.getProductIdByLcode(crs.getLcode(idcode));
			if ( !productid.equals(codeProductid) ){
				result="该条码与当前产品不匹配，请检查条码！";	
				addMsg(request, response, result);
				return null;
			}
			if ( !batch.equals(crs.getBatch(idcode)) ){
				result="该条码与当前产品批次不匹配，请检查条码！";	
				addMsg(request, response, result);
				return null;
			}
			
			OtherShipmentBillIdcode oldpii=app.getOtherShipmentBillIdcodeByidcode(productid, billid, idcode);
			if ( oldpii != null ){
				result="该条码已经存在当前列表中，不能新增！";
				addMsg(request, response, result);
				return null;
			}			

			
			Idcode ic =  appic.getIdcodeById(idcode);
			if ( ic == null ){
				
				String startno = crs.getStartNo(idcode);
				String endno = crs.getEndNo(idcode);
				Idcode bic = appic.getIdcodeByWLM(startno, endno);
				if ( bic == null || !bic.getWarehouseid().equals(wid) || !bic.getWarehousebit().equals(warehousebit) ){
					result="该条码不存在或者不存在"+warehousebit+"仓位中，不能出库！";
					addMsg(request, response, result);
					return null;
				}else{
					if ( appic.isBreak(startno, endno) ){
						result="该条码已被拆过，不能出库！";
						addMsg(request, response, result);
						return null;
					}
				}
				issplit=1;
			}else{
				if ( ic.getIsuse() == 0 || !ic.getWarehouseid().equals(wid) || !ic.getWarehousebit().equals(warehousebit)){
					result="该条码不存在或者不存在"+warehousebit+"仓位中，不能出库！";
					addMsg(request, response, result);
					return null;
				}				
			}
			

			OtherShipmentBillIdcode pi = new OtherShipmentBillIdcode();
			pi.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("other_shipment_bill_idcode", 0, "")));
			pi.setOsid(billid);
			pi.setProductid(productid);
			pi.setIsidcode(1);
			pi.setWarehousebit(request.getParameter("warehousebit"));
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
			pi.setIssplit(issplit);
			pi.setMakedate(DateUtil.getCurrentDate());
			app.addOtherShipmentBillIdcode(pi);
			result="条码新增成功！";
			addMsg(request, response, result);
			return null;
			
			
		}catch (IdcodeException e) {
			result = "条码不符合规则，请检查条码！";
			addMsg(request, response, result);
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	    
		return null;
	}
	
	private void addMsg(HttpServletRequest request, HttpServletResponse response, String msg) throws Exception{
		JSONObject json = new JSONObject();			
		json.put("result", msg);				
		response.setContentType("text/html; charset=UTF-8");
	    response.setHeader("Cache-Control", "no-cache");
	    PrintWriter out = response.getWriter();
	    out.print(json.toString());
	    out.close();
	}
	
}
