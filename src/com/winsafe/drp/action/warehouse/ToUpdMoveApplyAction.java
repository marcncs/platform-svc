package com.winsafe.drp.action.warehouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppMoveApply;
import com.winsafe.drp.dao.AppMoveApplyDetail;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.MoveApply;
import com.winsafe.drp.dao.MoveApplyDetail;
import com.winsafe.drp.dao.MoveApplyDetailForm;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.JProperties;
import com.winsafe.hbm.util.Internation;

public class ToUpdMoveApplyAction extends BaseAction {
	
	private OrganService os = new OrganService();
	private AppFUnit appFUnit = new AppFUnit();
	private AppProduct appProduct = new AppProduct();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		super.initdata(request);
		try{
			AppMoveApply asm = new AppMoveApply();
			MoveApply sm = asm.getMoveApplyByID(id);

			if (sm.getIsratify().intValue() == 1) { 
				String result = "databases.record.ratify.lock";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

		

			AppMoveApplyDetail asmd = new AppMoveApplyDetail();
			List smdls = asmd.getMoveApplyDetailByAmID(id);
			ArrayList als = new ArrayList();

			for (int i = 0; i < smdls.size(); i++) {
				MoveApplyDetailForm smdf = new MoveApplyDetailForm();
				MoveApplyDetail o = (MoveApplyDetail) smdls.get(i);
				smdf.setProductid(o.getProductid());
				smdf.setProductname(o.getProductname());
				smdf.setSpecmode(o.getSpecmode());
				smdf.setUnitid(o.getUnitid());
				smdf.setUnitidname(Internation.getStringByKeyPositionDB(
						"CountUnit", o.getUnitid()));
				smdf.setUnitprice(o.getUnitprice());
				smdf.setQuantity(o.getQuantity());
				smdf.setCanquantity(0d);
				smdf.setSubsum(o.getSubsum());
				
				// 转化数量为计量单位的数量
				Product product = appProduct.getProductByID(o.getProductid());
				smdf.setCountunit(product.getCountunit());
				smdf.setCountUnitName(Internation.getStringByKeyPositionDB("CountUnit", product.getCountunit()));
				// 转化数量为计量单位的数量
				Double quantity = appFUnit.getQuantity(o.getProductid(), o.getUnitid(), o.getQuantity());
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
			Organ organ = os.getOrganByID(users.getMakeorganid());
			request.setAttribute("organRank", organ.getRank());
			request.setAttribute("organType", organ.getOrganType());
			request.setAttribute("MakeOrganID", users.getMakeorganid());
			request.setAttribute("smf", sm);
			request.setAttribute("als", als);
			
			Properties pro = JProperties.loadProperties("system.properties", JProperties.BY_CLASSLOADER);
			String moveApplyOrganId = pro.getProperty("moveApplyOrganId")!=null?pro.getProperty("moveApplyOrganId"):"1";
			
			request.setAttribute("moveApplyOrganId", moveApplyOrganId);
			
			DBUserLog.addUserLog(request, "编号的：" + sm.getId());
			return mapping.findForward("toupd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
