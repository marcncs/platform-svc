package com.winsafe.drp.action.warehouse;

import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.dao.AppStockAlterMoveDetail;
import com.winsafe.drp.dao.AppTakeService;
import com.winsafe.drp.dao.StockAlterMove;
import com.winsafe.drp.dao.StockAlterMoveDetail;
import com.winsafe.drp.dao.TakeBill;
import com.winsafe.drp.dao.TakeServiceBean;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.MsgService;
import com.winsafe.drp.server.TakeTicketDetailService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.fileListener.UFIDA.ResXmlBean;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class MoveAndAuditStockAlterMoveAction extends BaseAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		super.initdata(request);
		try
		{
			String smid = request.getParameter("SMID");
			AppStockAlterMove api = new AppStockAlterMove();
			AppStockAlterMoveDetail asamd = new AppStockAlterMoveDetail();
			StockAlterMove pi = api.getStockAlterMoveByID(smid);

			if (pi.getIsaudit() == 1)
			{
				request.setAttribute("result", "databases.record.audit");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			if (pi.getIsblankout() == 1)
			{
				request.setAttribute("result", "databases.record.blankoutnooperator");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			if (!pi.getMakeorganid().equals(users.getMakeorganid()))
			{
				request.setAttribute("result", "databases.record.nopurview");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			List<StockAlterMoveDetail> dls = asamd.getStockAlterMoveDetailBySamID(smid);
			AppProductStockpileAll appps = new AppProductStockpileAll();
			AppFUnit appfu = new AppFUnit();

			for (StockAlterMoveDetail sod : dls)
			{
				double q = appfu.getQuantity(sod.getProductid(), sod.getUnitid(), sod.getQuantity());
				double stock = appps.getProductStockpileAllByPIDWID(sod.getProductid(), pi.getOutwarehouseid());
				if (q > stock)
				{
					request.setAttribute("result", sod.getProductname() + "产品库存不足，不能复核!");
					return new ActionForward("/sys/lockrecordclose2.jsp");
				}
			}

			addTakeBill(pi, dls, users);

			pi.setAuditdate(DateUtil.getCurrentDate());
			pi.setAuditid(userid);
			//设置订单为移库类型 richie.yu  2011-12-19
			pi.setIsmove(1);
			pi.setIsaudit(1);
			api.updstockAlterMove(pi);
			String[] param = new String[] { "name", "applytime", "billno" };
			String[] values = new String[] { pi.getOlinkman(), DateUtil.formatDate(pi.getMovedate()), pi.getId() };
			MsgService ms = new MsgService(param, values, users, 7);
			ms.addmag(1, pi.getOtel());

			request.setAttribute("result", "databases.audit.success");
			DBUserLog.addUserLog(userid, 4, "订购>>复核订购单,编号:" + smid);

			ResXmlBean resXmlBean = new ResXmlBean();
			if (("".equals(pi.getNccode())) || pi.getNccode() == null)
			{
				resXmlBean.setCgeneralhid(pi.getId());
			}
			else
			{
				resXmlBean.setCgeneralhid(pi.getNccode());
			}
			resXmlBean.setState(ResourceBundle.getBundle("com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat").getString("xml_state_success"));
			resXmlBean.setDetail(ResourceBundle.getBundle("com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat").getString("success"));
			//			SendXml sendXml=new SendXml(resXmlBean,"",100000);
			//			sendXml.start(false);
			return mapping.findForward("success");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

	public void addTakeBill(StockAlterMove so, List<StockAlterMoveDetail> pils, UsersBean users) throws Exception
	{
		TakeBill takebill = new TakeBill();
		takebill.setId(so.getId());
		takebill.setBsort(1);
		takebill.setOid(so.getReceiveorganid());
		takebill.setOname(so.getReceiveorganidname());
		takebill.setRlinkman(so.getOlinkman());
		takebill.setTel(so.getOtel());
		takebill.setInwarehouseid(so.getInwarehouseid());
		takebill.setSenddate(so.getMovedate());
		takebill.setMakeorganid(so.getMakeorganid());
		takebill.setMakedeptid(so.getMakedeptid());
		takebill.setEquiporganid(so.getMakeorganid());
		takebill.setMakeid(users.getUserid());
		takebill.setMakedate(DateUtil.getCurrentDate());
		takebill.setIsaudit(0);
		takebill.setIsblankout(0);

		TakeServiceBean tsb = new TakeServiceBean();
		tsb.setTakebill(takebill);

		TakeTicket tt = null;
		for (StockAlterMoveDetail pid : pils)
		{

			if (tsb.getTtmap().get(so.getOutwarehouseid()) == null)
			{
				tt = new TakeTicket();
				tt.setId(MakeCode.getExcIDByRandomTableName("take_ticket", 2, "TT"));
				tt.setWarehouseid(so.getOutwarehouseid());
				tt.setBillno(takebill.getId());
				tt.setBsort(1);
				tt.setOid(takebill.getOid());
				tt.setOname(takebill.getOname());
				tt.setRlinkman(takebill.getRlinkman());
				tt.setTel(takebill.getTel());
				tt.setRemark(so.getRemark());
				tt.setIsaudit(0);
				tt.setIsmove(1);
				tt.setMakeorganid(users.getMakeorganid());
				tt.setMakedeptid(users.getMakedeptid());
				tt.setMakeid(users.getUserid());
				tt.setMakedate(DateUtil.getCurrentDate());
				tt.setInwarehouseid(takebill.getInwarehouseid());
				tt.setPrinttimes(0);
				tt.setIsblankout(0);
				//设置车号和路线
				tt.setBusNo(so.getBusNo());
				tt.setBusWay(so.getBusWay());

				tsb.getTtmap().put(so.getOutwarehouseid(), tt);
				tsb.getWarehouseids().add(so.getOutwarehouseid());
			}
			else
			{
				tt = tsb.getTtmap().get(so.getOutwarehouseid());
			}
			//不控制批次			
//			TakeTicketDetailService ttds = new TakeTicketDetailService(tt, tt.getWarehouseid(), pid.getProductid(), pid.getProductname(), pid.getSpecmode(), pid.getUnitid(), pid.getUnitprice());
//			ttds.addBatchDetail(pid.getQuantity());

			TakeTicketDetail ttd = new TakeTicketDetail();
			ttd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("take_ticket_detail", 0, "")));
			ttd.setProductid(pid.getProductid());
			ttd.setProductname(pid.getProductname());
			ttd.setSpecmode(pid.getSpecmode());
			ttd.setUnitid(pid.getUnitid());
			ttd.setBatch(pid.getBatch());
			ttd.setUnitprice(pid.getUnitprice());
			ttd.setQuantity(pid.getQuantity());
			ttd.setTtid(tt.getId());
			tt.getTtdetails().add(ttd);
		}
		AppTakeService appts = new AppTakeService();
		appts.addTake(tsb, false);
	}
}