package com.winsafe.drp.action.report;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCacheDao;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppTakeBill;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppTakeTicketDetailBatchBit;
import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.DetailReportForm;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.TakeBill;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.RuleUtil;
import com.winsafe.drp.util.cache.CacheBean;
import com.winsafe.drp.util.cache.CacheController;
import com.winsafe.drp.util.cache.ICache;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

/**
 * 出库条码扫描率 报表导出
 * 
 * @Title: ExportIdcodeScanRateAction.java
 * @Description: TODO
 * @author: wenping
 * @CreateTime: Oct 26, 2012 2:40:50 PM
 * @version:
 */
public class ExportIdcodeScanRateAction extends BaseAction {

	private Logger logger = Logger.getLogger(ExportIdcodeScanRateAction.class);
	
	private AppTakeTicket att = new AppTakeTicket();
	private AppTakeTicketIdcode atti = new AppTakeTicketIdcode();
	private AppTakeTicketDetailBatchBit attd = new AppTakeTicketDetailBatchBit();

	
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		AppCacheDao appCache=new AppCacheDao();
		super.initdata(request);
		// int pageSize=20;
		OutputStream os = null;
		try {

			String[] tablename = { "TakeTicket", "TakeTicketDetail" };
			String whereSql = getWhereSql(tablename);
			// 制单日期
			String timeCondition = getTimeCondition("tt.makedate");
			String Condition = "  ttd.ttid=tt.id and tt.isaudit=1  and (inwarehouseid in (select wv.wid from  warehouse_visit as wv where wv.userid="
					+ userid
					+ ") or inwarehouseid in (select ruw.warehouse_Id from rule_user_wh ruw where ruw.user_Id = "
					+ userid + " and activeFlag = 1))";

			String blur = DbUtil.getOrBlur(map, tmpMap, "billno", "tt.id", "tt.nccode");
			whereSql = whereSql + timeCondition + blur + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			List ttlist = att.getIdcodeScanRateList(request, whereSql);

			Map tempMap =new HashMap(); 
			String ttid,proid,ncpid,outwid,outorgan,inwid,inorgan;
			String boxnum,scatternum,quantity;
			AppTakeTicketDetailBatchBit  attd = new AppTakeTicketDetailBatchBit();
			AppProduct ap = new AppProduct();
			AppWarehouse aw = new AppWarehouse();
			AppOrgan ao =new AppOrgan();
			AppTakeTicketIdcode atti = new AppTakeTicketIdcode();
			Warehouse w;
			Organ o;
			Warehouse inw;
			Organ ino;
			double totalquantity,ttdbbrealquantity;
			Product  p;
			AppFUnit af  =new AppFUnit();
			for(int i=0;i<ttlist.size();i++){
				tempMap =(HashMap)ttlist.get(i);
				ttid = (String)tempMap.get("id");
				proid= (String)tempMap.get("productid");
				
				boxnum=attd.getTotalBoxSumByttidAndpid(ttid, proid);
				scatternum=attd.getTotalScatterSumByttidAndpid(ttid, proid);
				tempMap.put("boxnum", boxnum);
				tempMap.put("scatternum", scatternum);
				
				p =ap.getProductByID(proid);
				ncpid=p.getNccode();
				tempMap.put("pnccode", ncpid);
				
				tempMap.put("pname", p.getProductname());
				tempMap.put("pspecmode", p.getSpecmode());
				
				outwid=  (String)tempMap.get("warehouseid");
				w= aw.getWarehouseByID(outwid);
				outorgan = w.getMakeorganid();
				tempMap.put("warehouseid", w.getWarehousename());
				o = ao.getOrganByID(outorgan);
				tempMap.put("outorganname",o.getOrganname());
				
				inwid = (String) tempMap.get("inwarehouseid");
				inw = aw.getWarehouseByID(inwid);
				inorgan = inw.getMakeorganid();
				ino = ao.getOrganByID(inorgan);
				tempMap.put("inorganname",ino.getOrganname());
				
				quantity =atti.getTotalkgquantityByttidAndpid(ttid, proid);
				if(quantity==null || StringUtil.isEmpty(quantity)){
					totalquantity = 0d;
				}else{
					totalquantity = Double.valueOf(quantity);
				}
				//箱到kg转换数
				double xtsQuantity =af.getXQuantity(proid, 2);
    			//散到kg转换数
    			double stsQuantity =af.getXQuantity(proid, p.getScatterunitid());
    			//得到整箱数
				int q = ((Double)ArithDouble.div(totalquantity, xtsQuantity)).intValue();
				tempMap.put("idcodeboxnum", String.valueOf(q));
				//得到散数
				double tqu = ArithDouble.sub(totalquantity, ArithDouble.mul(xtsQuantity, (double)q));
				tempMap.put("idcodescatternum", String.valueOf(((Double)ArithDouble.div(tqu, stsQuantity)).intValue()));
				
				String tempquantity = attd.getTotalrealquantityByttidAndpid(ttid, proid);
				if(tempquantity==null || StringUtil.isEmpty(tempquantity)){
					tempMap.put("rate","");
				}else{
					ttdbbrealquantity = Double.valueOf(tempquantity);
					if(ttdbbrealquantity==0){
						tempMap.put("rate", "");
					}else{
						double kgquantity = af.getXQuantity(proid, p.getScatterunitid());
						ttdbbrealquantity=ArithDouble.mul(ttdbbrealquantity, kgquantity);
						double rate = ArithDouble.div(totalquantity, ttdbbrealquantity, 2);
						String srate = Double.toString(ArithDouble.mul(rate, 100))+"%";
						tempMap.put("rate",srate);
					}
				}
			}

			if (ttlist.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过" + Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition", "attachment; filename=IdcodeScanRate.xls");
			response.setContentType("application/msexcel");
			writeXls(ttlist, os, request);
			os.flush();
			// os.close();
			DBUserLog.addUserLog(userid, 10, "报表分析>>导出出库扫描率");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				os.close();
			}
		}
		return null;
	}


	public void writeXls(List ttlist, OutputStream os, HttpServletRequest request) throws NumberFormatException, Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		OrganService organ = new OrganService();
		WarehouseService ws = new WarehouseService();
		int snum = 1;
		snum = ttlist.size() / 50000;
		if (ttlist.size() % 50000 >= 0) {
			snum++;
		}
		WritableSheet[] sheets = new WritableSheet[snum];

		try {
			for (int j = 0; j < snum; j++) {
				sheets[j] = workbook.createSheet("sheet" + j, j);
				int currentnum = (j + 1) * 50000;
				if (currentnum >= ttlist.size()) {
					currentnum = ttlist.size();
				}
				int start = j * 50000;

				sheets[j].mergeCells(0, start, 12, start);
				sheets[j].addCell(new Label(0, start, "出库扫描率报表", wchT));
				sheets[j].addCell(new Label(0, start + 1, "发货机构:", seachT));
				sheets[j].addCell(new Label(1, start + 1, organ.getOrganName(map.get("MakeOrganID").toString())));
				sheets[j].addCell(new Label(2, start + 1, "发货仓库:", seachT));
				sheets[j].addCell(new Label(3, start + 1, ws.getWarehouseName(map.get("WarehouseID").toString())));
				sheets[j].addCell(new Label(4, start + 1, "产品:", seachT));
				sheets[j].addCell(new Label(5, start + 1, organ.getOrganName(map.get("ProductName").toString())));

				sheets[j].addCell(new Label(0, start + 2, "制单日期:", seachT));
				sheets[j]
						.addCell(new Label(1, start + 2, map.get("BeginDate").toString() + "--" + map.get("EndDate").toString()));
				sheets[j].addCell(new Label(2, start + 2, "关键字:", seachT));
				sheets[j].addCell(new Label(3, start + 2, map.get("KeyWord").toString()));

				sheets[j].addCell(new Label(0, start + 3, "导出机构:", seachT));
				sheets[j].addCell(new Label(1, start + 3, request.getAttribute("porganname").toString()));
				sheets[j].addCell(new Label(2, start + 3, "导出人:", seachT));
				sheets[j].addCell(new Label(3, start + 3, request.getAttribute("pusername").toString()));
				sheets[j].addCell(new Label(4, start + 3, "导出时间:", seachT));
				sheets[j].addCell(new Label(5, start + 3, DateUtil.getCurrentDateTime()));

				sheets[j].addCell(new Label(0, start + 4, "发货机构", wcfFC));
				sheets[j].addCell(new Label(1, start + 4, "发货仓库", wcfFC));
				sheets[j].addCell(new Label(2, start + 4, "收货机构", wcfFC));
				sheets[j].addCell(new Label(3, start + 4, "单据号", wcfFC));
				sheets[j].addCell(new Label(4, start + 4, "产品内部编号", wcfFC));
				sheets[j].addCell(new Label(5, start + 4, "产品名称", wcfFC));
				sheets[j].addCell(new Label(6, start + 4, "产品规格", wcfFC));
				sheets[j].addCell(new Label(7, start + 4, "出库数量(箱)", wcfFC));
				sheets[j].addCell(new Label(8, start + 4, "出库数量(散)", wcfFC));
				sheets[j].addCell(new Label(9, start + 4, "扫描数量(箱)", wcfFC));
				sheets[j].addCell(new Label(10, start + 4, "扫描数量(散)", wcfFC));
				sheets[j].addCell(new Label(11, start + 4, "扫描率", wcfFC));
				sheets[j].addCell(new Label(12, start + 4, "备注", wcfFC));
				int row = 0;
				Double totalsum = 0.00;
				for (int i = start; i < currentnum; i++) {
					row = i - start + 5;
					Map temp = (HashMap) ttlist.get(i);
					sheets[j].addCell(new Label(0, row, (String) temp.get("outorganname")));
					sheets[j].addCell(new Label(1, row, (String) temp.get("warehouseid")));
					sheets[j].addCell(new Label(2, row, (String) temp.get("inorganname")));
					sheets[j].addCell(new Label(3, row, (String) temp.get("id")));
					sheets[j].addCell(new Label(4, row, (String) temp.get("pnccode")));
					sheets[j].addCell(new Label(5, row, (String) temp.get("pname")));
					sheets[j].addCell(new Label(6, row, (String) temp.get("pspecmode")));
					sheets[j].addCell(new Label(7, row, (String) temp.get("boxnum")));
					if(StringUtil.isEmpty((String)temp.get("scatternum"))){
						sheets[j].addCell(new Label(8, row, (String)temp.get("scatternum")));
					}else{
						sheets[j].addCell(new Label(8, row, Integer.toString(((Double) Double.parseDouble(temp.get("scatternum")
								.toString())).intValue())));
					}
					
					sheets[j].addCell(new Label(9, row, (String) temp.get("idcodeboxnum")));
					sheets[j].addCell(new Label(10, row, (String) temp.get("idcodescatternum")));
					sheets[j].addCell(new Label(11, row, (String) temp.get("rate")));
					sheets[j].addCell(new Label(12, row, (String) temp.get("remark")));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (workbook != null) {
				workbook.write();
				workbook.close();
			}
			if (os != null) {
				os.close();
			}
		}

	}
	
	
}
