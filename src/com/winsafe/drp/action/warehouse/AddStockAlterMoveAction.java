package com.winsafe.drp.action.warehouse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProduct; 
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.dao.AppStockAlterMoveDetail;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.StockAlterMove;
import com.winsafe.drp.dao.StockAlterMoveDetail;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.metadata.DeliveryType;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.StockAlterMoveService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AddStockAlterMoveAction extends BaseAction {
	private Logger logger = Logger.getLogger(AddStockAlterMoveAction.class);
	
	private AppWarehouse aw = new AppWarehouse();
    private AppProductStockpileAll appps = new AppProductStockpileAll();
    private AppFUnit appfu = new AppFUnit();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//DAO层类
		OrganService ao = new OrganService();
		AppStockAlterMove asm = new AppStockAlterMove();
		StockAlterMoveService sams = new StockAlterMoveService();
		
		//初始化
		initdata(request);
		userid = 10205;
		UsersBean users = new UsersBean();
		users.setMakedeptid(1);
		users.setMakeorganid("10001252");
		users.setUserid(10205);
		try {
			StockAlterMove sam = new StockAlterMove();
			String smid = MakeCode.getExcIDByRandomTableName("stock_alter_move", 2, "OM");
			sam.setId(smid);
			sam.setMovedate(DateUtil.StringToDate(request.getParameter("movedate")));
			sam.setOutwarehouseid(request.getParameter("outwarehouseid"));
			sam.setInwarehouseid(request.getParameter("inwarehouseid"));
			sam.setTransportaddr(request.getParameter("transportaddr"));
			sam.setOlinkman(request.getParameter("olinkman"));
			sam.setOtel(request.getParameter("otel"));
			sam.setIsmaketicket(0);
			sam.setIsreceiveticket(0);
			sam.setTickettitle(request.getParameter("tickettitle"));
			sam.setMovecause(request.getParameter("movecause"));
			sam.setRemark(request.getParameter("remark"));
			//是否复核
			sam.setIsaudit(0);
			sam.setMakeorganid(users.getMakeorganid());
			sam.setMakeorganidname(ao.getOrganName(users.getMakeorganid()));
			sam.setMakedeptid(users.getMakedeptid());
			sam.setMakeid(userid);
			sam.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
			//是否出库
			sam.setIsshipment(0);
			//是否记账
			sam.setIstally(0);
			//是否作废
			sam.setIsblankout(0);
			sam.setReceiveorganid(request.getParameter("receiveorganid"));
			sam.setReceiveorganidname(request.getParameter("oname"));
			//是否完成
			sam.setIscomplete(0);
			// 车号和路线
			sam.setBusNo(request.getParameter("busNo"));
			sam.setBusWay(request.getParameter("busWay"));
			//出库机构编号
			sam.setOutOrganId(request.getParameter("organid"));
			//出库机构名称
			sam.setOutOrganName(request.getParameter("orgname"));
			//内部销售单号
			sam.setNccode(request.getParameter("deliveryNo"));
			sam.setBonusStatus(0); 
			//单据类型
			sam.setBsort(Integer.valueOf(request.getParameter("bsort")));

			StringBuffer keyscontent = new StringBuffer();
			keyscontent.append(sam.getId()).append(",").append(sam.getOlinkman())
					.append(",").append(sam.getOtel()).append(",").append(
							sam.getMakeorganidname());

			String[] strproductid = request.getParameterValues("productid");
			String[] nccode = request.getParameterValues("nccode");
			String[] strproductname = request.getParameterValues("productname");
			String[] strspecmode = request.getParameterValues("specmode");
			String[] strunitid = request.getParameterValues("unitid");
			String[] strnum = request.getParameterValues("quantity");

			Integer unitid ;
			Double quantity, unitprice,  totalsum = 0.00;
			String productid, productname, specmode;
			List<StockAlterMoveDetail> samsList = new ArrayList<StockAlterMoveDetail>();
			AppStockAlterMoveDetail asmd = new AppStockAlterMoveDetail();
			if (strproductid != null) {
				Set<String> noBonusProductSet = new HashSet<String>();
				if(sam.getBsort() == DeliveryType.BONUS.getValue()) {
					AppProduct appProduct = new AppProduct();
					noBonusProductSet = appProduct.getNoBonusProductIdSet();
				}
				for (int i = 0; i < strproductid.length; i++) {
					productid = strproductid[i];
					//当单据类型为积分兑换时,检查产品列表中是否有不能积分兑换的产品
					if(sam.getBsort() == DeliveryType.BONUS.getValue()
							&& noBonusProductSet.contains(productid)) {
						request.setAttribute("result", "新增失败,编号为"+productid+"的产品不能积分兑换");
						return new ActionForward("/sys/lockrecordclose2.jsp");
					}
					
					productname = strproductname[i];
					specmode = strspecmode[i];
					unitid = Integer.valueOf(strunitid[i]);
					quantity = Double.valueOf(strnum[i]);
					unitprice = Double.valueOf(0.00);
					StockAlterMoveDetail smd = new StockAlterMoveDetail();
					//smd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("stock_alter_move_detail", 0, "")));
					smd.setSamid(smid);
					smd.setProductid(productid);
					smd.setProductname(productname);
					smd.setSpecmode(specmode);
					//单位
					smd.setUnitid(Integer.valueOf(strunitid[i]));
					//订购不选择批次
					smd.setBatch("");
					//设置内部编号 
					smd.setNccode(nccode[i]);
					smd.setUnitprice(unitprice);
					smd.setQuantity(quantity);
					smd.setTakequantity(0d);
					smd.setSubsum(unitprice * smd.getQuantity());
					smd.setMakedate(DateUtil.getCurrentDate());
					asmd.addStockAlterMoveDetail(smd);
					
					samsList.add(smd);
					totalsum += smd.getSubsum();
				}
			}
			sam.setTotalsum(totalsum);
			sam.setTakestatus(0);
			sam.setKeyscontent(keyscontent.toString());
			asm.addStockAlterMove(sam);
			
			// 默认复核单据,如果单据中的产品库存不足,不复核
			if(checkStock(sam, samsList)){
				//复核单据(新增tt表)
				sams.addTakeTicket(sam, samsList, users);
				sam.setAuditdate(DateUtil.getCurrentDate());
				sam.setAuditid(userid);
				sam.setIsaudit(1);
				sam.setIsmove(0);
				asm.updstockAlterMove(sam);
			}
			
			request.setAttribute("result", "databases.add.success");

			//DBUserLog.addUserLog(request, "编号：" + smid);
			return mapping.findForward("success");
		} catch (Exception e) {
			logger.error("", e);
			throw e;
		}
	}
	/**
	 * 检查库存
	 * @throws Exception 
	 */
	private boolean checkStock(StockAlterMove sam,List<StockAlterMoveDetail> samsList) throws Exception{
		// 如果仓库属性[是否负库存]为0,则检查库存
		Warehouse outWarehouse = aw.getWarehouseByID(sam.getOutwarehouseid());
		if(outWarehouse.getIsMinusStock() == null || outWarehouse.getIsMinusStock() == 0){
			for (StockAlterMoveDetail sod : samsList)
			{
				double q = appfu.getQuantity(sod.getProductid(), sod.getUnitid(), sod.getQuantity());
				double stock = appps.getProductStockpileAllByPIDWID(sod.getProductid(), sam.getOutwarehouseid());
				if (q > stock)
				{
					return false;
				}
			}
		}
		return true;
		
	}
}
