package com.winsafe.drp.action.sales;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.AppSampleBill;
import com.winsafe.drp.dao.AppSampleBillDetail;
import com.winsafe.drp.dao.AppTakeService;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.SampleBill;
import com.winsafe.drp.dao.SampleBillDetail;
import com.winsafe.drp.dao.TakeBill;
import com.winsafe.drp.dao.TakeServiceBean;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketDetail;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.server.MsgService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AuditSampleBillAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		initdata(request);

		try {
			//得到样品的订单号
			String sbid = request.getParameter("SBID");
			//样品dao
			AppSampleBill asb = new AppSampleBill();
			//样品明细dao
			AppSampleBillDetail asbd = new AppSampleBillDetail();
			//仓库dao，带仓位
			AppProductStockpile  aps = new AppProductStockpile();	
			//仓库dao
			AppProductStockpileAll apsa = new AppProductStockpileAll();
			
			
			//根据样品订单号查询样品对象
			SampleBill sb = asb.findByID(sbid);
			//样品订单号的仓库id
			String wid = sb.getWarehouseID(); 
			//样品订单明细
			List<SampleBillDetail> sls = asbd.findBySbid(sbid) ;
			
                        //如果已经复核，则提示已复核
			if (sb.getIsaudit() == 1) {
				String result = "databases.record.audit";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp"); 
			}
			
			//检查产品库存
			
			//库存dao
			AppProductStockpileAll appps = new AppProductStockpileAll();
			//单位dao
			AppFUnit appfu = new AppFUnit();
			//检查库存，如库存大于仓库库存，则显示库存不足
			for (SampleBillDetail sbd :sls){
				double q=appfu.getQuantity(sbd.getProductid(), sbd.getUnitid(), sbd.getQuantity());
				double stock=appps.getProductStockpileAllByPIDWID(sbd.getProductid(),asb.findByID(sbid).getWarehouseID());
				if (q>stock){
					request.setAttribute("result", sbd.getProductid()+"产品库存不足，不能复核！");
					return new ActionForward("/sys/lockrecordclose2.jsp");
				}
			}
			
			//添加出库单
			addTakeBill(sb, sls, users);
			  
			//库存发生变化，作出库处理
//			 for(int i=0;i<sls.size();i++){
//				 SampleBillDetail t_sbd = (SampleBillDetail)sls.get(i);
//				 aps.adjustProductStockpile(t_sbd.getProductid(), "-",
//						 t_sbd.getQuantity(), wid);		
//				 apsa.adjustProductStockpileAll(t_sbd.getProductid(), "-",
//						 t_sbd.getQuantity(), wid);		
//			 }	
			//List<SampleBillDetail> sbds=asbd.
			 
			sb.setAuditid(userid);
			sb.setAuditdate(DateUtil.getCurrentDate());
			sb.setIsaudit(1);
			asb.update(sb);
			
			String[] param = new String[]{"name","applytime","billno"};
			String[] values = new String[]{sb.getLinkman(), DateUtil.formatDate(sb.getShipmentdate()), String.valueOf(sb.getId())};
			MsgService ms = new MsgService(param, values, users, 7);
			ms.addmag(1,sb.getTel());		

			request.setAttribute("result", "databases.audit.success");
			DBUserLog.addUserLog(userid, "复核样品记录,编号：" + sbid);

			return mapping.findForward("audit");
		} catch (Exception e) {

			e.printStackTrace();
		} finally {

		}
		return new ActionForward(mapping.getInput());
	}
	
	/**
	 * 生成检货出库单
	 * 主要功能：
	 * @param sb
	 * @param sbds
	 * @param users
	 * @throws Exception
	 */
	public void addTakeBill(SampleBill sb,List<SampleBillDetail> sbds,UsersBean users) throws Exception{
		//机构dao
		AppOrgan ao=new AppOrgan();
		//仓库dao
		AppWarehouse appw=new AppWarehouse();
		//更据客户名得到仓库对象
		Warehouse wh=appw.getWarehouseByOID((ao.getOrganByOrganName(sb.getCname()).getId()));
		
		//添加订单信息
		TakeBill takebill=new TakeBill();
		takebill.setId(String.valueOf(sb.getId()));
		takebill.setBsort(14);
		takebill.setOid((ao.getOrganByOrganName(sb.getCname()).getId()));
		takebill.setOname(sb.getCname());
		takebill.setRlinkman(sb.getLinkman());
		takebill.setTel(sb.getTel());
		takebill.setInwarehouseid(wh.getId());
		takebill.setSenddate(sb.getShipmentdate());
		takebill.setMakeorganid(sb.getMakeorganid());
		takebill.setMakedeptid(sb.getMakedeptid());
		takebill.setEquiporganid(sb.getMakeorganid());
		takebill.setMakeid(users.getUserid());
		takebill.setMakedate(DateUtil.getCurrentDate());
		//是否已复核
		takebill.setIsaudit(0);
		//是否作废
		takebill.setIsblankout(0);
		
		TakeServiceBean tsb = new TakeServiceBean();
		tsb.setTakebill(takebill);
		
		TakeTicket tt = null;
		
		//添加入库小票信息
		for (SampleBillDetail sbd :sbds){
			if (tsb.getTtmap().get(wh.getId()) == null){
				tt = new TakeTicket();
				tt.setId(MakeCode.getExcIDByRandomTableName("take_ticket",2, "TT"));
				tt.setWarehouseid(wh.getId());
				tt.setBillno(takebill.getId());
				tt.setBsort(14);
				tt.setOid(takebill.getOid());
				tt.setOname(takebill.getOname());
				tt.setRlinkman(takebill.getRlinkman());
				tt.setTel(takebill.getTel());
				tt.setRemark(sb.getRemark());
				tt.setIsaudit(0);
				tt.setMakeorganid(users.getMakeorganid());
				tt.setMakedeptid(users.getMakedeptid());
				tt.setMakeid(users.getUserid());
				tt.setMakedate(DateUtil.getCurrentDate());
				tt.setInwarehouseid(takebill.getInwarehouseid());
				tt.setPrinttimes(0);
				tt.setIsblankout(0);
				tsb.getTtmap().put(wh.getId(), tt);
				tsb.getWarehouseids().add(wh.getId());
			}else {
				tt = tsb.getTtmap().get(wh.getId());
			}

			TakeTicketDetail ttd = new TakeTicketDetail();
			ttd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
					"take_ticket_detail", 0, "")));
			ttd.setProductid(sbd.getProductid());
			ttd.setProductname(sbd.getProductname());
			ttd.setSpecmode(sbd.getSpecmode());
			ttd.setUnitid(sbd.getUnitid());
			ttd.setBatch("");
			ttd.setUnitprice(sbd.getUnitprice());
			ttd.setQuantity(Double.valueOf(sbd.getQuantity()));
			ttd.setTtid(tt.getId());			
			tt.getTtdetails().add(ttd);	
		}
		AppTakeService appts = new AppTakeService();
		appts.addTake(tsb, false);
	}

}
