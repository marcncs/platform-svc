package com.winsafe.drp.action.warehouse;

import java.io.OutputStream;
import java.util.ArrayList;
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

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductStockpileAll;
import com.winsafe.drp.dao.ProductStockpileForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class ExcPutWaitProductStockAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Double> scatterUnitMap = new HashMap<String, Double>();

		AppTakeTicket att = new AppTakeTicket();

		Map<String, Product> pMap = new HashMap<String, Product>();
		AppProduct ap = new AppProduct();

		String isShowAssistQuantity = request.getParameter("isShowAssistQuantity");

		UsersBean users = UserManager.getUser(request);
		int userid = users.getUserid();
		String keyword = request.getParameter("KeyWord");
		String MakeOrganID = request.getParameter("MakeOrganID");
		WarehouseService aw = new WarehouseService();
		super.initdata(request);
		OutputStream os = null;
		try {
			// String strCondition =" p.id=ps.productid ";
			String strCondition = "  tt.warehouseid = wv.wid and wv.userid=" + userid + "";
			// " ps.warehouseid=wv.wid and wv.userid="+userid+" and (ps.stockpile+ps.prepareout)!=0 and p.id=ps.productid ";//
			// and (ps.stockpile!=0 or ps.prepareout>0) ";
			if (MakeOrganID != null && !"".equals(MakeOrganID)) {
				String ss = getWarehouseId(aw.getWarehouseListByOID(MakeOrganID));
				strCondition += " and tt.warehouseid in(" + ss + ") ";
			}

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);

			String[] tablename = { "ProductStockpileAll", "WarehouseVisit", "Product" };
			// String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String whereSql = " where ";
			if (!StringUtil.isEmpty(request.getParameter("ProductID"))) {
				whereSql += "  ttb.productid ='" + request.getParameter("ProductID") + "' and ";
			}
			if (!StringUtil.isEmpty(request.getParameter("WarehouseID"))) {
				whereSql += "  tt.warehouseid ='" + request.getParameter("WarehouseID") + "' and ";
			}
			// String blur = DbUtil.getBlur(map, tmpMap, "ProductName");
			String blur = DbUtil.getOrBlur(map, tmpMap, "p.id", "ProductName", "PYCode", "SpecMode", "p.nccode");
			String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");

			whereSql = whereSql + blur + leftblur + strCondition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppFUnit af = new AppFUnit();
			AppProductStockpileAll aps = new AppProductStockpileAll();

			List alp = new ArrayList();

			List ttbList = att.getWaitProductStock(whereSql);
			
			if (ttbList.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}

			for (int j = 0; j < ttbList.size(); j++) {
				Map p = (Map) ttbList.get(j);

				ProductStockpileForm psf = new ProductStockpileForm();

				psf.setWarehouseid(p.get("warehouseid").toString());
				psf.setWarehourseidname(p.get("warehousename").toString());
				psf.setSortName(p.get("sortname").toString());
				psf.setNccode(p.get("nccode").toString());
				psf.setPsproductname(p.get("productname").toString());
				psf.setPsspecmode(p.get("specmode").toString());

				Double quantity = Double.valueOf(p.get("quantity").toString());
				Double boxQuantity = Double.valueOf(p.get("boxquantity").toString());
				Double scatterQuantity = Double.valueOf(p.get("scatterquantity").toString());

				// 每小包装kg数
				Double scaq = scatterUnitMap.get(p.get("productid").toString());
				if (scaq == null) {

					Product product = pMap.get(p.get("productid").toString());
					if (product == null) {
						product = ap.getProductByID(p.get("productid").toString());
						if (product == null) {
							// 说明产品不存在
							product = new Product();
						}
						pMap.put(p.get("productid").toString(), product);
					}

					scaq = af.getXQuantity(p.get("productid").toString(), product.getScatterunitid());
					scatterUnitMap.put(p.get("productid").toString(), scaq);
				}

				// 预定出库量
				psf.setPrepareout(ArithDouble.mul(quantity, scaq));
				psf.setXnum(boxQuantity);
				psf.setScatterNum(scatterQuantity);
				// 对应单据日期
				psf.setDate(p.get("makedate").toString().substring(0, 10));
				// 对应单据号
				psf.setBillno(p.get("ttid").toString());

				alp.add(psf);

			}
			
			boolean isshow = false;
			if(isShowAssistQuantity!=null){
				isshow = true;
			}
			
			os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=ListWaitProductStockpile.xls");
			response.setContentType("application/msexcel");
			writeXls(alp, os, request,isshow);
