package com.winsafe.drp.action.scanner;

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

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppTakeTicketDetail;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketDetail;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ResponseUtil; 


public class DownloadTakeTicketAction extends BaseAction {
	private Logger logger = Logger.getLogger(DownloadTakeTicketAction.class);
	
	private AppFUnit af = new AppFUnit();
	private AppTakeTicketDetail appttd = new AppTakeTicketDetail();
	private AppUsers appUsers = new AppUsers();
	private AppProduct appProduct = new AppProduct();
	private AppWarehouse appWarehouse = new AppWarehouse();
	
	private static final String billSortAll = "1,2,7,8";

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			List pils = new ArrayList();
			String billsort = request.getParameter("Billsort"); //单据类型
			String username = request.getParameter("Username"); //登陆名
			String scannerno = request.getParameter("IMEI_number"); 
			String billno = request.getParameter("Billno"); //单据号
			if(StringUtil.isEmpty(billsort)){
				billsort = billSortAll;
			}
			String[] billsorts = billsort.split(","); 
			
			// 判断用户是否存在
			Users loginUsers = appUsers.getUsers(username);
			if(loginUsers != null){
				for(String bsort : billsorts){
					List ttList = download2scanner(String.valueOf(loginUsers.getUserid()),bsort,scannerno,billno);
					if(ttList != null){
						for (int i = 0; i < ttList.size(); i++) {
							TakeTicket tt = (TakeTicket) ttList.get(i);
							tt.setBsort(Integer.valueOf(bsort));
						}
					}
					pils.addAll(ttList);
				}
			}
			// 如果要下载的信息不为空，则进行下载操作
			List list = getMapDate(pils,response);
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, list
					,loginUsers.getUserid(),"采集器","IMEI:[" + scannerno + "],单据下载",true);
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	/**
	 * 转化数据为Map形式
	 */
	private List getMapDate(List ttlist,HttpServletResponse response) throws Exception{
		List<Map> list = new ArrayList<Map>();
		if(ttlist != null){
			for (int i = 0; i < ttlist.size(); i++) {
				TakeTicket tt = (TakeTicket) ttlist.get(i);
				// TODO 待优化
				Warehouse outWarehouse = appWarehouse.getWarehouseByID(tt.getWarehouseid());
				Warehouse inWarehouse = appWarehouse.getWarehouseByID(tt.getInwarehouseid());
				Map<String, Object> map = new HashMap<String, Object>();
				// TT单据号
				map.put("billNo", tt.getId());
				// 业务单据号
				map.put("billID", tt.getBillno());
				map.put("billSort", tt.getBsort());
				map.put("customID", tt.getNccode());
				map.put("fromOrgID", tt.getOid());
				map.put("fromOrgName", tt.getOname());
				// TODO 出货机构简称，以后添加
				map.put("fromOrgSName", "");
				map.put("fromWHID", tt.getWarehouseid());
				map.put("fromWHName", outWarehouse.getWarehousename());
				map.put("toOrgID", tt.getInOid());
				map.put("toOrgName", tt.getInOname());
				// TODO 收货机构简称，以后添加
				map.put("toOrgSName", "");
				map.put("toWHID", tt.getInwarehouseid());
				map.put("toWHName", inWarehouse.getWarehousename());
				map.put("totalCount", tt.getTotalQuantity());
				//查看详情
				List<TakeTicketDetail> ttds = appttd.getTakeTicketDetailByTtid(tt.getId());
				List<Map> listDetail = new ArrayList<Map>();
				for(TakeTicketDetail ttd : ttds){
					Map<String, String> dtl = new HashMap<String, String>();
					//将数量转化为最小单位
					Product product = appProduct.getProductByID(ttd.getProductid());
					//产品的最小单位
					dtl.put("unitID", product.getSunit()+"");
					Double quantity = af.getQuantity(ttd.getProductid(), ttd.getUnitid(), ttd.getQuantity());
					//
					dtl.put("lCode", product.getmCode());
					dtl.put("productID", ttd.getProductid());
					dtl.put("productName", ttd.getProductname());
					dtl.put("ticketCount", quantity+"");
					dtl.put("batchNumber", ttd.getBatch());
					listDetail.add(dtl);
				}
				map.put("takeTicketDTL", listDetail);
				list.add(map);
			}	
		}
		return list;
	}

	/**
	 * 采集器下载
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public List download2scanner(String userid,String type,String scannerno,String billno ) throws Exception {
			StringBuilder hql = new StringBuilder();

			// BSort=0|零售单,1|渠道订购单,2|渠道转仓单,3|渠道代销单
			// ,4|领用出库单,5|报损出库单,6|产品互转,7|渠道退货单
			// ,8|渠道换货单供方,9|渠道换货单换方,10|采购退货单
			// ,11|销售换货单,12|积分换购单,13|采购换货单,14|样品出库单
			
			//type类型  1:订购情况
			 if ("1".equals(type)) {
				// 订购出库单据
				hql.append(" from TakeTicket tt ");
				hql.append(" where 1 =1 ");
				hql.append(" and tt.bsort = 1 ");
				hql.append(" and (tt.isaudit is null or tt.isaudit=0)");
				hql.append(" and (tt.isblankout is null or tt.isblankout=0)");
				if(Constants.SCANNNER_VALIDATE_USER){
					hql.append(" and tt.warehouseid in (select ruw.warehouseId from RuleUserWh ruw where ruw.userId = "
							+ userid + ") ");
				}
				if(Constants.SCANNNER_VALIDATE_IMEI){
					hql.append(" and tt.warehouseid in ( select w.id from Warehouse w,ScannerWarehouse sw,Scanner s  ");
					hql.append("  where w.useflag=1 and w.id=sw.warehouseid  ");
					hql.append("  and sw.scannerid =s.id  ");
					hql.append("  and s.status=1 and s.scannerImeiN='" + scannerno +"' )  ");
				}
				hql.append(" and tt.inwarehouseid in (select wv.wid from  WarehouseVisit as wv where wv.userid="
								+ userid + ") ");
				//采集器编号
				//hql.append(" and (tt.scannerNo = '" + scannerno +  "') ");
				
				hql.append(" order by tt.id desc ");
			 }else if ("2".equals(type)) {
				//转仓单据
					hql.append(" from TakeTicket tt ");
					hql.append(" where 1 = 1 ");
					hql.append(" and tt.bsort = 2 ");
					hql.append(" and (tt.isaudit is null or tt.isaudit=0)");
					hql.append(" and (tt.isblankout is null or tt.isblankout=0)");
					if(Constants.SCANNNER_VALIDATE_USER){
						hql.append(" and tt.warehouseid in (select ruw.warehouseId from RuleUserWh ruw where ruw.userId = "
								+ userid + ") ");
					}
					if(Constants.SCANNNER_VALIDATE_IMEI){
						hql.append(" and tt.warehouseid in ( select w.id from Warehouse w,ScannerWarehouse sw,Scanner s  ");
						hql.append("  where w.useflag=1 and w.id=sw.warehouseid  ");
						hql.append("  and sw.scannerid =s.id  ");
						hql.append("  and s.status=1 and s.scannerImeiN='" + scannerno +"' )  ");
					}
//					hql.append("and tt.inwarehouseid in (select wv.wid from  WarehouseVisit as wv where wv.userid="
//									+ userid + ") ");
					//采集器编号
					//hql.append(" and (tt.scannerNo = '" + scannerno +  "') ");
					hql.append("order by tt.id desc ");
			 }else if ("7".equals(type)) {
					//退货单据
					hql.append("from TakeTicket tt ");
					hql.append("where 1 = 1 ");
					hql.append(" and tt.bsort = 7  ");
					hql.append(" and tt.billno like 'OW%' ");
					hql.append(" and (tt.isaudit is null or tt.isaudit=0)");
					hql.append(" and (tt.isblankout is null or tt.isblankout=0)");
					/*hql.append(" and tt.warehouseid in (select wv.wid from  WarehouseVisit as wv where wv.userid="
							+ userid + ") ");*/
					if(Constants.SCANNNER_VALIDATE_USER){
						hql.append(" and tt.inwarehouseid in (select ruw.warehouseId from RuleUserWh ruw where ruw.userId = "
								+ userid + ") ");
					} 
					if(Constants.SCANNNER_VALIDATE_IMEI){
						hql.append(" and tt.inwarehouseid in ( select w.id from Warehouse w,ScannerWarehouse sw,Scanner s  ");
						hql.append("  where w.useflag=1 and w.id=sw.warehouseid  ");
						hql.append("  and sw.scannerid =s.id  ");
						hql.append("  and s.status=1 and s.scannerImeiN='" + scannerno +"' )  ");
					}
					
					//采集器编号
					//hql.append(" and (tt.scannerNo = '" + scannerno +  "') ");
					hql.append("order by tt.id desc ");
				 }else if ("8".equals(type)) {
						//工厂退回单据
						hql.append(" from TakeTicket tt ");
						hql.append(" where 1 = 1 ");
						hql.append(" and tt.bsort = 7 ");
						hql.append(" and tt.billno like 'PW%' ");
						hql.append(" and (tt.isaudit is null or tt.isaudit = 0) ");
						hql.append(" and (tt.isblankout is null or tt.isblankout = 0) ");
						if(Constants.SCANNNER_VALIDATE_USER){
							hql.append(" and tt.warehouseid in (select ruw.warehouseId from RuleUserWh ruw where ruw.userId = "
									+ userid + ") ");
						}
						if(Constants.SCANNNER_VALIDATE_IMEI){
							hql.append(" and tt.warehouseid in ( select w.id from Warehouse w,ScannerWarehouse sw,Scanner s  ");
							hql.append("  where w.useflag=1 and w.id=sw.warehouseid  ");
							hql.append("  and sw.scannerid =s.id  ");
							hql.append("  and s.status=1 and s.scannerImeiN='" + scannerno +"' )  ");
						}
						hql.append("  and  tt.inwarehouseid in (select id from Warehouse  w where w.makeorganid in (select id from Organ o where  o.isrepeal=0 and  o.organType = 1 )) ");
						//采集器编号
						//hql.append(" and (tt.scannerNo = '" + scannerno +  "') ");
						hql.append("order by tt.id desc ");
					 }else {
				// 其他情况
				return null;
			 }
			 List outlist = EntityManager.getAllByHqlReadOnly(hql.toString());
			 return outlist;
	}
}
