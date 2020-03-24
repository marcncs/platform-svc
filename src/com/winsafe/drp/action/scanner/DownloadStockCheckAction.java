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
import com.winsafe.drp.dao.AppBarcodeInventory;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppTakeTicketDetail;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.BarcodeInventory;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ResponseUtil;

public class DownloadStockCheckAction extends BaseAction {
	private Logger logger = Logger.getLogger(DownloadTakeTicketAction.class);

	private AppFUnit af = new AppFUnit();
	private AppTakeTicketDetail appttd = new AppTakeTicketDetail();
	private AppUsers appUsers = new AppUsers();
	private AppProduct appProduct = new AppProduct();
	private AppWarehouse appWarehouse = new AppWarehouse();
	private AppBarcodeInventory abi = new AppBarcodeInventory();
	private static final String billSortAll = "99";
	AppOrgan ao = new AppOrgan();
	AppWarehouse aw = new AppWarehouse();
	Warehouse w = new Warehouse();
	Organ o = new Organ();
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			List pils = new ArrayList();
			String billsort = request.getParameter("Billsort"); // 单据类型
			String username = request.getParameter("Username"); // 登陆名
			String scannerno = request.getParameter("IMEI_number");
			if (StringUtil.isEmpty(billsort)) {
				billsort = billSortAll;
			}
			String[] billsorts = billsort.split(",");

			// 判断用户是否存在
			Users loginUsers = appUsers.getUsers(username);
			if (loginUsers != null) {
				for (String bsort : billsorts) {
					List ttList = download2scanner(String.valueOf(loginUsers.getUserid()), bsort,
							scannerno);
					if (ttList != null) {
						for (int i = 0; i < ttList.size(); i++) {
							BarcodeInventory bi = (BarcodeInventory) ttList.get(i);
							bi.setShipmentsort(Integer.valueOf(bsort));
						}
					}
					pils.addAll(ttList);
				}
			}
			// 如果要下载的信息不为空，则进行下载操作
			List list = getMapDate(pils, response);
			// List list = pils;
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG,
					list, loginUsers.getUserid(), "采集器", "IMEI:[" + scannerno + "],单据下载", true);
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	/**
	 * 转化数据为Map形式
	 */
	private List getMapDate(List ttlist, HttpServletResponse response) throws Exception {
		List<Map> list = new ArrayList<Map>();
		if (ttlist != null) {
			for (int i = 0; i < ttlist.size(); i++) {
				//主要数据
				BarcodeInventory bi = (BarcodeInventory) ttlist.get(i);
				o = ao.getOrganByID(bi.getMakeorganid());
				w = aw.getWarehouseByID(bi.getWarehouseid());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("billNo", bi.getId());
				// 业务单据号
				map.put("billID", bi.getId());
				map.put("billSort", bi.getShipmentsort());
				map.put("customID", "");
				map.put("fromOrgID", "");
				map.put("fromOrgName", "");
				map.put("fromOrgSName", "");
				map.put("fromWHID", "");
				map.put("fromWHName","" );
				map.put("toOrgID", bi.getMakeorganid());
				map.put("toOrgName", o.getOrganname());
				// TODO 收货机构简称，以后添加
				map.put("toOrgSName", "");
				map.put("toWHID", bi.getWarehouseid());
				map.put("toWHName", w.getWarehousename());
				map.put("totalCount", "");
				// 追加传递详情属性
				List<Map> listDetail = new ArrayList<Map>();
				Map<String, String> dtl = new HashMap<String, String>();
				// 将数量转化为最小单位
				// 产品的最小单位
				dtl.put("unitID", "");
				dtl.put("lCode", "");
				dtl.put("productID", "");
				dtl.put("productName", "");
				dtl.put("ticketCount", "");
				dtl.put("batchNumber", "");
				listDetail.add(dtl);
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
	public List download2scanner(String userid, String type, String scannerno) throws Exception {
		StringBuilder hql = new StringBuilder();

		// BSort=99|条码盘库单据下载
		if ("99".equals(type)) {
			hql.append(" from BarcodeInventory bi ");
			hql.append(" where 1= 1 ");
			hql.append(" and bi.shipmentsort=99 ");
			hql.append(" and bi.isaudit=0 and bi.isapprove = 1 ");
			if (Constants.SCANNNER_VALIDATE_USER) {
				hql
						.append(" and bi.warehouseid in (select ruw.warehouseId from RuleUserWh ruw where ruw.userId = "
								+ userid + ") ");
			}
			if (Constants.SCANNNER_VALIDATE_IMEI) {
				hql
						.append(" and bi.warehouseid in ( select w.id from Warehouse w,ScannerWarehouse sw,Scanner s  ");
				hql.append("  where w.useflag=1 and w.id=sw.warehouseid  ");
				hql.append("  and sw.scannerid =s.id  ");
				hql.append("  and s.status=1 and s.scannerImeiN='" + scannerno + "' ) ");
			}

		} else {
			// 其他情况
			return null;
		}
		List outlist = EntityManager.getAllByHqlReadOnly(hql.toString());
		return outlist;
	}
}