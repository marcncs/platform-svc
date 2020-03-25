package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppStockMove;
import com.winsafe.drp.dao.AppStockMoveDetail;
import com.winsafe.drp.dao.StockMove;
import com.winsafe.drp.dao.StockMoveDetail;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class UpdStockMoveAction extends BaseAction {
	private static Logger logger = Logger.getLogger(UpdStockMoveAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		initdata(request);
		AppStockMove asm = new AppStockMove();
		AppStockMoveDetail asmd = new AppStockMoveDetail();
		
		try {
			String id = request.getParameter("id");
			StockMove sm = asm.getStockMoveByID(id);
			StockMove oldsm = (StockMove) BeanUtils.cloneBean(sm);
			String movedate = request.getParameter("movedate");
			sm.setMovedate(DateUtil.StringToDate(movedate));
			sm.setOutwarehouseid(request.getParameter("outwarehouseid"));
			//转仓出入库机构相同
			sm.setOutorganid(request.getParameter("organid"));
			sm.setInorganid(request.getParameter("organid"));
			sm.setInwarehouseid(request.getParameter("inwarehouseid"));
			sm.setMovecause(request.getParameter("movecause"));
			sm.setRemark(request.getParameter("remark"));
			sm.setOlinkman(request.getParameter("olinkman"));
			sm.setOtel(request.getParameter("otel"));
			sm.setTransportaddr(request.getParameter("transportaddr"));

			String[] strproductid = request.getParameterValues("productid");
			String[] strproductname = request.getParameterValues("productname");
			String[] strspecmode = request.getParameterValues("specmode");
			 String[] strunitid = request.getParameterValues("unitid");
			 String[] strquantity = request.getParameterValues("quantity");
			 String[] nccode = request.getParameterValues("nccode");

			String productid;
			Integer unitid ;
			Double quantity,  totalsum = 0.00;
			String productname, specmode;
			StringBuffer keyscontent = new StringBuffer();
			keyscontent.append(sm.getId()).append(",");

			asmd.delStockMoveDetailBySmID(id);

			for (int i = 0; i < strproductid.length; i++) {
				productid = strproductid[i];
				productname = strproductname[i];
				specmode = strspecmode[i];
				unitid = Integer.valueOf(strunitid[i]);
				quantity = Double.valueOf(strquantity[i]);

				StockMoveDetail smd = new StockMoveDetail();
				smd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"stock_move_detail", 0, "")));
				smd.setSmid(id);
				smd.setProductid(productid);
				smd.setProductname(productname);
				smd.setSpecmode(specmode);
				//设置内部编号 
				smd.setNccode(nccode[i]);
				smd.setUnitid(unitid);
				smd.setBatch("");
				smd.setQuantity(quantity);
				smd.setBoxnum(0);
				smd.setScatternum(0d);
				smd.setCost(0d);
				smd.setTakequantity(0d);
				asmd.addStockMoveDetail(smd);
			}

			sm.setTotalsum(totalsum);
			sm.setKeyscontent(keyscontent.toString());
			asm.updstockMove(sm);

			request.setAttribute("result", "databases.upd.success");
			DBUserLog.addUserLog(request, "编号：" + id, oldsm, sm);
			return mapping.findForward("updresult");
		} catch (Exception e) {
			logger.error("", e);
			throw e;
		}
	}
}
