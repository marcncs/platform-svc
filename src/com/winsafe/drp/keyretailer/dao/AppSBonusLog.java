package com.winsafe.drp.keyretailer.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eitop.platform.tools.jdbc.Entity;
import com.winsafe.drp.dao.RegionItemInfo;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.keyretailer.pojo.SBonusLog;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager2.PageQuery;
public class AppSBonusLog {
	
	public void addSBonusLog(SBonusLog d) throws Exception {		
		EntityManager.save(d);		
	}
	
	public void updSBonusLog(SBonusLog d) throws Exception {		
		EntityManager.update(d);		
	}

	public List<SBonusLog> getSBonusLog(HttpServletRequest request,
			int pagesize, String whereSql) throws Exception { 
		String hql="from SBonusLog as p "+whereSql +" order by p.id desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	public List<Map<String, String>> getSBonusLogs(HttpServletRequest request,
			int pagesize, String whereSql) throws Exception {
		RegionItemInfo region = null;
		if(!StringUtil.isEmpty(request.getParameter("regionId"))) {
			AppSBonusArea arii = new AppSBonusArea();
			region = arii.getSBonusAreaById(request.getParameter("regionId"));
		}
		StringBuffer sql = new StringBuffer();
		sql.append("select sbl.*,o.ORGANNAME,sba3.name_loc BIGREGION,sba2.name_loc middleregion,sba1.name_loc smallregion from S_BONUS_LOG sbl ");
		sql.append("JOIN ORGAN o on o.id = SBL.ORGANID ");
		if(!StringUtil.isEmpty(request.getParameter("bonusType"))) {
			sql.append(" and sbl.bonusType="+request.getParameter("bonusType"));
		}
		sql.append(" LEFT JOIN SALES_AREA_COUNTRY sac on sac.countryareaid = o.areas and o.organtype=2 and organmodel not in (1,2) ");
		sql.append("LEFT JOIN S_BONUS_AREA sba1 on sba1.id = sac.salesareaid ");
		sql.append("LEFT JOIN S_BONUS_AREA sba2 on sba2.id = sba1.parentid ");
		sql.append("LEFT JOIN S_BONUS_AREA sba3 on sba3.id = sba2.parentid ");
		sql.append("where 1=1 ");
		if(!StringUtil.isEmpty(request.getParameter("organId"))) {
			sql.append(" and sbl.organId="+request.getParameter("organId"));
		}
		if(region != null) {
			if(region.getRank() == 2) {
				sql.append(" and sba1.id="+region.getId());
			} else if(region.getRank() == 1) {
				sql.append(" and sba2.id="+region.getId());
			} else if(region.getRank() == 0) {
				sql.append(" and sba3.id="+region.getId());
			}
		}
		sql.append(whereSql);
		return PageQuery.jdbcSqlserverQuery(request, "id desc", sql.toString(), pagesize);
	}
	
}