//			os.flush();
//			os.close();
			DBUserLog.addUserLog(userid, 7,"库存统计>>导出待客库存统计");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			os.flush();
			os.close();
		}
		return null;
	}
	
	public void writeXls(List<ProductStockpileForm> list, OutputStream os,
			HttpServletRequest request,boolean flag) throws NumberFormatException, Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		
		
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
			if(flag){
				sheets[j].mergeCells(0, start, 11, start);
			}else{
				sheets[j].mergeCells(0, start, 9, start);
			}
			sheets[j].addCell(new Label(0, start, "待客库存统计(导出) ",wchT));			
			sheets[j].addCell(new Label(0, start+1, "机构:",seachT));
			sheets[j].addCell(new Label(1, start+1, request.getParameter("oname")));
			sheets[j].addCell(new Label(2, start+1, "仓库:", seachT));
			sheets[j].addCell(new Label(3, start+1, request.getParameter("wname")));
			sheets[j].addCell(new Label(4, start+1, "产品类别:", seachT));
			sheets[j].addCell(new Label(5, start+1, request.getParameter("psname")));
			
			sheets[j].addCell(new Label(0, start+2, "产品:", seachT));
			sheets[j].addCell(new Label(1, start+2, request.getParameter("ProductName")));	
	
			sheets[j].addCell(new Label(0, start+3, "导出机构:", seachT));
			sheets[j].addCell(new Label(1, start+3, request.getAttribute("porganname").toString()));
			sheets[j].addCell(new Label(2, start+3, "导出人:", seachT));
			sheets[j].addCell(new Label(3, start+3, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+3, "导出时间:", seachT));
			sheets[j].addCell(new Label(5, start+3, DateUtil.getCurrentDateTime()));
			          
			sheets[j].addCell(new Label(0, start+4, "仓库编号", wcfFC));
			sheets[j].addCell(new Label(1, start+4, "仓库名称", wcfFC));
			sheets[j].addCell(new Label(2, start+4, "产品类别", wcfFC));
			sheets[j].addCell(new Label(3, start+4, "产品内部编号", wcfFC));
			sheets[j].addCell(new Label(4, start+4, "产品名称", wcfFC));
			sheets[j].addCell(new Label(5, start+4, "规格", wcfFC));
			sheets[j].addCell(new Label(6, start+4, "单位", wcfFC));
			sheets[j].addCell(new Label(7, start+4, "预定出库量", wcfFC));
			if(flag){
				sheets[j].addCell(new Label(8, start+4, "辅助数量(箱)", wcfFC));
				sheets[j].addCell(new Label(9, start+4, "辅助数量(EA)", wcfFC));
				sheets[j].addCell(new Label(10, start+4, "单据日期", wcfFC));
				sheets[j].addCell(new Label(11, start+4, "单据编号", wcfFC));
			}else{
				sheets[j].addCell(new Label(8, start+4, "单据日期", wcfFC));
				sheets[j].addCell(new Label(9, start+4, "单据编号", wcfFC));
			}
			
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 5;
				ProductStockpileForm p = (ProductStockpileForm) list.get(i);
				sheets[j].addCell(new Label(0, row, p.getWarehouseid()));
				sheets[j].addCell(new Label(1, row, p.getWarehourseidname()));
				sheets[j].addCell(new Label(2, row, p.getSortName()));
				sheets[j].addCell(new Label(3, row, p.getNccode()));
				sheets[j].addCell(new Label(4, row, p.getPsproductname()));
				sheets[j].addCell(new Label(5, row, p.getPsspecmode()));
				sheets[j].addCell(new Label(6, row, "kg"));
				sheets[j].addCell(new Number(7, row, p.getPrepareout(),QWCF));
				if(flag){
					sheets[j].addCell(new Number(8, row, p.getXnum(),QWCF));
					sheets[j].addCell(new Number(9, row, p.getScatterNum(),QWCF));
					sheets[j].addCell(new Label(10, row, p.getDate()));
					sheets[j].addCell(new Label(11, row, p.getBillno()));
					
				}else{
					sheets[j].addCell(new Label(8, row, p.getDate()));
					sheets[j].addCell(new Label(9, row, p.getBillno()));
				}
			}
		}
		workbook.write();
		workbook.close();
		os.close();
	}
	
	private String getWarehouseId(List wlist) {
		  StringBuffer sb = new StringBuffer();	  
		  for ( int i=0; i<wlist.size(); i++ ){
			  Warehouse w = (Warehouse)wlist.get(i);
			  if ( i==0 ){
				  sb.append("'").append(w.getId()).append("'");
			  }else{
				  sb.append(",'").append(w.getId()).append("'");
			  }		  
		  }
		  return sb.toString();
	  }
}
