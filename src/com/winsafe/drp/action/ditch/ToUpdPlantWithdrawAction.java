package com.winsafe.drp.action.ditch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppOrganWithdraw;
import com.winsafe.drp.dao.AppOrganWithdrawDetail;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.OrganWithdraw;
import com.winsafe.drp.dao.OrganWithdrawDetail;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.hbm.util.Internation;

/**
 * @author : jerry
 * @version : 2009-8-24 下午02:52:06
 * www.winsafe.cn
 */
public class ToUpdPlantWithdrawAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 出始化
		initdata(request);
		AppOrganWithdrawDetail appAMAD = new AppOrganWithdrawDetail();
		AppOrganWithdraw appAMA = new AppOrganWithdraw();
		AppWarehouse ap = new AppWarehouse();
		AppOrgan ao = new AppOrgan();
		AppProduct appProduct = new AppProduct();
		AppFUnit appFUnit = new AppFUnit();
		
		try{
			String id = request.getParameter("ID");
			OrganWithdraw ama = appAMA.getOrganWithdrawByID(id);
			Warehouse w = ap.getWarehouseByID(ama.getWarehouseid());
			Warehouse inw = ap.getWarehouseByID(ama.getInwarehouseid());
			Organ o =ao.getOrganByID(ama.getReceiveorganid());
			
			if(ama.getIsaudit()==1){
				String result = "databases.record.lock";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			List<OrganWithdrawDetail> listAmad = appAMAD.getOrganWithdrawDetailByOwid(id);
			
			for(OrganWithdrawDetail detail : listAmad){
				if(detail == null) continue;
				Product product = appProduct.getProductByID(detail.getProductid());
				if(product == null) continue;
				detail.setCountunit(product.getCountunit());
				detail.setCountUnitName(Internation.getStringByKeyPositionDB("CountUnit", product.getCountunit()));
				// 转化数量为计量单位的数量
				Double quantity = appFUnit.getQuantity(detail.getProductid(), detail.getUnitid(), detail.getQuantity());
				quantity = quantity * product.getBoxquantity();
				detail.setcUnitQuantity(quantity);
				
				//获取产品的所有单位
				List<FUnit> unitList = appFUnit.getFUnitByProductID(product.getId());
				List<Map> uMapList = new ArrayList<Map>();
				for(FUnit fUnit : unitList){
					Map uMap = new HashMap<String, String>();
					uMap.put("unitId", fUnit.getFunitid());
					uMap.put("xQuantity", fUnit.getXquantity());
					uMapList.add(uMap);
				}
				//增加计量单位
				Map uLMap = new HashMap<String, String>();
				uLMap.put("unitId", 0);
				uLMap.put("xQuantity", product.getBoxquantity());
				uMapList.add(uLMap);
				detail.setUnitList(uMapList.toString());
			}
			
			
			request.setAttribute("list", listAmad);
			request.setAttribute("ama", ama);
			request.setAttribute("owname", w.getWarehousename());
			request.setAttribute("wname", inw.getWarehousename());
			if(o!=null){
				request.setAttribute("oname", o.getOrganname());
			}
			
			return mapping.findForward("upd");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return super.execute(mapping, form, request, response);
	}
}
