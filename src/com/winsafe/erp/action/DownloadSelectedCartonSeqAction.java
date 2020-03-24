package com.winsafe.erp.action;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List; 
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label; 
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.Product;
import com.winsafe.erp.dao.AppCartonSeq;
import com.winsafe.erp.dao.AppProductPlan;
import com.winsafe.erp.pojo.ProductPlan;
import com.winsafe.hbm.util.DateUtil;

public class DownloadSelectedCartonSeqAction extends BaseAction {

	private static Logger logger = Logger.getLogger(DownloadSelectedCartonSeqAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		initdata(request);
		try {
			String id = request.getParameter("ID");

			AppProductPlan appProductPlan = new AppProductPlan();
			AppProduct appProduct = new AppProduct();
			ProductPlan productPlan = appProductPlan.getProductPlanByID(Integer.valueOf(id));
			Product product = appProduct.getProductByID(productPlan.getProductId());
			AppCartonSeq appCs = new AppCartonSeq();
			List<Map<String,String>> covertCode = appCs.getCartonSeq(request, 0, productPlan.getPrintJobId());
			String fileName = productPlan.getId()+product.getProductname()+product.getSpecmode()+"("+DateUtil.formatDate(productPlan.getPackDate(), "yyyyMMdd")+productPlan.getPbatch()+").xls";
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition","attachment; filename="+URLEncoder.encode(fileName,"utf-8"));
			response.setContentType("application/msexcel");
			writeXls(covertCode, os, request, productPlan);
			os.flush();
			os.close();
			
		} catch (Exception e) {
			logger.error("Download Product Plan Code  error:", e);
		}
		return null;
	}
	
	public void writeXls(List<Map<String, String>> covertCode, OutputStream os,
			HttpServletRequest request, ProductPlan productPlan) throws NumberFormatException, Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		
		int snum = 1; 
		snum = covertCode.size() / 50000;
		if (covertCode.size() % 50000 >= 0) {
			snum++;
		}
		WritableSheet[] sheets = new WritableSheet[snum];
		for (int j = 0; j < snum; j++) {
			sheets[j] = workbook.createSheet("sheet" + j, j);
			int currentnum = (j + 1) * 40000;
			if (currentnum >= covertCode.size()) {
				currentnum = covertCode.size();
			}
			int start = j * 40000;
			sheets[j].addCell(new Label(0, start, "打印顺序号", wcfFC));
			sheets[j].addCell(new Label(1, start, "条码", wcfFC));
			sheets[j].addCell(new Label(2, start, "对应箱顺序号", wcfFC));
			sheets[j].addCell(new Label(3, start, "对应托码", wcfFC));
			sheets[j].addCell(new Label(4, start, "释放状态", wcfFC));
			sheets[j].addCell(new Label(5, start, "产品名称", wcfFC));
			sheets[j].addCell(new Label(6, start, "规格", wcfFC));
			sheets[j].addCell(new Label(7, start, "分装日期", wcfFC));
			sheets[j].addCell(new Label(8, start, "产品批次", wcfFC));
			sheets[j].addCell(new Label(9, start, "生产计划编号", wcfFC));
			sheets[j].addCell(new Label(10, start, "物料号", wcfFC));
			sheets[j].addCell(new Label(11, start, "物料中文描述", wcfFC));
			sheets[j].addCell(new Label(12, start, "生产日期", wcfFC));
			sheets[j].addCell(new Label(13, start, "外箱二维码", wcfFC));
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 1;
				Map<String,String> p = covertCode.get(i);
				sheets[j].addCell(new Label(0, row, p.get("printseq")));
				sheets[j].addCell(new Label(1, row, p.get("cartoncode")));
				sheets[j].addCell(new Label(2, row, p.get("seq")));
				sheets[j].addCell(new Label(3, row, p.get("palletcode")));
				if("1".equals(p.get("isrelease"))) {
					sheets[j].addCell(new Label(4, row, "已释放"));
				} else {
					sheets[j].addCell(new Label(4, row, ""));
				}
				sheets[j].addCell(new Label(5, row, p.get("material_name")));
				sheets[j].addCell(new Label(6, row, p.get("pack_size")));
				sheets[j].addCell(new Label(7, row, p.get("packaging_date")));
				sheets[j].addCell(new Label(8, row, productPlan.getPbatch())); 
				sheets[j].addCell(new Label(9, row, request.getParameter("ID")));
				sheets[j].addCell(new Label(10, row, p.get("mcode")));
				sheets[j].addCell(new Label(11, row, p.get("matericalchdes")));
				sheets[j].addCell(new Label(12, row, p.get("production_date")));
				sheets[j].addCell(new Label(13, row, p.get("out_pin_code")));
			}
		}
		workbook.write();
		workbook.close();
		os.close();
	}
}
