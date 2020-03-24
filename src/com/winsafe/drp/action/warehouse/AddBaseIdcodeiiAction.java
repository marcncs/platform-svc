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
import com.winsafe.drp.dao.Idcode;
import com.winsafe.drp.exception.IdcodeException;
import com.winsafe.drp.server.CodeRuleService;

public abstract class AddBaseIdcodeiiAction extends BaseAction {
	
	protected AppCodeUnit appcu = new AppCodeUnit();
	protected CodeRuleService crs = new CodeRuleService();
	protected AppICode appicode = new AppICode();
	protected AppIdcode appic = new AppIdcode();
	
	protected String idcode="";
	protected String billid="";
	protected String productid="";
	protected String warehousebit="";

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
			
		idcode = request.getParameter("idcode");
		//idcode = crs.addQuantityCode(idcode);
		billid = request.getParameter("billid");
		productid = request.getParameter("prid");
		warehousebit = request.getParameter("warehousebit");
		super.initdata(request);try{
			String codeProductid = appicode.getProductIdByLcode(crs.getLcode(idcode));
			if ( !productid.equals(codeProductid) ){
				addMsg(request, response, "该条码与当前产品不匹配，请检查条码！");
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
			
			
			addIdcode(request, response);			
		}catch (IdcodeException e) {
			addMsg(request, response, "条码不符合规则，请检查条码！"+ e.getMessage());
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	protected void addMsg(HttpServletRequest request, HttpServletResponse response, String msg) throws Exception{
		JSONObject json = new JSONObject();			
		json.put("result", msg);				
		response.setContentType("text/html; charset=UTF-8");
	    response.setHeader("Cache-Control", "no-cache");
	    PrintWriter out = response.getWriter();
	    out.print(json.toString());
	    out.close();
	}
	
	protected abstract void addIdcode(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
}
