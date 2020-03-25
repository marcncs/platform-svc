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
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.server.CountryAreaService;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.HtmlSelect;

/**
 * @author : jerry
 * @version : 2009-8-22 下午02:31:07 www.winsafe.cn
 */
public class ExcPutFrontOrganAction extends BaseAction {

	@SuppressWarnings("unchecked")
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {

			initdata(request);
			OrganService organs = new OrganService();
			String Condition = " where o1.sysid like '"
				+ organs.getOrganByID(users.getMakeorganid()).getSysid()
				+ "%' ";
			Map map = new HashMap(request.getParameterMap());
			//Map tmpMap = EntityManager.scatterMap(map);
			//条件判断
			String whereSql = "";
			if(map.get("IsRepeal")!=null && !"".equals(map.get("IsRepeal"))){
				whereSql += " and o1.isrepeal='" + map.get("IsRepeal") + "'";
			}
			if(map.get("Areas")!=null && !"".equals(map.get("Areas"))){
				whereSql += " and o1.areas='" + map.get("Areas") + "'";
			}
			if(map.get("Province")!=null && !"".equals(map.get("Province"))){
				whereSql += " and o1.province='" + map.get("Province") + "'";
			}
			if(map.get("City")!=null && !"".equals(map.get("City"))){
				whereSql += " and o1.city='" + map.get("City") + "'";
			}
			if(map.get("KeyWord")!=null && !"".equals(map.get("KeyWord"))){
				whereSql += " and ( o1.id like '%" + map.get("KeyWord") + "%' or o1.organname like '%" + map.get("KeyWord")
				+ "%' or o1.omobile like '%" + map.get("KeyWord") + "%' or o1.oecode like '%" + map.get("KeyWord") + "%'  )";
			}
			
			whereSql = Condition + whereSql;
			/*
			String[] tablename = { "Organ" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String blur = DbUtil.getOrBlur(map, tmpMap, "ID", "OrganName",
					"OMobile", "OECode");
			whereSql = whereSql + blur + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);
			 */
			AppOrgan app = new AppOrgan();
			List list = app.getFontOrganByWhere(whereSql);
			
			if (list.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=FontOrgan.xls");
			response.setContentType("application/msexcel");
			writeXls(list, os, request);
			os.flush();
			os.close();
			DBUserLog.addUserLog(userid, 11, "导出网点信息资料!");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public void writeXls(List list, OutputStream os,
			HttpServletRequest request) throws Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		CountryAreaService appCA = new CountryAreaService();
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
			
			sheets[j].mergeCells(0, start, 10, start);
			sheets[j].addCell(new Label(0, start, "网点信息资料",wchT));
			
			sheets[j].addCell(new Label(0, start+1, "省份:",seachT));
			sheets[j].addCell(new Label(1, start+1, appCA.getCountryAreaName(getInt("Province"))));
			sheets[j].addCell(new Label(2, start+1, "城市:", seachT));
			sheets[j].addCell(new Label(3, start+1, appCA.getCountryAreaName(getInt("City"))));
			sheets[j].addCell(new Label(4, start+1, "地区:", seachT));
			sheets[j].addCell(new Label(5, start+1, appCA.getCountryAreaName(getInt("Areas"))));
			sheets[j].addCell(new Label(6, start+1, "是否撤消:", seachT));
			sheets[j].addCell(new Label(7, start+1, HtmlSelect.getNameByOrder(request, "YesOrNo", getInt("IsRepeal"))));	
			sheets[j].addCell(new Label(8, start+1, "关键字:", seachT));
			sheets[j].addCell(new Label(9, start+1, request.getParameter("KeyWord")));	
			
			sheets[j].addCell(new Label(0, start+2, "导出机构:", seachT));
			sheets[j].addCell(new Label(1, start+2, request.getAttribute("porganname").toString()));
			sheets[j].addCell(new Label(2, start+2, "导出人:", seachT));
			sheets[j].addCell(new Label(3, start+2, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+2, "导出时间:", seachT));
			sheets[j].addCell(new Label(5, start+2, DateUtil.getCurrentDateTime()));
			
			
			sheets[j].addCell(new Label(0, start+3, "大区",wcfFC));
			sheets[j].addCell(new Label(1, start+3, "办事处",wcfFC));
			sheets[j].addCell(new Label(2, start+3, "经销商编号",wcfFC));
			sheets[j].addCell(new Label(3, start+3, "经销商名称",wcfFC));
			sheets[j].addCell(new Label(4, start+3, "网点编号",wcfFC));
			sheets[j].addCell(new Label(5, start+3, "网点名称",wcfFC));
			sheets[j].addCell(new Label(6, start+3, "地址",wcfFC));
			sheets[j].addCell(new Label(7, start+3, "联系人",wcfFC));
			sheets[j].addCell(new Label(8, start+3, "联系电话",wcfFC));
			sheets[j].addCell(new Label(9, start+3, "主管编号",wcfFC));
			sheets[j].addCell(new Label(10, start+3, "主管名称",wcfFC));

			for (int i = start; i < currentnum; i++) {
				int row = i - start + 4;
				Map m = (Map) list.get(i);

				sheets[j].addCell(new Label(0, row, setNull(m.get("bigregionname"))));
				sheets[j].addCell(new Label(1, row, setNull(m.get("officename"))));
				sheets[j].addCell(new Label(2, row, setNull(m.get("dealeroecode"))));
				sheets[j].addCell(new Label(3, row, setNull(m.get("dealerorganname"))));
				sheets[j].addCell(new Label(4, row, setNull(m.get("oecode"))));
				sheets[j].addCell(new Label(5, row, setNull(m.get("organname"))));
				sheets[j].addCell(new Label(6, row, setNull(m.get("addr"))));
				sheets[j].addCell(new Label(7, row, setNull(m.get("olname"))));
				sheets[j].addCell(new Label(8, row, setNull(m.get("officetel"))));
				sheets[j].addCell(new Label(9, row, setNull(m.get("userlogin"))));
				sheets[j].addCell(new Label(10, row, setNull(m.get("username"))));				
			}
		}
		workbook.write();
		workbook.close();
		os.close();
	}

	public String setNull(Object obj) {
		return obj == null ? "" : obj.toString();
	}

}
