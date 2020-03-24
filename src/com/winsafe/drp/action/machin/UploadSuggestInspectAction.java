package com.winsafe.drp.action.machin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCustomerMatchOrder;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppSuggestInspect;
import com.winsafe.drp.dao.AppSuggestInspectDetail;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.CustomerMatchOrder;
import com.winsafe.drp.dao.IdcodeUploadForm;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.SuggestInspect;
import com.winsafe.drp.dao.SuggestInspectDetail;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.hbm.util.DateUtil;

/**
 * 管家婆订单上传
 * 
 * @author Andy.liu
 * 
 */
public class UploadSuggestInspectAction extends BaseAction {
	private Logger logger = Logger.getLogger(UploadSuggestInspectAction.class);
	AppSuggestInspect asi = new AppSuggestInspect();
	AppSuggestInspectDetail asid = new AppSuggestInspectDetail();
	AppWarehouse aw = new AppWarehouse();
	AppCustomerMatchOrder acmo = new AppCustomerMatchOrder();
	AppProduct ap = new AppProduct();

	int typeIdNo = 1;
	int typeNameNo = 2;
	int makeNameNo = 13;
	int makeDateNo = 14;
	int siidNo = 15;
	int disWarehouseNo = 17;
	int souWarehouseNo = 18;
	static Map<String, Integer[]> argMap = new HashMap<String, Integer[]>();
	// argMap[0] == 是否过账行号 argMap[1] == 商品开始行号 argMap[2] == 客户编号行号
	static {
		argMap.put("2768", new Integer[] { 43, 49, 25 });
		argMap.put("21", new Integer[] { 37, 45 });
		argMap.put("2710", new Integer[] { 21, 24 });
		argMap.put("2", new Integer[] { 44, 50, 25 });
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {

			IdcodeUploadForm mf = (IdcodeUploadForm) form;
			FormFile idcodefile = (FormFile) mf.getIdcodefile();

			boolean bool = false;
			if (idcodefile != null && !idcodefile.equals("")) {
				if (idcodefile.getContentType() != null) {
					if (idcodefile.getFileName().indexOf("xls") >= 0
							|| idcodefile.getFileName().indexOf("xlsx") >= 0) {
						bool = true;
					}
				}
			}
			if (bool) {
				// String fileName = idcodefile.getFileName();
				// String extName = fileName.substring(fileName.indexOf("."),
				// fileName.length());
				//
				// String sDateTime = DateUtil.getCurrentDateTimeString();
				// String firstName = fileName.substring(0,
				// fileName.indexOf("."));
				// String saveFileName = firstName + "_" + sDateTime + extName;
				// 读取Excel文件
				Workbook wb = Workbook.getWorkbook(idcodefile.getInputStream());
				// 获取第一个空间
				Sheet sheet = wb.getSheet(0);
				// 把参数传入方法内，该方法获取订单内容，和订单详情
				String typeId = sheet.getCell(1, 1).getContents();
				// 获取isPostNo和productStartNo
				Integer[] args = argMap.get(typeId);
				// 类型不存在
				if (args==null||args.length==0) {
					request.setAttribute("result", "订单类型:"+typeId+"不存在,请检查后重试!");
					return new ActionForward("/sys/lockrecord2.jsp");
				}

				String result = getSiAndSiDetail(sheet, args);
				if(!StringUtil.isEmpty(result)){
					request.setAttribute("result", result);
					return new ActionForward("/sys/lockrecord2.jsp");
				}

			}
			request.setAttribute("result", "databases.input.success");

		} catch (Exception e) {
			logger.error("", e);
			request.setAttribute("result", "databases.input.fail");
		}
		return mapping.findForward("success");
	}

