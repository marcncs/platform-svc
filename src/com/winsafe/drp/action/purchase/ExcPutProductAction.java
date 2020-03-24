package com.winsafe.drp.action.purchase;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStruct;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.ESAPIUtil;
import com.winsafe.drp.util.NumberUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

/**
 */
public class ExcPutProductAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// dao层所需类
		AppProduct app = new AppProduct();
		// 初始化
		initdata(request);
		try {
			// psid产品类别
			String pid = request.getParameter("OtherKey");
			// 获取查询条件
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Product" };
			// 查询条件
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			StringBuffer buf = new StringBuffer();
			if (pid != null && pid.length() > 0) {
				buf.append("(");
				buf.append("psid like '" + pid + "%')");
				whereSql = whereSql + buf;
			}
			// 关键字查询条件
			String blur = DbUtil.getOrBlur(map, tmpMap, "ID", "ProductName",
					"SpecMode", "PYCode", "NCcode");
			whereSql = whereSql + blur;
			whereSql = DbUtil.getWhereSql(whereSql);
			// 获取产品列表
			List<Product> list = app.findByWhereSql(whereSql);
			DBUserLog.addUserLog(request,"列表");
			if (list.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=Product.xls");
			response.setContentType("application/msexcel");
			//写入excel中
			writeXls(list, os, request);
			os.flush();
			os.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}
	/**
	 * 生成excel
	 * @param list
	 * @param os
	 * @param request
	 * @throws Exception
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	public void writeXls(List<Product> list, OutputStream os,
			HttpServletRequest request) throws Exception,
			RowsExceededException, WriteException {
		//DAO层所需类
		AppProductStruct appps = new AppProductStruct();
		OrganService ogs = new OrganService();
		UsersService us = new UsersService();
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		
		int snum = 1;
		int m = 0;
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

			sheets[j].mergeCells(0, start, 24, start);
			sheets[j].addCell(new Label(0, start, "产品资料", wchT));
			sheets[j].addCell(new Label(m++, start + 1, "是否可用:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, HtmlSelect.getNameByOrder(request, "YesOrNo", getInt("UseFlag"))));
			sheets[j].addCell(new Label(m++, start + 1, "关键字:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, request.getParameter("KeyWord")));
			sheets[j].addCell(new Label(m++, start + 1, "导出机构:", seachT)); 
			sheets[j].addCell(new Label(m++, start + 1, ESAPIUtil.decodeForHTML(request.getAttribute("porganname").toString())));
			sheets[j].addCell(new Label(m++, start + 1, "导出人:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(m++, start + 1, "导出时间:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, DateUtil.getCurrentDateTime()));

			//表头
			m = 0;
			sheets[j].addCell(new Label(m++, start + 2, "产品类别编号", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "产品类别名称", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "产品编号", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "产品名", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "拼音简码", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "产品英文名", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "规格明细", wcfFC));
			//物料号
			sheets[j].addCell(new Label(m++, start + 2, "物料号", wcfFC));
		    //物料中文描述
		    sheets[j].addCell(new Label(m++, start + 2, "物料中文描述", wcfFC));
		    //物料英文描述
		    sheets[j].addCell(new Label(m++, start + 2, "物料英文描述", wcfFC));
		    //包装大小
		    sheets[j].addCell(new Label(m++, start + 2, "包装规格", wcfFC));
		    //包装大小英文
		    sheets[j].addCell(new Label(m++, start + 2, "包装规格英文", wcfFC));
		    //保质期
		    sheets[j].addCell(new Label(m++, start + 2, "保质期(天)", wcfFC));
		    //箱码是否打印
		    sheets[j].addCell(new Label(m++, start + 2, "箱码打印", wcfFC));
		    //小包装是否打印
		    sheets[j].addCell(new Label(m++, start + 2, "小包装打印", wcfFC));
		    //箱码是否扫描
		    sheets[j].addCell(new Label(m++, start + 2, "箱码扫描", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "是否可用", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "最小计量单位编号", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "最小计量单位名称", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "备注", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "制单机构编号", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "制单机构名称", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "制单人编号", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "制单人名称", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "制单日期", wcfFC));
			
			sheets[j].addCell(new Label(m++, start + 2, "农药登记名称", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "登记类别", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "登记证号", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "登记证号后6位", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "登记证持有人名称", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "产品规格码", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "生产类型", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "内部生产类型", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "编码规则", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "前后关联", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "产品类型", wcfFC));
			
			//内容
			for (int i = start; i < currentnum; i++) {
				int row = i - start + 3;
				Product p = list.get(i);

				m = 0;
				sheets[j].addCell(new Label(m++, row, p.getPsid()));
				sheets[j].addCell(new Label(m++, row, appps.getName(p.getPsid())));
				sheets[j].addCell(new Label(m++, row, p.getId()));
				sheets[j].addCell(new Label(m++, row, p.getProductname()));
				sheets[j].addCell(new Label(m++, row, p.getPycode()));
				sheets[j].addCell(new Label(m++, row, p.getProductnameen()));
				//规格值设置成包装大小的值
//				sheets[j].addCell(new Label(m++, row, p.getSpecmode()));
				sheets[j].addCell(new Label(m++, row, p.getPackSizeName()));
				
				sheets[j].addCell(new Label(m++, row, p.getmCode()));
				sheets[j].addCell(new Label(m++, row, p.getMatericalChDes()));
				sheets[j].addCell(new Label(m++, row, p.getMatericalEnDes()));
				sheets[j].addCell(new Label(m++, row, p.getSpecmode()));
				sheets[j].addCell(new Label(m++, row, p.getPackSizeNameEn()));
				sheets[j].addCell(new Label(m++, row, p.getExpiryDays()+""));
				sheets[j].addCell(new Label(m++, row, HtmlSelect.getNameByOrder(request, "YesOrNo", NumberUtil.removeNull(p.getCartonPrintFlag()))));
				sheets[j].addCell(new Label(m++, row, HtmlSelect.getNameByOrder(request, "YesOrNo", NumberUtil.removeNull(p.getPrimaryPrintFlag()))));
				sheets[j].addCell(new Label(m++, row, HtmlSelect.getNameByOrder(request, "YesOrNo", NumberUtil.removeNull(p.getCartonScanning()))));
				
				sheets[j].addCell(new Label(m++, row, HtmlSelect.getNameByOrder(request, "YesOrNo", NumberUtil.removeNull(p.getUseflag()))));
				sheets[j].addCell(new Label(m++, row, NumberUtil.removeNull(p.getCountunit())+""));
				sheets[j].addCell(new Label(m++, row, HtmlSelect.getResourceName(request, "CountUnit", NumberUtil.removeNull(p.getCountunit()))));
				sheets[j].addCell(new Label(m++, row, p.getMemo()));
				sheets[j].addCell(new Label(m++, row, p.getMakeorganid()));
				sheets[j].addCell(new Label(m++, row, ogs.getOrganName(p.getMakeorganid())));
				sheets[j].addCell(new Label(m++, row, NumberUtil.removeNull(p.getMakeid())+""));
				sheets[j].addCell(new Label(m++, row, us.getUsersName(p.getMakeid())));
				sheets[j].addCell(new Label(m++, row, DateUtil.formatDate(p.getMakedate())));
				
				sheets[j].addCell(new Label(m++, row, p.getStandardName()));
				if(p.getRegCertType() != null) {
					sheets[j].addCell(new Label(m++, row, HtmlSelect.getNameByOrder(request, "RegCertType", p.getRegCertType())));
				} else {
					sheets[j].addCell(new Label(m++, row, ""));
				}
				
				sheets[j].addCell(new Label(m++, row, p.getRegCertCode()));
				sheets[j].addCell(new Label(m++, row, p.getRegCertCodeFixed()));
				sheets[j].addCell(new Label(m++, row, p.getRegCertUser()));
				sheets[j].addCell(new Label(m++, row, p.getSpecCode()));
				if(p.getProduceType() != null) {
					sheets[j].addCell(new Label(m++, row, HtmlSelect.getNameByOrder(request, "ProduceType", p.getProduceType())));
				} else {
					sheets[j].addCell(new Label(m++, row, ""));
				}
				if(p.getInnerProduceType() != null) {
					sheets[j].addCell(new Label(m++, row, HtmlSelect.getNameByOrder(request, "InnerProduceType", p.getInnerProduceType())));
				} else {
					sheets[j].addCell(new Label(m++, row, ""));
				}
				
				if(p.getCodeType() != null) {
					sheets[j].addCell(new Label(m++, row, HtmlSelect.getNameByOrder(request, "CodeType", p.getCodeType())));
				} else {
					sheets[j].addCell(new Label(m++, row, ""));
				}
				
				if(p.getLinkMode() != null) {
					sheets[j].addCell(new Label(m++, row, HtmlSelect.getNameByOrder(request, "LinkMode", p.getLinkMode())));
				} else {
					sheets[j].addCell(new Label(m++, row, ""));
				}
				if(p.getProductType() != null) {
					sheets[j].addCell(new Label(m++, row, HtmlSelect.getNameByOrder(request, "ProductType", p.getProductType())));
				} else {
					sheets[j].addCell(new Label(m++, row, ""));
				}
				
			}
		}
		workbook.write();
		workbook.close();
		os.close();
	}
}
