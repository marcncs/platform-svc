package com.winsafe.drp.action.sales;

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

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.ProductForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class ExcPutFindPriceAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {

			String strorganid = request.getParameter("equiporganid");
			String organid = "";
			if (strorganid != null && strorganid.length() > 0) {
				organid = strorganid;
			} else {
				organid = users.getMakeorganid();
			}

			String strCondition = " p.id=op.productid and op.policyid=1 and organid='"
					+ organid + "' and p.useflag=1 ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Product", "OrganPrice" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
	
			String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");
			String priceCondition = DbUtil.getPriceCondition(map, tmpMap,
					"UnitPrice");
			String blur = DbUtil.getOrBlur(map, tmpMap,"p.ID","op.ID","ProductName","PYCode",
					"SpecMode");
			whereSql = whereSql + leftblur + blur + priceCondition
					+ strCondition;
			whereSql = DbUtil.getWhereSql(whereSql); 
			AppProduct ap = new AppProduct();
			List pls = ap.findProductPrice(whereSql);
			List<ProductForm> sls = new ArrayList<ProductForm>();
			Double pricei=0d;
			Double priceii = 0d;

			for (int i = 0; i < pls.size(); i++) {
				ProductForm pf = new ProductForm();
				Object[] o = (Object[]) pls.get(i);
				pf.setId(String.valueOf(o[0]));
				pf.setProductname(o[1].toString());
				pf.setSpecmode(String.valueOf(o[2]));
				pf.setCountunit(Integer.valueOf(o[3].toString()));
				pf.setCountunitname(HtmlSelect.getResourceName(request,
						"CountUnit", Integer.parseInt(o[3].toString())));
				pricei= ap.getProductPriceByOIDPIDRate(
						users.getMakeorganid(), pf.getId(), pf.getCountunit(),
						0);
				pf.setPricei(pricei);
				priceii = Double.valueOf(o[4].toString());
				pf.setPriceii(priceii);
				sls.add(pf);
			}
			if (sls.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=ListInteralI.xls");
			response.setContentType("application/msexcel");
			writeXls(sls, os, request);
			os.flush();
			os.close();
			DBUserLog.addUserLog(userid,6,"零售管理>>导出零售查价!");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void writeXls(List<ProductForm> list, OutputStream os,
			HttpServletRequest request) throws NumberFormatException, Exception {
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
			
			sheets[j].mergeCells(0, start, 5, start);
			sheets[j].addCell(new Label(0, start, "零售查价",wchT));
			
			sheets[j].addCell(new Label(0, start+1, "产品类别:", seachT));
			sheets[j].addCell(new Label(1, start+1, request.getParameter("productstruts")));
			sheets[j].addCell(new Label(2, start+1, "价格范围:", seachT));
			sheets[j].addCell(new Label(3, start+1, request.getParameter("BeginPrice")+"-"+request.getParameter("EndPrice")));
			sheets[j].addCell(new Label(4, start+1, "送货机构:",seachT));
			sheets[j].addCell(new Label(5, start+1, request.getParameter("oname")));
		
			sheets[j].addCell(new Label(0, start+2, "关键字:", seachT));
			sheets[j].addCell(new Label(1, start+2, request.getParameter("KeyWord")));
			
			sheets[j].addCell(new Label(0, start+3, "导出机构:", seachT));
			sheets[j].addCell(new Label(1, start+3, request.getAttribute("porganname").toString()));
			sheets[j].addCell(new Label(2, start+3, "导出人:", seachT));
			sheets[j].addCell(new Label(3, start+3, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+3, "导出时间:", seachT));
			sheets[j].addCell(new Label(5, start+3, DateUtil.getCurrentDateTime()));
			          
			sheets[j].addCell(new Label(0, start+4, "产品编号",wcfFC));
			sheets[j].addCell(new Label(1, start+4, "产品名称",wcfFC));
			sheets[j].addCell(new Label(2, start+4, "规格",wcfFC));
			sheets[j].addCell(new Label(3, start+4, "单位",wcfFC));
			sheets[j].addCell(new Label(4, start+4, "非会员价",wcfFC));
			sheets[j].addCell(new Label(5, start+4, "会员价",wcfFC));
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 5;
				ProductForm p = (ProductForm) list.get(i);
				sheets[j].addCell(new Label(0, row, p.getId().toString()));
				sheets[j].addCell(new Label(1, row, p.getProductname()));
				sheets[j].addCell(new Label(2, row,p.getSpecmode() ));
				sheets[j].addCell(new Label(3, row, p.getCountunitname()));
				sheets[j].addCell(new Number(4, row, p.getPricei(),wcfN));
				sheets[j].addCell(new Number(5, row, p.getPriceii(),wcfN));
				
			}

		}
		workbook.write();
		workbook.close();
		os.close();
	}
}
