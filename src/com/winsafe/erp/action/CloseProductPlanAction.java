package com.winsafe.erp.action;


import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.HibernateException;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.erp.dao.AppProductPlan;
import com.winsafe.erp.pojo.ProductPlan;
import com.winsafe.erp.services.CartonSeqServices;
import com.winsafe.sap.util.SapConfig;

public class CloseProductPlanAction extends BaseAction {

	private AppProductPlan appProductPlan = new AppProductPlan();
	private static Logger logger = Logger.getLogger(CloseProductPlanAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		initdata(request);

		try {
			CartonSeqServices css = new CartonSeqServices();
			//Integer id = Integer.valueOf(request.getParameter("ID"));
			String pid = request.getParameter("ID");
			ProductPlan plan = appProductPlan.getProductPlanByIDString(pid);
			plan.setCloseFlag(1); 
			plan.setCloseMan(users.getUserid());
			appProductPlan.updProductPlan(plan);
			
			//默认包装单位
			Integer defaultUnitId = Integer.parseInt((String)SapConfig.getSapConfig().get("unitId"));
			AppFUnit appFUnit = new AppFUnit();
			FUnit fUnit = appFUnit.getFUnit(plan.getProductId(), defaultUnitId);
			
			//根据机构编号获取可用仓库信息
			AppWarehouse appw = new AppWarehouse();
			Warehouse warehouse = appw.getAvaiableWarehouseByOID(plan.getOrganId());
			if(warehouse==null){
				request.setAttribute("result", "计划结束失败");
				throw new Exception("基础数据错误，编号为"+ plan.getOrganId() +"的收货机构不存在可用仓库");
			}
			if(plan.getPrintJobId()!=null){
				//释放箱码序号
				css.releaseCartonSeqByPlanId(plan.getId());
				deleteReleaseCartonCode(plan.getId());
				deleteReleasePrimaryCode(plan.getId());
				addIdcodeByPrintJob(plan.getPrintJobId(), fUnit.getFunitid(), warehouse.getId(), fUnit.getXquantity());
				//结束箱码序号关联
				css.closeCartonSeq(plan.getId(), plan.getPrintJobId());
			}else{  
//				throw new Exception("2016.3.3号后审批的计划才能关闭");
			}
			
			DBUserLog.addUserLog(request, "编号：" + pid);
			request.setAttribute("result", "计划结束成功");
		} catch (Exception e) {
			logger.error("ApproveProductPlanAction  error:", e);
			throw  e;
		}
		return mapping.findForward("success");
	}
	private void deleteReleasePrimaryCode(long planid) throws HibernateException, SQLException, Exception {
		String sq = "delete from PRIMARY_CODE where PRIMARY_CODE in (select code from prepare_code pc where pc.productplan_id="+planid+" and pc.isrelease=1)";
		EntityManager.executeUpdate(sq); 
	}
	
	public void addIdcodeByPrintJob(Integer printJobId, Integer unitId, String warehousId, Double packQuantity) throws Exception {
		String sql = "insert into idcode(IDCODE, PRODUCTID, PRODUCTNAME,BATCH,PRODUCEDATE, UNITID, QUANTITY,ISUSE,ISOUT,MAKEORGANID,CARTONCODE,WAREHOUSEID,PACKQUANTITY,WAREHOUSEBIT,PALLETCODE)" +
						   " select cc.CARTON_CODE , cc.PRODUCT_ID, pj.MATERIAL_NAME, pj.PACKAGING_DATE||pj.MATERIAL_BATCH_NO, pj.PRODUCTION_DATE,"+unitId+",1,1,0,pj.CREATE_USER,cc.CARTON_CODE," +warehousId+","+packQuantity+"," +
						   		"'"+Constants.WAREHOUSE_BIT_DEFAULT +"',cc.PALLET_CODE"+
						   " from CARTON_CODE cc join PRINT_JOB pj on CC.PRINT_JOB_ID = PJ.PRINT_JOB_ID where PJ.PRINT_JOB_ID = " + printJobId+ " " +
						   		"and not exists(select idcode from idcode a where a.idcode=cc.CARTON_CODE)" +
						   		" and not exists(select code from prepare_code pc where pc.code=cc.CARTON_CODE and pc.isrelease=1)";
		EntityManager.executeUpdate(sql);
	}
	
	public void deleteReleaseCartonCode(Long planid) throws Exception {
		String sq = "delete from CARTON_CODE where CARTON_CODE in (select code from prepare_code pc where pc.productplan_id="+planid+" and pc.isrelease=1)";
		EntityManager.executeUpdate(sq);
	}

}
