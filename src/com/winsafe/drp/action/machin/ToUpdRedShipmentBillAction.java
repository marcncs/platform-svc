package com.winsafe.drp.action.machin;

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
import com.winsafe.drp.dao.AppOtherShipmentBillAll;
import com.winsafe.drp.dao.AppOtherShipmentBillDAll;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.OtherIncomeDetailForm;
import com.winsafe.drp.dao.OtherShipmentBillAll;
import com.winsafe.drp.dao.OtherShipmentBillDAll;
import com.winsafe.drp.dao.Product;
import com.winsafe.hbm.util.Internation;

public class ToUpdRedShipmentBillAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		AppProduct appProduct = new AppProduct();
		AppFUnit appFUnit = new AppFUnit();
		super.initdata(request);try{
			AppOtherShipmentBillAll aosb = new AppOtherShipmentBillAll();

			OtherShipmentBillAll osb = aosb.getOtherShipmentBillByID(id);
			if (osb.getIsaudit() == 1) {
				request.setAttribute("result", "databases.record.lock");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			AppOtherShipmentBillDAll asbd = new AppOtherShipmentBillDAll();
			List<OtherShipmentBillDAll> slls = asbd.getOtherShipmentBillDetailBySbID(id);

			ArrayList als = new ArrayList();
			for (int i = 0; i < slls.size(); i++) {
				OtherIncomeDetailForm smdf = new OtherIncomeDetailForm();
				OtherShipmentBillDAll smd = (OtherShipmentBillDAll) slls.get(i);
				smdf.setProductid(smd.getProductid());
				smdf.setProductname(smd.getProductname());
				smdf.setNccode("");
				smdf.setSpecmode(smd.getSpecmode());
				smdf.setUnitid(smd.getUnitid());
				smdf.setUnitidname(Internation.getStringByKeyPositionDB(
						"CountUnit", smd.getUnitid().intValue()));
				smdf.setBatch(smd.getBatch());
				smdf.setCost(smd.getCost());
				smdf.setQuantity(smd.getQuantity());
				/**
				 * 用于页面显示
				 */
				// 转化数量为计量单位的数量
				Product product = appProduct.getProductByID(smdf.getProductid());
				smdf.setCountunit(product.getCountunit());
				smdf.setCountUnitName(Internation.getStringByKeyPositionDB("CountUnit", product.getCountunit()));
				// 转化数量为计量单位的数量
				Double quantity = appFUnit.getQuantity(smdf.getProductid(), smdf.getUnitid(), smdf.getQuantity());
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
			AppOrgan appo=new AppOrgan();
			Organ o=appo.getOrganByWarehouseid(osb.getWarehouseid());
			request.setAttribute("o", o);
			request.setAttribute("osbf", osb);
			request.setAttribute("als", als);

			return mapping.findForward("toupd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
