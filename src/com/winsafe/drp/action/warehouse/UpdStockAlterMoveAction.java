package com.winsafe.drp.action.warehouse;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.dao.AppStockAlterMoveDetail;
import com.winsafe.drp.dao.StockAlterMove;
import com.winsafe.drp.dao.StockAlterMoveDetail;
import com.winsafe.drp.metadata.DeliveryType; 
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class UpdStockAlterMoveAction extends BaseAction {
	Logger logger = Logger.getLogger(UpdStockAlterMoveAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		initdata(request);
		AppStockAlterMoveDetail asmd = new AppStockAlterMoveDetail();
		AppStockAlterMove asm = new AppStockAlterMove();

		try{
			String id = request.getParameter("id");
			StockAlterMove sm = asm.getStockAlterMoveByID(id);
			StockAlterMove oldsm = (StockAlterMove) BeanUtils.cloneBean(sm);
			String movedate = request.getParameter("movedate");
			sm.setMovedate(DateUtil.StringToDate(movedate));
			sm.setOutOrganId(request.getParameter("organid"));
			sm.setOutOrganName(request.getParameter("ooname"));
			sm.setOutwarehouseid(request.getParameter("outwarehouseid"));
			sm.setReceiveorganid(request.getParameter("receiveorganid"));
			sm.setReceiveorganidname(request.getParameter("oname"));
			sm.setInwarehouseid(request.getParameter("inwarehouseid"));
			sm.setTickettitle(request.getParameter("tickettitle"));
			sm.setOlinkman(request.getParameter("olinkman"));
			sm.setOtel(request.getParameter("otel"));
			sm.setTransportaddr(request.getParameter("transportaddr"));
			sm.setMovecause(request.getParameter("movecause"));
			sm.setRemark(request.getParameter("remark"));
			//单据类型
			sm.setBsort(Integer.valueOf(request.getParameter("bsort")));

			String[] strproductid = request.getParameterValues("productid");
			String[] strproductname = request.getParameterValues("productname");
			String[] strspecmode = request.getParameterValues("specmode");
			String[] strunitid = request.getParameterValues("unitid");
			String[] strunitprice = request.getParameterValues("unitprice");
			String[] strquantity = request.getParameterValues("quantity");
			String[] nccode = request.getParameterValues("nccode");
			
			String productid;
			Integer unitid;
			Double unitprice, quantity,  totalsum = 0.00;
			String productname, specmode;
			StringBuffer keyscontent = new StringBuffer();
			keyscontent.append(sm.getId()).append(",").append(sm.getOlinkman()).append(",")
			.append(sm.getOtel()).append(",").append(sm.getMakeorganidname());
			//删除原有的产品详情
			asmd.delStockAlterMoveDetailBySamid(id);
			//新增产品详情
			if (strproductid != null) {
				Set<String> noBonusProductSet = new HashSet<String>();
				if(sm.getBsort() == DeliveryType.BONUS.getValue()) {
					AppProduct appProduct = new AppProduct();
					noBonusProductSet = appProduct.getNoBonusProductIdSet();
				}
				for (int i = 0; i < strproductid.length; i++) {
					productid = strproductid[i];
					//当单据类型为积分兑换时,检查产品列表中是否有不能积分兑换的产品
					if(sm.getBsort() == DeliveryType.BONUS.getValue()
							&& noBonusProductSet.contains(productid)) {
						request.setAttribute("result", "新增失败,编号为"+productid+"的产品不能积分兑换");
						return new ActionForward("/sys/lockrecordclose2.jsp");
					}
					productname = strproductname[i];
					specmode = strspecmode[i];
					unitid = Integer.valueOf(strunitid[i]);
					quantity= Double.valueOf(strquantity[i]);
					unitprice = Double.valueOf(0.00);

					StockAlterMoveDetail smd = new StockAlterMoveDetail();
					smd.setId(Integer.valueOf(MakeCode
							.getExcIDByRandomTableName(
									"stock_alter_move_detail", 0, "")));
					smd.setSamid(id);
					smd.setProductid(productid);
					smd.setProductname(productname);
					smd.setSpecmode(specmode);
					smd.setUnitid(unitid.intValue());
					//订购单据不选批次
					smd.setBatch("");
					//设置内部编号 
					smd.setNccode(nccode[i]);
					smd.setUnitprice(unitprice);
					smd.setQuantity(quantity);	
					smd.setTakequantity(0d);
					smd.setBoxnum(0);
					smd.setScatternum(0d);
					smd.setSubsum(unitprice * quantity);
					totalsum += smd.getSubsum();
					asmd.addStockAlterMoveDetail(smd);
				}
			}
			sm.setTotalsum(totalsum);
			sm.setKeyscontent(keyscontent.toString());
			asm.updstockAlterMove(sm);

			request.setAttribute("result", "databases.upd.success");
			DBUserLog.addUserLog(request, "修改编号：" + sm.getId(), oldsm,
					sm);
			return mapping.findForward("success");
		} catch (Exception e) {
			logger.error("", e);
			throw e;
		} finally {

		}
	}
}
