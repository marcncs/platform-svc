package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppProductIncome;
import com.winsafe.drp.dao.AppProductIncomeDetail;
import com.winsafe.drp.dao.AppProductIncomeIdcode;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.ProductIncome;
import com.winsafe.drp.dao.ProductIncomeDetail;
import com.winsafe.drp.dao.ProductIncomeIdcode;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class CancelAuditProductIncomeAction extends BaseAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UsersBean users = UserManager.getUser(request);
		int userid = users.getUserid();

		super.initdata(request);
		try
		{
			String piid = request.getParameter("PIID");
			AppProductIncome apb = new AppProductIncome();
			AppProductIncomeIdcode apidcode = new AppProductIncomeIdcode();
			//入库单详情dao
			AppProductIncomeDetail apid = new AppProductIncomeDetail();
			ProductIncome pb = apb.getProductIncomeByID(piid);
			AppProductStockpileAll apsa = new AppProductStockpileAll();
			AppFUnit af  =new AppFUnit();

			if (pb.getIsaudit() == 0)
			{
				request.setAttribute("result", "datebases.record.returnoperator");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			List<ProductIncomeIdcode> idcodelist = apidcode.getProductIncomeIdcodeByPiid(piid, 1);
			AppIdcode appidcode = new AppIdcode();
			//如果条码已使用或已经出库，则不能取消复核
//			String ids = getIdcodesByList(idcodelist);
//			if (appidcode.getIdcodeByIds(ids, 0) != null || appidcode.getIdcodeByIdsAndWarehouse(ids,pb.getWarehouseid())==null){
//				request.setAttribute("result", "databases.recode.idcodehasuse");
//				return new ActionForward("/sys/lockrecordclose.jsp");
//			}
			for (ProductIncomeIdcode ic : idcodelist)
			{
				if (appidcode.getIdcodeById(ic.getIdcode(), 0) != null || appidcode.getIdcodeByIdAndWarehouse(ic.getIdcode(),pb.getWarehouseid())==null)
				{
					request.setAttribute("result", "databases.recode.idcodehasuse1");
					return new ActionForward("/sys/lockrecordclose.jsp");
				}
			}
			List<ProductIncomeDetail> pidetails = apid.getProductIncomeDetailByPbId(piid);
			//如果库存不足，不能取消复核
			double stockQuantity= 0.0;
			double quantity= 0.0;
			for (ProductIncomeDetail pid : pidetails) {
				   stockQuantity = apsa.getProductStockpileAllByPIDWID(pid.getProductid(), pb.getWarehouseid(), pid.getBatch());
				   quantity = af.getQuantity(pid.getProductid(), 1, pid.getConfirmQuantity());
				  if(quantity>stockQuantity){
					  request.setAttribute("result", "当前批次产品库存不足，不能取消复核");
					  return new ActionForward("/sys/lockrecordclose2.jsp");
				  }
			}

			//删除Idcode中的相关条码
//			appidcode.delIdcodeByids(ids);
			for (ProductIncomeIdcode ic : idcodelist)
			{
				appidcode.delIdcodeByid(ic.getIdcode());
			}

			AppProductStockpile aps = new AppProductStockpile();
			
//			List<ProductIncomeIdcode> piilist = apidcode.getProductIncomeIdcodeByPiid(piid);
			
			/*
			if(pb.getIsAutoCreate()==1){//自动生成的单据
				for (ProductIncomeIdcode pii : piilist)
				{
					//RichieYu -----20100505
					//退还实际总数量
					aps.returninProductStockpileTotalQuantity(pii.getProductid(), pii.getUnitid(), pii.getBatch(), pii.getQuantity(), pb
							.getWarehouseid(), pii.getWarehousebit(), piid, "取消产成品入库-出货");
				}
		  }else{
			  for (ProductIncomeDetail pid : pidetails)
				{
					aps.returninProductStockpileTotalQuantity(pid.getProductid(), pid.getUnitid(), pid.getBatch(), pid.getQuantity(), pb
							.getWarehouseid(), "000", piid, "取消产成品入库-出货");
				}
		 }*/
			
			//还原库存
			for (ProductIncomeDetail pid : pidetails) {
				aps.returninProductStockpileTotalQuantity(pid.getProductid(), 1, pid.getBatch(), pid.getConfirmQuantity(),
						pb.getWarehouseid(), "000", piid, "取消产成品入库-出货");
				//设置确认数量为0
				pid.setConfirmQuantity(0.0);
			}
			//修改生产确认
			pb.setConfimState(0);
			pb.setConfimDate(null);
			pb.setConfimUserId(null);
			apb.updProductIncomeByID(pb);
			apb.cancelAudit(piid, 0, 0);

			request.setAttribute("result", "databases.cancel.success");
			DBUserLog.addUserLog(userid, 7, "入库>>产成品入库>>取消复核产成品入库,编号：" + piid);
			return mapping.findForward("noaudit");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
	
	
	public String getIdcodesByList(List<ProductIncomeIdcode> idcodelist) throws Exception{
		StringBuffer buffer = new StringBuffer();
		for (ProductIncomeIdcode pii : idcodelist) {
			buffer.append("'"+pii.getIdcode()+"',");
		}
		return buffer.substring(0,buffer.length()-1);
	}

}
