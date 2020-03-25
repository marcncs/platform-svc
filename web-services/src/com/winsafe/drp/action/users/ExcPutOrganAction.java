package com.winsafe.drp.action.users;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.owasp.esapi.ESAPI;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCountryArea;
import com.winsafe.drp.dao.AppOlinkMan;
import com.winsafe.drp.dao.Olinkman;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.metadata.DealerType;
import com.winsafe.drp.metadata.OrganType;
import com.winsafe.drp.metadata.PlantType;
import com.winsafe.drp.metadata.ValidateStatus;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;
import com.winsafe.hbm.util.StringUtil;

/**
 * @author : jerry
 */
public class ExcPutOrganAction extends BaseAction {

	private static Logger logger = Logger.getLogger(ExcPutOrganAction.class);
	AppOlinkMan al = new AppOlinkMan();
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OrganService ao = new OrganService();
		initdata(request);
		String organid=request.getParameter("CompanyID");
		String linkman=request.getParameter("linkman");
		String validateStatus=request.getParameter("ValidateStatus");
		try {
			String Condition = "";
			if(DbUtil.isDealer(users)) {
				Condition = " o.parentid ='"+users.getMakeorganid()+"' ";
			} else {
				Condition += DbUtil.getWhereCondition(users, "o");
			}
			/*if(users.getUserType() != null) {
				if(users.getUserType().equals(UserType.SS.getValue())
						|| users.getUserType().equals(UserType.SR.getValue())) {
					Condition = " (o.id in (select organId from UserCustomer where userId="+users.getUserid()+") or (o.organType="+OrganType.Dealer.getValue()+" and o.organModel not in ("+DealerType.PD.getValue()+","+DealerType.BKD.getValue()+") and o.areas in (select sac.countryAreaId from SalesAreaCountry sac,SUserArea sua where sua.areaId = sac.salesAreaId and sua.userId = "+users.getUserid()+")))";
				} else if(users.getUserType().equals(UserType.ASM.getValue())) {
					Condition = " (o.id in (select organId from UserCustomer where userId="+users.getUserid()+") or " +
							"o.id in (select uc.organId from UserCustomer uc,SUserArea sua where sua.userId=uc.userId and sua.areaId in (select id from RegionItemInfo where pId in (select areaId from SUserArea where userId = "+users.getUserid()+")))" +
							"or (o.organType="+OrganType.Dealer.getValue()+" and o.organModel not in ("+DealerType.PD.getValue()+","+DealerType.BKD.getValue()+") and o.areas in (" +
							"select sac.countryAreaId from SalesAreaCountry sac,SUserArea sua where sua.areaId = sac.salesAreaId and sac.salesAreaId in (select id from RegionItemInfo where pId in (select areaId from SUserArea where userId = "+users.getUserid()+"))" +
							")))";
				} else if(users.getUserType().equals(UserType.RM.getValue())) {
					Condition = " (o.id in (select uc.organId from UserCustomer uc,SUserArea sua where sua.userId=uc.userId and sua.areaId in (select id from RegionItemInfo where pId in (select areaId from SUserArea where userId = "+users.getUserid()+")))" +
					"o.id in (select uc.organId from UserCustomer uc,SUserArea sua where sua.userId=uc.userId and sua.areaId in (select id from RegionItemInfo where pId in (select id from RegionItemInfo where pId in (select areaId from SUserArea where userId = "+users.getUserid()+"))))" +
					" or o.areas in (" +
					"select sac.countryAreaId from SalesAreaCountry sac,SUserArea sua where sua.areaId = sac.salesAreaId and sac.salesAreaId in (select id from RegionItemInfo where pId in (select id from RegionItemInfo where pId in (select areaId from SUserArea where userId = "+users.getUserid()+")))" +
					"))";
				} else if(users.getOrganType() != null && OrganType.Dealer.getValue().equals(users.getOrganType())) {
					Condition = " o.parentid ='"+users.getMakeorganid()+"' ";
				} else {
					Condition = " o.sysid like '"
						+ ao.getOrganByID(users.getMakeorganid()).getSysid()
						+ "%' ";
				}
			} else {
				if(users.getOrganType() != null && OrganType.Dealer.getValue().equals(users.getOrganType())) {
					Condition = " o.parentid ='"+users.getMakeorganid()+"' ";
				} else {
					Condition = " o.sysid like '"
						+ ao.getOrganByID(users.getMakeorganid()).getSysid()
						+ "%' ";
				}
			}*/
			if(organid!=null&&organid!=""){
				Condition +="and o.id = '"+organid+"'";
			}
			if(!StringUtil.isEmpty(linkman)){
				Condition +="and ( (o.omobile like '%"+linkman+"%') or exists (select id from Olinkman where cid=o.id and (name like '%"+linkman+"%' or mobile like '%"+linkman+"%') ) or exists (select id from Warehouse w where makeorganid = o.id and (username like '%"+linkman+"%' or warehousetel like '%"+linkman+"%' or exists ( select id from Wlinkman where warehouseid = w.id and (name like '%"+linkman+"%' or mobile like '%"+linkman+"%') ))) ) ";
			}
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Organ" };
			String[] alias = { "o" };
			String whereSql = EntityManager.getTmpWhereSqlAlias(map, tablename, alias);

			String blur = DbUtil.getOrBlur(map, tmpMap, "o.ID", "OrganName",
					"OMobile", "OECode","bigRegionName","officeName");
			String timeCondition = "";
			String createTimeCondition = DbUtil.getTimeCondition(map, tmpMap,
				"o.creationTime"); 
			if(!StringUtil.isEmpty(createTimeCondition)) {
				createTimeCondition = "("+createTimeCondition.substring(0,createTimeCondition.lastIndexOf("and"))+")";
			}
			String modificationTimeCondition = DbUtil.getTimeCondition(map, tmpMap,
            	"o.modificationTime"); 
			if(!StringUtil.isEmpty(modificationTimeCondition)) {
				modificationTimeCondition = "("+modificationTimeCondition.substring(0,modificationTimeCondition.lastIndexOf("and"))+")";
			}
			
			if(!StringUtil.isEmpty(createTimeCondition) && !StringUtil.isEmpty(modificationTimeCondition)) {
				timeCondition = "("+createTimeCondition+" or " + modificationTimeCondition+") and ";
			} else if(!StringUtil.isEmpty(createTimeCondition) || !StringUtil.isEmpty(modificationTimeCondition)){
				timeCondition = createTimeCondition + modificationTimeCondition +" and ";
			}
			
			
			whereSql = whereSql + timeCondition+blur + Condition;
			if(!StringUtil.isEmpty(validateStatus)) {
				ValidateStatus status = ValidateStatus.parseByValue(Integer.valueOf(validateStatus));
				if(ValidateStatus.NOT_AUDITED == status) {
					whereSql += " and (validate_status is null or validate_status= "+ status.getValue()+") ";// whereSql.replace("validatestatus", "validate_status");
				} else {
					whereSql += " and validate_status="+ status.getValue();
				}
			}
			whereSql = DbUtil.getWhereSql(whereSql);


//			List<Map<String,String>> list = ao.getOrganList(request, 0, whereSql);
			
			List<Map<String,String>> list = ao.getExportOrganList(request, 0, whereSql);
			if (list.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=Organ.xls");
			response.setContentType("application/msexcel");
			// 写入excel
			writeXls(list, os, request);
			os.flush();
			os.close();
			DBUserLog.addUserLog(userid, 11, "导出机构资料!");
		} catch (Exception ex) {
			logger.error("机构导出异常", ex);
		}
		return null;
	}

