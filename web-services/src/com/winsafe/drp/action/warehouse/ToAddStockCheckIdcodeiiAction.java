package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppStockCheck;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.StockCheck;
import com.winsafe.drp.dao.WarehouseBit;

public class ToAddStockCheckIdcodeiiAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);try{
			String billno = request.getParameter("billno");
			AppStockCheck appsc = new AppStockCheck();
			StockCheck sc = appsc.getStockCheckByID(billno);
			
			if ( sc.getIsaudit() == 1 ){
				request.setAttribute("result", "databases.record.isapprovenooperator");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			if ( !sc.getWarehousebit().equals("") ){
				request.setAttribute("wbit", getText(sc.getWarehousebit()));
			}else{
				AppWarehouse aw = new AppWarehouse();
				List bitlist = aw.getWarehouseBitByWid(sc.getWarehouseid());				
				request.setAttribute("wbit", getSelect(bitlist));
			}
			
			request.setAttribute("sc", sc);			
			return mapping.findForward("toadd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
	
	private String getText(String wbit) {
		return "<input type='text' name='warehousebit' value='"+wbit+"' size='8' readonly>";
	}
	
	private String getSelect(List<WarehouseBit> bitlist){
		StringBuffer sb = new StringBuffer();
		sb.append("<select name='warehousebit' >");
		for(WarehouseBit wb : bitlist ){			
			sb.append("<option value='"+wb.getWbid()+"'>"+wb.getWbid()+"</option>");			
		}
		sb.append("</select>");
		return sb.toString();
	}

}
