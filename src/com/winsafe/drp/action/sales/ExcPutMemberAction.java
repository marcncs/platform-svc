package com.winsafe.drp.action.sales;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppMemberGrade;
import com.winsafe.drp.dao.Customer;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.CountryAreaService;
import com.winsafe.drp.server.DeptService;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.util.Constants;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class ExcPutMemberAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		super.initdata(request);
		try {
			AppCustomer ac = new AppCustomer();
			
		

			String Condition = " (c.makeid="+userid+" "+getOrVisitOrgan("c.makeorganid")+") and c.isdel=0 ";
			String tel = request.getParameter("tel");
			if ( tel != null && "0".equals(tel) ){
				Condition = Condition + " and c.mobile!='' ";
			}
			if ( tel != null && "1".equals(tel) ){
				Condition = Condition + " and c.mobile='' ";
			}
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = {"Customer"};
			String whereSql = EntityManager.getTmpWhereSql(map, tablename); 

			String blur = DbUtil.getOrBlur(map, tmpMap, "CID","CName", "CPYCode","Mobile");
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,"MakeDate");
			whereSql = whereSql + blur + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			List usList = ac.searchCustomer2(whereSql);

			if (usList.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}

			String fname = "customer";
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition", "attachment; filename="
					+ fname + ".xls");
			response.setContentType("application/msexcel");
			writeXls(usList, os, request);
			os.flush();
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public void writeXls(List list, OutputStream os, HttpServletRequest request)
			throws Exception {
		WritableWorkbook workbook = jxl.Workbook.createWorkbook(os);
		OrganService app = new OrganService();
		DeptService ds = new DeptService();
		UsersService us = new UsersService();
		AppCustomer ac = new AppCustomer();
		CountryAreaService appCA = new CountryAreaService();
		AppMemberGrade amg = new AppMemberGrade();
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
			
			sheets[j].mergeCells(0, start, 46, start);
			sheets[j].addCell(new Label(0, start, "会员资料",wchT));
			
			sheets[j].addCell(new Label(0, start+1, "省份:",seachT));
			sheets[j].addCell(new Label(1, start+1, appCA.getCountryAreaName(getInt("Province"))));
			sheets[j].addCell(new Label(2, start+1, "城市:", seachT));
			sheets[j].addCell(new Label(3, start+1, appCA.getCountryAreaName(getInt("City"))));
			sheets[j].addCell(new Label(4, start+1, "地区:", seachT));
			sheets[j].addCell(new Label(5, start+1, appCA.getCountryAreaName(getInt("Areas"))));
			sheets[j].addCell(new Label(6, start+1, "是否激活:", seachT));
			sheets[j].addCell(new Label(7, start+1, HtmlSelect.getNameByOrder(request, "YesOrNo", getInt("IsActivate"))));	
			sheets[j].addCell(new Label(8, start+1, "客户来源:", seachT));
			
			String resour = "";
			if(getInt("Source") != -1){
				resour= HtmlSelect.getResourceName(request, "Source",getInt("Source") );
			}
			sheets[j].addCell(new Label(9, start+1, resour));		
			sheets[j].addCell(new Label(10, start+1, "性别:", seachT));
			sheets[j].addCell(new Label(11, start+1, HtmlSelect.getNameByOrder(request, "Sex", getInt("MemerSex"))));		
			
			sheets[j].addCell(new Label(0, start+2, "制单机构:", seachT));
			sheets[j].addCell(new Label(1, start+2, request.getParameter("oname")));
			sheets[j].addCell(new Label(2, start+2, "制单人:", seachT));
			sheets[j].addCell(new Label(3, start+2, request.getParameter("uname")));
			sheets[j].addCell(new Label(4, start+2, "手机是否空:", seachT));
			sheets[j].addCell(new Label(5, start+2, HtmlSelect.getNameByOrder(request, "YesOrNo", getInt("tel"))));
			sheets[j].addCell(new Label(6, start+2, "登记日期:", seachT));
			sheets[j].addCell(new Label(7, start+2, request.getParameter("BeginDate")+""+request.getParameter("EndDate")));
			sheets[j].addCell(new Label(8, start+2, "关键字:", seachT));
			sheets[j].addCell(new Label(9, start+2, request.getParameter("KeyWord")));
			sheets[j].addCell(new Label(10, start+2, "导出机构:", seachT));
			sheets[j].addCell(new Label(11, start+2, request.getAttribute("porganname").toString()));
			sheets[j].addCell(new Label(12, start+2, "导出人:", seachT));
			sheets[j].addCell(new Label(13, start+2, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(14, start+2, "导出时间:", seachT));
			sheets[j].addCell(new Label(15, start+2, DateUtil.getCurrentDateTime()));
			
			sheets[j].addCell(new Label(0, start+3, "会员编号",wcfFC));
			sheets[j].addCell(new Label(1, start+3, "会员名称",wcfFC));
			sheets[j].addCell(new Label(2, start+3, "拼音简码 ",wcfFC));
			sheets[j].addCell(new Label(3, start+3, "会员卡号",wcfFC));
			sheets[j].addCell(new Label(4, start+3, "推荐人编号",wcfFC));
			sheets[j].addCell(new Label(5, start+3, "推荐人名称",wcfFC));
			sheets[j].addCell(new Label(6, start+3, "性别",wcfFC));
			sheets[j].addCell(new Label(7, start+3, "出生日期",wcfFC));
			sheets[j].addCell(new Label(8, start+3, "证件号码",wcfFC));
			sheets[j].addCell(new Label(9, start+3, "单位",wcfFC));
			sheets[j].addCell(new Label(10, start+3, "职业",wcfFC));
			
			sheets[j].addCell(new Label(11, start+3, "发票抬头",wcfFC));
			sheets[j].addCell(new Label(12, start+3, "帐期",wcfFC));
			sheets[j].addCell(new Label(13, start+3, "信用额度",wcfFC));
			sheets[j].addCell(new Label(14, start+3, "会员级别编号",wcfFC));
			sheets[j].addCell(new Label(15, start+3, "会员级别名称",wcfFC));
			sheets[j].addCell(new Label(16, start+3, "折扣",wcfFC));
			sheets[j].addCell(new Label(17, start+3, "税率",wcfFC));
			sheets[j].addCell(new Label(18, start+3, "客户来源",wcfFC));
			sheets[j].addCell(new Label(19, start+3, "省",wcfFC));			
			sheets[j].addCell(new Label(20, start+3, "省名称",wcfFC));
			
			sheets[j].addCell(new Label(21, start+3, "市",wcfFC));
			sheets[j].addCell(new Label(22, start+3, "市名称",wcfFC));
			sheets[j].addCell(new Label(23, start+3, "区",wcfFC));
			sheets[j].addCell(new Label(24, start+3, "区名称",wcfFC));
			sheets[j].addCell(new Label(25, start+3, "通讯地址",wcfFC));
			sheets[j].addCell(new Label(26, start+3, "送货地址",wcfFC));
			sheets[j].addCell(new Label(27, start+3, "邮编",wcfFC));
			sheets[j].addCell(new Label(28, start+3, "送货邮编",wcfFC));
			sheets[j].addCell(new Label(29, start+3, "网址",wcfFC));			
			sheets[j].addCell(new Label(30, start+3, "办公电话",wcfFC));
			
			sheets[j].addCell(new Label(31, start+3, "手机",wcfFC));
			sheets[j].addCell(new Label(32, start+3, "传真",wcfFC));
			sheets[j].addCell(new Label(33, start+3, "邮箱",wcfFC));
			sheets[j].addCell(new Label(34, start+3, "税号",wcfFC));
			sheets[j].addCell(new Label(35, start+3, "制单机构",wcfFC));
			sheets[j].addCell(new Label(36, start+3, "制单部门",wcfFC));
			sheets[j].addCell(new Label(37, start+3, "录入人员",wcfFC));
			sheets[j].addCell(new Label(38, start+3, "登记日期",wcfFC));
			sheets[j].addCell(new Label(39, start+3, "最后联系日期",wcfFC));			
			sheets[j].addCell(new Label(40, start+3, "下次联系日期",wcfFC));
			
			sheets[j].addCell(new Label(41, start+3, "备注",wcfFC));
			sheets[j].addCell(new Label(42, start+3, "是否激活",wcfFC));
			sheets[j].addCell(new Label(43, start+3, "激活人",wcfFC));
			sheets[j].addCell(new Label(44, start+3, "激活日期",wcfFC));
			sheets[j].addCell(new Label(45, start+3, "是否接收短信",wcfFC));
			sheets[j].addCell(new Label(46, start+3, "是否删除",wcfFC));

			for (int i = start; i < currentnum; i++) {
				int row = i - start + 4;
				Customer c = (Customer) list.get(i);
				sheets[j].addCell(new Label(0, row, c.getCid()));
				sheets[j].addCell(new Label(1, row, c.getCname()));
				sheets[j].addCell(new Label(2, row, c.getCpycode()));
				sheets[j].addCell(new Label(3, row, c.getCardno()));
				sheets[j].addCell(new Label(4, row, c.getParentid()));
				sheets[j].addCell(new Label(5, row, ac.getCName(c.getParentid())));
				sheets[j].addCell(new Label(6, row, HtmlSelect.getNameByOrder(
						request, "Sex", c.getMembersex())));				
				sheets[j].addCell(new Label(7, row, DateUtil.formatDate(c
						.getMemberbirthday())));
				sheets[j].addCell(new Label(8, row, c.getMemberidcard()));
				sheets[j].addCell(new Label(9, row, c.getMembercompany()));
				sheets[j].addCell(new Label(10, row, c.getMemberduty()));
				
				sheets[j].addCell(new Label(11, row, c.getTickettitle()));
				sheets[j].addCell(new Label(12, row, c.getPrompt().toString()));
				sheets[j].addCell(new Label(13, row, c.getCreditlock().toString()));
				sheets[j].addCell(new Label(14, row, c.getRate().toString()));	
				sheets[j].addCell(new Label(15, row, amg.getMemberGradeName(c.getRate())));
				sheets[j].addCell(new Label(16, row, c.getDiscount().toString()));
				sheets[j].addCell(new Label(17, row, c.getTaxrate().toString()));
				sheets[j].addCell(new Label(18, row, HtmlSelect.getResourceName(request, "Source",c.getSource())));
				
				sheets[j].addCell(new Label(19, row, c.getProvince().toString()));
				sheets[j].addCell(new Label(20, row, appCA.getCountryAreaName(c.getProvince())));
				sheets[j].addCell(new Label(21, row, c.getCity().toString()));
				sheets[j].addCell(new Label(22, row, appCA.getCountryAreaName(c.getCity())));
				sheets[j].addCell(new Label(23, row, c.getAreas().toString()));
				sheets[j].addCell(new Label(24, row, appCA.getCountryAreaName(c.getAreas())));
				sheets[j].addCell(new Label(25, row, c.getCommaddr()));
				sheets[j].addCell(new Label(26, row, c.getDetailaddr()));
				sheets[j].addCell(new Label(27, row, c.getPostcode()));
				sheets[j].addCell(new Label(28, row, c.getSendpostcode()));
				sheets[j].addCell(new Label(29, row, c.getHomepage()));
				sheets[j].addCell(new Label(30, row, c.getOfficetel()));
				
				sheets[j].addCell(new Label(31, row, c.getMobile()));
				sheets[j].addCell(new Label(32, row, c.getFax()));
				sheets[j].addCell(new Label(33, row, c.getEmail()));
				sheets[j].addCell(new Label(34, row, c.getTaxcode()));
				sheets[j].addCell(new Label(35, row, app.getOrganName(c.getMakeorganid())));
				sheets[j].addCell(new Label(36, row, ds.getDeptName(c.getMakedeptid())));
				sheets[j].addCell(new Label(37, row, us.getUsersName(c.getMakeid())));
				sheets[j].addCell(new Label(38, row, DateUtil.formatDate(c.getMakedate())));
				sheets[j].addCell(new Label(39, row, DateUtil.formatDate(c.getLastcontact())));
				sheets[j].addCell(new Label(40, row, DateUtil.formatDate(c.getNextcontact())));
				
				sheets[j].addCell(new Label(41, row, c.getRemark().toString()));
				sheets[j].addCell(new Label(42, row, HtmlSelect.getNameByOrder(request, "YesOrNo", c.getIsactivate())));
				sheets[j].addCell(new Label(43, row, us.getUsersName(c.getActivateid())));
				sheets[j].addCell(new Label(44, row, DateUtil.formatDate(c.getActivatedate())));
				sheets[j].addCell(new Label(45, row, HtmlSelect.getNameByOrder(request, "YesOrNo", c.getIsreceivemsg())));
				sheets[j].addCell(new Label(46, row, HtmlSelect.getNameByOrder(request, "YesOrNo", c.getIsdel())));

			}
		}
		workbook.write();
		workbook.close();
		os.close();
	}

}
