package com.winsafe.drp.keyretailer.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.RecAndDisSumReportForm;
import com.winsafe.drp.dao.RegionItemInfo;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.keyretailer.dao.AppSBonusArea;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class RecAndDisSumReportService {
	private static Logger logger = Logger.getLogger(RecAndDisSumReportService.class);
	private AppBaseResource appBr = new AppBaseResource();
	
	// 根据条件查询 
	public List<RecAndDisSumReportForm> queryDisReport(HttpServletRequest request, int pageSize,RecAndDisSumReportForm scrForm,UsersBean users) throws Exception{
		
		//对于结束日期增加一天
		if(!StringUtil.isEmpty(scrForm.getEndDate())){
			String endDateStr = scrForm.getEndDate();
			Date endDate = Dateutil.StringToDate(endDateStr);
			endDate = Dateutil.addDay2Date(1, endDate);
			scrForm.setEndDate(Dateutil.formatDate(endDate));
		}
		RegionItemInfo region = null;
		if(!StringUtil.isEmpty(scrForm.getRegionId())) {
			AppSBonusArea arii = new AppSBonusArea();
			region = arii.getSBonusAreaById(scrForm.getRegionId());
		}
		String condition = SBonusService.getWhereCondition(users);
		
		List<RecAndDisSumReportForm> resultList = new ArrayList<RecAndDisSumReportForm>();
		StringBuffer sql = new StringBuffer();
		
		/*sql.append(" \r\n select ");
		sql.append(" \r\n  o.organname outorganname");
		sql.append(" \r\n ,max(sba3.name_loc) BIGREGION");
		sql.append(" \r\n ,max(sba2.name_loc) outmiddleREGION");
		sql.append(" \r\n ,ino.organname inorganname ");
		sql.append(" \r\n ,max(sba5.name_loc) inmiddleREGION ");
		sql.append(" \r\n ,max(sba4.name_loc) smallREGION");
		sql.append(" \r\n ,p.productname");
		sql.append(" \r\n ,p.specmode spec ");
		sql.append(" \r\n ,sum(ttd.realquantity*fu.xquantity*p.boxquantity) amount ");
		sql.append(" \r\n ,sum(bonuspoint) bonusPoint ");
		sql.append(" \r\n ,max(p.countunit) unitId  ");
		
		
		sql.append(" \r\n from TAKE_TICKET tt ");
		sql.append(" \r\n join ORGAN o on tt.OID = o.id and o.ORGANTYPE = 2 and o.ORGANMODEL <> 1 and tt.isaudit = 1 ");
		
		if(!StringUtil.isEmpty(scrForm.getBeginDate())){
			sql.append(" \r\n and tt.makedate >=to_date('" + scrForm.getBeginDate() + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		if(!StringUtil.isEmpty(scrForm.getEndDate())){
			sql.append(" \r\n and tt.makedate < to_date('" + scrForm.getEndDate() + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		}
		if(!StringUtil.isEmpty(scrForm.getOutOrganId())){
			sql.append(" \r\n and o.id ='" + scrForm.getOutOrganId() + "'"); //结束时间条件
		} else {
			sql.append(" \r\n and " + condition);
		}
		sql.append(" \r\n join ORGAN ino on tt.inoid=ino.id ");
		if(!StringUtil.isEmpty(scrForm.getInOrganId())){
			sql.append(" \r\n and ino.id ='" + scrForm.getInOrganId() + "'"); //结束时间条件
		}
		
		sql.append(" \r\n join TAKE_TICKET_DETAIL ttd on ttd.ttid=tt.id ");
		sql.append(" \r\n join F_UNIT fu on fu.productid = ttd.productid and fu.funitid = ttd.unitid ");
		sql.append(" \r\n join product p on p.id = ttd.productid ");
		
		if(!StringUtil.isEmpty(scrForm.getProductName())){
			sql.append(" \r\n and p.productname ='" + scrForm.getProductName() + "'"); //结束时间条件
		}
		if(!StringUtil.isEmpty(scrForm.getSpec())){
			sql.append(" \r\n and p.specmode ='" + scrForm.getSpec() + "'"); //结束时间条件
		}
		sql.append(" \r\n LEFT JOIN S_BONUS_DETAIL sbd on sbd.remark=tt.billno and sbd.mcode = p.mcode ");
		sql.append(" \r\n LEFT JOIN SALES_AREA_COUNTRY sac on o.areas = sac.countryareaid and o.organmodel not in (1,2) ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba1 on sba1.id = sac.salesareaid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba2 on sba2.id = sba1.parentid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba3 on sba3.id = sba2.parentid ");
		
		sql.append(" \r\n LEFT JOIN SALES_AREA_COUNTRY sac2 on ino.areas = sac2.countryareaid and ino.organmodel not in (1,2) ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba4 on sba4.id = sac2.salesareaid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba5 on sba5.id = sba4.parentid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba6 on sba6.id = sba5.parentid ");
		sql.append(" where 1=1 ");
		if(region != null) {
			if(region.getRank() == 2) {
				sql.append(" and sba1.id="+region.getId());
			} else if(region.getRank() == 1) {
				sql.append(" and sba2.id="+region.getId());
			} else if(region.getRank() == 0) {
				sql.append(" and sba3.id="+region.getId());
			}
		}
		
		sql.append(" \r\n group by o.organname,ino.organname,p.productname,p.specmode");*/
		
		sql.append(" \r\n select ");
		sql.append(" \r\n  outorganname");
		sql.append(" \r\n ,max(BIGREGION) BIGREGION");
		sql.append(" \r\n ,max(outmiddleREGION) outmiddleREGION");
		sql.append(" \r\n ,inorganname ");
		sql.append(" \r\n ,max(inmiddleREGION) inmiddleREGION ");
		sql.append(" \r\n ,max(smallREGION)  smallREGION");
		sql.append(" \r\n ,productname");
		sql.append(" \r\n ,spec ");
		sql.append(" \r\n ,sum(amount) amount ");
		sql.append(" \r\n ,sum(bonusPoint) bonusPoint ");
		sql.append(" \r\n ,max(unitId) unitId from (");
		
		sql.append(" \r\n select ");
		sql.append(" \r\n  o.organname outorganname ");
		sql.append(" \r\n ,sba3.name_loc BIGREGION ");
		sql.append(" \r\n ,sba2.name_loc outmiddleREGION ");
		sql.append(" \r\n ,ino.organname inorganname   ");
		sql.append(" \r\n ,sba5.name_loc inmiddleREGION   ");
		sql.append(" \r\n ,sba4.name_loc smallREGION");
		sql.append(" \r\n ,p.productname ");
		sql.append(" \r\n ,p.specmode spec ");
		sql.append(" \r\n ,samd.receivequantity*fu.xquantity*p.boxquantity amount  ");
		sql.append(" \r\n ,bonuspoint bonusPoint  ");
		sql.append(" \r\n ,p.countunit unitId  ");
		
		sql.append(" \r\n from STOCK_ALTER_MOVE sam ");
		sql.append(" \r\n join ORGAN o on sam.outorganid=o.id and o.ORGANTYPE = 2 and o.ORGANMODEL <> 1 and sam.iscomplete = 1 ");
		if(!StringUtil.isEmpty(scrForm.getOutOrganId())){
			sql.append(" \r\n and o.id ='" + scrForm.getOutOrganId() + "'"); //结束时间条件
		} else {
			sql.append(" \r\n and " + condition);
		}
		
		sql.append(" \r\n join ORGAN ino on sam.receiveorganid = ino.id ");
		
		if(!StringUtil.isEmpty(scrForm.getBeginDate())){
			sql.append(" \r\n and sam.makedate >=to_date('" + scrForm.getBeginDate() + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		if(!StringUtil.isEmpty(scrForm.getEndDate())){
			sql.append(" \r\n and sam.makedate < to_date('" + scrForm.getEndDate() + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		}
		if(!StringUtil.isEmpty(scrForm.getInOrganId())){
			sql.append(" \r\n and ino.id ='" + scrForm.getInOrganId() + "'"); //结束时间条件
		}
		
		sql.append(" \r\n join STOCK_ALTER_MOVE_DETAIL samd on samd.samid=sam.id ");
		sql.append(" \r\n join F_UNIT fu on fu.productid = samd.productid and fu.funitid = samd.unitid ");
		sql.append(" \r\n join product p on p.id = samd.productid ");
		
		if(!StringUtil.isEmpty(scrForm.getProductName())){
			sql.append(" \r\n and p.productname ='" + scrForm.getProductName() + "'"); //结束时间条件
		}
		if(!StringUtil.isEmpty(scrForm.getSpec())){
			sql.append(" \r\n and p.specmode ='" + scrForm.getSpec() + "'"); //结束时间条件
		}
		sql.append(" \r\n LEFT JOIN S_BONUS_DETAIL sbd on sbd.remark=sam.id and sbd.mcode = p.mcode and sbd.organid=sam.outorganid");
		sql.append(" \r\n LEFT JOIN SALES_AREA_COUNTRY sac on o.areas = sac.countryareaid and o.organmodel not in (1,2) ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba1 on sba1.id = sac.salesareaid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba2 on sba2.id = sba1.parentid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba3 on sba3.id = sba2.parentid ");
		
		sql.append(" \r\n LEFT JOIN SALES_AREA_COUNTRY sac2 on ino.areas = sac2.countryareaid and ino.organmodel not in (1,2) ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba4 on sba4.id = sac2.salesareaid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba5 on sba5.id = sba4.parentid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba6 on sba6.id = sba5.parentid ");
		sql.append(" where 1=1 ");
		if(region != null) {
			if(region.getRank() == 2) {
				sql.append(" and sba1.id="+region.getId());
			} else if(region.getRank() == 1) {
				sql.append(" and sba2.id="+region.getId());
			} else if(region.getRank() == 0) {
				sql.append(" and sba3.id="+region.getId());
			}
		}
		if(!StringUtil.isEmpty(scrForm.getAreaId())) {
			sql.append(" and o.areas="+scrForm.getAreaId());
		}
		
		sql.append(" UNION ALL ");
		
		sql.append(" \r\n select ");
		sql.append(" \r\n  o.organname outorganname ");
		sql.append(" \r\n ,sba3.name_loc BIGREGION ");
		sql.append(" \r\n ,sba2.name_loc outmiddleREGION ");
		sql.append(" \r\n ,ino.organname inorganname   ");
		sql.append(" \r\n ,sba5.name_loc inmiddleREGION   ");
		sql.append(" \r\n ,sba4.name_loc smallREGION");
		sql.append(" \r\n ,p.productname ");
		sql.append(" \r\n ,p.specmode spec ");
		sql.append(" \r\n ,owd.takequantity*fu.xquantity*p.boxquantity amount  ");
		sql.append(" \r\n ,bonuspoint bonusPoint  ");
		sql.append(" \r\n ,p.countunit unitId  ");
		
		sql.append(" \r\n from ORGAN_WITHDRAW ow ");
		sql.append(" \r\n join ORGAN o on ow.porganid=o.id and o.ORGANTYPE = 2 and o.ORGANMODEL <> 1 and ow.iscomplete = 1 ");
		if(!StringUtil.isEmpty(scrForm.getOutOrganId())){
			sql.append(" \r\n and o.id ='" + scrForm.getOutOrganId() + "'"); //结束时间条件
		} else {
			sql.append(" \r\n and " + condition);
		}
		
		sql.append(" \r\n join ORGAN ino on ow.receiveorganid = ino.id ");
		
		if(!StringUtil.isEmpty(scrForm.getBeginDate())){
			sql.append(" \r\n and ow.makedate >=to_date('" + scrForm.getBeginDate() + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		if(!StringUtil.isEmpty(scrForm.getEndDate())){
			sql.append(" \r\n and ow.makedate < to_date('" + scrForm.getEndDate() + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		}
		if(!StringUtil.isEmpty(scrForm.getInOrganId())){
			sql.append(" \r\n and ino.id ='" + scrForm.getInOrganId() + "'"); //结束时间条件
		}
		
		sql.append(" \r\n join ORGAN_WITHDRAW_DETAIL owd on owd.owid=ow.id ");
		sql.append(" \r\n join F_UNIT fu on fu.productid = owd.productid and fu.funitid = owd.unitid ");
		sql.append(" \r\n join product p on p.id = owd.productid ");
		
		if(!StringUtil.isEmpty(scrForm.getProductName())){
			sql.append(" \r\n and p.productname ='" + scrForm.getProductName() + "'"); //结束时间条件
		}
		if(!StringUtil.isEmpty(scrForm.getSpec())){
			sql.append(" \r\n and p.specmode ='" + scrForm.getSpec() + "'"); //结束时间条件
		}
		sql.append(" \r\n LEFT JOIN S_BONUS_DETAIL sbd on sbd.remark=ow.id and sbd.mcode = p.mcode and sbd.organid=ow.porganid");
		sql.append(" \r\n LEFT JOIN SALES_AREA_COUNTRY sac on o.areas = sac.countryareaid and o.organmodel not in (1,2) ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba1 on sba1.id = sac.salesareaid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba2 on sba2.id = sba1.parentid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba3 on sba3.id = sba2.parentid ");
		
		sql.append(" \r\n LEFT JOIN SALES_AREA_COUNTRY sac2 on ino.areas = sac2.countryareaid and ino.organmodel not in (1,2) ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba4 on sba4.id = sac2.salesareaid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba5 on sba5.id = sba4.parentid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba6 on sba6.id = sba5.parentid ");
		sql.append(" where 1=1 ");
		if(region != null) {
			if(region.getRank() == 2) {
				sql.append(" and sba1.id="+region.getId());
			} else if(region.getRank() == 1) {
				sql.append(" and sba2.id="+region.getId());
			} else if(region.getRank() == 0) {
				sql.append(" and sba3.id="+region.getId());
			}
		}
		if(!StringUtil.isEmpty(scrForm.getAreaId())) {
			sql.append(" and o.areas="+scrForm.getAreaId());
		}
		
		sql.append(" \r\n ) temp");
		sql.append(" \r\n group by outorganname,inorganname,productname,spec");
		
		System.out.println(sql.toString());
		List<Map> list =  new ArrayList<Map>();
		if(request == null && pageSize == 0){
			list =  EntityManager.jdbcquery(sql.toString());
		}else {
			list =  PageQuery.jdbcSqlserverQuery(request, "outorganname", sql.toString(), pageSize);
		}
		if(list != null && list.size()>0){
			Map<Integer, String> countUnitMap = appBr.getBaseResourceMap("CountUnit");
			for(Map map : list){
				RecAndDisSumReportForm scForm = new RecAndDisSumReportForm();
				//将Map中对应的值赋值给实例
				MapUtil.mapToObject(map, scForm);
				scForm.setUnitName(countUnitMap.get(scForm.getUnitId()));
				resultList.add(scForm);
			}
		}
		return resultList;
	}
	
	// 根据条件查询 
	public List<RecAndDisSumReportForm> queryDisDetailReport(HttpServletRequest request, int pageSize,RecAndDisSumReportForm scrForm,UsersBean users) throws Exception{
		
		//对于结束日期增加一天
		if(!StringUtil.isEmpty(scrForm.getEndDate())){
			String endDateStr = scrForm.getEndDate();
			Date endDate = Dateutil.StringToDate(endDateStr);
			endDate = Dateutil.addDay2Date(1, endDate);
			scrForm.setEndDate(Dateutil.formatDate(endDate));
		}
		RegionItemInfo region = null;
		if(!StringUtil.isEmpty(scrForm.getRegionId())) {
			AppSBonusArea arii = new AppSBonusArea();
			region = arii.getSBonusAreaById(scrForm.getRegionId());
		}
		String condition = SBonusService.getWhereCondition(users);
		
		List<RecAndDisSumReportForm> resultList = new ArrayList<RecAndDisSumReportForm>();
		StringBuffer sql = new StringBuffer();
		
		/*sql.append(" \r\n select ");
		sql.append(" \r\n  o.organname outorganname");
		sql.append(" \r\n ,sba3.name_loc BIGREGION");
		sql.append(" \r\n ,sba2.name_loc outmiddleREGION");
		sql.append(" \r\n ,ino.organname inorganname ");
		sql.append(" \r\n ,sba5.name_loc inmiddleREGION ");
		sql.append(" \r\n ,sba4.name_loc smallREGION");
		sql.append(" \r\n ,p.productname");
		sql.append(" \r\n ,p.specmode spec ");
		sql.append(" \r\n ,ttd.realquantity*fu.xquantity*p.boxquantity amount ");
		sql.append(" \r\n ,bonuspoint bonusPoint ");
		sql.append(" \r\n ,p.countunit unitId  ");
		sql.append(" \r\n ,tt.BSORT ");
		sql.append(" \r\n ,TT.MAKEDATE ");
		
		sql.append(" \r\n from TAKE_TICKET tt ");
		sql.append(" \r\n join ORGAN o on tt.OID = o.id and o.ORGANTYPE = 2 and o.ORGANMODEL <> 1 and tt.isaudit = 1 ");
		
		if(!StringUtil.isEmpty(scrForm.getBeginDate())){
			sql.append(" \r\n and tt.makedate >=to_date('" + scrForm.getBeginDate() + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		if(!StringUtil.isEmpty(scrForm.getEndDate())){
			sql.append(" \r\n and tt.makedate < to_date('" + scrForm.getEndDate() + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		}
		if(!StringUtil.isEmpty(scrForm.getOutOrganId())){
			sql.append(" \r\n and o.id ='" + scrForm.getOutOrganId() + "'"); //结束时间条件
		} else {
			sql.append(" \r\n and " + condition);
		}
		sql.append(" \r\n join ORGAN ino on tt.inoid=ino.id ");
		if(!StringUtil.isEmpty(scrForm.getInOrganId())){
			sql.append(" \r\n and ino.id ='" + scrForm.getInOrganId() + "'"); //结束时间条件
		}
		
		sql.append(" \r\n join TAKE_TICKET_DETAIL ttd on ttd.ttid=tt.id ");
		sql.append(" \r\n join F_UNIT fu on fu.productid = ttd.productid and fu.funitid = ttd.unitid ");
		sql.append(" \r\n join product p on p.id = ttd.productid ");
		
		if(!StringUtil.isEmpty(scrForm.getProductName())){
			sql.append(" \r\n and p.productname ='" + scrForm.getProductName() + "'"); //结束时间条件
		}
		if(!StringUtil.isEmpty(scrForm.getSpec())){
			sql.append(" \r\n and p.specmode ='" + scrForm.getSpec() + "'"); //结束时间条件
		}
		sql.append(" \r\n LEFT JOIN S_BONUS_DETAIL sbd on sbd.remark=tt.billno and sbd.mcode = p.mcode ");
		sql.append(" \r\n LEFT JOIN SALES_AREA_COUNTRY sac on o.areas = sac.countryareaid and o.organmodel not in (1,2) ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba1 on sba1.id = sac.salesareaid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba2 on sba2.id = sba1.parentid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba3 on sba3.id = sba2.parentid ");
		
		sql.append(" \r\n LEFT JOIN SALES_AREA_COUNTRY sac2 on ino.areas = sac2.countryareaid and ino.organmodel not in (1,2) ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba4 on sba4.id = sac2.salesareaid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba5 on sba5.id = sba4.parentid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba6 on sba6.id = sba5.parentid ");
		sql.append(" where 1=1 ");
		if(region != null) {
			if(region.getRank() == 2) {
				sql.append(" and sba1.id="+region.getId());
			} else if(region.getRank() == 1) {
				sql.append(" and sba2.id="+region.getId());
			} else if(region.getRank() == 0) {
				sql.append(" and sba3.id="+region.getId());
			}
		}*/
		
		sql.append(" \r\n select ");
		sql.append(" \r\n  o.organname outorganname ");
		sql.append(" \r\n ,sba3.name_loc BIGREGION ");
		sql.append(" \r\n ,sba2.name_loc outmiddleREGION ");
		sql.append(" \r\n ,ino.organname inorganname   ");
		sql.append(" \r\n ,sba5.name_loc inmiddleREGION   ");
		sql.append(" \r\n ,sba4.name_loc smallREGION");
		sql.append(" \r\n ,p.productname ");
		sql.append(" \r\n ,p.specmode spec ");
		sql.append(" \r\n ,samd.receivequantity*fu.xquantity*p.boxquantity amount  ");
		sql.append(" \r\n ,bonuspoint bonusPoint  ");
		sql.append(" \r\n ,p.countunit unitId  ");
		sql.append(" \r\n ,1 BSORT ");
		sql.append(" \r\n ,sam.MAKEDATE ");
		
		sql.append(" \r\n from STOCK_ALTER_MOVE sam ");
		sql.append(" \r\n join ORGAN o on sam.outorganid=o.id and o.ORGANTYPE = 2 and o.ORGANMODEL <> 1 and sam.iscomplete = 1 ");
		if(!StringUtil.isEmpty(scrForm.getOutOrganId())){
			sql.append(" \r\n and o.id ='" + scrForm.getOutOrganId() + "'"); //结束时间条件
		} else {
			sql.append(" \r\n and " + condition);
		}
		
		sql.append(" \r\n join ORGAN ino on sam.receiveorganid = ino.id ");
		
		if(!StringUtil.isEmpty(scrForm.getBeginDate())){
			sql.append(" \r\n and sam.makedate >=to_date('" + scrForm.getBeginDate() + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		if(!StringUtil.isEmpty(scrForm.getEndDate())){
			sql.append(" \r\n and sam.makedate < to_date('" + scrForm.getEndDate() + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		}
		if(!StringUtil.isEmpty(scrForm.getInOrganId())){
			sql.append(" \r\n and ino.id ='" + scrForm.getInOrganId() + "'"); //结束时间条件
		}
		
		sql.append(" \r\n join STOCK_ALTER_MOVE_DETAIL samd on samd.samid=sam.id ");
		sql.append(" \r\n join F_UNIT fu on fu.productid = samd.productid and fu.funitid = samd.unitid ");
		sql.append(" \r\n join product p on p.id = samd.productid ");
		
		if(!StringUtil.isEmpty(scrForm.getProductName())){
			sql.append(" \r\n and p.productname ='" + scrForm.getProductName() + "'"); //结束时间条件
		}
		if(!StringUtil.isEmpty(scrForm.getSpec())){
			sql.append(" \r\n and p.specmode ='" + scrForm.getSpec() + "'"); //结束时间条件
		}
		sql.append(" \r\n LEFT JOIN S_BONUS_DETAIL sbd on sbd.remark=sam.id and sbd.mcode = p.mcode and sbd.organid=sam.outorganid");
		sql.append(" \r\n LEFT JOIN SALES_AREA_COUNTRY sac on o.areas = sac.countryareaid and o.organmodel not in (1,2) ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba1 on sba1.id = sac.salesareaid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba2 on sba2.id = sba1.parentid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba3 on sba3.id = sba2.parentid ");
		
		sql.append(" \r\n LEFT JOIN SALES_AREA_COUNTRY sac2 on ino.areas = sac2.countryareaid and ino.organmodel not in (1,2) ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba4 on sba4.id = sac2.salesareaid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba5 on sba5.id = sba4.parentid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba6 on sba6.id = sba5.parentid ");
		sql.append(" where 1=1 ");
		if(region != null) {
			if(region.getRank() == 2) {
				sql.append(" and sba1.id="+region.getId());
			} else if(region.getRank() == 1) {
				sql.append(" and sba2.id="+region.getId());
			} else if(region.getRank() == 0) {
				sql.append(" and sba3.id="+region.getId());
			}
		}
		if(!StringUtil.isEmpty(scrForm.getAreaId())) {
			sql.append(" and o.areas="+scrForm.getAreaId());
		}
		
		sql.append(" UNION ALL ");
		
		sql.append(" \r\n select ");
		sql.append(" \r\n  o.organname outorganname ");
		sql.append(" \r\n ,sba3.name_loc BIGREGION ");
		sql.append(" \r\n ,sba2.name_loc outmiddleREGION ");
		sql.append(" \r\n ,ino.organname inorganname   ");
		sql.append(" \r\n ,sba5.name_loc inmiddleREGION   ");
		sql.append(" \r\n ,sba4.name_loc smallREGION");
		sql.append(" \r\n ,p.productname ");
		sql.append(" \r\n ,p.specmode spec ");
		sql.append(" \r\n ,owd.takequantity*fu.xquantity*p.boxquantity amount  ");
		sql.append(" \r\n ,bonuspoint bonusPoint  ");
		sql.append(" \r\n ,p.countunit unitId  ");
		sql.append(" \r\n ,7 BSORT ");
		sql.append(" \r\n ,ow.MAKEDATE ");
		
		sql.append(" \r\n from ORGAN_WITHDRAW ow ");
		sql.append(" \r\n join ORGAN o on ow.porganid=o.id and o.ORGANTYPE = 2 and o.ORGANMODEL <> 1 and ow.iscomplete = 1 ");
		if(!StringUtil.isEmpty(scrForm.getOutOrganId())){
			sql.append(" \r\n and o.id ='" + scrForm.getOutOrganId() + "'"); //结束时间条件
		} else {
			sql.append(" \r\n and " + condition);
		}
		
		sql.append(" \r\n join ORGAN ino on ow.receiveorganid = ino.id ");
		
		if(!StringUtil.isEmpty(scrForm.getBeginDate())){
			sql.append(" \r\n and ow.makedate >=to_date('" + scrForm.getBeginDate() + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		if(!StringUtil.isEmpty(scrForm.getEndDate())){
			sql.append(" \r\n and ow.makedate < to_date('" + scrForm.getEndDate() + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		}
		if(!StringUtil.isEmpty(scrForm.getInOrganId())){
			sql.append(" \r\n and ino.id ='" + scrForm.getInOrganId() + "'"); //结束时间条件
		}
		
		sql.append(" \r\n join ORGAN_WITHDRAW_DETAIL owd on owd.owid=ow.id ");
		sql.append(" \r\n join F_UNIT fu on fu.productid = owd.productid and fu.funitid = owd.unitid ");
		sql.append(" \r\n join product p on p.id = owd.productid ");
		
		if(!StringUtil.isEmpty(scrForm.getProductName())){
			sql.append(" \r\n and p.productname ='" + scrForm.getProductName() + "'"); //结束时间条件
		}
		if(!StringUtil.isEmpty(scrForm.getSpec())){
			sql.append(" \r\n and p.specmode ='" + scrForm.getSpec() + "'"); //结束时间条件
		}
		sql.append(" \r\n LEFT JOIN S_BONUS_DETAIL sbd on sbd.remark=ow.id and sbd.mcode = p.mcode and sbd.organid=ow.porganid");
		sql.append(" \r\n LEFT JOIN SALES_AREA_COUNTRY sac on o.areas = sac.countryareaid and o.organmodel not in (1,2) ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba1 on sba1.id = sac.salesareaid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba2 on sba2.id = sba1.parentid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba3 on sba3.id = sba2.parentid ");
		
		sql.append(" \r\n LEFT JOIN SALES_AREA_COUNTRY sac2 on ino.areas = sac2.countryareaid and ino.organmodel not in (1,2) ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba4 on sba4.id = sac2.salesareaid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba5 on sba5.id = sba4.parentid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba6 on sba6.id = sba5.parentid ");
		sql.append(" where 1=1 ");
		if(region != null) {
			if(region.getRank() == 2) {
				sql.append(" and sba1.id="+region.getId());
			} else if(region.getRank() == 1) {
				sql.append(" and sba2.id="+region.getId());
			} else if(region.getRank() == 0) {
				sql.append(" and sba3.id="+region.getId());
			}
		}
		if(!StringUtil.isEmpty(scrForm.getAreaId())) {
			sql.append(" and o.areas="+scrForm.getAreaId());
		}
		
//		System.out.println(sql.toString());
		List<Map> list =  new ArrayList<Map>();
		if(request == null && pageSize == 0){
			list =  EntityManager.jdbcquery(sql.toString());
		}else {
			list =  PageQuery.jdbcSqlserverQuery(request, "outorganname", sql.toString(), pageSize);
		}
		if(list != null && list.size()>0){
			Map<Integer, String> countUnitMap = appBr.getBaseResourceMap("CountUnit");
			for(Map map : list){
				RecAndDisSumReportForm scForm = new RecAndDisSumReportForm();
				//将Map中对应的值赋值给实例
				MapUtil.mapToObject(map, scForm);
				scForm.setUnitName(countUnitMap.get(scForm.getUnitId()));
				resultList.add(scForm);
			}
		}
		return resultList;
	}
	
	
	public List<RecAndDisSumReportForm> queryReport(HttpServletRequest request, int pageSize,RecAndDisSumReportForm scrForm,UsersBean users) throws Exception{
		
		//对于结束日期增加一天
		if(!StringUtil.isEmpty(scrForm.getEndDate())){
			String endDateStr = scrForm.getEndDate();
			Date endDate = Dateutil.StringToDate(endDateStr);
			endDate = Dateutil.addDay2Date(1, endDate);
			scrForm.setEndDate(Dateutil.formatDate(endDate));
		}
		
		String condition = SBonusService.getWhereCondition(users);
		
		List<RecAndDisSumReportForm> resultList = new ArrayList<RecAndDisSumReportForm>();
		StringBuffer sql = new StringBuffer();
		
		RegionItemInfo region = null;
		if(!StringUtil.isEmpty(scrForm.getRegionId())) {
			AppSBonusArea arii = new AppSBonusArea();
			region = arii.getSBonusAreaById(scrForm.getRegionId());
		}
		
		
		/*sql.append(" \r\n select ");
		sql.append(" \r\n  outo.organname outorganname");
		sql.append(" \r\n ,max(sba3.name_loc) BIGREGION");
		sql.append(" \r\n ,max(sba2.name_loc) outmiddleREGION");
		sql.append(" \r\n ,o.organname inorganname ");
		sql.append(" \r\n ,max(sba5.name_loc) inmiddleREGION ");
		sql.append(" \r\n ,max(sba4.name_loc) smallREGION");
		sql.append(" \r\n ,p.productname");
		sql.append(" \r\n ,p.specmode spec ");
		sql.append(" \r\n ,sum(ttd.realquantity*fu.xquantity*p.boxquantity) amount ");
		sql.append(" \r\n ,sum(bonuspoint) bonusPoint ");
		sql.append(" \r\n ,max(p.countunit) unitId  ");
		
		sql.append(" \r\n from TAKE_TICKET tt ");
		sql.append(" \r\n join ORGAN o on tt.inoid=o.id and o.ORGANTYPE = 2 and o.ORGANMODEL <> 1 and tt.isaudit = 1 ");
		if(!StringUtil.isEmpty(scrForm.getInOrganId())){
			sql.append(" \r\n and o.id ='" + scrForm.getInOrganId() + "'"); //结束时间条件
		} else {
			sql.append(" \r\n and " + condition);
		}
		
		sql.append(" \r\n join ORGAN outo on tt.OID = OUTO.id ");
		
		if(!StringUtil.isEmpty(scrForm.getBeginDate())){
			sql.append(" \r\n and tt.makedate >=to_date('" + scrForm.getBeginDate() + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		if(!StringUtil.isEmpty(scrForm.getEndDate())){
			sql.append(" \r\n and tt.makedate < to_date('" + scrForm.getEndDate() + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		}
		if(!StringUtil.isEmpty(scrForm.getOutOrganId())){
			sql.append(" \r\n and outo.id ='" + scrForm.getOutOrganId() + "'"); //结束时间条件
		}
		
		sql.append(" \r\n join TAKE_TICKET_DETAIL ttd on ttd.ttid=tt.id ");
		sql.append(" \r\n join F_UNIT fu on fu.productid = ttd.productid and fu.funitid = ttd.unitid ");
		sql.append(" \r\n join product p on p.id = ttd.productid ");
		
		if(!StringUtil.isEmpty(scrForm.getProductName())){
			sql.append(" \r\n and p.productname ='" + scrForm.getProductName() + "'"); //结束时间条件
		}
		if(!StringUtil.isEmpty(scrForm.getSpec())){
			sql.append(" \r\n and p.specmode ='" + scrForm.getSpec() + "'"); //结束时间条件
		}
		sql.append(" \r\n LEFT JOIN S_BONUS_DETAIL sbd on sbd.remark=tt.billno and sbd.mcode = p.mcode ");
		sql.append(" \r\n LEFT JOIN SALES_AREA_COUNTRY sac on outo.areas = sac.countryareaid and outo.organmodel not in (1,2) ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba1 on sba1.id = sac.salesareaid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba2 on sba2.id = sba1.parentid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba3 on sba3.id = sba2.parentid ");
		
		sql.append(" \r\n LEFT JOIN SALES_AREA_COUNTRY sac2 on o.areas = sac2.countryareaid and o.organmodel not in (1,2) ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba4 on sba4.id = sac2.salesareaid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba5 on sba5.id = sba4.parentid ");
		
		sql.append(" \r\n group by outo.organname,o.organname,p.productname,p.specmode");*/
		
		
		sql.append(" \r\n select ");
		sql.append(" \r\n  outorganname");
		sql.append(" \r\n ,max(BIGREGION) BIGREGION");
		sql.append(" \r\n ,max(outmiddleREGION) outmiddleREGION");
		sql.append(" \r\n ,inorganname ");
		sql.append(" \r\n ,max(inmiddleREGION) inmiddleREGION ");
		sql.append(" \r\n ,max(smallREGION)  smallREGION");
		sql.append(" \r\n ,productname");
		sql.append(" \r\n ,spec ");
		sql.append(" \r\n ,sum(amount) amount ");
		sql.append(" \r\n ,sum(bonusPoint) bonusPoint ");
		sql.append(" \r\n ,max(unitId) unitId from (");
		
		sql.append(" \r\n select ");
		sql.append(" \r\n  outo.organname outorganname ");
		sql.append(" \r\n ,sba3.name_loc BIGREGION ");
		sql.append(" \r\n ,sba2.name_loc outmiddleREGION ");
		sql.append(" \r\n ,o.organname inorganname   ");
		sql.append(" \r\n ,sba5.name_loc inmiddleREGION   ");
		sql.append(" \r\n ,sba4.name_loc smallREGION");
		sql.append(" \r\n ,p.productname ");
		sql.append(" \r\n ,p.specmode spec ");
		sql.append(" \r\n ,samd.receivequantity*fu.xquantity*p.boxquantity amount  ");
		sql.append(" \r\n ,bonuspoint bonusPoint  ");
		sql.append(" \r\n ,p.countunit unitId  ");
		
		sql.append(" \r\n from STOCK_ALTER_MOVE sam ");
		sql.append(" \r\n join ORGAN o on sam.receiveorganid=o.id and o.ORGANTYPE = 2 and o.ORGANMODEL <> 1 and sam.iscomplete = 1 ");
		if(!StringUtil.isEmpty(scrForm.getInOrganId())){
			sql.append(" \r\n and o.id ='" + scrForm.getInOrganId() + "'"); //结束时间条件
		} else {
			sql.append(" \r\n and " + condition);
		}
		
		sql.append(" \r\n join ORGAN outo on sam.outorganid = OUTO.id ");
		
		if(!StringUtil.isEmpty(scrForm.getBeginDate())){
			sql.append(" \r\n and sam.makedate >=to_date('" + scrForm.getBeginDate() + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		if(!StringUtil.isEmpty(scrForm.getEndDate())){
			sql.append(" \r\n and sam.makedate < to_date('" + scrForm.getEndDate() + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		}
		if(!StringUtil.isEmpty(scrForm.getOutOrganId())){
			sql.append(" \r\n and outo.id ='" + scrForm.getOutOrganId() + "'"); //结束时间条件
		}
		
		sql.append(" \r\n join STOCK_ALTER_MOVE_DETAIL samd on samd.samid=sam.id ");
		sql.append(" \r\n join F_UNIT fu on fu.productid = samd.productid and fu.funitid = samd.unitid ");
		sql.append(" \r\n join product p on p.id = samd.productid ");
		
		if(!StringUtil.isEmpty(scrForm.getProductName())){
			sql.append(" \r\n and p.productname ='" + scrForm.getProductName() + "'"); //结束时间条件
		}
		if(!StringUtil.isEmpty(scrForm.getSpec())){
			sql.append(" \r\n and p.specmode ='" + scrForm.getSpec() + "'"); //结束时间条件
		}
		sql.append(" \r\n LEFT JOIN S_BONUS_DETAIL sbd on sbd.remark=sam.id and sbd.mcode = p.mcode and sbd.organid=sam.receiveorganid");
		sql.append(" \r\n LEFT JOIN SALES_AREA_COUNTRY sac on outo.areas = sac.countryareaid and outo.organmodel not in (1,2) ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba1 on sba1.id = sac.salesareaid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba2 on sba2.id = sba1.parentid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba3 on sba3.id = sba2.parentid ");
		
		sql.append(" \r\n LEFT JOIN SALES_AREA_COUNTRY sac2 on o.areas = sac2.countryareaid and o.organmodel not in (1,2) ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba4 on sba4.id = sac2.salesareaid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba5 on sba5.id = sba4.parentid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba6 on sba6.id = sba5.parentid ");
		sql.append(" where 1=1 ");
		if(region != null) {
			if(region.getRank() == 2) {
				sql.append(" and sba4.id="+region.getId());
			} else if(region.getRank() == 1) {
				sql.append(" and sba5.id="+region.getId());
			} else if(region.getRank() == 0) {
				sql.append(" and sba6.id="+region.getId());
			}
		}
		if(!StringUtil.isEmpty(scrForm.getAreaId())) {
			sql.append(" and o.areas="+scrForm.getAreaId());
		}
		
		sql.append(" UNION ALL ");
		
		sql.append(" \r\n select ");
		sql.append(" \r\n  outo.organname outorganname ");
		sql.append(" \r\n ,sba3.name_loc BIGREGION ");
		sql.append(" \r\n ,sba2.name_loc outmiddleREGION ");
		sql.append(" \r\n ,o.organname inorganname   ");
		sql.append(" \r\n ,sba5.name_loc inmiddleREGION   ");
		sql.append(" \r\n ,sba4.name_loc smallREGION");
		sql.append(" \r\n ,p.productname ");
		sql.append(" \r\n ,p.specmode spec ");
		sql.append(" \r\n ,-(owd.takequantity*fu.xquantity*p.boxquantity) amount  ");
		sql.append(" \r\n ,bonuspoint bonusPoint  ");
		sql.append(" \r\n ,p.countunit unitId  ");
		
		sql.append(" \r\n from ORGAN_WITHDRAW ow ");
		sql.append(" \r\n join ORGAN o on ow.porganid=o.id and o.ORGANTYPE = 2 and o.ORGANMODEL <> 1 and ow.iscomplete = 1 ");
		if(!StringUtil.isEmpty(scrForm.getInOrganId())){
			sql.append(" \r\n and o.id ='" + scrForm.getInOrganId() + "'"); //结束时间条件
		} else {
			sql.append(" \r\n and " + condition);
		}
		
		sql.append(" \r\n join ORGAN outo on ow.receiveorganid = OUTO.id ");
		
		if(!StringUtil.isEmpty(scrForm.getBeginDate())){
			sql.append(" \r\n and ow.makedate >=to_date('" + scrForm.getBeginDate() + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		if(!StringUtil.isEmpty(scrForm.getEndDate())){
			sql.append(" \r\n and ow.makedate < to_date('" + scrForm.getEndDate() + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		}
		if(!StringUtil.isEmpty(scrForm.getOutOrganId())){
			sql.append(" \r\n and outo.id ='" + scrForm.getOutOrganId() + "'"); //结束时间条件
		}
		
		sql.append(" \r\n join ORGAN_WITHDRAW_DETAIL owd on owd.owid=ow.id ");
		sql.append(" \r\n join F_UNIT fu on fu.productid = owd.productid and fu.funitid = owd.unitid ");
		sql.append(" \r\n join product p on p.id = owd.productid ");
		
		if(!StringUtil.isEmpty(scrForm.getProductName())){
			sql.append(" \r\n and p.productname ='" + scrForm.getProductName() + "'"); //结束时间条件
		}
		if(!StringUtil.isEmpty(scrForm.getSpec())){
			sql.append(" \r\n and p.specmode ='" + scrForm.getSpec() + "'"); //结束时间条件
		}
		sql.append(" \r\n LEFT JOIN S_BONUS_DETAIL sbd on sbd.remark=ow.id and sbd.mcode = p.mcode and sbd.organid=ow.receiveorganid");
		sql.append(" \r\n LEFT JOIN SALES_AREA_COUNTRY sac on outo.areas = sac.countryareaid and outo.organmodel not in (1,2) ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba1 on sba1.id = sac.salesareaid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba2 on sba2.id = sba1.parentid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba3 on sba3.id = sba2.parentid ");
		
		sql.append(" \r\n LEFT JOIN SALES_AREA_COUNTRY sac2 on o.areas = sac2.countryareaid and o.organmodel not in (1,2) ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba4 on sba4.id = sac2.salesareaid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba5 on sba5.id = sba4.parentid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba6 on sba6.id = sba5.parentid ");
		sql.append(" where 1=1 ");
		if(region != null) {
			if(region.getRank() == 2) {
				sql.append(" and sba4.id="+region.getId());
			} else if(region.getRank() == 1) {
				sql.append(" and sba5.id="+region.getId());
			} else if(region.getRank() == 0) {
				sql.append(" and sba6.id="+region.getId());
			}
		}
		if(!StringUtil.isEmpty(scrForm.getAreaId())) {
			sql.append(" and o.areas="+scrForm.getAreaId());
		}
		
		sql.append(" \r\n ) temp");
		sql.append(" \r\n group by outorganname,inorganname,productname,spec");
		
//		System.out.println(sql.toString());
		List<Map> list =  new ArrayList<Map>();
		if(request == null && pageSize == 0){
			sql.append(" \r\n order by outorganname,inorganname,productname,spec");
			list =  EntityManager.jdbcquery(sql.toString());
		}else {
			list =  PageQuery.jdbcSqlserverQuery(request, "outorganname,inorganname,productname,spec", sql.toString(), pageSize);
		}
		if(list != null && list.size()>0){
			Map<Integer, String> countUnitMap = appBr.getBaseResourceMap("CountUnit");
			for(Map map : list){
				RecAndDisSumReportForm scForm = new RecAndDisSumReportForm();
				//将Map中对应的值赋值给实例
				MapUtil.mapToObject(map, scForm);
				scForm.setUnitName(countUnitMap.get(scForm.getUnitId()));
				resultList.add(scForm);
			}
		}
		return resultList;
	}
	
	public List<RecAndDisSumReportForm> queryDetailReport(HttpServletRequest request, int pageSize,RecAndDisSumReportForm scrForm,UsersBean users) throws Exception{
		
		//对于结束日期增加一天
		if(!StringUtil.isEmpty(scrForm.getEndDate())){
			String endDateStr = scrForm.getEndDate();
			Date endDate = Dateutil.StringToDate(endDateStr);
			endDate = Dateutil.addDay2Date(1, endDate);
			scrForm.setEndDate(Dateutil.formatDate(endDate));
		}
		
		RegionItemInfo region = null;
		if(!StringUtil.isEmpty(scrForm.getRegionId())) {
			AppSBonusArea arii = new AppSBonusArea();
			region = arii.getSBonusAreaById(scrForm.getRegionId());
		}
		
		String condition = SBonusService.getWhereCondition(users);
		
		List<RecAndDisSumReportForm> resultList = new ArrayList<RecAndDisSumReportForm>();
		StringBuffer sql = new StringBuffer();
		
		/*sql.append(" \r\n select ");
		sql.append(" \r\n  outo.organname outorganname");
		sql.append(" \r\n ,sba3.name_loc BIGREGION");
		sql.append(" \r\n ,sba2.name_loc outmiddleREGION");
		sql.append(" \r\n ,o.organname inorganname ");
		sql.append(" \r\n ,sba5.name_loc inmiddleREGION ");
		sql.append(" \r\n ,sba4.name_loc smallREGION");
		sql.append(" \r\n ,p.productname");
		sql.append(" \r\n ,p.specmode spec ");
		sql.append(" \r\n ,ttd.realquantity*fu.xquantity*p.boxquantity amount ");
		sql.append(" \r\n ,bonuspoint bonusPoint ");
		sql.append(" \r\n ,p.countunit unitId  ");
		sql.append(" \r\n ,tt.BSORT ");
		sql.append(" \r\n ,TT.MAKEDATE ");
		
		sql.append(" \r\n from TAKE_TICKET tt ");
		sql.append(" \r\n join ORGAN o on tt.inoid=o.id and o.ORGANTYPE = 2 and o.ORGANMODEL <> 1 and tt.isaudit = 1 ");
		if(!StringUtil.isEmpty(scrForm.getInOrganId())){
			sql.append(" \r\n and o.id ='" + scrForm.getInOrganId() + "'"); //结束时间条件
		} else {
			sql.append(" \r\n and " + condition);
		}
		
		sql.append(" \r\n join ORGAN outo on tt.OID = OUTO.id ");
		
		if(!StringUtil.isEmpty(scrForm.getBeginDate())){
			sql.append(" \r\n and tt.makedate >=to_date('" + scrForm.getBeginDate() + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		if(!StringUtil.isEmpty(scrForm.getEndDate())){
			sql.append(" \r\n and tt.makedate < to_date('" + scrForm.getEndDate() + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		}
		if(!StringUtil.isEmpty(scrForm.getOutOrganId())){
			sql.append(" \r\n and outo.id ='" + scrForm.getOutOrganId() + "'"); //结束时间条件
		}
		
		sql.append(" \r\n join TAKE_TICKET_DETAIL ttd on ttd.ttid=tt.id ");
		sql.append(" \r\n join F_UNIT fu on fu.productid = ttd.productid and fu.funitid = ttd.unitid ");
		sql.append(" \r\n join product p on p.id = ttd.productid ");
		
		if(!StringUtil.isEmpty(scrForm.getProductName())){
			sql.append(" \r\n and p.productname ='" + scrForm.getProductName() + "'"); //结束时间条件
		}
		if(!StringUtil.isEmpty(scrForm.getSpec())){
			sql.append(" \r\n and p.specmode ='" + scrForm.getSpec() + "'"); //结束时间条件
		}
		sql.append(" \r\n LEFT JOIN S_BONUS_DETAIL sbd on sbd.remark=tt.billno and sbd.mcode = p.mcode ");
		sql.append(" \r\n LEFT JOIN SALES_AREA_COUNTRY sac on outo.areas = sac.countryareaid and outo.organmodel not in (1,2) ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba1 on sba1.id = sac.salesareaid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba2 on sba2.id = sba1.parentid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba3 on sba3.id = sba2.parentid ");
		
		sql.append(" \r\n LEFT JOIN SALES_AREA_COUNTRY sac2 on o.areas = sac2.countryareaid and o.organmodel not in (1,2) ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba4 on sba4.id = sac2.salesareaid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba5 on sba5.id = sba4.parentid ");*/
		
		
		sql.append(" \r\n select ");
		sql.append(" \r\n  outo.organname outorganname ");
		sql.append(" \r\n ,sba3.name_loc BIGREGION ");
		sql.append(" \r\n ,sba2.name_loc outmiddleREGION ");
		sql.append(" \r\n ,o.organname inorganname   ");
		sql.append(" \r\n ,sba5.name_loc inmiddleREGION   ");
		sql.append(" \r\n ,sba4.name_loc smallREGION");
		sql.append(" \r\n ,p.productname ");
		sql.append(" \r\n ,p.specmode spec ");
		sql.append(" \r\n ,samd.receivequantity*fu.xquantity*p.boxquantity amount  ");
		sql.append(" \r\n ,bonuspoint bonusPoint  ");
		sql.append(" \r\n ,p.countunit unitId  ");
		sql.append(" \r\n ,1 BSORT ");
		sql.append(" \r\n ,sam.MAKEDATE ");
		
		sql.append(" \r\n from STOCK_ALTER_MOVE sam ");
		sql.append(" \r\n join ORGAN o on sam.receiveorganid=o.id and o.ORGANTYPE = 2 and o.ORGANMODEL <> 1 and sam.iscomplete = 1 ");
		if(!StringUtil.isEmpty(scrForm.getInOrganId())){
			sql.append(" \r\n and o.id ='" + scrForm.getInOrganId() + "'"); //结束时间条件
		} else {
			sql.append(" \r\n and " + condition);
		}
		
		sql.append(" \r\n join ORGAN outo on sam.outorganid = OUTO.id ");
		
		if(!StringUtil.isEmpty(scrForm.getBeginDate())){
			sql.append(" \r\n and sam.makedate >=to_date('" + scrForm.getBeginDate() + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		if(!StringUtil.isEmpty(scrForm.getEndDate())){
			sql.append(" \r\n and sam.makedate < to_date('" + scrForm.getEndDate() + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		}
		if(!StringUtil.isEmpty(scrForm.getOutOrganId())){
			sql.append(" \r\n and outo.id ='" + scrForm.getOutOrganId() + "'"); //结束时间条件
		}
		
		sql.append(" \r\n join STOCK_ALTER_MOVE_DETAIL samd on samd.samid=sam.id ");
		sql.append(" \r\n join F_UNIT fu on fu.productid = samd.productid and fu.funitid = samd.unitid ");
		sql.append(" \r\n join product p on p.id = samd.productid ");
		
		if(!StringUtil.isEmpty(scrForm.getProductName())){
			sql.append(" \r\n and p.productname ='" + scrForm.getProductName() + "'"); //结束时间条件
		}
		if(!StringUtil.isEmpty(scrForm.getSpec())){
			sql.append(" \r\n and p.specmode ='" + scrForm.getSpec() + "'"); //结束时间条件
		}
		sql.append(" \r\n LEFT JOIN S_BONUS_DETAIL sbd on sbd.remark=sam.id and sbd.mcode = p.mcode and sbd.organid=sam.receiveorganid");
		sql.append(" \r\n LEFT JOIN SALES_AREA_COUNTRY sac on outo.areas = sac.countryareaid and outo.organmodel not in (1,2) ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba1 on sba1.id = sac.salesareaid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba2 on sba2.id = sba1.parentid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba3 on sba3.id = sba2.parentid ");
		
		sql.append(" \r\n LEFT JOIN SALES_AREA_COUNTRY sac2 on o.areas = sac2.countryareaid and o.organmodel not in (1,2) ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba4 on sba4.id = sac2.salesareaid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba5 on sba5.id = sba4.parentid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba6 on sba6.id = sba5.parentid ");
		sql.append(" where 1=1 ");
		if(region != null) {
			if(region.getRank() == 2) {
				sql.append(" and sba4.id="+region.getId());
			} else if(region.getRank() == 1) {
				sql.append(" and sba5.id="+region.getId());
			} else if(region.getRank() == 0) {
				sql.append(" and sba6.id="+region.getId());
			}
		}
		if(!StringUtil.isEmpty(scrForm.getAreaId())) {
			sql.append(" and o.areas="+scrForm.getAreaId());
		}
		
		sql.append(" UNION ALL ");
		
		sql.append(" \r\n select ");
		sql.append(" \r\n  outo.organname outorganname ");
		sql.append(" \r\n ,sba3.name_loc BIGREGION ");
		sql.append(" \r\n ,sba2.name_loc outmiddleREGION ");
		sql.append(" \r\n ,o.organname inorganname   ");
		sql.append(" \r\n ,sba5.name_loc inmiddleREGION   ");
		sql.append(" \r\n ,sba4.name_loc smallREGION");
		sql.append(" \r\n ,p.productname ");
		sql.append(" \r\n ,p.specmode spec ");
		sql.append(" \r\n ,-(owd.takequantity*fu.xquantity*p.boxquantity) amount  ");
		sql.append(" \r\n ,bonuspoint bonusPoint  ");
		sql.append(" \r\n ,p.countunit unitId  ");
		sql.append(" \r\n ,7 BSORT ");
		sql.append(" \r\n ,ow.MAKEDATE ");
		
		sql.append(" \r\n from ORGAN_WITHDRAW ow ");
		sql.append(" \r\n join ORGAN o on ow.porganid=o.id and o.ORGANTYPE = 2 and o.ORGANMODEL <> 1 and ow.iscomplete = 1 ");
		if(!StringUtil.isEmpty(scrForm.getInOrganId())){
			sql.append(" \r\n and o.id ='" + scrForm.getInOrganId() + "'"); //结束时间条件
		} else {
			sql.append(" \r\n and " + condition);
		}
		
		sql.append(" \r\n join ORGAN outo on ow.receiveorganid = OUTO.id ");
		
		if(!StringUtil.isEmpty(scrForm.getBeginDate())){
			sql.append(" \r\n and ow.makedate >=to_date('" + scrForm.getBeginDate() + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		if(!StringUtil.isEmpty(scrForm.getEndDate())){
			sql.append(" \r\n and ow.makedate < to_date('" + scrForm.getEndDate() + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		}
		if(!StringUtil.isEmpty(scrForm.getOutOrganId())){
			sql.append(" \r\n and outo.id ='" + scrForm.getOutOrganId() + "'"); //结束时间条件
		}
		
		sql.append(" \r\n join ORGAN_WITHDRAW_DETAIL owd on owd.owid=ow.id ");
		sql.append(" \r\n join F_UNIT fu on fu.productid = owd.productid and fu.funitid = owd.unitid ");
		sql.append(" \r\n join product p on p.id = owd.productid ");
		
		if(!StringUtil.isEmpty(scrForm.getProductName())){
			sql.append(" \r\n and p.productname ='" + scrForm.getProductName() + "'"); //结束时间条件
		}
		if(!StringUtil.isEmpty(scrForm.getSpec())){
			sql.append(" \r\n and p.specmode ='" + scrForm.getSpec() + "'"); //结束时间条件
		}
		sql.append(" \r\n LEFT JOIN S_BONUS_DETAIL sbd on sbd.remark=ow.id and sbd.mcode = p.mcode and sbd.organid=ow.receiveorganid");
		sql.append(" \r\n LEFT JOIN SALES_AREA_COUNTRY sac on outo.areas = sac.countryareaid and outo.organmodel not in (1,2) ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba1 on sba1.id = sac.salesareaid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba2 on sba2.id = sba1.parentid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba3 on sba3.id = sba2.parentid ");
		
		sql.append(" \r\n LEFT JOIN SALES_AREA_COUNTRY sac2 on o.areas = sac2.countryareaid and o.organmodel not in (1,2) ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba4 on sba4.id = sac2.salesareaid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba5 on sba5.id = sba4.parentid ");
		sql.append(" \r\n LEFT JOIN S_BONUS_AREA sba6 on sba6.id = sba5.parentid ");
		sql.append(" where 1=1 ");
		if(region != null) {
			if(region.getRank() == 2) {
				sql.append(" and sba4.id="+region.getId());
			} else if(region.getRank() == 1) {
				sql.append(" and sba5.id="+region.getId());
			} else if(region.getRank() == 0) {
				sql.append(" and sba6.id="+region.getId());
			}
		}
		if(!StringUtil.isEmpty(scrForm.getAreaId())) {
			sql.append(" and o.areas="+scrForm.getAreaId());
		}
		
		System.out.println(sql.toString());
		List<Map> list =  new ArrayList<Map>();
		if(request == null && pageSize == 0){
			list =  EntityManager.jdbcquery(sql.toString());
		}else {
			list =  PageQuery.jdbcSqlserverQuery(request, "outorganname", sql.toString(), pageSize);
		}
		if(list != null && list.size()>0){
			Map<Integer, String> countUnitMap = appBr.getBaseResourceMap("CountUnit");
			for(Map map : list){
				RecAndDisSumReportForm scForm = new RecAndDisSumReportForm();
				//将Map中对应的值赋值给实例
				MapUtil.mapToObject(map, scForm);
				scForm.setUnitName(countUnitMap.get(scForm.getUnitId()));
				resultList.add(scForm);
			}
		}
		return resultList;
	}
}
