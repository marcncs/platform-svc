package com.winsafe.drp.action.sys;

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
import com.winsafe.drp.dao.AppOrganVisit;
import com.winsafe.drp.dao.AppRuleUserWH;
import com.winsafe.drp.dao.AppUserVisit;
import com.winsafe.drp.dao.AppWarehouseVisit;
import com.winsafe.drp.dao.OrganVisit;
import com.winsafe.drp.dao.RuleUserWh;
import com.winsafe.drp.dao.UserVisit;
import com.winsafe.drp.dao.ValidateWarehouse;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.dao.WarehouseBit;
import com.winsafe.drp.dao.WarehouseVisit;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.drp.util.NumberUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AddWarehouseAction extends BaseAction {
    private static Logger logger = Logger.getLogger(AddWarehouseAction.class);
    private WarehouseService aw = new WarehouseService();
    private AppRuleUserWH appRuWH = new AppRuleUserWH();
    private AppUserVisit appUserVisit = new AppUserVisit();
    private AppOrganVisit appOrganVisit = new AppOrganVisit();
    private AppWarehouseVisit appWarehouseVisit = new AppWarehouseVisit();
    
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//dao层所需类
		initdata(request);
		
		try {
			ValidateWarehouse vw = new ValidateWarehouse();
			Map map = new HashMap(request.getParameterMap()); 
			MapUtil.mapToObjectIgnoreCase(map, vw);
			
			String oid = request.getParameter("oid");
			Warehouse w = new Warehouse();
			Integer warehouseid = Integer.valueOf(MakeCode.getExcIDByRandomTableName("warehouse", 0, ""));
			w.setId(MakeCode.getFormatNums(warehouseid, 5));
			w.setWarehousename(vw.getWarehousename());
			w.setDept(vw.getDept());
			w.setUserid(0);
			w.setUsername(vw.getUsername());
			w.setWarehousetel(vw.getWarehousetel());
			w.setWarehouseproperty(vw.getWarehouseproperty());
			w.setProvince(vw.getProvince());
			w.setCity(vw.getCity());
			w.setAreas(vw.getAreas());
			w.setWarehouseaddr(vw.getWarehouseaddr());
			w.setMakeorganid(oid);
			w.setMakedeptid(vw.getDept());
			w.setUseflag(vw.getUseflag());
			w.setIsautoreceive(vw.getIsautoreceive());
			w.setRemark(vw.getRemark());
			w.setNccode(request.getParameter("nccode"));
			w.setShortname(request.getParameter("shortname"));
			// 设置上限
			w.setHighNumber(request.getParameter("highNumber"));
			// 设置下限
			w.setBelowNumber(request.getParameter("belowNumber"));
			// 设置是否库存
			w.setIsMinusStock(NumberUtil.getInteger(request.getParameter("isMinusStock")));
			w.setCreationTime(DateUtil.getCurrentDate());
			aw.addWarehouse(w); 
			WarehouseBit wb = new WarehouseBit();
			wb.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("warehouse_bit", 0, "")));
			wb.setWid(w.getId());
			wb.setWbid("000");
			aw.addWarehouseBit(wb);
			// 默认为仓库配置管辖用户权限
			addRuleUserWh(oid,w.getId());
			// 默认为仓库配置许可用户权限
			addWarehouseVisit(oid,w.getId());
			
			request.setAttribute("result", "databases.add.success");
			DBUserLog.addUserLog(request, "新增编号:" + w.getId());
			return mapping.findForward("addresult");
		} catch (Exception e) {
			logger.error("",e);
			throw e;
		} finally {
		}
	}
	
	/**
	 * 默认为仓库配置管辖用户权限
	 */
	private void addRuleUserWh(String oid,String wid) throws Exception{
		List<UserVisit>  uvList = appUserVisit.queryByOrganId(oid);
		for(UserVisit userVisit : uvList){
			if(userVisit != null){
				RuleUserWh ruleUserWh = new RuleUserWh();
				ruleUserWh.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("rule_user_wh", 0, "")));
				ruleUserWh.setUserId(userVisit.getUserid());
				ruleUserWh.setOrganId(oid);
				ruleUserWh.setWarehouseId(wid);
				appRuWH.addRuleUserWh(ruleUserWh);
			}
		}
	}
	
	/**
	 * 默认为仓库配置许可用户权限
	 */
	private void addWarehouseVisit(String oid,String wid) throws Exception{
		List<OrganVisit> ovList = appOrganVisit.queryByOrganId(oid);
		for(OrganVisit organVisit : ovList){
			if(organVisit != null){
				WarehouseVisit wv = new WarehouseVisit();
				wv.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("warehouse_visit", 0, "")));
				wv.setUserid(organVisit.getUserid());
				wv.setOrganId(oid);
				wv.setWid(wid);
				appWarehouseVisit.addWarehouseVisit(wv);
			}
		}
	}
	
	
}
