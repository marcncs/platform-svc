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
import com.winsafe.drp.dao.AppOrganTradesTIdcode;
import com.winsafe.drp.dao.Idcode;
import com.winsafe.drp.dao.OrganTradesTIdcode;
import com.winsafe.drp.exception.IdcodeException;
import com.winsafe.drp.server.CodeRuleService;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AddOrganTradesTIdcodeiiAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		AppOrganTradesTIdcode app = new AppOrganTradesTIdcode();
		AppCodeUnit appcu = new AppCodeUnit();
		CodeRuleService crs = new CodeRuleService();
		AppICode appicode = new AppICode();
		AppIdcode appic = new AppIdcode();
		String idcode = request.getParameter("idcode");
		idcode = crs.addQuantityCode(idcode);
		String billid = request.getParameter("billid");
		String productid = request.getParameter("prid");
		String batch = request.getParameter("batch");
		super.initdata(request);try{
			String codeProductid = appicode.getProductIdByLcode(crs.getLcode(idcode));
			if ( !productid.equals(codeProductid) ){
				addMsg(request, response, "该条码与当前产品不匹配，请检查条码！");
				return null;
			}
			if ( !batch.equals(crs.getBatch(idcode)) ){
				addMsg(request, response, "该条码与当前产品批次不匹配，请检查条码！");
				return null;
			}
			OrganTradesTIdcode oldpii=app.getOrganTradesTIdcodeByidcode(productid, billid, idcode);
			if ( oldpii != null ){
				addMsg(request, response, "该条码已经存在当前列表中，不能新增！");
				return null;
			}
			
			Idcode ic =  appic.getIdcodeById(idcode);
			if ( ic != null && ic.getIsuse() == 1 ){
				addMsg(request, response, "该条码已经存在，不能新增！");
				return null;
			}
			if ( ic != null && ic.getIsuse() == 0 && ic.getQuantity().doubleValue() != ic.getFquantity().doubleValue()){
				addMsg(request, response, "该条码已经存在，不能新增！");
				return null;
			}
			if ( ic == null ){
				
				String startno = crs.getStartNo(idcode);
				String endno = crs.getEndNo(idcode);
				if ( appic.isAlreadyByWLM(startno, endno) ){
					addMsg(request, response, "该条码已经存在，不能新增！");
					return null;
				}
				if ( appic.isBreak(startno, endno) ){
					addMsg(request, response, "该条码已经存在，不能新增！");
					return null;
				}
			}
			
			OrganTradesTIdcode pi = new OrganTradesTIdcode();
			pi.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("organ_trades_t_idcode", 0, "")));
			pi.setOtid(billid);
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
			pi.setMakedate(DateUtil.getCurrentDate());
			app.addOrganTradesTIdcode(pi);
			addMsg(request, response, "条码新增成功！");
			return null;
			
			
		}catch (IdcodeException e) {
			addMsg(request, response, "条码不符合规则，请检查条码！");
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
