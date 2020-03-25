package com.winsafe.drp.action.warehouse;

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

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppStockWasteBook;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;
import com.winsafe.hbm.util.StringUtil;

public class ExcPutStockWasteBookAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		int userid = users.getUserid();
		
		String showRemarkDetail = request.getParameter("showRemarkDetail");
		String isShowAssistQuantity= request.getParameter("isShowAssistQuantity");

		super.initdata(request);
		try {
			String condition = "";
			if(DbUtil.isDealer(users)) {
				condition += " s.warehouseid in (select wv.warehouse_Id from  Rule_User_Wh wv where  wv.user_Id="+userid+") and ";
			} else { 
				condition += DbUtil.getWhereCondition(users, "o")+" and ";
			}
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);

			String[] tablename = { "StockWasteBook" };
			String whereSql = EntityManager.getTmpWhereSql2(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap, " RecordDate");
			String leftblur = DbUtil.getBlurLeft(map, tmpMap, "p.PSID");
			whereSql = whereSql + leftblur + timeCondition + condition ;
			
			//#start modified by ryan.xi 20150610
			if(StringUtil.isEmpty((String)map.get("WarehouseID")) && !StringUtil.isEmpty((String)map.get("MakeOrganID"))) {
				whereSql += " w.makeorganid = '"+map.get("MakeOrganID")+"' ";
			}
			//#end modified by ryan.xi 20150610
			
			whereSql = DbUtil.getWhereSql(whereSql);
			AppStockWasteBook aswb = new AppStockWasteBook();
			List<Map<String,String>> sals = aswb.getStockWastBookList(request, 0, whereSql,users);

			if (sals.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过" + Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition", "attachment; filename=ListStockWasteBook.xls");
			response.setContentType("application/msexcel");
			writeXls(sals, os, request,isShowAssistQuantity,showRemarkDetail);
			os.flush();
			os.close();
			DBUserLog.addUserLog(request,"列表");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void writeXls(List list, OutputStream os, HttpServletRequest request,String isShowAssistQuantity,String showRemarkDetail) throws NumberFormatException, Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		WarehouseService ws = new WarehouseService();
		Map<String,Double> boxUnitMap  = new HashMap<String, Double>();
		Map<String,Double> scatterUnitMap  = new HashMap<String, Double>();
		Map<String,Product> pMap = new HashMap<String, Product>();
		AppProduct ap =new AppProduct();
		int snum = 1;
		snum = list.size() / 50000;
		if (list.size() % 50000 >= 0) {
			snum++;
		}
		WritableSheet[] sheets = new WritableSheet[snum];
		for (int j = 0; j < snum; j++) {
			sheets[j] = workbook.createSheet("sheet" + j, j);
			int currentnum = (j + 1) * 50000;
			if (currentnum >= list.size()) {
				currentnum = list.size();
			}
			int start = j * 50000;
			sheets[j].mergeCells(0, start, 15, start);
			sheets[j].addCell(new Label(0, start, "库存台账 (导出)", wchT));
			sheets[j].addCell(new Label(0, start+1, "机构:",seachT));
			sheets[j].addCell(new Label(1, start+1, request.getParameter("oname")));
			sheets[j].addCell(new Label(2, start + 1, "仓库:", seachT));
			sheets[j].addCell(new Label(3, start + 1, request.getParameter("wname")));
//			sheets[j].addCell(new Label(2, start + 1, "仓位:", seachT));
//			sheets[j].addCell(new Label(3, start + 1, request.getParameter("bitname")));
			sheets[j].addCell(new Label(4, start+1, "产品类别:", seachT));
			sheets[j].addCell(new Label(5, start+1, request.getParameter("psname")));
			
			
			sheets[j].addCell(new Label(0, start + 2, "产品:", seachT));
			sheets[j].addCell(new Label(1, start + 2, request.getParameter("ProductName")));
			sheets[j].addCell(new Label(2, start + 2, "日期:", seachT));
			sheets[j].addCell(new Label(3, start + 2, request.getParameter("BeginDate") + "-" + request.getParameter("EndDate")));

			sheets[j].addCell(new Label(0, start + 3, "导出机构:", seachT));
			sheets[j].addCell(new Label(1, start + 3, request.getAttribute("porganname").toString()));
			sheets[j].addCell(new Label(2, start + 3, "导出人:", seachT));
			sheets[j].addCell(new Label(3, start + 3, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start + 3, "导出时间:", seachT));
			sheets[j].addCell(new Label(5, start + 3, DateUtil.getCurrentDateTime()));

			
			sheets[j].addCell(new Label(0, start + 4, "仓库编号", wcfFC));
			sheets[j].addCell(new Label(1, start + 4, "仓库名称", wcfFC));
			sheets[j].addCell(new Label(2, start + 4, "产品类别", wcfFC));
			sheets[j].addCell(new Label(3, start + 4, "物料号", wcfFC));
			sheets[j].addCell(new Label(4, start + 4, "产品名称", wcfFC));
			sheets[j].addCell(new Label(5, start + 4, "产品规格", wcfFC));
			sheets[j].addCell(new Label(6, start + 4, "批号", wcfFC));
			sheets[j].addCell(new Label(7, start + 4, "单位", wcfFC));
			sheets[j].addCell(new Label(8, start + 4, "日期", wcfFC));
//			sheets[j].addCell(new Label(4, start + 4, "仓位", wcfFC));
			sheets[j].addCell(new Label(9, start + 4, "单据号", wcfFC));
			sheets[j].addCell(new Label(10, start + 4, "摘要", wcfFC));
			sheets[j].addCell(new Label(11, start + 4, "期初数量", wcfFC));
			sheets[j].addCell(new Label(12, start + 4, "收入数量", wcfFC));
			sheets[j].addCell(new Label(13, start + 4, "发出数量", wcfFC));
			sheets[j].addCell(new Label(14, start + 4, "结存数量", wcfFC));
//			if(isShowAssistQuantity!=null){
//				sheets[j].addCell(new Label(14, start + 4, "辅助数量(箱)", wcfFC));
//				sheets[j].addCell(new Label(15, start + 4, "辅助数量(EA)", wcfFC));
//			}
			
			int row = 0;
			AppFUnit af = new AppFUnit();
			for (int i = start; i < currentnum; i++) {
				row = i - start + 5;
				Map p = (Map) list.get(i);
//				int unitid = Integer.valueOf(p.get("sunit").toString());
				String productid = p.get("productid").toString();
				sheets[j].addCell(new Label(0, row, p.get("warehouseid").toString()));
				sheets[j].addCell(new Label(1, row, p.get("warehouseidname").toString()));
				sheets[j].addCell(new Label(2, row, p.get("sortname").toString()));
				sheets[j].addCell(new Label(3, row, p.get("nccode")==null?"":p.get("nccode").toString()));
				sheets[j].addCell(new Label(4, row, p.get("productname").toString()));
				sheets[j].addCell(new Label(5, row, p.get("specmode").toString()));
				sheets[j].addCell(new Label(6, row, p.get("batch").toString()));
				
				sheets[j].addCell(new Label(7, row, HtmlSelect.getResourceName(request,"CountUnit", Constants.DEFAULT_UNIT_ID)));
				sheets[j].addCell(new Label(8, row, p.get("recorddate").toString().substring(0, 10)));
				sheets[j].addCell(new Label(9, row, p.get("billcode").toString()));
				
				if(showRemarkDetail!=null){
					sheets[j].addCell(new Label(10, row, p.get("memo").toString()));
				}else{
//					if(p.get("memo").toString().contains("出")){
//						sheets[j].addCell(new Label(10, row, "出库"));
//					}else{
//						sheets[j].addCell(new Label(10, row, "入库"));
//					}
					sheets[j].addCell(new Label(10, row, p.get("memo") ==null ? "" : p.get("memo").toString()));
				}
				Double xquantity = af.getXQuantity(p.get("productid").toString(), Constants.DEFAULT_UNIT_ID);
				if (xquantity != 0) {
					sheets[j].addCell(new Number(11, row, Double.valueOf(p.get("cyclefirstquantity").toString())/xquantity, QWCF));
					sheets[j].addCell(new Number(12, row, Double.valueOf(p.get("cycleinquantity").toString())/xquantity, QWCF));
					sheets[j].addCell(new Number(13, row, Double.valueOf(p.get("cycleoutquantity").toString())/xquantity, QWCF));
					sheets[j].addCell(new Number(14, row, Double.valueOf(p.get("cyclebalancequantity").toString())/xquantity, QWCF));
				}else {
					sheets[j].addCell(new Number(11, row, 0.0, QWCF));
					sheets[j].addCell(new Number(12, row, 0.0, QWCF));
					sheets[j].addCell(new Number(13, row, 0.0, QWCF));
					sheets[j].addCell(new Number(14, row, 0.0, QWCF));
				}
				
				
				 //是否显示辅助数量
		        if(isShowAssistQuantity!=null){
		        	//显示
		        	
		        	//每箱多少kg
		        	Double Xquantity = boxUnitMap.get(productid);
		        	if(Xquantity == null){
		        		Xquantity = af.getXQuantity(productid, 2);
		        		boxUnitMap.put(productid, Xquantity);
		        	}
		            
		          //每小包装kg数
		        	Double scaq = scatterUnitMap.get(productid);
		        	if(scaq == null){
		        		Product product = pMap.get(productid);
		        		if(product==null){
		        			product = ap.getProductByID(productid);
		        			if(product==null){
		        				//说明产品不存在
		        				product = new Product();
		        			}
		        			pMap.put(productid, product);
		        		}
		        		
		        		scaq = af.getXQuantity(productid, product.getScatterunitid());
		        		scatterUnitMap.put(productid, scaq);
		        	}
		        	
		        	
					Double cyclebalancequantity = Double.valueOf(p.get("cyclebalancequantity").toString());
		        	
					Double BoxNum = (double)((Double)ArithDouble.div(cyclebalancequantity, Xquantity)).intValue();
					
		             Double other = ArithDouble.sub(cyclebalancequantity, ArithDouble.mul(Xquantity, BoxNum));
		             Double ScatterNum = ArithDouble.div(other, scaq);
		             
		             sheets[j].addCell(new Number(15, row, BoxNum, QWCF));
					 sheets[j].addCell(new Number(16, row, ScatterNum, QWCF));
						
		        	
		        }
			}

		}
		workbook.write();
		workbook.close();
		os.close();
	}

}
