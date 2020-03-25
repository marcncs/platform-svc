package com.winsafe.drp.server;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.winsafe.common.util.FileUtil;
import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.dao.AppCacheDao;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppTakeTicketDetailBatchBit;
import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.util.ArithDouble;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.DateFormat;
import jxl.write.Label;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.log4j.Logger;

import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.RuleUtil;
import com.winsafe.drp.util.cache.CacheBean;
import com.winsafe.drp.util.cache.CacheController;
import com.winsafe.drp.util.cache.ICache;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.MailSender;

public class IdcodeScanRateService {
	private Logger logger = Logger.getLogger(IdcodeScanRateService.class);
	private AppTakeTicket att = new AppTakeTicket();
	private AppTakeTicketIdcode atti = new AppTakeTicketIdcode();
	private AppTakeTicketDetailBatchBit attd = new AppTakeTicketDetailBatchBit();


	
	public File getFile(){
		AppCacheDao appCache=new AppCacheDao();

		OutputStream os = null;
		try {

			Date before3Month=DateUtil.addMonth2Date(-3, DateUtil.getCurrentDate());
			
			String dateStr=DateUtil.formatDate(before3Month, "yyyy-MM-dd");
			
			String whereSql=" where ttd.ttid=tt.id and tt.isaudit=1 and tt.MakeDate>='"+dateStr+"'";
			List ttlist = att.getIdcodeScanRateList(null, whereSql);

			Map tempMap = new HashMap();
			String ttid, proid, ncpid, outwid, outorgan;
			String boxnum, scatternum, quantity;

			Warehouse w;
			Organ o;
			double totalquantity, ttdbbrealquantity;
			Product p;
			
			List<Map> totalkgquantityList=atti.queryTotalkgquantityByttidAndpid(whereSql);
			List<Map> batchbitList=attd.getBatchBit(whereSql);
			for (int i = 0; i < ttlist.size(); i++) {
				tempMap = (HashMap) ttlist.get(i);
				ttid = (String) tempMap.get("id");
				proid = (String) tempMap.get("productid");
				
				Map<String,String> map=appCache.getBatchbit(batchbitList,ttid, proid);
				boxnum = map.get("realboxnum");
				scatternum = map.get("realscatternum");
				String tempquantity =map.get("realQuantity");
				
//				boxnum = appCache.getTotalBoxSum(ttid, proid);
//				scatternum = appCache.getTotalScatterSum(ttid, proid);
				tempMap.put("boxnum", boxnum);
				tempMap.put("scatternum", scatternum);

				
				p = appCache.getProduct(proid);
				ncpid = p.getNccode();
				tempMap.put("pnccode", ncpid);

				outwid = (String) tempMap.get("warehouseid");
				w = appCache.getWarehouse(outwid);
				outorgan = w.getMakeorganid();
				tempMap.put("warehouseid", w.getWarehousename());
				o = appCache.getOrgan(outorgan);
				tempMap.put("outorganname", o.getOrganname());

//				quantity = atti.getTotalkgquantityByttidAndpid(ttid, proid);
				quantity = appCache.getTotalkgquantity(totalkgquantityList,ttid, proid);
				if (quantity == null || StringUtil.isEmpty(quantity)) {
					totalquantity = 0d;
				} else {
					totalquantity = Double.valueOf(quantity);
				}
				// 箱到kg转换数
				double xtsQuantity = appCache.getXQuantity(proid, 2);
				// 散到kg转换数
				double stsQuantity = appCache.getXQuantity(proid, p.getScatterunitid());
				// 获取条码箱数
				int q = ((Double) ArithDouble.div(totalquantity, xtsQuantity)).intValue();
				tempMap.put("idcodeboxnum", Integer.toString(q));
				// 获取条码散数
				double tqu = ArithDouble.sub(totalquantity, ArithDouble.mul(xtsQuantity, (double) q));
				tempMap.put("idcodescatternum", Integer.toString(((Double) ArithDouble.div(tqu, stsQuantity)).intValue()));

				
//				String tempquantity = appCache.getTotalrealquantity(ttid, proid);
				if (tempquantity == null || StringUtil.isEmpty(tempquantity)) {
					tempMap.put("rate", "");
				} else {
					ttdbbrealquantity = Double.valueOf(tempquantity);
					if(ttdbbrealquantity==0){
						tempMap.put("rate", "");
					}else{
						double kgquantity = appCache.getXQuantity(proid, p.getScatterunitid());
						ttdbbrealquantity = ArithDouble.mul(ttdbbrealquantity, kgquantity);
						double rate = ArithDouble.div(totalquantity, ttdbbrealquantity, 2);
						String srate = Double.toString(ArithDouble.mul(rate, 100)) + "%";
						tempMap.put("rate", srate);
					}
				}
//				appCache.clearTTDBBList(ttid, proid);
			}

			URL url = MailSender.class.getClassLoader().getResource("");
			File temp=new File(url.getPath());
			File root=temp.getParentFile().getParentFile();
			File reportDir=new File(root.getAbsolutePath()+File.separator+"upload"+File.separator+"report");
			FileUtil.createDir(reportDir);
			File file=FileUtil.getUniqueFile("IdcodeScanRateReport.xls", reportDir.getAbsolutePath());
			
			os=new FileOutputStream(file);
			writeXls(ttlist, os,before3Month,new Date());
			os.flush();

			return file;
		} catch (Exception e) {
			logger.error("",e);
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					logger.error("",e);
				}
			}
		}
		return null;
	}


	public void writeXls(List ttlist, OutputStream os,Date startDate,Date endDate) throws NumberFormatException, Exception {
		
		WritableCellFormat wcfN = null;
		WritableCellFormat QWCF = null;
		WritableCellFormat wcfFC = null;
		WritableCellFormat wchT = null;
		WritableCellFormat wcfDF = null;
		WritableCellFormat seachT = null;

		NumberFormat nf = new NumberFormat("#,##0.00");
		wcfN = new WritableCellFormat(nf);

		NumberFormat QNF = new NumberFormat("#,##0.00");
		QWCF = new WritableCellFormat(QNF);

		WritableFont wfct = new WritableFont(WritableFont.ARIAL, 10,
				WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE);
		wchT = new WritableCellFormat(wfct);
		wchT.setAlignment(Alignment.CENTRE);
		
		WritableFont wfc = new WritableFont(WritableFont.ARIAL, 10,
				WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE);
		wcfFC = new WritableCellFormat(wfc);
		wcfFC.setBackground(Colour.GREY_25_PERCENT);

		
		WritableFont wfcs = new WritableFont(WritableFont.ARIAL, 10,
				WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE);
		seachT= new WritableCellFormat(wfcs);

		DateFormat df = new DateFormat("yyyy-MM-dd hh:mm:ss");
		wcfDF = new WritableCellFormat(df);
		
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
				sheets[j].addCell(new Label(1, start + 1, ""));
				sheets[j].addCell(new Label(2, start + 1, "发货仓库:", seachT));
				sheets[j].addCell(new Label(3, start + 1, ""));
				sheets[j].addCell(new Label(4, start + 1, "产品:", seachT));
				sheets[j].addCell(new Label(5, start + 1, ""));

				sheets[j].addCell(new Label(0, start + 2, "制单日期:", seachT));
				sheets[j]
						.addCell(new Label(1, start + 2, DateUtil.formatDate(startDate,"yyyy-MM-dd")+"--"+DateUtil.formatDate(endDate,"yyyy-MM-dd")));
				sheets[j].addCell(new Label(2, start + 2, "关键字:", seachT));
				sheets[j].addCell(new Label(3, start + 2, ""));

				sheets[j].addCell(new Label(0, start + 3, "导出机构:", seachT));
				sheets[j].addCell(new Label(1, start + 3, ""));
				sheets[j].addCell(new Label(2, start + 3, "导出人:", seachT));
				sheets[j].addCell(new Label(3, start + 3, ""));
				sheets[j].addCell(new Label(4, start + 3, "导出时间:", seachT));
				sheets[j].addCell(new Label(5, start + 3, DateUtil.getCurrentDateTime()));

				sheets[j].addCell(new Label(0, start + 4, "单据编号", wcfFC));
				sheets[j].addCell(new Label(1, start + 4, "出库单号", wcfFC));
				sheets[j].addCell(new Label(2, start + 4, "订单编号", wcfFC));
				sheets[j].addCell(new Label(3, start + 4, "发货机构", wcfFC));
				sheets[j].addCell(new Label(4, start + 4, "发货仓库", wcfFC));
				sheets[j].addCell(new Label(5, start + 4, "产品编号", wcfFC));
				sheets[j].addCell(new Label(6, start + 4, "产品内部编号", wcfFC));
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
					sheets[j].addCell(new Label(0, row, (String) temp.get("billno")));
					sheets[j].addCell(new Label(1, row, (String) temp.get("id")));
					sheets[j].addCell(new Label(2, row, (String) temp.get("nccode")));
					sheets[j].addCell(new Label(3, row, (String) temp.get("outorganname")));
					sheets[j].addCell(new Label(4, row, (String) temp.get("warehouseid")));
					sheets[j].addCell(new Label(5, row, (String) temp.get("productid")));
					sheets[j].addCell(new Label(6, row, (String) temp.get("pnccode")));
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
