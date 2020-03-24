package com.winsafe.drp.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppInventorySSCCCodeReport {

	@SuppressWarnings("unchecked")
	public List<Map> getInventorySSCCCodeReport(HttpServletRequest request, int pageSize, UsersBean users) throws Exception {
		//生成根据页面条件生成whereSql
		StringBuffer whereSql = new StringBuffer();
		Map paraMap = new HashMap(request.getParameterMap());
		//区域
		if(!StringUtil.isEmpty((String)paraMap.get("region"))){
			whereSql.append(" \r\n and temp.inRegionCode = '").append(((String)paraMap.get("region")).trim()).append("' "); 
		}
		//机构条件
		if(!StringUtil.isEmpty((String)paraMap.get("organId"))){
			whereSql.append(" \r\n and temp.inOrganId = '").append(((String)paraMap.get("organId")).trim()).append("' "); 
		}
		//仓库条件
		if(!StringUtil.isEmpty((String)paraMap.get("warehouseId"))){
			whereSql.append(" \r\n and temp.inWarehouseId = '").append(((String)paraMap.get("warehouseId")).trim()).append("' "); 
		}
		//产品条件
		if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) {
			whereSql.append("\r\n and temp.productName = '").append(((String)paraMap.get("ProductName")).trim()).append("' and temp.specmode= '").append(((String)paraMap.get("packSizeName")).trim()).append("' ");
		} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) {
			whereSql.append("\r\n and temp.productName = '").append(((String)paraMap.get("ProductName")).trim()).append("' ");
		}
		//批次
		if(!StringUtil.isEmpty((String)paraMap.get("batch"))){
			whereSql.append("\r\n and temp.batch = '").append(((String)paraMap.get("batch")).trim()).append("' ");
		}
		//箱码
		if(!StringUtil.isEmpty((String)paraMap.get("idcode"))){
			whereSql.append("\r\n and temp.idcode = '").append(((String)paraMap.get("idcode")).trim()).append("' ");
		}
		//管辖权限
		if(DbUtil.isDealer(users)) {
			whereSql.append("\r\n and temp.inWarehouseId in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="+users.getUserid()+")");
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("\r\n select * from ( ");
		sb.append("\r\n select temp3.billno, temp3.billnccode, oregion.regioncode as inRegionCode, oregion.sortname as inRegionName, oprovince.areaname as inProvinceName, o.id as inOrganId, o.oecode as inOrganOecode, o.organname as inOrganName,  w.id as inWarehouseId, w.warehousename as inWarehouseName, p.id as productid, p.mcode, p.matericalchdes, p.matericalendes, p.productname, p.productnameen, p.specmode, p.packsizeNameEn, temp3.batch, temp3.idcode, temp3.isout, to_date(temp3.producedate, 'yyyy-mm-dd') as productiondate, to_date(temp3.producedate, 'yyyy-mm-dd')+p.expirydays as expirydate ");
		sb.append("\r\n from ( ");
		sb.append("\r\n 	select sam.id as billno, sam.nccode as billnccode, i.idcode, i.isout, i.productid, i.batch, i.producedate, case when i.isout=0 then i.warehouseid else tt.inwarehouseid end as warehouseid ");
		sb.append("\r\n 	from idcode i left join ");
		sb.append("\r\n 	( ");
		//sb.append("\r\n 		select tti.idcode, max(tt.id) as ttid ");
		//sb.append("\r\n 		from take_ticket_idcode tti,(select * from TAKE_TICKET where ISAUDIT=1 and bsort=1) tt ");
		//sb.append("\r\n 		where tt.id=tti.ttid ");
		//sb.append("\r\n 		group by tti.idcode ");
		sb.append("\r\n 		select temp12.idcode, temp12.ttid from ( ");
		sb.append("\r\n 			select temp11.*, row_number() over(partition by temp11.idcode order by temp11.auditdate desc) as rn from ( ");
		sb.append("\r\n 				select tti.idcode, tt.id as ttid, tt.auditdate ");
		sb.append("\r\n 				from (select tt.id, tt.auditdate from take_ticket tt where tt.bsort=1 and tt.isaudit=1) tt, take_ticket_idcode tti ");
		sb.append("\r\n 				where tt.id=tti.ttid ");
		sb.append("\r\n 			) temp11 ");
		sb.append("\r\n 		) temp12 ");
		sb.append("\r\n 		where rn=1 ");
		sb.append("\r\n 	) temp2 on i.idcode=temp2.idcode ");
		sb.append("\r\n 	left join take_ticket tt on temp2.ttid=tt.id ");
		sb.append("\r\n 	left join stock_alter_move sam on sam.id=tt.billno ");
		sb.append("\r\n ) temp3 left join product p on temp3.productid=p.id  ");
		sb.append("\r\n left join warehouse w on w.id=temp3.warehouseid and w.USEFLAG = 1");
		sb.append("\r\n left join organ o on o.id=w.makeorganid ");
		sb.append("\r\n left join country_area oprovince on oprovince.id=o.province ");
		sb.append("\r\n left join region_area oregionarea on oregionarea.areaid=oprovince.id ");
		sb.append("\r\n left join region oregion on oregion.regioncode=oregionarea.regioncodeid ");
		sb.append("\r\n where p.USEFLAG = 1 "); //过滤不可用的产品
		if(!DbUtil.isDealer(users)) {
			sb.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
		}
		sb.append("\r\n ) temp");
		sb.append("\r\n where 1=1 ").append(whereSql);
		sb.append("\r\n order by NLSSORT(inRegionName,'NLS_SORT = SCHINESE_PINYIN_M'),NLSSORT(inProvinceName,'NLS_SORT = SCHINESE_PINYIN_M'),NLSSORT(inOrganName,'NLS_SORT = SCHINESE_PINYIN_M'), NLSSORT(productnameen,'NLS_SORT = SCHINESE_PINYIN_M')");
		List<Map> list = null;
		if(pageSize <= 0){
			list = EntityManager.jdbcquery(sb.toString());
		}else {
			list = PageQuery.jdbcSqlserverQuery(request, "NLSSORT(inRegionName,'NLS_SORT = SCHINESE_PINYIN_M'),NLSSORT(inProvinceName,'NLS_SORT = SCHINESE_PINYIN_M'),NLSSORT(inOrganName,'NLS_SORT = SCHINESE_PINYIN_M'), NLSSORT(productnameen,'NLS_SORT = SCHINESE_PINYIN_M')", sb.toString(), pageSize);
		}
		return list;
	}
}