	/**
	 * 获取表格内容，插入数据库
	 * 
	 * @param sheet
	 * @param args
	 * @return null 则没错误
	 * @throws Exception
	 */
	public String getSiAndSiDetail(Sheet sheet, Integer[] args) throws Exception {
		// 获取行数
		int row = sheet.getRows();
		// 先判断是否存在该单号
		// 如果存在则更新,如果不存在则新增
		String siid = sheet.getCell(1, siidNo).getContents();
		if(StringUtil.isEmpty(siid)){
			return "单据编号为空！";
		}
		SuggestInspect si = asi.getSiBySiid(siid);
		List<SuggestInspectDetail> sidlist = new ArrayList<SuggestInspectDetail>();
		//先获取详情并判断是否存在，暂不入库
		for (int i = args[1]; i < row; i++) {
			SuggestInspectDetail sid = new SuggestInspectDetail();
			String No = sheet.getCell(0, i).getContents();
			if (StringUtil.isEmpty(No)) {
				break;
			}
			sid.setSiid(siid);
			sid.setSeqNumber(Integer.valueOf(sheet.getCell(0, i).getContents()));
			sid.setProductId(sheet.getCell(1, i).getContents());
			Product p = ap.getProductByID(sid.getProductId());
			if(p==null){
				return "商品编号："+sid.getProductId()+"不存在！";
			}
			sid.setProductName(sheet.getCell(2, i).getContents());
			sid.setProductCode(sheet.getCell(3, i).getContents());
			sid.setUnit(sheet.getCell(4, i).getContents());
			sid.setQuantity(Integer.valueOf(sheet.getCell(5, i).getContents()));
			sid.setIsOut(0);
			//是否促销品
			if(args.length < 3){
				sid.setIsGift(1);
			}else{
				sid.setIsGift(0);
			}
			sidlist.add(sid);
			
		}
		//是新增还是更新
		boolean isMerge = false;
		if (si == null) {
			// 新增
			si = new SuggestInspect();
			si.setSiid(siid);
			String makeName = sheet.getCell(1, makeNameNo).getContents();
			si.setMakeName(makeName);
			String makeDate = sheet.getCell(1, makeDateNo).getContents();
			if(makeDate.length()==10){
				makeDate = makeDate +" 00:00:00";
			}
			Date date = null;
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				date = sdf.parse(makeDate);
			} catch (Exception e) {
				return "日期格式不正确！<br/>格式应为:2014-04-12 12:00:00  或者：2014-04-12";
			}
			si.setMakeDate(date);
			String typeName = sheet.getCell(1, typeNameNo).getContents();
			if(StringUtil.isEmpty(typeName)){
				return "样式名称为空！";
			}
			si.setTypeName(typeName);
			si.setTypeId(sheet.getCell(1, typeIdNo).getContents());
			String disWarehouse = sheet.getCell(1, disWarehouseNo).getContents();
			if(StringUtil.isEmpty(disWarehouse)){
				return "往来单位为空！";
			}
			Warehouse w = aw.getWhBySiName(disWarehouse);
			if(w==null){
				return "往来单位:"+disWarehouse+"不存在,或机构名称有误!";
			}
			si.setDisWareHouseName(disWarehouse);
			String souWarehouse = sheet.getCell(1, souWarehouseNo).getContents();
			if(StringUtil.isEmpty(souWarehouse)){
				return "仓库名称为空！";
			}
			si.setSouWareHouseName(souWarehouse);
			si.setIsPost(sheet.getCell(1, args[0]).getContents());
			si.setIsMerge(0);
			si.setIsRemove(0);
			si.setIsOut(0);
			si.setCreateDate(DateUtil.getCurrentDate());
			// 赠送单无客户编号
			if (args.length >= 3) {
				si.setCustomerCode(sheet.getCell(1, args[2]).getContents());
			}
			//如果是新增，则保存
			asi.addSuggestInspect(si);
		} else {
			
			isMerge = true;
			// 更新
			// 若此单已出库，则不做任何操作
			if (si.getIsOut() == 1) {
				return null;
			}
			si.setSiid(siid);
			si.setMakeName(sheet.getCell(1, makeNameNo).getContents());
			String makeDate = sheet.getCell(1, makeDateNo).getContents();
			if(makeDate.length()==10){
				makeDate = makeDate +" 00:00:00";
			}
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				sdf.parse(makeDate);
			} catch (Exception e) {
				return "日期格式不正确！<br/>格式应为:2014-04-12 12:00:00  或者：2014-04-12";
			}
			si.setMakeDate(DateUtil.StringToDate(makeDate, "yyyy-MM-dd HH:mm:ss"));
			String typeName = sheet.getCell(1, typeNameNo).getContents();
			if(StringUtil.isEmpty(typeName)){
				return "样式名称为空！";
			}
			si.setTypeName(typeName);
			si.setTypeId(sheet.getCell(1, typeIdNo).getContents());
			String disWarehouse = sheet.getCell(1, disWarehouseNo).getContents();
			if(StringUtil.isEmpty(disWarehouse)){
				return "往来单位为空!";
			}
			Warehouse w = aw.getWhBySiName(disWarehouse);
			if(w==null){
				return "往来单位:"+disWarehouse+"不存在,或单位名称有误!";
			}
			
			si.setDisWareHouseName(disWarehouse);
			String souWarehouse = sheet.getCell(1, souWarehouseNo).getContents();
			if(StringUtil.isEmpty(souWarehouse)){
				return "仓库名称为空!";
			}
			si.setSouWareHouseName(souWarehouse);
			si.setIsPost(sheet.getCell(1, args[0]).getContents());
			si.setCreateDate(DateUtil.getCurrentDate());
			// 赠送单无客户编号
			if (args.length >= 3) {
				si.setCustomerCode(sheet.getCell(1, args[2]).getContents());
			}
			//如果是已导入的订购单，则更新
			asi.updateSuggestInspect(si);
			// 更新订单后删除订单详情，根据对应的siid删除
			asid.deleteBySiid(si.getSiid());
			
		}
		/* *****此时订单均已更新或者新增,此单暂无详情***** */
		/* *************新增此单详情************** */
		//确保所有产品均存在，则入库
		for (SuggestInspectDetail suggestInspectDetail : sidlist) {
			// 保存
			asid.addSuggestInspectDetail(suggestInspectDetail);
		}
		/* ******如果此单据存在，且已合并，在更新合并后单据的详情******* */
		if (isMerge) {
			if (si.getMergeId() != null && si.getMergeId() != 0) {
				String whereSql = " where id = " + si.getMergeId();
				SuggestInspect sidetail = (SuggestInspect) asi.getSiByIds(
						whereSql).get(0);
				asid.deleteBySiid(sidetail.getSiid());
				asid.addMergeDetailBySiid(sidetail.getId(), sidetail.getSiid());
			}
		}
		return null;

	}
	
//	
//	public static boolean checkDateByYYYYMMDD(String date){
//		String el="^\\d{4}(\\-|V|\\.)\\d{1,2}\\1\\d{1,2}$";
//		Pattern p = Pattern.compile(el);
//		Matcher m = p.matcher(date);
//		return m.matches();
//	}
	

}
