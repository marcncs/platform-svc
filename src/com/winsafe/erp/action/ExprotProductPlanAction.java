package com.winsafe.erp.action;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.BaseResource;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.ESAPIUtil; 
import com.winsafe.erp.dao.AppPrepareCode;
import com.winsafe.erp.dao.AppProductPlan;
import com.winsafe.erp.dao.AppUnitInfo;
import com.winsafe.erp.pojo.ProductPlan;
import com.winsafe.erp.pojo.ProductPlanForm;
import com.winsafe.erp.pojo.UnitInfo;
import com.winsafe.hbm.util.Constants;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class ExprotProductPlanAction extends BaseAction {
	private static Logger logger = Logger
			.getLogger(ListProductPlanAction.class);
	private AppProduct appProduct = new AppProduct();
	private AppOrgan aog = new AppOrgan();
	private AppProductPlan appProductPlan = new AppProductPlan();
	private static AppPrepareCode auv = new AppPrepareCode();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		Map map = new HashMap(request.getParameterMap());
		Map tmpMap = EntityManager.scatterMap(map);
		String Condition = " organId in (select makeorganid from Warehouse where id in (select ruw.warehouseId from RuleUserWh ruw where ruw.userId = "
				+ userid + " and activeFlag = 1))";
		String[] tablename = { "ProductPlan" };
		// 查询条件
		String whereSql = EntityManager.getTmpWhereSql2(map, tablename);
		// 关键字查询条件
		String blur = DbUtil.getOrBlur(map, tmpMap, "id", "PONO", "mbatch",
				"pbatch", "productId");

		String proTimeCondition = getProTimeCondition(map, tmpMap, " proDate");
		String packTimeCondition = getPackTimeCondition(map, tmpMap,
				" packDate");

		whereSql = whereSql + blur + proTimeCondition + packTimeCondition
				+ Condition;
		whereSql = DbUtil.getWhereSql(whereSql);

		List<ProductPlan> configs = appProductPlan.getProductPlanAll(whereSql);
		List<ProductPlanForm> ProductPlanForms = new ArrayList<ProductPlanForm>();
		for (ProductPlan ProductPlan : configs) {
			ProductPlanForm ProductPlanForm = new ProductPlanForm();
			ProductPlanForm.setId(ProductPlan.getId());
			ProductPlanForm.setPONO(ProductPlan.getPONO());
			ProductPlanForm.setTemp(ProductPlan.getTemp());
			ProductPlanForm.setOrganId(ProductPlan.getOrganId());
			ProductPlanForm.setProductId(ProductPlan.getProductId());
			ProductPlanForm.setMbatch(ProductPlan.getMbatch());
			ProductPlanForm.setPbatch(ProductPlan.getPbatch());
			ProductPlanForm.setProDate(ProductPlan.getProDate());
			ProductPlanForm.setPackDate(ProductPlan.getPackDate());
			ProductPlanForm.setBoxnum(ProductPlan.getBoxnum());
			ProductPlanForm.setCopys(ProductPlan.getCopys());
			ProductPlanForm.setApprovalMan(ProductPlan.getApprovalMan());
			ProductPlanForm.setApprovalFlag(ProductPlan.getApprovalFlag());
			ProductPlanForm.setCloseFlag(ProductPlan.getCloseFlag());
			ProductPlanForm.setCloseMan(ProductPlan.getCloseMan());
			if(ProductPlan.getCopys()!=null&&ProductPlan.getBoxnum()!=null) {
				ProductPlanForm.setTotalnum(String.valueOf(ProductPlan.getCopys()*ProductPlan.getBoxnum()));
			} else {
				ProductPlanForm.setTotalnum(String.valueOf(ProductPlan.getBoxnum()));
			}
			
			
			Product product = appProduct.getProductByID(ProductPlanForm.getProductId());
			if (product != null) {
				ProductPlanForm.setProductname(product.getProductname());
				ProductPlanForm.setSpecmode(product.getSpecmode());
				ProductPlanForm.setPacksize(product.getPackSizeName());
				ProductPlanForm.setMcode(product.getmCode());
				ProductPlanForm.setCountNum(CountNum(product,ProductPlan));
			} else {
				ProductPlanForm.setProductname("");
				ProductPlanForm.setSpecmode("");
				ProductPlanForm.setPacksize("");
				ProductPlanForm.setMcode("");
				ProductPlanForm.setCountNum("");
			}
			//工厂名称
			Organ og = aog.getOrganByID(ProductPlan.getOrganId());
			
			if(og!=null){
				ProductPlanForm.setOrganname(ESAPIUtil.decodeForHTML(og.getOrganname()));
			}else{
				ProductPlanForm.setOrganname("");
			}
			//托数
			ProductPlanForm.setTnum(Tnum(ProductPlan));
			//已经释放箱数
			ProductPlanForm.setReaLnum(reaLnum(ProductPlan));
			
			if(ProductPlan.getBoxnum()!=null&&ProductPlanForm.getReaLnum()!=null) {
				ProductPlanForm.setSjnum(ProductPlan.getBoxnum()-ProductPlanForm.getReaLnum());
			} else {
				ProductPlanForm.setSjnum(0);
			}
			
			
			ProductPlanForms.add(ProductPlanForm);
		}
		
		if (ProductPlanForms.size() > Constants.EXECL_MAXCOUNT) {
			request.setAttribute("result", "当前记录数超过" + Constants.EXECL_MAXCOUNT + "条，请重新查询！");
			return new ActionForward("/sys/lockrecord2.jsp");
		}
		OutputStream os = response.getOutputStream();
		response.reset();
		response.setHeader("content-disposition", "attachment; filename=ListProductPlan.xls");
		response.setContentType("application/msexcel");
		writeXls(ProductPlanForms, os, request);
		os.flush();
		os.close();
		//DBUserLog.addUserLog(userid, 2, "采购管理>>导出生产计划");
		return null;
	}

	public static String getProTimeCondition(Map map, Map tmpMap, String field) {
		StringBuffer buf = new StringBuffer();
		if (map.containsKey("proBeginDate")) {
			String timeField = (String) tmpMap.get("proBeginDate");
			if (timeField != null && !timeField.equals("")) {

				buf.append("pp."+field + ">=to_date('" + timeField
						+ "','yyyy-MM-dd hh24:mi:ss')");
				buf.append(" and ");
			}
		}
		if (map.containsKey("proEndDate")) {
			String timeField = (String) tmpMap.get("proEndDate");
			if (timeField != null && !timeField.equals("")) {

				buf.append("pp."+field + "<=to_date('" + timeField
						+ " 23:59:59','yyyy-MM-dd hh24:mi:ss')");
				buf.append(" and ");
			}
		}
		return buf.toString();
	}

	public static String getPackTimeCondition(Map map, Map tmpMap, String field) {
		StringBuffer buf = new StringBuffer();
		if (map.containsKey("packBeginDate")) {
			String timeField = (String) tmpMap.get("packBeginDate");
			if (timeField != null && !timeField.equals("")) {

				buf.append("pp."+field + ">=to_date('" + timeField
						+ "','yyyy-MM-dd hh24:mi:ss')");
				buf.append(" and ");
			}
		}
		if (map.containsKey("packEndDate")) {
			String timeField = (String) tmpMap.get("packEndDate");
			if (timeField != null && !timeField.equals("")) {

				buf.append("pp."+field + "<=to_date('" + timeField
						+ " 23:59:59','yyyy-MM-dd hh24:mi:ss')");
				buf.append(" and ");
			}
		}
		return buf.toString();
	}

	public void writeXls(List<ProductPlanForm> list,
			OutputStream os, HttpServletRequest request)
			throws NumberFormatException, Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);

		WritableFont wfct = new WritableFont(WritableFont.ARIAL, 10,
				WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE);
		WritableCellFormat wchT = new WritableCellFormat(wfct);
		wchT.setAlignment(Alignment.CENTRE);

		WritableFont wfc = new WritableFont(WritableFont.ARIAL, 10,
				WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE);
		WritableCellFormat wcfFC = new WritableCellFormat(wfc);
		wcfFC.setBackground(Colour.GREY_25_PERCENT);

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
			sheets[j].mergeCells(0, start, 19, start);
			sheets[j].setColumnView(0, 19);
			sheets[j].addCell(new Label(0, start, "生产计划报表", wchT));

			int index = 0;
			sheets[j].addCell(new Label(index++, start + 2, "序号", wcfFC));
			sheets[j].addCell(new Label(index++, start + 2, "PO编号", wcfFC));
			sheets[j].addCell(new Label(index++, start + 2, "工厂", wcfFC));
			sheets[j].addCell(new Label(index++, start + 2, "产品ID", wcfFC));
			sheets[j].addCell(new Label(index++, start + 2, "产品名称", wcfFC));
			sheets[j].addCell(new Label(index++, start + 2, "产品规格", wcfFC));
			sheets[j].addCell(new Label(index++, start + 2, "产品批次", wcfFC));
			sheets[j].addCell(new Label(index++, start + 2, "物料号", wcfFC));
			sheets[j].addCell(new Label(index++, start + 2, "物料批次", wcfFC));
			sheets[j].addCell(new Label(index++, start + 2, "生产总量", wcfFC));
			sheets[j].addCell(new Label(index++, start + 2, "计划箱数", wcfFC));
			sheets[j].addCell(new Label(index++, start + 2, "实际箱数", wcfFC));
			sheets[j].addCell(new Label(index++, start + 2, "标签总数", wcfFC));
			sheets[j].addCell(new Label(index++, start + 2, "已释放箱数", wcfFC));
			sheets[j].addCell(new Label(index++, start + 2, "托盘数量", wcfFC));
			sheets[j].addCell(new Label(index++, start + 2, "生产日期", wcfFC));
			sheets[j].addCell(new Label(index++, start + 2, "分装日期", wcfFC));
			//sheets[j].addCell(new Label(index++, start + 2, "生成状态", wcfFC));
			sheets[j].addCell(new Label(index++, start + 2, "是否结束", wcfFC));
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 3;
				int number = 0;
				ProductPlanForm p = (ProductPlanForm) list.get(i);
				sheets[j].addCell(new Label(number++, row, String.valueOf(i + 1)));
				sheets[j].addCell(new Label(number++, row, p.getPONO()));
				sheets[j].addCell(new Label(number++, row, ESAPIUtil.decodeForHTML(p.getOrganname())));
				sheets[j].addCell(new Label(number++, row, p.getProductId()));
				sheets[j].addCell(new Label(number++, row, p.getProductname()));
				sheets[j].addCell(new Label(number++, row, p.getSpecmode()));
				sheets[j].addCell(new Label(number++, row, p.getPbatch()));
				sheets[j].addCell(new Label(number++, row, p.getMcode()));
				sheets[j].addCell(new Label(number++, row, p.getMbatch()));
				sheets[j].addCell(new Label(number++, row, p.getCountNum()));//生产总量
				sheets[j].addCell(new Number(number++, row, p.getBoxnum()));//计划箱数
				sheets[j].addCell(new Number(number++, row, p.getSjnum()));//实际箱数
				if(p.getTotalnum()!=null){
					sheets[j].addCell(new Number(number++, row, Integer.parseInt(p.getTotalnum())));//标签总数
				}else{
					sheets[j].addCell(new Number(number++, row, 0));//标签总数
				}
				sheets[j].addCell(new Number(number++, row, p.getReaLnum()));
				sheets[j].addCell(new Number(number++, row, p.getTnum()));
				sheets[j].addCell(new Label(number++, row, DateUtil.formatDate(p.getProDate())));
				sheets[j].addCell(new Label(number++, row, DateUtil.formatDate(p.getPackDate())));
				//sheets[j].addCell(new Label(number++, row, p.getTemp()));
				if(p.getCloseFlag()!=null){
					sheets[j].addCell(new Label(number++, row, HtmlSelect.getNameByOrder(request, "YesOrNo", p.getCloseFlag())));
				}else{
					sheets[j].addCell(new Label(number++, row,""));
				}
			}
		}
		workbook.write();
		workbook.close();
		os.close();
	}
	
	/**
	 * 总量
	 * @param product
	 * @param ProductPlan
	 * @return
	 * @throws Exception
	 */
	public static String CountNum(Product product,ProductPlan ProductPlan) throws Exception{
		
		String zongliang = "";
		if((product.getBoxquantity() != null && product.getSunit()>0 && product.getBoxquantity() != 0d) ){
			AppFUnit appFUnit = new AppFUnit();
			Double xquantity = appFUnit.getQuantityByProductId(product.getId());
			if (xquantity!= null) {
				//计算
				Double getmu = product.getBoxquantity()*xquantity*ProductPlan.getBoxnum();
				BaseResource brByUnit = new AppBaseResource().getBaseResourceValue("CountUnit", product.getCountunit());
				zongliang = getmu+brByUnit.getTagsubvalue();
			} 
		}
		return zongliang;
	}
	/**
	 * 托数
	 * @param ProductPlan
	 * @return
	 * @throws Exception
	 */
	public static Integer Tnum(ProductPlan ProductPlan) throws Exception{
		
		Integer tnum = 0;
		AppUnitInfo appu = new AppUnitInfo();
		UnitInfo unitinfo = appu.getUnitInfoByOidAndPid(ProductPlan.getOrganId(),ProductPlan.getProductId());
		if(null!= unitinfo&&ProductPlan.getBoxnum()!=null){
			tnum = (int) Math.ceil(ProductPlan.getBoxnum().doubleValue()/unitinfo.getUnitCount().doubleValue());
			return tnum;
		}else{
			
			return 0;
		}
	}
	/**
	 * 已释放箱数
	 * @param ProductPlan
	 * @return
	 * @throws Exception
	 */
	public static Integer reaLnum(ProductPlan ProductPlan) throws Exception{
		String where = " where productPlanId ="+ProductPlan.getId();
		Integer realnum = auv.countReleaseCode(where);
		return realnum;
	}
}