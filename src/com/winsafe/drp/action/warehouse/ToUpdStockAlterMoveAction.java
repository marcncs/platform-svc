package com.winsafe.drp.action.warehouse;

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
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.dao.AppStockAlterMoveDetail;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.StockAlterMove;
import com.winsafe.drp.dao.StockAlterMoveDetail;
import com.winsafe.drp.dao.StockAlterMoveDetailForm;
import com.winsafe.hbm.util.Internation;

public class ToUpdStockAlterMoveAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		initdata(request);
		AppStockAlterMove asm = new AppStockAlterMove();
		AppStockAlterMoveDetail asmd = new AppStockAlterMoveDetail();
		AppFUnit appFUnit = new AppFUnit();
		AppProduct appProduct = new AppProduct();
		
		try{
			String id = request.getParameter("ID");
			StockAlterMove sm = asm.getStockAlterMoveByID(id);

			if (sm.getIsaudit().intValue() == 1) { 
				String result = "databases.record.noupdate";
				request.setAttribute("result", result);
				return mapping.findForward("lock");
			}

			
			List<StockAlterMoveDetail> smdls = asmd.getStockAlterMoveDetailBySamID(id);
			ArrayList als = new ArrayList();
			for (int i = 0; i < smdls.size(); i++) {
				StockAlterMoveDetailForm smdf = new StockAlterMoveDetailForm();
				StockAlterMoveDetail samd = (StockAlterMoveDetail) smdls.get(i);
				smdf.setProductid(samd.getProductid());
				smdf.setProductname(samd.getProductname());
				smdf.setNccode(samd.getNccode());
				smdf.setSpecmode(samd.getSpecmode());
				smdf.setUnitid(samd.getUnitid());
				smdf.setUnitidname(Internation.getStringByKeyPositionDB("CountUnit", samd.getUnitid()));
				smdf.setBatch(samd.getBatch());
				smdf.setUnitprice(samd.getUnitprice());
				smdf.setQuantity(samd.getQuantity());
				smdf.setSubsum(samd.getSubsum());
				
				// 转化数量为计量单位的数量
				Product product = appProduct.getProductByID(samd.getProductid());
				smdf.setCountunit(product.getCountunit());
				smdf.setCountUnitName(Internation.getStringByKeyPositionDB("CountUnit", product.getCountunit()));
				// 转化数量为计量单位的数量
				Double quantity = appFUnit.getQuantity(samd.getProductid(), samd.getUnitid(), samd.getQuantity());
				quantity = quantity * product.getBoxquantity();
				smdf.setcUnitQuantity(quantity);
				
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
				smdf.setUnitList(uMapList.toString());
				
				als.add(smdf);
			}

			request.setAttribute("smf", sm);
			request.setAttribute("als", als);

			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