	/*
	 * 将数据写入excel中
	 */
	public void writeXls(List<Map<String,String>> list, OutputStream os,
			HttpServletRequest request) throws Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
//		AppOrganGrade aog = new AppOrganGrade();
		AppCountryArea aca = new AppCountryArea();
		Map<String,String> existOrganName = new HashMap<String, String>();
		Set<String> notExistOrganName = new HashSet<String>();
		OrganService organs = new OrganService();
		Map<String, String> areaNameMap = aca.getAreaNameMap();
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
			sheets[j].addCell(new Label(0, start, "机构资料", wchT));
			m = 0;
			sheets[j].addCell(new Label(m++, start + 1, "省份:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, StringUtil.removeNull(areaNameMap.get(request.getParameter("Province")))));
			sheets[j].addCell(new Label(m++, start + 1, "城市:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, StringUtil.removeNull(areaNameMap.get(request.getParameter("city")))));
			sheets[j].addCell(new Label(m++, start + 1, "地区:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, StringUtil.removeNull(areaNameMap.get(request.getParameter("areas")))));
			sheets[j].addCell(new Label(m++, start + 1, "是否撤消:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, HtmlSelect
					.getNameByOrder(request, "YesOrNo", getInt("IsRepeal"))));
			sheets[j].addCell(new Label(m++, start + 1, "关键字:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, request
					.getParameter("KeyWord")));
			sheets[j].addCell(new Label(m++, start + 1, "导出机构:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, request.getAttribute(
					"porganname").toString()));
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
			sheets[j].addCell(new Label(m++, start + 2, "机构类型", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "省", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "省名称", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "市", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "市名称", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "区", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "区名称", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "地址", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "是否撤消", wcfFC));
//			sheets[j].addCell(new Label(m++, start + 2, "撤消人", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "撤消时间", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "是否关键零售商", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "联系人", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "电话", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "Email", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "新增/修改", wcfFC));
			
			sheets[j].addCell(new Label(m++, start + 2, "公司名称", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "法人名称", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "成立日期", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "注册号", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "企业状态", wcfFC));

			for (int i = start; i < currentnum; i++) {
				int row = i - start + 3;
				Map<String,String> p = list.get(i);
				m = 0;
				sheets[j].addCell(new Label(m++, row, p.get("id")));
				sheets[j].addCell(new Label(m++, row, ESAPI.encoder().decodeForHTML(p.get("organname"))));
				sheets[j].addCell(new Label(m++, row, p.get("oecode")));
				sheets[j].addCell(new Label(m++, row, p.get("parentid")));
				//获取上级机构名称
				if(existOrganName.containsKey(p.get("parentid"))) {
					sheets[j].addCell(new Label(m++, row, ESAPI.encoder().decodeForHTML(existOrganName.get(p
							.get("parentid")))));
				} else {
					if(notExistOrganName.contains(p.get("parentid"))) {
						sheets[j].addCell(new Label(m++, row, ""));
					} else {
						String organName = organs.getOrganName(p.get("parentid"));
						if(StringUtil.isEmpty(organName)) {
							sheets[j].addCell(new Label(m++, row, ""));
							notExistOrganName.add(p.get("parentid"));
						} else {
							sheets[j].addCell(new Label(m++, row, ESAPI.encoder().decodeForHTML(organName)));
							existOrganName.put(p.get("parentid"), organName);
						}
					}
				} 
				if(StringUtil.isEmpty(p.get("organmodel"))) {
					sheets[j].addCell(new Label(m++, row, ""));
				} else if(OrganType.Plant.getValue().toString().equals(p.get("organtype"))) {
					PlantType type = PlantType.parseByValue(Integer.valueOf(p.get("organmodel")));
					sheets[j].addCell(new Label(m++, row, type!=null?type.getName():""));
				} else if(OrganType.Dealer.getValue().toString().equals(p.get("organtype"))) {
					DealerType type = DealerType.parseByValue(Integer.valueOf(p.get("organmodel")));
					sheets[j].addCell(new Label(m++, row, type!=null?type.getName():""));
				} else {
					sheets[j].addCell(new Label(m++, row, ""));
				}
				sheets[j].addCell(new Label(m++, row, p.get("province")
						.toString()));
				sheets[j].addCell(new Label(m++, row, StringUtil.removeNull(areaNameMap.get(p.get("province")))));
				sheets[j].addCell(new Label(m++, row, p.get("city").toString()));
				sheets[j].addCell(new Label(m++, row, StringUtil.removeNull(areaNameMap.get(p.get("city")))));
				sheets[j].addCell(new Label(m++, row, p.get("areas").toString()));
				sheets[j].addCell(new Label(m++, row, StringUtil.removeNull(areaNameMap.get(p.get("areas")))));
				sheets[j].addCell(new Label(m++, row, p.get("oaddr")));
				if("1".equals(p.get("isrepeal"))) {
					sheets[j].addCell(new Label(m++, row, "是"));
				} else {
					sheets[j].addCell(new Label(m++, row, "否"));
				}
//				sheets[j].addCell(new Label(m++, row, uses
//						.getUsersName(p.get("repealid")!=null?Integer.valueOf(p.get("repealid")):0)));
				sheets[j].addCell(new Label(m++, row, p
						.get("repealdate")));
				sheets[j].addCell(new Label(m++, row, isKeyRetailer(!StringUtil.isEmpty(p.get("iskeyretailer"))?Integer.valueOf(p.get("iskeyretailer")):null)));
				Olinkman oman = al.getMainLinkmanByCid(p.get("id"));
				sheets[j].addCell(new Label(m++, row, oman!=null?oman.getName():""));
				sheets[j].addCell(new Label(m++, row, oman!=null?oman.getMobile():""));
				sheets[j].addCell(new Label(m++, row, oman!=null?oman.getEmail():""));
				sheets[j].addCell(new Label(m++, row, p.get("modificationtime")!=null?"修改":"新增"));
				
				sheets[j].addCell(new Label(m++, row, StringUtil.removeNull(p.get("iname"))));
				sheets[j].addCell(new Label(m++, row, StringUtil.removeNull(p.get("leg"))));
				sheets[j].addCell(new Label(m++, row, StringUtil.removeNull(p.get("c_date"))));
				sheets[j].addCell(new Label(m++, row, StringUtil.removeNull(p.get("re_code"))));
				sheets[j].addCell(new Label(m++, row, StringUtil.removeNull(p.get("status"))));

			}
		}
		workbook.write();
		workbook.close();
		os.close();
	}
	
	private String isKeyRetailer(Integer v) {
		String ret = "";
		if (v == null) {
			 ret = "";
		} else if (v == 1) {
			ret = "是";
		} else if (v == 0) {
			ret = "否";
		}
		return ret;
	}
}
