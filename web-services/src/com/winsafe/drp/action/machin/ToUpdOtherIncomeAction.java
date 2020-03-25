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
import com.winsafe.drp.dao.AppOtherIncomeAll;
import com.winsafe.drp.dao.AppOtherIncomeDetailAll;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.OtherIncomeAll;
import com.winsafe.drp.dao.OtherIncomeDetailAll;
import com.winsafe.drp.dao.OtherIncomeDetailForm;
import com.winsafe.drp.dao.Product;
import com.winsafe.hbm.util.Internation;

public class ToUpdOtherIncomeAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppProduct appProduct = new AppProduct();
		AppFUnit appFUnit = new AppFUnit();
		String id = request.getParameter("ID");
		super.initdata(request);
		try {
			AppOtherIncomeAll aoi = new AppOtherIncomeAll();

			OtherIncomeAll oi = aoi.getOtherIncomeByID(id);

			if (oi.getIsaudit() == 1) {
				request.setAttribute("result", "databases.record.approvestatus2");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			AppOtherIncomeDetailAll apid = new AppOtherIncomeDetailAll();
			List<OtherIncomeDetailAll> apils = apid.getOtherIncomeDetailByOiid(id);
			ArrayList als = new ArrayList();
			for (int i = 0; i < apils.size(); i++) {
				OtherIncomeDetailForm smdf = new OtherIncomeDetailForm();
				OtherIncomeDetailAll smd = (OtherIncomeDetailAll) apils.get(i);
				smdf.setProductid(smd.getProductid());
				smdf.setProductname(smd.getProductname());
				smdf.setNccode(smd.getNccode());
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
			AppOrgan appo = new AppOrgan();
			Organ o = appo.getOrganByWarehouseid(oi.getWarehouseid());
			request.setAttribute("o", o);
			request.setAttribute("oif", oi);
			request.setAttribute("als", als);
			return mapping.findForward("toupd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
