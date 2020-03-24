package com.winsafe.drp.action.users;

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

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOlinkMan;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.Olinkman;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.keyretailer.dao.AppSTransferRelation;
import com.winsafe.drp.server.CountryAreaService;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ESAPIUtil;
import com.winsafe.drp.util.NumberUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

/**
 * @author : jerry
 */
public class ExcPutSTranferRelationAction extends BaseAction {
	private AppSTransferRelation appst = new AppSTransferRelation();
	private AppOlinkMan al = new AppOlinkMan(); 
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 所需dao类
		OrganService organs = new OrganService();
		AppOrgan app = new AppOrgan();
		String oid = request.getParameter("OID");
		// 初始化
		initdata(request);
		try {
			String condition = " p.organizationId='" + oid + "' ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "STransferRelation" };
			// 查询条件
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			// 关键字查询条件
			String blur = DbUtil.getOrBlur(map, tmpMap, "organizationId", "oppOrganId");
			whereSql = whereSql + blur + condition;
			whereSql = DbUtil.getWhereSql(whereSql);
			List list = appst.getSTransferRelation(whereSql);
			if (list.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=stransferorgan.xls");
			response.setContentType("application/msexcel");
			// 写入excel
			writeXls(list, oid,os, request);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/*
	 * 将数据写入excel中
	 */
	public void writeXls(List list, String oid,OutputStream os,
			HttpServletRequest request) throws Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		OrganService organs = new OrganService();
		CountryAreaService appCA = new CountryAreaService();
		UsersService uses = new UsersService();
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

			sheets[j].mergeCells(0, start,24, start);
			Organ norgan = organs.getOrganByID(oid);
			String sonorganid = norgan==null?"":norgan.getId();
			String sonorganname = norgan==null?"":norgan.getOrganname();
			sheets[j].addCell(new Label(0, start,sonorganid+"——"+ESAPIUtil.decodeForHTML(sonorganname)+ "进货机构资料", wchT));
			m = 0;
			sheets[j].addCell(new Label(m++, start + 1, "导出机构:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, ESAPIUtil.decodeForHTML(request.getAttribute("porganname").toString())));
			sheets[j].addCell(new Label(m++, start + 1, "导出人:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, request.getAttribute(
					"pusername").toString()));
			sheets[j].addCell(new Label(m++, start + 1, "导出时间:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, DateUtil
					.getCurrentDateTime()));

			m = 0;
			sheets[j].addCell(new Label(m++, start + 2, "编码", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "机构名称", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "企业内部代码", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "上级机构编号", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "上级机构名称", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "级别", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "省", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "省名称", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "市", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "市名称", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "区", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "区名称", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "地址", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "是否撤消", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "撤消人", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "撤消时间", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "联系人", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "电话", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "Email", wcfFC));

			for (int i = start; i < currentnum; i++) {
				int row = i - start + 3;
				Object[] obj = (Object[]) list.get(i);
				Organ p = (Organ) obj[1];
				m = 0;
				sheets[j].addCell(new Label(m++, row, p.getId()));
				sheets[j].addCell(new Label(m++, row, ESAPIUtil.decodeForHTML(p.getOrganname())));
				sheets[j].addCell(new Label(m++, row, p.getOecode()));
				sheets[j].addCell(new Label(m++, row, p.getParentid()));
				sheets[j].addCell(new Label(m++, row, organs.getOrganName(p
						.getParentid())));
				sheets[j].addCell(new Label(m++, row, p.getRank().toString()));
				sheets[j].addCell(new Label(m++, row, p.getProvince()
						.toString()));
				sheets[j].addCell(new Label(m++, row, appCA
						.getCountryAreaName(p.getProvince())));
				sheets[j].addCell(new Label(m++, row, p.getCity().toString()));
				sheets[j].addCell(new Label(m++, row, appCA
						.getCountryAreaName(p.getCity())));
				sheets[j].addCell(new Label(m++, row, p.getAreas().toString()));
				sheets[j].addCell(new Label(m++, row, appCA
						.getCountryAreaName(p.getAreas())));
				sheets[j].addCell(new Label(m++, row, p.getOaddr()));
				sheets[j].addCell(new Label(m++, row, HtmlSelect
						.getNameByOrder(request, "YesOrNo", p.getIsrepeal())));
				sheets[j].addCell(new Label(m++, row, uses
						.getUsersName(NumberUtil.removeNull(p.getRepealid()))));
				sheets[j].addCell(new Label(m++, row, DateUtil.formatDate(p
						.getRepealdate())));
				Olinkman oman = al.getMainLinkmanByCid(p.getId());
				sheets[j].addCell(new Label(m++, row, oman!=null?oman.getName():""));
				sheets[j].addCell(new Label(m++, row, oman!=null?oman.getMobile():""));
				sheets[j].addCell(new Label(m++, row, oman!=null?oman.getEmail():""));
			}
		}
		workbook.write();
		workbook.close();
		os.flush();
		os.close();
	}
}
