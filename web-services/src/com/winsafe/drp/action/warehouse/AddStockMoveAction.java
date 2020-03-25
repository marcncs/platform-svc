package com.winsafe.drp.action.warehouse;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppStockMove;
import com.winsafe.drp.dao.AppStockMoveDetail;
import com.winsafe.drp.dao.StockMove;
import com.winsafe.drp.dao.StockMoveDetail;
import com.winsafe.drp.server.StockMoveService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AddStockMoveAction extends BaseAction {
	private static Logger logger = Logger.getLogger(AddStockMoveAction.class);
	
    private AppStockMove asm = new AppStockMove();
    private AppStockMoveDetail asmd = new AppStockMoveDetail();
    private StockMoveService smService = new StockMoveService();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		initdata(request);
		try{
			StockMove sm = new StockMove();
			logger.debug("获取转仓单号开始");
			String smid = MakeCode.getExcIDByRandomTableName("stock_move", 2, "SM");
			logger.debug("获取转仓单号结束，smid="+smid);
			sm.setId(smid);
			sm.setMovedate(DateUtil.StringToDate(request.getParameter("movedate")));
			sm.setOutwarehouseid(request.getParameter("outwarehouseid"));
			sm.setInorganid(request.getParameter("inorganid"));
			sm.setOlinkman(request.getParameter("olinkman"));
			sm.setOtel(request.getParameter("otel"));
			sm.setInwarehouseid(request.getParameter("inwarehouseid"));
			sm.setTransportaddr(request.getParameter("transportaddr"));
			sm.setMovecause(request.getParameter("movecause"));
			sm.setRemark(request.getParameter("remark"));
			sm.setIsaudit(0);
			sm.setMakeorganid(users.getMakeorganid());
			sm.setMakedeptid(users.getMakedeptid());
			sm.setMakeid(userid);
			sm.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
			//是否出库
			sm.setIsshipment(0);
			//是否完成
			sm.setIscomplete(0);
			//是否作废
			sm.setIsblankout(0);
			//转仓出入库机构相同
			sm.setOutorganid(sm.getInorganid());
			String[] strproductid = request.getParameterValues("productid");
			String[] strproductname = request.getParameterValues("productname");
			String[] strspecmode = request.getParameterValues("specmode");
			String[] strunitid = request.getParameterValues("unitid");
			String[] boxquantity = request.getParameterValues("quantity");
			String[] nccode = request.getParameterValues("nccode");

			Integer unitid;
			Double quantity, totalsum = 0d;
			String productid, productname,specmode, batch;
			StringBuffer keyscontent = new StringBuffer();
			keyscontent.append(sm.getId()).append(",");
			List<StockMoveDetail> smdList = new ArrayList<StockMoveDetail>();
			for (int i = 0; i < strproductid.length; i++) {
				productid = strproductid[i];
				productname = strproductname[i];
				specmode = strspecmode[i];
				unitid = Integer.valueOf(strunitid[i]);
				quantity= Double.valueOf(boxquantity[i]);
				//转仓无批次
				batch = "";
				StockMoveDetail smd = new StockMoveDetail();
				logger.debug("获取转仓单明细单编号开始");
				smd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("stock_move_detail", 0, "")));
				logger.debug("获取转仓单明细单编号结束smd="+smd.getId());
				smd.setSmid(smid);
				smd.setProductid(productid);
				smd.setProductname(productname);
				smd.setSpecmode(specmode);
				//单位
				smd.setUnitid(unitid);
				//设置内部编号 
				smd.setNccode(nccode[i]);
				smd.setBatch(batch);
				smd.setCost(0d);
				smd.setQuantity(quantity);
				//检货数量
				smd.setTakequantity(0d);
				smd.setCost(0d);
				smd.setMakedate(Dateutil.getCurrentDate());
				asmd.addStockMoveDetail(smd);
				totalsum = totalsum + quantity;
				smdList.add(smd);

			}
			
			sm.setTotalsum(totalsum);
			sm.setKeyscontent(keyscontent.toString());
			asm.addStockMove(sm);
			logger.debug("添加单据成功");
			DBUserLog.addUserLog(request,"编号："+sm.getId());
			
			// 默认复核单据,如果单据中的产品库存不足,不复核
			if(smService.checkStock(sm, smdList)){
				//增加检货出库单据
				smService.addTakeTicket(sm, smdList, users);
				//更新单据为复核状态
				sm.setIsaudit(1);
				sm.setAuditid(userid);
				sm.setAuditdate(DateUtil.getCurrentDate());
				asm.updstockMove(sm);
			}
			
			request.setAttribute("result", "databases.add.success");
			
			return mapping.findForward("addresult");
		} catch (Exception e) {
			logger.error("", e);
			throw e;
		}
	}
}
