package com.winsafe.drp.action.purchase;

import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProvider;
import com.winsafe.drp.dao.Provider;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.CountryAreaService;
import com.winsafe.drp.server.DeptService;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

/**
 * @author : jerry
 * @version : 2009-8-22 下午02:31:07 www.winsafe.cn
 */
public class ExcPutProviderAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			super.initdata(request);
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = this.getOrVisitOrgan("p.makeorganid");
			}

			String Condition = " (p.makeid=" + userid + " " + visitorgan
					+ ") and p.isdel=0";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Provider" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename); 
			String blur = DbUtil.getOrBlur(map, tmpMap, "ID","PName", "Tel",
					"Mobile"); 
			whereSql = whereSql + blur + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			AppProvider app = new AppProvider();
			List<Provider> list = app.finAll(whereSql);
			
			if (list.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=provider.xls");
			response.setContentType("application/msexcel");
			writeXls(list, os, request);
			os.flush();
			os.close();
			DBUserLog.addUserLog(userid, 2,"采购管理>>导出供应商");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	public void writeXls(List<Provider> list, OutputStream os,
			HttpServletRequest request) throws Exception,
			RowsExceededException, WriteException {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		OrganService organs = new OrganService();
		CountryAreaService appCA = new CountryAreaService();
		DeptService ds = new DeptService();
		UsersService uses = new UsersService();
		int snum = 1;
		snum = list.size() / 50000;
		if (list.size() % 50000 >= 0) {
			snum++;
		}
		WritableSheet[] sheets = new WritableSheet[snum];
		for (int j = 0; j < snum; j++) {
			sheets[j] = workbook.createSheet("sheet" + j, j);
			sheets[j].setRowView(0, false);
			sheets[j].getSettings().setDefaultColumnWidth(20);
			
			int currentnum = (j + 1) * 50000;
			if (currentnum >= list.size()) {
				currentnum = list.size();
			}
			int start = j * 50000;
			sheets[j].mergeCells(0, start, 35, start);
			sheets[j].addCell(new Label(0, start, "供应商资料",wchT));
			sheets[j].addCell(new Label(0, start+1, "制单机构:",seachT));
			sheets[j].addCell(new Label(1, start+1, request.getParameter("oname")));
			sheets[j].addCell(new Label(2, start+1, "制单部门:", seachT));
			sheets[j].addCell(new Label(3, start+1, request.getParameter("deptname")));
			sheets[j].addCell(new Label(4, start+1, "制单人:", seachT));
			sheets[j].addCell(new Label(5, start+1, request.getParameter("uname")));
			
			sheets[j].addCell(new Label(0, start+2, "供应商类型:", seachT));
			sheets[j].addCell(new Label(1, start+2, HtmlSelect.getResourceName(request, "Genre", getInt("Genre"))));	
			sheets[j].addCell(new Label(2, start+2, "ABC分类:", seachT));
			sheets[j].addCell(new Label(3, start+2, HtmlSelect.getNameByOrder(request, "AbcSort", getInt("AbcSort"))));		
			sheets[j].addCell(new Label(4, start+2, "关键字:", seachT));
			sheets[j].addCell(new Label(5, start+2, request.getParameter("KeyWord")));		
			
			sheets[j].addCell(new Label(0, start+3, "导出机构:", seachT));
			sheets[j].addCell(new Label(1, start+3, request.getAttribute("porganname").toString()));
			sheets[j].addCell(new Label(2, start+3, "导出人:", seachT));
			sheets[j].addCell(new Label(3, start+3, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+3, "导出时间:", seachT));
			sheets[j].addCell(new Label(5, start+3, DateUtil.getCurrentDateTime()));

			sheets[j].addCell(new Label(0, start+4, "供应商编号", wcfFC));
			sheets[j].addCell(new Label(1, start+4, "供应商名", wcfFC));
			sheets[j].addCell(new Label(2, start+4, "供应商行业编号", wcfFC));
			sheets[j].addCell(new Label(3, start+4, "供应商行业名称", wcfFC));
			sheets[j].addCell(new Label(4, start+4, "税号", wcfFC));
			sheets[j].addCell(new Label(5, start+4, "法人代表", wcfFC));
			sheets[j].addCell(new Label(6, start+4, "银行帐号", wcfFC));
			sheets[j].addCell(new Label(7, start+4, "开户银行", wcfFC));
			sheets[j].addCell(new Label(8, start+4, "供应商类型编号", wcfFC));
			sheets[j].addCell(new Label(9, start+4, "供应商类型名称", wcfFC));
			sheets[j].addCell(new Label(10, start+4, "电话", wcfFC));

			sheets[j].addCell(new Label(11, start+4, "传真", wcfFC));			
			sheets[j].addCell(new Label(12, start+4, "手机", wcfFC));
			sheets[j].addCell(new Label(13, start+4, "邮箱", wcfFC));
			sheets[j].addCell(new Label(14, start+4, "邮编", wcfFC));
			sheets[j].addCell(new Label(15, start+4, "地址", wcfFC));
			sheets[j].addCell(new Label(16, start+4, "ABC分类", wcfFC));
			sheets[j].addCell(new Label(17, start+4, "帐期", wcfFC));
			sheets[j].addCell(new Label(18, start+4, "税率", wcfFC));
			sheets[j].addCell(new Label(19, start+4, "付款条件", wcfFC));
			sheets[j].addCell(new Label(20, start+4, "公司主页", wcfFC));

			sheets[j].addCell(new Label(21, start+4, "省", wcfFC));
			sheets[j].addCell(new Label(22, start+4, "省名称", wcfFC));
			sheets[j].addCell(new Label(23, start+4, "市", wcfFC));
			sheets[j].addCell(new Label(24, start+4, "市名称", wcfFC));
			sheets[j].addCell(new Label(25, start+4, "区", wcfFC));
			sheets[j].addCell(new Label(26, start+4, "区名称", wcfFC));
			sheets[j].addCell(new Label(27, start+4, "制单机构", wcfFC));
			sheets[j].addCell(new Label(28, start+4, "制单部门", wcfFC));
			sheets[j].addCell(new Label(29, start+4, "登记人", wcfFC));
			sheets[j].addCell(new Label(30, start+4, "登记日期", wcfFC));

			sheets[j].addCell(new Label(31, start+4, "备注", wcfFC));
			sheets[j].addCell(new Label(32, start+4, "是否激活", wcfFC));
			sheets[j].addCell(new Label(33, start+4, "激活人", wcfFC));
			sheets[j].addCell(new Label(34, start+4, "激活日期", wcfFC));
			sheets[j].addCell(new Label(35, start+4, "是否删除", wcfFC));
			for (int i = start; i < currentnum; i++) {
				int row = i - start + 5;
				Provider p = list.get(i);

				sheets[j].addCell(new Label(0, row, p.getPid()));
				sheets[j].addCell(new Label(1, row, p.getPname()));
				sheets[j].addCell(new Label(2, row, p.getVocation().toString()));
				sheets[j].addCell(new Label(3, row, HtmlSelect.getResourceName(request, "Vocation", p.getVocation())));
				sheets[j].addCell(new Label(4, row, p.getTaxcode()));
				sheets[j].addCell(new Label(5, row, p.getCorporation()));
				sheets[j].addCell(new Label(6, row, p.getBankaccount()));
				sheets[j].addCell(new Label(7, row, p.getBankname()));
				sheets[j].addCell(new Label(8, row, p.getGenre().toString()));
				sheets[j].addCell(new Label(9, row, HtmlSelect.getResourceName(request, "Vocation", p.getGenre())));
				sheets[j].addCell(new Label(10, row, p.getTel()));
				
				sheets[j].addCell(new Label(11, row, p.getFax()));
				sheets[j].addCell(new Label(12, row, p.getMobile()));
				sheets[j].addCell(new Label(13, row, p.getEmail()));
				sheets[j].addCell(new Label(14, row, p.getPostcode()));
				sheets[j].addCell(new Label(15, row, p.getAddr()));
				sheets[j].addCell(new Label(16, row, HtmlSelect.getNameByOrder(request, "AbcSort", p.getAbcsort())));
				sheets[j].addCell(new Label(17, row, p.getPrompt().toString()));
				sheets[j].addCell(new Label(18, row, p.getTaxrate().toString()));
				sheets[j].addCell(new Label(19, row, p.getPaycondition()));
				sheets[j].addCell(new Label(20, row, p.getHomepage()));
				
				sheets[j].addCell(new Label(21, row, p.getProvince().toString()));
				sheets[j].addCell(new Label(22, row, appCA.getCountryAreaName(p.getProvince())));
				sheets[j].addCell(new Label(23, row, p.getCity().toString()));
				sheets[j].addCell(new Label(24, row, appCA.getCountryAreaName(p.getCity())));
				sheets[j].addCell(new Label(25, row, p.getAreas().toString()));
				sheets[j].addCell(new Label(26, row, appCA.getCountryAreaName(p.getAreas())));
				sheets[j].addCell(new Label(27, row, organs.getOrganName(p.getMakeorganid())));
				sheets[j].addCell(new Label(28, row, ds.getDeptName(p.getMakedeptid())));
				sheets[j].addCell(new Label(29, row, uses.getUsersName(p.getMakeid())));
				sheets[j].addCell(new Label(30, row, DateUtil.formatDate(p.getMakedate())));
				
				sheets[j].addCell(new Label(31, row, p.getRemark()));
				sheets[j].addCell(new Label(32, row, HtmlSelect.getNameByOrder(request, "YesOrNo", p.getIsactivate())));
				sheets[j].addCell(new Label(33, row, uses.getUsersName(p.getActivateid())));
				sheets[j].addCell(new Label(34, row, DateUtil.formatDate(p.getActivatedate())));
				sheets[j].addCell(new Label(35, row, HtmlSelect.getNameByOrder(request, "YesOrNo", p.getIsdel())));

			}
		}
		workbook.write();
		workbook.close();
		os.close();
	}

	public DateTime setDateTime(int cell, int row, Date datetime) {
		jxl.write.DateFormat df = new jxl.write.DateFormat(
				"yyyy-MM-dd hh:mm:ss");
		jxl.write.WritableCellFormat wcfDF = new jxl.write.WritableCellFormat(
				df);
		return new DateTime(cell, row, datetime, wcfDF);
	}

}
