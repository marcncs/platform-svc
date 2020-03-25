package com.winsafe.erp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.erp.dao.AppUnitInfo;
import com.winsafe.erp.pojo.UnitInfo;
import com.winsafe.erp.pojo.UnitInfoForm;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;

public class ListUnitInfoAction extends BaseAction {
	private static Logger logger = Logger.getLogger(ListUnitInfoAction.class);
	private AppProduct appProduct = new AppProduct();
	private AppUnitInfo appUnitInfo = new AppUnitInfo();

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		int pagesize = 10;
		initdata(request);
		Map map = new HashMap(request.getParameterMap());
		Map tmpMap = EntityManager.scatterMap(map);
		String Condition = " organId in (select makeorganid from Warehouse where id in (select ruw.warehouseId from RuleUserWh ruw where ruw.userId = "
			+ userid + " and activeFlag = 1))";
		
//		String 	condition = " o.isrepeal=0 and organType = 1 and (organModel = 1 or organModel = 2)  " +
//		"and  o.id in (select visitorgan from UserVisit as uv where  uv.userid=" + userid + ")";
		
		String[] tablename = { "UnitInfo" };
		// 查询条件
		String whereSql = EntityManager.getTmpWhereSql(map, tablename);
		// 关键字查询条件
		String blur = DbUtil.getOrBlur(map, tmpMap, "id", "productId");
		whereSql = whereSql + blur + Condition;
		whereSql = DbUtil.getWhereSql(whereSql);

		List<UnitInfo> configs = appUnitInfo.getUnitInfos(request, pagesize, whereSql);
		List<UnitInfoForm> UnitInfoForms = new ArrayList<UnitInfoForm>();
		for (UnitInfo UnitInfo : configs) {
			UnitInfoForm UnitInfoForm = new UnitInfoForm();
			UnitInfoForm.setId(UnitInfo.getId());
			UnitInfoForm.setIsactive(UnitInfo.getIsactive());
			UnitInfoForm.setFormatDate(DateUtil.formatDateTime(UnitInfo.getModifiedDate()));
			UnitInfoForm.setModifiedUserID(UnitInfo.getModifiedUserID());
			UnitInfoForm.setProductId(UnitInfo.getProductId());
			UnitInfoForm.setOrganId(UnitInfo.getOrganId());
			UnitInfoForm.setUnitCount(UnitInfo.getUnitCount());
			UnitInfoForm.setLabelType(UnitInfo.getLabelType());
			UnitInfoForm.setUnitId(UnitInfo.getUnitId());
			UnitInfoForm.setNeedRepackage(UnitInfo.getNeedRepackage());
			Product product = appProduct.getProductByID(UnitInfoForm.getProductId());
			if (product != null) {
				UnitInfoForm.setProductName(product.getProductname());
				UnitInfoForm.setMcode(product.getmCode());
				UnitInfoForm.setSpecmode(product.getSpecmode());
			}else{
				UnitInfoForm.setProductName("");
				UnitInfoForm.setMcode("");
				UnitInfoForm.setSpecmode("");
			} 
			UnitInfoForm.setNeedCovertCode(UnitInfo.getNeedCovertCode());
			UnitInfoForms.add(UnitInfoForm);
		}

		request.setAttribute("configs", UnitInfoForms);
		DBUserLog.addUserLog(request, "列表");
		return mapping.findForward("list");
	}

}
